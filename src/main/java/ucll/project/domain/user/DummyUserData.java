package ucll.project.domain.user;

public class DummyUserData {
    public static void addData(UserRepository userRepository) {
        userRepository.createUser(
                new User("admin", "user",
                        "admin@example.com", Role.ADMIN,
                        false
                ),
                "admin" // password
        );
        userRepository.createUser(
                new User("support", "user",
                        "support@example.com", Role.ADMIN,
                        false
                ),
                "support" // password
        );
        userRepository.createUser(
                new User("simple", "user",
                        "user@example.com",Role.USER,
                        true
                ),
                "user" // password
        );
        userRepository.createUser(
                new User("simple2", "user",
                        "user2@example.com", Role.USER,
                        true
                ),
                "user2" // password
        );
    }
}
