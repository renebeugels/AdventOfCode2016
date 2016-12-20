import java.util.BitSet;

public class Day16 {
    public static void main(String[] args) {
        Day16 problem = new Day16();
        System.out.printf("checksum A %s\n", problem.solve("01000100010010111", 272));
        System.out.printf("checksum B %s\n", problem.solve("01000100010010111", 35651584));
    }

    public String expand(String s, int k) {
        for ( int i = 0; i < k; i++ ) {
            s = s + "0" + s.chars().mapToObj(a -> "" + (char)(a^1))
                    .reduce((a, b) -> b + a).get();
        }
        return s;
    }

    public String solveStupid(String input, int length) {
        BitSet x = new BitSet(length);
        for ( int i = 0; i < input.length() && i < length; i++) x.set(i, input.charAt(i) == '1');
        int n = input.length();
        while ( n < length ) {
            x.set(n, false);
            for ( int i = n + 1; i < Math.min(length, 2*n + 1); i++ ) x.set(i, !x.get(2*n-i));
            n = 2*n + 1;
        }
        while ( length % 2 == 0 ) {
            for ( int i = 0; i < length/2; i++ ) x.set(i, x.get(2*i) ^ x.get(2*i+1) ^ true);
            length /= 2;
        }
        String s = "";
        for ( int i = 0; i < length; i++ ) s += x.get(i) ? "1" : "0";

        return s;
    }

    public String solve(String input, long length) {
        long m = 1;
        while ( m < length ) m = 2*m + 1;
        long n = length;
        // length = n*2**p for some p
        while ( n % 2 == 0 ) n /= 2;
        // precomputation of some values
        int[] prefixSumA = new int[input.length()];
        for ( int i = 0; i < input.length(); i++ )
            prefixSumA[i] = (i == 0 ? 0 : prefixSumA[i-1]) + (input.charAt(i) == '1' ? 1 : 0);
        int[] prefixSumAInv = new int[input.length()];
        for ( int i = 0; i < input.length(); i++ )
            prefixSumAInv[i] = (i == 0 ? 0 : prefixSumAInv[i-1]) +
                               (input.charAt(input.length() - 1 - i) == '0' ? 1 : 0);
        int weightA = prefixSumA[input.length() - 1];
        int weightAInv = input.length() - weightA;

        /* Let a be the input and A its reverse + inverse
         * After applying the lengthening operation:
         * round 1: a 0 A
         * round 2: a 0 A 0 a 1 A
         * round 3: a 0 A 0 a 1 A 0 a 0 A 1 a 1 A
         * In general it is an alternating sequence of a and A with 0 or 1 in between
         *
         * Each reduction step replaces a pair of bits (x, y) by x ^ y ^ 1
         * The reduction step is only done if there is an even number of pairs, so all
         * added 1's cancel out. After the last reduction an odd number of 1's remains.
         */

        int k = input.length() + 1;
        String result = "";
        long previousSum = 0;
        for ( int i = 0; i < n; i++ ) {
            // compute xor of bits in range (i*2**p, (i+1)*2**p]
            long up = (i+1)*length/n;
            long blocks = up/k;
            long total = blocks/2*weightAInv + (blocks - blocks/2)*weightA;
            if ( up % k > 0 ) {
                // we need to add a part of either a or A
                total += blocks % 2 == 0 ? prefixSumA[(int)(up % k) - 1] : prefixSumAInv[(int)(up % k) - 1];
            }
            // compute weight of 0/1 subsequence
            total += prefixSum(blocks, m);
            // If n == length we don't get an extra bit caused by reduction!
            result += n == length ^ (total - previousSum) % 2 == 0 ? "1" : "0";
            previousSum = total;
        }
        return result;
    }

    public long prefixSum(long k, long m) {
        // m = 2**p - 1 for some p
        // 1 <= k <= m
        if ( k >= m ) return (m - 1)/2;
        if ( k <= m/2 + 1 ) return prefixSum(k, (m-1)/2);
        /* Total number of 1's is m/2. The number of 1's strictly
         * to the right of position k is the difference between the
         * number of bits to the right of k and the number of 0's.
         * This last number equals the prefixSum on the previous iteration
         * (remember, the pattern is inverted).
         */
        return m/2 - (m - k - prefixSum(m - k, m/2));
    }
}
