package no.hvl.dat109.Uno.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import no.hvl.dat109.Uno.persistence.entity.User;
import no.hvl.dat109.Uno.service.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final PersistenceService persistenceService;
    @Value("${}") private String INVALID_USERNAME_MESSAGE;
    @Value("${}") private String LOGIN_URL;

    @Autowired
    PersistenceService db;

    @Autowired
    public LoginController(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public boolean logIn(@RequestParam String username, @RequestParam String pword, HttpServletRequest request, RedirectAttributes ra) {

        if (!username.equals("") && !pword.equals("")) { // Empty username or password
            ra.addFlashAttribute("redirectMessage", INVALID_USERNAME_MESSAGE);
            return false;
        }

        //Conntecting to databse to check if info is correct
        User user = persistenceService.findUserByUsername(username);

        if (user == null) {
            // User not found in database
            return false;
        }

        String savedHash = user.getPasswordHash();
        String newHash = user.getPasswordHash();
        //String newHash = RegistrationUtil.hashPassword(pword);


        //Checking if password is correct
        return savedHash.equals(newHash);



    }


}