package projlab.rail.logic;

import java.util.LinkedList;

public class Locomotive extends MovingEntity {

    public Locomotive(Car firstCar) {
        next = firstCar;
    }

    public LinkedList<StaticEntity> getDestination() throws CrashException {
        LinkedList<StaticEntity> ret = new LinkedList<>();

        MovingEntity temp = this;
        do {
            ret.add(temp.next());
            temp = temp.next;
        } while (temp != null);

        return ret;
    }

    @Override
    public boolean move() {
        super.move();
        Color color = currentPosition.getColor();

        if (color == null) {
            // Not a station
            return false;
        }

        Car temp = next;
        while (temp != null) {
            if (temp.color == color) {
                if (temp.hasPassengers) {
                    temp.unboard();

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

    private void board() {
        Car temp = next;
        while (temp != null) {
            if (!temp.hasPassengers && currentPosition.board(temp.color)) {
                temp.hasPassengers = true;
            }
            temp = temp.next;
        }
    }

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
