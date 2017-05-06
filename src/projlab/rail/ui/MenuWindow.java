package projlab.rail.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MenuWindow extends JFrame{

    private static class BackgroundPanel extends JComponent {
        private BufferedImage backgroundImage;

        private BackgroundPanel(BufferedImage backgroundImage) {
            this.backgroundImage = backgroundImage;
            setPreferredSize(new Dimension(1000, 1000));
            setSize(1000, 1000);
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            graphics.drawImage(backgroundImage, 0, 0, this);
        }
    }

    public MenuWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);

        try {
            BufferedImage img = ImageIO.read(new File("bground.png"));
            add(new BackgroundPanel(img));
        }
        catch(Exception e){

        }


        setLayout(null);

        JButton button1 = new JButton();
        button1.setBounds(this.getWidth()*2/3, this.getHeight()/8, 100, 40);

        JButton button2 = new JButton();
        button2.setBounds(this.getWidth()*2/3, this.getHeight()*2/8, 100, 40);

        JButton button3 = new JButton();
        button3.setBounds(this.getWidth()*2/3, this.getHeight()*3/8, 100, 40);


        this.add(button1);
        this.add(button2);
        this.add(button3);

        setVisible(true);
    }
}
