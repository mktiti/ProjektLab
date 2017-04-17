package projlab.rail.exception;

import projlab.rail.logic.MovingEntity;
import projlab.rail.logic.StaticEntity;

public class IllegalMoveException extends TrainException {
    public final StaticEntity current;
    public final StaticEntity from;

    public IllegalMoveException(StaticEntity current, StaticEntity from) {
        super(current + " does not have connection to " + from);
        this.current = current;
        this.from = from;
    }
}