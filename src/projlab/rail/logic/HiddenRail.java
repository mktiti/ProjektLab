package projlab.rail.logic;

public class HiddenRail extends Rail {

    public HiddenRail() {
        super(aDir, bDir);
    }

    /** Should not be drawn */
    @Override
    public boolean isHidden() {
        System.out.println("HiddenRail.getColor called");
        return true;
    }
}
