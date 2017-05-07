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

    MainWindow() {
        init(1);
    }

    private void init(int map) {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + 28));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        try {
            gameEngine.load(map);
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        }

        graphicsEngine.init(gameEngine, map);
        setContentPane(graphicsEngine);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }

}