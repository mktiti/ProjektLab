package projlab.rail.logic;

import java.util.HashSet;
import java.util.Set;

public class Station extends Rail{
    private final Color color;

    private final Set<Color> people = new HashSet<>(Color.values().length);

    public Station(Color color) {
        this.color = color;
    }

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
