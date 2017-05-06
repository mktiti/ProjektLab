package projlab.rail.logic;

import projlab.rail.ui.Direction;

public class HiddenRail extends Rail {

    public HiddenRail() {
        super(Direction.EAST, Direction.WEST);
    }

    /** Should not be drawn */
    @Override
    public boolean isHidden() {
        System.out.println("HiddenRail.getColor called");
        return true;
    }
}
