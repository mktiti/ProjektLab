package projlab.rail.logic;

import java.util.Set;

public class Rail extends StaticEntity{
    public StaticEntity connectionA;
    public StaticEntity connectionB;

    public void connectA(StaticEntity a){}

    public void connectB(StaticEntity b){}

    @Override
    public Set<StaticEntity> getConnections(){
        return null;
    }

    @Override
    public StaticEntity next(StaticEntity previous){ return null; }
}
