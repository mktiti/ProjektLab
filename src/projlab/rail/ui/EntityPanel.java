package projlab.rail.ui;

import projlab.rail.GameEngine;
import projlab.rail.logic.StaticEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EntityPanel {

    public static final int PANEL_SIZE = 200;


    final StaticEntity entity;
    final GameEngine engine;
    final JPanel mainPanel;

    private BufferedImage image = null;

    int x, y;

    /**
     * Initializes the EntityPanel at the given X, Y coordinates
     * @param entity Corresponding entity
     * @param gameEngine Current game engine
     * @param x X coordinate
     * @param y Y coordinate
     * @param mainPanel Parent panel
     */
    public EntityPanel(StaticEntity entity, GameEngine gameEngine, int x, int y, JPanel mainPanel) {
        this.entity = entity;
        this.engine = gameEngine;
        this.mainPanel = mainPanel;
        this.x = x;
        this.y = y;
        update();
    }

    /**
     * Updates the image of the entity
     */
    public void update() {
        image = entity.image();
    }

    /**
     * Paints the component on the given graphics object
     * @param graphics Graphics object to draw on
     */
    protected void paintComponent(Graphics graphics) {
        if (image != null) {
            graphics.drawImage(image.getScaledInstance(PANEL_SIZE, PANEL_SIZE, 0), x * PANEL_SIZE, y * PANEL_SIZE, mainPanel);
        }
        if (GraphicsEngine.DEBUG) {
            switch (x%4){
                case 0:
                    graphics.setColor(Color.RED);
                    break;
                case 1:
                    graphics.setColor(Color.GREEN);
                    break;
                case 2:
                case 3:
                    graphics.setColor(Color.PINK);
                    break;
                case 4:
                case 5:
                default:
                    graphics.setColor(Color.YELLOW);
            }
            if(y == 8)
                graphics.setColor(Color.ORANGE);
            graphics.fillRect(x * PANEL_SIZE, y * PANEL_SIZE, PANEL_SIZE, PANEL_SIZE);
        }
    }

    /**
     * Does nothing when clicked on a non Tunnel or Switch entity
     */
    void click() {}
}