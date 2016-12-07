import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.TreeSet;

public class Day7 {
    public static void main(String[] args) throws FileNotFoundException {
        Day7 problem = new Day7();
    }

    public Day7() throws FileNotFoundException {
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day7.txt")));
        int totalTLS = 0, totalSSL = 0;
        TreeSet<String> abaSet = new TreeSet<>();
        TreeSet<String> babSet = new TreeSet<>();
        while ( scan.hasNext() ) {
            String[] parts = scan.next().replaceAll("[\\[\\]]", " ").split(" ");
            boolean[] check = new boolean[2];
            abaSet.clear();
            babSet.clear();
            for ( int i = 0; i < parts.length; i++ ) {
                check[i % 2] |= hasABBA(parts[i]);
                // Find 'aba' substrings, and store 'ab' part
                collectABA(parts[i], i % 2 == 0 ? abaSet : babSet);
            }
            if ( check[0] && !check[1] ) totalTLS++;
            boolean isSSL = false;
            for ( String s: abaSet ) isSSL |= babSet.contains(new StringBuilder(s).reverse().toString());
            if ( isSSL ) totalSSL++;
        }
        System.out.printf("totals TLS: %d SSL: %d\n", totalTLS, totalSSL);
    }

    public boolean hasABBA(String s) {
        for ( int i = 0; i + 3 < s.length(); i++ ) {
            if ( s.charAt(i) == s.charAt(i+3) &&
                    s.charAt(i+1) == s.charAt(i+2) &&
                    s.charAt(i) != s.charAt(i+1)) {
                return true;
            }
        }
        return false;
    }

    public void collectABA(String s, TreeSet<String> storage) {
        for ( int i = 0; i + 2 < s.length(); i++ ) {
            if ( s.charAt(i) == s.charAt(i+2) && s.charAt(i) != s.charAt(i+1)) {
                storage.add(s.charAt(i) + "" + s.charAt(i+1));
            }
        }
    }
}
