package main.days;

import main.AOCRiddle;

import java.util.ArrayList;
import java.util.List;


public class Day01 extends AOCRiddle {
    public Day01(String in, int part) {
        super(in, part);
        parse();
    }

    private List<Integer> expenses;
    private static final int VALUE = 2020;


    @Override
    protected String solve1() {
        for(int i = 0; i< expenses.size()-1; i++){
            for(int j = i+1; j< expenses.size(); j++){
                if(expenses.get(i)+expenses.get(j) == VALUE){
                    return String.valueOf(expenses.get(i)*expenses.get(j));
                }
            }
        }
        return "-1";
    }

    @Override
    protected String solve2() {
        for(int i = 0; i< expenses.size()-2; i++){
            for(int j = i+1; j< expenses.size()-1; j++){
                for(int k = j+1; k <expenses.size(); k++) {
                    if (expenses.get(i) + expenses.get(j) + expenses.get(k) == VALUE) {
                        return String.valueOf(expenses.get(i) * expenses.get(j) * expenses.get(k));
                    }
                }
            }
        }
        return "-1";
    }

    void parse(){
        String[] temp = getInput().split("\n");
        expenses = new ArrayList<>();

        for(String s : temp){
            expenses.add(Integer.parseInt(s));
        }
    }
}
