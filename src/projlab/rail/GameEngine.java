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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

import static projlab.rail.logic.StaticEntity.ConnectionType.*;

/** Controls the logic of the game */
public class GameEngine {

    private static final int MAP_COUNT = 5;

    /** List of all static entities */
    List<StaticEntity> statics = new LinkedList<>();
    /** List of all locomotives */
    List<Locomotive> locos = new LinkedList<>();
    /** Active tunnel connections */
    public Tunnel activeTunnelA;
    public Tunnel activeTunnelB;
    /** Entry point of the level */
    public HiddenRail entryPoint;
    /** Direction the trains are arriving from to the entry point */
    public HiddenRail entrySecond;
    /** State of the game*/
    public GameState state;

    private int map;

    public GameEngine() {
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
        Locomotive loco = new Locomotive();
        locos.add(loco);

        MovingEntity temp = loco;
        for (Color c : Color.values()) {
            Car car = new Car(c);
            temp.next = car;
            temp = car;
        }

        loco.setPosition(entryPoint, entrySecond);
        StaticEntity pos = entrySecond;
        StaticEntity posPrev = entryPoint;

        for (Car car = loco.next; car != null; car = car.next) {
            car.setPosition(pos, pos.next(posPrev));
            StaticEntity tmp = pos;
            pos = pos.next(posPrev);
            posPrev = tmp;
        }

        new Thread(() -> {



        }).start();
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
                    se = new Station(getDir(e, "dir-a"), getDir(e, "dir-b"), Color.valueOf(e.getAttribute("color")));
                    break;
                case "switch":
                    se = new Switch(getDir(e, "dir-in"));
                    break;
                case "tunnel":
                    se = new Tunnel(getDir(e, "dir-visible"));
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
                if(l.currentPosition == null)
                    continue;
                if(l.move()){
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

        HiddenRail[] tunnel = new HiddenRail[10];
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