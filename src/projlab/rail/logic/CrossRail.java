package projlab.rail.logic;

import javafx.util.Pair;
import projlab.rail.exception.IllegalMoveException;
import projlab.rail.ui.Direction;
import projlab.rail.ui.ResourceManager;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CrossRail extends StaticEntity {
    /** A connection, connected to B */
    private StaticEntity connectionA;
    /** B connection, connected to A */
    private StaticEntity connectionB;
    /** X connection, connected to Y */
    private StaticEntity connectionX;
    /** Y connection, connected to X */
    private StaticEntity connectionY;
    /** A list of all connections */
    private List<Pair<StaticEntity, Direction>> connections = new ArrayList<>(4);

    public CrossRail() {
        for (int i = 0; i < 4; i++) {
            connections.add(null);
        }
    }

    /** connects A connection */
    public void connectA(StaticEntity a) {
        connections.set(0, new Pair<>(a,Direction.WEST));
        connectionA = a;
    }
    /** connects B connection */
    public void connectB(StaticEntity b) {
        connections.set(1, new Pair<>(b,Direction.EAST));
        connectionB = b;
    }
    /** connects X connection */
    public void connectX(StaticEntity x) {
        connections.set(2, new Pair<>(x,Direction.NORTH));
        connectionX = x;
    }
    /** connects Y connection */
    public void connectY(StaticEntity y) {
        connections.set(3, new Pair<>(y,Direction.SOUTH));
        connectionY = y;
    }

    @Override
    public List<Pair<StaticEntity,Direction>> getConnections() {
        return connections;
    }

    @Override
    public StaticEntity next(StaticEntity previous) throws IllegalMoveException {
        if (previous == connectionB) {
            return connectionA;
        } else if (previous == connectionA) {
            return connectionB;
        } else if (previous == connectionX) {
            return connectionY;
        } else if (previous == connectionY) {
            return connectionX;
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
            case X:
                connectX(entity);
                break;
            case Y:
                connectY(entity);
                break;
            default:
                throw new IllegalArgumentException("Illegal connection type");
        }
    }

    @Override
    public BufferedImage image() {
        BufferedImage image = ResourceManager.getCrossRail();
        if (vehicle != null) {
            boolean abLine = vehicle.lastPosition == connectionB || vehicle.lastPosition == connectionA;
            boolean fromFirst = abLine ? (vehicle.lastPosition == connectionA) : (vehicle.lastPosition == connectionX);
            Direction aDir = Direction.values()[(fromFirst ? 0 : 2) + (abLine ? 0 : 1)];
            BufferedImage vehicleImage = vehicle .image(aDir, aDir.invert());
            return ResourceManager.mergeImages(image, vehicleImage);
        }
        return image;
    }
}