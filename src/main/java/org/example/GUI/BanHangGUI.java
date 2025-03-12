package org.example.GUI;

import javax.swing.*;

public class BanHangGUI extends JFrame {
    public BanHangGUI() {
        initializeFrame();
    }

    private void initializeFrame() {
        this.setTitle("Bán hàng");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new BanHangGUI();
    }
}
