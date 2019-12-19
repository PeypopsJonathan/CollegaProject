package ucll.project.ui.controller;

import ucll.project.db.ConnectionPool;
import ucll.project.domain.star.Star;
import ucll.project.domain.star.StarRepository;
import ucll.project.domain.star.StarRepositoryDb;
import ucll.project.domain.user.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

public class Profile extends RequestHandler {
    private UserRepository userRep = new UserRepositoryDb();
    private StarRepository starRep = new StarRepositoryDb();

    public Profile(String command, UserService userService) {

        super(command, userService);
    }

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        //Session opalen en user bepalen

        request.setAttribute("users", this.getUserService().getUsers());

        UserRepository userRep = new UserRepositoryDb();
        int id = (int)request.getSession().getAttribute("user");
        User currentUser = userRep.get(id);
        request.setAttribute("userId", currentUser.getUserId());
        request.setAttribute("firstname", currentUser.getFirstName().trim());
        request.setAttribute("lastname", currentUser.getLastName().trim());
        request.setAttribute("email", currentUser.getEmail().trim());
        request.setAttribute("availableStars",userRep.getAvailableStars((int)request.getSession().getAttribute("user")));
        getUserStars(request, response, currentUser.getUserId());

        return "profile.jsp";
    }

    private void getUserStars(HttpServletRequest request, HttpServletResponse response, int userId) {
        List<Star> localStars = starRep.getUserInvolvedInStarExchanges(userId);
        for (Star star : localStars) {
            star.setReceiver_name(userRep.get(star.getReceiver_id()).getFirstName() + " " + userRep.get(star.getReceiver_id()).getLastName());
            star.setSender_name(userRep.get(star.getSender_id()).getFirstName() + " " + userRep.get(star.getSender_id()).getLastName());
        }
        sortStars(localStars);
        request.setAttribute("stars", localStars);
    }

    private void sortStars(List<Star> unsortedStars) {
        Collections.sort(unsortedStars);

    }
}
