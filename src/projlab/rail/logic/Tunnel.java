package projlab.rail.logic;

import java.util.Set;

public class Tunnel extends StaticEntity {

    public StaticEntity visibleConnection;
    public StaticEntity hiddenConnection = null;

    public boolean isActive;

    public void connectVisible(StaticEntity visible) {
        visibleConnection = visible;
    }

    public void setHiddenConnection(StaticEntity hidden) {
        hiddenConnection = hidden;
    }

    @Override
    public Set<StaticEntity> getConnections() {
        System.out.println("Tunnel.getConnections called");
        return null;
    }

    @Override
    public StaticEntity next(StaticEntity previous) {
        System.out.println("Tunnel.next called");
        return null;
    }
}