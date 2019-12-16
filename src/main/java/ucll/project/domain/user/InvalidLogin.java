package ucll.project.domain.user;

public class InvalidLogin extends Exception {
    public InvalidLogin(String error) {
        super(error);
    }
}
