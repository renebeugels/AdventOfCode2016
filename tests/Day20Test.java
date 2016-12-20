import org.junit.Test;

import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;


public class Day20Test {
    @Test
    public void solve() throws Exception {
        Day20 problem = new Day20();
        long[] result = problem.solve(new Scanner("5-8\n0-2\n4-7"), 10);
        assertEquals(3, result[0]);
        assertEquals(3, result[1]);
        result = problem.solve(new Scanner("2-4\n5-7\n6-9"), 10);
        assertEquals(0, result[0]);
        assertEquals(3, result[1]);
        result = problem.solve(new Scanner("0-9\n3-3\n6-9"), 10);
        assertEquals(10, result[0]);
        assertEquals(1, result[1]);
    }
}