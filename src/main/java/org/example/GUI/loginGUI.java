package org.example.GUI;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomPasswordField;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class loginGUI extends JFrame {
    public loginGUI() {
        // Tạo JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(240, 240, 240));

        setLayout(new GridBagLayout());

        // Tạo JPanel chứa form đăng nhập
        RoundedPanel panel = new RoundedPanel(30);
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(400, 300));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2; // Các thành phần chiếm 2 cột
        // mở rộng theo chiều ngang


        // Logo
        JLabel logo = new JLabel("Đăng nhập", SwingConstants.CENTER);
        logo.setFont(new Font("Roboto", Font.BOLD, 20));
        logo.setForeground(new Color(0, 102, 204)); // Xanh dương
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(logo, gbc);

        // ===============Ô nhập tên đăng nhập (Placeholder)=============
        CustomTexField usernameField = new CustomTexField("Tên đăng nhập");
        usernameField.setPreferredSize(new Dimension(200, 35));
        gbc.gridy = 1;
        gbc.ipadx = 50;
        panel.add(usernameField, gbc);

        // ===============Ô nhập mật khẩu (Placeholder)===================
        CustomPasswordField passwordField = new CustomPasswordField("Mật khẩu");
        passwordField.setPreferredSize(new Dimension(200, 35));
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        // ===============Quên mật khẩu?===================
        JLabel forgotPassword = new JLabel("Quên mật khẩu?");
        forgotPassword.setForeground(new Color(0, 102, 204));
        // hover color dùng để thay đổi màu khi rê chuột vào
        forgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                forgotPassword.setForeground(new Color(0, 150, 255));
                // cursor sẽ thay đổi khi rê chuột vào
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                forgotPassword.setForeground(new Color(0, 102, 204));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        gbc.gridy = 3;
        panel.add(forgotPassword, gbc);

        // ===============Nút Đăng nhập và Quản lý===================
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        FlatSVGIcon manageIcon = new FlatSVGIcon("Images/chart-column-grow-svgrepo-com.svg", 16, 16);
        CustomButton manageButton = new CustomButton("Quản lý", manageIcon);

        FlatSVGIcon sellIcon = new FlatSVGIcon("Images/shop-2-svgrepo-com.svg", 16, 16);
        CustomButton sellButton = new CustomButton("Bán hàng", sellIcon);

        sellButton.setButtonColors(CustomButton.ButtonColors.GREEN);

        buttonPanel.add(manageButton);
        buttonPanel.add(sellButton);
        gbc.gridy = 4;
        //ADD vào panel Form ĐĂNG NHẬP
        panel.add(buttonPanel, gbc);



        // Thêm panel chính vào JFrame
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(panel, gbc);

        setVisible(true);
    }


    public static void main(String[] args)
    {
        // dùng flatlaf để thiết kế giao diện
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            // khởi tạo giao diện mượt mà
            SwingUtilities.invokeLater(() -> {
                new loginGUI();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
