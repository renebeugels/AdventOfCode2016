import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.Scanner;

public class Day18 {
    public static void main(String[] args) throws FileNotFoundException {
        Day18 problem = new Day18();
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day18.txt")));
        System.out.printf("safe tiles %d\n", problem.solve(scan.next(), 400000));
    }

    public int solve(String input, int rows) {
        int n = input.length();
        BigInteger row = new BigInteger(input.replace('^', '1').replace('.', '0'), 2);
        BigInteger all = BigInteger.valueOf(2).pow(n).subtract(BigInteger.ONE);
        int safeTiles = 0;
        for ( int i = 0; i < rows; i++) {
            safeTiles += n - row.bitCount();
            BigInteger rowSR = row.shiftRight(1);
            BigInteger rowSL = row.shiftLeft(1);
            BigInteger next = row.and(rowSR).and(rowSL.not());
            next = next.or(row.and(rowSR.not()).and(rowSL));
            next = next.or(row.not().and(rowSR).and(rowSL.not()));
            next = next.or(row.not().and(rowSR.not()).and(rowSL));
            row = next.and(all);
        }
        return safeTiles;
    }
}
