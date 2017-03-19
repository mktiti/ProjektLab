package projlab.rail.logic;

import java.util.Set;

public class Switch extends StaticEntity{
    public StaticEntity input;
    public StaticEntity outputA;
    public StaticEntity outputB;
    public boolean isAActive;

    public void connectA(StaticEntity a){}

    public void connectB(StaticEntity b){}

    public void connectIn(StaticEntity in){}

    @Override
    public Set<StaticEntity> getConnections(){
        return null;
    }

    @Override
    public StaticEntity next(StaticEntity previous){ return null; }

    public void toggle(){}
}
