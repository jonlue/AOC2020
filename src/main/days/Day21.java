package main.days;

import main.AOCRiddle;

import java.util.*;

public class Day21 extends AOCRiddle {
    public Day21(String in, int part) {
        super(in, part);
        parse();
    }

    private List<List<String>> ingredients;
    private List<List<String>> allergens;

    @Override
    protected String solve1() {
        long sum = 0;
        String toRemove = getWordsToRemove();
        String[] toRemoveArr = toRemove.split(",");
        for(List<String> l : ingredients){
            l.removeAll(Arrays.asList(toRemoveArr));
            sum += l.size();
        }
        return String.valueOf(sum);
    }

    @Override
    protected String solve2() {
        return getWordsToRemove();
    }


    private String getWordsToRemove(){
        // init map
        Map<String, Map<String,Integer>> allergenToIngredientCount = initBigMap();
        Map<String, Integer> allergenCount = initSmallMap();

        // count ingredients per allergen
        for(String allergen : allergenToIngredientCount.keySet()){
            Map<String, Integer> temp = new HashMap<>();
            for(int j = 0; j < ingredients.size(); ++j){
                if(allergens.get(j).contains(allergen)){
                    List<String> l = ingredients.get(j);
                    for(String i : l) {
                        temp.putIfAbsent(i,0);
                        temp.put(i,temp.get(i) + 1);
                    }
                }

            }
            allergenToIngredientCount.put(allergen, temp);
        }

        Map<String, String> allergenIngredient = mapAllergenToIngredient(allergenToIngredientCount, allergenCount);
        List<String> allergen = new ArrayList<>(allergenIngredient.keySet());
        Collections.sort(allergen);
        StringBuilder sb = new StringBuilder();
        for(String a : allergen){
            sb.append(allergenIngredient.get(a));
            sb.append(",");
        }
        String temp = sb.toString();

        return temp.substring(0,temp.length()-1);
    }

    private Map<String,String> mapAllergenToIngredient(Map<String, Map<String, Integer>> allergenToIngredientCount, Map<String, Integer> allergenCount) {
        Map<String,List<String>> res = new HashMap<>();
        for(String a : allergenCount.keySet()){
            for(String s : allergenToIngredientCount.get(a).keySet()){
                if(allergenCount.get(a).equals(allergenToIngredientCount.get(a).get(s))){
                    res.putIfAbsent(a, new ArrayList<>());
                    List<String> temp = res.get(a);
                    temp.add(s);
                    res.put(a,temp);
                }
            }
        }
        Map<String,String> result = new HashMap<>();
        while(result.size() != res.size()){
            for(String s : res.keySet()){
                if(res.get(s).size() == 1){
                    result.put(s,res.get(s).get(0));
                    res.put(s,new ArrayList<>());
                }else{
                    for(String ing : result.values()){
                        res.get(s).remove(ing);
                    }
                }
            }
        }
        return result;
    }

    private Map<String, Map<String, Integer>> initBigMap() {
        Map<String, Map<String,Integer>> res = new HashMap<>();
        for(List<String> l : allergens){
            for(String a : l) {
                res.putIfAbsent(a,new HashMap<>());
            }
        }
        return res;
    }

    private Map<String, Integer> initSmallMap() {
        Map<String,Integer> res = new HashMap<>();
        for(List<String> l : allergens){
            for(String a : l) {
                res.putIfAbsent(a,0);
                res.put(a,res.get(a) + 1);
            }
        }
        return res;
    }


    private void parse(){
        ingredients = new ArrayList<>();
        allergens = new ArrayList<>();
        for(String s : getInput().split("\n")){
            String[] a = s.split("\\(contains ")[1].replaceAll(",","").replaceAll("\\)","").split(" ");
            allergens.add(new ArrayList<>(Arrays.asList(a)));
            ingredients.add(new ArrayList<>(Arrays.asList(s.split(" \\(contains ")[0].split(" "))));
        }
    }
}
