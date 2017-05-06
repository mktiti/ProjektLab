package projlab.rail.ui;

import projlab.rail.GameEngine;
import projlab.rail.logic.StaticEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EntityPanel {

    public static final int PANEL_SIZE = 50;


    final StaticEntity entity;
    final GameEngine engine;
    final JPanel mainPanel;

    private BufferedImage image = null;

    int x, y;

    public EntityPanel(StaticEntity entity, GameEngine gameEngine, int x, int y, JPanel mainPanel) {
        this.entity = entity;
        this.engine = gameEngine;
        this.mainPanel = mainPanel;
        this.x = x;
        this.y = y;
        update();
    }

    public void update() {
        image = entity.image();
    }

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
}