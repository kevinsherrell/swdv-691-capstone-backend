package com.dev.mcc_tools.validation;

import com.dev.mcc_tools.domain.Notification;

import java.util.HashMap;
import java.util.Map;

public class NotificationValidator extends MccValidator{

    public HashMap<String, String> checkNotification(Notification notification){
        if(notification == null){
            setErrors("notification", "notification not found");
        }
        return this.errors;
    }
    public HashMap<String, String> checkNotification(Iterable<Notification> notifications){
        int count = 0;
        for(Notification notification: notifications){
            count++;
        }
        if(count < 1){
            setErrors("notifications", "no available notifications");
        }
        return this.errors;
    }

}