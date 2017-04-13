package projlab.rail.logic;

import java.util.*;

public class Rail extends StaticEntity {
    private StaticEntity connectionA;
    private StaticEntity connectionB;
    private List<StaticEntity> connections = new ArrayList<>(2);

    public Rail(){
        connections.add(null);
        connections.add(null);
    }

    public void connectA(StaticEntity a) {
        connections.set(0, a);
        connectionA = a;
    }

    public void connectB(StaticEntity b) {
        connections.set(1, b);
        connectionB = b;
    }

    @Override
    public List<StaticEntity> getConnections() {
        return connections;
    }

    @Override
    public StaticEntity next(StaticEntity previous) throws CrashException{
       if(previous == connectionB){
           return connectionB;
       }
       else if (previous == connectionA){
           return connectionA;
       }
       else {
           throw new CrashException("Comming to Rail from illegal direction.");
       }
    }
}
