package menu;

import fileMethods.ReadDataFromTXTFile;
import fileMethods.UserInput;
import users.Admin;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AdminMenu {

    // Display a menu for user before login
    public void view() throws IOException, ParseException, InterruptedException {
        System.out.println("\n================================================= WELCOME TO PORT MANAGEMENT SYSTEM =================================================");
        System.out.println("1. Login by admin account");
        System.out.println("2. Back to authentication system");
        System.out.println("3. Exit");

        boolean validUser = false;
        Admin admin = new Admin();
        AuthenticationSystem authenticationSystem = new AuthenticationSystem();
        String option = UserInput.rawInput();
        switch (option) {
            case "1":
                System.out.println("\n================================================= LOGIN FORM =================================================");
                do {
                    // Ask user to input username and password
                    // if the username and password are not correct, the system will ask user to input again
                    // otherwise, the system will change to the homepage after login
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    if (!admin.verifyAdmin(username, password)) {
                        System.out.println("Wrong password, try again !!!!!");

                    } else {
                        this.viewHomepage();
                        validUser = true;
                    }
                } while (!validUser);

            case "2":
                authenticationSystem.mainMenu();

            case "3":
                System.exit(1);

            default:
                // If customer input another option that don't have in the menu
                // then the system will give he/she message and back to the viewpage
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                this.view();
        }
    }

    // Display the homepage for admin when he/she log in successfully
    public void viewHomepage() throws IOException, InterruptedException, ParseException {
        System.out.println("\n================================================= HOMEPAGE =================================================");
        System.out.println("1. User");
        System.out.println("2. Log out");
        System.out.println("3. Exit");

        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();
        AdminMenu adminMenu = new AdminMenu();
        String choice = UserInput.rawInput();

        switch (choice) {
            case "1":
                // Allow user to add more product or remove any product
                System.out.println("\n================================================= USER =================================================");
                System.out.println("1. List all users' information");
                System.out.println("2. Add new user");
                System.out.println("3. Delete user");
                System.out.println("4. Back to homepage");

                String categoryOption = UserInput.rawInput();
                switch (categoryOption) {
                    case "1":
                        // Display the information of all customers
                        admin.getAllUsersInfo();
                        TimeUnit.SECONDS.sleep(1);
                        adminMenu.viewHomepage();
                    case "2":
                        System.out.print("Enter new name (e.g: Hoang): ");
                        String name = scanner.nextLine();
                        System.out.print("Enter new email (e.g: S3962514@rmit.edu.vn): ");
                        String email = scanner.nextLine();
                        System.out.print("Enter new address (e.g: 265 Kim Ma): ");
                        String address = scanner.nextLine();
                        System.out.print("Enter new phone (e.g: 0942009919): ");
                        String phone = scanner.nextLine();
                        System.out.print("Enter new username (e.g: dvhoang): ");
                        String username = scanner.nextLine();
                        System.out.print("Enter new password (e.g: 123): ");
                        String password = scanner.nextLine(); // Consider hashing this password before saving

                        int portInt;
                        do {
                            System.out.print("Enter new port (e.g: 1): ");
                            String port = scanner.nextLine();

                            try {
                                portInt = Integer.parseInt(port);
                                if (!admin.isUsernameUnique(username)) {
                                    System.out.println("Username already exists! Please choose another.");
                                    return;
                                }
                                admin.createNewUsers(name, email, address, phone, username, password, portInt);
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter a valid port number.");
                                portInt = -1;  // Setting to an invalid value to continue the loop
                            } catch (IOException e) {
                                System.out.println("An error occurred. Please try again.");
                                portInt = -1;  // Setting to an invalid value to continue the loop
                            }
                        } while (portInt == -1);  // If portInt is -1, it means there was an error and we continue the loop

                        adminMenu.viewHomepage();


                    case "3":
                        admin.getAllUsersInfo();
                        System.out.println("CHOOSE AN INDEX WANT TO DELETE");
                        String delUser = UserInput.rawInput();
                        ArrayList<String[]> userList = ReadDataFromTXTFile.readAllLines("./src/dataFile/users.txt");
                        String[] userInfo = new String[9];
                        for (int i = 0; i < userList.size(); i++) {
                            if (i == Integer.parseInt(delUser)) {
                                userInfo = ReadDataFromTXTFile.readSpecificLine(userList.get(i)[1], 1, "./src/dataFile/users.txt", ",");
                            }
                        }
                        admin.deleteUser("./src/dataFile/users.txt", userInfo[1]);
                        adminMenu.viewHomepage();

                    case "4":
                        adminMenu.viewHomepage();
                }

            case "2":
                adminMenu.view();

            case "3":
                System.exit(1);

            default:
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewHomepage();
        }
    }
}


