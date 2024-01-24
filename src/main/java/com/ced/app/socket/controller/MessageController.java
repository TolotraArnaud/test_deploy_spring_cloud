package com.ced.app.socket.controller;

import com.ced.app.model.Message;
import com.ced.app.model.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.Map;

@Controller
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ResponseMessage getMessage(@RequestBody Map<String, String> data) throws InterruptedException{
        Thread.sleep(1000);
        return new ResponseMessage(HtmlUtils.htmlEscape(data.get("messageContent")));
    }

    @MessageMapping("/private-message")
    @SendToUser("/topic/private-messages")
    public ResponseMessage getPrivateMessage(@RequestBody Map<String, String> data, final Principal principal) throws InterruptedException{
        Thread.sleep(1000);
        return new ResponseMessage(HtmlUtils.htmlEscape(
                    "Sending private message to user "+principal.getName()+" : "+data.get("messageContent")));
    }
}
