package ucll.project.ui.controller;

import ucll.project.db.ConnectionPool;
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
        getStars(request, response);
        request.setAttribute("availableStars",userDb.getAvailableStars((int)request.getSession().getAttribute("user")));
        checkStars();
        return "index.jsp";
    }

    private void getStars(HttpServletRequest request, HttpServletResponse response){
        List<Star> localStars = starDb.getAll();
        for (Star star : localStars){
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

    private void sortStars(List<Star> unsortedStars){
        Collections.sort(unsortedStars);
    }
}
