package ucll.project.domain.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void CreateUserTest(){

        User user = new User(
                "firstName",
                "lastName",
                "email@example.com",
                Role.ADMIN, true
        );

        assertEquals(user.getFirstName(), "firstName");
        assertEquals(user.getLastName(), "lastName");
        assertEquals(user.getEmail(), "email@example.com");
        assertEquals(user.getRole(), Role.ADMIN);
    }


    @Test(expected = IllegalArgumentException.class)
    public void CreateUserWithWrongEmailFormatTest(){
        User user = new User(
                "firstName",
                "lastName",
                "emailexample.com",
                Role.ADMIN, false
        );
    }

    @Test
    public void UserMakesValidPassword(){
        User user = new User(
                "firstName",
                "lastName",
                "email@example.com",
                Role.ADMIN, true
        );
        user.hashAndSetPassword("validPassword1");
        assertEquals(user.getHashedPassword(), user.getPasswordToHashedPassword("validPassword1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void UserMakesInvalidPassword(){
        User user = new User(
                "firstName",
                "lastName",
                "email@example.com",
                Role.ADMIN, true
        );
        user.hashAndSetPassword("a");
    }


}