package no.hvl.dat109.Uno.api;

import no.hvl.dat109.Uno.api.dto.ErrorResponse;
import no.hvl.dat109.Uno.api.dto.GameResponse;
import no.hvl.dat109.Uno.api.dto.ListOfGamesResponse;
import no.hvl.dat109.Uno.persistence.entity.Game;
import no.hvl.dat109.Uno.service.GameService;
import no.hvl.dat109.Uno.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class GameLobbyController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;
    private final MappingService mappingService;

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
        messagingTemplate.convertAndSendToUser(user.getName(), "/topic/lobby", response);
    }

    @MessageMapping("/lobby/game/create")
    public void createGame(SimpMessageHeaderAccessor accessor) {
        Principal user = getUserPrincipal(accessor);
        Game game = gameService.createGame(user.getName());
        if(game == null) {
            ErrorResponse errorResponse = new ErrorResponse("You can not create a new game. You may be in another game");
            messagingTemplate.convertAndSendToUser(user.getName(), "/topic/lobby", errorResponse);
            return;
        }
        GameResponse response = mappingService.map(game);
        System.out.println(response.toString());
        messagingTemplate.convertAndSend("/topic/lobby", response);
    }

    @MessageMapping("/lobby/game/leave")
    public void leaveGame() {
        // TODO
    }

    @MessageMapping("/lobby/game/join")
    public void joinGame() {
        // TODO
    }

    @MessageMapping("/lobby/game/start")
    public void startGame() {
        // TODO
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
