package ucll.project.domain.user;

public class DummyUserData {
    public static void addData(UserRepository userRepository) {
        userRepository.createUser(
                new User("admin",
                        "admin", "user",
                        "admin@example.com",
                        Gender.FEMALE, Role.ADMIN
                ),
                "admin" // password
        );
        userRepository.createUser(
                new User("support",
                        "support", "user",
                        "support@example.com",
                        Gender.MALE, Role.SUPPORT
                ),
                "support" // password
        );
        userRepository.createUser(
                new User("user",
                        "simple", "user",
                        "user@example.com",
                        Gender.FEMALE, Role.USER
                ),
                "user" // password
        );
        userRepository.createUser(
                new User("user2",
                        "simple2", "user",
                        "user2@example.com",
                        Gender.MALE, Role.USER
                ),
                "user2" // password
        );
    }
}
