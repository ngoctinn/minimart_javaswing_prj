package org.example.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderTextField extends JTextField {
    private String placeholder;
    private Color placeholderColor = Color.GRAY;
    private Color textColor = Color.BLACK;
    private int cornerRadius = 15; // Độ cong góc

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        setForeground(placeholderColor);
        setText(placeholder);
        setOpaque(false); // Tắt nền mặc định

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(textColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(placeholderColor);
                }
            }
        });
    }

    // Cho phép đổi màu placeholder
    public void setPlaceholderColor(Color color) {
        this.placeholderColor = color;
        if (getText().equals(placeholder)) {
            setForeground(color);
        }
    }

    // Cho phép đổi màu chữ khi nhập
    public void setTextColor(Color color) {
        this.textColor = color;
    }

    // Cho phép đổi nội dung placeholder
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        if (!hasFocus() && getText().isEmpty()) {
            setText(placeholder);
            setForeground(placeholderColor);
        }
    }

    // Cho phép chỉnh độ cong góc
    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ nền với bo góc
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ viền bo góc
        g2.setColor(new Color(0, 102, 204));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        g2.dispose();
    }
}
