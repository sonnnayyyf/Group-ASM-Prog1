package fileMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CreateTable {
    // Attributes
    private static final String HORIZONTAL_SEP = "-";
    private static String verticalSep;
    private static String joinSep;
    private static String[] headers;
    private static ArrayList<String[]> rows = new ArrayList<>();
    private static boolean rightAlign;

    // Constructor
    public CreateTable() {
        setShowVerticalLines(true);
    }

    public static void setRightAlign(boolean rightAlign) {
        CreateTable.rightAlign = rightAlign;
    }

    // When you set the setShowVerticalLines to true, the border of the table will be set under the below format.
    public static void setShowVerticalLines(boolean showVerticalLines) {
        verticalSep = showVerticalLines ? "|" : "";
        joinSep = showVerticalLines ? "*" : " ";
    }

    // Set header for the table
    public static void setHeaders(String... headers) {
        CreateTable.headers = headers;
    }

    // Add new row for the table
    public static void addRow(String... cells) {
        rows.add(cells);
    }

    // Print the table
    public static void print() {
        // If the user input the header, the header will become !=null and all the borders will be measured.*/
        int[] maxWidths = headers != null ?
                Arrays.stream(headers).mapToInt(String::length).toArray() : null;

        for (String[] cells : rows) {
            if (maxWidths == null) {
                maxWidths = new int[cells.length];
            }
            if (cells.length != maxWidths.length) {
                throw new IllegalArgumentException("Number of row-cells and headers should be consistent");
            }
            for (int i = 0; i < cells.length; i++) {
                maxWidths[i] = Math.max(maxWidths[i], cells[i].length());
            }
        }

        // If the user input the header, the header will become !=null and print out the table */
        if (headers != null) {
            printLine(maxWidths);
            printRow(headers, maxWidths);
            printLine(maxWidths);
        }
        for (String[] cells : rows) {
            printRow(cells, maxWidths);
        }
        if (headers != null) {
            printLine(maxWidths);
        }
    }

    // This method is used to print the information in the line column.
    private static void printLine(int[] columnWidths) {
        for (int i = 0; i < columnWidths.length; i++) {
            String line = String.join("", Collections.nCopies(columnWidths[i] +
                    verticalSep.length() + 1, HORIZONTAL_SEP));
            System.out.print(joinSep + line + (i == columnWidths.length - 1 ? joinSep : ""));
        }
        System.out.println();
    }

    // This method is used to print the information in the row column.
    private static void printRow(String[] cells, int[] maxWidths) {
        for (int i = 0; i < cells.length; i++) {
            String s = cells[i];
            String verStrTemp = i == cells.length - 1 ? verticalSep : "";
            if (rightAlign) {
                System.out.printf("%s %" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
            } else {
                System.out.printf("%s %-" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
            }
        }
        System.out.println();
    }

    // Setter method for rows
    public static void setRows(ArrayList<String[]> rows) {
        CreateTable.rows = rows;
    }
}