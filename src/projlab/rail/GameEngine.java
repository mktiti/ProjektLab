package projlab.rail;

import projlab.rail.exception.CrashException;
import projlab.rail.exception.TrainException;
import projlab.rail.logic.*;

import java.util.*;

public class GameEngine {

    List<StaticEntity> statics = new LinkedList<>();
    List<Locomotive> locos = new LinkedList<>();
    public Tunnel activeTunnelA;
    public Tunnel activeTunnelB;
    public HiddenRail entryPoint;
    public HiddenRail entrySecond;
    private StaticEntity last;

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

    public void load(){
        //TODO: Implement this method
        System.out.println("GameEngine.load called");
    }

    public void gameOver(){
        //TODO: Implement this method
        System.out.println("GameEngine.gameOver called");
    }

    public void gameWon(){
        //TODO: Implement this method
        System.out.println("GameEngine.gameWon called");
    }

    public void setLast(StaticEntity entity){
        //TODO: Implement this method
        System.out.println("GameEngine.setLast called");
    }

    public void step() throws TrainException {
        Set<StaticEntity> occupied = new HashSet<>();
        int occupiedCount = 0;

        for (Locomotive l : locos) {
            Collection<StaticEntity> occ = l.getDestination();
            occupied.addAll(occ);
            occupiedCount += occ.size();
        }

        if (occupied.size() < occupiedCount) {
            throw new CrashException();
        }

        for (Locomotive l : locos) {
            l.move();
        }
    }

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

    private void destroyTunnel() {
        if (activeTunnelA == null || activeTunnelB == null) {
            return;
        }

        activeTunnelA.connectHidden(null);
        activeTunnelB.connectHidden(null);
    }

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