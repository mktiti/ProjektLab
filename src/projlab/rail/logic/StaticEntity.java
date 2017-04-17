package projlab.rail.logic;

import projlab.rail.exception.TrainException;

import java.util.List;

/** Represents an elem of the level such as a rail, a switch, a crossing, etc */
public abstract class StaticEntity {

    /** The way StaticEntities are connected */
    public enum ConnectionType { A, B, IN, X, Y, VISIBLE, INVISIBLE }

    /** The currently occupying vehicle, or null if empty */
    MovingEntity vehicle;

    /**
     *   All of the entity's connections. (Used for drawings and traversing)
     *   return all of the entity's connections
     */
    public abstract List<StaticEntity> getConnections();

    /**
     * The color of the entity, if it has any (is station).
     * @return the color, or null if not a station
     */
    public Color getColor() {
        return null;
    }

    /**
     * Is the entity hidden or not.
     * @return whether the entity is hidden
     */
    public boolean isHidden() {
        return false;
    }

    /**
     * Returns the next entity the line is going through
     * @param previous the entity the travelling vehicle is coming from
     * @return the entity where the vehicle should travel to
     * @throws TrainException if the route cannot be continued
     */
    public abstract StaticEntity next(StaticEntity previous) throws TrainException;

    /**
     * Wheter the entity is occupied or not
     * @return wheter the entity is occupied or not
     */
    public boolean hasVehicle() {
        return vehicle != null;
    }

    /**
     * Boards the currently waiting passenger of the given color to the currently occupying train.
     * @param c the color of the accepted passengers
     * @return whether any passengers were removed (and should be placed on a car)
     */
    public boolean board(Color c){
        return false;
    }

    /**
     * Connects the entity to another one.
     * @param entity the entity to be connected with
     * @param connectionType the way it should be connected
     * @throws IllegalArgumentException if the entity does not support the given type of connection
     */
    public abstract void connect(StaticEntity entity, ConnectionType connectionType) throws IllegalArgumentException;

}