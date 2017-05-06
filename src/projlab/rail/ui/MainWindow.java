package projlab.rail.ui;

import org.xml.sax.SAXException;
import projlab.rail.GameEngine;
import projlab.rail.logic.CrossRail;
import projlab.rail.logic.Rail;
import projlab.rail.logic.Switch;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainWindow extends JFrame {

    private static final int WINDOW_SIZE = 1000;

    private final GraphicsEngine graphicsEngine = new GraphicsEngine();
    private final GameEngine gameEngine = new GameEngine();

    private static class BackgroundPanel extends JComponent {
        private BufferedImage backgroundImage;

        private BackgroundPanel(BufferedImage backgroundImage) {
            this.backgroundImage = backgroundImage;
            setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
            setSize(WINDOW_SIZE, WINDOW_SIZE);
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
        setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        try {
            gameEngine.load(0);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        add(new BackgroundPanel(ResourceManager.getMap(map)));

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }

}