package projlab.rail.logic;

import java.util.Set;

public abstract class StaticEntity {

    protected MovingEntity vehicle;

    public abstract Set<StaticEntity> getConnections();

    public Color getColor() {
        return null;
    }

    public boolean isHidden() {
        return false;
    }

    public abstract StaticEntity next(StaticEntity previous);

    public boolean hasVehicle() {
        return vehicle != null;
    }

    public boolean board(Color c){
        return false;
    }

}