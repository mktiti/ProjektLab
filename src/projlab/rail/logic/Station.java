package projlab.rail.logic;

import java.util.HashSet;
import java.util.Set;

/** Represents a station */
public class Station extends Rail{
    /** The color of the station */
    private final Color color;

    /** The people waiting at the station */
    private final Set<Color> people = new HashSet<>(Color.values().length);

    public Station(Color color) {
        this.color = color;
    }

    /**
     * Adds a person to the station
     * @param color the color of the car the person is waiting for
     */
    public void addPerson(Color color) {
        people.add(color);
    }

    @Override
    public boolean board(Color color) {
        return people.remove(color);
    }

    @Override
    public Color getColor(){
        return color;
    }

}
