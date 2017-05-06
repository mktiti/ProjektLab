package projlab.rail.logic;

import projlab.rail.exception.IllegalMoveException;
import projlab.rail.exception.IllegalSwitchStateException;
import projlab.rail.ui.Direction;

import java.util.ArrayList;
import java.util.List;

public class Switch extends StaticEntity{
    /** Input, connected to either A or B */
    public StaticEntity input;
    /** A connection */
    public StaticEntity outputA;
    /** B connection */
    public StaticEntity outputB;
    /** Whether A or B is connected to the input */
    public boolean isAActive = true;

    /** A list of all the connections */
    private final ArrayList<StaticEntity> conns = new ArrayList<>(3);

    private final Direction inDir;
    private final Direction aDir;
    private final Direction bDir;

    public Switch(Direction inDir, Direction aDir, Direction bDir) {
        this.inDir = inDir;
        this.aDir = aDir;
        this.bDir = bDir;
        for (int i = 0; i < 3; i++) {
            conns.add(null);
        }
    }

    /** connects A connection */
    public void connectA(StaticEntity a) {
        conns.set(1, a);
        outputA = a;
    }
    /** connects B connection */
    public void connectB(StaticEntity b) {
        conns.set(2, b);
        outputB = b;
    }
    /** connects input */
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

    /**
     * Changes the state of the switch
     */
    public void toggle() {
        isAActive = !isAActive;
    }
}
