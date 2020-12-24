package main.days;

import main.AOCRiddle;

import java.util.Arrays;
import java.util.List;

public class Day03 extends AOCRiddle {
    public Day03(String in, int part) {
        super(in,part);
        parse();
    }

    private List<String> treeLines;
    private static final char TREE = '#';

    @Override
    protected String solve1() {
        return String.valueOf(checkSlope(3,1));
    }

    @Override
    protected String solve2() {
        List<Integer> slopes = Arrays.asList(checkSlope(1,1),checkSlope(3,1),checkSlope(5,1),checkSlope(7,1),checkSlope(1,2));
        return String.valueOf(slopes.stream().reduce(1, (tot, el) -> tot * el));
    }


    private int checkSlope(int right, int down){
        int treeHit = 0;
        int rightCount = 0;
        for(int i = 0; i < treeLines.size(); i+= down){
            if(treeLines.get(i).charAt(rightCount) == TREE){
                treeHit++;
            }
            rightCount = (rightCount + right) % treeLines.get(i).length();
        }
        return treeHit;
    }
    
    private void parse(){
        treeLines = Arrays.asList(getInput().split("\n"));
    }
}
