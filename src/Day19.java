import java.util.ArrayDeque;

public class Day19 {
    public static void main(String[] args) {
        Day19 problem = new Day19();
        System.out.printf("part A %d\n", problem.solveA(3001330));
        System.out.printf("part B %d\n", problem.solveB(3001330));
    }

    public int solveA(int n) {
        ArrayDeque<Integer> elves = new ArrayDeque<>();
        for ( int i = 1; i <= n; i++ ) elves.add(i);
        while ( elves.size() > 1 ) {
            elves.add(elves.poll());
            elves.pop();
        }
        return elves.peek();
    }

    public int solveB(int n) {
        ArrayDeque<Integer> elves = new ArrayDeque<>();
        for ( int i = n/2 + 2; i <= n; i++ ) elves.add(i);
        for ( int i = 1; i <= n/2 + 1; i++ ) elves.add(i);
        while ( elves.size() > 1 ) {
            elves.pollLast();
            elves.add(elves.poll());
            if ( elves.size() % 2 == 0 ) elves.add(elves.poll());
        }
        return elves.peek();
    }
}
