import java.util.Arrays;

/**
 * Open-Close doors/lockers problem
 */
public class OpenCloseDoors {

    public static void eval(int n) {
        final boolean[] doors = new boolean[n+1];
        init(doors);
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j <= n; j += i) {
                doors[j] = !doors[j];
            }
        }
        print(doors);
    }

    private static void init(boolean[] doors) {
        for (int i = 1; i < doors.length; ++i) {
            doors[i] = false;
        }
    }

    private static void print(boolean[] doors) {
        for (int i = 1; i < doors.length; ++i) {
            if (doors[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public static void main(final String[] args) {
        eval(100);
    }
}
