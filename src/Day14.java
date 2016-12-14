import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Day14 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Day14 problem = new Day14("jlmsuwbz", 2016);
//        Day14 problem = new Day14("abc", 2016);
        System.out.printf("index of 64th key %d\n", problem.solve());
    }

    public Day14(String salt, int extra) throws NoSuchAlgorithmException {
        this.salt = salt;
        extraHashings = extra;
        md = MessageDigest.getInstance("MD5");
        for ( int i = 0; i < FUTURE; i++ ) adjustQuintupleCount(i, true);
    }

    public int solve() {
        int index = 0;
        int keysFound = 0;
        while ( keysFound < 64 ) {
            /* update counts of quintuples in next 1000 (FUTURE) hashes
             * current range [index, index + FUTURE), needs to be
             * (index, index + FUTURE]
             */
            adjustQuintupleCount(index, false);
            adjustQuintupleCount(index + FUTURE, true);
            Data data = allData.get(index);
            if ( data.firstTriple >= 0 && futureQuintuples[data.firstTriple] > 0) {
                // found a key!
                keysFound++;
            }
            index++;
        }
        return index - 1;
    }

    ArrayList<Data> allData = new ArrayList<>();
    MessageDigest md;
    String salt;
    int extraHashings = 0;
    final int FUTURE = 1000;
    byte[] tmp = new byte[32];
    int[] futureQuintuples = new int[16];

    void adjustQuintupleCount(int index, boolean add) {
        compute(index);
        Data data = allData.get(index);
        for ( int i = 0; i < 16; i++ ) {
            if ( (1<<i & data.quintuples) != 0 )
                futureQuintuples[i] += add ? 1 : -1;
        }
    }

    byte[] getHash(String msg) {
        byte[] hash = md.digest(msg.getBytes());
        for ( int i = 0; i < extraHashings; i++) {
            expandHashIntoTmp(hash);
            // map numbers 0..15 to byte values of 0..9ab..f
            for ( int j = 0; j < tmp.length; j++) {
                tmp[j] += tmp[j] < 10 ? 48 : 87;
            }
            hash = md.digest(tmp);
        }
        return hash;
    }

    void expandHashIntoTmp(byte[] hash) {
        for ( int j = 0; j < hash.length; j++) {
            tmp[2*j] = (byte)((hash[j] & 0xf0) >> 4);
            tmp[2*j+1] = (byte)(hash[j] & 0x0f);
        }
    }

    void compute(int index) {
        for ( int i = allData.size(); i <= index; i++) {
            // compute the hash
            byte[] hash = getHash(salt + i);
            expandHashIntoTmp(hash);
            // find first triple and quintuples
            Data data = new Data();
            data.firstTriple = -1;
            data.quintuples = 0;
            int prev = -1;
            int count = 0;
            for ( int j = 0; j < tmp.length; j++) {
                if ( tmp[j] == prev ) {
                    count++;
                    if ( count == 3 && data.firstTriple < 0 ) data.firstTriple = prev;
                    if ( count == 5 ) data.quintuples |= 1 << prev;
                } else {
                    count = 1;
                    prev = tmp[j];
                }
            }
            allData.add(data);
        }
    }

    class Data {
        int firstTriple;
        // bitmask, bit i is set if it contains iiiii in hex representation of hash
        int quintuples;
    }
}
