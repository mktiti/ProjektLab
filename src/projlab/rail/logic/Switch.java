package projlab.rail.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Switch extends StaticEntity{
    public StaticEntity input;
    public StaticEntity outputA;
    public StaticEntity outputB;
    public boolean isAActive = true;

    private final ArrayList<StaticEntity> conns = new ArrayList<>(3);

    public Switch() {
        for (int i = 0; i < 3; i++) {
            conns.add(null);
        }
    }

    public void connectA(StaticEntity a) {
        conns.set(1, a);
        outputA = a;
    }

    public void connectB(StaticEntity b) {
        conns.set(2, b);
        outputB = b;
    }

    public void connectIn(StaticEntity in) {
        conns.set(0, in);
        input = in;
    }

    @Override
    public List<StaticEntity> getConnections() {
        return conns;
    }

    @Override
    public StaticEntity next(StaticEntity previous) throws CrashException {
        if (previous == input) {
            return isAActive ? outputA : outputB;
        } else if (previous == outputA) {
            if (isAActive) {
                return input;
            } else {
                throw new CrashException("Switch in bad state");
            }
        } else if (previous == outputB) {
            if (!isAActive) {
                return input;
            } else {
                throw new CrashException("Switch in bad state");
            }
        }
        throw new CrashException("Coming to switch from illegal direction!");
    }

    public void toggle() {
        isAActive = !isAActive;
    }
}
