package ucll.project.domain.user;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private boolean isManager;

    // hashed password
    private transient String hashedPassword;

    public User() {
    }

    public User(String firstName, String lastName, String email, Role role, boolean isManager) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setRole(role);
        this.isManager = isManager;
    }

    public void hashAndSetPassword(String password) {
        if (password.length() < 4) {
            throw new IllegalArgumentException("Too short password!");
        }
        String hashed = getPasswordToHashedPassword(password);
        setHashedPassword(hashed);
    }

    // This function will hash the password
    protected String getPasswordToHashedPassword(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
        digest.update(password.getBytes(StandardCharsets.UTF_8));
        String hash = DatatypeConverter.printHexBinary(digest.digest()).toUpperCase();
        return hash;
    }

    public boolean isValidPassword(String password) {
        if (getHashedPassword() == null) {
            return false;
        }
        return getPasswordToHashedPassword(password).equals(getHashedPassword());
    }

    // Getters and setters and toString
    public int getUserId() {
        return this.userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Role getRole() {
        return this.role;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        if (email.trim().isEmpty() || !mat.matches()) throw new IllegalArgumentException("invalid email");
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setManager(boolean isManager){
        this.isManager = isManager;
    }

    public boolean isManager(){
        return this.isManager;
    }

    public String toString() {
        return "User(userId=" + this.getUserId() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", email=" + this.getEmail() + ", role=" + this.getRole() + ", hashedPassword=" + this.getHashedPassword() + ")";
    }
}
