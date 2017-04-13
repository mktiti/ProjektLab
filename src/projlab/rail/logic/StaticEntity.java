package projlab.rail.logic;

import java.util.List;

public abstract class StaticEntity {

    protected MovingEntity vehicle;

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

}