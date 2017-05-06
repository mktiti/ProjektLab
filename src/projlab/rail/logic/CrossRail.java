package projlab.rail.logic;

import projlab.rail.exception.IllegalMoveException;
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
    private List<StaticEntity> connections = new ArrayList<>(4);

    public CrossRail() {
        for (int i = 0; i < 4; i++) {
            connections.add(null);
        }
    }

    /** connects A connection */
    public void connectA(StaticEntity a) {
        connections.set(0, a);
        connectionA = a;
    }
    /** connects B connection */
    public void connectB(StaticEntity b) {
        connections.set(1, b);
        connectionB = b;
    }
    /** connects X connection */
    public void connectX(StaticEntity x) {
        connections.set(2, x);
        connectionX = x;
    }
    /** connects Y connection */
    public void connectY(StaticEntity y) {
        connections.set(3, y);
        connectionY = y;
    }

    @Override
    public List<StaticEntity> getConnections() {
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
        return ResourceManager.getCrossRail();
    }
}