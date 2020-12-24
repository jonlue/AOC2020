package main.days;

import main.AOCRiddle;

import java.util.ArrayList;
import java.util.List;

public class Day24 extends AOCRiddle {
    public Day24(String in, int part) {
        super(in, part);
        parse();
    }

    private List<List<String>> toFlipOver;
    private static final int SIZE = 300;
    private final int[][] floor = new int[SIZE][SIZE];
    private final int[][] neighbors = new int[SIZE][SIZE];

    @Override
    protected String solve1() {
        layPattern();
        return String.valueOf(countBlackTiles());
    }

    @Override
    protected String solve2() {
        layPattern();
        flipToDay();
        return String.valueOf(countBlackTiles());
    }

    private void flipToDay() {
        for(int d = 0; d < 100; d++) {
            countNeighbors();
            updateFloor();
        }
    }

    private void updateFloor() {
        for(int i = 0; i< SIZE; ++i){
            for(int j = 0; j < SIZE; ++j){
                if(floor[i][j] != -1) {
                    if (floor[i][j] == 1) {
                        if (neighbors[i][j] == 0 || neighbors[i][j] > 2) {
                            floor[i][j] = 0;
                        }
                    } else {
                        if (neighbors[i][j] == 2) {
                            floor[i][j] = 1;
                        }
                    }
                }
            }
        }
    }

    private void countNeighbors() {
        for(int i = 0; i< SIZE; ++i){
            for(int j = 0; j < SIZE; ++j){
                if(floor[i][j] != -1) {
                    neighbors[i][j] = countNeighbors(i, j);
                }
            }
        }

    }

    private int countNeighbors(int x, int y) {
        int sum = 0;
        // left
        if(x-2 >= 0){
            sum += floor[x-2][y];
        }
        // right
        if(x+2 < SIZE){
            sum += floor[x+2][y];
        }

        // top
        if(y-1 >= 0){
            // left
            if(x+1 < SIZE){
                sum += floor[x+1][y-1];
            }
            // right
            if(x-1 >= 0){
                sum += floor[x-1][y-1];
            }
        }

        // bottom
        if(y+1 < SIZE){
            if(x+1 < SIZE){
                sum += floor[x+1][y+1];
            }
            if(x-1 >= 0){
                sum += floor[x-1][y+1];
            }
        }

        return sum;
    }

    private void layPattern(){
        for(List<String> l : toFlipOver){
            int y = SIZE / 2;
            int x = SIZE / 2;
            for(String s : l){
                switch (s){
                    case "ne":
                        y--;
                        x++;
                        break;
                    case "nw":
                        y--;
                        x--;
                        break;
                    case "se":
                        y++;
                        x++;
                        break;
                    case "sw":
                        y++;
                        x--;
                        break;
                    case "e":
                        x+=2;
                        break;
                    case "w":
                        x-=2;
                        break;
                }
            }
            if(floor[x][y] == -1){
                floor[x][y] = 1;
            }else {
                floor[x][y] = (floor[x][y] + 1) % 2;
            }
        }
    }

    private int countBlackTiles(){
        int sum = 0;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if(floor[i][j] != -1) {
                    sum += floor[i][j];
                }
            }
        }
        return sum;
    }

    private void parse(){
        toFlipOver = new ArrayList<>();
        for(String s : getInput().split("\n")){
            List<String> temp = new ArrayList<>();
            for(int i = 0; i< s.length(); ++i){
                if(s.charAt(i) == 'n' || s.charAt(i) == 's'){
                    temp.add("" + s.charAt(i) + s.charAt(i+1));
                    ++i;
                    continue;
                }
                temp.add("" + s.charAt(i));
            }
            toFlipOver.add(temp);
        }

        for(int i = 0; i<SIZE; ++i){
            for(int j = 0; j< SIZE; ++j){
                if(i % 2 == 0){
                    if(j % 2 == 0) {
                        floor[i][j] = 0;
                        continue;
                    }
                } else{
                    if( j % 2 == 1){
                        floor[i][j] = 0;
                        continue;
                    }
                }
                floor[i][j] = -1;
                neighbors[i][j] = -1;
            }
        }

    }
}
