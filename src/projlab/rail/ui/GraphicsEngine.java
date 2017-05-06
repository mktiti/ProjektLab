package projlab.rail.ui;

import javafx.util.Pair;
import projlab.rail.GameEngine;
import projlab.rail.exception.IllegalMoveException;
import projlab.rail.logic.*;

import java.util.ArrayList;
import java.util.List;


public class GraphicsEngine {
    private static final int SIZE = 20;
    private EntityPanel[][] entities;
    private GameEngine engine;

    private void initPanel(int i, int j, ArrayList<StaticEntity> processedElements, StaticEntity active){
        processedElements.add(active);

        if(active.getClass() == Tunnel.class)
            entities[i][j] = new TunnelPanel((Tunnel)active);
        else if(active.getClass() == Switch.class)
            entities[i][j] = new SwitchPanel((Switch)active);
        else
            entities[i][j] = new EntityPanel(active);

        for(Pair<StaticEntity,Direction> pair: active.getConnections()){
            if(pair.getKey() == null || pair.getValue() == null)
                continue;
            if(processedElements.contains(pair.getKey()))
                continue;
            initPanel(pair.getValue().offsetI(i), pair.getValue().offsetJ(j), processedElements, pair.getKey());
        }
    }

    public void init(HiddenRail entry, List<Tunnel> tunnels, int startX, int startY){
        entities = new EntityPanel[SIZE][];
        for(int i = 0; i < SIZE; i++)
            entities[i] = new EntityPanel[SIZE];
        int i = startY;
        int j = startX;
        ArrayList<StaticEntity> processedElements = new ArrayList<>();

        try {
            StaticEntity current = entry.next(engine.entrySecond);
            initPanel(startX, startY, processedElements, current);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }

    }


    public void draw(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++)
            {
                if(entities[i][j] != null)
                    entities[i][j].update();
            }
        }
    }

}
