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
            temp = '"'+ u.getFirstName() + " " + u.getLastName() + '"';
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

    public boolean getManagerStatusUser(int userId){
        for (User user: getUsers()) {
            if (user.getUserId() == userId){
                return user.isManager();
            }
        }
        return false;
    }

    public String getUserMailById(int id) {
        return ((UserRepositoryDb)userRepo).userMailById(id);
    }

    public String getUserNameById(int id) {
        return ((UserRepositoryDb)userRepo).userNameById(id);
    }


    public List<User> getAllManagers() {
        return ((UserRepositoryDb)userRepo).getAllMAnagers();
    }
}
