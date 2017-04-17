package projlab.rail.exception;

import projlab.rail.logic.MovingEntity;

public class CrashException extends TrainException {

    public CrashException() {
        super("Train crash!");
    }

}