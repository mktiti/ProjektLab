package projlab.rail.ui;

import projlab.rail.logic.StaticEntity;

import javax.swing.*;
import java.awt.*;

public class EntityPanel extends JPanel {

    final StaticEntity entity;

    private Image image;

    public EntityPanel(StaticEntity entity) {
        this.entity = entity;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image, 0, 0, this);
    }
}