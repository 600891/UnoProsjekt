package no.hvl.dat109.Uno.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import no.hvl.dat109.Uno.persistence.entity.User;
import no.hvl.dat109.Uno.service.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginUtil {

    @Autowired static PersistenceService persistenceService;


    public static String hashPassword(String password) {

        return password;
    }

    public static void logoutUser(HttpSession session) {
        session.invalidate();
    }

    public static void loginUser(HttpServletRequest request, User user) {

        logoutUser(request.getSession());

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(10000);
        session.setAttribute("user", user);
    }

    public static boolean isUserLoggedIn(HttpSession session) {
        return session != null
                && session.getAttribute("person") != null;
    }

    //Sjekker om deltageren ligger i databasen
    public static boolean isAttendee(HttpSession session) {

        User attendee = (User) session.getAttribute("person");

        if (attendee == null) {
            return false;
        }
        return persistenceService.isUser(attendee);
    }

    public static boolean isAutorised(HttpSession session) {
        return LoginUtil.isUserLoggedIn(session) || LoginUtil.isAttendee(session);
    }
}
