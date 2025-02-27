package org.example.test.ducTayTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public LoginScreen() {
        setTitle("POS Pro - Đăng Nhập");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel headerLabel = new JLabel("ĐĂNG NHẬP HỆ THỐNG", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(new Color(41, 128, 185));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 15));
        
        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameField = new JTextField(15);
        
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordField = new JPasswordField(15);
        
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton loginButton = new JButton("Đăng Nhập");
        loginButton.setPreferredSize(new Dimension(120, 35));
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        
        JButton exitButton = new JButton("Thoát");
        exitButton.setPreferredSize(new Dimension(120, 35));
        exitButton.setFocusPainted(false);
        
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        
        // Add components to main panel
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        loginButton.addActionListener(e -> {
            if (authenticate(usernameField.getText(), new String(passwordField.getPassword()))) {
                dispose();
                MainPOSInterface mainInterface = new MainPOSInterface();
                mainInterface.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu", 
                        "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        exitButton.addActionListener(e -> System.exit(0));
        
        add(mainPanel);
    }
    
    private boolean authenticate(String username, String password) {
        // Mock authentication - in real application would connect to a database
        return username.equals("admin") && password.equals("admin");
    }
}