package projlab.rail.logic;

public class HiddenRail extends Rail {

    @Override
    public boolean isHidden() {
        System.out.println("HiddenRail.getColor called");
        return true;
    }
}
