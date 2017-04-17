package projlab.rail.logic;

import projlab.rail.exception.IllegalMoveException;

import java.util.*;

public class Rail extends StaticEntity {
    private StaticEntity connectionA;
    private StaticEntity connectionB;
    private List<StaticEntity> connections = new ArrayList<>(2);

    public Rail(){
        connections.add(null);
        connections.add(null);
    }

    public void connectA(StaticEntity a) {
        connections.set(0, a);
        connectionA = a;
    }

    public void connectB(StaticEntity b) {
        connections.set(1, b);
        connectionB = b;
    }

    @Override
    public List<StaticEntity> getConnections() {
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
}
