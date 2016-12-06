import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day6 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day6.txt")));
        ArrayList<String> words = new ArrayList<>();
        while ( scan.hasNext() ) words.add(scan.next());
        int n = words.get(0).length();
        String mostAnswer = "";
        String leastAnswer = "";
        for ( int i = 0; i < n; i++ ) {
            HashMap<Character, Integer> count = new HashMap<>();
            for ( String s: words ) {
                char c = s.charAt(i);
                count.put(c, count.getOrDefault(c, 0) + 1);
            }
            char mostChar = '.', leastChar = '.';
            int mostCount = 0, leastCount = Integer.MAX_VALUE;
            for (Map.Entry<Character, Integer> entry: count.entrySet()) {
                if ( entry.getValue() > mostCount ) {
                    mostChar = entry.getKey();
                    mostCount = entry.getValue();
                }
                if ( entry.getValue() < leastCount ) {
                    leastChar = entry.getKey();
                    leastCount = entry.getValue();
                }
            }
            mostAnswer += mostChar;
            leastAnswer += leastChar;
        }
        System.out.println("Most occurring: " + mostAnswer);
        System.out.println("Least occurring: " + leastAnswer);
    }
}
