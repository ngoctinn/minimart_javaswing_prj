package org.example.Components;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {

    // =============== THUỘC TÍNH CHÍNH ===============
    private Color normalColor;
    private Color hoverColor;
    private Color pressColor;
    private ImageIcon originalIcon;

    // =============== ĐỊNH NGHĨA CÁC BỘ MÀU ===============
    public static final class ButtonColors {
        // Xanh dương (mặc định)
public static final Color[] BLUE = {
        new Color(0, 102, 204),  // normal
        new Color(0, 90, 195),   // press
        new Color(0, 105, 225),  // hover
};
        // Xanh lá
        public static final Color[] GREEN = {
                new Color(0, 184, 63),  // normal
                new Color(0, 146, 50),   // hover
                new Color(0, 180, 76)    // press
        };

        // Đỏ
        public static final Color[] RED = {
                new Color(231, 76, 60),   // normal
                new Color(192, 57, 43),    // press
                new Color(236, 112, 99)  // hover
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
    public CustomButton(String text) {
        super(text);
        setButtonColors(ButtonColors.BLUE);
        setupButton();
    }

    public CustomButton(String text, ImageIcon icon) {
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
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setupListeners();
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

    // =============== CUSTOM METHODS ===============
    public void setButtonColors(Color[] colors) {
        if (colors.length != 3) {
            throw new IllegalArgumentException("Colors array must contain exactly 3 colors");
        }
        this.normalColor = colors[0];
        this.hoverColor = colors[1];
        this.pressColor = colors[2];
        setBackground(normalColor);
    }

    // =============== PAINT METHOD ===============
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(g);
        g2.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Custom Button Demo");
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1. Tạo button cơ bản chỉ có text
        CustomButton basicButton = new CustomButton("Click Me");

        // 2. Tạo button với icon
        ImageIcon icon = new ImageIcon("src/main/resources/Images/shop.png");
        CustomButton iconButton = new CustomButton("Download", icon);

        // 3. Sử dụng các bộ màu có sẵn
        // Button màu xanh (mặc định)
        CustomButton blueButton = new CustomButton("Blue Button");

        // Button màu xanh lá
        CustomButton greenButton = new CustomButton("Green Button");
        greenButton.setButtonColors(CustomButton.ButtonColors.GREEN);

        // Button màu vàng
        FlatSVGIcon svgIcon = new FlatSVGIcon("Images/chart-column-grow-svgrepo-com.svg", 16, 16);
        CustomButton yellowButton = new CustomButton("Yellow Button", svgIcon);
        yellowButton.setButtonColors(CustomButton.ButtonColors.YELLOW);

        // Button màu cam
        CustomButton orangeButton = new CustomButton("Orange Button");
        orangeButton.setButtonColors(CustomButton.ButtonColors.ORANGE);

        // Button màu đỏ
        CustomButton redButton = new CustomButton("Red Button");
        redButton.setButtonColors(CustomButton.ButtonColors.RED);

        // Button màu xám
        CustomButton grayButton = new CustomButton("Gray Button");
        grayButton.setButtonColors(CustomButton.ButtonColors.GRAY);

        // 4. Tạo bộ màu tùy chỉnh
        Color[] customColors = {
                new Color(155, 89, 182),  // normal
                new Color(165, 105, 189), // hover
                new Color(142, 68, 173)   // press
        };
        CustomButton customButton = new CustomButton("Custom Color");
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