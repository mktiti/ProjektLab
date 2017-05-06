package projlab.rail.ui;

public enum Direction {
    EAST(0), WEST(1), NORTH(2), SOUTH(3);

    public final int value;

    Direction(int value) {
        this.value = value;
    }
}