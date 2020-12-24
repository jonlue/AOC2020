package main.days;

import main.AOCRiddle;

import java.util.*;
import java.util.stream.Collectors;

public class Day14 extends AOCRiddle {
    public Day14(String in, int part) {
        super(in, part);
        parse();
    }
    private List<String> instructions;
    private Map<Long,Long> mem;
    private String mask = "";

    @Override
    protected String solve1() {
        writeToMemory();

        return String.valueOf(sumMemory());
    }

    @Override
    protected String solve2() {
        writeToMemoryV2();
        return String.valueOf(sumMemory());
    }

    private void writeToMemory() {
        mem = new HashMap<>();
        for(String s : instructions){
            s = s.replaceAll("= ","");
            if(s.startsWith("mask")){
                mask = s.split(" ")[1];
            }else{
                s = s.replaceAll("mem\\[","").replaceAll("]","");
                String[] t = s.split(" ");
                mem.put(Long.parseLong(t[0]),applyMask(Long.parseLong(t[1])));
            }
        }
    }

    private long applyMask(long num) {
        String number = prepareNumber(num);

        int j = mask.length()-1;
        StringBuilder sb = new StringBuilder();
        for(int i = number.length()-1; i>= 0; i--){
            if(mask.charAt(j) == '1'){
                sb.insert(0,1);
            }else if( mask.charAt(j) == '0'){
                sb.insert(0,0);
            }else{
                sb.insert(0,number.charAt(i));
            }
            j--;
        }
        return Long.parseLong(sb.toString(),2);
    }

    private void writeToMemoryV2(){
        mem = new HashMap<>();
        for(String s : instructions){
            s = s.replace("= ","");
            if(s.startsWith("mask")){
                mask = s.split(" ")[1];
            }else{
                s = s.replaceAll("mem\\[","").replaceAll("]","");
                long address = Long.parseLong(s.split(" ")[0]);
                long value = Long.parseLong(s.split(" ")[1]);
                saveValueAtAddress(address,value);
            }
        }
    }

    private void saveValueAtAddress(long address, long value) {
        String ad = applyMaskV2(address);
        List<String> addresses = new ArrayList<>();
        addresses.add(ad);
        ListIterator<String> it = addresses.listIterator();
        while (it.hasNext()){
            String t = it.next();
            if (t.contains("X")) {
                it.set(t.replaceFirst("X", "1"));
                it.add(t.replaceFirst("X", "0"));
                while (it.hasPrevious()){
                    it.previous();
                }
            }
        }

        for(String s : addresses){
            mem.put(Long.parseLong(s,2),value);
        }
    }

    private String applyMaskV2(long address) {
        String binary = prepareNumber(address);

        StringBuilder sb = new StringBuilder();

        int j = mask.length()-1;
        for(int i = binary.length()-1; i>= 0; i--){
            if(mask.charAt(j) == '0'){
                sb.insert(0,binary.charAt(i));
            }else if(mask.charAt(j) == '1'){
                sb.insert(0,1);
            }else{
                sb.insert(0,"X");
            }
            j--;
        }
        return sb.toString();
    }

    private String prepareNumber(long num) {
        StringBuilder t = new StringBuilder();
        String number = Long.toBinaryString(num);
        t.append("0".repeat(36 - number.length()));
        t.append(number);
        return t.toString();
    }

    private long sumMemory(){
        long sum = 0;
        for(long k : mem.keySet()){
            sum += mem.get(k);
        }
        return sum;
    }

    private void parse(){
        instructions = Arrays.stream(getInput().split("\n")).collect(Collectors.toList());
    }
}
