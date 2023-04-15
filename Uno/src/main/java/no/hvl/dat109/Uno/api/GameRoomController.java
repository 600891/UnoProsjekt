package no.hvl.dat109.Uno.api;

import no.hvl.dat109.Uno.api.dto.*;
import no.hvl.dat109.Uno.service.GameService;
import no.hvl.dat109.Uno.service.MappingService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import no.hvl.dat109.Uno.enums.LobbyEvent;
import no.hvl.dat109.Uno.persistence.entity.Game;
import no.hvl.dat109.Uno.service.GameService;
import no.hvl.dat109.Uno.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.WebSocketMessage;

import java.security.Principal;

@Controller
public class GameRoomController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;
    private final MappingService mappingService;
    private final String MESSAGE_CHANNEL = "/topic/gameroom/{gameid}";

    @Autowired
    public GameRoomController(SimpMessagingTemplate messagingTemplate, GameService gameService, MappingService mappingService) {
        this.messagingTemplate = messagingTemplate;
        this.gameService = gameService;
        this.mappingService = mappingService;
    }

    @MessageMapping("/gamroom/{gameid}/start")
    public void gameStart(SimpMessageHeaderAccessor accessor) {
        Principal user = getUserPrincipal(accessor);
        GameStateResponse response = mappingService.mapGameState(gameService.findGame(user.getName()));
        messagingTemplate.convertAndSendToUser(user.getName(), MESSAGE_CHANNEL, response);
    }

    @MessageMapping("/gamroom/{gameid}")
    public void gameState(SimpMessageHeaderAccessor accessor, WebSocketMessage message) {
        Principal user = getUserPrincipal(accessor);
        GameStateResponse response = mappingService.mapGameState(gameService.findGame(user.getName()));





        messagingTemplate.convertAndSend(MESSAGE_CHANNEL, message.getPayload());
    }

    private Principal getUserPrincipal(SimpMessageHeaderAccessor accessor) {
        Principal user = accessor.getUser();
        if(user == null) {
            throw new IllegalArgumentException("accessor.getUser() can not be null!");
        }
        return user;
    }
}
