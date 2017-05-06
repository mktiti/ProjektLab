package projlab.rail.ui;

import projlab.rail.logic.*;
import projlab.rail.logic.Color;

import java.awt.*;

public class ResourceManager {

    static {
        // load
    }

    private ResourceManager() {
        throw new AssertionError("Static class!");
    }

    public static Image getRail(Direction aDir, Direction bDir) {}

    public static Image getSwitch(Direction inDir, Direction aDir, Direction bDir, boolean isAActive) {}

    public static Image getStation(Direction aDir, Direction bDir, Color color) {}

    public static Image getTunnel(Direction visibleDir, boolean isActive) {}

    public static Image getLocomotive(Direction fromDir, Direction toDir) {}

    public static Image getCar(Direction fromDir, Direction toDir, Color color, boolean hasPassengers) {}

}