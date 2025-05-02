package org.example.Components;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int cornerRadius;
    private int shadowOffsetX = 0;
    private int shadowOffsetY = 0;
    private int shadowSize = 0;
    private int shadowSpread = 0;
    private Color shadowColor = null;

    public RoundedPanel(int radius) {
        this.cornerRadius = radius;
        setOpaque(false); // Đảm bảo nền trong suốt để hiển thị góc bo
    }

    public void setShadowProperties(int offsetX, int offsetY, int size, int spread, Color color) {
        this.shadowOffsetX = offsetX;
        this.shadowOffsetY = offsetY;
        this.shadowSize = size;
        this.shadowSpread = spread;
        this.shadowColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        
        // Bật khử răng cưa
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw shadow if shadow properties are set
        if (shadowColor != null && (shadowSize > 0 || shadowSpread > 0)) {
            g2.setColor(shadowColor);
            
            // Draw the shadow 
            for (int i = 1; i <= shadowSize; i++) {
                float alpha = shadowColor.getAlpha() / 255.0f;
                alpha = alpha * (1.0f - (float) i / shadowSize);
                Color currentColor = new Color(
                    shadowColor.getRed() / 255.0f,
                    shadowColor.getGreen() / 255.0f,
                    shadowColor.getBlue() / 255.0f,
                    alpha
                );
                
                g2.setColor(currentColor);
                
                int blurSize = i;
                g2.fillRoundRect(
                    shadowSpread + shadowOffsetX - blurSize, 
                    shadowSpread + shadowOffsetY - blurSize, 
                    getWidth() - (shadowSpread * 2) + (blurSize * 2), 
                    getHeight() - (shadowSpread * 2) + (blurSize * 2), 
                    cornerRadius + blurSize, 
                    cornerRadius + blurSize
                );
            }
        }
        
        // Vẽ nền bo góc
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        int maxShadow = Math.max(shadowSize + Math.abs(shadowOffsetX), shadowSize + Math.abs(shadowOffsetY));
        return new Dimension(size.width + maxShadow * 2, size.height + maxShadow * 2);
    }

    // Main method is left for testing purposes
    public static void main(String[] args) {
        JFrame frame = new JFrame("Rounded Panel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        RoundedPanel panel = new RoundedPanel(15); // Bo góc 15px
        panel.setBackground(new Color(255, 255, 255)); // White background
        panel.setBounds(50, 50, 300, 150);
        panel.setShadowProperties(0, 5, 10, 0, new Color(0, 0, 0, 0.15f)); // Add shadow

        frame.add(panel);
        frame.setVisible(true);
    }
}
