package main.days;

import main.AOCRiddle;

import java.util.Arrays;

public class Day11 extends AOCRiddle {
    public Day11(String in, int part) {
        super(in, part);
        parse();
    }

    private String[] rows;

    @Override
    protected String solve1() {
        shuffleSeats(4,false);
        return String.valueOf(countOccupiedSeats());
    }

    @Override
    protected String solve2() {
        shuffleSeats(5,true);
        return String.valueOf(countOccupiedSeats());
    }


    private int countNeighbours(int x, int y, boolean part2) {
        if(part2){
            return neighboursOnSight(x,y);
        }else{
            return directNeighbour(x,y);
        }
    }

    private int neighboursOnSight(int x, int y) {
        int sumNeighbour = 0;

        // down
        for(int i = x + 1; i < rows.length; i++){
            char c = rows[i].charAt(y);
            if(c == '#'){
                sumNeighbour++;
                break;
            }else if(c == 'L'){
                break;
            }
        }

        // up
        for(int i = x-1; i >= 0; i--){
            char c = rows[i].charAt(y);
            if(c == '#'){
                sumNeighbour++;
                break;
            }else if(c == 'L'){
                break;
            }
        }
        // right
        for(int i = y+1; i < rows[x].length(); i++){
            char c = rows[x].charAt(i);
            if(c == '#'){
                sumNeighbour++;
                break;
            }else if(c == 'L'){
                break;
            }
        }
        // left
        for(int i = y-1; i >= 0; i--){
            char c =rows[x].charAt(i);
            if( c == '#'){
                sumNeighbour++;
                break;
            }else if(c == 'L'){
                break;
            }
        }

        // up left
        for(int i = 1; (x - i >= 0) && (y - i >= 0); i++){
            char c = rows[x - i].charAt(y - i);
            if(c == '#'){
                sumNeighbour++;
                break;
            }else if(c == 'L'){
                break;
            }
        }
        // upright
        for(int i = 1; (x - i >= 0) && (y + i < rows[x].length()); i++){
            char c = rows[x - i].charAt(y + i);
            if(c == '#'){
                sumNeighbour++;
                break;
            }else if(c == 'L'){
                break;
            }
        }
        // down left
        for(int i = 1; (x + i < rows.length) && (y - i >= 0); i++){
            char c = rows[x + i].charAt(y - i);
            if(c == '#'){
                sumNeighbour++;
                break;
            }else if(c == 'L'){
                break;
            }
        }
        // downright
        for(int i = 1; (x + i < rows.length) && (y + i < rows[x].length()); i++){
            char c = rows[x + i].charAt(y + i);
            if(c == '#'){
                sumNeighbour++;
                break;
            }else if(c == 'L'){
                break;
            }
        }
        return sumNeighbour;
    }



    private int directNeighbour(int x, int y) {
        int sumNeighbours = 0;
        if (x > 0) {
            if (y > 0) {
                sumNeighbours += rows[x - 1].charAt(y - 1) == '#' ? 1 : 0;

            }
            if (y + 1 < rows[x].length()) {
                sumNeighbours += rows[x - 1].charAt(y + 1) == '#' ? 1 : 0;
            }
            sumNeighbours += rows[x - 1].charAt(y) == '#' ? 1 : 0;
        }
        if(y>0) {
            sumNeighbours += rows[x].charAt(y - 1) == '#' ? 1 : 0;
        }
        if(y+1 < rows[x].length()) {
            sumNeighbours += rows[x].charAt(y + 1) == '#' ? 1 : 0;
        }
        if (x + 1 < rows.length ) {
            if (y > 0) {
                sumNeighbours += rows[x + 1].charAt(y - 1) == '#' ? 1 : 0;
            }
            if (y + 1 < rows[x].length()) {
                sumNeighbours += rows[x + 1].charAt(y + 1) == '#' ? 1 : 0;
            }
            sumNeighbours += rows[x + 1].charAt(y) == '#' ? 1 : 0;
        }
        return sumNeighbours;
    }

    private void shuffleSeats(int maxNeighbors, boolean part2){
        String[] copy = new String[rows.length];
        System.arraycopy(rows, 0, copy, 0, rows.length);
        do {
            System.arraycopy(copy, 0, rows, 0, rows.length);
            for (int i = 0; i < rows.length; ++i) {
                for (int j = 0; j < rows[i].length(); ++j) {
                    char c = rows[i].charAt(j);
                    if (c != '.') {
                        int neighbours = countNeighbours(i, j, part2);
                        if (c == 'L') {
                            if (neighbours == 0) {
                                copy[i] = copy[i].substring(0, j) + "#" + copy[i].substring(j + 1);
                            }
                        } else {
                            if (neighbours >= maxNeighbors) {
                                copy[i] = copy[i].substring(0, j) + "L" + copy[i].substring(j + 1);
                            }
                        }
                    }
                }
            }
        } while (!Arrays.equals(copy, rows));
    }

    private int countOccupiedSeats() {
        int sum = 0;
        for (String r : rows) {
            sum += r.chars().filter(ch -> ch == '#').count();
        }
        return sum;
    }

    private void parse() {
        rows = getInput().split("\n");
    }
}
