import org.junit.Test;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class Day24Test {
    @Test
    public void solve() throws Exception {
        Day24 problem = new Day24();
        String[] map = {"###########",
                        "#0.1.....2#",
                        "#.#######.#",
                        "#4.......3#",
                        "###########"};
        Function<String[], String> join = list -> Arrays.stream(list)
                                                        .reduce((a,b) -> a + "\n" + b)
                                                        .get();
        assertEquals(14, problem.solve(new Scanner(join.apply(map)), false));
        assertEquals(20, problem.solve(new Scanner(join.apply(map)), true));
        map = new String[]{"#########",
                           "#0.1.2.3#",
                           "#########"};
        assertEquals(6, problem.solve(new Scanner(join.apply(map)), false));
        assertEquals(12, problem.solve(new Scanner(join.apply(map)), true));

        map = new String[]{"#####",
                           "##1##",
                           "#203#",
                           "##4##",
                           "#####"};
        assertEquals(7, problem.solve(new Scanner(join.apply(map)), false));
        assertEquals(8, problem.solve(new Scanner(join.apply(map)), true));
    }
}