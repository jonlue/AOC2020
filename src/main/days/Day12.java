package main.days;

import main.AOCRiddle;


public class Day12 extends AOCRiddle {
    public Day12(String in, int part) {
        super(in, part);
        parse();
    }

    private String[] actions;
    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;

    @Override
    protected String solve1() {
        int[] t = driveShip();
        int result = Math.abs(t[0]) + Math.abs(t[1]);
        return String.valueOf(result);
    }

    @Override
    protected String solve2() {
        int[] t = moveWaypoint();
        int result = Math.abs(t[0]) + Math.abs(t[1]);
        return String.valueOf(result);
    }

    private int[] moveWaypoint() {
        int[] wayPoint = {1,10};
        int[] coordinates = {0,0};
        for(String action : actions){
            int number = Integer.parseInt(action.substring(1));
            switch (action.charAt(0)) {
                case 'N':
                    wayPoint[0] += number;
                    break;
                case 'S':
                    wayPoint[0] -= number;
                    break;
                case 'E':
                    wayPoint[1] += number;
                    break;
                case 'W':
                    wayPoint[1] -= number;
                    break;
                case 'L':
                    turnWaypoint(wayPoint, number, false);
                    break;
                case 'R':
                    turnWaypoint(wayPoint, number, true);
                    break;
                case 'F':
                    coordinates[0] += wayPoint[0] * number;
                    coordinates[1] += wayPoint[1] * number;
            }
        }
        return coordinates;
    }

    private void turnWaypoint(int[] wayPoint, int number, boolean clockwise) {
        int times = number / 90;
        int north = wayPoint[0];
        int east = wayPoint[1];
        switch (times){
            case 1:
                if(clockwise){
                    wayPoint[0] = -east;
                    wayPoint[1] = north;
                }else{
                    wayPoint[0] = east;
                    wayPoint[1] = -north;
                }
                break;
            case 2:
                wayPoint[0] = -wayPoint[0];
                wayPoint[1] = -wayPoint[1];
                break;
            case 3:
                if(clockwise){
                    wayPoint[0] = east;
                    wayPoint[1] = -north;
                }else{
                    wayPoint[0] = -east;
                    wayPoint[1] = north;
                }
                break;
        }
    }

    private int[] driveShip(){
        int direction = EAST;
        int[] coordinates = {0,0};
        for(String action : actions){
            int number = Integer.parseInt(action.substring(1));
            switch (action.charAt(0)){
                case 'N':
                    coordinates[0] += number;
                    break;
                case 'S':
                    coordinates[0] -= number;
                    break;
                case 'E':
                    coordinates[1] += number;
                    break;
                case 'W':
                    coordinates[1] -= number;
                    break;
                case 'L':
                    direction = turn(direction, false, number);
                    break;
                case 'R':
                    direction = turn(direction, true, number);
                    break;
                case 'F':
                    switch (direction){
                        case NORTH:
                            coordinates[0] += number;
                            break;
                        case EAST:
                            coordinates[1] += number;
                            break;
                        case SOUTH:
                            coordinates[0] -= number;
                            break;
                        case WEST:
                            coordinates[1] -= number;
                            break;
                    }
                    break;
            }
        }
        return coordinates;
    }

    private int turn(int dir, boolean clockwise, int degrees) {
        if(clockwise){
            dir += degrees / 90;
        }else{
            dir += 3 * (degrees / 90);
        }
        dir %= 4;
        return dir;
    }

    private void parse(){
        actions = getInput().split("\n");
    }
}
