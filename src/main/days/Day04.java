package main.days;

import main.AOCRiddle;

import java.util.ArrayList;
import java.util.List;

public class Day04 extends AOCRiddle {
    public Day04(String in, int part) {
        super(in,part);
        parse();
    }

    private List<Day06.Passport> passports;

    @Override
    protected String solve1() {
        int validPassports = 0;
        for(Day06.Passport passport : passports){
            if(passport.hasFields()){
                validPassports++;
            }
        }
        return String.valueOf(validPassports);
    }

    @Override
    protected String solve2() {
        int validPassports = 0;
        for(Day06.Passport passport : passports){
            if(passport.validFields()){
                validPassports++;
            }
        }
        return String.valueOf(validPassports);
    }

    private void parse(){
        passports = new ArrayList<>();
        for(String s : getInput().split("\n\n")){
            passports.add(new Day06.Passport(s.replaceAll("\n"," ")));
        }
    }
}
