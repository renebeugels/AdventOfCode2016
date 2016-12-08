import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day8 {
    public static void main(String[] args) throws FileNotFoundException {
        Day8 problem = new Day8();
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day8.txt")));
        long result = problem.solve(scan);
        System.out.printf("totals pixels: %d\n", result);
    }

    public long solve(Scanner scan) {
        int height = 6, width = 50;
        Stream<Pixel> allPixels = Stream.of();
        while ( scan.hasNext() ) {
            String command = scan.nextLine();
            Scanner numberScan = new Scanner(command.replaceAll("\\D", " "));
            int a = numberScan.nextInt(), b = numberScan.nextInt();
            if ( command.contains("rect") ) {
                allPixels = Stream.concat(allPixels,
                        IntStream.range(0, a*b).mapToObj(i -> new Pixel(i/b, i % b))).distinct();
            } else if ( command.contains("row y")) {
                allPixels = allPixels.peek(p -> { if (p.y == a) p.x = (p.x + b) % width; });
            } else allPixels = allPixels.peek(p -> { if (p.x == a) p.y = (p.y + b) % height; });
        }
        char[][] screen = new char[height][width];
        for ( char[] a: screen ) Arrays.fill(a, '.');
        long result = allPixels.peek(p -> screen[p.y][p.x] = '#').count();
        for ( char[] a: screen ) System.out.println(new String(a));
        return result;
    }

    public class Pixel {
        int x, y;
        public Pixel(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() { return String.format("(%d, %d)", x, y); }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pixel pixel = (Pixel) o;

            return x == pixel.x && y == pixel.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}


