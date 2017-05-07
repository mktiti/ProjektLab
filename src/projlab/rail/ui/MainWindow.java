package projlab.rail.ui;

import projlab.rail.GameEngine;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends JFrame {

    public static final int WINDOW_WIDTH = 1800;
    private static final int WINDOW_HEIGHT = 1000;

    private final GraphicsEngine graphicsEngine = new GraphicsEngine(this);
    private GameEngine gameEngine = new GameEngine(graphicsEngine);

    MainWindow(int map) {
        init(map);
    }

    private void init(int map) {
        //setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        int increment = 0;
        if(System.getProperty("os.name").toUpperCase().contains("WINDOWS"));
            increment = 28;
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + increment));
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
        setVisible(true);
        pack();
    }

}