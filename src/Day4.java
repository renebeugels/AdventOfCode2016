import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class Day4 {
    public static void main(String[] args) throws FileNotFoundException {
        Day4 problem = new Day4();
    }

    public Day4() throws FileNotFoundException {
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day4.txt")));
        int answer = 0;
        while ( scan.hasNext() ) {
            String s = scan.next();
            int firstBracket = s.indexOf('[');
            String checkSum = s.substring(firstBracket + 1, s.length() - 1);
            int lastDash = s.lastIndexOf('-');
            int id = Integer.valueOf(s.substring(lastDash + 1, firstBracket));
            s = s.substring(0, lastDash);
            Pair[] letters = new Pair[26];
            for ( int i = 0; i < 26; i++ ) letters[i] = new Pair((char)('a'+i));
            for ( int i = 0; i < s.length(); i++ ) {
                if ( s.charAt(i) != '-' ) letters[s.charAt(i) - 'a'].count++;
            }
            Arrays.sort(letters);
            String firstFive = "";
            for ( int i = 0; i < 5; i++ ) firstFive += letters[i].letter;
            if ( firstFive.equals(checkSum) ) answer += id;
            String decodedS = decode(s, id);
            if ( decodedS.contains("north")) System.out.printf("id %d %s\n", id, decodedS);
        }
        System.out.println("answer = " + answer);
    }

    public String decode(String input, int shift) {
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < input.length(); i++ ) {
            char c = input.charAt(i);
            if ( c != '-' ) {
                c = (char)('a' + (c - 'a' + shift) % 26);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public class Pair implements Comparable<Pair> {
        char letter;
        int count;
        public Pair(char c) {
            letter = c;
        }

        @Override
        public int compareTo(Pair other) {
            if ( count != other.count ) return other.count - count;
            return letter - other.letter;
        }
    }

}
