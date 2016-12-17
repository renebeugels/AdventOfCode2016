import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by rene on 15-12-16.
 */
public class Day15Test {
    @Test(timeout=10)
    public void solve() throws Exception {
        Day15 problem = new Day15();
        assertEquals(problem.solve(new long[] {4, 1}, new long[] {5, 2}), 5);
        assertEquals(problem.solve(new long[] {0, 0, 1}, new long[] {2, 3, 5}), 1);
        assertEquals(problem.solve(new long[] {5, 16, 2}, new long[] {7, 19, 6}), 1);
        assertEquals(problem.solve(new long[] {0, 0, 1, 2,  5,  6, 9,  10, 13, 18, 19},
                                   new long[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31}), 1);

    }

}