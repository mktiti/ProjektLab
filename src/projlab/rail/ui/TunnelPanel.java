package projlab.rail.ui;

import projlab.rail.GameEngine;
import projlab.rail.logic.Tunnel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TunnelPanel extends EntityPanel implements MouseListener {

    public TunnelPanel(Tunnel entity, GameEngine engine) {
        super(entity, engine);
        addMouseListener(this);
    }

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