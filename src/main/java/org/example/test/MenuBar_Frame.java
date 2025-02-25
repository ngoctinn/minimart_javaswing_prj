package org.example.test;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuBar_Frame extends JFrame implements ActionListener {
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;

    public MenuBar_Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Menu Bar Example");
        FlatSVGIcon icon = new FlatSVGIcon("Images/shop-2-svgrepo-com.svg",16,16);
        this.setIconImage(icon.getImage());
        this.setSize(400, 400);
        this.setLayout(new FlowLayout());

        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuItem = new JMenuItem("Item 1");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("Item 2");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("Item 3");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        this.add(new JLabel("Menu Bar Example", SwingConstants.CENTER));

        // thêm menu nằm bên phải của menu bar
        menuBar.add(Box.createHorizontalGlue());
        menu = new JMenu("Help");
        FlatSVGIcon logo = new FlatSVGIcon("Images/shop-2-svgrepo-com.svg",16,16);
        menu.setIcon(logo);
        menuItem = new JMenuItem("About");
        // menu item phải hiện bên trái
        menuItem.setHorizontalAlignment(SwingConstants.RIGHT);
        menu.add(menuItem);
        menuBar.add(menu);




    }

    public static void main(String[] args) {
        // Thiết lập Look and Feel của FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            MenuBar_Frame frame = new MenuBar_Frame();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getActionCommand().equals("Item 1")) {
            System.out.println("Item 1 selected");
        } else if (e.getActionCommand().equals("Item 2")) {
            System.out.println("Item 2 selected");
        } else if (e.getActionCommand().equals("Item 3")) {
            System.out.println("Item 3 selected");
        }
    }
}
