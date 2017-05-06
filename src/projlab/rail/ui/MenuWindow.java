package projlab.rail.ui;

import javax.swing.*;
import java.awt.*;

public class MenuWindow extends JFrame{

    public MenuWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        JButton button1 = new JButton();

        FlowLayout layout = new FlowLayout(40,10,10);

        setLayout(layout);

        this.add(button1, BOTTOM_ALIGNMENT);

        setVisible(true);
    }
}
