package projlab.rail.ui;

import javafx.util.Pair;
import org.xml.sax.SAXException;
import projlab.rail.GameEngine;
import projlab.rail.Result;
import projlab.rail.exception.IllegalMoveException;
import projlab.rail.exception.TrainException;
import projlab.rail.logic.*;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class GraphicsEngine extends JPanel implements MouseListener {
    public static final boolean DEBUG = false;

    /**Maximum size of the grid **/
    private static final int SIZE = 20;
    private EntityPanel[][] entities;
    private GameEngine engine;
    private MainWindow parent;
    private BufferedImage backgroundImage;

    /**
     * Paints the background image and then all entities on the panel.
     * @param g Drawable graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
        draw(g);
    }

    /**
     * Constructor with pre-initialization
     * @param parent Parent window reference
     */
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
        setFocusable(true);
        addMouseListener(this);
    }

    /**
     * Recursive function which initializes it's corresponding EntityPanel
     * @param i Array row coordinate
     * @param j Array column coordinate
     * @param processedElements List of the StaticEntities which has already been processed, therefore they can be
     *                          skipped
     * @param active Currently observed StaticEntity
     */
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

    /**
     * Initializes the GraphicsEngine and loads the map with the given ID
     * @param engine GameEngine reference
     * @param map Map to load
     */
    public void init(GameEngine engine, int map){
        this.engine = engine;

        ArrayList<StaticEntity> processedElements = new ArrayList<>();

        try {
            StaticEntity current = engine.entryPoint.next(engine.entrySecond);
            initPanel(engine.entryCoords.getX(), engine.entryCoords.getY(), processedElements, current);
            for (Map.Entry<Tunnel, Point> p : engine.tunnels.entrySet()) {
                initPanel(p.getValue().getX(), p.getValue().getY(), processedElements, p.getKey());
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

    /**
     * Resets all the panels to null
     */
    private void ResetPanels(){
        entities = new EntityPanel[SIZE][];
        for(int i = 0; i < SIZE; i++)
            entities[i] = new EntityPanel[SIZE];
    }

    /**
     * Requests all EntityPanels to draw on the screen
     * @param g The graphics object to draw on
     */
    public void draw(Graphics g){
        loop: for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++)
            {
                if(entities == null)
                    continue ;
                if(entities[i] == null)
                    continue ;
                if(entities[i][j] != null)
                    entities[i][j].paintComponent(g);
            }
        }
    }

    /**
     * Invalidates the panels
     */
    public void update() {
        for(int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(entities == null)
                    continue;
                if(entities[i] == null)
                    continue;
                if (entities[i][j] != null) {
                    entities[i][j].update();
                }
            }
        }
        repaint();
    }

    /**
     * Loads the map with the give ID to the screen
     * @param mapid Map to load
     */
    private void loadMapWithID(int mapid){
        engine = new GameEngine(this);
        ResetPanels();
        ProgressManager.saveProgress(mapid);
        try{
            engine.load(mapid);
            init(engine,mapid);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    /**
     * Upon crash, shows a dialog asking the player whether to quit or restart the map
     */
    public void showCrash() {
        switch (JOptionPane.showConfirmDialog(this, "Crash! Do you want to restart?", "Crash", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE)) {
            case JOptionPane.YES_OPTION:
                loadMapWithID(engine.map);
                break;
            default:
                System.exit(0);
        }
    }

    /**
     * Upon winning the map, shows a dialog asking the player whether to quit or load the next map
     */
    public void showMapWin() {
        switch (JOptionPane.showConfirmDialog(this, "Map won! Want to load next map?", "Map victory", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
            case JOptionPane.YES_OPTION:
                loadMapWithID(engine.map+1);
                break;
            default:
                System.exit(0);
        }
    }

    /**
     * Upon victory, shows a Game Victory dialog
     */
    public void showGameWin() {
        JOptionPane.showMessageDialog(this,"You have won the game! Press OK to exit");
        System.exit(0);
    }

    /**
     * Executes the corresponding panel's click action
     * @param mouseEvent Generated mouse event
     */
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
