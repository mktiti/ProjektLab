package projlab.rail.logic;

import projlab.rail.exception.IllegalMoveException;

import java.util.*;

/** Represents a simple rail elem */
public class Rail extends StaticEntity {
    /** Connection A */
    private StaticEntity connectionA;
    /** Connection B */
    private StaticEntity connectionB;
    /** A list of all connections */
    private List<StaticEntity> connections = new ArrayList<>(2);

    public Rail(){
        connections.add(null);
        connections.add(null);
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
