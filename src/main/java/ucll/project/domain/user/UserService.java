package ucll.project.domain.user;

import java.util.List;

public class UserService {
    private UserRepository userRepo;

    public UserService(){
        userRepo = new UserRepositoryMemory();
    }

    public List<User> getUsers(){
        return userRepo.getAll();
    }
}
