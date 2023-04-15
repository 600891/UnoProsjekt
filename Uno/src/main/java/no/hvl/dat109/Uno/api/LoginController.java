package no.hvl.dat109.Uno.api;

import jakarta.servlet.http.HttpServletRequest;
import no.hvl.dat109.Uno.persistence.entity.User;
import no.hvl.dat109.Uno.service.PersistenceService;
import no.hvl.dat109.Uno.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/login")
public class LoginController {


    @Autowired
    PersistenceService db;

    @Autowired
    public LoginController(PersistenceService persistenceService) {
        this.db = persistenceService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public boolean logIn(@RequestParam String username, @RequestParam String pword, HttpServletRequest request, RedirectAttributes ra) {

        if (!username.equals("") && !pword.equals("")) { // Empty username or password

            // Username or/and password field was emptry when posting
            return false;
        }

        //Conntecting to databse to check if info is correct
        User user = db.findUserByUsername(username);

        if (user == null) {
            // User not found in database
            return false;
        }

        String savedHash = user.getPasswordHash();
        String newHash = user.getPasswordHash();
        //String newHash = RegistrationUtil.hashPassword(pword);

        // Checking if password is correct, and loging in is so
        if(!savedHash.equals(newHash)) {

            return false;
        }
        LoginUtil.loginUser(request, user);
        return true;

    }


}