package com.dev.mcc_tools.respositories;

import com.dev.mcc_tools.domain.Notification;
import com.dev.mcc_tools.domain.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {
    Iterable<Notification> findAll();
    Notification findById(int id);
    Iterable<Notification> findNotificationByProfileID(int profileID);
}