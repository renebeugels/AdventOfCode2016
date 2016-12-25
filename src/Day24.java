import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Day24 {
    public static void main(String[] args) throws FileNotFoundException {
        Day24 problem = new Day24();
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day24.txt")));
        System.out.printf("result %d\n", problem.solve(scan, true));
    }

    public int solve(Scanner scan, boolean closed) {
        ArrayList<char[]> tmp = new ArrayList<>();
        while ( scan.hasNext() ) tmp.add(scan.next().toCharArray());
        char[][] map = tmp.toArray(new char[tmp.size()][]);
        // number of locations, assumes that all locations 0,..., n-1 are present
        int n = Arrays.stream(map)
                      .flatMapToInt(row -> IntStream.range(0, row.length)
                                             .filter(i -> Character.isDigit(row[i]))
                                             .map(i -> Character.digit(row[i], 10)))
                      .max().getAsInt() + 1;
        // compute distances between locations
        int[][] dist = new int[n][n];
        int height = map.length, width = map[0].length;
        for ( int row = 0; row < height; row++)
            for ( int col = 0; col < width; col++)
                if ( Character.isDigit(map[row][col])) computeDistance(row, col, map, dist);
        /* dp[mask][i] is shortest distance from 0 to i and having visited
         * all locations specified by mask.
         */
        int[][] dp = new int[1<<n][n];
        for ( int[] a: dp ) Arrays.fill(a, Integer.MAX_VALUE);
        dp[1][0] = 0;
        for ( int mask = 0; mask < 1<<n; mask++)
            for ( int last = 0; last < n; last++ ) {
                if ( dp[mask][last] == Integer.MAX_VALUE ) continue;
                for ( int next = 0; next < n; next++ ) {
                    dp[mask|1<<next][next] = Math.min(dp[mask|1<<next][next],
                                                      dp[mask][last]+dist[last][next]);
                }
            }
        return IntStream.range(0, n)
                        .map(i -> dp[(1<<n)-1][i] + (closed ? dist[i][0] : 0))
                        .min().getAsInt();
    }

    // directions NESW
    final int[] dr = {-1, 0, 1, 0};
    final int[] dc = {0, 1, 0, -1};
    private void computeDistance(int row, int col, char[][] map, int[][] dist) {
        int height = map.length, width = map[0].length;
        int[][] localDist = new int[height][width];
        for ( int[] a: localDist ) Arrays.fill(a, -1);
        LinkedList<Position> todo = new LinkedList<>();
        int source = map[row][col] - '0';
        todo.add(new Position(row, col));
        localDist[row][col] = 0;
        while ( todo.size() > 0 ) {
            Position p = todo.poll();
            if ( Character.isDigit(map[p.row][p.col])) {
                // distance from source to other location has been found
                dist[source][map[p.row][p.col]-'0'] = localDist[p.row][p.col];
            }
            for ( int i = 0; i < 4; i++ ) {
                int nr = p.row + dr[i];
                int nc = p.col + dc[i];
                if ( nr >= 0 && nr < height && nc >= 0 && nc < width &&
                     map[nr][nc] != '#' && localDist[nr][nc] < 0 ) {
                    localDist[nr][nc] = localDist[p.row][p.col] + 1;
                    todo.add(new Position(nr, nc));
                }
            }
        }
    }

    class Position {
        int row, col;
        public Position(int r, int c) {
            row = r; col = c;
        }
    }
}
