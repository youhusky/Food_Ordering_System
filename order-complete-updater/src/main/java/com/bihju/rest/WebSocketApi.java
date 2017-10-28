package com.bihju.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WebSocketApi {
    @MessageMapping("/sendMessage")
    @SendTo("/topic/orders")
    public String sendMessage(String message) throws Exception {
        log.info("Message received: " + message);
        return message;
    }
}
