package com.ced.app.socket.service;

import com.ced.app.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyFrontend(String id, final String message){
        System.out.println("sending message to `"+id+"`: '"+message+"'");
        ResponseMessage response = new ResponseMessage(message);
        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", response);
    }
}
