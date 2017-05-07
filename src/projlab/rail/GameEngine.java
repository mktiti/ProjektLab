package projlab.rail;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import projlab.rail.exception.CrashException;
import projlab.rail.exception.TrainException;
import projlab.rail.logic.*;
import projlab.rail.ui.Direction;
import projlab.rail.ui.GraphicsEngine;
import projlab.rail.ui.Point;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

import static projlab.rail.logic.StaticEntity.ConnectionType.*;

/** Controls the logic of the game */
public class GameEngine {

    public static final int MAP_COUNT = 5;

    /** List of all static entities */
    List<StaticEntity> statics = new LinkedList<>();
    /** List of all locomotives */
    List<Locomotive> locos = new LinkedList<>();
    /** Active tunnel connections */

    private final List<Switch> switches = new LinkedList<>();
    private final List<Station> stations = new LinkedList<>();
    public final Map<Tunnel, Point> tunnels = new HashMap<>();

    public Tunnel activeTunnelA;
    public Tunnel activeTunnelB;
    /** Entry point of the level */
    public HiddenRail entryPoint;
    /** Direction the trains are arriving from to the entry point */
    public HiddenRail entrySecond;
    /** State of the game*/
    public GameState state;

    private final GraphicsEngine graphicsEngine;

    public Point entryCoords;
    public int map;
    private volatile int iteration = 0;

    public GameEngine(GraphicsEngine graphicsEngine) {
        this.graphicsEngine = graphicsEngine;

        HiddenRail prev = new HiddenRail();
        for (int i = 0; i <= Color.values().length; i++) {
            HiddenRail current = new HiddenRail();
            prev.connectA(current);
            current.connectB(prev);
            entryPoint = current;
            entrySecond = prev;
            prev = current;
        }
        state = GameState.INGAME;
    }

    public void start() throws TrainException {
        iteration = 0;

        state = GameState.INGAME;

        new Thread(() -> {

            synchronized (this) {
                try {
                    loop: while (true) {
                        Thread.sleep(1000);
                        Result result = step();
                        if (graphicsEngine != null) {
                            graphicsEngine.update();

                            switch (result) {
                                case CRASH:
                                    graphicsEngine.showCrash();
                                    break loop;
                                case GAME_WIN:
                                    break loop;
                                case MAP_WIN:
                                    break loop;
                                default: break;
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public void startTrain(Locomotive train) {
        try {
            train.setPosition(entryPoint, entrySecond);
            StaticEntity pos = entrySecond;
            StaticEntity posPrev = entryPoint;

            for (Car car = train.next; car != null; car = car.next) {
                car.setPosition(pos, pos.next(posPrev));
                StaticEntity tmp = pos;
                pos = pos.next(posPrev);
                posPrev = tmp;
            }
        } catch (TrainException te) {
            te.printStackTrace();
        }
    }

    public void resetMap() {
        destroyTunnel();
        if (activeTunnelA != null) {
            activeTunnelA.isActive = false;
            activeTunnelA = null;
        }
        if (activeTunnelB != null) {
            activeTunnelB.isActive = false;
            activeTunnelB = null;
        }

        for (Switch s : switches) {
            s.isAActive = true;
        }

        for (Locomotive loco : locos) {
            removeTrain(loco);
        }

        state = GameState.INGAME;
    }

    /** Loads the needed level */
    public void load(int mapId) throws ParserConfigurationException, IOException, SAXException {
        map = mapId;

        String path = "/map/" + (mapId + 1) + ".xml";
        Map<Integer, StaticEntity> allStatics = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(GameEngine.class.getResourceAsStream(path));
        Element map = (Element)doc.getElementsByTagName("map").item(0);

        // map attributes
        NodeList l = map.getElementsByTagName("entry");
        if (l == null || l.getLength() == 0) {
            entryCoords = new Point(0, 0);
        } else {
            Element entry = (Element)l.item(0);
            entryCoords = getXY(entry);
        }


        // adding trains
        NodeList trains = byName(map, "trains").getChildNodes();
        for (int i = 0; i < trains.getLength(); i++) {
            Element train;
            if (trains.item(i).getNodeType() != Node.ELEMENT_NODE || !"train".equals(((train = (Element)trains.item(i)).getNodeName()))) continue;

            Locomotive locomotive = new Locomotive(Integer.parseInt(train.getAttribute("start")));
            locos.add(locomotive);
            NodeList cars = train.getElementsByTagName("car");

            MovingEntity current = locomotive;

            for (int j = 0; j < cars.getLength(); j++) {
                Element car = (Element)cars.item(j);
                Color color = Color.lookup(car.getAttribute("color"));
                boolean pass = car.getAttribute("pass") == null ? false : Boolean.valueOf(car.getAttribute("pass"));
                Car c = new Car(color, pass);
                current.next = c;
                current = c;
            }
        }


        // adding static entites
        NodeList entities = byName(map, "entities").getChildNodes();
        for (int i = 0; i < entities.getLength(); i++) {
            if (entities.item(i).getNodeType() != Node.ELEMENT_NODE) continue;

            Element e = (Element)entities.item(i);
            StaticEntity se;
            int id = Integer.parseInt(e.getAttribute("id"));
            switch (e.getNodeName()) {
                case "rail":
                    se = new Rail(getDir(e, "dir-a"), getDir(e, "dir-b"));
                    break;
                case "cross":
                    se = new CrossRail();
                    break;
                case "station":
                    Station st = new Station(getDir(e, "dir-a"), getDir(e, "dir-b"), Color.valueOf(e.getAttribute("color")));
                    stations.add(st);
                    se = st;
                    break;
                case "switch":
                    Switch sw = new Switch(getDir(e, "dir-in"));
                    switches.add(sw);
                    se = sw;
                    break;
                case "tunnel":
                    Tunnel t = new Tunnel(getDir(e, "dir-visible"));
                    tunnels.put(t, getXY(e));
                    se = t;
                    break;
                default:
                    se = null;
            }
            allStatics.put(id, se);
        }

        for (int i = 0; i < entities.getLength(); i++) {
            if (entities.item(i).getNodeType() != Node.ELEMENT_NODE) continue;

            Element e = (Element) entities.item(i);
            int id = Integer.parseInt(e.getAttribute("id"));
            StaticEntity se = allStatics.get(id);
            switch (e.getNodeName()) {
                case "rail":
                    connect(allStatics, e, se, A);
                    connect(allStatics, e, se, B);
                    break;
                case "cross":
                    connect(allStatics, e, se, A);
                    connect(allStatics, e, se, B);
                    connect(allStatics, e, se, X);
                    connect(allStatics, e, se, Y);
                    break;
                case "station":
                    connect(allStatics, e, se, A);
                    connect(allStatics, e, se, B);
                    break;
                case "switch":
                    connect(allStatics, e, se, IN);
                    connect(allStatics, e, se, A);
                    connect(allStatics, e, se, B);
                    break;
                case "tunnel":
                    connect(allStatics, e, se, VISIBLE);
                    break;
                default: break;
            }
        }

        statics.addAll(allStatics.values());
    }

    private static Point getXY(Element e) {
        return new Point(Integer.parseInt(e.getAttribute("x")), Integer.parseInt(e.getAttribute("y")));
    }

    private static Element byName(Element e, String name) {
        return (Element)e.getElementsByTagName(name).item(0);
    }

    private static Direction getDir(Element e, String dirName) {
        return Direction.valueOf(byName(e, dirName).getTextContent());
    }

    private void connect(Map<Integer, StaticEntity> map, Element e, StaticEntity se, StaticEntity.ConnectionType ct) {
        Node n = e.getElementsByTagName(ct.name()).item(0);
        if (n == null) return;

        int id = Integer.parseInt(n.getTextContent());
        if (id == 0) {
            entryPoint.connectA(se);
            se.connect(entryPoint, StaticEntity.ConnectionType.B);
            return;
        }

        StaticEntity toConnect = map.get(id);
        if (toConnect != null) {
            se.connect(toConnect, ct);
        }
    }

    /** Ends lost game */
    public void gameOver() {
        state = GameState.DEFEAT;
        System.out.println("Game over");
    }

    /** Ends won game */
    public void gameWon() {
        state = GameState.VICTORY;
        if(map == MAP_COUNT-1)
            graphicsEngine.showGameWin();
        else
            graphicsEngine.showMapWin();
        System.out.println("Game Won");
    }

    public void removeTrain(Locomotive l) {
        for(MovingEntity current = l; current != null; current = current.next){
            current.currentPosition.vehicle = null;
            current.currentPosition = null;
            current.lastPosition = null;
        }
    }

    /** iterates one on the whole level */
    public Result step() {
        locos.stream().filter(l -> l.startTime == iteration).forEach(this::startTrain);

        iteration++;

        try {
            Set<StaticEntity> occupied = new HashSet<>();
            int occupiedCount = 0;

            for (Locomotive l : locos) {
                if (l.currentPosition == null)
                    continue;
                Collection<StaticEntity> occ = l.getDestination();
                occupied.addAll(occ);
                occupiedCount += occ.size();
            }

            if (occupied.size() < occupiedCount) {
                throw new CrashException();
            }

            Iterator<Locomotive> i = locos.iterator();
            while (i.hasNext()){
                Locomotive l = i.next();
                if (l.currentPosition == null)
                    continue;
                if (l.move()){
                    removeTrain(l);
                    i.remove();
                }
            }

        } catch (TrainException te){
            gameOver();
            return Result.CRASH;
        }
        if (locos.isEmpty()) {
            gameWon();
            return map == MAP_COUNT ? Result.GAME_WIN : Result.MAP_WIN;
        }

        return Result.NOTHING;
    }

    /**
     * Deactivates a tunnel
     * @param tunnel the tunnel to be deactivated
     * @throws IllegalArgumentException if the tunnel is not active
     */
    public void deactivateTunnel(Tunnel tunnel) throws IllegalArgumentException {
        tunnel.isActive = false;
        if (activeTunnelA == tunnel) {
            destroyTunnel();
            activeTunnelA = null;
        } else if (activeTunnelB == tunnel) {
            destroyTunnel();
            activeTunnelB = null;
        } else {
            throw new IllegalArgumentException("Tunnel not active!");
        }
    }

    /**
     * Destroys the built tunnel
     */
    private void destroyTunnel() {
        if (activeTunnelA == null || activeTunnelB == null) {
            return;
        }

        activeTunnelA.connectHidden(null);
        activeTunnelB.connectHidden(null);
    }

    /**
     * Activates a tunnel
     * @param tunnel the tunnel to be activated
     * @throws IllegalArgumentException if the tunnel is active
     */
    public void activateTunnel(Tunnel tunnel) throws IllegalArgumentException {
        tunnel.isActive = true;
        if (activeTunnelA == null) {
            activeTunnelA = tunnel;
            buildTunnel();
        } else if (activeTunnelB == null) {
            activeTunnelB = tunnel;
            buildTunnel();
        } else {
            throw new IllegalArgumentException("Tunnel already built!");
        }
    }

    /**
     * Builds a tunnel if the two tunnels are active
     */
    private void buildTunnel() {
        if (activeTunnelA == null || activeTunnelB == null) {
            return;
        }

        int dist = tunnels.get(activeTunnelA).getDist(tunnels.get(activeTunnelB));
        HiddenRail[] tunnel = new HiddenRail[dist];
        for (int i = 0; i < tunnel.length; i++) {
            tunnel[i] = new HiddenRail();
            if (i > 0) {
                tunnel[i].connectB(tunnel[i - 1]);
                tunnel[i - 1].connectA(tunnel[i]);
            }
        }
        tunnel[0].connectB(activeTunnelA);
        activeTunnelA.connectHidden(tunnel[0]);

        tunnel[tunnel.length - 1].connectA(activeTunnelB);
        activeTunnelB.connectHidden(tunnel[tunnel.length - 1]);
    }

}