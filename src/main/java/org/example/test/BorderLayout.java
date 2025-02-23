package org.example.test;

import javax.swing.*;

public class BorderLayout extends JFrame {
    public BorderLayout() {
        setTitle("BorderLayout");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.BorderLayout());

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();

        panel1.setBackground(java.awt.Color.CYAN);
        panel2.setBackground(java.awt.Color.MAGENTA);
        panel3.setBackground(java.awt.Color.ORANGE);
        panel4.setBackground(java.awt.Color.BLUE);
        panel5.setBackground(java.awt.Color.GREEN);

        panel1.add(new JLabel("Giao dịch", SwingConstants.CENTER));
        panel2.add(new JLabel("Số quỹ", SwingConstants.CENTER));
        panel3.add(new JLabel("Đối tác", SwingConstants.CENTER));
        panel4.add(new JLabel("Bán Online", SwingConstants.CENTER));
        panel5.add(new JLabel("Bán Online", SwingConstants.CENTER));

        add(panel1, java.awt.BorderLayout.NORTH);
        add(panel5, java.awt.BorderLayout.CENTER);
        add(panel2, java.awt.BorderLayout.SOUTH);
        add(panel3, java.awt.BorderLayout.WEST);
        add(panel4, java.awt.BorderLayout.EAST);




        setVisible(true);
    }

    public static void main(String[] args) {
        new BorderLayout();
    }
}
