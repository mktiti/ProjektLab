package projlab.rail.exception;

import projlab.rail.logic.MovingEntity;
import projlab.rail.logic.Tunnel;

public class InactiveTunnelException extends TrainException {

    public final Tunnel tunnel;

    public InactiveTunnelException(Tunnel tunnel) {
        super("Running into inactive tunnel (" + tunnel + ")");
        this.tunnel = tunnel;
    }
}