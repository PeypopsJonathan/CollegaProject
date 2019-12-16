package ucll.project.ui.controller;

import ucll.project.domain.user.UserRepository;
import ucll.project.domain.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Users extends RequestHandler {
    public Users(String command, UserService userService){
        super(command, userService);
    }

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("users", this.getUserService().getUsers());
        return "users.jsp";
    }
}
