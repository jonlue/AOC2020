package main.days;

import main.AOCRiddle;

import java.math.BigInteger;

public class Day25 extends AOCRiddle {
    public Day25(String in, int part) {
        super(in, part);
        parse();
    }

    private long cardPK;
    private long doorPK;
    private static final BigInteger modulator = BigInteger.valueOf(20201227);
    private static final BigInteger startSubject = BigInteger.valueOf(7);

    @Override
    protected String solve1() {
        long result;
        long cardLS = -1;
        long doorLS = -1;
        long count = 0;
        do{
            count++;
            result = transform(startSubject, count);
            if(result == cardPK){
                cardLS = count;
            }
            if(result == doorPK){
                doorLS = count;
            }
        }while(cardLS == -1 || doorLS == -1);

        return String.valueOf(transform(BigInteger.valueOf(cardPK), doorLS));
    }

    private long transform(BigInteger subject, long loopSize){
        return subject.modPow(BigInteger.valueOf(loopSize), modulator).longValue();
    }

    @Override
    protected String solve2() {
        return "Well done, you have done it";
    }

    private void parse(){
        String[] t = getInput().split("\n");
        cardPK = Integer.parseInt(t[0]);
        doorPK = Integer.parseInt(t[1]);
    }
}
