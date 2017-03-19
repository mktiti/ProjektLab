package projlab.rail.logic;

public class Station extends Rail{
    private Color color;

    @Override
    public Color getColor(){
        System.out.println("Station.getColor called");
        return color;
    }
}
