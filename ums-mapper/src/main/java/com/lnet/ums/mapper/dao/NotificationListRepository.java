package com.lnet.ums.mapper.dao;

import com.lnet.model.ums.notification.notificationEntity.NotificationList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationListRepository extends CrudRepository<NotificationList, String> {

    Page<NotificationList> findAll(Specification specification, Pageable pageable);

    @Query("select n from NotificationList n where (n.read=false and n.recalled = false and n.receiver=?1) order by n.sentTime desc")
    List<NotificationList> findByReceiver(String receiver);
}
