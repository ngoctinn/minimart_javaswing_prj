package org.example.GUI;

import org.example.Components.CustomButton;
import org.example.Components.CustomPasswordField;
import org.example.Components.CustomTexField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class loginGUI extends JFrame {
    public loginGUI() {
        // Tạo JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(new GridBagLayout());

        // Tạo JPanel chứa form đăng nhập
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(400, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2; // Các thành phần chiếm 2 cột

        // Logo
        JLabel logo = new JLabel("Đăng nhập", SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setForeground(new Color(0, 102, 204)); // Xanh dương
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(logo, gbc);

        // Ô nhập tên đăng nhập (Placeholder)
        CustomTexField usernameField = new CustomTexField("Tên đăng nhập");
        usernameField.setPreferredSize(new Dimension(200, 35));
        usernameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

        // Ô nhập mật khẩu (Placeholder)
        CustomPasswordField passwordField = new CustomPasswordField("Mật khẩu");
        passwordField.setPreferredSize(new Dimension(200, 35));
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        setPlaceholder(passwordField, "Mật khẩu");
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        // link "Quên mật khẩu"
        JLabel forgotPassword = new JLabel("Quên mật khẩu?");
        forgotPassword.setForeground(new Color(0, 102, 204));
        // hover color dùng để thay đổi màu khi rê chuột vào
        forgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                forgotPassword.setForeground(new Color(0, 51, 102));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                forgotPassword.setForeground(new Color(0, 102, 204));
            }
        });

        gbc.gridy = 3;
        panel.add(forgotPassword, gbc);

        // Nút "Quản lý" (Xanh dương) và "Bán hàng" (Xanh lá)
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        ImageIcon manageIcon = new ImageIcon("src/main/resources/Images/statistics.png");
        CustomButton manageButton = new CustomButton("Quản lý", manageIcon);

        ImageIcon sellIcon = new ImageIcon("src/main/resources/Images/shopping-bag.png");
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

    // Phương thức tạo placeholder cho JTextField và JPasswordField
    private void setPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    public static void main(String[] args) {
        new loginGUI();
    }
}
