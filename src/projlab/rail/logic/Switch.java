package projlab.rail.logic;

import projlab.rail.exception.IllegalMoveException;
import projlab.rail.exception.IllegalSwitchStateException;

import java.util.ArrayList;
import java.util.List;

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
    public StaticEntity next(StaticEntity previous) throws IllegalMoveException, IllegalSwitchStateException {
        if (previous == input) {
            return isAActive ? outputA : outputB;
        } else if (previous == outputA) {
            if (isAActive) {
                return input;
            } else {
                throw new IllegalSwitchStateException(this, true);
            }
        } else if (previous == outputB) {
            if (!isAActive) {
                return input;
            } else {
                throw new IllegalSwitchStateException(this, false);
            }
        }
        throw new IllegalMoveException(this, previous);
    }

    @Override
    public void connect(StaticEntity entity, ConnectionType connectionType) throws IllegalArgumentException {
        switch (connectionType) {
            case A:
                connectA(entity);
                break;
            case B:
                connectB(entity);
                break;
            case IN:
                connectIn(entity);
                break;
            default:
                throw new IllegalArgumentException("Illegal connection type");
        }
    }

    public void toggle() {
        isAActive = !isAActive;
    }
}
