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

    private final int WINDOW_WIDTH = 300;
    private final int WINDOW_HEIGHT = 400;

    private final Dimension FILL = new Dimension(WINDOW_WIDTH/6, WINDOW_HEIGHT/6);

    JButton ngame, cont, quit;

    private class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ngame) {
                new MainWindow(0);
            } else if (e.getSource() == cont) {
                new MainWindow(ProgressManager.getProgress());
            } else if (e.getSource() == quit) {
                System.exit(0);
            }
        }
    }



    private void init(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        ButtonListener listener = new ButtonListener();
        getContentPane().setBackground(Color.lightGray);

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        ngame = new JButton("New Game");
        ngame.setBackground(Color.darkGray);
        ngame.setForeground(Color.white);

        cont = new JButton("Continue");
        cont.setBackground(Color.darkGray);
        cont.setForeground(Color.white);

        quit = new JButton("Quit");
        quit.setBackground(Color.darkGray);
        quit.setForeground(Color.white);

        ngame.setAlignmentX(Component.CENTER_ALIGNMENT);
        cont.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);

        ngame.addActionListener(listener);
        cont.addActionListener(listener);
        quit.addActionListener(listener);

        add(new Box.Filler(FILL, FILL, FILL));
        this.add(ngame);
        add(new Box.Filler(FILL, FILL, FILL));
        this.add(cont);
        add(new Box.Filler(FILL, FILL, FILL));
        this.add(quit);

        setVisible(true);
    }

    MenuWindow() {
        init();
    }

    public static void main(String[] args){
        // Kis gyorsító mágia
        new Thread(ResourceManager::init).start();
        new MenuWindow();
    }
}
