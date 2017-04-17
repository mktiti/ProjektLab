package projlab.rail.exception;

import projlab.rail.logic.Switch;

/** Thrown when a vehicle arrives onto a switch which is in an unaccepting state (the other output is active) */
public class IllegalSwitchStateException extends TrainException {
    /** The switch the vehicle arrived to */
    public final Switch s;
    /** Whether the vehicle came from connection A or not (B) */
    public final boolean fromA;

    public IllegalSwitchStateException(Switch s, boolean fromA) {
        super("Coming to " + s + " from " + (fromA ? "A" : "B") + ", switch is in other state");
        this.s = s;
        this.fromA = fromA;
    }

}