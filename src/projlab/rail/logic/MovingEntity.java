package projlab.rail.logic;

import projlab.rail.exception.TrainException;

public abstract class MovingEntity {
    public StaticEntity currentPosition;
    public StaticEntity lastPosition;
    public Car next;

    public StaticEntity next() throws TrainException {
        return currentPosition.next(lastPosition);
    }

    public boolean move() throws TrainException {
        StaticEntity temp = currentPosition;
        currentPosition = currentPosition.next(lastPosition);
        if (currentPosition == null) {
            throw new NullPointerException("Rail line ended unexpectedly!");
        }

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

    public void setPosition(StaticEntity current, StaticEntity previous) {
        this.currentPosition = current;
        currentPosition.vehicle = this;
        this.lastPosition = previous;
    }

}