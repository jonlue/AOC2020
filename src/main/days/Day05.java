package main.days;

import main.AOCRiddle;

import java.util.*;

public class Day05 extends AOCRiddle {
    public Day05(String in, int part) {
        super(in,part);
        parse();
        setIDs();
    }

    private List<String> boardingPasses;
    private List<Integer> passID;

    @Override
    protected String solve1() {
        return String.valueOf(Collections.max(passID));
    }

    @Override
    protected String solve2() {
        Collections.sort(passID);
        for(int i = 1; i < passID.size()-1; ++i){
            int id = passID.get(i);
            int next = passID.get(i+1);
            if(id + 2 == next){
                return String.valueOf(id+1);
            }
        }
        return "-1";
    }

    private void setIDs(){
        passID = new ArrayList<>();
        for(String pass : boardingPasses){
            int row = getPlace(pass.substring(0,7),127);
            int column = getPlace(pass.substring(7),7);
            int id = row * 8 + column;
            passID.add(id);
        }
    }

    private int getPlace(String code, int higher){
        int lower = 0;
        for(char c : code.toCharArray()) {
            int difference = (higher-lower)/2 + 1;
            if (c == 'F' ||c == 'L') {
                higher -= difference;
            } else {
                lower += difference;
            }
        }
        return lower;
    }

    private void parse(){
        boardingPasses = Arrays.asList(getInput().split("\n"));
    }
}
