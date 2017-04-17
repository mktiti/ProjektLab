package projlab.rail.exception;

import projlab.rail.logic.Tunnel;

/** Thrown when a vehicle hits an inactive tunnel opening */
public class InactiveTunnelException extends TrainException {

    /** The opening th vehicle reached */
    public final Tunnel tunnel;

    public InactiveTunnelException(Tunnel tunnel) {
        super("Running into inactive tunnel (" + tunnel + ")");
        this.tunnel = tunnel;
    }
}