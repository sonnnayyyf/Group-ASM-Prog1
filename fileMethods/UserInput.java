package fileMethods;

import java.util.Scanner;

// This class is used to collect input of the users(option 1,2,3 etc...)
public class UserInput {
    public static String rawInput() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter the number to select an option:  ");
            return sc.nextLine();
        }
    }
}
