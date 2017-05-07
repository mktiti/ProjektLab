package projlab.rail.ui;

import projlab.rail.GameEngine;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainWindow extends JFrame {

    public static final int WINDOW_WIDTH = 1800;
    private static final int WINDOW_HEIGHT = 1000;

    private final GraphicsEngine graphicsEngine = new GraphicsEngine(this);
    private GameEngine gameEngine = new GameEngine();

    private static class BackgroundPanel extends JComponent {
        private BufferedImage backgroundImage;

        private BackgroundPanel(BufferedImage backgroundImage) {
            this.backgroundImage = backgroundImage;
            setPreferredSize(new Dimension(MainWindow.WINDOW_WIDTH, MainWindow.WINDOW_HEIGHT));
            setSize(MainWindow.WINDOW_WIDTH, MainWindow.WINDOW_HEIGHT);
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            graphics.drawImage(backgroundImage, 0, 0, this);
        }
    }


    MainWindow() {
        init(1);
    }

    private void init(int map) {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT+28));
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        try {
            gameEngine.load(1);
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        }

        pack();
        graphicsEngine.init(gameEngine.entryPoint,null, 0, 0,gameEngine,map);
        setContentPane(graphicsEngine);
        setVisible(true);
        repaint();
    }

}