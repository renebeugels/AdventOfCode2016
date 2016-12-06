import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Day1 {
    public static void main(String[] args) throws IOException {
        Day1 problem = new Day1();
    }

    public Day1() throws IOException {
        String input = new BufferedReader(new FileReader("input/day1.txt")).readLine();
        Scanner scan = new Scanner(input.replaceAll(",", ""));
        // x and y changes for directions NESW
        int[] dx = new int[]{0, 1, 0, -1};
        int[] dy = new int[]{1, 0, -1, 0};
        int dir = 0;
        int x = 0, y = 0;
        HashSet<Pair> visited = new HashSet<>();
        visited.add(new Pair(0,0));
        Pair firstRevisited = null;
        while ( scan.hasNext() ) {
            String s = scan.next();
            if ( s.charAt(0) == 'R') dir = (dir+1) % 4;
            else dir = (dir+3) % 4;
            int d = Integer.valueOf(s.substring(1));
            for ( int i = 0; i < d; i++) {
                x += dx[dir];
                y += dy[dir];
                Pair next = new Pair(x, y);
                if ( visited.contains(next) && firstRevisited == null ) firstRevisited = next;
                visited.add(next);
            }
        }
        System.out.printf("end point x = %d y = %d\n", x, y);
        System.out.println("total distance " + (Math.abs(x) + Math.abs(y)));
        System.out.println("first revisited point " + firstRevisited);
    }

    public class Pair {
        int x, y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("(%d, %d) dist %d", x, y, Math.abs(x) + Math.abs(y));
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
