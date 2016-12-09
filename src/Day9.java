import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Day9 {
    public static void main(String[] args) throws FileNotFoundException {
        Day9 problem = new Day9();
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day9.txt")));
        // part A: recursive = false, part B: recursive = true
        long result = problem.solve(scan.next(), false);
        System.out.printf("decompressed length: %d\n", result);
    }

    public long solve(String input, boolean recursive) {
        long result = 0;
        int index = 0;
        while ( index < input.length() ) {
            int nextOpen = input.indexOf('(', index);
            int nextClose = input.indexOf(')', nextOpen);
            if ( nextOpen < 0 || nextOpen >= input.length() ||
                 nextClose < 0 || nextClose >= input.length() ) {
                // rest of line doesn't contain a marker
                result += input.length() - index;
                break;
            } else {
                result += nextOpen - index;
                // Assume that every parenthesized substring is a valid marker
                String marker = input.substring(nextOpen + 1, nextClose);
                Scanner scan = new Scanner(marker).useDelimiter("x");
                // Length can not go past the end of the input
                int length = Math.min(input.length() - nextClose - 1, scan.nextInt());
                int repeat = scan.nextInt();
                result += repeat*(recursive ? solve(input.substring(nextClose + 1, nextClose + length + 1), recursive)
                                            : length);
                index = nextClose + length + 1;
            }
        }
        return result;
    }
}
