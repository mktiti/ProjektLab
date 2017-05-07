package projlab.rail.logic;

import javafx.util.Pair;
import projlab.rail.exception.IllegalMoveException;
import projlab.rail.exception.InactiveTunnelException;
import projlab.rail.ui.Direction;
import projlab.rail.ui.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/** Represents a tunnel opening */
public class Tunnel extends StaticEntity {
    /** Visible connection, connected to hidden */
    public StaticEntity visibleConnection;
    /** Hidden connection, connected to visible */
    public StaticEntity hiddenConnection;
    /** Whether the tunnel is activated or not */
    public boolean isActive;
    /** A list of all connections */
    private final ArrayList<Pair<StaticEntity,Direction>> conns = new ArrayList<>(3);

    public final Direction visDir;

    public Tunnel(Direction visDire) {
        this.visDir = visDire;

        for (int i = 0; i < 2; i++) {
            conns.add(null);
        }
    }

    /** connects visible connection */
    public void connectVisible(StaticEntity visible) {
        conns.set(0, new Pair<>(visible, visDir));
        visibleConnection = visible;
    }
    /** connects hidden connection */
    public void connectHidden(StaticEntity hidden) {
        conns.set(1, new Pair<>(hidden, null));
        hiddenConnection = hidden;
    }

    @Override
    public List<Pair<StaticEntity, Direction>> getConnections() {
        return conns;
    }

    @Override
    public StaticEntity next(StaticEntity previous) throws IllegalMoveException, InactiveTunnelException {
        if (!isActive) {
            throw new InactiveTunnelException(this);
        }

        if (previous == hiddenConnection) {
            return visibleConnection;
        } else if (previous == visibleConnection) {
            return hiddenConnection;
        }

        throw new IllegalMoveException(this, previous);
    }

    @Override
    public void connect(StaticEntity entity, ConnectionType connectionType) throws IllegalArgumentException {
        switch (connectionType) {
            case VISIBLE:
                connectVisible(entity);
                break;
            case INVISIBLE:
                connectHidden(entity);
                break;
            default:
                throw new IllegalArgumentException("Illegal connection type");
        }
    }

    @Override
    public BufferedImage image() {
        BufferedImage base = ResourceManager.getTunnel(visDir, isActive);
        if (vehicle == null) {
            return base;
        } else {
            Direction from = vehicle.lastPosition == visibleConnection ? visDir : visDir.invert();
            return ResourceManager.mergeImages(base, vehicle.image(from, from.invert()));
        }
    }
}