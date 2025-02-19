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
        // Frame đăng nhập
        setTitle("Đăng nhập");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        // Panel chứa label và textfield username
        JPanel panelUsername = new JPanel(new FlowLayout());
        lblUsername = new JLabel("Tên đăng nhập");
        txtUsername = new JTextField(20);
        panelUsername.add(lblUsername);
        panelUsername.add(txtUsername);
        add(panelUsername);

        // Panel chứa label và textfield password
        JPanel panelPassword = new JPanel(new FlowLayout());
        lblPassword = new JLabel("Mật khẩu");
        txtPassword = new JPasswordField(20);
        panelPassword.add(lblPassword);
        panelPassword.add(txtPassword);
        add(panelPassword);

        // Panel chứa button đăng nhập
        JPanel panelLogin = new JPanel(new FlowLayout());
        btnLogin = new CustomButton("Đăng nhập ajdsfjdsalfjlkdj aldskjfdsalfk");
        btnLogin.setSize(200, 50);
        panelLogin.add(btnLogin);
        add(panelLogin);



    }

}
