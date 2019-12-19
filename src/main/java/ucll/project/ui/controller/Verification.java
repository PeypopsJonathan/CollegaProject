package ucll.project.ui.controller;

import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.ui.controller.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Verification extends RequestHandler {
    public Verification(String command, UserService userService) {
        super(command, userService);
    }

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("login");
        String password = request.getParameter("password");
        int id = this.getUserService().verifyUserLogin(email, password);
        if (id != -1) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(-1);
            session.setAttribute("user",id);
            boolean isManager = this.getUserService().getManagerStatusUser(id);
            request.setAttribute("isManager", isManager);
            if (password.isEmpty()){
                return "setPassword.jsp";
            }
            return "Controller?command=Index";
        }
        else
            return "login.jsp";
    }
}