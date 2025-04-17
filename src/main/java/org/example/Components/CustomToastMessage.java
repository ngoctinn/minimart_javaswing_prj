package org.example.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class CustomToastMessage extends JDialog {
    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;
    public static final int INFO = 4;

    private final int messageType;
    private final String message;
    private final Timer timer;
    private float opacity = 0.0f;
    private final int displayTime;
    private final JPanel mainPanel;
    private final JLabel iconLabel;
    private final JLabel messageLabel;

    /**
     * Tạo một toast message tùy chỉnh
     * @param parent Component cha
     * @param message Nội dung thông báo
     * @param messageType Loại thông báo (SUCCESS, WARNING, ERROR, INFO)
     * @param displayTime Thời gian hiển thị (ms)
     */
    public CustomToastMessage(Component parent, String message, int messageType, int displayTime) {
        this.message = message;
        this.messageType = messageType;
        this.displayTime = displayTime;

        setUndecorated(true);
        setSize(320, 75);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));



        // Đặt vị trí ở góc phải dưới của frame
        if (parent != null) {
            Point parentLocation = parent.getLocationOnScreen();
            Dimension parentSize = parent.getSize();
            int x = parentLocation.x + parentSize.width - getWidth() - 20; // Cách lề phải 20px
            int y = parentLocation.y + parentSize.height - getHeight() - 20; // Cách lề dưới 20px
            setLocation(x, y);
        } else {
            setLocationRelativeTo(null);
        }

        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

                // Vẽ đổ bóng
                int shadowSize = 2;
                for (int i = 0; i < shadowSize; i++) {
                    float shadowOpacity = 0.05f - (i * 0.05f);
                    if (shadowOpacity < 0) shadowOpacity = 0;
                    g2d.setColor(new Color(0, 0, 0, shadowOpacity));
                    g2d.fill(new RoundRectangle2D.Float(i, i, getWidth() - (i * 2), getHeight() - (i * 2), 10, 10));
                }

                // Vẽ nền màu pastel theo loại thông báo
                g2d.setColor(getPastelColorByType(messageType));
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(shadowSize, shadowSize,
                        getWidth() - (shadowSize * 2), getHeight() - (shadowSize * 2), 12, 12);
                g2d.fill(roundedRectangle);

                // Vẽ viền dưới với màu theo loại thông báo
                Color borderColor = getColorByType(messageType, true);
                g2d.setColor(borderColor);
                int borderHeight = 5;
                g2d.fillRect(shadowSize, getHeight() - shadowSize - borderHeight,
                        getWidth() - (shadowSize * 2), borderHeight);

                g2d.dispose();
            }
        };

        mainPanel.setLayout(new BorderLayout(15, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        mainPanel.setOpaque(false);

        // Thêm icon
        iconLabel = new JLabel();
        iconLabel.setIcon(getIconByType(messageType));
        mainPanel.add(iconLabel, BorderLayout.WEST);

        // Cập nhật text
        messageLabel = new JLabel(message);
        messageLabel.setForeground(new Color(60, 60, 60)); // Màu chữ đậm hơn một chút
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(messageLabel, BorderLayout.CENTER);

        add(mainPanel);

        // Tạo timer để hiển thị và ẩn toast
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opacity < 0.95f && !isClosing) {
                    // Hiệu ứng fade in
                    opacity += 0.05f;
                    if (opacity > 0.95f) {
                        opacity = 0.95f;
                        // Sau khi hiển thị đủ thời gian, bắt đầu ẩn
                        new Timer(displayTime, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                isClosing = true;
                                ((Timer) e.getSource()).stop();
                            }
                        }).start();
                    }
                } else if (isClosing) {
                    // Hiệu ứng fade out
                    opacity -= 0.05f;
                    if (opacity < 0) {
                        opacity = 0;
                        timer.stop();
                        dispose();
                        return;
                    }
                }
                repaint();
            }
        });
    }

    private boolean isClosing = false;

    /**
     * Hiển thị toast message
     */
    public void showToast() {
        setVisible(true);
        timer.start();
    }

    /**
     * Lấy màu dựa trên loại thông báo
     * @param type Loại thông báo
     * @param isPrimary Màu chính hay màu phụ
     * @return Màu tương ứng
     */
    private Color getColorByType(int type, boolean isPrimary) {
        switch (type) {
            case SUCCESS:
                return isPrimary ? new Color(46, 125, 50) : new Color(76, 175, 80);
            case WARNING:
                return isPrimary ? new Color(237, 108, 2) : new Color(255, 152, 0);
            case ERROR:
                return isPrimary ? new Color(198, 40, 40) : new Color(239, 83, 80);
            case INFO:
                return isPrimary ? new Color(2, 119, 189) : new Color(33, 150, 243);
            default:
                return isPrimary ? new Color(55, 71, 79) : new Color(96, 125, 139);
        }
    }


    private Color getPastelColorByType(int type) {
        switch (type) {
            case SUCCESS:
                return new Color(232, 245, 233); // Pastel green
            case WARNING:
                return new Color(255, 243, 224); // Pastel orange
            case ERROR:
                return new Color(253, 236, 234); // Pastel red
            case INFO:
                return new Color(227, 242, 253); // Pastel blue
            default:
                return new Color(236, 239, 241); // Pastel grey
        }
    }

    /**
     * Lấy icon dựa trên loại thông báo
     * @param type Loại thông báo
     * @return Icon tương ứng
     */
    private Icon getIconByType(int type) {
        String iconPath = "/icons/";
        switch (type) {
            case SUCCESS:
                iconPath += "success.png";
                break;
            case WARNING:
                iconPath += "warning.png";
                break;
            case ERROR:
                iconPath += "error.png";
                break;
            case INFO:
                iconPath += "info.png";
                break;
            default:
                iconPath += "info.png";
        }

        // Nếu không tìm thấy icon, tạo icon mặc định
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(getClass().getResource(iconPath));
            // Đặt kích thước icon
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImg);
        } catch (Exception e) {
            // Tạo icon mặc định nếu không tìm thấy
            icon = createDefaultIcon(type);
        }
        return icon;
    }

    /**
     * Tạo icon mặc định nếu không tìm thấy file icon
     * @param type Loại thông báo
     * @return Icon mặc định
     */
    private ImageIcon createDefaultIcon(int type) {
        int size = 32;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ hình tròn với màu tương ứng theo loại thông báo
        g2d.setColor(getColorByType(type, true));
        g2d.fillOval(0, 0, size, size);

        // Vẽ biểu tượng màu trắng
        g2d.setColor(Color.WHITE);
        switch (type) {
            case SUCCESS: // Dấu tích
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(8, 16, 14, 22);
                g2d.drawLine(14, 22, 24, 10);
                break;
            case WARNING: // Dấu chấm than
                g2d.fillRect(14, 7, 4, 15);
                g2d.fillRect(14, 24, 4, 4);
                break;
            case ERROR: // Dấu X
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(10, 10, 22, 22);
                g2d.drawLine(22, 10, 10, 22);
                break;
            case INFO: // Chữ i
                g2d.fillRect(14, 7, 4, 4);
                g2d.fillRect(14, 13, 4, 15);
                break;
        }
        g2d.dispose();
        return new ImageIcon(image);
    }

    /**
     * Hiển thị toast message thành công
     * @param parent Component cha
     * @param message Nội dung thông báo
     */
    public static void showSuccessToast(Component parent, String message) {
        new CustomToastMessage(parent, message, SUCCESS, 2000).showToast();
    }

    /**
     * Hiển thị toast message cảnh báo
     * @param parent Component cha
     * @param message Nội dung thông báo
     */
    public static void showWarningToast(Component parent, String message) {
        new CustomToastMessage(parent, message, WARNING, 2000).showToast();
    }

    /**
     * Hiển thị toast message lỗi
     * @param parent Component cha
     * @param message Nội dung thông báo
     */
    public static void showErrorToast(Component parent, String message) {
        new CustomToastMessage(parent, message, ERROR, 2000).showToast();
    }

    /**
     * Hiển thị toast message thông tin
     * @param parent Component cha
     * @param message Nội dung thông báo
     */
    public static void showInfoToast(Component parent, String message) {
        new CustomToastMessage(parent, message, INFO, 2000).showToast();
    }

    /**
     * Phương thức main để demo các loại toast message
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Toast Message Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridLayout(2, 2, 10, 10));
        frame.setLocationRelativeTo(null);

        // Tạo các nút để hiển thị các loại toast message
        JButton successBtn = new JButton("Success Toast");
        successBtn.addActionListener(e -> showSuccessToast(frame, "Thao tác thành công!"));

        JButton warningBtn = new JButton("Warning Toast");
        warningBtn.addActionListener(e -> showWarningToast(frame, "Cảnh báo: Dữ liệu chưa được lưu!"));

        JButton errorBtn = new JButton("Error Toast");
        errorBtn.addActionListener(e -> showErrorToast(frame, "Lỗi: Không thể kết nối đến máy chủ!"));

        JButton infoBtn = new JButton("Info Toast");
        infoBtn.addActionListener(e -> showInfoToast(frame, "Thông tin: Đang tải dữ liệu..."));

        frame.add(successBtn);
        frame.add(warningBtn);
        frame.add(errorBtn);
        frame.add(infoBtn);

        frame.setVisible(true);
    }
}