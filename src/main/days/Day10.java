package main.days;

import main.AOCRiddle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 extends AOCRiddle {
    public Day10(String in, int part) {
        super(in, part);
        parse();
    }

    private List<Integer> jolts;

    @Override
    protected String solve1() {
        int oneDifference = countDifferences(jolts,1);
        int threeDifference = countDifferences(jolts,3);

        return String.valueOf(oneDifference*threeDifference);
    }

    @Override
    protected String solve2() {
        List<List<Integer>> subLists = getSubLists(jolts);

        long variants = 1;
        for(List<Integer> t : subLists){
            variants *= fiboSimilar(t.size());
        }
        return String.valueOf(variants);
    }

    private List<List<Integer>> getSubLists(List<Integer> lst) {
        int start = 0;
        List<List<Integer>> temp = new ArrayList<>();
        for(int i = 0; i< lst.size()-1; ++i){
            if(lst.get(i+1) - lst.get(i) == 3){
                temp.add(lst.subList(start,i+1));
                start = i+1;
            }
        }
        return temp;
    }



    private int fiboSimilar(int times){
        //untested above 6
        if(times < 1) return 0;
        if(times == 1 ||times == 2) return 1;

        return fiboSimilar(times-1) + fiboSimilar(times-2) + fiboSimilar(times-3);
    }

    private int countDifferences(List<Integer> lst, int difference){
        int result = 0;
        for (int i = 0; i < lst.size() - 1; ++i) {
            if(lst.get(i+1)-lst.get(i) == difference){
                result++;
            }
        }
        return result;
    }

    private void parse(){
        jolts = Arrays.stream(getInput().split("\n")).map(Integer::parseInt).collect(Collectors.toList());
        Collections.sort(jolts);
        jolts.add(0,0);
        jolts.add(jolts.get(jolts.size() - 1) + 3);
    }
}
