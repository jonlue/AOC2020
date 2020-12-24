package main.days;

import main.AOCRiddle;

import java.util.*;
import java.util.regex.Pattern;

public class Day06 extends AOCRiddle {
    public Day06(String in, int part) {
        super(in,part);
        parse();
    }

    private List<String> groups;

    @Override
    protected String solve1() {
        int count = 0;
        for(String g : groups){
            Set<Character> answer = new HashSet<>();
            for(char c : g.toCharArray()){
                if(c == ','){
                    continue;
                }
                answer.add(c);
            }
            count += answer.size();
        }
        return String.valueOf(count);
    }

    @Override
    protected String solve2() {
        int count = 0;
        for(String g : groups){
            int person = 1;
            Map<Character,Integer> answer = new HashMap<>();
            for(char c : g.toCharArray()){
                if(c == ','){
                    person++;
                    continue;
                }
                answer.putIfAbsent(c,0);
                answer.put(c,answer.get(c)+1);
            }
            for(Character c : answer.keySet()){
                if(answer.get(c) == person){
                    count++;
                }
            }
        }
        return String.valueOf(count);
    }

    private void parse() {
        String[] temp = getInput().split("\n\n");
        groups = new ArrayList<>();
        for(String g : temp){
            groups.add(g.replace("\n",","));
        }
    }

    public static class Passport {

        private static final String BIRTH = "byr";
        private static final String ISSUE = "iyr";
        private static final String EXPIRATION = "eyr";
        private static final String HEIGHT = "hgt";
        private static final String HAIR = "hcl";
        private static final String EYE = "ecl";
        private static final String PASSPORT_ID = "pid";

        private static final Pattern HAIR_COLOR = Pattern.compile("^#[0-9a-f]{6}$");
        private static final Pattern EYE_COLOR = Pattern.compile("(amb)|(blu)|(brn)|(gry)|(grn)|(hzl)|(oth)");
        private static final Pattern ID = Pattern.compile("^[0-9]{9}$");

        private int birthYear = -1;
        private int issueYear = -1;
        private int expirationYear = -1;
        private int height = -1;
        private boolean inches = false;
        private String hairColor = "";
        private String eyeColor = "";
        private String passportID = "";

        public Passport(String passport){
            for(String s : passport.split(" ")){
                String[] entry = s.split(":");
                switch (entry[0]) {
                    case (BIRTH):
                        birthYear = Integer.parseInt(entry[1]);
                        break;
                    case (ISSUE):
                        issueYear = Integer.parseInt(entry[1]);
                        break;
                    case (EXPIRATION):
                        expirationYear = Integer.parseInt(entry[1]);
                        break;
                    case (HEIGHT):
                        if (entry[1].contains("cm")) {
                            inches = false;
                            height = Integer.parseInt(entry[1].replace("cm", ""));
                        } else {
                            inches = true;
                            height = Integer.parseInt(entry[1].replace("in", ""));
                        }
                        break;
                    case (HAIR):
                        hairColor = entry[1];
                        break;
                    case (EYE):
                        eyeColor = entry[1];
                        break;
                    case (PASSPORT_ID):
                        passportID = entry[1];
                        break;
                }
            }
        }

        public boolean hasFields(){
            return birthYear != -1 && issueYear != -1 && expirationYear != -1 && height != -1 && !hairColor.equals("") && !eyeColor.equals("") && !passportID.equals("");
        }

        public boolean validFields(){
            return validYears() && validHeight() && validColor() && validID();
        }

        private boolean validID() {
            return ID.matcher(passportID).matches();
        }

        private boolean validColor() {
            return (HAIR_COLOR.matcher(hairColor).matches() && EYE_COLOR.matcher(eyeColor).matches());
        }

        private boolean validHeight() {
            if(inches){
                return (height>=59 && height<= 76);
            }else{
                return (height >= 150 && height<=193);
            }
        }

        private boolean validYears() {
            return (birthYear >=1920 && birthYear <= 2002) && (issueYear >= 2010 && issueYear <= 2020) && (expirationYear >= 2020 && expirationYear <= 2030);
        }
    }
}
