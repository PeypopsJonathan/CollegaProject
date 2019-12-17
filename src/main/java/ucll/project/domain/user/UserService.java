package ucll.project.domain.user;

import java.util.List;

public class UserService {
    private UserRepository userRepo;

    public UserService() {
        userRepo = new UserRepositoryDb();
    }

    public List<User> getUsers() {
        return userRepo.getAll();
    }

    public User getUserById(int receiver_id) {
        return userRepo.get(receiver_id);
    }
}
