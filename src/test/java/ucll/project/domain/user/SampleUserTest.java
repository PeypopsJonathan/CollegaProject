package ucll.project.domain.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * This is a sample unit test, write your own!
 */
public class SampleUserTest {

    @Test
    public void CreateUserTest() {

        User user = new User(
                "username",
                "firstName",
                "lastName",
                "email@example.com",
                Gender.FEMALE,
                Role.ADMIN
        );

        assertEquals(user.getUserName(), "username");
        assertEquals(user.getFirstName(), "firstName");
        assertEquals(user.getLastName(), "lastName");
        assertEquals(user.getEmail(), "email@example.com");
        assertEquals(user.getGender(), Gender.FEMALE);
        assertEquals(user.getRole(), Role.ADMIN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void CreateUserWithWrongEmailTest(){
        User user = new User(
                "username",
                "firstName",
                "lastName",
                "emailexample.com",
                Gender.FEMALE,
                Role.ADMIN
        );
    }

}
