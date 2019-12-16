package ucll.project.domain.user;

public enum Role {
    USER("user"),
    SUPPORT("support"),
    ADMIN("admin");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
