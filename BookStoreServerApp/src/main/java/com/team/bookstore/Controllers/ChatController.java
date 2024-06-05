package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.MessageRequest;
import com.team.bookstore.Dtos.Responses.MessageResponse;
import com.team.bookstore.Entities.Message;
import com.team.bookstore.Mappers.MessageMapper;
import com.team.bookstore.Services.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
public class ChatController {
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    MessageService messageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @SecurityRequirement(name = "bearerAuth")
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(MessageRequest messageRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                log.info("!!!!!!!!!!!!!!!!!!!!!NULL");
            }
            Message         message     = messageMapper.toMessage(messageRequest);
            MessageResponse response    = messageService.createMessage(message);
            String          destination = "/queue/" + message.getReceiver().getUsername();
            messagingTemplate.convertAndSend(destination, response);
        } catch (Exception e) {
            log.info(e);
            throw e;
        }
    }

    @MessageMapping("/chat.addUser")
    public void addUser(MessageRequest messageRequest) {
        String destination = "/topic/public";
        messagingTemplate.convertAndSend(destination,
                messageMapper.toMessage(messageRequest));
    }

}
