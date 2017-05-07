package projlab.rail.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame{

    private final int WIDTH = 300;
    private final int HEIGHT = 400;

    private final Dimension FILL = new Dimension(WIDTH/6, HEIGHT/6);

    JButton ngame, cont, quit;

    private class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == ngame){
                new MainWindow();
            }
            else if(e.getSource() == cont){

            }
            else if(e.getSource() == quit){
                System.exit(0);
            }
        }
    }

    private void init(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        ButtonListener listener = new ButtonListener();

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        ngame = new JButton("New Game");

        cont = new JButton("Continue");

        quit = new JButton("Quit");

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
        new MenuWindow();
    }
}
