import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day3 {
    public static void main(String[] args) throws FileNotFoundException {
        Day3 problem = new Day3();
    }

    public Day3() throws FileNotFoundException {
        BufferedReader in = new BufferedReader(new FileReader("input/day3.txt"));
        Scanner scan = new Scanner(in);
        int[] sides = new int[3];
        int answer = 0;
        ArrayList<Integer> numbers = new ArrayList<>();
        while ( scan.hasNext() ) numbers.add(scan.nextInt());
        /* triples are at positions i, i + step, i + 2*step
         * part a: step = 1
         * part b: step = 3
         */
        int step = 1;
        boolean[] done = new boolean[numbers.size()];
        for ( int k = 0; k + 2*step < numbers.size(); k++) {
            if ( done[k] ) continue;
            for ( int i = 0; i < 3; i++ ) {
                sides[i] = numbers.get(k + i*step);
                done[k + i*step] = true;
            }
            Arrays.sort(sides);
            if ( sides[0] + sides[1] > sides[2] ) answer++;
        }
        System.out.println(answer);
    }
}
