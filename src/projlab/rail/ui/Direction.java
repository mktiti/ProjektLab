package projlab.rail.ui;

public enum Direction {
    WEST(0), NORTH(1), EAST(2), SOUTH(3);

    public final int value;

    Direction(int value) {
        this.value = value;
    }

    public int offsetI(int i){
        return value == 0 ? i-1 : value == 2 ? i+1 : i;
    }

    public int offsetJ(int j){
        return value == 1 ? j+1 : value == 3 ? j-1 : j;
    }

    public Direction rotateCW(){
        return this ==  WEST ? NORTH : this == NORTH ? EAST : this == EAST ? SOUTH : WEST;
    }

    public Direction rotateCCW(){
        return this == WEST ? SOUTH : this == SOUTH ? EAST : this == EAST ? NORTH : WEST;
    }

    public Direction invert(){
        return this == WEST ? EAST : this == EAST ? WEST : this == NORTH ? SOUTH : NORTH;
    }
}