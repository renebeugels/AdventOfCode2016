import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalLong;
import java.util.Scanner;

public class Day23and25 {
    public static void main(String[] args) throws FileNotFoundException {
        Day23and25 problem = new Day23and25();
        Scanner scan = new Scanner(new BufferedReader(new FileReader("input/day25.txt")));
        // 23 A
//        System.out.printf("result %d\n", problem.solve(scan, new long[]{7, 0, 0, 0}).getAsLong());
        // 23 B
//        System.out.printf("result %d\n", problem.solve(scan, new long[]{12, 0, 0, 0}).getAsLong());
        // problem 25
         problem.solve25(scan);
    }

    private OptionalLong solve25(Scanner scan) {
        ArrayList<Instruction> instructions = parseInstructions(scan);
        long[] init = new long[4];
        for ( int a = 1; a <= 1000; a++) {
            init[0] = a;
            OptionalLong result = solve(instructions, init, 100_000_000L);
            if ( result.isPresent() ) System.out.println("candidate " + a);
        }
        return OptionalLong.empty();
    }

    private OptionalLong solve(Scanner scan) {
        return solve(parseInstructions(scan), new long[4], Long.MAX_VALUE);
    }

    private OptionalLong solve(Scanner scan, long[] init) {
        return solve(parseInstructions(scan), init, Long.MAX_VALUE);
    }

    private OptionalLong solve(ArrayList<Instruction> instructions, long[] initRegister,
                               long maxIterations) {
        State state = new State(initRegister);
        long iterations = 0;
        int prevOutput = 1;
        while ( state.instructionIndex >= 0 && state.instructionIndex < instructions.size()) {
            iterations++;
            if ( iterations >= maxIterations ) break;
            Instruction instr = instructions.get(state.instructionIndex);
            switch ( instr.type ) {
                case COPY:
                    instr.secondArg().store(state, instr.firstArg().get(state));
                    state.instructionIndex++;
                    break;
                case DEC:
                    instr.firstArg().store(state, instr.firstArg().get(state) - 1);
                    state.instructionIndex++;
                    break;
                case INC:
                    instr.firstArg().store(state, instr.firstArg().get(state) + 1);
                    state.instructionIndex++;
                    break;
                case JNZ:
                    state.instructionIndex += instr.firstArg().get(state) != 0 ?
                                                   instr.secondArg().get(state) : 1;
                    break;
                case TOGGLE:
                    int modIndex = (int)(state.instructionIndex + instr.firstArg().get(state));
                    if ( modIndex >= 0 && modIndex < instructions.size() ) {
                        instructions.get(modIndex).toggle();
                    }
                    state.instructionIndex++;
                    break;
                case OUT:
                    // we are looking for a 0,1,0,1,0,1,0,1,... infinite output sequence
                    if ( instr.firstArg().get(state) != 1 - prevOutput ) return OptionalLong.empty();
                    prevOutput = 1 - prevOutput;
                    state.instructionIndex++;
                    break;
            }
        }
        return OptionalLong.of(state.getValue(0));
    }

    private ArrayList<Instruction> parseInstructions(Scanner scan) {
        ArrayList<Instruction> result = new ArrayList<>();
        while ( scan.hasNext() ) result.add(createInstruction(scan.nextLine()));
        return result;
    }

    private Instruction createInstruction(String s) {
        String[] parts = s.split("\\s+");
        ArrayList<Value> arguments = new ArrayList<>();
        for ( int i = 1; i < parts.length; i++) {
            int index = "abcd".indexOf(parts[i]);
            if ( index >= 0 ) arguments.add(new RegisterValue(index));
            else arguments.add(new Constant(Integer.valueOf(parts[i])));
        }
        InstructionType type = InstructionType.COPY;
        switch ( parts[0] ) {
            case "cpy" : type = InstructionType.COPY; break;
            case "inc" : type = InstructionType.INC; break;
            case "dec" : type = InstructionType.DEC; break;
            case "jnz" : type = InstructionType.JNZ; break;
            case "tgl" : type = InstructionType.TOGGLE; break;
            case "out" : type = InstructionType.OUT; break;
        }
        return new Instruction(type, arguments);
    }


    public enum InstructionType { COPY, INC, DEC, JNZ, TOGGLE, OUT};
    public class Instruction {
        InstructionType type;
        ArrayList<Value> args;

        public Instruction(InstructionType type, ArrayList<Value> arguments) {
            this.type = type;
            this.args = new ArrayList<>(arguments);
        }

        public Value firstArg() { return args.get(0); }
        public Value secondArg() { return args.get(1); }

        public void toggle() {
            switch ( type ) {
                case INC: type = InstructionType.DEC; break;
                case DEC:
                case OUT:
                case TOGGLE: type = InstructionType.INC; break;
                case COPY: type = InstructionType.JNZ; break;
                case JNZ: type = InstructionType.COPY; break;
            }
        }
    }

    interface Value {
        public long get(State state);
        public void store(State state, long value);
    }

    class Constant implements Value {
        long value;
        public Constant(int v) { value = v; }
        @Override
        public long get(State state) { return value; }
        @Override
        public void store(State state, long v) {}
    }

    class RegisterValue implements Value {
        int register;
        public RegisterValue(int index) { register = index; }
        @Override
        public long get(State state) { return state.getValue(register); }
        @Override
        public void store(State state, long v ) { state.store(register, v); }
    }

    class State {
        long[] register = new long[4];
        public State(long[] init) { register = Arrays.copyOf(init, init.length); }
        int instructionIndex = 0;
        public long getValue(int index) { return register[index]; }
        public void store(int index, long value) { register[index] = value; }
    }
}
