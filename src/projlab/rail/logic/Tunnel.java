package projlab.rail.logic;

import java.util.ArrayList;
import java.util.List;

public class Tunnel extends StaticEntity {

    public StaticEntity visibleConnection;
    public StaticEntity hiddenConnection;

    public boolean isActive;

    private final ArrayList<StaticEntity> conns = new ArrayList<>(3);

    public Tunnel() {
        for (int i = 0; i < 2; i++) {
            conns.add(null);
        }
    }

    public void connectVisible(StaticEntity visible) {
        conns.set(0, visible);
        visibleConnection = visible;
    }

    public void setHiddenConnection(StaticEntity hidden) {
        conns.set(1, hidden);
        hiddenConnection = hidden;
    }

    @Override
    public List<StaticEntity> getConnections() {
        return conns;
    }

    @Override
    public StaticEntity next(StaticEntity previous) throws CrashException {
        if (!isActive) {
            throw new CrashException("Inactive tunnel!");
        }

        if (previous == hiddenConnection) {
            return visibleConnection;
        } else if (previous == visibleConnection) {
            return hiddenConnection;
        }

        throw new CrashException("Coming to tunnel from illegal direction!");
    }
}