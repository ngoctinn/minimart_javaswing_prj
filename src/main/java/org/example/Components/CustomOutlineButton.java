package org.example.Components;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomOutlineButton extends JButton {

    // =============== THUỘC TÍNH CHÍNH ===============
    private Color normalColor;
    private Color hoverColor;
    private Color pressColor;
    private ImageIcon originalIcon;
    private int borderThickness = 2;

    // =============== ĐỊNH NGHĨA CÁC BỘ MÀU ===============
    public static final class ButtonColors {
        // Xanh dương (mặc định)
        public static final Color[] BLUE = {
                new Color(0, 102, 204),  // normal
                new Color(0, 105, 225),  // hover
                new Color(0, 90, 195),   // press
        };
        
        // Xanh lá
        public static final Color[] GREEN = {
                new Color(0, 184, 63),   // normal
                new Color(0, 180, 76),   // hover
                new Color(0, 146, 50)    // press
        };

        // Đỏ
        public static final Color[] RED = {
                new Color(231, 76, 60),   // normal
                new Color(236, 112, 99),  // hover
                new Color(192, 57, 43)    // press
        };

        // Vàng
        public static final Color[] YELLOW = {
                new Color(241, 196, 15),  // normal
                new Color(245, 215, 110), // hover
                new Color(230, 163, 10)   // press
        };

        // Cam
        public static final Color[] ORANGE = {
                new Color(243, 156, 18),  // normal
                new Color(247, 202, 24),  // hover
                new Color(230, 126, 34)   // press
        };

        // Xám
        public static final Color[] GRAY = {
                new Color(120, 120, 120), // normal
                new Color(160, 160, 160), // hover
                new Color(80, 80, 80)     // press
        };
    }

    // =============== CONSTRUCTORS ===============
    public CustomOutlineButton(String text) {
        super(text);
        setButtonColors(ButtonColors.BLUE);
        setupButton();
    }

    public CustomOutlineButton(String text, ImageIcon icon) {
        super(text);
        this.originalIcon = icon;
        setIcon(icon);
        setIconTextGap(10);
        setButtonColors(ButtonColors.BLUE);
        setupButton();
    }

    // =============== SETUP METHODS ===============
    private void setupButton() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(normalColor);
        setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setupListeners();
    }

    private void setupListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(hoverColor);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(normalColor);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setForeground(pressColor);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setForeground(hoverColor);
                repaint();
            }
        });
    }

    // =============== CUSTOM METHODS ===============
    public void setButtonColors(Color[] colors) {
        if (colors.length != 3) {
            throw new IllegalArgumentException("Colors array must contain exactly 3 colors");
        }
        this.normalColor = colors[0];
        this.hoverColor = colors[1];
        this.pressColor = colors[2];
        setForeground(normalColor);
    }
    
    public void setBorderThickness(int thickness) {
        this.borderThickness = thickness;
        repaint();
    }

    // =============== PAINT METHOD ===============
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw outline/border
        g2.setColor(getForeground());
        g2.setStroke(new BasicStroke(borderThickness));
        g2.drawRoundRect(borderThickness/2, borderThickness/2, 
                         getWidth() - borderThickness, 
                         getHeight() - borderThickness, 15, 15);
        
        super.paintComponent(g);
        g2.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Custom Outline Button Demo");
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1. Tạo button cơ bản chỉ có text
        CustomOutlineButton basicButton = new CustomOutlineButton("Click Me");

        // 2. Tạo button với icon
        ImageIcon icon = new ImageIcon("src/main/resources/Images/shop.png");
        CustomOutlineButton iconButton = new CustomOutlineButton("Download", icon);

        // 3. Sử dụng các bộ màu có sẵn
        // Button màu xanh (mặc định)
        CustomOutlineButton blueButton = new CustomOutlineButton("Blue Button");

        // Button màu xanh lá
        CustomOutlineButton greenButton = new CustomOutlineButton("Green Button");
        greenButton.setButtonColors(CustomOutlineButton.ButtonColors.GREEN);

        // Button màu vàng
        FlatSVGIcon svgIcon = new FlatSVGIcon("Images/chart-column-grow-svgrepo-com.svg", 16, 16);
        CustomOutlineButton yellowButton = new CustomOutlineButton("Yellow Button", svgIcon);
        yellowButton.setButtonColors(CustomOutlineButton.ButtonColors.YELLOW);

        // Button màu cam
        CustomOutlineButton orangeButton = new CustomOutlineButton("Orange Button");
        orangeButton.setButtonColors(CustomOutlineButton.ButtonColors.ORANGE);
        orangeButton.setBorderThickness(3); // Thay đổi độ dày viền

        // Button màu đỏ
        CustomOutlineButton redButton = new CustomOutlineButton("Red Button");
        redButton.setButtonColors(CustomOutlineButton.ButtonColors.RED);

        // Button màu xám
        CustomOutlineButton grayButton = new CustomOutlineButton("Gray Button");
        grayButton.setButtonColors(CustomOutlineButton.ButtonColors.GRAY);

        // 4. Tạo bộ màu tùy chỉnh
        Color[] customColors = {
                new Color(155, 89, 182),  // normal
                new Color(165, 105, 189), // hover
                new Color(142, 68, 173)   // press
        };
        CustomOutlineButton customButton = new CustomOutlineButton("Custom Color");
        customButton.setButtonColors(customColors);

        // 5. Thêm sự kiện click
        basicButton.addActionListener(e -> {
            System.out.println("Button clicked!");
        });

        // 6. tuỳ chỉnh kích thước button
        basicButton.setPreferredSize(new Dimension(150, 40));

        // Thêm các button vào frame
        frame.add(basicButton);
        frame.add(iconButton);
        frame.add(blueButton);
        frame.add(greenButton);
        frame.add(yellowButton);
        frame.add(orangeButton);
        frame.add(redButton);
        frame.add(grayButton);
        frame.add(customButton);

        frame.setVisible(true);
    }
}