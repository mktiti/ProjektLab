package projlab.rail.logic;

import javafx.util.Pair;
import projlab.rail.exception.IllegalMoveException;
import projlab.rail.ui.Direction;
import projlab.rail.ui.EntityPanel;
import projlab.rail.ui.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/** Represents a simple rail elem */
public class Rail extends StaticEntity {
    /** Connection A */
    private StaticEntity connectionA;
    /** Connection B */
    private StaticEntity connectionB;
    /** A list of all connections */
    private List<Pair<StaticEntity,Direction>> connections = new ArrayList<>(2);

    private final Direction aDir;
    private final Direction bDir;

    public Rail(Direction aDir, Direction bDir){
        this.aDir = aDir;
        this.bDir = bDir;
        connections.add(null);
        connections.add(null);
    }

    /** connects A connection */
    public void connectA(StaticEntity a) {
        connections.set(0, new Pair<>(a,aDir));
        connectionA = a;
    }
    /** connects B connection */
    public void connectB(StaticEntity b) {
        connections.set(1, new Pair<>(b,bDir));
        connectionB = b;
    }

    @Override
    public List<Pair<StaticEntity,Direction>> getConnections() {
        return connections;
    }

    @Override
    public StaticEntity next(StaticEntity previous) throws IllegalMoveException {
        if (previous == connectionB){
            return connectionA;
        } else if (previous == connectionA) {
            return connectionB;
        } else {
            throw new IllegalMoveException(this, previous);
        }
    }

    @Override
    public void connect(StaticEntity entity, ConnectionType connectionType) throws IllegalArgumentException {
        switch (connectionType) {
            case A:
                connectA(entity);
                break;
            case B:
                connectB(entity);
                break;
            default:
                throw new IllegalArgumentException("Illegal connection type");
        }
    }

    @Override
    public BufferedImage image() {
        if (isHidden()) return null;

        BufferedImage image = ResourceManager.getRail(aDir, bDir);
        if (vehicle != null) {
            BufferedImage vehicleImage = vehicle.image();
            int w = Math.max(image.getWidth(), image.getWidth());
            int h = Math.max(image.getHeight(), vehicleImage.getHeight());
            BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics g = combined.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.drawImage(vehicleImage, 0, 0, null);
            return combined;
        }
        return image;
    }
}
