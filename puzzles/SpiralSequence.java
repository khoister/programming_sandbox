import java.util.ArrayList;
import java.util.List;


public class SpiralSequence {

    private enum Direction {
        RIGHT(0, 1),
        LEFT(0, -1),
        DOWN(1, 0),
        UP(-1, 0);

        private final int vert;
        private final int horz;

        Direction(int vert, int horz) {
            this.vert = vert;
            this.horz = horz;
        }
    }

    private static Direction dirs[] = {
        Direction.RIGHT,
        Direction.DOWN,
        Direction.LEFT,
        Direction.UP
    };

    /**
     * Returns the sequence of "traversing" an NxN 2D array in a clock-wise,
     * spiral direction.
     *
     * O(n^2) time complexity
     * O(n^2) memory complexity
     */
    public static List<Integer> spiralSequenceOne(int[][] A) {
        final List<Integer> results = new ArrayList<>();
        if (A == null || A.length == 0)
            return results;

        final int N = A.length;
        final int layers = (N + 1) / 2;
        int steps = N - 1;
        int currentDir = 0;
        Direction dir = dirs[currentDir];

        for (int i = 0; i < layers; i++) {
            // At each layer, start at the upper left corner
            int row = i;
            int col = i;
            // For each side of the 2D array
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < steps; k++) {
                    results.add(A[row][col]);
                    row += dir.vert;
                    col += dir.horz;
                }
                // Change direction
                currentDir = (currentDir + 1) % dirs.length;
                dir = dirs[currentDir];
            }
            steps -= 2;
        }

        // For NxN 2D array where N is odd, add the center element
        if (N % 2 != 0) {
            results.add(A[N/2][N/2]);
        }
        return results;
    }

    /**
     * Returns the sequence of "traversing" an NxN 2D array in a clock-wise,
     * spiral direction.
     *
     * O(n^2) time complexity
     * O(2n^2) memory complexity
     */
    public static List<Integer> spiralSequence(int[][] A) {
        final List<Integer> results = new ArrayList<>();
        if (A == null || A.length == 0)
            return results;

        final int N = A.length;

        // O(n^2) memory complexity
        // An alternative is to modify the input 2D array
        boolean visitedMap[][] = new boolean[N][N];

        int currentDir = 0;
        Direction dir = dirs[currentDir];
        int row = 0;
        int col = -1;

        while (results.size() < N * N) {
            int nextRow = row + dir.vert;
            int nextCol = col + dir.horz;

            if (isValid(nextRow, nextCol, N) && !visited(nextRow, nextCol, visitedMap)) {
                row = nextRow;
                col = nextCol;
                results.add(A[row][col]);
                visit(row, col, visitedMap);
            } else {
                // Change direction
                currentDir = (currentDir + 1) % dirs.length;
                dir = dirs[currentDir];
            }
        }
        return results;
    }

    private static boolean isValid(int row, int col, int N) {
        return (row >= 0 && row < N) && (col >= 0 && col < N);
    }

    private static boolean visited(int row, int col, boolean[][] map) {
        return map[row][col];
    }

    private static void visit(int row, int col, boolean[][] map) {
        map[row][col] = true;
    }

    public static void main(final String[] args) {
        int A[][] = {
            {1,   2,  3,  4,  5},
            {6,   7,  8,  9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25}
        };
        if (!spiralSequence(A).equals(spiralSequenceOne(A))) {
            System.out.println("Failure!");
        }
    }
}
