package main.days;

import main.AOCRiddle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day09 extends AOCRiddle {
    public Day09(String in, int part) {
        super(in, part);
        parse();
    }

    private static final int preamble = 25;
    private List<Long> XMAS;

    @Override
    protected String solve1() {
        return String.valueOf(findInvalidNumber());
    }

    @Override
    protected String solve2() {
        long invalid = findInvalidNumber();

        for(int i = 0; i < XMAS.size()-1; ++i){
            for(int j =i+2; j <= XMAS.size() ; ++j){
                List<Long> sub = XMAS.subList(i,j);
                if(sub.stream().mapToLong(Long :: longValue).sum() == invalid){
                    return String.valueOf(Collections.min(sub) + Collections.max(sub));
                }
            }
        }

        return "-1";
    }

    private long findInvalidNumber(){
        for(int i = preamble; i < XMAS.size(); ++i){
            if(!isSum(i)){
                return XMAS.get(i);
            }
        }
        return -1;
    }

    private boolean isSum(int index){
        long number = XMAS.get(index);
        for(int i = index - preamble; i < index-1; ++i){
            for(int j = i + 1; j < index; ++j){
                if(XMAS.get(i) + XMAS.get(j) == number){
                    return true;
                }
            }
        }
        return false;
    }

    private void parse(){
        XMAS = new ArrayList<>();
        for(String s : getInput().split("\n")){
            XMAS.add(Long.parseLong(s));
        }
    }

}
