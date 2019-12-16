package ucll.project.domain.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserRepositoryMemoryTest {

    @Test
    public void createUserWithUnusedUserAndPassword() {
        User user = new User(
                "username",
                "firstName",
                "lastName",
                "email@example.com",
                Gender.FEMALE,
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(user, "Password1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserWithUsedLogin(){
        User user = new User(
                "username",
                "firstName",
                "lastName",
                "email@example.com",
                Gender.FEMALE,
                Role.ADMIN
        );
        User invalidUser = new User(
                "username",
                "firstName2",
                "lastName2",
                "email@example2.com",
                Gender.FEMALE,
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(user, "Password1");
        u.createUser(invalidUser, "Password2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserWithUsedEmail(){
        User user = new User(
                "username",
                "firstName",
                "lastName",
                "email@example.com",
                Gender.FEMALE,
                Role.ADMIN
        );
        User invalidUser = new User(
                "username2",
                "firstName2",
                "lastName2",
                "email@example.com",
                Gender.FEMALE,
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(user, "Password1");
        u.createUser(invalidUser, "Password2");
    }

    @Test
    public void get() {
        User user = new User(
                "username",
                "firstName",
                "lastName",
                "email@example.com",
                Gender.FEMALE,
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(user, "Password1");
        User copyUser = u.get(5);
        assertEquals(copyUser.getUserName(), user.getUserName());
        assertEquals(copyUser.getFirstName(), user.getFirstName());
        assertEquals(copyUser.getLastName(), user.getLastName());
        assertEquals(copyUser.getEmail(), user.getEmail());
        assertEquals(copyUser.getGender(), user.getGender());
        assertEquals(copyUser.getRole(), user.getRole());
    }

    @Test
    public void loginUser() throws InvalidLogin {
        User firstUser = new User(
                "username",
                "firstName",
                "lastName",
                "email@example.com",
                Gender.FEMALE,
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(firstUser, "Password1");
        u.loginUser("username","Password1");
    }

    @Test(expected = InvalidLogin.class)
    public void loginUserWithInvalidPassword() throws InvalidLogin {
        User user = new User(
                "username1",
                "firstName",
                "lastName",
                "email@example.com",
                Gender.FEMALE,
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(user, "Password1");
        u.loginUser("username1", "Password2");

    }

    @Test(expected = InvalidLogin.class)
    public void loginUserWithInvalidUsername() throws InvalidLogin {
        User user = new User(
                "username1",
                "firstName",
                "lastName",
                "email@example.com",
                Gender.FEMALE,
                Role.ADMIN
        );
        UserRepositoryMemory u = new UserRepositoryMemory();
        u.createUser(user, "Password1");
        u.loginUser("username2", "Password2");
    }
}