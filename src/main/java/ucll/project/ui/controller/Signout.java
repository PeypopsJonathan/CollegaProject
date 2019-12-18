package ucll.project.ui.controller;

import ucll.project.domain.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Signout extends RequestHandler{

    public Signout(String command, UserService userService) {
        super(command, userService);
    }

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("user",null);
        return "login.jsp";
    }
}
