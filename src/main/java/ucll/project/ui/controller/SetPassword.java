package ucll.project.ui.controller;

import ucll.project.domain.user.UserRepositoryDb;
import ucll.project.domain.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetPassword extends RequestHandler {

    public SetPassword(String command, UserService userService) {
        super(command, userService);
    }

    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");

        if (password.equals(confirm)){
            UserRepositoryDb db = new UserRepositoryDb();
            int id = (int) request.getSession().getAttribute("user");
            db.setPassword(id,password);
            return "Controller?command=Index";
        } else {
            request.setAttribute("errorClass",true);
            request.setAttribute("error","Passwords don't match");
            return "setPassword.jsp";
        }
    }
}