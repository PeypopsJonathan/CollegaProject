package ucll.project.domain.user;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserRepository userRepo;

    public UserService(){
        userRepo = new UserRepositoryDb();
    }

    public List<User> getUsers(){
        return userRepo.getAll();
    }

    public List<String> getAllNames(){
        List<String> names = new ArrayList<>();
        String temp;
        for (User u: getUsers()) {
            temp = '"'+ u.getFirstName() + '"';
            names.add(temp);
        }
        return names;
    }

    public int verifyUserLogin(String email,String password){
        return userRepo.verify(email,password);
    }

    public int getUserByName(String firstName, String lastName) {
        return ((UserRepositoryDb)userRepo).UserIdByName(firstName, lastName);
    }
}
