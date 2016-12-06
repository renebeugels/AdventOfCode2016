import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Day2 {
    public static void main(String[] args) throws FileNotFoundException {
        Day2 problem = new Day2();
    }

    String[] numbersA = new String[] {"123", "456", "789"};
    String[] numbersB = new String[] {"..1..", ".234.", "56789", ".ABC.", "..D.."};
    public Day2() throws FileNotFoundException {
        BufferedReader in = new BufferedReader(new FileReader("input/day2.txt"));
        Scanner scan = new Scanner(in);
        String answer = "";
        // select part a or b of problem here
        String[] numbers = numbersB;
        int width = numbers[0].length();
        int height = numbers.length;
        int row = 2, col = 0;
        while ( scan.hasNext() ) {
            String s = scan.next();
            for ( int i = 0; i < s.length(); i++) {
                int nRow = row;
                int nCol = col;
                switch ( s.charAt(i) ) {
                    case 'R' : nCol++; break;
                    case 'L' : nCol--; break;
                    case 'D' : nRow++; break;
                    case 'U' : nRow--; break;
                }
                if ( nCol >= 0 && nCol < width && nRow >= 0 && nRow < height &&
                        numbers[nRow].charAt(nCol) != '.') {
                    row = nRow;
                    col = nCol;
                }
            }
            answer += numbers[row].charAt(col);
        }
        System.out.println(answer);
    }
}
