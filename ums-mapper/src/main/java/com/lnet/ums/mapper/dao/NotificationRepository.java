package com.lnet.ums.mapper.dao;

import com.lnet.model.ums.notification.notificationEntity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findBySender(String sender);

    Notification getByNotifyId(String notifyId);

    //@Query("select n from Notification n where n.subject like ?1 and n.sender like ?2")
    Page<Notification> findAll(Specification specification, Pageable pageable);
}
