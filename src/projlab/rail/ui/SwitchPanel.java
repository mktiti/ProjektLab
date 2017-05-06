package projlab.rail.ui;

import projlab.rail.logic.Switch;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SwitchPanel extends EntityPanel implements MouseListener {

    public SwitchPanel(Switch entity) {
        super(entity);
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