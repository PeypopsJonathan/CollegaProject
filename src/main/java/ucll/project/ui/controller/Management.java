package ucll.project.ui.controller;

import ucll.project.domain.user.Tags;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserService;
import ucll.project.ui.controller.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class Management extends RequestHandler {
    public Management(String command, UserService userService) {
        super(command, userService);
    }

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int id = (Integer) session.getAttribute("user");
        setTagAttribute(request);
        request.setAttribute("listName", getUserService().getAllNames());
        if (request.getParameter("IsForm") == null) {
            boolean isManager = this.getUserService().getManagerStatusUser(id);
            if (isManager) return "management.jsp";
            else return "Controller?command=Index";
        }
        else{
            return "Controller?command=" + request.getParameter("action");
        }
    }

    public void setTagAttribute(HttpServletRequest request) {
        ArrayList<String> tempTags = new ArrayList<>();

        for (int i = 0; i < Tags.values().length; i++) {
            tempTags.add(Tags.values()[i].getTag());
        }

        request.setAttribute("tags", tempTags);
    }
}