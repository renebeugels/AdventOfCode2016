import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class Day10 {
    public static void main(String[] args) throws FileNotFoundException {
        Day10 problem = new Day10();
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day10.txt")));
        problem.solve(scan);
    }

    Map<String, Bot> botMap;
    public void solve(Scanner scan) {
        botMap = new HashMap<>();
        while ( scan.hasNext() ) {
            String s = scan.nextLine();
            if ( s.startsWith("value") ) {
                String name = s.substring(s.indexOf(" to ") + 4);
                botMap.computeIfAbsent(name, Bot::new);
                botMap.get(name).addValue(Integer.parseInt(s.substring(6, s.indexOf(" goes"))));
            } else {
                String from = s.substring(0, s.indexOf("gives") - 1);
                String low = s.substring(s.indexOf("low to") + 7, s.indexOf(" and"));
                String high = s.substring(s.indexOf("high to") + 8);
                Stream.of(from, low, high).forEach(name -> botMap.computeIfAbsent(name, Bot::new));
                botMap.get(from).setRecipients(botMap.get(low), botMap.get(high));
            }
        }
        botMap.values().stream()
                .sorted((a, b) -> a.name.compareTo(b.name) )
                .forEach(System.out::println);
    }

    public class Bot {
        String name;
        Bot recipientLow, recipientHigh;
        ArrayDeque<Integer> values = new ArrayDeque<>();
        public Bot(String s) {
            name = s;
        }

        public void addValue(int v) {
            values.add(v);
            propagate();
        }

        public void setRecipients(Bot low, Bot high) {
            recipientLow = low;
            recipientHigh = high;
            propagate();
        }

        private void propagate() {
            /* The order in which a robot passes on its chips is not
             * specified in the problem statement. This method does
             * dfs on low then on high.
             */
            if ( recipientLow != null ) {
                while ( values.size() >= 2 ) {
                    int a = values.pop();
                    int b = values.pop();
                    // output for part A of the problem
                    if ( Math.max(a, b) == 61 && Math.min(a, b) == 17) {
                        System.out.println("Found pair " + name);
                    }
                    recipientLow.addValue(Math.min(a, b));
                    recipientHigh.addValue(Math.max(a, b));
                }
            }
        }

        @Override
        public String toString() { return String.format("%s values %s", name, values.toString()); }
    }
}
