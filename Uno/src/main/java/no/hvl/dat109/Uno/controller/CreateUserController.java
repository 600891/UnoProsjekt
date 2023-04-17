// package no.hvl.dat109.Uno.controller;

// import jakarta.servlet.http.HttpServletRequest;
// import no.hvl.dat109.Uno.persistence.entity.Player;
// import no.hvl.dat109.Uno.persistence.entity.User;
// import no.hvl.dat109.Uno.service.PersistenceService;
// import no.hvl.dat109.Uno.utils.CreateUserUtil;
// import no.hvl.dat109.Uno.utils.LoginUtil;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// @RestController
// @RequestMapping("/createuser")
// public class CreateUserController {
//     @Value("/createuser")
//     private String CREATREUSER_URL;
//     @Value("/createuserconfirmation")
//     private String CONFORMATION_URL;
//     @Value("Username is already in use!")
//     private String USERNAME_ALREADY_USED_MESSAGE;
//     @Value("${app.message.invalidUser}")
//     private String INVALID_REGISTRATION_MESSAGE;
//     @Value("${app.message.unequalPassword}")
//     private String UNEQUAL_PASSWORD_MESSAGE;

//     @Autowired
//     PersistenceService db;

//     @PostMapping
//     public String registerUser(@RequestParam("player_id") Long player_id,
//                                @RequestParam("username") String username,
//                                @RequestParam("passwordhash") String passwordhash,
//                                @RequestParam("name") String name,
//                                @RequestParam("email") String email,
//                                @RequestParam("passwordsalt") byte[] passwordsalt,
//                                @RequestParam("pword") String pword,
//                                @RequestParam("pwordRep") String pwordRep,
//                                HttpServletRequest request,
//                                RedirectAttributes ra) {
//         //checks if user is already in database
//         if (db.findUserByUsername(username) != null) {
//             ra.addFlashAttribute("redirectMessage", USERNAME_ALREADY_USED_MESSAGE);
//             return "redirect:" + CREATREUSER_URL;
//         }

//         //checks if required information exists
//         if (username == null || email == null || pword == null || pwordRep == null) {
//             ra.addFlashAttribute("redirecetMessage", INVALID_REGISTRATION_MESSAGE);
//             return "redirect:" + CREATREUSER_URL;
//         }

//         //checks if password is equal
//         if (!pwordRep.equals(pword)) {
//             ra.addFlashAttribute("redirectMessage", UNEQUAL_PASSWORD_MESSAGE);
//             return "redirect:" + CREATREUSER_URL;
//         }

//         //Hash og salt password, adds to database
//         byte[] salt = CreateUserUtil.getSalt();
//         String hash = CreateUserUtil.hashPassword(pword, salt);

//         User user = new User(player_id, username, passwordhash, name, email, passwordsalt);
//         db.createUser(user);
//         LoginUtil.loginUser(request, user);
//         return "redirect:" + CONFORMATION_URL;

//     }
// }
