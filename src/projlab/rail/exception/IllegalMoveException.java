package projlab.rail.exception;

import projlab.rail.logic.StaticEntity;

/** Thrown when one shouldn't have arrived to an entity from another one but it did */
public class IllegalMoveException extends TrainException {
    /** The entity the vehicle arrived to */
    public final StaticEntity current;
    /** The entity the vehicle cane from */
    public final StaticEntity from;

    public IllegalMoveException(StaticEntity current, StaticEntity from) {
        super(current + " does not have connection to " + from);
        this.current = current;
        this.from = from;
    }
}