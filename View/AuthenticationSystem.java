package View;

import fileMethods.CreateTable;
import fileMethods.UserInput;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AuthenticationSystem {

    // Main menu when user start our system
    public void view() throws IOException, InterruptedException, ParseException {
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("PORT MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu & Dr. Phong Ngo");
        System.out.println("Group 3");
        CreateTable.setShowVerticalLines(true);

        CreateTable.setHeaders("sID", "FULL NAME");
        CreateTable.addRow("s3962514", "Duong Viet Hoang");
        CreateTable.addRow("s3978145", "Phan Nguyen Viet Nhan");
        CreateTable.addRow("s3686752", "Tran Hoang Son");
        CreateTable.addRow("s4043296", "Tran Nhat Minh");
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());

        System.out.println("\n================================================= WELCOME TO PORT MANAGEMENT SYSTEM =================================================");
        System.out.println("1. Use as a manager port");
        System.out.println("2. Use as an admin");
        System.out.println("3. Exit");

        AdminMenu adminMenu = new AdminMenu();
        UserMenu portManagerMenu = new UserMenu();
        String option = UserInput.rawInput();
        switch (option) {
            case "1":
                portManagerMenu.view();
            case "2":
                adminMenu.view();
            case "3":
                System.exit(1);
            default:
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                this.view();
        }
    }
}
