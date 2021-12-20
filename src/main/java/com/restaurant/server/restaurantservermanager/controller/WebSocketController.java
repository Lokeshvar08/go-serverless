package com.restaurant.server.restaurantservermanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller("/socket")
public class WebSocketController {

    @Autowired private SimpMessagingTemplate messagingTemplate;

    public SimpMessagingTemplate getMessagingTemplate() {
        return messagingTemplate;
    }

    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    public void interaction(@Payload Message message) {
            if (message.getMessage().equals("kitchen")) {
                messagingTemplate.convertAndSendToUser(
                        message.getDine().toString(),
                        "/queue/messages",
                        message
                );
                messagingTemplate.convertAndSendToUser(
                        message.getRestaurant().toString(),
                        "/queue/messages",
                        message
                );
            } else {
                messagingTemplate.convertAndSendToUser(
                        message.getRestaurant().toString(),
                        "/queue/messages",
                        message
                );
            }

    }

    @MessageMapping("/chat/to-din")
    public void toDine(@Payload Message message) {
        System.out.println("hello from toDine()");
        messagingTemplate.convertAndSendToUser(
                message.getDine().toString(),
                "/queue/messages",
                message
        );
    }

    @MessageMapping("/chat/to-kitchen")
    public void toKitchen(@Payload Message message) {
        System.out.println("hello from toKitchen()");
        messagingTemplate.convertAndSendToUser(
                message.getRestaurant().toString(),
                "/queue/messages",
                message
        );
    }
}
