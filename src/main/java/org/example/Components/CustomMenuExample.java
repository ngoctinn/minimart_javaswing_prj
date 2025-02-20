package org.example.Components;

import javax.swing.*;
import java.awt.*;

public class CustomMenuExample extends JFrame {
    
    public CustomMenuExample() {
        initUI();
    }
    
    private void initUI() {
        setTitle("Custom Menu Example");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Tạo menu bar và custom màu nền, border
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(60, 63, 65)); // Màu nền dark
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Tạo menu "File" và custom font, màu chữ
        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(Color.WHITE);
        fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Tạo các menu item cho "File"
        JMenuItem newItem = createMenuItem("New");
        JMenuItem openItem = createMenuItem("Open");
        JMenuItem exitItem = createMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator(); // Tạo ngăn cách
        fileMenu.add(exitItem);
        
        menuBar.add(fileMenu);
        
        // Tạo menu "Edit"
        JMenu editMenu = new JMenu("Edit");
        editMenu.setForeground(Color.WHITE);
        editMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JMenuItem cutItem = createMenuItem("Cut");
        JMenuItem copyItem = createMenuItem("Copy");
        JMenuItem pasteItem = createMenuItem("Paste");
        
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        
        menuBar.add(editMenu);
        
        // Gán menu bar cho frame
        setJMenuBar(menuBar);
        
        // Tạo panel chính với màu nền tùy chỉnh
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(43, 43, 43));
        add(mainPanel);
    }
    
    // Phương thức tạo các menu item với style chung
    private JMenuItem createMenuItem(String title) {
        JMenuItem item = new JMenuItem(title);
        item.setBackground(new Color(77, 77, 77));
        item.setForeground(Color.WHITE);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return item;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomMenuExample ex = new CustomMenuExample();
            ex.setVisible(true);
        });
    }
}
