package ucll.project.domain.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserRepositoryMemoryTest {

    @Test
    public void createUserWithUnusedEmail() {
        User user = new User(
                "firstName",
                "lastName",
                "email@example.com",
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(user, "Password1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserWithUsedEmail(){
        User user = new User(
                "firstName",
                "lastName",
                "email@example.com",
                Role.ADMIN
        );
        User invalidUser = new User(
                "firstName2",
                "lastName2",
                "email@example.com",
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(user, "Password1");
        u.createUser(invalidUser, "Password2");
    }

    @Test
    public void get() {
        User user = new User(
                "firstName",
                "lastName",
                "email@example.com",
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(user, "Password1");
        User copyUser = u.get(5);
        assertEquals(copyUser.getFirstName(), user.getFirstName());
        assertEquals(copyUser.getLastName(), user.getLastName());
        assertEquals(copyUser.getEmail(), user.getEmail());
        assertEquals(copyUser.getRole(), user.getRole());
    }

    @Test
    public void loginUser() throws InvalidLogin {
        User user = new User(
                "firstName",
                "lastName",
                "email@example.com",
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(user, "Password1");
        u.loginUser("email@example.com", "Password1");
    }

    @Test(expected = InvalidLogin.class)
    public void loginUserWithInvalidPassword() throws InvalidLogin {
        User user = new User(
                "firstName",
                "lastName",
                "email@example.com",
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(user, "Password1");
        u.loginUser("email@example.com", "Password2");

    }

    @Test(expected = InvalidLogin.class)
    public void loginUserWithInvalidUsername() throws InvalidLogin {
        User user = new User(
                "firstName",
                "lastName",
                "email@example.com",
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(user, "Password1");
        u.loginUser("email@example2.com", "Password2");
    }
}