package projlab.rail;

import projlab.rail.logic.*;

import java.util.*;

public class Proto {

    public enum RailType {
        PLAIN, CROSS, SWITCH, TUNNEL;

        public static RailType lookup(String in, RailType defaultType) {
            if (in == null || (in = in.trim()).length() == 0) {
                return defaultType;
            }
            try {
                return valueOf(in.toUpperCase());
            } catch (IllegalArgumentException iea) {
                return defaultType;
            }
        }
    }

    private class StaticStore<E extends StaticEntity> {
        private final Map<Integer, E> store = new HashMap<>();

        private E get(int id) throws IllegalArgumentException {
            E ret = store.get(id);
            if (ret != null) {
                return ret;
            }
            throw new IllegalArgumentException("Illegal StaticEntity id!");
        }

        private int add(E entity) {
            statics.add(entity);
            int id = statics.size() - 1;
            store.put(id, entity);
            engine.statics.add(entity);
            return id;
        }

        private boolean contains(int id) {
            return store.get(id) != null;
        }
    }

    final List<Locomotive> locomotives = new LinkedList<>();
    final List<Car> cars = new LinkedList<>();

    final List<StaticEntity> statics = new ArrayList<>(100);

    final StaticStore<Rail> rails = new StaticStore<>();
    final StaticStore<Switch> switches = new StaticStore<>();
    final StaticStore<CrossRail> crosses = new StaticStore<>();
    final StaticStore<Station> stations = new StaticStore<>();
    final StaticStore<Tunnel> tunnels = new StaticStore<>();

    private final GameEngine engine = new GameEngine();

    public void step() throws CrashException {
        engine.step();
    }

    public boolean activateTunnel(int tunnelId) throws IllegalArgumentException {
        return false;
    }

    public boolean deactivateTunnel(int tunnelId) throws IllegalArgumentException {
        return false;
    }

    public void toggle(int switchId) throws IllegalArgumentException {
        switches.get(switchId).toggle();
    }

    public void launch(int trainId) throws IllegalArgumentException, CrashException {
        Locomotive loc = locomotives.get(trainId);

        loc.setPosition(engine.entryPoint, engine.entrySecond);
        StaticEntity pos = engine.entrySecond;
        StaticEntity posPrev = engine.entryPoint;

        for (Car car = loc.next; car != null; car = car.next) {
            System.out.println("Set up");
            car.setPosition(pos, pos.next(posPrev));
            StaticEntity tmp = pos;
            pos = pos.next(posPrev);
            posPrev = tmp;
        }
    }

    public int createRail(RailType type) {
        switch (type) {
            case CROSS:
                return crosses.add(new CrossRail());
            case SWITCH:
                return switches.add(new Switch());
            case TUNNEL:
                return tunnels.add(new Tunnel());
            default:
                return rails.add(new Rail());
        }
    }

    public int createStation(Color color) throws IllegalArgumentException {
        return stations.add(new Station(color));
    }

    public int createCar(Color color) {
        cars.add(new Car(color));
        return cars.size() - 1;
    }

    public int createLocomotive() {
        Locomotive locomotive = new Locomotive();
        locomotives.add(locomotive);
        engine.locos.add(locomotive);
        return locomotives.size() - 1;
    }

    public void connectToTrain(int trainId, int carId) throws IllegalArgumentException {
        if (trainId < 0 || trainId >= locomotives.size() || carId < 0 || carId >= cars.size()) {
            throw new IllegalArgumentException("Invalid entity id");
        }

        MovingEntity temp = locomotives.get(trainId);
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = cars.get(carId);
    }

    public void connect(int aId, StaticEntity.ConnectionType aConn, int bId, StaticEntity.ConnectionType bConn) throws IllegalArgumentException {
        if (bId < 0 || bId >= statics.size()) {
            throw new IllegalArgumentException("Invalid entity id for 'B'");
        }
        StaticEntity b = statics.get(bId);

        if (aId == -1) {
            engine.entryPoint.connectA(b);
            b.connect(engine.entryPoint, StaticEntity.ConnectionType.B);
            return;
        }

        if (aId < 0 || aId >= statics.size()) {
            throw new IllegalArgumentException("Invalid entity id for 'A'");
        }

        StaticEntity a = statics.get(aId);

        a.connect(b, aConn);
        b.connect(a, bConn);
    }

    public void addPerson(int carId) {
        if (carId < 0 || carId >= cars.size()) {
            throw new IllegalArgumentException("Invalid car id");
        }

        cars.get(carId).hasPassengers = true;
    }

    public void addPerson(int stationId, Color color) throws IllegalArgumentException {
        stations.get(stationId).addPerson(color);
    }

    public static void main(String[] args) throws CrashException {
        Proto p = new Proto();

        int lId = p.createLocomotive();
        for (Color color : Color.values()) {
            p.connectToTrain(lId, p.createCar(color));
        }
        int cId = -1;
        for (int i = 0; i < 10; i++) {
            p.connect(cId, StaticEntity.ConnectionType.A, cId = p.createRail(RailType.PLAIN), StaticEntity.ConnectionType.B);
        }

        p.launch(lId);
        for (int i = 0; i < 10; i++) {
            System.out.println("i: " + i);
            p.step();
        }
    }

}