import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day20 {
    public static void main(String[] args) throws FileNotFoundException {
        Day20 problem = new Day20();
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day20.txt")));
        long[] result = problem.solve(scan, 4294967295L);
        System.out.printf("first IP %d valid ips %d\n", result[0], result[1]);
    }

    public long[] solve(Scanner scan, long maxIP) {
        scan = scan.useDelimiter("[-\\s]");
        ArrayList<Event> events = new ArrayList<>();
        boolean enter = true;
        while ( scan.hasNext() ) {
            events.add(new Event(scan.nextLong(), enter));
            enter = !enter;
        }
        long firstValidIP = -1;
        long validIntervalStart = 0;
        long validIPCount = 0;
        int count = 0;
        Collections.sort(events);
        for ( Event event: events ) {

            if ( event.enter ) {
                if ( count == 0 && validIntervalStart < event.x ) {
                    validIPCount += event.x - validIntervalStart;
                    if ( firstValidIP < 0 ) firstValidIP = validIntervalStart;
                }
                count++;
            } else {
                count--;
                if ( count == 0 ) validIntervalStart = event.x + 1;
            }
        }
        if ( firstValidIP < 0 && validIntervalStart <= maxIP ) firstValidIP = validIntervalStart;
        validIPCount += Math.max(0, maxIP + 1 - validIntervalStart);
        return new long[]{firstValidIP, validIPCount};
    }

    private class Event implements Comparable<Event> {
        long x;
        boolean enter;
        public Event(long x, boolean enter) {
            this.x = x;
            this.enter = enter;
        }

        @Override
        public int compareTo(Event other) {
            if ( x != other.x ) return Long.compare(x, other.x);
            if ( enter != other.enter ) return enter ? 1 : -1;
            return 0;
        }

        @Override
        public String toString() { return String.format("x = %d enter %s", x, enter); }
    }
}
