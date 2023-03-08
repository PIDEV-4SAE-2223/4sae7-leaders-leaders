package com.example.backend.Repository;

import com.example.backend.Entity.Notification;
import com.example.backend.Entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotifictionRepository extends JpaRepository<Notification,Long> {
    public List<Notification> getNotificationByStatus(Status status);

}
