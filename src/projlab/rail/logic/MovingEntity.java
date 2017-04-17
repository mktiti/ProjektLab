package projlab.rail.logic;

import projlab.rail.exception.TrainException;

/** Represents a vehicle (Car or Locomotive) */
public abstract class MovingEntity {
    /** The position it"s occupying */
    protected StaticEntity currentPosition;
    /** The position it occupied in the last timeframe */
    protected StaticEntity lastPosition;
    /** the car connected to this vehicle (null if this is the last one) */
    public Car next;

    /**
     * Where this vehicle should go
     * @return the position it should occupy in the next timeframe
     * @throws TrainException if the route cannot be continued
     */
    public StaticEntity next() throws TrainException {
        return currentPosition.next(lastPosition);
    }

    /**
     * Moves the vehicle to the next position
     * @return whther the train should disappear
     * @throws TrainException if the route cannot be continued
     */
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

    /**
     * Sets the position of the vehicle to an arbitrary place
     * @param current the position to jump to
     * @param previous the direction it should pretend to be coming from
     */
    public void setPosition(StaticEntity current, StaticEntity previous) {
        this.currentPosition = current;
        currentPosition.vehicle = this;
        this.lastPosition = previous;
    }

}