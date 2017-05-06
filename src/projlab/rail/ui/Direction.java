package projlab.rail.ui;

public enum Direction {
    WEST(0), NORTH(1), EAST(2), SOUTH(3);

    public final int value;

    Direction(int value) {
        this.value = value;
    }
}