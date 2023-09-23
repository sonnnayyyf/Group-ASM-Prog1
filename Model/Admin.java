package Model;

import fileMethods.CreateTable;
import fileMethods.ReadDataFromTXTFile;
import fileMethods.Write;
//import product.Product;
//import product.SortProduct;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Admin extends User {
    // Constructor
    public Admin(String uID, String name, String email, String address, String phone, String userName, String password) {
        super(uID, name, email, address, phone, userName, password);
    }
}

