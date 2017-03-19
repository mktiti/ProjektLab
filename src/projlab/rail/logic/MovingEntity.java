package projlab.rail.logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class MovingEntity {
    protected StaticEntity currentPosition;
    protected StaticEntity lastPosition;
    public MovingEntity next;

    public StaticEntity next(){
        System.out.println("StaticEntity.next called");
        return null;
    }

    public boolean move(){
        System.out.println("StaticEntity.move called");
        return false;
    }

}