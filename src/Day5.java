import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Day5 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String prefix = "abc";
        prefix = "uqwqemis";
        boolean partB = true;
        char[] password = new char[8];
        Arrays.fill(password, '.');
        int found = 0;
        int k = 0;
        long now = System.currentTimeMillis();
        while ( found < 8 ) {
            byte[] hash = md.digest((prefix + k).getBytes());
            if (hash[0] == 0 && hash[1] == 0 && (hash[2] & 15) == hash[2]) {
                int pos = found;
                int nr = hash[2];
                if (partB) {
                    pos = nr;
                    nr = (hash[3] & 0xff) >> 4;
                }
                if (pos < password.length && password[pos] == '.') {
                    password[pos] = String.format("%x", nr).charAt(0);
                    System.out.printf("k = %d found %s for position %d\n", k, password[pos], pos);
                    found++;
                }
            }
            k++;
        }
        System.out.println("answer " + new String(password));
        System.out.println("elapsed " + (System.currentTimeMillis() - now));
    }
}
