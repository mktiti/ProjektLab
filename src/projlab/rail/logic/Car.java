package projlab.rail.logic;

public class Car extends MovingEntity {
    public boolean hasPassengers;
    public final Color color; // Null represents Coal car

    public Car(Color color, boolean hasPassengers) {
        this.color = color;
        this.hasPassengers = hasPassengers;
    }

    public Car(Color color) {
        this(color, false);
    }

    public void unboard() {
        hasPassengers = false;
    }
}
