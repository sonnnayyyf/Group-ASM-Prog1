package UserManagement;

public class User {
    private String userId;
    private String username;
    private String password;
    private String role;

    public User() {}

    public User(String userId, String username, String password, String role) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void logout() {
        this.userId = null;
        this.username = null;
        this.password = null;
        this.role = null;
        System.out.println("Logged out successfully.");
    }
}
