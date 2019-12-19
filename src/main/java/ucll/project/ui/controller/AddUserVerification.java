package ucll.project.ui.controller;

import ucll.project.domain.user.Role;
import ucll.project.domain.user.User;
import ucll.project.domain.user.UserRepositoryDb;
import ucll.project.domain.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class AddUserVerification extends RequestHandler {

    public AddUserVerification(String command, UserService userService) {
        super(command, userService);
    }

    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        boolean manager = request.getParameter("manager") != null;
        boolean superuser = request.getParameter("superuser") != null;

        User user = new User();
        ArrayList<String> error = new ArrayList<>();

        setFirstname(user,request,firstname,error);
        setLastname(user,request,lastname,error);
        setEmail(user,request,email,error);
        setRole(user,request,role,error);
        setManager(user,request,manager,error);
        setSuperuser(user,request,superuser,error);

        if (error.size() >  0) {
            request.setAttribute("errors", error);
            return "management.jsp";
        }
        UserRepositoryDb userDB = new UserRepositoryDb();
        userDB.addUserWithoutPassword(user);
        return "Controller?command=Index";
    }

    private void setSuperuser(User user, HttpServletRequest request, boolean superuser, ArrayList<String> error) {
        user.setSuperuser(superuser);
        request.setAttribute("previousSuperuser",superuser);
    }

    private void setManager(User user, HttpServletRequest request, boolean manager, ArrayList<String> error) {
        user.setManager(manager);
        request.setAttribute("previousManager",manager);
    }

    private void setRole(User user, HttpServletRequest request, String stringrole, ArrayList<String> error) {
        try {
            Role role = Role.valueOf(stringrole);
            user.setRole(role);
            request.setAttribute(stringrole,true);
        } catch (Exception e) {
            error.add("no valid role selected");
        }
    }

    private void setEmail(User user, HttpServletRequest request, String email, ArrayList<String> error) {
        try {
            user.setEmail(email);
            request.setAttribute("previousEmail", email);
        } catch (IllegalArgumentException e){
            error.add(e.getMessage());
        }

    }

    private void setLastname(User user, HttpServletRequest request, String lastname,ArrayList<String> error) {
        if (lastname != null && !lastname.trim().isEmpty()){
            user.setLastName(lastname);
            request.setAttribute("previousLastname",lastname);
        } else {
            error.add("lastname can't be empty");
        }
    }

    private void setFirstname(User user, HttpServletRequest request, String firstname,ArrayList<String> error) {
        if (firstname != null  && !firstname.trim().isEmpty()){
            user.setFirstName(firstname);
            request.setAttribute("previousFirstname",firstname);
        } else {
            error.add("firstname can't be empty");
        }
    }
}
