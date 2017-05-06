package projlab.rail.logic;

import javafx.util.Pair;
import projlab.rail.exception.IllegalMoveException;
import projlab.rail.exception.IllegalSwitchStateException;
import projlab.rail.ui.Direction;
import projlab.rail.ui.ResourceManager;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Switch extends StaticEntity {
    /** Input, connected to either A or B */
    public StaticEntity input;
    /** A connection */
    public StaticEntity outputA;
    /** B connection */
    public StaticEntity outputB;
    /** Whether A or B is connected to the input */
    public boolean isAActive = true;

    /** A list of all the connections */
    private final ArrayList<Pair<StaticEntity,Direction>> conns = new ArrayList<>(3);

    private final Direction inDir;

    public Switch(Direction inDir) {
        this.inDir = inDir;
        for (int i = 0; i < 3; i++) {
            conns.add(null);
        }
    }

    /** connects A connection */
    public void connectA(StaticEntity a) {
        conns.set(1, new Pair<>(a,inDir.rotateCW()));
        outputA = a;
    }
    /** connects B connection */
    public void connectB(StaticEntity b) {
        conns.set(2, new Pair<>(b, inDir.rotateCCW()));
        outputB = b;
    }
    /** connects input */
    public void connectIn(StaticEntity in) {
        conns.set(0, new Pair<>(in, inDir));
        input = in;
    }

    @Override
    public List<Pair<StaticEntity,Direction>> getConnections() {
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

    @Override
    public BufferedImage image() {
        return ResourceManager.getSwitch(inDir, isAActive);
    }

    /**
     * Changes the state of the switch
     */
    public void toggle() {
        isAActive = !isAActive;
    }
}
