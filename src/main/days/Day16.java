package main.days;

import main.AOCRiddle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 extends AOCRiddle {
    public Day16(String in, int part) {
        super(in, part);
        parse();
        getRules();
    }

    private List<Integer> myTicket;
    private List<List<Integer>> nearbyTickets;
    private List<String> info;
    private List<List<Integer>> rules;


    @Override
    protected String solve1() {
        int sum = 0;
        for(List<Integer> ticket : nearbyTickets){
            int n = isValid(ticket);
            if(n >= 0){
                sum += n;
            }

        }
        return String.valueOf(sum);
    }

    @Override
    protected String solve2() {
        nearbyTickets.removeIf(integers -> isValid(integers) != -1);
        List<List<Integer>> fieldToRule = reduceRulesOfFields(orderFieldToRule(reArrangeTickets()));


        long result = 1;
        for(int i = 0; i < 6; ++i){
                result *= myTicket.get(fieldToRule.get(i).get(0));
        }
        return String.valueOf(result);
    }

    private List<List<Integer>> reduceRulesOfFields(List<List<Integer>> fieldToRule) {
        int highestRuleIndex = -1;
        int highestRules = -1;
        for(int i = 0; i < fieldToRule.size(); ++i){
            if(fieldToRule.get(i).size()> highestRules){
                highestRules = fieldToRule.get(i).size();
                highestRuleIndex = i;
            }
        }
        Set<Integer> clearedIndex = new HashSet<>();
        while (fieldToRule.get(highestRuleIndex).size() != 1) {
            for (int i = 0; i < fieldToRule.size(); i++) {
                int toRemove;
                if (fieldToRule.get(i).size() == 1 && !clearedIndex.contains(i)) {
                    clearedIndex.add(i);
                    toRemove = fieldToRule.get(i).get(0);
                    for (int j = 0; j < fieldToRule.size(); ++j) {
                        if (!clearedIndex.contains(j)) {
                            fieldToRule.get(j).remove((Integer) toRemove);
                        }
                    }
                }
            }
        }
        return fieldToRule;
    }

    //1569592597

    private List<List<Integer>> orderFieldToRule(List<List<Integer>> rearranged) {
        List<List<Integer>> fieldToRule = new ArrayList<>();
        for(List<Integer> rule : rules){
            List<Integer> temp = new ArrayList<>();
            for(int field = 0; field < rearranged.size(); field++){
                boolean found = true;
                for(int num : rearranged.get(field)){
                    if(!checkRule(num, rule)){
                        found = false;
                        break;
                    }
                }
                if(found){
                    temp.add(field);
                }
            }
            fieldToRule.add(temp);
        }
        return fieldToRule;
    }

    private List<List<Integer>> reArrangeTickets() {
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0; i < nearbyTickets.get(0).size(); ++i) {
            List<Integer> temp = new ArrayList<>();
            for (List<Integer> l : nearbyTickets) {
                temp.add(l.get(i));
            }
            result.add(temp);
        }
        return result;
    }


    private int isValid(List<Integer> ticket) {
        for(int num : ticket){
            List<Boolean> ruleForNum = new ArrayList<>();
            for(List<Integer> rule : rules){
                ruleForNum.add(checkRule(num,rule));
            }
            if(!ruleForNum.contains(true)){
                return num;
            }
        }
        return -1;
    }

    private boolean checkRule(int num, List<Integer> rule) {
        return num >= rule.get(0) && num <= rule.get(1) || num >= rule.get(2) && num <= rule.get(3);
    }


    private void getRules() {
        rules = new ArrayList<>();
        Pattern p = Pattern.compile("\\d+");
        for(String i : info){
            Matcher m = p.matcher(i);
            List<Integer> temp = new ArrayList<>();
            while(m.find()){
                temp.add(Integer.parseInt(m.group()));
            }
            rules.add(temp);
        }
    }

    private void parse(){
        int inputPart = 0;
        nearbyTickets = new ArrayList<>();
        myTicket = new ArrayList<>();
        info = new ArrayList<>();
        for(String s : getInput().split("\n")){
            if(s.equals("")){
                inputPart++;
                continue;
            }
            if (inputPart == 0) {
                info.add(s);
            }else if(inputPart == 1) {
                if (s.startsWith("your")){
                    continue;
                }
                for(String m : s.split(",")){
                    myTicket.add(Integer.parseInt(m));
                }
            }else{
                if(s.startsWith("nearby")){
                    continue;
                }
                List<Integer> temp = new ArrayList<>();
                for(String m : s.split(",")){
                    temp.add(Integer.parseInt(m));
                }
                nearbyTickets.add(temp);
            }
        }
    }
}
