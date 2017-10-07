/**
 * Print diagonals start from SW going to NE
 */
public class Diagonals {
    public static final int UP = -1;
    public static final int DOWN = 1;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    public static void print(final int[][] m, int rows, int cols) {
        // Start at the bottom SW corner of the matrix
        int lastRow = rows - 1;
        int firstCol = 0;
        while (lastRow >= 0) {
            printDiagonal(m, lastRow, firstCol, rows, cols);
            lastRow += UP;
        }

        int firstRow = 0;
        int col = 1;
        while (col < cols) {
            printDiagonal(m, firstRow, col, rows, cols);
            col += RIGHT;
        }
    }

    private static void printDiagonal(final int[][] m, int currRow, int currCol, int rowLength, int colLength) {
        while (currRow < rowLength && currCol < colLength) {
            System.out.print(m[currRow][currCol] + " ");
            currRow += DOWN;
            currCol += RIGHT;
        }
        System.out.println();
    }

    public static void main(final String[] args) {
        final int[][] m = {
            { 1,  2,  3,  4 },
            { 5,  6,  7,  8 },
            { 9, 10, 11, 12 }
        };
        print(m, m.length, m[0].length);

        final int[][] n = {
                { 1,   2,  3,  4 },
                { 5,   6,  7,  8 },
                { 9,  10, 11, 12 },
                { 13, 14, 15, 16 }
        };
        print(n, n.length, n[0].length);

        final int[][] o = {
                {  1,  2, },
                {  3,  4, },
                {  5,  6, },
                {  7,  8, },
                {  9, 10, },
                { 11, 12  }
        };
        print(o, o.length, o[0].length);
    }
}
