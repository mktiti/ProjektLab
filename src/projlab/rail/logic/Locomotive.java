package projlab.rail.logic;

import projlab.rail.exception.TrainException;
import projlab.rail.ui.Direction;
import projlab.rail.ui.ResourceManager;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Locomotive extends MovingEntity {

    public Locomotive() {}

    public Locomotive(Car firstCar) {
        next = firstCar;
    }

    /**
     * The list of positions the whole train will occupy in the next timeframe. Used for crash detection.
     * @return The list of positions the whole train will occupy in the next timeframe.
     * @throws TrainException if the route cannot be continued
     */
    public LinkedList<StaticEntity> getDestination() throws TrainException {
        LinkedList<StaticEntity> ret = new LinkedList<>();

        MovingEntity temp = this;
        do {
            ret.add(temp.next());
            temp = temp.next;
        } while (temp != null);

        return ret;
    }

    @Override
    public boolean move() throws TrainException {
        super.move();
        Color color = currentPosition.getColor();

        if (color == null) {
            // Not a station
            return false;
        }
        // iterating through all cars
        Car temp = next;
        while (temp != null) {
            if (temp.color == color) {
                if (temp.hasPassengers) {
                    temp.unboard();
                    board();
                    return isEmpty();
                }
                else{
                    board();
                    return isEmpty();
                }
            } else if (temp.hasPassengers) {
                return false;
            }

            temp = temp.next;
        }
        return false;
    }

    @Override
    public BufferedImage image(Direction from, Direction to) {
        return ResourceManager.getLocomotive(from, to);
    }

    /**
     * Boards all accepted waiting passengers
     */
    private void board() {
        Car temp = next;
        while (temp != null) {
            if (!temp.hasPassengers && currentPosition.board(temp.color)) {
                temp.hasPassengers = true;
            }
            temp = temp.next;
        }
    }

    /**
     * @return Whether the train has any people on it
     */
    private boolean isEmpty() {
        Car temp = next;
        while (temp != null) {
            if (temp.hasPassengers) {
                return false;
            }
            temp = temp.next;
        }
        return true;
    }
}