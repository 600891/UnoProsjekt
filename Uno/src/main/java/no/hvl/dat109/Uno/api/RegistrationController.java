package no.hvl.dat109.Uno.api;

import jakarta.servlet.http.HttpServletRequest;
import no.hvl.dat109.Uno.service.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Service class.
 * @author Oda Bastesen Storebo
 */

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    @Value("/registration") private String REGISTRATION_URL;
    @Value("/registrationConfirmation") private String CONFORMATION_URL;
    @Value("Username is already in use!") private String USERNAME_ALREADY_USED_MESSAGE;
    @Value("${app.message.invalidRegistration}") private String INVALID_REGISTRATION_MESSAGE;

    @Autowired
    PersistenceService db;

    @GetMapping
    public String getRegistrationView(){
        return "registrationView";
    }

    @PostMapping
    public String registerUser(@RequestParam("user") String user,
                               @RequestParam("name") String name,
                               @RequestParam("pword") String pword,
                               HttpServletRequest request,
                               RedirectAttributes ra) {
        //checks if user is already in database
        if (db.findPlayerByUsername(user) != null) {
            ra.addFlashAttribute("redirectMessage", USERNAME_ALREADY_USED_MESSAGE);
            return "redirect:" + REGISTRATION_URL;}


            //checks if required information exists
            if (user == null || name == null || pword == null) {
                ra.addFlashAttribute("redirecetMessage", INVALID_REGISTRATION_MESSAGE);
                return "redirect:" + REGISTRATION_URL;
            }


            //sjekk om noe e null, viss, registrer på nytt
            //sjekk om det har verdi, viss ja bra
            //sjekk om passord er likt


        return "";
    }
}
