package org.example.test;

import javax.swing.*;
import java.awt.*;

public class huhu extends JPanel {
    public huhu() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo
        JLabel logo = new JLabel("KiotViet", SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setForeground(new Color(0, 102, 204)); // Xanh dương
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(logo, gbc);

        // Ô nhập tên đăng nhập
        JTextField usernameField = new JTextField("Tên đăng nhập");
        usernameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        gbc.gridy = 1;
        add(usernameField, gbc);

        // Ô nhập mật khẩu
        JPasswordField passwordField = new JPasswordField("Mật khẩu");
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        gbc.gridy = 2;
        add(passwordField, gbc);

        // Dòng checkbox "Duy trì đăng nhập" và "Quên mật khẩu"
        JPanel rememberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rememberPanel.setBackground(Color.WHITE);
        JCheckBox rememberCheckBox = new JCheckBox("Duy trì đăng nhập");
        rememberCheckBox.setBackground(Color.WHITE);
        JLabel forgotPassword = new JLabel("Quên mật khẩu?");
        forgotPassword.setForeground(new Color(0, 102, 204)); // Xanh hyperlink
        rememberPanel.add(rememberCheckBox);
        rememberPanel.add(new JLabel("|"));
        rememberPanel.add(forgotPassword);
        gbc.gridy = 3;
        add(rememberPanel, gbc);

        // Nút "Quản lý" (Xanh dương) và "Bán hàng" (Xanh lá)
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton manageButton = new JButton("Quản lý");
        manageButton.setBackground(new Color(0, 102, 255));
        manageButton.setForeground(Color.WHITE);
        JButton sellButton = new JButton("Bán hàng");
        sellButton.setBackground(new Color(0, 204, 102));
        sellButton.setForeground(Color.WHITE);
        buttonPanel.add(manageButton);
        buttonPanel.add(sellButton);
        gbc.gridy = 4;
        add(buttonPanel, gbc);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Đăng nhập");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.add(new huhu());
        frame.setVisible(true);
    }
}
