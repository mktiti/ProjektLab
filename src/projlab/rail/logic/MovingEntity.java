package projlab.rail.logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class MovingEntity {
    protected StaticEntity currentPosition;
    protected StaticEntity lastPosition;
    public MovingEntity next;

    public StaticEntity next(){
        System.out.println("MovingEntity.next called");
        return null;
    }

    public boolean move(){
        System.out.println("MovingEntity.move called");
        return false;
    }

}