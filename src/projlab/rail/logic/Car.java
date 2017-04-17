package projlab.rail.logic;

public class Car extends MovingEntity {
    /** Whether the car carries people or not */
    public boolean hasPassengers;
    /** Null represents Coal car */
    public final Color color;

    public Car(Color color, Car next, boolean hasPassengers) {
        this.color = color;
        this.next = next;
        this.hasPassengers = hasPassengers;
    }

    public Car(Color color, Car next) {
        this(color, next, true);
    }

    public Car(Color color, boolean hasPassengers) {
        this(color, null, hasPassengers);
    }

    public Car(Color color) {
        this(color, null, true);
    }

    /**
     * Removes all passengers from the car
     */
    public void unboard() {
        hasPassengers = false;
    }
}
