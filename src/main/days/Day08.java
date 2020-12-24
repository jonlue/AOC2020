package main.days;

import main.AOCRiddle;

import java.util.*;

public class Day08 extends AOCRiddle {
    public Day08(String in, int part) {
        super(in, part);
        parse();
    }

    private List<String> corruptedInstructions;

    @Override
    protected String solve1() {
        return String.valueOf(run());
    }

    @Override
    protected String solve2() {
        for (int i = 0; i < corruptedInstructions.size(); ++i) {
            String line;
            if (corruptedInstructions.get(i).contains("nop")) {
                line = corruptedInstructions.get(i).replace("nop", "jmp");
            } else if (corruptedInstructions.get(i).contains("jmp")) {
                line = corruptedInstructions.get(i).replace("jmp", "nop");
            } else{
                continue;
            }
            List<String> ins = new ArrayList<>(corruptedInstructions.subList(0, i));
            ins.add(line);
            ins.addAll(corruptedInstructions.subList(i + 1, corruptedInstructions.size()));

            int acc = debug(ins);
            if (acc != Integer.MIN_VALUE) {
                return String.valueOf(acc);
            }
        }
        return "-1";
    }

    private int run() {
        Set<Integer> visited = new HashSet<>();
        int accumulator = 0;
        for (int i = 0; i < corruptedInstructions.size(); ) {
            if (visited.contains(i)) {
                break;
            }
            visited.add(i);
            String line = corruptedInstructions.get(i);
            String ins = line.split(" ")[0];
            int value = Integer.parseInt(line.split(" ")[1]);
            switch (ins) {
                case ("nop"):
                    i += 1;
                    break;
                case ("acc"):
                    accumulator += value;
                    i += 1;
                    break;
                case ("jmp"):
                    i += value;
                    break;
            }
        }
        return accumulator;
    }

    private int debug(List<String> instructions) {
        Set<Integer> visited = new HashSet<>();
        int accumulator = 0;
        for (int i = 0; i < instructions.size(); ) {
            if (visited.contains(i)) {
                return Integer.MIN_VALUE;
            }
            visited.add(i);
            String line = instructions.get(i);
            String ins = line.split(" ")[0];
            int value = Integer.parseInt(line.split(" ")[1]);
            switch (ins) {
                case ("nop"):
                    i += 1;
                    break;
                case ("acc"):
                    accumulator += value;
                    i += 1;
                    break;
                case ("jmp"):
                    i += value;
                    break;
            }
        }
        return accumulator;
    }

    private void parse() {
        corruptedInstructions = Arrays.asList(getInput().replaceAll("[+]", "").split("\n"));
    }
}
