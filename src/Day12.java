import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;


public class Day12 {
    public static void main(String[] args) throws FileNotFoundException {
        Day12 problem = new Day12();
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day12.txt")));
        System.out.printf("output %d\n", problem.solve(scan));
    }

    public int solve(Scanner scan) {
        class State {
            int[] register = new int[4];
            int index;
            @Override
            public String toString() {
                return String.format("index %d, registers %s", index, Arrays.toString(register));
            }
        }
        final State state = new State();
        // initialization for part B
//        state.register[2] = 1;

        ArrayList<Consumer<State>> instructions = new ArrayList<Consumer<State>>();

        while ( scan.hasNext() ) {
            String[] parts = scan.nextLine().split("\\s+");
            int registerIndex = "abcd".indexOf(parts[1]);
            switch ( parts[0] ) {
                case "cpy" :
                    int target = "abcd".indexOf(parts[2]);
                    if ( registerIndex >= 0 ) {
                        instructions.add(s -> {s.register[target] = s.register[registerIndex]; s.index++; });
                    } else {
                        int value = Integer.parseInt(parts[1]);
                        instructions.add(s -> {s.register[target] = value; s.index++; });
                    }
                    break;
                case "inc" :
                    instructions.add(s -> {s.register[registerIndex]++; s.index++;});
                    break;
                case "dec" :
                    instructions.add(s -> {s.register[registerIndex]--; s.index++;});
                    break;
                case "jnz" :
                    int jump = Integer.parseInt(parts[2]);
                    if ( registerIndex >= 0 ) {
                        instructions.add(s -> {if ( s.register[registerIndex] != 0 ) s.index += jump; else s.index++;});
                    } else {
                        int value = Integer.parseInt(parts[1]);
                        instructions.add(s -> { if ( value != 0 ) s.index += jump; else s.index++; });
                    }
                    break;
            }
        }
        while ( state.index >= 0 && state.index < instructions.size() ) {
            instructions.get(state.index).accept(state);
        }
        return state.register[0];
    }
}
