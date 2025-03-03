import java.util.ArrayList;

public class Matrix {
    private final double[][] matrix;
    private int rows;
    private int columns;

    public Matrix(double[][] matrix) {
        this.columns = matrix[0].length;
        this.rows = matrix.length;

        for (int i = 1; i < matrix.length; i++) {
            if (this.columns != matrix[i].length) {
                throw new IllegalArgumentException("Matrix Rows are not consistent with the Matrix Columns. i.e: Matrix rows are not the same size");
            }
        }
        this.matrix = matrix;
    }

    public String printMatrix() {
        StringBuilder print = new StringBuilder();
        int padding = 0;

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                String digits = String.valueOf(this.matrix[i][j]);
                if (padding < digits.length()) {
                    padding = digits.length();
                }
            }
        }

        padding += 3;

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                print.append(String.format("%-"+padding+"s",matrix[i][j]));
            }
            print.append("\n");
        }
        return print.toString();
    }

    // recursive algorithm definition
    // go through first row
    // first element * | sgn | * | smaller mtrx | + second ...
    // base if 2x2 matrix. det = [0][0]*[1][1] + [0][1]*[1][0]
    public double getDeterminate() {
        if (this.rows != this.columns) {
            throw new IllegalArgumentException("Matrix is not a square matrix");
        }

        // base case
        if (this.rows == 2) {
            return this.matrix[0][0] * this.matrix[1][1] - this.matrix[0][1] * this.matrix[1][0];
        }

        double determinate = 0;

        for (int i = 0; i < this.columns; i++) {
            determinate += this.matrix[0][i] * Math.pow(-1, 1 + i + 1) * minorMatrix(0, i).getDeterminate();
        }

        return determinate;
    }

    public Matrix minorMatrix(int row, int column) {
        int newRows = this.rows-1;
        int newColumns = this.columns-1;
        double[][] newMatrix = new double[newRows][newColumns];

        int index_i = 0;
        int index_j = 0;
        for (int i = 0; i < this.rows; i++) {
            if (i != row) {
                double[] newRow = new double[newColumns];
                for (int j = 0; j < this.columns; j++) {
                    if (j != column) {
                        newRow[index_j] = this.matrix[i][j];
                        index_j++;
                    }
                }
                index_j = 0;
                newMatrix[index_i] = newRow;
                index_i++;
            }
        }
        return new Matrix(newMatrix);
    }

    // guassian reduction
    // algorithm
    // make column element to be 0 except the first diagonal. Using valid row reduction operations (1. swap, 2. add row to another row, 3, multiply row by scalar)
    // continue along the diagonal until the end
    // known problem. If too big sometimes the numbers will go to infinity or 0 and mess up the calculation. Maybe some scalling rules will solve the problem
    public void gaussianMatrix() {
        int pivots = Math.min(this.rows, this.columns);

        for (int i = 0; i < pivots; i++) {
//            int largestRow = i;
//            for (int j = i+1; j < getRowNum(); j++) {
//                if (Math.abs(getElement(j, i)) < Math.abs(getElement(largestRow, i))) {
//                    largestRow = j;
//                }
//            }
//            swapRows(largestRow, i);

            double pivotElement = this.matrix[i][i];

            // moves 0 pivot row to bottom and swaps to non-zero pivot. if non-available then skip pivot
            if (pivotElement==0) {
                for(int j = this.rows-1; j > i; j--) {
                    if (this.matrix[j][i] != 0) {
                        swapRows(i, j);
                        break;
                    }
                }
                pivotElement = this.matrix[i][i];
                if (pivotElement==0) {
                    continue;
                }
            }

            for (int j = i+1; j < this.rows; j++) {
                double scalar = getScalarReduction(pivotElement, this.matrix[j][i]);
                addRowToAnother(i, j, scalar);
            }
        }
    }

    public Matrix deepCopy() {
        double[][] newMatrix = new double[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++) {
            double[] newRow = new double[this.columns];
            for (int j = 0; j < this.columns; j++) {
                newRow[j] = this.matrix[i][j];
            }
            newMatrix[i] = newRow;
        }
        return new Matrix(newMatrix);
    }

    public void swapRows(int row1, int row2) {
        if (row1 == row2) {
            return;
        }
        double[] temp = this.matrix[row1];
        this.matrix[row1] = this.matrix[row2];
        this.matrix[row2] = temp;
    }

    //row2 + row1 * scalar
    public void addRowToAnother(int row1, int row2, double scalar) {
        for (int i = 0; i < this.columns; i++) {
            double element1 = this.matrix[row1][i];
            double element2 = this.matrix[row2][i];
            double newElement = element2 + scalar * element1;
            this.matrix[row2][i] = newElement;
        }
    }

    public void multiplyRow(int row, double scalar) {
        for (int i = 0; i < this.columns; i++) {
            double newElement = this.matrix[row][i]*scalar;
            this.matrix[row][i] = newElement;
        }
    }

    // determine scalar such that row below is zero
    private double getScalarReduction(double pivot, double num) {
        return -num/pivot;
    }

    public Matrix transpose() {
        double[][] newMatrix = new double[this.columns][this.rows];

        for (int i = 0; i < this.columns; i++) {
            double[] newRow = new double[this.rows];
            for (int j = 0; j < this.rows; j++) {
                newRow[j] = matrix[j][i];
            }
            newMatrix[i] = newRow;
        }
        return new Matrix(newMatrix);
    }

    public Matrix multiply(Matrix other) {
        if (this.columns != other.rows) {
            throw new IllegalArgumentException("Invalid Matrix dimensions for multiplication");
        }
        double[][] newMatrix = new double[this.rows][other.columns];

        for (int i = 0; i < newMatrix.length; i++) {
            double[] newRow = new double[other.columns];
            for (int j = 0; j < newMatrix[0].length; j++) {
                newRow[j] = 0;
                for (int k = 0; k < this.columns; k++) {
                    newRow[j] += this.matrix[i][k]*other.matrix[k][j];
                }
            }
            newMatrix[i] = newRow;
        }

        return new Matrix(newMatrix);
    }
}
