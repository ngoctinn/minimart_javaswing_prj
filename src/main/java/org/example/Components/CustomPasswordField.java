package org.example.Components;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class CustomPasswordField extends JPasswordField {
    private String placeholder;
    private Color placeholderColor = new Color(150, 150, 150);
    private Color borderColor = new Color(66, 133, 244);  // Google Blue
    private boolean showPlaceholder;
    private boolean showPassword;
    private int borderThickness = 2;
    private Rectangle2D iconBounds;
    private boolean mouseOverIcon;

    // Thêm các ImageIcon
    private ImageIcon eyeOpenIcon;
    private ImageIcon eyeClosedIcon;
    private static final int ICON_SIZE = 20;

    public CustomPasswordField(String placeholder) {
        this.placeholder = placeholder;
        this.showPlaceholder = true;
        this.showPassword = false;

        // Tải hình ảnh từ resources
        try {
            eyeOpenIcon = new FlatSVGIcon("Images/eye-svgrepo-com.svg", ICON_SIZE, ICON_SIZE);
            eyeClosedIcon = new FlatSVGIcon("Images/eye-closed-svgrepo-com.svg", ICON_SIZE, ICON_SIZE);


        } catch (Exception e) {
            System.err.println("Error loading eye icons: " + e.getMessage());
        }

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
                boolean prevMouseOverIcon = mouseOverIcon;
                mouseOverIcon = iconBounds != null && iconBounds.contains(e.getPoint());

                if (mouseOverIcon != prevMouseOverIcon) {
                    setCursor(mouseOverIcon ? new Cursor(Cursor.HAND_CURSOR) : new Cursor(Cursor.TEXT_CURSOR));
                }
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
        int iconX = getWidth() - ICON_SIZE - 5;
        int iconY = (getHeight() - ICON_SIZE) / 2;
        iconBounds = new Rectangle2D.Double(iconX, iconY, ICON_SIZE, ICON_SIZE);

        // Vẽ hình ảnh phù hợp
        if (eyeOpenIcon != null && eyeClosedIcon != null) {
            ImageIcon iconToDraw = showPassword ? eyeOpenIcon : eyeClosedIcon;
            if (mouseOverIcon) {
                // Tạo hiệu ứng hover nếu cần
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            }
            iconToDraw.paintIcon(this, g2, iconX, iconY);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
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
        JFrame frame = new JFrame("Modern Password Field Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        CustomPasswordField passwordField = new CustomPasswordField("Enter password");
        passwordField.setPreferredSize(new Dimension(200, 35));

        CustomPasswordField passwordField2 = new CustomPasswordField("Enter password");
        passwordField2.setPreferredSize(new Dimension(200, 35));

        frame.add(passwordField);
        frame.add(passwordField2);
        frame.setVisible(true);
    }
}