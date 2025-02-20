package org.example.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

// ================CÁCH SỬ DỤNG===========================
/*// Tạo button với text
CustomButton btn1 = new CustomButton("Click me!");

        // Tạo button với icon + text
        ImageIcon icon = new ImageIcon("path/to/icon.png");
        CustomButton btn2 = new CustomButton("Download", icon);

// Thay đổi màu sắc
btn1.setButtonColor(
    new Color(46, 204, 113), // Normal
    new Color(88, 214, 141), // Hover
    new Color(40, 180, 99)    // Pressed
);

// Thay đổi icon
        btn2.setIcon(new ImageIcon("new_icon.png"));*/

public class CustomButton extends JButton {
    private Color normalColor = new Color(0, 120, 215);
    private Color hoverColor = new Color(0, 150, 255);
    private Color pressColor = new Color(0, 90, 175);

    // =============== BỘ MÀU XANH DƯƠNG (Mặc định) ===============
    Color blueNormal = new Color(0, 120, 215);
    Color blueHover = new Color(0, 150, 255);
    Color bluePress = new Color(0, 90, 175);

    // =============== BỘ MÀU XANH LÁ CÂY ========================
    Color greenNormal = new Color(46, 204, 113);
    Color greenHover = new Color(88, 214, 141);
    Color greenPress = new Color(40, 180, 99);

    // =============== BỘ MÀU ĐỎ ==================================
    Color redNormal = new Color(231, 76, 60);
    Color redHover = new Color(236, 112, 99);
    Color redPress = new Color(192, 57, 43);

    // =============== BỘ MÀU XÁM ================================
    Color grayNormal = new Color(120, 120, 120);
    Color grayHover = new Color(160, 160, 160);
    Color grayPress = new Color(80, 80, 80);
    private ImageIcon originalIcon;

    public CustomButton(String text) {
        super(text);
        setupButton();
        setupListeners();
    }

    public CustomButton(String text, ImageIcon icon) {
        super(text);
        this.originalIcon = icon;
        setIcon(scaleIcon(icon));
        setupButton();
        setupListeners();
    }

    private void setupButton() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(Color.WHITE);
        setBackground(normalColor);
        setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    private void setupListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressColor);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(g);
        g2.dispose();
    }

    // Phương thức thay đổi màu sắc
    public void setButtonColor(Color normal, Color hover, Color press) {
        this.normalColor = normal;
        this.hoverColor = hover;
        this.pressColor = press;
        setBackground(normal);
    }


    // Phương thức thay đổi icon
    public void setIcon(ImageIcon icon) {
        this.originalIcon = icon;
        super.setIcon(scaleIcon(icon));
    }

    private ImageIcon scaleIcon(ImageIcon icon) {
        if (icon == null) return null;
        BufferedImage resized = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.drawImage(icon.getImage(), 0, 0, 20, 20, null);
        g2d.dispose();
        return new ImageIcon(resized);
    }

    // Các phương thức getter/setter cho màu sắc
    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
        setBackground(normalColor);
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setPressColor(Color pressColor) {
        this.pressColor = pressColor;
    }
}