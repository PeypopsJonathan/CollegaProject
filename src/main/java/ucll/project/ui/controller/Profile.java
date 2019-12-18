package ucll.project.ui.controller;

import ucll.project.db.ConnectionPool;
import ucll.project.domain.star.Star;
import ucll.project.domain.user.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Profile extends RequestHandler {
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
        request.setAttribute("firstname", currentUser.getFirstName().trim());
        request.setAttribute("lastname", currentUser.getLastName().trim());
        request.setAttribute("email", currentUser.getEmail().trim());
        List<Star> userStars = userRep.getStar((Integer) request.getSession().getAttribute("user"));
        if (userStars != null) {
            request.setAttribute("stars", userStars.size());
        } else {
            request.setAttribute("stars", "Star list is null");
        }

        return "profile.jsp";
    }
}
