package main.days;

import main.AOCRiddle;

import java.util.*;

public class Day22 extends AOCRiddle {
    public Day22(String in, int part) {
        super(in, part);
        parse();
    }

    private LinkedList<Integer> playerOne;
    private LinkedList<Integer> playerTwo;

    @Override
    protected String solve1() {
        playCombat();
        boolean pOne = playerOne.size() != 0;
        return String.valueOf(sumPoints(pOne));
    }

    @Override
    protected String solve2() {
        boolean pOne = playRecursiveCombat(playerOne, playerTwo);
        return String.valueOf(sumPoints(pOne));
    }

    private boolean playRecursiveCombat(LinkedList<Integer> pOne, LinkedList<Integer> pTwo) {
        Set<List<LinkedList<Integer>>> rounds = new HashSet<>();
        infiniteLoop(rounds,pOne,pTwo);
        while(pOne.size() != 0 && pTwo.size() != 0) {
            int first = pOne.getFirst();
            pOne.removeFirst();
            int second = pTwo.getFirst();
            pTwo.removeFirst();
            if(infiniteLoop(rounds,pOne,pTwo)){
                return true;
            }
            if (getWinner(first, second, pOne, pTwo)) {
                pOne.addLast(first);
                pOne.addLast(second);
            } else {
                pTwo.addLast(second);
                pTwo.addLast(first);
            }
        }
        return pOne.size() != 0;
    }

    private boolean infiniteLoop(Set<List<LinkedList<Integer>>> set, LinkedList<Integer> pOne, LinkedList<Integer> pTwo) {
        List<LinkedList<Integer>> temp = new ArrayList<>();
        temp.add(pOne);
        temp.add(pTwo);

        if(set.contains(temp)){
            return true;
        }else{
            set.add(temp);
        }
        return false;
    }

    private boolean getWinner(int first, int second, LinkedList<Integer> pOne, LinkedList<Integer> pTwo) {
        if(pOne.size() >= first && pTwo.size() >= second){
            LinkedList<Integer> t1 = new LinkedList<>();
            LinkedList<Integer> t2 = new LinkedList<>();
            for(int i = 0; i< first; ++i){
                t1.add(pOne.get(i));
            }
            for(int i = 0; i< second; ++i){
                t2.add(pTwo.get(i));
            }
            return playRecursiveCombat(t1,t2);
        }else{
            return first > second;
        }
    }

    private void playCombat(){
        while(playerOne.size() != 0 && playerTwo.size() != 0) {
            playCombatStep();
        }
    }

    private void playCombatStep(){
        int first = playerOne.getFirst();
        playerOne.removeFirst();
        int second = playerTwo.getFirst();
        playerTwo.removeFirst();
        if(first>second){
            playerOne.addLast(first);
            playerOne.addLast(second);
        }else{
            playerTwo.addLast(second);
            playerTwo.addLast(first);
        }
    }

    private int sumPoints(boolean pOne){
        LinkedList<Integer> temp;
        if(pOne){
            temp = playerOne;
        }else{
            temp = playerTwo;
        }
        int result = 0;
        int i = 1;
        while(temp.size() != 0) {
            result += i * temp.getLast();
            temp.removeLast();
            ++i;
        }
        return result;
    }


    private void parse(){
        playerOne = new LinkedList<>();
        playerTwo = new LinkedList<>();

        String[] s = getInput().split("\n\n");
        String[] l = s[0].split("\n");
        for(int i = 1; i <l.length; ++i){
            playerOne.add(Integer.parseInt(l[i]));
        }
        l = s[1].split("\n");
        for(int i = 1; i <l.length; ++i){
            playerTwo.add(Integer.parseInt(l[i]));
        }
    }
}


