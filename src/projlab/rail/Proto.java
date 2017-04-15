package projlab.rail;

import projlab.rail.logic.*;

import java.util.LinkedList;
import java.util.List;

public class Proto {

    public enum RailType { PLAIN, CROSS, SWITCH, TUNNEL }
    public enum ConnectionType { A, B, IN, X, Y, VISIBLE, INVISIBLE }

    private final List<Locomotive> locomotives = new LinkedList<>();
    private final List<Car> cars = new LinkedList<>();
    private final List<Switch> switches = new LinkedList<>();
    private final List<Station> stations = new LinkedList<>();
    private final List<Tunnel> tunnels = new LinkedList<>();

    public void step() throws CrashException {}

    public boolean activateTunnel(int tunnelId) throws IllegalArgumentException {}

    public boolean deactivateTunnel(int tunnelId) throws IllegalArgumentException {}

    public void toggle(int switchId) throws IllegalArgumentException {}

    public void launch(int trainId) throws IllegalArgumentException {}

    public int createRail(RailType type) {}

    public int createStation(Color color) throws IllegalArgumentException {}

    public int createCar(Color color) {}

    public int createLocomotive() {}

    public int connectToTrain(int trainId, int carId) throws IllegalArgumentException {}

    private void getEntityType(int id) {}

    public void connect(int aId, ConnectionType aConn, int bId, ConnectionType bConn) throws IllegalArgumentException {

    }

    public void addPerson(int id, Color color) throws IllegalArgumentException {}

}