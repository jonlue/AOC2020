package main.days;

import main.AOCRiddle;

import java.math.BigInteger;
import java.util.*;

public class Day13 extends AOCRiddle {
    public Day13(String in, int part) {
        super(in, part);
        parse();
    }

    private int timeStamp;
    private List<Integer> busses;
    private static final long START_VALUE = 100000000000000L;

    @Override
    protected String solve1() {
        return String.valueOf(findEarliestBus());
    }

    @Override
    protected String solve2() {
        List<BigInteger> ms = new ArrayList<>();
        List<BigInteger> as = new ArrayList<>();
        for(int i = 0; i< busses.size(); i++){
            if(busses.get(i) != -1){
                ms.add(BigInteger.valueOf(busses.get(i)));
                as.add(BigInteger.valueOf(busses.get(i) - i));
            }
        }
        return String.valueOf(chineseRemainder(as, ms));
    }


    private int findEarliestBus(){
        for(int i = timeStamp; i < Integer.MAX_VALUE; ++i){
            for(int bus : busses){
                if(bus == -1){
                    continue;
                }
                if(i % bus == 0){
                    return bus * (i-timeStamp);
                }
            }
        }
        return -1;
    }

    private BigInteger chineseRemainder(List<BigInteger> ai, List<BigInteger> mi){
        BigInteger M = BigInteger.ONE;
        for(BigInteger m : mi){
            M = M.multiply(m);
        }

        BigInteger[] Mi = new BigInteger[mi.size()];
        for(int i = 0; i< mi.size(); i++){
            Mi[i] = M.divide(mi.get(i));
        }

        BigInteger nOne = BigInteger.ZERO.subtract(BigInteger.ONE);
        BigInteger[] ui = new BigInteger[mi.size()];
        for(int i = 0; i < mi.size(); i++){
            ui[i] = Mi[i].modPow(nOne,mi.get(i));
        }

        BigInteger x = BigInteger.ZERO;
        for(int i = 0; i < mi.size(); i++){
            x = x.add(ai.get(i).multiply(ui[i].multiply(Mi[i])).mod(M));
        }
        return x.mod(M);
    }

    private void parse(){
        String[] t = getInput().split("\n");
        timeStamp = Integer.parseInt(t[0]);
        busses = new ArrayList<>();

        for(String i : t[1].replaceAll("x","-1").split(",")){
            busses.add(Integer.parseInt(i));
        }
    }
}
