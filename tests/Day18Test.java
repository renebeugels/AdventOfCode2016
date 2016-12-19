import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Day18Test {
    @Test
    public void solve() throws Exception {
        Day18 problem = new Day18();
        assertEquals(problem.solve("..^^.", 3), 6);
        assertEquals(problem.solve(".^^.^.^^^^", 10), 38);
    }
}