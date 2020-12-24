package main.days;

import main.AOCRiddle;

import java.util.*;

public class Day19 extends AOCRiddle {
    public Day19(String in, int part) {
        super(in, part);
        parse();
    }

    private Map<Integer, String> rules;
    private Map<Integer, String> cleanedRules;
    private List<String> input;
    private List<Integer> numberOfLetters;

    @Override
    protected String solve1() {
        cleanRules();
        Set<String> temp = new HashSet<>();
        return String.valueOf(sumMatchingData(temp).size());
    }


    @Override
    protected String solve2() {
        Set<String> result = new HashSet<>();
        cleanRules();
        // 6 is enough
        for(int i = 1; i < 100; i++) {
            String amount = "{"+i+"}";
            String ruleElven = "((" + cleanedRules.get(42) +")" + amount +"(" + cleanedRules.get(31) +")"+amount+ ")";
            cleanedRules.put(0,"("+cleanedRules.get(8)+")+" + ruleElven);
            sumMatchingData(result);
        }
        return String.valueOf(result.size());
    }

    private void cleanRules(){
        cleanedRules = new HashMap<>();
        for(int i : numberOfLetters){
            cleanedRules.put(i, rules.get(i).replaceAll(" ","").replaceAll("\"",""));
        }

        while(cleanedRules.keySet().size() < rules.keySet().size()){
            for(int i : rules.keySet()){
                Map<Integer, String> temp = new HashMap<>();
                for(int j : cleanedRules.keySet()){
                    String replacement = rules.get(i).replaceAll(" "+ j +" ", " (" + cleanedRules.get(j) + ") ");
                    if(!cleanedRules.containsKey(i) && !replacement.matches(".*\\d.*")){
                        replacement = replacement.replaceAll(" ","");
                        temp.put(i,replacement);
                    }
                    rules.put(i,replacement);
                }
                cleanedRules.putAll(temp);
            }
        }
        //cleanedRules.replaceAll((i, v) -> v.replaceAll(" ", ""));
        cleanedRules.replaceAll((i, v) -> v.replaceAll("\\(([a-zA-Z]+)\\)", "$1"));
    }

    private Set<String> sumMatchingData(Set<String> matching) {
        String ruleZero = cleanedRules.get(0);
        for (String s : input) {
            if (s.matches(ruleZero)) {
                matching.add(s);
            }
        }
        return matching;
    }

    private void parse(){
        rules = new HashMap<>();
        input = new ArrayList<>();
        numberOfLetters = new ArrayList<>();
        boolean readingRules = true;
        for(String s : getInput().split("\n")){
            if(readingRules) {
                if (s.equals("")) {
                    readingRules = false;
                    continue;
                }
                String[] temp = s.split(":");
                if(temp[1].contains("\"")){
                    numberOfLetters.add(Integer.parseInt(temp[0]));
                    temp[1] = temp[1].replaceAll("\"","").replaceAll(" ","");
                }
                rules.put(Integer.parseInt(temp[0]), temp[1] + " ");
            }else{
                input.add(s);
            }

        }
    }
}
