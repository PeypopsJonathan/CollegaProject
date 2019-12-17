package ucll.project.ui.controller;

import ucll.project.domain.star.Star;
import ucll.project.domain.star.StarRepository;
import ucll.project.domain.star.StarRepositoryDb;
import ucll.project.domain.user.UserRepository;
import ucll.project.domain.user.UserRepositoryDb;
import ucll.project.domain.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
        return "index.jsp";
    }

    private void getStars(HttpServletRequest request, HttpServletResponse response){
        List<Star> localStars = starDb.getAll();
        for (Star star : localStars){
            star.setReceiver_name(userDb.get(star.getReceiver_id()).getFirstName() + " " + userDb.get(star.getReceiver_id()).getLastName());
            star.setSender_name(userDb.get(star.getSender_id()).getFirstName() + " " + userDb.get(star.getSender_id()).getLastName());
        }
        System.out.println(localStars);
        request.setAttribute("stars", localStars);
    }
}
