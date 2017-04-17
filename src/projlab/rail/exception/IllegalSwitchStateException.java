package projlab.rail.exception;

import projlab.rail.logic.Switch;

public class IllegalSwitchStateException extends TrainException {

    public final Switch s;
    public final boolean fromA;

    public IllegalSwitchStateException(Switch s, boolean fromA) {
        super("Coming to " + s + " from " + (fromA ? "A" : "B") + ", switch is in other state");
        this.s = s;
        this.fromA = fromA;
    }

}