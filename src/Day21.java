import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Day21 {
    public static void main(String[] args) throws FileNotFoundException {
        Day21 problem = new Day21();
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day21.txt")));
//        System.out.printf("scrambled %s\n", problem.solveA(scan, "abcde"));

        System.out.printf("scrambled %s\n", problem.solveB(scan, "fbgdceah"));
    }

    public String solveA(Scanner scan, String input) {
        while ( scan.hasNext() ) input = transform(input, scan.nextLine());
        return input;
    }

    public String solveB(Scanner scan, String output) {
        ArrayList<String> rules = new ArrayList<>();
        while ( scan.hasNext() ) rules.add(scan.nextLine());
        int[] p = IntStream.range(0, output.length()).toArray();
        do {
            String input = IntStream.range(0, output.length())
                                    .mapToObj(i -> "" + output.charAt(p[i]))
                                    .reduce((a, b) -> a + b).get();
            String tmp = input;
            for ( String rule: rules ) {
                tmp = transform(tmp, rule);
            }
            if ( tmp.equals(output) ) return input;
        } while (nextPermutation(p));
        return "IMPOSSIBLE";
    }

    public String transform(String input, String rule) {
        String[] data = rule.split("\\s+");
        switch ( data[0] ) {
            case "swap":
                switch ( data[1] ) {
                    case "position":
                        int i = IntStream.of(2, 5).map(x -> Integer.valueOf(data[x])).min().getAsInt();
                        int j = IntStream.of(2, 5).map(x -> Integer.valueOf(data[x])).max().getAsInt();
                        input = input.substring(0, i) + input.charAt(j) + input.substring(i+1, j) +
                                input.charAt(i) + input.substring(j + 1);
                        break;
                    case "letter":
                        input = input.replaceAll(data[2], data[5].toUpperCase())
                                .replaceAll(data[5], data[2]).toLowerCase();
                        break;
                }
            case "rotate":
                int k = 0;
                switch ( data[1] ) {
                    case "left":
                        k = Integer.valueOf(data[2]);
                        break;
                    case "right":
                        k = input.length() - Integer.valueOf(data[2]);
                        break;
                    case "based":
                        k = input.indexOf(data[6]);
                        k += k >= 4 ? 2 : 1;
                        k = (2*input.length() - k) % input.length();
                        break;
                }
                input = input.substring(k) +  input.substring(0, k);
                break;
            case "reverse":
                int x = Integer.valueOf(data[2]);
                int y = Integer.valueOf(data[4]);
                char[] tmp = input.toCharArray();
                while ( x < y ) {
                    char swap = tmp[x];
                    tmp[x] = tmp[y];
                    tmp[y] = swap;
                    x++;
                    y--;
                }
                input = new String(tmp);
                break;
            case "move":
                int from = Integer.valueOf(data[2]);
                int to = Integer.valueOf(data[5]);
                char c = input.charAt(from);
                input = input.substring(0, from) + input.substring(from + 1);
                input = input.substring(0, to) + c + input.substring(to);
                break;
        }
        return input;
    }

    public boolean nextPermutation(int[] a) {
        if (a.length <= 1) return false;
        int k = a.length - 2;
        while (k >= 0 && a[k] >= a[k + 1]) k--;
        int m = a.length - 1;
        if (k < 0) {
            reverse(a, 0, m);
            return false;
        }
        while (a[m] <= a[k]) m--;
        swap(a, m, k);
        reverse(a, k + 1, a.length - 1);
        return true;
    }

    public void reverse(int[] a, int s, int t) {
        while (s < t) swap(a, s++, t--);
    }

    public void swap(int[] a, int s, int t) {
        int b = a[s];
        a[s] = a[t];
        a[t] = b;
    }
}
