package com.lnet.ums.mapper.dao;

import com.lnet.model.ums.notification.notificationEntity.NotificationReceiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationReceiverRepository extends JpaRepository<NotificationReceiver, String> {

    NotificationReceiver findByNotifyIdAndReceiver(String notifyId, String receiver);

    List<NotificationReceiver> findByNotifyId(String notifyId);
}
