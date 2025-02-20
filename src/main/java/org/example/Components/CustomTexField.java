package org.example.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomTexField extends JTextField {
    private String placeholder;
    private Color placeholderColor = new Color(150, 150, 150);
    private Color borderColor = new Color(66, 133, 244);  // Google Blue
    private boolean showPlaceholder;
    private int borderThickness = 2;

    public CustomTexField(String placeholder) {
        this.placeholder = placeholder;
        this.showPlaceholder = true;
        
        // Thiết lập giao diện cơ bản
        setOpaque(false);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setForeground(Color.BLACK);
        
        // Thêm sự kiện focus
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                    showPlaceholder = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(placeholderColor);
                    showPlaceholder = true;
                }
            }
        });

        // Khởi tạo với placeholder
        setText(placeholder);
        setForeground(placeholderColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Vẽ background
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Vẽ viền dưới
        if (isFocusOwner()) {
            g2.setColor(borderColor);
        } else {
            g2.setColor(new Color(200, 200, 200));
        }
        g2.fillRect(0, getHeight() - borderThickness, getWidth(), borderThickness);

        super.paintComponent(g);
    }

    // Setter methods
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        if (showPlaceholder) {
            setText(placeholder);
        }
    }

    public void setPlaceholderColor(Color color) {
        this.placeholderColor = color;
        if (showPlaceholder) {
            setForeground(placeholderColor);
        }
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
        JFrame frame = new JFrame("Modern TextField Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        frame.getContentPane().setBackground(new Color(255, 255, 255));

        // Tạo ModernTextField
        CustomTexField textField = new CustomTexField("Enter your name");
        textField.setPreferredSize(new Dimension(200, 35));

        CustomTexField textField2 = new CustomTexField("Enter your email");
        textField2.setPreferredSize(new Dimension(200, 35));


        // Tùy chỉnh thêm nếu muốn
        // textField.setPlaceholderColor(new Color(180, 180, 180));
        // textField.setBorderColor(new Color(0, 150, 136));  // Material Teal
        // textField.setBorderThickness(3);

        frame.add(textField);
        frame.add(textField2);
        frame.setVisible(true);
    }
}