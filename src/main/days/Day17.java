package main.days;

import main.AOCRiddle;

public class Day17 extends AOCRiddle {
    public Day17(String in, int part) {
        super(in, part);
    }

    private static final int SIZE = 50;
    private static final int START_SLICE = SIZE/2;

    private static final char ACTIVE = '#';
    private static final char INACTIVE = '.';

    private char[][][] cube = new char[SIZE][SIZE][SIZE];
    private char[][][][] hCube = new char[SIZE][SIZE][SIZE][SIZE];
    private short[][][] cubeNeighbors = new short[SIZE][SIZE][SIZE];
    private short[][][][] hCubeNeighbors = new short[SIZE][SIZE][SIZE][SIZE];

    @Override
    protected String solve1() {
        initCube();
        parse();
        runSimulation();
        return String.valueOf(sumOfActive());
    }

    @Override
    protected String solve2() {
        initHyperCube();
        parse();
        runHyperSimulation();
        return String.valueOf(sumOfHyperActive());
    }

    private void runSimulation(){
        for(int i = 0; i < 6; i++){
            countNeighbors();
            updateCube();
        }
    }
    private void runHyperSimulation(){
        for (int i = 0; i< 6; i++){
            countHyperNeighbors();
            updateHyperCube();
        }
    }

    private void countNeighbors() {
        for (int i = 0; i < cube.length; ++i) {
            for (int j = 0; j < cube[0].length; ++j) {
                for (int k = 0; k < cube[0][0].length; ++k) {
                    cubeNeighbors[i][j][k] = countNeighbor(i, j, k);
                }
            }
        }
    }

    private void countHyperNeighbors() {
        for (int i = 0; i < hCube.length; ++i) {
            for (int j = 0; j < hCube[0].length; ++j) {
                for (int k = 0; k < hCube[0][0].length; ++k) {
                    for (int w = 0; w < hCube[0][0][0].length; ++w){
                        hCubeNeighbors[i][j][k][w] = countHyperNeighbor(i, j, k, w);
                    }
                }
            }
        }
    }

    private short countNeighbor(int x, int y, int z) {
        short count = 0;
        for (int i = Math.max(0, x - 1); i <= Math.min(SIZE-1, x + 1); ++i) {
            for (int j = Math.max(0, y - 1); j <= Math.min(SIZE-1, y + 1); ++j) {
                for (int k = Math.max(0, z - 1); k <= Math.min(SIZE-1, z + 1); ++k) {
                    if(i == x && j == y && k == z){
                        continue;
                    }
                    if(cube[i][j][k] == ACTIVE){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private short countHyperNeighbor(int x, int y, int z, int a) {
        short count = 0;
        for (int i = Math.max(0, x - 1); i <= Math.min(SIZE-1, x + 1); ++i) {
            for (int j = Math.max(0, y - 1); j <= Math.min(SIZE-1, y + 1); ++j) {
                for (int k = Math.max(0, z - 1); k <= Math.min(SIZE-1, z + 1); ++k) {
                    for (int w = Math.max(0, a - 1); w <= Math.min(SIZE-1, a + 1); ++w) {
                        if (i == x && j == y && k == z && w == a) {
                            continue;
                        }
                        if (hCube[i][j][k][w] == ACTIVE) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    private void updateCube() {
        for (int i = 0; i < cube.length; ++i) {
            for (int j = 0; j < cube[0].length; ++j) {
                for (int k = 0; k < cube[0][0].length; ++k) {
                    if(cube[i][j][k] == INACTIVE){
                        if(cubeNeighbors[i][j][k] == 3) {
                            cube[i][j][k] = ACTIVE;
                        }
                    }else{
                        if(!(cubeNeighbors[i][j][k] == 2 || cubeNeighbors[i][j][k] == 3)) {
                            cube[i][j][k] = INACTIVE;
                        }
                    }
                }
            }
        }
    }

    private void updateHyperCube() {
        for (int i = 0; i < hCube.length; ++i) {
            for (int j = 0; j < hCube[0].length; ++j) {
                for (int k = 0; k < hCube[0][0].length; ++k) {
                    for (int w = 0; w < hCube[0][0][0].length; ++w) {
                        if (hCube[i][j][k][w] == INACTIVE) {
                            if (hCubeNeighbors[i][j][k][w] == 3) {
                                hCube[i][j][k][w] = ACTIVE;
                            }
                        } else {
                            if (!(hCubeNeighbors[i][j][k][w] == 2 || hCubeNeighbors[i][j][k][w] == 3)) {
                                hCube[i][j][k][w] = INACTIVE;
                            }
                        }
                    }
                }
            }
        }
    }

    private long sumOfActive(){
        long sum = 0;
        for (char[][] slice : cube) {
            for (char[] line : slice) {
                for (char point : line) {
                    if (point == ACTIVE) {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    private long sumOfHyperActive(){
        long sum = 0;
        for (char[][][] cub : hCube) {
            for (char[][] slice : cub) {
                for (char[] line : slice) {
                    for(char point : line) {
                        if (point == ACTIVE) {
                            sum++;
                        }
                    }
                }
            }
        }
        return sum;
    }

    private void parse(){
        int i = START_SLICE;
        int w = START_SLICE;
        for(String s : getInput().split("\n")){
            int j = START_SLICE;
            for(char c : s.toCharArray()){
                hCube[START_SLICE][START_SLICE][i][j] = c;
                cube[START_SLICE][i][j] = c;
                j++;
            }
            i++;
        }
    }

    private void initCube(){
        for (int i = 0; i < cube.length; ++i) {
            for (int j = 0; j < cube[0].length; ++j) {
                for (int k = 0; k < cube[0][0].length; ++k) {
                    cube[i][j][k] = INACTIVE;
                }
            }
        }
    }
    private void initHyperCube(){
        for (int i = 0; i < hCube.length; ++i) {
            for (int j = 0; j < hCube[0].length; ++j) {
                for (int k = 0; k < hCube[0][0].length; ++k) {
                    for(int w = 0; w < hCube[0][0][0].length; ++ w) {
                        hCube[i][j][k][w] = INACTIVE;
                    }
                }
            }
        }
    }

}
