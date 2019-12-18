package ucll.project.ui.controller;

import ucll.project.db.ConnectionPool;
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
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;


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
        request.setAttribute("availableStars",userDb.getAvailableStars((int)request.getSession().getAttribute("user")));
        checkStars();

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


    private void getStars(HttpServletRequest request, HttpServletResponse response){
        List<Star> localStars = starDb.getAll();
        for (Star star : localStars) {
            star.setReceiver_name(userDb.get(star.getReceiver_id()).getFirstName() + " " + userDb.get(star.getReceiver_id()).getLastName());
            star.setSender_name(userDb.get(star.getSender_id()).getFirstName() + " " + userDb.get(star.getSender_id()).getLastName());
        }
        sortStars(localStars);
        request.setAttribute("stars", localStars);
    }

    private void checkStars(){
        boolean reassign = false;
        try (Connection conn = ConnectionPool.getConnection()){
            String sql = "select * from \"award-team9\".checkdate";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Timestamp stamp = rs.getTimestamp("date");
                LocalDate date = stamp.toLocalDateTime().toLocalDate();
                LocalDate now = LocalDate.now();
                if (now.getMonth().getValue() > date.getMonth().getValue()){
                    reassign = true;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        if (reassign){
            userDb.reassignStars();
        }
        safeDate();
    }


    private void safeDate(){
        try (Connection conn = ConnectionPool.getConnection()){
            String sql = "update \"award-team9\".checkdate\n" +
                    "set date = \tCURRENT_TIMESTAMP";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
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
            String receiver_name = request.getParameter("receiver");

            if (!receiver_name.trim().isEmpty()) {
                request.setAttribute("previous_input_receiver", receiver_name);



                // getUserService().getUserByName();


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

    private boolean isFormSubmition(HttpServletRequest request) {
        return request.getParameter("isForm") != null;
    }

    private String submitForm(HttpServletRequest request) {
        Star star = new Star();
        // TODO senderid text field and star id setting
        //star.setSender_id(1);
        //star.setStar_id(0);
        ArrayList<String> errorList = new ArrayList<>();

        receiverValidator(star, request, errorList);
        tagsValidator(star, request, errorList);
        descriptionValidator(star, request, errorList);

        if (errorList.isEmpty()) {

            //starDb.createStar(star);


            return "users.jsp"; // TODO Show success page
        } else {
            request.setAttribute("errors", errorList);
            return "index.jsp";
        }
    }
}
