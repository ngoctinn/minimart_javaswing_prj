package org.example.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomTexField extends JTextField {
    private Color borderColor = new Color(66, 133, 244);  // Google Blue
    private int borderThickness = 2;

    public CustomTexField(String placeholder) {
        // Use FlatLaf's built-in placeholder support
        putClientProperty("JTextField.placeholderText", placeholder);
        // loai bỏ viền mặc định
        setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Removed custom background drawing - let FlatLaf handle it
        // g2.setColor(getBackground());
        // g2.fillRect(0, 0, getWidth(), getHeight());

        // Call super.paintComponent first to let L&F draw the component background and text
        super.paintComponent(g);

        // Vẽ viền dưới (Draw bottom border) - Draw this *after* super.paintComponent
        if (isFocusOwner()) {
            g2.setColor(borderColor);
        } else {
            g2.setColor(new Color(200, 200, 200)); // Color for border when not focused
        }
        g2.fillRect(0, getHeight() - borderThickness, getWidth(), borderThickness);
    }

    public void setBorderThickness(int thickness) {
        this.borderThickness = thickness;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }
    public static void main(String[] args) {
        // It's recommended to set the Look and Feel *before* creating UI components
        // Example using FlatLaf Light:
        try {
            UIManager.setLookAndFeel( new com.formdev.flatlaf.FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        JFrame frame = new JFrame("Modern TextField Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        frame.getContentPane().setBackground(new Color(255, 255, 255));

        // Tạo ModernTextField
        CustomTexField textField = new CustomTexField("Enter your name");
        textField.setPreferredSize(new Dimension(200, 35));

        CustomTexField textField2 = new CustomTexField("Enter your email");
        textField2.setPreferredSize(new Dimension(200, 35));


        // Tùy chỉnh thêm nếu muốn
        // Removed calls to setPlaceholderColor as the method is removed
        // textField.setBorderColor(new Color(0, 150, 136));  // Material Teal
        // textField.setBorderThickness(3);

        frame.add(textField);
        frame.add(textField2);
        frame.setVisible(true);
    }
}