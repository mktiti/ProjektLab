package projlab.rail.ui;

import javafx.util.Pair;
import projlab.rail.GameEngine;
import projlab.rail.Result;
import projlab.rail.exception.IllegalMoveException;
import projlab.rail.exception.TrainException;
import projlab.rail.logic.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class GraphicsEngine extends JPanel implements MouseListener {
    public static final boolean DEBUG = false;

    private static final int SIZE = 20;
    private EntityPanel[][] entities;
    private GameEngine engine;
    private MainWindow parent;
    private BufferedImage backgroundImage;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage,0,0,this);
        draw(g);
    }

    public GraphicsEngine(MainWindow parent){
        super();
        this.parent = parent;
        setPreferredSize(new Dimension(SIZE * EntityPanel.PANEL_SIZE, SIZE * EntityPanel.PANEL_SIZE));
        setLayout(null);
        entities = new EntityPanel[SIZE][];
        for(int i = 0; i < SIZE; i++)
            entities[i] = new EntityPanel[SIZE];
        if(DEBUG){
            for(int i = 0; i < SIZE; i++){
                for(int j = 0; j < SIZE; j++) {
                    if(i%2 == 0)
                        entities[i][j] = new EntityPanel(new Rail(Direction.SOUTH, Direction.NORTH), engine, j, i, this);
                }
            }
        }

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                if (engine != null) {
                    Result result = engine.step();
                    for(int i = 0; i < SIZE; i++) {
                        for (int j = 0; j < SIZE; j++) {
                            if (entities[i][j] != null) {
                                entities[i][j].update();
                            }
                        }
                    }

                    switch (result) {
                        case CRASH:
                            showCrash();
                            break;
                        case GAME_WIN:
                            break;
                        case MAP_WIN:
                            break;
                        default: break;
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {}

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
        setFocusable(true);

        addMouseListener(this);
    }

    private void initPanel(int i, int j, ArrayList<StaticEntity> processedElements, StaticEntity active){
        if (active.isHidden()) return;

        processedElements.add(active);
        if(active instanceof Tunnel)
            entities[i][j] = new TunnelPanel((Tunnel)active, engine,i,j,this);
        else if(active instanceof Switch)
            entities[i][j] = new SwitchPanel((Switch)active,engine,i,j,this);
        else
            entities[i][j] = new EntityPanel(active, engine, i, j, this);

        for(Pair<StaticEntity, Direction> pair: active.getConnections()){
            if(pair == null) continue;
            if(pair.getKey() == null || pair.getValue() == null)
                continue;
            if(processedElements.contains(pair.getKey()))
                continue;
            initPanel(pair.getValue().offsetI(i), pair.getValue().offsetJ(j), processedElements, pair.getKey());
        }
    }

    public void init(HiddenRail entry, List<Pair<Tunnel,Point>> tunnels, int startX, int startY, GameEngine engine, int map){
        this.engine = engine;

        int i = startY;
        int j = startX;
        ArrayList<StaticEntity> processedElements = new ArrayList<>();

        try {
            if(entry != null) {
                StaticEntity current = entry.next(engine.entrySecond);
                initPanel(startX, startY, processedElements, current);
                if(tunnels != null) {
                    for (Pair<Tunnel, Point> p : tunnels)
                        initPanel(p.getValue().GetY(), p.getValue().GetX(), processedElements, p.getKey());
                }
            }
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        backgroundImage = ResourceManager.getMap(map);

        try {
            engine.start();
        } catch (TrainException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++)
            {
                if(entities[i][j] != null)
                    entities[i][j].paintComponent(g);
            }
        }
    }

    private void showCrash() {
        switch (JOptionPane.showConfirmDialog(this, "Crash! Do you want to restart?", "Crash", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE)) {
            case JOptionPane.YES_OPTION:
                engine.resetMap();
                break;
            default:
                System.exit(0);
        }
    }

    private void showMapWin() {

    }

    private void showGameWin() {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = mouseEvent.getX() / EntityPanel.PANEL_SIZE;
        int y = mouseEvent.getY() / EntityPanel.PANEL_SIZE;

        if (entities[x][y] != null) {
            entities[x][y].click();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
