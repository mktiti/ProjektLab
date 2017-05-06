package projlab.rail.ui;

import projlab.rail.logic.Rail;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        Rail r = new Rail(Direction.EAST, Direction.SOUTH);
        EntityPanel ep = new EntityPanel(r);
        setLayout(null);
        ep.update();
        add(ep);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }

}