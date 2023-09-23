package View;

import Controller.UserController;
import Model.Admin;
import Model.PortManager;
import Model.User;
import fileMethods.CreateTable;
import fileMethods.UserInput;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AuthenticationMenu implements Menu{
    // Main menu when user start our system
    private Scanner scanner = new Scanner(System.in);
    private UserController userController = UserController.getInstance();

    public AuthenticationMenu(){
        userController.loadUsersFromFile();
    }
    public void view() {
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("PORT MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu & Dr. Phong Ngo");
        System.out.println("Group 3");
        CreateTable.setShowVerticalLines(true);

        CreateTable.setHeaders("sID", "FULL NAME");
        CreateTable.addRow("s3962514", "Duong Viet Hoang");
        CreateTable.addRow("s3978145", "Phan Nguyen Viet Nhan");
        CreateTable.addRow("s3978450", "Tran Hoang Son");
        CreateTable.addRow("s3977767", "Tran Nhat Minh");
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());

        System.out.println("\n================================================= WELCOME TO PORT MANAGEMENT SYSTEM =================================================");
        int choice;
        do {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: {
                    System.out.println("Please login with username and password");
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    Optional<User> user = userController.verifyUser(username, password);
                    if (user.isPresent()) {
                        if (user.get() instanceof Admin) {
                            AdminMenu adminMenu = new AdminMenu((Admin) user.get());
                            adminMenu.view();
                        } else {
                            PortManagerMenu portManagerMenu = new PortManagerMenu((PortManager) user.get());
                            portManagerMenu.view();
                        }
                    } else {
                        System.out.println("Unauthorized");
                    }
                    break;
                }
                case 2: {
                    System.out.println("Exiting...");
                }
            }
        }while (choice != 2);
    }
}
