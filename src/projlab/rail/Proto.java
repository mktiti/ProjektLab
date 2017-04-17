package projlab.rail;

import projlab.rail.exception.TrainException;
import projlab.rail.logic.*;

import java.util.*;

public class Proto {

    /** The types of rail */
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

    /** id counter of vehicles */
    private int movingCounter = 0;

    /** Vehicle storage */
    public class MovingStore<E extends MovingEntity> {
        final Map<Integer, E> store = new HashMap<>();

        /**
         * Gets the vehicle by id
         * @param id the id
         * @return the vehcle
         * @throws IllegalArgumentException if no vehicle is found
         */
        E get(int id) throws IllegalArgumentException {
            E ret = store.get(id);
            if (ret != null) {
                return ret;
            }
            throw new IllegalArgumentException("Illegal MovingEntity id!");
        }

        /**
         * Adds new vehicle
         * @param entity the vehilce to be added
         * @return the id of the vehicle
         */
        private int add(E entity) {
            int id = movingCounter++;
            store.put(id, entity);
            return id;
        }

        /**
         * Checks if the vehicle exists
         * @param id the of the vehicle
         * @return whether the vehicle exists
         */
        boolean contains(int id) {
            return store.get(id) != null;
        }
    }

    /** Entity storage */
    public class StaticStore<E extends StaticEntity> {
        final Map<Integer, E> store = new HashMap<>();

        /**
         * Gets the entity by id
         * @param id the id
         * @return the entity
         * @throws IllegalArgumentException if no entity is found
         */
        E get(int id) throws IllegalArgumentException {
            E ret = store.get(id);
            if (ret != null) {
                return ret;
            }
            throw new IllegalArgumentException("Illegal StaticEntity id!");
        }

        /**
         * Adds new entity
         * @param entity the entity to be added
         * @return the id of the entity
         */
        private int add(E entity) {
            statics.add(entity);
            int id = statics.size() - 1;
            store.put(id, entity);
            engine.statics.add(entity);
            return id;
        }

        /**
         * Checks if the entity exists
         * @param id the of the entity
         * @return whether the entity exists
         */
        boolean contains(int id) {
            return store.get(id) != null;
        }
    }

    public final MovingStore<Locomotive> locomotives = new MovingStore<>();
    public final MovingStore<Car> cars = new MovingStore<>();

    /** all static entities */
    final List<StaticEntity> statics = new ArrayList<>(100);

    public final StaticStore<Rail> rails = new StaticStore<>();
    public final StaticStore<Switch> switches = new StaticStore<>();
    public final StaticStore<CrossRail> crosses = new StaticStore<>();
    public final StaticStore<Station> stations = new StaticStore<>();
    public final StaticStore<Tunnel> tunnels = new StaticStore<>();

    private final GameEngine engine = new GameEngine();

    public void step() throws TrainException {
        engine.step();
    }

    public void activateTunnel(int tunnelId) throws IllegalArgumentException {
        engine.activateTunnel(tunnels.get(tunnelId));
    }

    public void deactivateTunnel(int tunnelId) throws IllegalArgumentException {
        engine.deactivateTunnel(tunnels.get(tunnelId));
    }

    public void toggle(int switchId) throws IllegalArgumentException {
        switches.get(switchId).toggle();
    }

    public void launch(int trainId) throws IllegalArgumentException, TrainException {
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
        return cars.add(new Car(color));
    }

    public int createLocomotive() {
        Locomotive locomotive = new Locomotive();
        int id = locomotives.add(locomotive);
        engine.locos.add(locomotive);
        return id;
    }

    public void connectToTrain(int trainId, int carId) throws IllegalArgumentException {
        MovingEntity host;

        if (locomotives.contains(trainId)) {
            host = locomotives.get(trainId);
        } else {
            host = cars.get(trainId);
        }

        host.next = cars.get(carId);
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
        cars.get(carId).hasPassengers = true;
    }

    public void addPerson(int stationId, Color color) throws IllegalArgumentException {
        stations.get(stationId).addPerson(color);
    }

    public static void main(String[] args) throws TrainException {
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