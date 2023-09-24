package Controller;

import Model.Admin;
import Model.PortManager;
import Model.User;
import fileMethods.EncryptUtil;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserController {
    private static UserController instance;
    private ArrayList<User> listOfUsers = new ArrayList<User>();
    public static UserController getInstance(){
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }
    public ArrayList<User> getAllUsers(){
        return this.listOfUsers;
    }
    public Optional<User> verifyUser(String userName, String password){
        Optional<User> user = this.listOfUsers.stream().filter(user1 -> user1.getUsername().equals(userName)).findFirst();
        return user;
    }
    public void createManager(String name, String email, String address, String phone, String userName, String password, String port){
        PortManager portManager = new PortManager("u-"+generateUniqueUserID(), name, email, address, phone, userName, EncryptUtil.getInstance().hashing(password), port);
        listOfUsers.add(portManager);
        writeUsersToFile();
    }
    public void removeUser(String username){
        listOfUsers.removeIf(user -> user.getUsername().equals(username));
        writeUsersToFile();
    }
    public ArrayList<User> getAllManagers(){
        return this.listOfUsers.stream().filter(user -> user instanceof PortManager).collect(Collectors.toCollection(ArrayList::new));
    }
    public Optional<User> getUserByID(String id){
        return this.listOfUsers.stream().filter(user -> user.getuID().equals(id)).findFirst();
    }
    public Optional<User> getUserByUsername(String username){
        return this.listOfUsers.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
    public void loadUsersFromFile() {
        try {
            ArrayList<User> user = new ArrayList<User>(); // Create an arraylist to contain all users' information
            Scanner fileScanner = new Scanner(new File("dataFile/users.txt"));
            fileScanner.nextLine();
            while (fileScanner.hasNext()) {
                String[] data; // Create an array to store one user's information
                String line = fileScanner.nextLine();
                StringTokenizer stringTokenizer = new StringTokenizer(line, ",");

                // Separate the line's information by comma
                String ID = stringTokenizer.nextToken();
                String name = stringTokenizer.nextToken();
                String email = stringTokenizer.nextToken();
                String address = stringTokenizer.nextToken();
                String phone = stringTokenizer.nextToken();
                String userName = stringTokenizer.nextToken();
                String password = stringTokenizer.nextToken();
                String port = stringTokenizer.nextToken();
                String role = stringTokenizer.nextToken();
                if (role.equals("Manager")) {
                    user.add(new PortManager(ID, name, email, address, phone, userName, password, port));
                } else {
                    user.add(new Admin(ID, name, email, address, phone, userName, password));
                }
            }
            this.listOfUsers = user;
            System.out.println("Users loaded from dataFile/users.txt");
        } catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    public void writeUsersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("dataFile/users.txt"))) {
            // Write the CSV header
            writer.println("#ID,Name,Email,Address,Phone,Username,Password,Port,Role");

            // Write user records
            for (User user : listOfUsers) {
                String port; String role;
                if (user instanceof PortManager){
                    port = ((PortManager) user).getPort();
                    role = "Manager";
                } else {
                    port = "#";
                    role = "Admin";
                }
                writer.println(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",
                        user.getuID(),
                        user.getName(),
                        user.getEmail(),
                        user.getAddress(),
                        user.getPhone(),
                        user.getUsername(),
                        user.getPassword(),
                        port,
                        role
                ));
            }

            System.out.println("Users have been written to " + "dataFile/users.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private synchronized String generateUniqueUserID() {
        int maxAssignedNumber = 0;
        for (User user : listOfUsers) {
            String containerID = user.getuID();
            if (containerID.startsWith("u-")) {
                try {
                    int number = Integer.parseInt(containerID.substring(2));
                    maxAssignedNumber = Math.max(maxAssignedNumber, number);
                } catch (NumberFormatException e) {
                }
            }
        }
        return String.valueOf(maxAssignedNumber+1);
    }
}
