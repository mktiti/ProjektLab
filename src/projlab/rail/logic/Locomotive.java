package projlab.rail.logic;

import java.util.LinkedList;

public class Locomotive extends MovingEntity {

    public Locomotive(Car firstCar) {
        next = firstCar;
    }

    public LinkedList<StaticEntity> getDestination() {
        //TODO: Implement this method
        System.out.println("Locomotive.getDestination called");
        return null;
    }
}
