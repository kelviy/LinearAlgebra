import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(">>> Matrix Solver <<<<");

        Matrix matrix = new Matrix(inputMatrix(kb));
        System.out.println("Matrix inputted: ");
        System.out.println(matrix.printMatrix());

        repl_loop(kb, matrix);

    }

    public static void repl_loop(BufferedReader kb ,Matrix matrix) {
        String ans = "";
        String menu = "-----MENU-----\n" +
                "1. Calculate Determinate\n" +
                "2. Gaussian Elimination\n" +
                "3. Renter Matrix\n" +
                "q. Quit";

        while (!ans.equals("q")) {
            System.out.println(menu);

            try {
                System.out.println("Enter option:");
                ans = kb.readLine();

                switch (ans) {
                    case "1":
                        System.out.println("The determinate of the matrix is: " + matrix.getDeterminate());
                        break;
                    case "2":
                        System.out.println("Row Echelon Matrix");
                        matrix.guassianMatrix();
                        System.out.println(matrix.printMatrix());
                        break;
                    case "3":
                        matrix = new Matrix(inputMatrix(kb));
                        System.out.println("Matrix inputted: ");
                        System.out.println(matrix.printMatrix());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<ArrayList<Double>>  inputMatrix(BufferedReader kb) {
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
        String ans = "";
        String errorMsg = "Invalid answer. Try again.";

        try {
            System.out.println("Enter your matrix dimensions...");
            int rows = -1;
            int cols = -1;

            // Enter Rows
            while (true) {
                System.out.print("Enter number of rows: ");
                ans = kb.readLine();
                if (!isNumeric(ans)) {
                    System.out.println(errorMsg);
                } else {
                    rows = Integer.parseInt(ans);
                    break;
                }
            }

            // Enter Columns
            while (true) {
                System.out.print("Enter number of columns: ");
                ans = kb.readLine();
                if (!isNumeric(ans)) {
                    System.out.println(errorMsg);
                } else {
                    cols = Integer.parseInt(ans);
                    break;
                }
            }

            // Enter matrix
            System.out.println("Now enter matrix data. Enter each row of the matrix with each element separated by a space. (press b to re-enter previous row)");
            System.out.println("Enter rows (top down): ");


            for (int i = 0; i < rows; i++) {
                ans = kb.readLine();

                String[] elements = ans.split(" ");
                ArrayList<Double> row = new ArrayList<>();

                for (int j = 0; j < cols; j++) {
                    row.add(Double.parseDouble(elements[j]));
                }

                matrix.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}