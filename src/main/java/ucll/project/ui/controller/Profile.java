package ucll.project.ui.controller;

import ucll.project.db.ConnectionPool;
import ucll.project.domain.user.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Profile extends RequestHandler {
    public Profile(String command, UserService userService) {
        super(command, userService);
    }

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        //Session opalen en user bepalen
        UserRepository userRep = new UserRepositoryDb();
        int id = (int)request.getSession().getAttribute("user");
        User currentUser = userRep.get(id);
        request.setAttribute("firstname", currentUser.getFirstName().trim());
        request.setAttribute("lastname", currentUser.getLastName().trim());
        request.setAttribute("email", currentUser.getEmail().trim());


        return "profile.jsp";
    }
}
