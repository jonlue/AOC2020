package main.days;

import main.AOCRiddle;

import java.util.*;

public class Day07 extends AOCRiddle {
    public Day07(String in, int part) {
        super(in, part);
        parse();
    }

    private Map<String, List<String>> bags;

    private static final String BAG = "shiny gold";
    private Set<String> counted;

    @Override
    protected String solve1() {
        counted = new HashSet<>();
        return String.valueOf(bagIn(BAG));

    }

    private int bagIn(String bag){
        int sum = 0;
        for(String t : bags.keySet()){
            for(String b : bags.get(t)){
                if(b.contains(bag) && !counted.contains(t)){
                    counted.add(t);
                    sum += 1 + bagIn(t);
                }
            }
        }
        return sum;
    }

    @Override
    protected String solve2() {
        return String.valueOf(contains(BAG));
    }

    private int contains(String bag){
        int sum = 0;
        for(String b : bags.get(bag)){
            int num;
            try{
                num = Integer.parseInt(b.substring(0,1));
            }catch (Exception e){
                return sum;
            }
            sum += num + num * contains(b.substring(2));
        }
        return sum;
    }

    private void parse(){
        bags = new HashMap<>();
        String t = getInput().replaceAll(" bags ","").replaceAll(" bags","").replaceAll(" bag","").replaceAll("contain ",",").replaceAll("[.]","").replaceAll(", ",",");
        for(String l :t.split("\n")){
            String[] temp = l.split(",");
            bags.put(temp[0],new ArrayList<>(Arrays.asList(temp).subList(1, temp.length)));
        }
    }
}
