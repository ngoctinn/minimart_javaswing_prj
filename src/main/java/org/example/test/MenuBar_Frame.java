package org.example.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuBar_Frame extends JFrame implements ActionListener {
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;

    public MenuBar_Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLayout(new FlowLayout());

    }

    public static void main(String[] args) {

    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        System.out.println("Action performed");
    }
}
