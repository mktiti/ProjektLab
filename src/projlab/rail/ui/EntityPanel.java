package projlab.rail.ui;

import projlab.rail.logic.StaticEntity;

import javax.swing.*;

public class EntityPanel extends JPanel {

    final StaticEntity entity;

    public EntityPanel(StaticEntity entity) {
        this.entity = entity;
    }
}