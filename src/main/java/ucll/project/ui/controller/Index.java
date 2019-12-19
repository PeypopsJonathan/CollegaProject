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
import java.sql.Timestamp;
import java.sql.*;
import java.time.LocalDate;
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
        request.setAttribute("listTag", getAllTags());
        int userId = (int) request.getSession().getAttribute("user");
        request.setAttribute("availableStars", userDb.getAvailableStars(userId));
        checkStars();

        if (isFormSubmition(request)) {
            try {
                return submitForm(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return "index.jsp";
        }
        return "index.jsp";
    }

    public List<String> getAllTags(){
        ArrayList<String> listTags = new ArrayList<>();
        String tags;

        for (int i = 0; i < Tags.values().length; i++) {
            tags = '"' + Tags.values()[i].getTag() + '"';
            listTags.add(tags);
        }
        return listTags;
    }

    public void setTagAttribute(HttpServletRequest request) {
        ArrayList<String> tempTags = new ArrayList<>();

        for (int i = 0; i < Tags.values().length; i++) {
            tempTags.add(Tags.values()[i].getTag());
        }

        request.setAttribute("tags", getAllTags());
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

    private void checkStars() {
        boolean reassign = false;
        try (Connection conn = ConnectionPool.getConnection()) {
            String sql = "select * from \"award-team9\".checkdate";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp stamp = rs.getTimestamp("date");
                LocalDate date = stamp.toLocalDateTime().toLocalDate();
                LocalDate now = LocalDate.now();
                if (now.getMonth().getValue() > date.getMonth().getValue()) {
                    reassign = true;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        if (reassign) {
            userDb.reassignStars();
        }
        safeDate();
    }


    private void safeDate() {
        try (Connection conn = ConnectionPool.getConnection()) {
            String sql = "update \"award-team9\".checkdate\n" +
                    "set date = \tCURRENT_TIMESTAMP";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException ex) {
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
            String tag5 = request.getParameter("4");
            String tag6 = request.getParameter("5");
            String tag7 = request.getParameter("6");
            String tag8 = request.getParameter("7");
            String tag9 = request.getParameter("8");
            String tag10 = request.getParameter("9");
            String tag11 = request.getParameter("10");

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
            if (tag5 != null && !tag5.trim().isEmpty()) {
                tagList.add(tag5);
            }
            if (tag6 != null && !tag6.trim().isEmpty()) {
                tagList.add(tag6);
            }
            if (tag7 != null && !tag7.trim().isEmpty()) {
                tagList.add(tag7);
            }
            if (tag8 != null && !tag8.trim().isEmpty()) {
                tagList.add(tag8);
            }
            if (tag9 != null && !tag9.trim().isEmpty()) {
                tagList.add(tag9);
            }
            if (tag10 != null && !tag10.trim().isEmpty()) {
                tagList.add(tag10);
            }
            if (tag11 != null && !tag11.trim().isEmpty()) {
                tagList.add(tag11);
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

    private String submitForm(HttpServletRequest request) throws Exception {
        Star star = new Star();

        int userId = (Integer) request.getSession().getAttribute("user");

        star.setSender_id(userId);

        ArrayList<String> errorList = new ArrayList<>();

        receiverValidator(star, request, errorList);
        tagsValidator(star, request, errorList);
        descriptionValidator(star, request, errorList);

        if (errorList.isEmpty()) {

            int maxIdStar = starDb.getAll().get(0).getStar_id();
            for (Star s : starDb.getAll()) {
                int temp = s.getStar_id();

                if (temp > maxIdStar) {
                    maxIdStar = temp;
                }
            }
            maxIdStar++;
            star.setStar_id(maxIdStar);

            int availableStars = userDb.getAvailableStars(userId);

            if (availableStars > 0) {
                starDb.createStar(star);

                userDb.setAvailableStar(userId, availableStars - 1);


                //HARD CODED DAAN ZEN EMAIL

                SimpleMail.send("dennisw@live.be", "Control alt de yeet");

                request.setAttribute("availableStars", availableStars - 1);
                request.setAttribute("success", "Successfully Added Star!");
                return "index.jsp";
            } else {
                errorList.add("No more stars left to give :(");
                request.setAttribute("errors", errorList);
                return "index.jsp";
            }

        } else {
            request.setAttribute("errors", errorList);
            return "index.jsp";
        }
    }

}
