package com.lnet.ums.service;

import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.framework.core.ResponseBuilder;
import com.lnet.model.ums.notification.notificationDto.NotificationDto;
import com.lnet.model.ums.notification.notificationEntity.Notification;
import com.lnet.model.ums.notification.notificationEntity.NotificationBuilder;
import com.lnet.model.ums.notification.notificationEntity.NotificationList;
import com.lnet.model.ums.notification.notificationEntity.NotificationReceiver;
import com.lnet.ums.contract.api.NotificationService;
import com.lnet.ums.mapper.dao.NotificationListRepository;
import com.lnet.ums.mapper.dao.NotificationReceiverRepository;
import com.lnet.ums.mapper.dao.NotificationRepository;
import com.lnet.ums.mapper.dao.UnderscoresNameStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Resource
    private NotificationRepository notificationRepository;

    @Resource
    private NotificationReceiverRepository receiverRepository;

    @Resource
    private NotificationListRepository notificationListRepository;


    @Override
    public Response<String> send(String subject, String body, String sender, String receiver) {
        try {
            Assert.hasText(subject, "主题不能为空！");
            Assert.hasText(body, "内容不能为空！");
            Assert.hasText(sender, "发送人不能为空！");
            Assert.hasText(receiver, "接收人不能为空！");

            return ResponseBuilder.supply(() -> {
                Notification notification = NotificationBuilder.builder()
                        .withSubject(subject)
                        .withBody(body)
                        .withReceiver(receiver)
                        .withSender(sender)
                        .build();
                Notification saved = notificationRepository.save(notification);
                return saved.getNotifyId();
            });
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail("创建通知失败！" + e.getMessage());
        }
    }

    @Override
    public Response<String> send(String subject, String body, String sender, List<String> receivers) {
        try {
            Assert.hasText(subject, "主题不能为空！");
            Assert.hasText(body, "内容不能为空！");
            Assert.hasText(sender, "发送人不能为空！");
            Assert.notEmpty(receivers, "接收人不能为空！");

            return ResponseBuilder.supply(() -> {
                Notification notification = NotificationBuilder.builder()
                        .withSubject(subject)
                        .withBody(body)
                        .withSender(sender)
                        .withReceivers(receivers)
                        .build();

                Notification saved = notificationRepository.save(notification);

                return saved.getNotifyId();
            });

            /*Notification notification = NotificationBuilder.builder()
                    .withSubject(subject)
                    .withBody(body)
                    .withSender(sender)
                    .withReceivers(receivers)
                    .build();

            Notification saved = notificationRepository.save(notification);
            Assert.notNull(saved);
            return ResponseBuilder.success(saved.getNotifyId());*/
        } catch (Exception e) {
            log.error("", e);

            return ResponseBuilder.fail("创建通知失败！" + e.getMessage());
        }
    }

    @Override
    public Response recall(String notifyId) {
        try {
            return ResponseBuilder.supply(() -> {
                Notification notification = notificationRepository.findOne(notifyId);
                Assert.notNull(notification);

                notification.setRecalled(true);
                notificationRepository.save(notification);

                return null;
            });
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail("通知撤销失败！" + e.getMessage());
        }
    }

    @Override
    public Response<List<NotificationDto>> getNotificationsForReader(String reader, int count) {
        try {
            return ResponseBuilder.supply(() -> {
                List<NotificationList> notificationLists = notificationListRepository.findByReceiver(reader.toUpperCase());
                Assert.notEmpty(notificationLists, "没有未读通知！");
                List<NotificationDto> notificationDtos = notificationLists.stream()
                        .map(ele -> {
                            NotificationDto dto = NotificationDto.builder()
                                    .id(ele.getNotifyId())
                                    .subject(ele.getSubject())
                                    .body(ele.getBody())
                                    .sender(ele.getSender())
                                    .sentTime(ele.getSentTime())
                                    .timeAgo(String.valueOf(LocalDateTime.now().getMinute() - ele.getSentTime().getMinute()))
                                    .isRead(ele.isRead())
                                    .build();
                            return dto;
                        }).collect(Collectors.toList());
                return notificationDtos;
            });
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response<NotificationDto> get(String notifyId) {
        try {
            return ResponseBuilder.supply(() -> {
                Notification notification = notificationRepository.getByNotifyId(notifyId);
                List<NotificationReceiver> notificationReceivers = receiverRepository.findByNotifyId(notifyId);

                NotificationDto dto = NotificationDto.builder()
                        .id(notification.getNotifyId())
                        .subject(notification.getSubject())
                        .body(notification.getBody())
                        .sender(notification.getSender())
                        .sentTime(notification.getSentTime())
                        .build();
                dto.setReceivers(notificationReceivers.stream().map(ele -> new String(ele.getReceiver())).collect(Collectors.toList()));
                return dto;
            });
        } catch (Exception e) {
            log.error("", e);
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @Override
    public Response read(String notifyId, String receiver) {

        return ResponseBuilder.supply(() -> {

            NotificationReceiver receiverEntity = receiverRepository.findByNotifyIdAndReceiver(notifyId, receiver.toUpperCase());
            Assert.notNull(receiverEntity);

            receiverEntity.setRead(true);
            receiverEntity.setReadTime(LocalDateTime.now());
            receiverRepository.save(receiverEntity);

            return null;
        });
    }

    @Override
    public PageResponse<NotificationDto> pageList(Integer pageNo, Integer pageSize, Map<String, Object> params) {
        try {
            Sort sort = null;
            if (!StringUtils.isEmpty(params.get("orderBy"))) {
                String[] s = params.get("orderBy").toString().split(" ");
                sort = new Sort(s[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, UnderscoresNameStrategy.columnToProperty(s[0]));
            }
            Page<Notification> pageInfo = notificationRepository.findAll(new Specification<Notification>() {
                @Override
                public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Expression> expressions = new ArrayList<Expression>();
                    if (!StringUtils.isEmpty(params.get("subject"))) {
                        expressions.add(cb.like(root.get("subject"), "%" + params.get("subject").toString() + "%"));
                    }
                    if (!StringUtils.isEmpty(params.get("sender"))) {
                        expressions.add(cb.equal(root.get("sender"), params.get("sender").toString()));
                    }
                    if (expressions.size() > 0) {
                        return cb.and(expressions.toArray(new Predicate[expressions.size()]));
                    }
                    return null;
                }
            }, new PageRequest(pageNo - 1, pageSize, sort));


            List<NotificationDto> notificationDtos = pageInfo.getContent().stream()
                    .map(ele -> {
                        NotificationDto dto = NotificationDto.builder()
                                .id(ele.getNotifyId())
                                .subject(ele.getSubject())
                                .body(ele.getBody())
                                .sender(ele.getSender())
                                .isRecalled(ele.isRecalled())
                                .sentTime(ele.getSentTime())
                                .build();
                        return dto;
                    }).collect(Collectors.toList());

            return ResponseBuilder.page(notificationDtos, pageNo, pageInfo.getSize(), pageInfo.getTotalElements());
        } catch (Exception e) {
            log.error("", e);
            return (PageResponse) ResponseBuilder.fail(e);
        }
    }

    @Override
    public PageResponse<NotificationDto> queryForReceive(Integer pageNo, Integer pageSize, Map<String, Object> params) {
        try {
            Sort sort = null;
            params.put("recalled", 0);
            if (!StringUtils.isEmpty(params.get("orderBy"))) {
                String[] s = params.get("orderBy").toString().split(" ");
                sort = new Sort(s[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, UnderscoresNameStrategy.columnToProperty(s[0]));
            }
            Page<NotificationList> pageInfo = notificationListRepository.findAll(new Specification<NotificationList>() {
                @Override
                public Predicate toPredicate(Root<NotificationList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Expression> expressions = new ArrayList<Expression>();
                    if (!StringUtils.isEmpty(params.get("subject"))) {
                        expressions.add(cb.like(root.get("subject"), "%" + params.get("subject").toString() + "%"));
                    }
                    if (!StringUtils.isEmpty(params.get("sender"))) {
                        expressions.add(cb.like(root.get("sender"), "%" + params.get("sender").toString() + "%"));
                    }
                    if (!StringUtils.isEmpty(params.get("receiver"))) {
                        expressions.add(cb.equal(root.get("receiver"), params.get("receiver").toString()));
                    }
                    if (params.get("read") != null) {
                        expressions.add(cb.equal(root.get("read"), Integer.parseInt(params.get("read").toString())));
                    }
                    expressions.add(cb.equal(root.get("recalled"), params.get("recalled")));
                    if (expressions.size() > 0) {
                        return cb.and(expressions.toArray(new Predicate[expressions.size()]));
                    }
                    return null;
                }
            }, new PageRequest(pageNo - 1, pageSize, sort));

            List<NotificationDto> notificationDtos = pageInfo.getContent().stream()
                    .map(ele -> {
                        NotificationDto dto = NotificationDto.builder()
                                .id(ele.getNotifyId())
                                .subject(ele.getSubject())
                                .body(ele.getBody())
                                .sender(ele.getSender())
                                .isRecalled(ele.isRecalled())
                                .isRead(ele.isRead())
                                .sentTime(ele.getSentTime())
                                .build();
                        return dto;
                    }).collect(Collectors.toList());

            return ResponseBuilder.page(notificationDtos, pageNo, pageInfo.getSize(), pageInfo.getTotalElements());
        } catch (Exception e) {
            log.error("", e);
            return (PageResponse) ResponseBuilder.fail(e);
        }
    }
}
