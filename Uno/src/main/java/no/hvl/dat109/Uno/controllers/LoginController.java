package no.hvl.dat109.Uno.controllers;

import no.hvl.dat109.Uno.models.Player;
import no.hvl.dat109.Uno.utils.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    DatabaseService db;
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public String getRegisterView(
            @RequestParam(name = "body") String body ){
        System.out.println("user accessed data");
        System.out.println(body);


        return "Username entered";
    }
}