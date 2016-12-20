import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;


public class Day16Test {
    @Test
    public void expand() throws Exception {
        Day16 problem = new Day16();
        assertEquals("001", problem.expand("0", 1));
        assertEquals("0010011", problem.expand("0", 2));
        assertEquals("001001100011011", problem.expand("0", 3));
    }

    @Test
    public void solve() throws Exception {
        Day16 problem = new Day16();
        assertEquals("01100", problem.solve("10000", 20));
        assertEquals("1001001", problem.solve("1001", 7));

        assertEquals(problem.solveStupid("01001", 33*64),
                     problem.solve("01001", 33*64));
        assertEquals(problem.solveStupid("010110", 57*128),
                problem.solve("010110", 57*128));
    }

    @Test
    public void prefixSum() throws Exception {
        Day16 problem = new Day16();
        int m = 3;
        for ( int k = 1; k <= 4; k++, m = 2*m + 1 ) {
            long prev = 0;
            String result = "";
            for ( int j = 1; j <= m; j++ ) {
                long sum = problem.prefixSum(j, m);
                assertTrue(sum >= prev && sum <= prev + 1);
                result += sum > prev ? "1" : "0";
                prev = sum;
            }
            System.out.printf("k = %d \nresult %s\nexpand %s\n", k, result, problem.expand("0", k));
            assertEquals(problem.expand("0", k), result);
        }
    }

}