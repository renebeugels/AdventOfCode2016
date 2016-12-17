import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.PriorityQueue;

public class Day17 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Day17 problem = new Day17(4, 4);
        String path = problem.solve("udskfozm", true);
        System.out.printf("length %d path %s\n", path.length(), path);
        System.out.printf("longest path %d\n", problem.solve("udskfozm", false).length());
    }

    int MAX_X = 4, MAX_Y = 4;
    public Day17(int maxX, int maxY) {
        MAX_X = maxX;
        MAX_Y = maxY;
    }

    public String solve(String input, boolean shortest) throws NoSuchAlgorithmException {
        int[] dx = new int[] {0, 0, -1, 1};
        int[] dy = new int[] {-1, 1, 0, 0};

        MessageDigest md = MessageDigest.getInstance("MD5");
        // A* algorithm with manhattan distance to (MAX_X, MAX_Y) as lower bound
        PriorityQueue<State> todo = new PriorityQueue<>((a, b) -> a.path.length() - a.x - a.y -
                                                                  b.path.length() + b.x + b.y);
        todo.add(new State(1, 1, ""));
        String answer = "IMPOSSIBLE";

        while ( todo.size() > 0 ) {
            State s = todo.poll();
            byte[] hash = md.digest((input + s.path).getBytes());
            String doors = String.format("%02x%02x", hash[0], hash[1]);
            if ( s.x == MAX_X && s.y == MAX_Y ) {
                answer = s.path;
                if ( shortest ) break;
                else continue;
            }
            for ( int i = 0; i < 4; i++ ) {
                int nextX = s.x + dx[i];
                int nextY = s.y + dy[i];
                if (doors.charAt(i) >= 'b' &&
                        nextX >= 1 && nextX <= MAX_X && nextY >= 1 && nextY <= MAX_Y) {
                    todo.add(new State(nextX, nextY, s.path + "UDLR".charAt(i)));
                }
            }
        }
        return answer;
    }

    class State {
        int x, y;
        String path;
        public State(int x, int y, String path) {
            this.x = x;
            this.y = y;
            this.path = path;
        }
    }


}
