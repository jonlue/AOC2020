package main.days;

import main.AOCRiddle;

import java.util.ArrayList;
import java.util.List;

public class Day02 extends AOCRiddle {
    public Day02(String in, int part) {
        super(in, part);
        parse();
    }

    List<String> policies;
    List<String> passwords;

    @Override
    protected String solve1() {
        return String.valueOf(countValid(false));
    }

    @Override
    protected String solve2() {
        return String.valueOf(countValid(true));
    }

    private int countValid(boolean part2){
        int count = 0;
        for(int i = 0; i < passwords.size(); ++i){
            if(part2){
                if(isValid2(passwords.get(i),policies.get(i))){
                    count++;
                }
            }else {
                if (isValid(passwords.get(i), policies.get(i))) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isValid(String password, String policy){
        String[] t = policy.split(" ");
        int min = Integer.parseInt(t[0]);
        int max = Integer.parseInt(t[1]);
        char letter = t[2].toCharArray()[0];

        int count = (int) password.chars().filter(ch -> ch == letter).count();

        return count >= min && count <= max;
    }

    private boolean isValid2(String password, String policy){
        String[] t = policy.split(" ");
        int firstIndex = Integer.parseInt(t[0]);
        int secondIndex = Integer.parseInt(t[1]);
        char letter = t[2].toCharArray()[0];

        return password.charAt(firstIndex) == letter ^ password.charAt(secondIndex) == letter;
    }

    private void parse(){
        policies = new ArrayList<>();
        passwords = new ArrayList<>();
        for(String s : getInput().split("\n")){
            String[] t = s.split(":");
            policies.add(t[0].replaceAll("-"," "));
            passwords.add(t[1]);
        }
    }
}
