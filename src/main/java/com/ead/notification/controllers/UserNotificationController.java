package com.ead.notification.controllers;

import com.ead.notification.dtos.NotificationDto;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    private NotificationService notificationService;

    public UserNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> getAllNotificationsByUser(
            @PathVariable(value = "userId") UUID userId,
            @PageableDefault(page = 0, size = 10 ,sort = "notificationId", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return status(OK).body(notificationService.findAllNotificationsByUser(userId, pageable));

    }

    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable(value = "userId") UUID userId,
                                                     @PathVariable(value = "notificationId") UUID notificationId,
                                                     @RequestBody @Valid NotificationDto notificationDto){
        Optional<NotificationModel> notificationModelOptional = notificationService.findByNotificationIdAndUserId(notificationId, userId);
        if(notificationModelOptional.isEmpty()){
            return ResponseEntity.status(NOT_FOUND).body("Notification not found");
        }

        notificationModelOptional.get().setNotificationStatus(notificationDto.getNotificationStatus());
        notificationService.saveNotification(notificationModelOptional.get());
        return status(OK).body(notificationModelOptional.get());
    }

}