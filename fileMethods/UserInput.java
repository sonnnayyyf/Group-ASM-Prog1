/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2023B
  Assessment: Group Assessment
  Author: Group 30
  ID: s3978145 - Phan Nguyen Viet Nhan
s3962514-Duong Viet Hoang
s3978450-Tran Hoang Son
s3977767-Tran Nhat Minh

  Acknowledgement:

*/
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
