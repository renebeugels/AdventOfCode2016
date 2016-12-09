import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by rene on 09-12-16.
 */
public class Day9Test {
    @Test
    public void solve() throws Exception {
        Day9 problem = new Day9();
        assertEquals(problem.solve("ADVENT", false), 6);
        assertEquals(problem.solve("A(1x5)BC", false), 7);
        assertEquals(problem.solve("(3x3)XYZ", false), 9);
        assertEquals(problem.solve("A(2x2)BCD(2x2)EFG", false), 11);
        assertEquals(problem.solve("(6x1)(1x3)A", false), 6);
        assertEquals(problem.solve("X(8x2)(3x3)ABCY", false), 18);
        assertEquals(problem.solve("A(5x5)B", false), 6);
        // with recursive calls
        assertEquals(problem.solve("(3x3)XYZ", true), 9);
        assertEquals(problem.solve("X(8x2)(3x3)ABCY", true), 20);
        assertEquals(problem.solve("(27x12)(20x12)(13x14)(7x10)(1x12)A", true), 241920);
        assertEquals(problem.solve("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN", true), 445);
        assertEquals(problem.solve("(6x2)A(6x2)", true), 2);
    }

    @Test
    public void solve2() throws Exception {
        Day9 problem = new Day9();
        assertEquals(problem.solve("A(5x5)B", false), 6);

    }

}