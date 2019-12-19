package ucll.project.domain.user;

import ucll.project.domain.star.Star;

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
    public int verify(String email, String password) {
        return 0;
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
    public int getAvailableStars(int id) {
        return -1;
    }

    @Override
    public void update(User user) {
        users.replace(user.getUserId(), user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getUserId());
    }

    @Override
    public void reassignStars() {
    }

    @Override
    public void setAvailableStar(int id, int aantal) {

    }


    @Override
    public List<Star> getStar(int id) {
        return new ArrayList<Star>();
    }
}
