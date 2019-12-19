package ucll.project.ui.controller;

import ucll.project.domain.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUser extends RequestHandler {

    public AddUser(String command, UserService userService) {
        super(command, userService);
    }

    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        return "addUser.jsp";
    }
}
