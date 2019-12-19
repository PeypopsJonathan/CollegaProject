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
import java.util.ArrayList;
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
        int total = countGivenStars(request, response, currentUser.getUserId());
        total += countReceivedStars(request, response, currentUser.getUserId());
        request.setAttribute("totalStars", total);
        request.setAttribute("listName", getUserService().getAllNames());
        setTagAttribute(request);

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

    private int countGivenStars(HttpServletRequest request, HttpServletResponse response, int userId){
        int givenStars = starRep.countGivenStars(userId);
        request.setAttribute("givenStars", givenStars);
        return givenStars;
    }

    private int countReceivedStars(HttpServletRequest request, HttpServletResponse response, int userId){
        int receivedStars = starRep.countReceivedStars(userId);
        request.setAttribute("receivedStars", receivedStars);
        return receivedStars;
    }

    public void setTagAttribute(HttpServletRequest request) {
        ArrayList<String> tempTags = new ArrayList<>();

        for (int i = 0; i < Tags.values().length; i++) {
            tempTags.add(Tags.values()[i].getTag());
        }

        request.setAttribute("tags", tempTags);
    }
}
