package org.example.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class CustomPasswordField extends JPasswordField {
    private String placeholder;
    private Color placeholderColor = new Color(150, 150, 150);
    private Color borderColor = new Color(66, 133, 244);  // Google Blue
    private Color iconColor = new Color(150, 150, 150);
    private boolean showPlaceholder;
    private boolean showPassword;
    private int borderThickness = 2;
    private Rectangle2D iconBounds;
    private boolean mouseOverIcon;
    
    public CustomPasswordField(String placeholder) {
        this.placeholder = placeholder;
        this.showPlaceholder = true;
        this.showPassword = false;
        
        // Thiết lập giao diện cơ bản
        setOpaque(false);
        setBorder(new EmptyBorder(5, 5, 5, 35)); // Thêm padding bên phải cho icon
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setForeground(Color.BLACK);
        setEchoChar('•');
        
        // Thêm sự kiện focus
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(getPassword()).equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                    showPlaceholder = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(getPassword()).isEmpty()) {
                    setText(placeholder);
                    setForeground(placeholderColor);
                    showPlaceholder = true;
                }
            }
        });

        // Thêm sự kiện chuột để xử lý click vào icon
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (iconBounds != null && iconBounds.contains(e.getPoint())) {
                    showPassword = !showPassword;
                    setEchoChar(showPassword ? (char) 0 : '•');
                    repaint();
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                mouseOverIcon = false;
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseOverIcon = iconBounds != null && iconBounds.contains(e.getPoint());
                repaint();
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

        // Vẽ icon eye
        int iconSize = 18;
        int iconX = getWidth() - iconSize - 5;
        int iconY = (getHeight() - iconSize) / 2;
        iconBounds = new Rectangle2D.Double(iconX, iconY, iconSize, iconSize);

        g2.setColor(mouseOverIcon ? borderColor : iconColor);
        if (showPassword) {
            drawEyeOpen(g2, iconX, iconY, iconSize);
        } else {
            drawEyeClosed(g2, iconX, iconY, iconSize);
        }
    }

    private void drawEyeOpen(Graphics2D g2, int x, int y, int size) {
        g2.setStroke(new BasicStroke(1.5f));
        
        // Vẽ hình oval cho mắt
        g2.drawOval(x + 2, y + 4, size - 4, size - 8);
        
        // Vẽ con ngươi
        g2.fillOval(x + (size/2) - 3, y + (size/2) - 2, 6, 6);
    }

    private void drawEyeClosed(Graphics2D g2, int x, int y, int size) {
        g2.setStroke(new BasicStroke(1.5f));
        
        // Vẽ đường cong cho mắt đóng
        g2.drawArc(x + 2, y + 4, size - 4, size - 8, 0, -180);
        
        // Vẽ đường gạch ngang
        g2.drawLine(x + 2, y + (size/2), x + size - 2, y + (size/2));
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

    public void setIconColor(Color color) {
        this.iconColor = color;
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Modern Password Field Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Tạo ModernPasswordField
        CustomPasswordField passwordField = new CustomPasswordField("Enter password");
        passwordField.setPreferredSize(new Dimension(200, 35));

        // Tùy chỉnh thêm nếu muốn
        // passwordField.setPlaceholderColor(new Color(180, 180, 180));
        // passwordField.setBorderColor(new Color(0, 150, 136));
        // passwordField.setBorderThickness(3);
        //passwordField.setIconColor(new Color(100, 100, 100));

        frame.add(passwordField);
        frame.setVisible(true);
    }
}