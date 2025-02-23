package org.example.test;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

public class MENU extends JFrame {
    public MENU() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setBackground(Color.BLACK);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        menuBar.setFont(new Font("Arial", Font.PLAIN, 20));


        JMenu menu = new JMenu("Menu");
        menu.setBackground(Color.BLACK);
        // Lấy ảnh SVG
        FlatSVGIcon icon = new FlatSVGIcon("Images/chart-column-grow-svgrepo-com.svg", 16, 16);
        menu.setIcon(icon);
        JMenu menu2 = new JMenu("Menu 2");

        JMenuItem menuItem1 = new JMenuItem("Item 1");
        menu.add(menuItem1);

        menuBar.add(menu);
        menuBar.add(menu2);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            // Cài đặt FlatLaf MacOS Light theme
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
            // Tạo giao diện MENU
            new MENU();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
