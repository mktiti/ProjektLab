package projlab.rail.logic;

import java.util.Set;

public class Rail extends StaticEntity {
    public StaticEntity connectionA;
    public StaticEntity connectionB;

    public void connectA(StaticEntity a){
        System.out.println("Rail.connectA called");
    }

    public void connectB(StaticEntity b){
        System.out.println("Rail.connectB called");
    }

    @Override
    public Set<StaticEntity> getConnections(){
        System.out.println("Rail.getConnections called");
        return null;
    }

    @Override
    public StaticEntity next(StaticEntity previous) {
        System.out.println("Rail.next called");
        return null;
    }
}
