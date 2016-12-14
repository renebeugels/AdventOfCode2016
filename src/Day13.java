import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.IntBinaryOperator;

public class Day13 {
    public static void main(String[] args) {
        Day13 problem = new Day13();
        // part A
        System.out.printf("shortest distance %d\n", problem.solve(31, 39, 1358, Integer.MAX_VALUE));
        // part B
//        System.out.printf("shortest distance %d\n", problem.solve(31, 39, 1358, 50));
    }

    public int solve(int targetX, int targetY, int favoriteNumber, int maxDist) {
        IntBinaryOperator f = (x, y) -> x*x + 3*x + 2*x*y + y + y*y;
        Pair start = new Pair(1, 1);
        Pair target = new Pair(targetX, targetY);
        LinkedList<Pair> todo = new LinkedList<>();
        HashMap<Pair, Integer> distance = new HashMap<>();
        todo.add(start);
        distance.put(start, 0);
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        while ( todo.size() > 0 ) {
            Pair p = todo.poll();
            if ( distance.get(p) >= maxDist ) continue;
            if ( p.x == targetX && p.y == targetY ) break;
            for ( int i = 0; i < 4; i++) {
                Pair next = new Pair(p.x + dx[i], p.y + dy[i]);
                if ( next.isValid() && !distance.containsKey(next) &&
                     Integer.bitCount(f.applyAsInt(next.x, next.y) + favoriteNumber) % 2 == 0 ) {
                    todo.add(next);
                    distance.put(next, distance.get(p) + 1);
                }
            }
        }
        System.out.println(distance.size());
        return distance.getOrDefault(target, -1);
    }

    class Pair {
        int x, y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public boolean isValid() {
            return x >= 0 && y >= 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return x == pair.x && y == pair.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

}
