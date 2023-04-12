package no.hvl.dat109.Uno.api;

import no.hvl.dat109.Uno.api.dto.ErrorResponse;
import no.hvl.dat109.Uno.api.dto.GameResponse;
import no.hvl.dat109.Uno.api.dto.ListOfGamesResponse;
import no.hvl.dat109.Uno.api.dto.LobbyEventResponse;
import no.hvl.dat109.Uno.enums.LobbyEvent;
import no.hvl.dat109.Uno.persistence.entity.Game;
import no.hvl.dat109.Uno.service.GameService;
import no.hvl.dat109.Uno.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

import static no.hvl.dat109.Uno.enums.LobbyEvent.*;

@Controller
public class GameLobbyController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;
    private final MappingService mappingService;
    private final String MESSAGE_CHANNEL = "/topic/lobby";

    @Autowired
    public GameLobbyController(SimpMessagingTemplate messagingTemplate, GameService gameService, MappingService mappingService) {
        this.messagingTemplate = messagingTemplate;
        this.gameService = gameService;
        this.mappingService = mappingService;
    }

    @MessageMapping("/lobby")
    public void lobby(SimpMessageHeaderAccessor accessor) {
        Principal user = getUserPrincipal(accessor);
        ListOfGamesResponse response = mappingService.map(gameService.getAllNotStartedGames());
        messagingTemplate.convertAndSendToUser(user.getName(), MESSAGE_CHANNEL, response);
    }

    @MessageMapping("/lobby/game/create")
    public void createGame(SimpMessageHeaderAccessor accessor) {
        Principal user = getUserPrincipal(accessor);
        Game game = gameService.createGame(user.getName());
        if(game == null) {
            ErrorResponse errorResponse = new ErrorResponse("You can not create a new game. You may be in another game!");
            messagingTemplate.convertAndSendToUser(user.getName(), MESSAGE_CHANNEL, errorResponse);
            return;
        }
        LobbyEventResponse response = new LobbyEventResponse(game.getUuid(), user.getName(), CREATE_GAME_EVENT);
        messagingTemplate.convertAndSend(MESSAGE_CHANNEL, response);
    }

    @MessageMapping("/lobby/game/leave")
    public void leaveGame(SimpMessageHeaderAccessor accessor) {
        Principal user = getUserPrincipal(accessor);
        Game game = gameService.leaveNotStartedGame(user.getName());
        if(game == null) {
            ErrorResponse response = new ErrorResponse("The game you want to leave does not exist or is not present in lobby!");
            messagingTemplate.convertAndSendToUser(user.getName(), MESSAGE_CHANNEL, response);
            return;
        }
        LobbyEventResponse response = new LobbyEventResponse(game.getUuid(), user.getName(), LEAVE_GAME_EVENT);
        messagingTemplate.convertAndSend(MESSAGE_CHANNEL, response);
    }

    @MessageMapping("/lobby/game/join/{gameId}")
    public void joinGame(SimpMessageHeaderAccessor accessor, @DestinationVariable String gameId) {
        Principal user = getUserPrincipal(accessor);
        Game game = gameService.joinGame(user.getName(), gameId);
        if(game == null) {
            ErrorResponse response = new ErrorResponse("The game you are about to join may be full or not existing!");
            messagingTemplate.convertAndSendToUser(user.getName(), MESSAGE_CHANNEL, response);
            return;
        }
        LobbyEventResponse response = new LobbyEventResponse(game.getUuid(), user.getName(), JOIN_GAME_EVENT);
        messagingTemplate.convertAndSend(MESSAGE_CHANNEL, response);
    }

    @MessageMapping("/lobby/game/start/{gameId}")
    public void startGame(SimpMessageHeaderAccessor accessor) {
        Principal user = getUserPrincipal(accessor);
        Game game = gameService.startGame(user.getName());
        if(game == null) {
            ErrorResponse response = new ErrorResponse("Not able to start the game. The game may not exist, " +
                    "you may not have joined the game, maybe not enough players");
            messagingTemplate.convertAndSendToUser(user.getName(), MESSAGE_CHANNEL, response);
            return;
        }
        LobbyEventResponse response = new LobbyEventResponse(game.getUuid(), user.getName(), START_GAME_EVENT);
        messagingTemplate.convertAndSend(MESSAGE_CHANNEL, response);
    }

    private Principal getUserPrincipal(SimpMessageHeaderAccessor accessor) {
        Principal user = accessor.getUser();
        if(user == null) {
            throw new IllegalArgumentException("accessor.getUser() can not be null!");
        }
        return user;
    }

//    @MessageMapping("/join")
//    public void joinUno(SimpMessageHeaderAccessor accessor, @Payload JoinLobbyRequest request) {
//        Principal user = accessor.getUser();
//        if(user == null) {
//            throw new IllegalArgumentException("accessor.getUser() can not be null!");
//        }
//
//        String userAnswer = "Hei " + user.getName() + "! I will broadcast your message to everyone!";
//        String broadcastAnswer = user.getName() + " says: " + request.input();
//
//        messagingTemplate.convertAndSendToUser(user.getName(), "/topic/reply", new JoinLobbyResponse(userAnswer));
//        messagingTemplate.convertAndSend("/topic/foo", new JoinLobbyResponse(broadcastAnswer));
//    }

}
