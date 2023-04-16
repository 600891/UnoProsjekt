package no.hvl.dat109.Uno.api;

import no.hvl.dat109.Uno.api.dto.*;
import no.hvl.dat109.Uno.service.GameService;
import no.hvl.dat109.Uno.service.MappingService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import no.hvl.dat109.Uno.persistence.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import static no.hvl.dat109.Uno.enums.GameEvent.*;

@Controller
public class GameRoomController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;
    private final MappingService mappingService;
    private final String MESSAGE_CHANNEL = "/topic/gameroom/";

    @Autowired
    public GameRoomController(SimpMessagingTemplate messagingTemplate, GameService gameService, MappingService mappingService) {
        this.messagingTemplate = messagingTemplate;
        this.gameService = gameService;
        this.mappingService = mappingService;
    }
    
    @MessageMapping("/gameroom/{gameid}")
    public void gameState(SimpMessageHeaderAccessor accessor, @DestinationVariable String gameid, @RequestBody String payload) {
        Principal user = getUserPrincipal(accessor);
        System.out.println("Message received in gameState.");
        System.out.println(payload);
        //  GameStateResponse response = mappingService.mapGameState(gameService.findGame(user.getName()));
        //  String jsonGameState = (String) message.getPayload();
        //  ObjectMapper objectMapper = new ObjectMapper();

        //  try {
        //      Game game = objectMapper.readValue(jsonGameState, Game.class);
        //  } catch (JsonProcessingException e) {
        //      throw new RuntimeException(e);
        //  }
        messagingTemplate.convertAndSend(MESSAGE_CHANNEL, "Message received");
     }

    @MessageMapping("/gameroom/{gameid}/start")
    public void gameStart(SimpMessageHeaderAccessor accessor, @DestinationVariable String gameid) {
        Principal user = getUserPrincipal(accessor);
        System.out.println("Hi from gameStart, " + user.getName());
        GameStateResponse gameState = mappingService.mapGameState(gameService.findStartedGameById(gameid));
        GameEventResponse response = new GameEventResponse(gameState, START_GAME_EVENT);
        messagingTemplate.convertAndSendToUser(user.getName(), MESSAGE_CHANNEL + gameid, response);
    }


    private Principal getUserPrincipal(SimpMessageHeaderAccessor accessor) {
        Principal user = accessor.getUser();
        if(user == null) {
            throw new IllegalArgumentException("accessor.getUser() can not be null!");
        }
        return user;
    }
}
