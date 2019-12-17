package ucll.project.domain.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryMemory implements UserRepository {
    private Map<Integer, User> users = new HashMap<Integer, User>();

    public UserRepositoryMemory() {
        DummyUserData.addData(this);
    }

    @Override
    public void createUser(User user, String password) {
        for (User u : users.values()) {
            if (u.getEmail().equals(user.getEmail())) {
                throw new IllegalArgumentException("Email already in use");
            }
        }
        int userId = users.size() + 1;
        user.setUserId(userId);
        user.hashAndSetPassword(password);
        users.put(userId, user);
    }

    @Override
    public User get(int userId) {
        return users.get(userId);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<User>(users.values());
    }

    @Override
    public User loginUser(String email, String password) throws InvalidLogin {
        for (User u : users.values()) {
            if (u.getEmail().equals(email)) {
                if (u.isValidPassword(password)) {
                    return u;
                } else {
                    throw new InvalidLogin("Invalid password");
                }
            }
        }
        throw new InvalidLogin("Invalid username");
    }

    @Override
    public void update(User user) {
        users.replace(user.getUserId(), user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getUserId());
    }
}
