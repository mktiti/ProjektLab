package projlab.rail.ui;

import projlab.rail.GameEngine;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainWindow extends JFrame {

    public static final int WINDOW_SIZE = 1000;

    private final GraphicsEngine graphicsEngine = new GraphicsEngine(this);
    private GameEngine gameEngine = new GameEngine();

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


    MainWindow() {
        init(0);
    }

    private void init(int map) {


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_SIZE, WINDOW_SIZE);
        setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE+28));
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        try {
            gameEngine.load(0);
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        }

        pack();
        graphicsEngine.init(gameEngine.entryPoint,null, 0, 0,gameEngine,0);
        setContentPane(graphicsEngine);
        setVisible(true);
        repaint();
    }

}