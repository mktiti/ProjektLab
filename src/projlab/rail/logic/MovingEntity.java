package projlab.rail.logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class MovingEntity {
    protected StaticEntity currentPosition;
    protected StaticEntity lastPosition;
    public MovingEntity next;

    public StaticEntity next(){
        //TODO: Implement this method
        throw new NotImplementedException();
    }

    public boolean move(){
        //TODO: Implement this method
        throw new NotImplementedException();
    }


}