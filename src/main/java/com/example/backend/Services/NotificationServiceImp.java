package com.example.backend.Services;

import com.example.backend.Entity.Notification;
import com.example.backend.Entity.Status;
import com.example.backend.Repository.NotifictionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImp implements NotificationService {

    private NotifictionRepository notifictionRepository;

    @Override
    public List<Notification> getNotificationByStatus(Status status) {
        return notifictionRepository.getNotificationByStatus(status);
    }

    @Override
    public List<Notification> findAll() { return notifictionRepository.findAll();}

}
