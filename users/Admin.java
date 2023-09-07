package users;

import fileMethods.CreateTable;
import fileMethods.ReadDataFromTXTFile;
import fileMethods.UserInput;
import fileMethods.Write;
//import product.Product;
//import product.SortProduct;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Admin extends User {
    // Constructor
    public Admin() {
        super();
    }

    // This method would verify username and password for admin account
    public boolean verifyAdmin(String username, String password) {
        String hashPassword = this.hashing(password); // Hash the input password
        // If the username and password after hashing are correct
        return username.equals("admin") && hashPassword.equals("21232f297a57a5a743894a0e4a801fc3");
    }

    // This method will display all the customers' information that existed in customers' file
    public void getAllUsersInfo() throws FileNotFoundException {
        ArrayList<String[]> user = new ArrayList<>(); // Create an arraylist to contain all users' information
        Scanner fileScanner = new Scanner(new File("./src/dataFile/users.txt"));

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
            String username = stringTokenizer.nextToken();
            String password = stringTokenizer.nextToken();
            String port = stringTokenizer.nextToken();
            String role = stringTokenizer.nextToken();
            data = new String[]{ID, name, username, email, address, phone, password, role, port};
            // Add one user's information to an array
            user.add(data); // Add one user's information in an arraylist
        }

        // Create table to display users' information
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("INDEX", "UID", "NAME", "USERNAME", "PASSWORD", "EMAIL", "ADDRESS", "PHONE", "PORT");
        // Set header for the table including the new INDEX column

        for (int i = 1; i < user.size(); i++) {  // Bắt đầu từ dòng thứ hai
            CreateTable.addRow(
                    String.valueOf(i), // The new INDEX column
                    user.get(i)[0],
                    user.get(i)[1],
                    user.get(i)[2],
                    user.get(i)[6],
                    user.get(i)[3],
                    user.get(i)[4],
                    user.get(i)[5],
                    user.get(i)[8]
            );
        }

        CreateTable.print(); // Print the table
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }


    // This method is used to create new user and add to the users.txt file.
    public void createNewUsers(String name, String email, String address, String phone, String username, String password, int port) throws IOException {
        Path path = Paths.get("./src/dataFile/users.txt");
        int id = (int) Files.lines(path).count();

        try (PrintWriter pw = new PrintWriter(new FileWriter("./src/dataFile/users.txt", true))) {
            pw.println("u" + id + "," + name + "," + email + "," + address + "," + phone + "," + username + "," + password + "," + port + "," + "Manager");
        }
        System.out.println("Updated successful");
    }

//    This method will check if a given username already exists in the users.txt file
    public boolean isUsernameUnique(String username) throws IOException {
        Path path = Paths.get("./src/dataFile/users.txt");
        return Files.lines(path).noneMatch(line -> line.split(",")[5].equals(username));
    }

    /* All the methods delete basically works in the same logic
     * First it finds the corresponding customer ID or name for category or product ID and exclude the information belong to
     * that specific input, then it adds all the remaining info into a temporary ArrayList (newDataBase) and deletes all the content in .txt file
     * and rewrite the file with the data in the newDataBase which will not have the "deleted data" since that has been excluded from the newDataBase
     */
//    public void deleteProductCategory(String filepath, String delCategory) throws IOException {
//        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");
//
//        // Loop through all the product ID
//        for (String[] strings : database) {
//
//            // If a category is the same, it will change the category of that product to None
//            if (strings[3].equals(delCategory)) {
//                strings[3] = "None";
//            }
//        }
//
//        PrintWriter pw = new PrintWriter("./src/dataFile/items.txt");
//
//        pw.write(""); // The file would erase all the data in products file
//        pw.close();
//
//        // This method would allow system to write all data including new data into the customers' file
//        for (String[] obj : database) {
//            Write.rewriteFile(filepath, "#ID,Title,Price,Category", String.join(",", obj));
//        }
//        System.out.println("Deletion successful");
//    }

    // This method allow admin to delete a category that had existed in categories' file
    public void deleteUser(String filepath, String delCategory) throws IOException {
        ArrayList<String[]> categoryList = ReadDataFromTXTFile.readAllLines("./src/dataFile/users.txt");
        ArrayList<String[]> newCategoryList = new ArrayList<>();


        // Loop through all the categories
        for (String[] strings : categoryList) {
            if (!strings[1].equals(delCategory)) {
                newCategoryList.add(strings); // Add all categories except the deleted category
            }
        }
        PrintWriter pw = new PrintWriter("./src/dataFile/users.txt");

        pw.write(""); // The file would erase all the data in categories' file
        pw.close();
        for (String[] obj : newCategoryList) {
            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,Username,Password,Port,Role", String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
        System.out.println("Deletion successful");
    }

}

