import org.junit.Test;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;

public class Day12Test {
    @Test
    public void solve() throws Exception {
        Day12 problem = new Day12();
        String input = Stream.of("cpy 41 a", "inc a", "inc a",
                "dec a", "jnz a 2", "dec a")
                .collect(Collectors.joining("\n"));
        assertEquals(problem.solve(new Scanner(input)), 42);
        input = Stream.of("cpy 50 d", "inc c", "dec d", "jnz d -2", "cpy c a")
                .collect(Collectors.joining("\n"));
        assertEquals(problem.solve(new Scanner(input)), 50);
        input = Stream.of("cpy 25 b", "jnz 2 3", "dec c", "dec a", "cpy b a")
                .collect(Collectors.joining("\n"));
        assertEquals(problem.solve(new Scanner(input)), 25);
    }
}