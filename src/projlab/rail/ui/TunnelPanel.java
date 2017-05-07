package projlab.rail.ui;

import projlab.rail.GameEngine;
import projlab.rail.logic.Tunnel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TunnelPanel extends EntityPanel implements MouseListener {

    public TunnelPanel(Tunnel entity, GameEngine engine, int x, int y, JPanel mainPanel) {
        super(entity, engine,x,y,mainPanel);
    }

    @Override
    void click() {

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