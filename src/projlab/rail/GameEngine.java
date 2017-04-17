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
    public Tunnel activeTunnelA;
    public Tunnel activeTunnelB;
    /** Entry point of the level */
    public HiddenRail entryPoint;
    /** Direction the trains are arriving from to the entry point */
    public HiddenRail entrySecond;
    /** State of the game*/
    public GameState state;

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
        state = GameState.INGAME;
    }

    /** Loads the needed level */
    public void load(){
        //TODO: Implement this method
        System.out.println("GameEngine.load called");
    }

    /** Ends lost game */
    public void gameOver(){
        state = GameState.DEFEAT;
        System.out.println("Game over");
    }

    /** Ends won game */
    public void gameWon(){
        state = GameState.VICTORY;
        System.out.println("Game Won");
    }

    public void removeTrain(Locomotive l){
        for(MovingEntity current = l; current != null; current = current.next){
            current.currentPosition.vehicle = null;
            current.currentPosition = null;
            current.lastPosition = null;
        }
    }

    /** iterates one on the whole level */
    public void step() throws TrainException {
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

            /*for (Locomotive l : locos) {
                if (l.currentPosition == null)
                    continue;
                if(l.move()){

                }
            }*/
        } catch (TrainException te){
            gameOver();
            throw te;
        }
        if(locos.isEmpty())
            gameWon();
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