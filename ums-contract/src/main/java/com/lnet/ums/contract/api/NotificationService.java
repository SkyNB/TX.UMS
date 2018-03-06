package com.lnet.ums.contract.api;

import com.lnet.framework.core.PageResponse;
import com.lnet.framework.core.Response;
import com.lnet.model.ums.notification.notificationDto.NotificationDto;

import java.util.List;
import java.util.Map;

/**
 * Created by simin on 2016-08-02.
 */
public interface NotificationService {

    Response<String> send(String subject, String body, String sender, String receiver);

    Response<String> send(String subject, String body, String sender, List<String> receivers);

    Response recall(String notifyId);

    Response<List<NotificationDto>> getNotificationsForReader(String reader, int count);

    Response<NotificationDto> get(String notifyId);

    Response read(String notifyId, String receiver);

    PageResponse<NotificationDto> pageList(Integer pageNo, Integer pageSize, Map<String, Object> params);

    PageResponse<NotificationDto> queryForReceive(Integer pageNo, Integer pageSize, Map<String, Object> params);
}
