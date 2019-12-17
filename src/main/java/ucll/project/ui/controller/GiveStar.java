package ucll.project.ui.controller;


import ucll.project.domain.DomainException;
import ucll.project.domain.star.Star;
import ucll.project.domain.user.Tags;
import ucll.project.domain.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class GiveStar extends RequestHandler {

    public GiveStar(String command, UserService userService) {
        super(command, userService);
    }


    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {

        Star star = new Star();

        ArrayList<String> errorList = new ArrayList<>();

        receiverValidator(star, request, errorList);
        tagsValidator(star, request, errorList);
        descriptionValidator(star, request, errorList);

        star.setSender_id(1);
        star.setStar_id(0);

        if (errorList.isEmpty()) {
            return "users.jsp";
        } else {
            request.setAttribute("errors", errorList);
            Index.setTagAttribute(request);
            return "index.jsp";
        }
    }

    private void descriptionValidator(Star star, HttpServletRequest request, ArrayList<String> errorList) {
        try {
            String description = request.getParameter("description");
            if (!description.trim().isEmpty()) {
                request.setAttribute("previous_input_description", description);
                star.setComment(description);
            } else {
                errorList.add("Empty description big no no");
            }
        } catch (DomainException e) {
            errorList.add("Empty description not allowed");
        }
    }

    private void receiverValidator(Star star, HttpServletRequest request, ArrayList<String> errorList) {
        try {
            String receiver_id = request.getParameter("receiver");

            if (!receiver_id.trim().isEmpty()) {
                request.setAttribute("previous_input_receiver", receiver_id);
                int receiver = Integer.parseInt(receiver_id);
                if (this.getUserService().getUserById(receiver) == null) {
                    errorList.add("User dosn't exist");
                } else {
                    star.setReceiver_id(receiver);
                }
            } else {
                errorList.add("Empty receiver id");
            }
        } catch (NumberFormatException e) {
            errorList.add("Please enter a number");
        } catch (DomainException e) {
            errorList.add("Please enter a correct receiver id");
        }
    }

    public void tagsValidator(Star star, HttpServletRequest request, ArrayList<String> errorList) {
        try {

            ArrayList<String> tagList = new ArrayList<>();

            String tag1 = request.getParameter("0");
            String tag2 = request.getParameter("1");
            String tag3 = request.getParameter("2");
            String tag4 = request.getParameter("3");
            if (tag1 != null || !tag1.trim().isEmpty()) {
                tagList.add(tag1);
            }
            if (tag2 != null || !tag2.trim().isEmpty()) {
                tagList.add(tag2);
            }
            if (tag3 != null || !tag3.trim().isEmpty()) {
                tagList.add(tag3);
            }
            if (tag4 != null || !tag4.trim().isEmpty()) {
                tagList.add(tag4);
            }

            if (tagList.isEmpty()) {
                errorList.add("Tags can't be empty");
            } else {
                star.setTags(tagList);
            }

        } catch (DomainException e) {
            errorList.add("Incorrect tag");
        }
    }
}
