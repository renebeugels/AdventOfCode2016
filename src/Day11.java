import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day11 {
    public static void main(String[] args) throws FileNotFoundException {
        Day11 problem = new Day11();
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day11.txt")));
        System.out.printf("number of steps %d\n", problem.solve(scan));
    }

    public int solve(Scanner scan) {
        int floor = 0;
        Pattern pattern = Pattern.compile("([-\\w]+) (microchip|generator)");
        HashMap<String, Integer> floorMap = new HashMap<>();
        while ( scan.hasNext() ) {
            Matcher matcher = pattern.matcher(scan.nextLine());
            while ( matcher.find()) floorMap.put(matcher.group(1), floor);
            floor++;
        }
        State start = setup(floorMap);
        start.canonical();

        HashMap<State, Integer> distance = new HashMap<>();
        LinkedList<State> todo = new LinkedList<>();
        distance.put(start, 0);
        todo.add(start);
        State goal = new State();
        goal.elevator = 3;
        goal.mask[3] = (1<<n) - 1;
        long now = System.currentTimeMillis();
        while ( todo.size() > 0 ) {
            State s = todo.poll();
            int currentDistance = distance.get(s);
            if ( s.equals(goal) ) break;
            int mask = s.mask[s.elevator];
            for ( int i = 0; i < n; i++ ) {
                if ( (1 << i & mask) == 0 ) continue;
                for ( int j = i; j < n; j++ ) {
                    if ( (1 << j & mask) == 0 ) continue;
                    int delta = (1 << i) | (1 << j);
                    boolean isPair = i % 2 == 0 && j == i + 1;
                    /* If we denote generators by capitals and their chips by the lowercase character
                     * and if we have items aA bB cC dD ...  then we only need to consider the transitions
                     * - a
                     * - A  : a might get fried, but mask check will handle that
                     * - aA : always possible to do
                     * - aB : useless because b gets fried by A, but will be caught by mask check
                     * - AB : only allowed if there are no other generators X, but will be caught by mask check
                     */
                    for ( int nextElevator = s.elevator - 1; nextElevator <= s.elevator + 1; nextElevator += 2) {
                        if ( 0 <= nextElevator && nextElevator < NR_FLOORS) {
                            // Optimization: check new bitmasks before creating a new state
                            if ( !check(s.mask[s.elevator] ^ delta) ||
                                 !check(s.mask[nextElevator] ^ delta)) continue;
                            State next = new State(s);
                            next.elevator = nextElevator;
                            next.mask[s.elevator] ^= delta;
                            next.mask[nextElevator] ^= delta;
                            next.canonical();
                            if ( distance.put(next, currentDistance + 1) == null ) {
                                todo.add(next);
                            }
                        }
                    }
                }
            }
        }
        System.out.printf("distance %s\n", distance.getOrDefault(goal, -1));
        System.out.println("distance size " + distance.size());
        System.out.println("elapsed " + (System.currentTimeMillis() - now));
        return distance.getOrDefault(goal, -1);
    }

    private State setup(HashMap<String, Integer> floorMap) {
        items = floorMap.keySet().stream().sorted().toArray(size -> new String[size]);
        n = items.length;
        // chips are now at odd positions, generators at even positions
        for ( int i = 0; i < n; i += 2 ) generatorMask |= 1 << i;
        // setup initial state
        State start = new State();
        for ( int i = 0; i < n; i++) {
            start.mask[floorMap.get(items[i])] |= 1 << i;
        }
        return start;
    }

    String[] items;
    int generatorMask;
    int n;
    /* Return true iff all items specified by the bitmask
     * can coexist together. That is possible if one of the
     * following conditions hold:
     * - there are no generators
     * - each chip is connected to its generator
     */
    public boolean check(int mask)  {
        boolean noGenerators = (mask & generatorMask) == 0;
        int needGenerators = mask/2 & generatorMask;
        boolean allChipsConnected = (mask & needGenerators) == needGenerators;
        return noGenerators | allChipsConnected;
    }

    final int NR_FLOORS = 4;
    public class State {
        int elevator;
        int[] mask;

        public State() {
            mask = new int[NR_FLOORS];
        }
        public State(State s) {
            elevator = s.elevator;
            mask = Arrays.copyOf(s.mask, NR_FLOORS);
        }

        /* The state can be described by a list of tuples
         * (floor chip, floor generator, element name).
         * The distance to the final state (everything on top floor)
         * does not depend on the element names. This means that two
         * states are equivalent if their list of their floor pairs
         * are the same. This can be done easily if the pairs are
         * in sorted order. This method does that.
         */
        void canonical() {
            Pair[] repr = representation();
            Arrays.sort(repr);
            Arrays.fill(mask, 0);

            for ( int i = 0; i < repr.length; i++) {
                mask[repr[i].generator] |= 1 << (2*i);
                mask[repr[i].chip] |= 1 << (2*i+1);
            }
        }

        Pair[] representation() {
            Pair[] result = new Pair[n/2];
            for ( int i = 0; i < n/2; i++) {
                int gen = 0, chip = 0;
                for ( int j = 0; j < NR_FLOORS; j++) {
                    if ( (1<<(2*i) & mask[j]) != 0 ) gen = j;
                    if ( (1<<(2*i+1) & mask[j]) != 0 ) chip = j;
                }
                result[i] = new Pair(gen,chip);
            }
            return result;
        }

        class Pair implements Comparable<Pair> {
            int generator, chip;
            public Pair(int g, int c) {
                generator = g; chip = c;
            }

            @Override
            public String toString() { return String.format("(%d, %d)", generator, chip); }

            @Override
            public int compareTo(Pair o) {
                if ( generator != o.generator) return generator - o.generator;
                return chip - o.chip;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            if (elevator != state.elevator) return false;
            return Arrays.equals(mask, state.mask);

        }

        @Override
        public int hashCode() {
            int result = elevator;
            result = 31 * result + Arrays.hashCode(mask);
            return result;
        }
    }
}
