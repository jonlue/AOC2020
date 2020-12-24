package main.days;

import main.AOCRiddle;

import java.util.*;

public class Day15 extends AOCRiddle {
    public Day15(String in, int part) {
        super(in, part);
        parse();
    }

    private List<Integer> startingNumbers;

    @Override
    protected String solve1() {
        return String.valueOf(playGame(2020));
    }

    private int playGame(int endingNumber) {
        Map<Integer, Integer> lastOccurrences = new HashMap<>();
        int turn = 1;
        int lastNumber = 0;
        int nextNumber;
        for(int i = 0; i< startingNumbers.size()-1; i++){
            lastOccurrences.put(startingNumbers.get(i),turn);
            turn++;
            lastNumber = startingNumbers.get(i+1);
        }


        while(turn < endingNumber){
            if(!lastOccurrences.containsKey(lastNumber)){
                nextNumber = 0;
            }else{
                nextNumber = turn - lastOccurrences.get(lastNumber);
            }
            lastOccurrences.put(lastNumber, turn);
            lastNumber = nextNumber;
            turn++;
        }
        return lastNumber;
    }

    @Override
    protected String solve2() {
        return String.valueOf(playGame(30000000));
    }

    private void parse(){
        startingNumbers = new ArrayList<>();
        for(String s : getInput().split(",")){
            startingNumbers.add(Integer.parseInt(s));
        }
    }
}
