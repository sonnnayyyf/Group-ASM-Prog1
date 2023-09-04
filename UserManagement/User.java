package UserManagement;

public enum UserRole {
    ADMIN, PORT_MANAGER
}

public class User {
    private String userId;
    private String username;
    private String password;
    private UserRole role;

    public User() {}

    public User(String userId, String username, String password, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void logout() {
        System.out.println("Logged out successfully.");
    }

    public boolean login(String username, String password) {
        String storedUsername = "sampleUsername";
        String storedPassword = "samplePassword";
        UserRole storedRole = UserRole.ADMIN;

        if (this.username != null) {
            System.out.println("Already logged in.");
            return false;
        }

        if (username.equals(storedUsername) && password.equals(storedPassword)) {
            this.userId = "sampleUserId";
            this.username = storedUsername;
            this.password = storedPassword;
            this.role = storedRole;
            System.out.println("Logged in successfully.");
            return true;
        } else {
            System.out.println("Login failed.");
            return false;
        }
    }
}
