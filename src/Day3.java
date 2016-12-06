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
        /* The list of numbers is a sequence of blocks of the form
         * a_1...a_step|b_1...b_step|c_1...c_step
         * The triples in it are (a_i, b_i, c_i)
         * In part a step = 1, in part b step = 3
         */
        int step = 3;
        int indexA = 1;
        for ( int k = 0; k + 2*step < numbers.size(); ) {
            for ( int i = 0; i < 3; i++ ) sides[i] = numbers.get(k + i*step);
            Arrays.sort(sides);
            if ( sides[0] + sides[1] > sides[2] ) answer++;
            k++;
            indexA++;
            if ( indexA > step ) {
                // We got to the b's, move forward
                k += 2*step;
                indexA = 1;
            }
        }
        System.out.println(answer);
    }
}
