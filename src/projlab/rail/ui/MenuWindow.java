package projlab.rail.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MenuWindow extends JFrame{
    private static boolean DEBUG = true;
    private final int WINDOW_WIDTH = 300;
    private final int WINDOW_HEIGHT = 400;

    private final Dimension FILL = new Dimension(WINDOW_WIDTH/6, WINDOW_HEIGHT/6);

    JButton ngame, cont, quit;
    JButton map1, map2, map3;

    private class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ngame) {
                ProgressManager.saveProgress(0);
                new MainWindow(0);
            } else if (e.getSource() == cont) {
                new MainWindow(ProgressManager.getProgress());
            } else if (e.getSource() == quit) {
                System.exit(0);
            } else if(DEBUG){
                if(e.getSource() == map1){
                    new MainWindow(0);
                }
                if(e.getSource() == map2){
                    new MainWindow(1);
                }
                if(e.getSource() == map3){
                    new MainWindow(2);
                }

            }
        }
    }

    /**
     * Initializes the Windows
     */
    private void init(){
        setTitle("<3 IIT");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        ButtonListener listener = new ButtonListener();
        getContentPane().setBackground(Color.lightGray);

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        ngame = new JButton("New Game");
        ngame.setBackground(Color.darkGray);
        ngame.setForeground(Color.white);

        if(DEBUG) {
            map1 = new JButton("1");
            map1.setBackground(Color.darkGray);
            map1.setForeground(Color.white);

            map2 = new JButton("2");
            map2.setBackground(Color.darkGray);
            map2.setForeground(Color.white);

            map3 = new JButton("3");
            map3.setBackground(Color.darkGray);
            map3.setForeground(Color.white);
        }

        cont = new JButton("Continue");
        cont.setBackground(Color.darkGray);
        cont.setForeground(Color.white);

        quit = new JButton("Quit");
        quit.setBackground(Color.darkGray);
        quit.setForeground(Color.white);

        ngame.setAlignmentX(Component.CENTER_ALIGNMENT);
        if(DEBUG) {
            map1.setAlignmentX(Component.CENTER_ALIGNMENT);
            map2.setAlignmentX(Component.CENTER_ALIGNMENT);
            map3.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        cont.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);

        ngame.addActionListener(listener);
        cont.addActionListener(listener);
        quit.addActionListener(listener);
        if(DEBUG) {
            map1.addActionListener(listener);
            map2.addActionListener(listener);
            map3.addActionListener(listener);
        }

        add(new Box.Filler(FILL, FILL, FILL));
        this.add(ngame);

        if(DEBUG) {
            this.add(map1);
            this.add(map2);
            this.add(map3);
        }

        add(new Box.Filler(FILL, FILL, FILL));
        this.add(cont);
        add(new Box.Filler(FILL, FILL, FILL));
        this.add(quit);

        setVisible(true);
    }

    /**
     * Creates and initializes a MenuWindow
     */
    MenuWindow() {
        init();
    }

    /**
     * Program entry point
     * @param args Arguments
     */
    public static void main(String[] args){
        // Kis gyorsító mágia
        new Thread(ResourceManager::init).start();
        new MenuWindow();
    }
}
