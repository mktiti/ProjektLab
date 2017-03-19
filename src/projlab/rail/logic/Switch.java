package projlab.rail.logic;

import java.util.Set;

public class Switch extends StaticEntity{
    public StaticEntity input;
    public StaticEntity outputA;
    public StaticEntity outputB;
    public boolean isAActive;

    public void connectA(StaticEntity a){
        System.out.println("Switch.connectA called");
    }

    public void connectB(StaticEntity b){
        System.out.println("Switch.connectB called");
    }

    public void connectIn(StaticEntity in){
        System.out.println("Switch.connectIn called");
    }

    @Override
    public Set<StaticEntity> getConnections(){
        System.out.println("Switch.getConnections called");
        return null;
    }

    @Override
    public StaticEntity next(StaticEntity previous){
        System.out.println("Switch.next called");
        return null;
    }

    public void toggle(){
        System.out.println("Switch.toggle called");
    }
}
