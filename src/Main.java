import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        String menu = """
                -----MENU-----
                1. Print Matrix again
                2. Calculate Determinate
                3. Transform to Gaussian Elimination
                4. Transpose Matrix
                5. Multiply with another Matrix
                6. Renter Matrix
                q. Quit""";

        while (!ans.equals("q")) {
            System.out.println(menu);

            try {
                System.out.println("Enter option:");
                ans = kb.readLine();

                switch (ans) {
                    case "1":
                        System.out.println(matrix.printMatrix());
                        break;
                    case "2":
                        System.out.println("The determinate of the matrix is: " + matrix.getDeterminate());
                        break;
                    case "3":
                        System.out.println("Row Echelon Matrix");
                        matrix.gaussianMatrix();
                        System.out.println(matrix.printMatrix());
                        break;
                    case "4":
                        matrix = matrix.transpose();
                        System.out.println(matrix.printMatrix());
                        break;
                    case "5":
                        System.out.println("Enter new Matrix:");
                        Matrix newMatrix = new Matrix(inputMatrix(kb));
                        matrix = matrix.multiply(newMatrix);
                        System.out.println("Result of multiplication:");
                        System.out.println(matrix.printMatrix());
                        break;
                    case "6":
                        matrix = new Matrix(inputMatrix(kb));
                        System.out.println("Matrix inputted: ");
                        System.out.println(matrix.printMatrix());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static double[][]  inputMatrix(BufferedReader kb) {
        String ans = "";
        String errorMsg = "Invalid answer. Try again.";
        double[][] matrix = null;

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

            matrix = new double[rows][cols];

            // Enter matrix
            System.out.println("Now enter matrix data. Enter each row of the matrix with each element separated by a space. (press b to re-enter previous row)");
            System.out.println("Enter rows (top down): ");


            for (int i = 0; i < rows; i++) {
                ans = kb.readLine();

                String[] elements = ans.split(" ");
                double[] row = new double[cols];

                for (int j = 0; j < cols; j++) {
                    row[j] = Double.parseDouble(elements[j]);
                }

                matrix[i] = row;
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