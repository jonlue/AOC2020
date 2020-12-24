package main.days;

import main.AOCRiddle;

public class Day23 extends AOCRiddle {
    public Day23(String in, int part) {
        super(in, part);
        parse();
    }

    private int[] cups;
    private int highest;
    private int firstCup;
    private int lastCup;

    @Override
    protected String solve1() {
        highest = getInput().length();
        cups[lastCup] = firstCup;
        playGame(100,firstCup);

        StringBuilder sb = new StringBuilder();
        int temp = 1;
        for(int i = 0; i< 10; ++i){
            temp = cups[temp];
            sb.append(temp);
        }
        return sb.toString();
    }

    @Override
    protected String solve2() {
        highest = 1_000_000;
        lastCup = highest;
        prepareCups();
        playGame(10_000_000, firstCup);

        long result = cups[1];
        result *= cups[cups[1]];
        return String.valueOf(result);
    }

    private int move(int start){
        int a = cups[start];
        int b = cups[a];
        int c = cups[b];

        //find destination
        int destination = start-1;
        while (destination == a || destination == b || destination == c || destination <= 0) {
            destination--;
            if(destination <= 0){
                destination = highest;
            }
        }

        cups[start] = cups[c];
        int temp = cups[destination];
        cups[destination] = a;
        cups[c] = temp;
        return cups[start];
    }

    private void playGame(int moves, int start){
        for(int i = 0; i < moves+1; i++){
            start = move(start);
        }
    }

    private void prepareCups() {
        int index = Character.getNumericValue(getInput().charAt(getInput().length()-1));
        cups[index] = 10;
        for(int i = 10; i < 1_000_000; ++i){
            cups[i] = i+1;
        }
        cups[1_000_000] = firstCup;
    }


    private void parse(){
        cups = new int[1_000_001];
        String t = getInput();
        firstCup = Character.getNumericValue(t.charAt(0));
        for(int i = 0; i < getInput().length()-1; i++){
            int index = Character.getNumericValue(t.charAt(i));
            int value = Character.getNumericValue(t.charAt(i+1));
            lastCup = value;
            cups[index] = value;
        }
    }
}
