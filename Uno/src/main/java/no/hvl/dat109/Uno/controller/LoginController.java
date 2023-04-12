package no.hvl.dat109.Uno.controller;

import jakarta.servlet.http.HttpServletRequest;
import no.hvl.dat109.Uno.persistence.entity.Player;
import no.hvl.dat109.Uno.service.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Value("${}") private String INVALID_USERNAME_MESSAGE;
    @Value("${}") private String LOGIN_URL;

    @Autowired
    PersistenceService db;

    @GetMapping
    public boolean getRegisterView(){

        return true;
    }

    @PostMapping
    public String logIn(@RequestParam String username, @RequestParam String pword, HttpServletRequest request, RedirectAttributes ra) {

        if(username.equals("") || pword.equals("")) {
            ra.addFlashAttribute("redirectMessage", INVALID_USERNAME_MESSAGE);
        }

        Player user = db.findPlayerByUsername(username);

        if(user == null) {
            ra.addFlashAttribute("redirectMessage", INVALID_USERNAME_MESSAGE);
            return "redirect:" + LOGIN_URL;
        }


        return "redirect:" + LOGIN_URL;
    }


}