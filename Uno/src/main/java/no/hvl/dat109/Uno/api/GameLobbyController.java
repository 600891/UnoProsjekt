package no.hvl.dat109.Uno.api;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class GameLobbyController {

    private final SimpMessagingTemplate messagingTemplate;

    public GameLobbyController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/join")
    public void joinUno(SimpMessageHeaderAccessor accessor, @Payload JoinLobbyRequest request) {
        Principal user = accessor.getUser();
        if(user == null) {
            throw new IllegalArgumentException("accessor.getUser() can not be null!");
        }

        String userAnswer = "Hei " + user.getName() + "! I will broadcast your message to everyone!";
        String broadcastAnswer = user.getName() + " says: " + request.input();

        messagingTemplate.convertAndSendToUser(user.getName(), "/topic/reply", new JoinLobbyResponse(userAnswer));
        messagingTemplate.convertAndSend("/topic/foo", new JoinLobbyResponse(broadcastAnswer));
    }

}
