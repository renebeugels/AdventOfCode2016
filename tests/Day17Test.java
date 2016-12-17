import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Day17Test {
    @Test
    public void solve() throws Exception {
        Day17 problem = new Day17(4, 4);
        // test shortest path
        assertEquals(problem.solve("hijkl", true), "IMPOSSIBLE");
        assertEquals(problem.solve("ihgpwlah", true), "DDRRRD");
        assertEquals(problem.solve("kglvqrro", true), "DDUDRLRRUDRD");
        assertEquals(problem.solve("ulqzkmiv", true), "DRURDRUDDLLDLUURRDULRLDUUDDDRR");
        // test longest path
        assertEquals(problem.solve("ihgpwlah", false).length(), 370);
        assertEquals(problem.solve("kglvqrro", false).length(), 492);
        assertEquals(problem.solve("ulqzkmiv", false).length(), 830);
    }
}