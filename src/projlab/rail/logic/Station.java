package projlab.rail.logic;

import projlab.rail.ui.Direction;
import projlab.rail.ui.ResourceManager;

import java.awt.image.BufferedImage;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/** Represents a station */
public class Station extends Rail{
    /** The color of the station */
    private final Color color;

    /** The people waiting at the station */
    private final EnumSet<Color> people = EnumSet.noneOf(Color.class);

    public Station(Direction aDir, Direction bDir, Color color) {
        super(aDir, bDir);
        this.color = color;
    }

    /**
     * Adds a person to the station
     * @param color the color of the car the person is waiting for
     */
    public void addPerson(Color color) {
        if (color != this.color) {
            people.add(color);
        }
    }

    @Override
    public boolean board(Color color) {
        return people.remove(color);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    protected BufferedImage getBaseImage() {
        return ResourceManager.getStation(aDir, bDir, color, people);
    }
}
