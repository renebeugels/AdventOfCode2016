import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Day21Test {
    @Test
    public void transform() throws Exception {
        Day21 problem = new Day21();
        String input = "abcde";
        input = problem.transform(input, "swap position 4 with position 0");
        assertEquals("ebcda", input);
        input = problem.transform(input, "swap letter d with letter b");
        assertEquals("edcba", input);
        input = problem.transform(input, "reverse positions 0 through 4");
        assertEquals("abcde", input);
        input = problem.transform(input, "rotate left 1 step");
        assertEquals("bcdea", input);
        input = problem.transform(input, "move position 1 to position 4");
        assertEquals("bdeac", input);
        input = problem.transform(input, "move position 3 to position 0");
        assertEquals("abdec", input);
        input = problem.transform(input, "rotate based on position of letter b");
        assertEquals("ecabd", input);
        input = problem.transform(input, "rotate based on position of letter d");
        assertEquals("decab", input);
    }
}