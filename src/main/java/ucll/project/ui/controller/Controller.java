package ucll.project.ui.controller;

import ucll.project.domain.user.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
    private UserService service;
    private HandlerFactory handlerFactory;


    @Override
    public void init() throws ServletException {
        super.init();
        handlerFactory = new HandlerFactory();
        service = new UserService();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String command = request.getParameter("command");
            int id = 0;
            try {
                id = (int)request.getSession().getAttribute("user");
            } catch (Exception e){ }
            UserService userService = new UserService();
            boolean isManager = userService.getManagerStatusUser(id);
            request.setAttribute("isManager", isManager);
            if (command == null || command.trim().isEmpty() || id == 0) {
                if (!(command != null && command.equals("Verification"))) {
                    if (id == 0) command = "Login";
                    else {
                        command = "Index";
                    }
                }
            }
            RequestHandler handler = handlerFactory.getHandler(command, service);
            String destination = handler.handleRequest(request, response);
            handler.forwardRequest(destination, request, response);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }
}

