package no.hvl.dat109.Uno.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import no.hvl.dat109.Uno.persistence.entity.User;
import no.hvl.dat109.Uno.service.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginUtil {
    @Autowired static PersistenceService persistenceService;

    public static void logoutUser(HttpSession session) {session.invalidate();}
    public static void loginUser(HttpServletRequest request, User user){

        logoutUser(request.getSession());

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }
}
