package com.dev.mcc_tools.services;

import com.dev.mcc_tools.domain.Notification;
import com.dev.mcc_tools.domain.Profile;
import com.dev.mcc_tools.domain.User;
import com.dev.mcc_tools.respositories.NotificationRepository;
import com.dev.mcc_tools.respositories.ProfileRepository;
import com.dev.mcc_tools.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Notification saveOrUpdateNotification(Notification notification) {
        try {
            return notificationRepository.save(notification);
        } catch (Exception e) {
            e.getCause();
        }
        return notification;
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