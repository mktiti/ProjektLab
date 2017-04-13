package projlab.rail.logic;

public class Car extends MovingEntity {
    public boolean hasPassengers;
    public final Color color; // Null represents Coal car

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

    public void unboard() {
        hasPassengers = false;
    }
}
