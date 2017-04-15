package projlab.rail;

import projlab.rail.logic.*;

import java.util.*;

public class GameEngine {

    List<StaticEntity> statics = new LinkedList<>();
    List<Locomotive> locos = new LinkedList<>();
    private Tunnel activeTunnelA;
    private Tunnel activeTunnelB;
    HiddenRail entryPoint;
    HiddenRail entrySecond;
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

    public void step() throws CrashException {
        Set<StaticEntity> occupied = new HashSet<>();
        int occupiedCount = 0;

        for (Locomotive l : locos) {
            Collection<StaticEntity> occ = l.getDestination();
            occupied.addAll(occ);
            occupiedCount += occ.size();
        }

        if (occupied.size() < occupiedCount) {
            throw new CrashException("Train crash!");
        }

        for (Locomotive l : locos) {
            l.move();
        }
    }

    public void buildTunnel(Tunnel start, Tunnel end) {
        System.out.println("GameEngine.buildTunnel called");
    }

}