package org.example.GUI;

import org.example.Components.CustomButton;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private CustomButton btnLogin;
    private JButton btnRegister;
    private JButton btnExit;

    public LoginGUI() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Đăng nhập");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        lblUsername = new JLabel("Tên đăng nhập");
        lblPassword = new JLabel("Mật khẩu");
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new CustomButton("Đăng nhập");
        btnRegister = new JButton("Đăng ký");
        btnExit = new JButton("Thoát");

        add(lblUsername);
        add(txtUsername);
        add(lblPassword);
        add(txtPassword);
        add(btnLogin);
        add(btnRegister);
        add(btnExit);
    }

}
