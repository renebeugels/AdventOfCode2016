import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day22 {
    public static void main(String[] args) throws FileNotFoundException {
        Day22 problem = new Day22();
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day22.txt")));
        System.out.printf("result %d\n", problem.solve(scan));
    }

    public int solve(Scanner scan) {
        Pattern pattern = Pattern.compile("x(\\d+)-y(\\d+)\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)%");
        ArrayList<Node> nodes = new ArrayList<>();
        while ( scan.hasNext() ) {
            Matcher matcher = pattern.matcher(scan.nextLine());
            while ( matcher.find() ) {
                int[] v = IntStream.range(0, 6)
                                   .map(i -> Integer.valueOf(matcher.group(i + 1)))
                                   .toArray();
                nodes.add(new Node(v[0], v[1], v[2], v[3], v[4]));
            }
        }
        int answer = 0;
        for ( Node node: nodes) {
            answer += nodes.stream()
                           .filter(x -> x.used > 0 && x.used <= node.available && x != node)
                           .count();
        }
        // print grid to solve it manually...
        Node[][] grid = new Node[30][34];
        for ( Node node: nodes ) grid[node.y][node.x] = node;
        for ( int i = 0; i < grid.length; i++) {
            for ( Node node: grid[i]) System.out.printf("%6s ", node);
            System.out.println();
        }
        return answer;
    }

    class Node {
        int x, y, size, used, available;
        public Node(int x, int y, int size, int used, int available) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.used = used;
            this.available = available;
        }
        @Override
        public String toString() {
            return String.format("%d/%d", used, size);
        }
    }

}
