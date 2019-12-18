package ucll.project.domain.user;

import ucll.project.domain.star.Star;

import java.util.List;

public interface UserRepository {

    // CREATE
    void createUser(User user, String password);

    // READ ONE
    User get(int userId);

    // READ ALL
    List<User> getAll();

    // LOGIN (= GET but with password check)
    User loginUser(String username, String password) throws InvalidLogin;

    // UPDATE
    void update(User user);

    // DELETE
    void delete(User user);

    int verify(String email, String password);

    List<Star> getStar();

}
