package projlab.rail.ui;

public enum Direction {
    WEST(0), NORTH(1), EAST(2), SOUTH(3);

    public final int value;

    Direction(int value) {
        this.value = value;
    }

    /**
     * Offsets an array row coordinate
     * @param i Coordinate to offset
     * @return Offset coordinate
     */
    public int offsetI(int i){
        return value == 0 ? i-1 : value == 2 ? i+1 : i;
    }

    /**
     * Offsets an array row coordinate
     * @param j Coordinate to offset
     * @return Offset coordinate
     */
    public int offsetJ(int j){
        return value == 1 ? j-1 : value == 3 ? j+1 : j;
    }

    /**
     * Rotates the direction clockwise (eg. West -> North)
     * @return Rotated direction
     */
    public Direction rotateCW(){
        return this ==  WEST ? NORTH : this == NORTH ? EAST : this == EAST ? SOUTH : WEST;
    }

    /**
     * Rotates the direction counter-clockwise (eg. North -> West)
     * @return Rotated direction
     */
    public Direction rotateCCW(){
        return this == WEST ? SOUTH : this == SOUTH ? EAST : this == EAST ? NORTH : WEST;
    }

    /**
     * Inverts the direction (eg. South -> North)
     * @return Inverted direction
     */
    public Direction invert(){
        return this == WEST ? EAST : this == EAST ? WEST : this == NORTH ? SOUTH : NORTH;
    }
}