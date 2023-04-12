package no.hvl.dat109.Uno.api;

import no.hvl.dat109.Uno.service.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final PersistenceService persistenceService;

    @Autowired
    public LoginController(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public String getRegisterView( @RequestBody() String data ) {

        System.out.println(data);

        return "Hello World";
    }

}