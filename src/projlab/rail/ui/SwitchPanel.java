package projlab.rail.ui;

import projlab.rail.GameEngine;
import projlab.rail.logic.Switch;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SwitchPanel extends EntityPanel implements MouseListener {

    public SwitchPanel(Switch entity, GameEngine engine, int x, int y, JPanel mainPanel) {
        super(entity, engine,x,y,mainPanel);

    }

    @Override
    void click() {
        Switch s = (Switch)entity;
        if (!s.hasVehicle()) {
            s.toggle();
            update();
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        click();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}