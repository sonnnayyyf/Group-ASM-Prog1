package fileMethods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadDataFromTXTFile {

    // Read all of a file's content within a specific column.
    public static String[] readColString(int col, String filepath, String delimiter) throws IOException {
        String[] data;
        String currentLine;
        ArrayList<String> colData = new ArrayList<>();

        try {
            // will read a specific text file in a particular method when using this method to support that method.
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // while reading that file, if the reader contact a symbol that is set in another method that this method supported
            // it will then break, skip all other columns except for one column that is asked to read.
            while ((currentLine = bufferedReader.readLine()) != null) {
                data = currentLine.split(delimiter);
                colData.add(data[col]);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return colData.toArray(new String[0]);
    }

    // Read all of a file's content lines
    public static ArrayList<String[]> readAllLines(String filepath) {
        String[] data;
        String currentLine;
        ArrayList<String[]> allFileData = new ArrayList<>();

        try {
            // Will read a specific text file in a particular method when using this method to support that method.
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // While reading that file, if the reader contact a "," it will then break,
            // so it will be much easier to add into an ArrayList later on when support another method.
            while ((currentLine = bufferedReader.readLine()) != null) {
                data = currentLine.split(",");
                allFileData.add(data);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return allFileData;
    }

    // Read all of a file's content within a specific line.
    public static String[] readSpecificLine(String search, int col, String filepath, String delimiter) {
        String[] lineData;
        String currentLine;

        try {
            // will read a specific text file in a particular method when using this method to support that method.
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // If a symbol set in a different manner than the one this method supports is encountered when reading that file,
            // the reader will break and skip all other lines except for the one that is requested to be read.
            // Additionally, it will look at a certain column to determine which one the requested line is in.
            while ((currentLine = bufferedReader.readLine()) != null) {
                lineData = currentLine.split(delimiter);
                if (lineData[col].equals(search)) {
                    return lineData;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return new String[0];
    }
}
