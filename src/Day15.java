import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {
    public static void main(String[] args) throws FileNotFoundException {
        Day15 problem = new Day15();
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day15.txt")));
        Pattern pattern = Pattern.compile("has (\\d+) .* position (\\d+)");

        ArrayList<String[]> data = new ArrayList<>();
        while ( scan.hasNext() ) {
            Matcher matcher = pattern.matcher(scan.nextLine());
            while ( matcher.find() ) data.add( new String[]{matcher.group(1), matcher.group(2)});
        }
        long[] n = data.stream().mapToLong(pair -> Long.valueOf(pair[0])).toArray();
        long[] pos = data.stream().mapToLong(pair -> Long.valueOf(pair[1])).toArray();

        System.out.printf("output %d\n", problem.solve(pos, n));
    }

    public long solve(long[] pos, long[] n) {
        /* Find smallest t such that
         * pos[i] + t + i + 1 = 0 mod n[i]
         * Assumes that the n[i] are all relatively prime
         */
        long[] a = new long[pos.length];
        for ( int i = 0; i < pos.length; i++) {
            a[i] = (-(i+1) - pos[i]) % n[i];
            if ( a[i] < 0 ) a[i] += n[i];
        }
        return chineseRemainder(a, n);
    }

    /* Assumes that n[i] are relative prime and
     * product of n[i] fits in long type
     * Return x such that x mod n[i] = a[i] for all i
     */
    public long chineseRemainder(long[] a, long[] n) {
        long N = 1;
        for ( long nn: n ) N *= nn;
        long result = 0;
        for ( int i = 0; i < a.length; i++ ) {
            result += a[i]*(N/n[i])*modInverse(N/n[i], n[i]);
        }
        return result % N;
    }

    /* return b such that b*a == 1 mod n
     * or 0 if there is no inverse
     */
    public long modInverse(long a, long n) {
        long[] z = extendedGCD(a % n, n);
        return z[0] > 1 ? 0 : (z[1] + n) % n;
    }

    /* Compute u and v such that
     * gcd(a, b) = u*a + v*b
     * return (gcd(a,b), u, v)
     */
    public long[] extendedGCD(long a, long b) {
        if (b == 0) return new long[] {a, 1, 0};
        long[] z = extendedGCD(b, a % b);
        return new long[] { z[0], z[2], z[1] - (a/b)*z[2] };
    }
}
