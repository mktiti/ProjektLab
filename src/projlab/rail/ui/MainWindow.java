package projlab.rail.ui;

import projlab.rail.logic.CrossRail;
import projlab.rail.logic.Rail;
import projlab.rail.logic.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MainWindow extends JFrame {

    public static final int WINDOW_SIZE = 1000;

    private final GraphicsEngine graphicsEngine = new GraphicsEngine(this);

    private static class BackgroundPanel extends JComponent {
        private BufferedImage backgroundImage;

        private BackgroundPanel(BufferedImage backgroundImage) {
            this.backgroundImage = backgroundImage;
            setPreferredSize(new Dimension(MainWindow.WINDOW_SIZE, MainWindow.WINDOW_SIZE));
            setSize(MainWindow.WINDOW_SIZE, MainWindow.WINDOW_SIZE);
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            graphics.drawImage(backgroundImage, 0, 0, this);
        }
    }


    private MainWindow() {
        init(0);
    }

    private void init(int map) {


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_SIZE, WINDOW_SIZE);
        setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE+28));
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        pack();
        graphicsEngine.init(null,null,0,0,null,0);
        setContentPane(graphicsEngine);
        setVisible(true);
        repaint();
    }

    public static void main(String[] args) {
        new MainWindow();
    }

}