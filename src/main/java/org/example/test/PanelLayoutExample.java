package org.example.test;

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
public class PanelLayoutExample {
    public static void main(String[] args) {
        // Tạo JFrame
        JFrame frame = new JFrame("Panel Layout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        // hiển thị full màn hình
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // Tạo JPanel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.RED);
        leftPanel.setLayout(new BorderLayout());
        //chỉnh kích thước
        leftPanel.setPreferredSize(new Dimension(250, 0));

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLUE);
        rightPanel.setLayout(new BorderLayout());

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);


        JPanel leftPanel1 = new JPanel();
        leftPanel1.setBackground(Color.GREEN);
        leftPanel1.setPreferredSize(new Dimension(0, 100));

        JPanel leftPanel2 = new JPanel();
        leftPanel2.setBackground(Color.YELLOW);

        leftPanel.add(leftPanel1, BorderLayout.NORTH);
        leftPanel.add(leftPanel2, BorderLayout.CENTER);

        JPanel rightPanel1 = new JPanel();
        rightPanel1.setLayout(new BorderLayout());
        rightPanel1.setBackground(Color.PINK);
        rightPanel1.setPreferredSize(new Dimension(0, 100));

        JPanel rightPanel2 = new JPanel();
        rightPanel2.setBackground(Color.ORANGE);

        rightPanel.add(rightPanel1, BorderLayout.NORTH);
        rightPanel.add(rightPanel2, BorderLayout.CENTER);

        JPanel rightPanel11 = new JPanel();
        rightPanel11.setBackground(Color.MAGENTA);
        rightPanel11.setPreferredSize(new Dimension(200, 0));

        JPanel rightPanel12 = new JPanel();
        rightPanel12.setBackground(Color.CYAN);

        rightPanel1.add(rightPanel11, BorderLayout.WEST);
        rightPanel1.add(rightPanel12, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
