package com.example.backend.Services;

import com.example.backend.Entity.Notification;
import com.example.backend.Entity.Status;

import java.util.List;

public interface NotificationService {

    public List<Notification> getNotificationByStatus(Status status);
    public List<Notification> findAll();

}
