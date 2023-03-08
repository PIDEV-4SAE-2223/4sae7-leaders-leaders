package com.example.backend.Controller;

import com.example.backend.Entity.Notification;
import com.example.backend.Entity.Status;
import com.example.backend.Services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/notification")
@RestController
@AllArgsConstructor
public class NotificationController {

    private NotificationService notificationService;
    @GetMapping("/getnotification")
    public List<Notification> getAllNotification() {
        return notificationService.findAll();
    }

    @GetMapping("/notifications/{status}")
    public List<Notification> getNoficationByStatus(@PathVariable("status") Status status) {
        return notificationService.getNotificationByStatus(status);    }
}
