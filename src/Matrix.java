import java.util.ArrayList;

public class Matrix {
    private ArrayList<ArrayList<Double>> matrix = new ArrayList<>();

    public Matrix(ArrayList<ArrayList<Double>> matrix) {
        int columns = matrix.get(0).size();
        for (int i = 1; i < matrix.size(); i++) {
            if (columns != matrix.get(i).size()) {
                throw new IllegalArgumentException("Matrix Rows are not consistent with the Matrix Columns. i.e: Matrix rows are not the same size");
            }
        }
        this.matrix = matrix;
    }

    public String printMatrix() {
        StringBuilder print = new StringBuilder();
        int padding = 0;

        for (ArrayList<Double> row : matrix) {
            for (Double element: row) {
                String digits = String.valueOf(element);
                if (padding < digits.length()) {
                    padding = digits.length();
                }
            }
        }

        padding += 3;

        for (ArrayList<Double> row : matrix) {
            for (Double element: row) {
                print.append(String.format("%-"+padding+"s",element));
            }
            print.append("\n");
        }
        return print.toString();
    }

    public double getElement(int i, int j) {
        return matrix.get(i).get(j);
    }

    public int getRowNum() {
        return matrix.size();
    }

    public int getColumnNum() {
        return matrix.get(0).size();
    }


    // recursive algorithm definition
    // go through first row
    // first element * | sgn | * | smaller mtrx | + second ...
    // base if 2x2 matrix. det = [0][0]*[1][1] + [0][1]*[1][0]
    public double getDeterminate() {
        if (matrix.size() != matrix.get(0).size()) {
            throw new IllegalArgumentException("Matrix is not a square matrix");
        }

        // base case
        if (matrix.size() == 2 && matrix.get(0).size() == 2) {
            return getElement(0, 0) * getElement(1, 1) - getElement(0, 1) * getElement(1, 0);
        }

        double determinate = 0;

        for (int i = 0; i < matrix.size(); i++) {
            determinate += getElement(0, i) * Math.pow(-1, 1 + i + 1) * minorMatrix(0, i).getDeterminate();
        }

        return determinate;
    }

    public Matrix minorMatrix(int row, int column) {
        ArrayList<ArrayList<Double>> newMatrix = new ArrayList<>();

        for (int i = 0; i < getRowNum(); i++) {
            if (i != row) {
                ArrayList<Double> newRow = new ArrayList<>();
                for (int j = 0; j < getColumnNum(); j++) {
                    if (j != column) {
                        newRow.add(getElement(i, j));
                    }
                }
                newMatrix.add(newRow);
            }
        }
        return new Matrix(newMatrix);
    }

    // guassian reduction
    // algorithm
    // make column element to be 0 except the first diagonal. Using valid row reduction operations (1. swap, 2. add row to another row, 3, multiply row by scalar)
    // continue along the diagonal until the end
    // known problem. If too big sometimes the numbers will go to infinity or 0 and mess up the calculation. Maybe some scalling rules will solve the problem
    public void guassianMatrix() {
        int pivots = Math.min(getRowNum(), getColumnNum());

        for (int i = 0; i < pivots; i++) {
            double pivotElement = getElement(i, i);
            for (int j = i+1; j < getRowNum(); j++) {
                double scalar = getScalarReduction(pivotElement, getElement(j, i));
                addRowToAnother(i, j, scalar);
            }
        }
    }

    public Matrix deepCopy() {
        ArrayList<ArrayList<Double>> newMatrix = new ArrayList<>();
        for (int i = 0; i < getRowNum(); i++) {
            ArrayList<Double> newRow = new ArrayList<>();
            for (int j = 0; j < getColumnNum(); j++) {
                newRow.add(getElement(i, j));
            }
            newMatrix.add(newRow);
        }
        return new Matrix(newMatrix);
    }

    public void swapRows(int row1, int row2) {
        ArrayList<Double> temp = matrix.get(row1);
        matrix.set(row1, matrix.get(row2));
        matrix.set(row2, temp);
    }

    //row2 + row1 * scalar
    public void addRowToAnother(int row1, int row2, double scalar) {
        for (int i = 0; i < getColumnNum(); i++) {
            double element1 = getElement(row1, i);
            double element2 = getElement(row2, i);
            double newElement = element2 + scalar * element1;
            matrix.get(row2).set(i, newElement);
        }
    }

    public void multiplyRow(int row, double scalar) {
        for (int i = 0; i < getColumnNum(); i++) {
            double newElement = getElement(row, i)*scalar;
            matrix.get(row).set(i, newElement);
        }
    }

    // determine scalar such that row below is zero
    private double getScalarReduction(double pivot, double num) {
        return -num/pivot;
    }
}
