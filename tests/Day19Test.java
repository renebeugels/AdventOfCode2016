import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Day19Test {
    @Test
    public void solveA() throws Exception {
        Day19 problem = new Day19();
        assertEquals(problem.solveA(1), 1);
        assertEquals(problem.solveA(2), 1);
        assertEquals(problem.solveA(3), 3);
        assertEquals(problem.solveA(4), 1);
        assertEquals(problem.solveA(5), 3);
    }

    @Test
    public void solveB() throws Exception {
        Day19 problem = new Day19();
        assertEquals(problem.solveB(1), 1);
        assertEquals(problem.solveB(2), 1);
        assertEquals(problem.solveB(3), 3);
        assertEquals(problem.solveB(4), 1);
        assertEquals(problem.solveB(5), 2);
    }

}