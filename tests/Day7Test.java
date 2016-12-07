import org.junit.Test;

import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class Day7Test {
    @Test
    public void solve() throws Exception {
        Day7 problem = new Day7();
        assertTrue(Arrays.equals(problem.solve(new Scanner("abba[cdef]aba")),
                                 new int[]{1, 0}));
        assertTrue(Arrays.equals(problem.solve(new Scanner("abba[bab]aba")),
                                 new int[]{1, 1}));
        assertTrue(Arrays.equals(problem.solve(new Scanner("xxxx[abce]yxyy")),
                                 new int[]{0, 0}));
        assertTrue(Arrays.equals(problem.solve(new Scanner("abbacdc[cd][cddcd]")),
                                 new int[]{0, 1}));
    }
}