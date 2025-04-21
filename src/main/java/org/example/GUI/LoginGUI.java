package org.example.GUI;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomPasswordField;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class
LoginGUI extends JFrame {
    public LoginGUI() {
        // Tạo JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Sử dụng màu nền nhẹ nhàng hơn
        getContentPane().setBackground(new Color(245, 246, 250));
        setTitle("Minimart - Đăng nhập");

        setLayout(new GridBagLayout());

        // Tạo JPanel chứa form đăng nhập với góc bo tròn hơn
        RoundedPanel panel = new RoundedPanel(20);
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        // Tăng kích thước panel để có thêm khoảng trống
        panel.setPreferredSize(new Dimension(450, 350));
        
        // Thêm border đổ bóng cho panel
        panel.setBorder(new CompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createEmptyBorder(15, 25, 25, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        // Tăng khoảng cách giữa các thành phần
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2;

        // Logo và tiêu đề
        JLabel logo = new JLabel("Đăng nhập", SwingConstants.CENTER);
        logo.setFont(new Font("Roboto", Font.BOLD, 30));
        logo.setForeground(new Color(25, 118, 210)); // Màu xanh đậm hơn
        // Thêm padding
        logo.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(logo, gbc);

        // Ô nhập tên đăng nhập
        CustomTexField usernameField = new CustomTexField("Tên đăng nhập");
        usernameField.setPreferredSize(new Dimension(200, 40));
        usernameField.setFont(new Font("Roboto", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.ipadx = 40;
        panel.add(usernameField, gbc);

        // Ô nhập mật khẩu
        CustomPasswordField passwordField = new CustomPasswordField("Mật khẩu");
        passwordField.setPreferredSize(new Dimension(200, 40));
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 16));
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        // Quên mật khẩu?
        JLabel forgotPassword = new JLabel("Quên mật khẩu?");
        forgotPassword.setForeground(new Color(25, 118, 210));
        forgotPassword.setFont(new Font("Roboto", Font.PLAIN, 16));
        // Thêm css style để làm nổi bật liên kết
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPassword.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        
        // hover color
        forgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                forgotPassword.setForeground(new Color(41, 128, 185));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                forgotPassword.setForeground(new Color(25, 118, 210));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(forgotPassword, gbc);

        // Nút Đăng nhập và Quản lý
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0)); // Tăng khoảng cách giữa các nút
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Thêm padding phía trên

        FlatSVGIcon manageIcon = new FlatSVGIcon("Images/chart-column-grow-svgrepo-com.svg", 20, 20);
        CustomButton manageButton = new CustomButton("Quản lý", manageIcon);
        manageButton.setFont(new Font("Roboto", Font.BOLD, 16));

        FlatSVGIcon sellIcon = new FlatSVGIcon("Images/shop-2-svgrepo-com.svg", 20, 20);
        CustomButton sellButton = new CustomButton("Bán hàng", sellIcon);
        sellButton.setFont(new Font("Roboto", Font.BOLD, 16));

        // Tùy chỉnh màu nút bán hàng
        sellButton.setButtonColors(CustomButton.ButtonColors.GREEN);

        buttonPanel.add(manageButton);
        buttonPanel.add(sellButton);
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        // Thêm panel chính vào JFrame
        add(panel, new GridBagConstraints());

        setVisible(true);
    }

    public static void main(String[] args)
    {
        // dùng flatlaf để thiết kế giao diện
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            // khởi tạo giao diện mượt mà
            SwingUtilities.invokeLater(() -> {
                new LoginGUI();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
