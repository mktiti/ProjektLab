package projlab.rail;

import projlab.rail.logic.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.LinkedList;

public class GameEngine {

    private LinkedList<StaticEntity> statics;
    private LinkedList<Locomotive> locos;
    private Tunnel activeTunnelA;
    private Tunnel activeTunnelB;
    private HiddenRail entryPoint;
    private StaticEntity last;

    public void connect(MovingEntity first, MovingEntity second){
        //TODO: Implement this method
        System.out.println("GameEngine.connect called");
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

}