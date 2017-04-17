package projlab.rail.logic;

import java.util.List;

public abstract class StaticEntity {

    public enum ConnectionType { A, B, IN, X, Y, VISIBLE, INVISIBLE }

    MovingEntity vehicle;

    public abstract List<StaticEntity> getConnections();

    public Color getColor() {
        return null;
    }

    public boolean isHidden() {
        return false;
    }

    public abstract StaticEntity next(StaticEntity previous) throws CrashException;

    public boolean hasVehicle() {
        return vehicle != null;
    }

    public boolean board(Color c){
        return false;
    }

    public abstract void connect(StaticEntity entity, ConnectionType connectionType) throws IllegalArgumentException;

}