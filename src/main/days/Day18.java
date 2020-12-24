package main.days;

import main.AOCRiddle;
import main.days.day18Support.Calculator;
import main.days.day18Support.Scanner;

import java.util.Arrays;
import java.util.List;

public class Day18 extends AOCRiddle {
    public Day18(String in, int part) {
        super(in, part);
        parse();
    }

    private List<String> lines;
    private static final char ADD = '+';
    private static final char MUL = '*';

    @Override
    protected String solve1() {
        return String.valueOf(sumAllLines(false));
    }

    @Override
    protected String solve2() {
        return String.valueOf(sumAllLines(true));
    }

    private Long sumAllLines(boolean part2) {
        long sum = 0;
        if(part2) {
            for(String line : lines){
                Scanner.init(line);
                Scanner.scan();
                try {
                    Calculator.expr();
                    sum += Calculator.pop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            for (String line : lines) {
                long temp = solveLine(line);
                System.out.println(temp);
                sum += temp;
            }
        }
        return sum;
    }
    /*
    1 + 2 * 3 + 4 * 5 + 6
    1 + (2 * 3) + (4 * (5 + 6))
    2 * 3 + (4 * 5)
    5 + (8 * 3 + 9 + 3 * 4 * 3)
    5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))
    ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
     */

    private long solveLine(String line) {
        long number = 0;
        char function = '.';
        for(int i = 0; i < line.length(); ++i){
            char c = line.charAt(i);
            switch (c){
                case '(':
                    if(function == ADD){
                        number += solveLine(line.substring(i+1));
                    }else if(function == MUL){
                        number *= solveLine(line.substring(i+1));
                    }else{
                        number = solveLine(line.substring(i+1));
                    }
                    i += nextClosingBracket(line.substring(i+1));
                    break;
                case ')':
                    return number;
                case ADD:
                    function = ADD;
                    break;
                case MUL:
                    function = MUL;
                    break;
                default:
                    int t = Character.getNumericValue(c);
                    if(function == ADD){
                        number += t;
                    }else if(function == MUL){
                        number *= t;
                    }else{
                        number = t;
                    }
            }
        }
        return number;
    }

    private int nextClosingBracket(String substring) {
        int count = 0;
        for(int i = 0; i < substring.length(); ++i){
            char c = substring.charAt(i);
            if(c == '(') {
                count++;
            }else if(c == ')'){
                if(count == 0){
                    return i+1;
                }else{
                    count--;
                }
            }
        }
        return -1;
    }

    private void parse(){
        lines = Arrays.asList(getInput().replaceAll(" ","").split("\n"));
    }
}
