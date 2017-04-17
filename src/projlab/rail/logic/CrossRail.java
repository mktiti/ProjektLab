package projlab.rail.logic;

import java.util.ArrayList;
import java.util.List;

public class CrossRail extends StaticEntity {
    private StaticEntity connectionA;
    private StaticEntity connectionB;
    private StaticEntity connectionX;
    private StaticEntity connectionY;
    private List<StaticEntity> connections = new ArrayList<>(4);

    public CrossRail() {
        for (int i = 0; i < 4; i++) {
            connections.add(null);
        }
    }

    public void connectA(StaticEntity a) {
        connections.set(0, a);
        connectionA = a;
    }

    public void connectB(StaticEntity b) {
        connections.set(1, b);
        connectionB = b;
    }

    public void connectX(StaticEntity x) {
        connections.set(2, x);
        connectionA = x;
    }

    public void connectY(StaticEntity y) {
        connections.set(3, y);
        connectionB = y;
    }

    @Override
    public List<StaticEntity> getConnections() {
        return connections;
    }

    @Override
    public StaticEntity next(StaticEntity previous) throws CrashException {
        if (previous == connectionB) {
            return connectionA;
        } else if (previous == connectionA) {
            return connectionB;
        } else if (previous == connectionX) {
            return connectionY;
        } else if (previous == connectionY) {
            return connectionX;
        } else {
            throw new CrashException("Comming to Rail from illegal direction.");
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
}