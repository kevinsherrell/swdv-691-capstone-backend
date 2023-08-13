package com.dev.mcc_tools.services;

import com.dev.mcc_tools.domain.Notification;
import com.dev.mcc_tools.respositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Notification saveOrUpdateNotification(Notification notification) {
            return notificationRepository.save(notification);
    }

    public Iterable<Notification> findAllNotifications() {
        return notificationRepository.findAll();
    }

    //
    public Notification findNotificationByID(int id) {
        return notificationRepository.findById(id);
    }



    //
    public Iterable<Notification> findNotificationsByProfileID(int profileID) {
        return notificationRepository.findNotificationByProfileID(profileID);

    }
}