package org.example.Components;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int cornerRadius;

    public RoundedPanel(int radius) {
        this.cornerRadius = radius;
        setOpaque(false); // Đảm bảo nền trong suốt để hiển thị góc bo
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        
        // Bật khử răng cưa
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Vẽ nền bo góc
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        g2.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rounded Panel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        RoundedPanel panel = new RoundedPanel(30); // Bo góc 30px
        panel.setBackground(new Color(100, 150, 255)); // Màu nền xanh
        panel.setBounds(50, 50, 300, 150);

        frame.add(panel);
        frame.setVisible(true);
    }
}
