package no.hvl.dat109.Uno.api;

import no.hvl.dat109.Uno.service.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

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
    public Boolean logIn( @RequestBody() String data ) {

        //TODO Her må det implementeres logikk for å sjekke om bruker med brukernavn og passord finnes i databasen
        System.out.println(data);

        if(data.length() > 29){
            // om bruker eksisterer returneres true. Bruker må også settes til innlogget på backendsiden.
            // må ha en metode som kan kalles fra frontend for å sjekke om en gitt bruker er logget inn.
            return true;
        }else{

            return false;
        }



    }

}