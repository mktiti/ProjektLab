package projlab.rail;

import projlab.rail.exception.CrashException;
import projlab.rail.exception.TrainException;
import projlab.rail.logic.*;

import java.util.*;

/** Controls the logic of the game */
public class GameEngine {

    /** List of all static entities */
    List<StaticEntity> statics = new LinkedList<>();
    /** List of all locomotives */
    List<Locomotive> locos = new LinkedList<>();
    /** Active tunnel connections */
    private Tunnel activeTunnelA;
    private Tunnel activeTunnelB;
    /** Entry point of the level */
    public HiddenRail entryPoint;
    /** Direction the trains are arriving from to the entry point */
    public HiddenRail entrySecond;

    GameEngine() {
        HiddenRail prev = new HiddenRail();
        for (Color c : Color.values()) {
            HiddenRail current = new HiddenRail();
            prev.connectA(current);
            current.connectB(prev);
            entryPoint = current;
            entrySecond = prev;
            prev = current;
        }
    }

    /** Loads the needed level */
    public void load(){
        //TODO: Implement this method
        System.out.println("GameEngine.load called");
    }

    /** Ends lost game */
    public void gameOver(){
        //TODO: Implement this method
        System.out.println("GameEngine.gameOver called");
    }

    /** Ends won game */
    public void gameWon(){
        //TODO: Implement this method
        System.out.println("GameEngine.gameWon called");
    }

    /** iterates one on the whole level */
    public void step() throws TrainException {
        Set<StaticEntity> occupied = new HashSet<>();
        int occupiedCount = 0;

        for (Locomotive l : locos) {
            if(l.currentPosition == null)
                continue;
            Collection<StaticEntity> occ = l.getDestination();
            occupied.addAll(occ);
            occupiedCount += occ.size();
        }

        if (occupied.size() < occupiedCount) {
            throw new CrashException();
        }

        for (Locomotive l : locos) {
            if(l.currentPosition == null)
                continue;
            l.move();
        }
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