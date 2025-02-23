package org.example.test;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;

public class SVGIconExample {
    public static void main(String[] args) {
        JButton button = new JButton("Click Me");
        button.setIcon(new FlatSVGIcon("Images/search-svgrepo-com.svg", 16, 16)); // Kích thước 16x16

        JFrame frame = new JFrame("FlatLaf SVG Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(button);
        frame.pack();
        frame.setVisible(true);
    }
}
