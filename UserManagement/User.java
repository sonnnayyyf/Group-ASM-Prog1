package UserManagement;

public enum Role {
    Admin,
    PortManager
}

public class User {
    private String userId;
    private String username;
    private String password;
    private Role role;

    public User() {}

    public User(String userId, String username, String password, Role role) {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean login(String username, String password) {
        String storedUsername = "sampleUsername";
        String storedPassword = "samplePassword";
        Role storedRole = Role.Admin;

        if (this.username != null) {
            System.out.println("Already logged in.");
            return false;
        }

        if (username.equals(storedUsername) && password.equals(storedPassword)) {
            this.userId = generateUserId();
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

    private String generateUserId() {
        return "USER" + System.currentTimeMillis();
    }


    public void logout() {
        this.userId = null;
        this.username = null;
        this.password = null;
        this.role = null;
        System.out.println("Logged out successfully.");
    }
}
