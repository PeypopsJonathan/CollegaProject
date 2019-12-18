package ucll.project.ui.controller;

import ucll.project.domain.DomainException;
import ucll.project.domain.user.Tags;

import ucll.project.domain.star.Star;
import ucll.project.domain.star.StarRepository;
import ucll.project.domain.star.StarRepositoryDb;
import ucll.project.domain.user.UserRepository;
import ucll.project.domain.user.UserRepositoryDb;

import ucll.project.domain.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Index extends RequestHandler {
    UserRepository userDb = new UserRepositoryDb();
    StarRepository starDb = new StarRepositoryDb();

    public Index(String command, UserService userService) {
        super(command, userService);
    }

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {

        setTagAttribute(request);
        getStars(request);
        request.setAttribute("listName", getUserService().getAllNames());
        if (isFormSubmition(request)) {
            return submitForm(request);
        } else {
            return "index.jsp";
        }
    }

    public void setTagAttribute(HttpServletRequest request) {
        ArrayList<String> tempTags = new ArrayList<>();

        for (int i = 0; i < Tags.values().length; i++) {
            tempTags.add(Tags.values()[i].getTag());
        }

        request.setAttribute("tags", tempTags);
    }

    private void getStars(HttpServletRequest request) {
        List<Star> localStars = starDb.getAll();
        for (Star star : localStars) {
            star.setReceiver_name(userDb.get(star.getReceiver_id()).getFirstName() + " " + userDb.get(star.getReceiver_id()).getLastName());
            star.setSender_name(userDb.get(star.getSender_id()).getFirstName() + " " + userDb.get(star.getSender_id()).getLastName());
        }
        sortStars(localStars);
        request.setAttribute("stars", localStars);
    }

    private void sortStars(List<Star> unsortedStars) {
        Collections.sort(unsortedStars);
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
            String receiver_name = request.getParameter("receiverName");

            if (!receiver_name.trim().isEmpty()) {
                request.setAttribute("previous_input_receiver", receiver_name);

                String[] splited = receiver_name.split("\\s+");


                star.setReceiver_id(getUserService().getUserByName(splited[0], splited[1]));


            } else {
                errorList.add("Enter name of receiver");
            }
        } catch (DomainException e) {
            errorList.add("Please enter a correct receiver id");
        }
    }

    private void tagsValidator(Star star, HttpServletRequest request, ArrayList<String> errorList) {
        try {

            ArrayList<String> tagList = new ArrayList<>();

            String tag1 = request.getParameter("0");
            String tag2 = request.getParameter("1");
            String tag3 = request.getParameter("2");
            String tag4 = request.getParameter("3");
            if (tag1 != null && !tag1.trim().isEmpty()) {
                tagList.add(tag1);
            }
            if (tag2 != null && !tag2.trim().isEmpty()) {
                tagList.add(tag2);
            }
            if (tag3 != null && !tag3.trim().isEmpty()) {
                tagList.add(tag3);
            }
            if (tag4 != null && !tag4.trim().isEmpty()) {
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

    private boolean isFormSubmition(HttpServletRequest request) {
        return request.getParameter("isForm") != null;
    }

    private String submitForm(HttpServletRequest request) {
        Star star = new Star();

        int id = (Integer) request.getSession().getAttribute("user");

        star.setSender_id(id);

        Random r = new Random();
        int low = 1;
        int high = 100000;
        int result = r.nextInt(high-low) + low;
        star.setStar_id(result);

        ArrayList<String> errorList = new ArrayList<>();

        receiverValidator(star, request, errorList);
        tagsValidator(star, request, errorList);
        descriptionValidator(star, request, errorList);

        if (errorList.isEmpty()) {

            star.setComment("Comment");
            System.out.println(star);

            starDb.createStar(star);

            return "users.jsp"; // TODO Show success page
        } else {
            request.setAttribute("errors", errorList);
            return "index.jsp";
        }
    }

}
