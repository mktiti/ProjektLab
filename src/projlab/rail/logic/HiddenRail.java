package projlab.rail.logic;

public class HiddenRail extends Rail {

    /** Should not be drawn */
    @Override
    public boolean isHidden() {
        System.out.println("HiddenRail.getColor called");
        return true;
    }
}
