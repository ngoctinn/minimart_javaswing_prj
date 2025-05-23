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
    private Color disabledColor;
    private ImageIcon originalIcon;

    // =============== ĐỊNH NGHĨA CÁC BỘ MÀU ===============
    public static final class ButtonColors {
        // Xanh dương (mặc định)
        public static final Color[] BLUE = {
                new Color(0, 102, 204),  // normal
                new Color(0, 90, 195),   // press
                new Color(0, 105, 225),  // hover
                new Color(0, 102, 204, 120)  // disabled
        };
        // Xanh lá
        public static final Color[] GREEN = {
                new Color(0, 153, 102),  // normal
                new Color(0, 130, 90),    // press
                new Color(0, 180, 120),   // hover
                new Color(0, 153, 102, 120)  // disabled
        };

        // Đỏ
        public static final Color[] RED = {
                new Color(195, 0, 0),   // normal
                new Color(180, 0, 0),  // hover
                new Color(230, 50, 50),    // press
                new Color(195, 0, 0, 120)  // disabled
        };

        // Vàng
        public static final Color[] YELLOW = {
                new Color(241, 196, 15),  // normal
                new Color(245, 215, 110), // hover
                new Color(230, 163, 10),   // press
                new Color(241, 196, 15, 120)  // disabled
        };

        // Cam
        public static final Color[] ORANGE = {
                new Color(243, 156, 18),  // normal
                new Color(247, 202, 24),  // hover
                new Color(230, 126, 34),   // press
                new Color(243, 156, 18, 120)  // disabled
        };

        // Xám
        public static final Color[] GRAY = {
                new Color(120, 120, 120), // normal
                new Color(80, 80, 80),     // press
                new Color(160, 160, 160), // hover
                new Color(120, 120, 120, 120)  // disabled
        };
        
        // Vô hiệu hóa (Disable)
        public static final Color[] DISABLE = {
                new Color(200, 200, 200), // normal
                new Color(180, 180, 180), // press
                new Color(210, 210, 210), // hover
                new Color(200, 200, 200, 120)  // disabled
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
                if (isEnabled()) {
                    setBackground(hoverColor);
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isEnabled()) {
                    setBackground(normalColor);
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (isEnabled()) {
                    setBackground(pressColor);
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isEnabled()) {
                    setBackground(hoverColor);
                    repaint();
                }
            }
        });
    }

    // =============== CUSTOM METHODS ===============
    public void setButtonColors(Color[] colors) {
        if (colors.length < 4) {
            throw new IllegalArgumentException("Colors array must contain at least 4 colors (normal, press, hover, disabled)");
        }
        this.normalColor = colors[0];
        this.hoverColor = colors[1];
        this.pressColor = colors[2];
        this.disabledColor = colors[3];
        setBackground(isEnabled() ? normalColor : disabledColor);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setBackground(enabled ? normalColor : disabledColor);
        setForeground(enabled ? Color.WHITE : new Color(255, 255, 255, 150));
        repaint();
    }

    // =============== PAINT METHOD ===============
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        
        // If disabled, draw with different alpha
        if (!isEnabled() && getIcon() != null) {
            Composite oldComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            super.paintComponent(g2);
            g2.setComposite(oldComposite);
        } else {
            super.paintComponent(g);
        }
        
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
        redButton.setEnabled(false);

        // Button màu xám
        CustomButton grayButton = new CustomButton("Gray Button");
        grayButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        
        // Button vô hiệu hóa (Disable)
        CustomButton disableButton = new CustomButton("Disable Button");
        disableButton.setButtonColors(CustomButton.ButtonColors.DISABLE);

        // 4. Tạo bộ màu tùy chỉnh
        Color[] customColors = {
                new Color(155, 89, 182),  // normal
                new Color(165, 105, 189), // hover
                new Color(142, 68, 173),   // press
                new Color(155, 89, 182, 120)  // disabled
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
        frame.add(disableButton);
        frame.add(customButton);

        frame.setVisible(true);
    }
}
