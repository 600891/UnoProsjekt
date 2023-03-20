package no.hvl.dat109.Uno.api;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class GameController {

    private final SimpMessagingTemplate messagingTemplate;

    public GameController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/join")
    @SendToUser("/topic/reply")
    public JoinResponse joinUno(@Header("simpSessionId") String sessionId, JoinRequest request) {
        String uuid = UUID.randomUUID().toString();
        String answer = "Hei " + request.name() + "! Your user uuid is: " + uuid;

        // todo: write interceptor for session id to get this to work
//        messagingTemplate.convertAndSendToUser(sessionId, "/topic/reply", answer);

        messagingTemplate.convertAndSend("/topic/foo", new JoinResponse(request.name() + " wants to play Uno!"));
        return new JoinResponse(answer);
    }

//    @MessageMapping("/foo")
//    @SendTo("/stream/foo")
//    public FooResponse foo(FooRequest foo) {
//        return new FooResponse("Hello to " + foo.name());
//    }

}
