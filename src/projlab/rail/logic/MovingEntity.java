package projlab.rail.logic;

public abstract class MovingEntity {
    protected StaticEntity currentPosition;
    protected StaticEntity lastPosition;
    public Car next;

    public StaticEntity next(){
        return currentPosition.next(lastPosition);
    }

    public boolean move(){
        StaticEntity temp = currentPosition;
        currentPosition = currentPosition.next(lastPosition);
        currentPosition.vehicle = this;
        if (temp.vehicle == this) {
            temp.vehicle = null;
        }
        lastPosition = temp;
        if (next != null) {
            next.move();
        }

        return false;
    }

}