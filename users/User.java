package users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

public class User {
    // Attributes
    private String uID;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String username;
    private String password;
    private int port;
    private String role;

    // Constructor
    public User(String uID, String name, String email, String address, String phone, String userName, String password, int port, String role) {
        this.uID = uID;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.username = userName;
        this.password = password;
        this.port = port;
        this.role = role;
    }

    // Constructor
    public User() {
    }


    // Hashing the customer's password
    public String hashing(String password) {
        try {
            // MessageDigest instance for MD5.
            MessageDigest m = MessageDigest.getInstance("MD5");

            // Add plain-text password bytes to digest using MD5 update() method.
            m.update(password.getBytes());

            // Convert the hash value into bytes
            byte[] bytes = m.digest();

            // The bytes array has bytes in decimal form. Converting it into hexadecimal format.
            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) {
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            // Complete hashed password in hexadecimal format
            return s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Getter method for customer ID
    public String getuID() {
        return uID;
    }

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Setter method for customer ID
    public void setuID(String uID) {
        this.uID = uID;
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Setter method for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for email
    public String getEmail() {
        return email;
    }

    // Setter method for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter method for address
    public String getAddress() {
        return address;
    }

    // Setter method for address
    public void setAddress(String address) {
        this.address = address;
    }

    // Getter method for phone number
    public String getPhone() {
        return phone;
    }

    // Setter method for phone number
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Setter method for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Setter method for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter method for port
    public int getPort() {
        return port;
    }

    // Setter method for port
    public void setPort(int port) {
        this.port = port;
    }

    // Getter method for role
    public String getRole() {
        return role;
    }

    // Setter method for role
    public void setRole(String role) {
        this.role = role;
    }
}