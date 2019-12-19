package ucll.project.ui.controller;

import ucll.project.domain.user.Tags;
import ucll.project.domain.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class AddUser extends RequestHandler {

    public AddUser(String command, UserService userService) {
        super(command, userService);
    }

    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("listName", getUserService().getAllNames());
        setTagAttribute(request);
        return "addUser.jsp";
    }

    public void setTagAttribute(HttpServletRequest request) {
        ArrayList<String> tempTags = new ArrayList<>();

        for (int i = 0; i < Tags.values().length; i++) {
            tempTags.add(Tags.values()[i].getTag());
        }

        request.setAttribute("tags", tempTags);
    }
}
