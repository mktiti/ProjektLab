package projlab.rail.logic;

import java.util.Set;

public abstract class StaticEntity {

    protected MovingEntity vehicle;

    public abstract Set<StaticEntity> getConnections();

    public Color getColor() {
        System.out.println("StaticEntity.getColor called");
        return null;
    }

    public boolean isHidden() {
        System.out.println("StaticEntity.isHidden called");
        return false;
    }

    public abstract StaticEntity next(StaticEntity previous);

    public boolean hasVehicle() {
        System.out.println("StaticEntity.hasVehicle called");
        return vehicle != null;
    }

}