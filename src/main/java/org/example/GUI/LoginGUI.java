package org.example.GUI;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.LoginBUS;
import org.example.BUS.PhanQuyenBUS;
import org.example.Components.CustomButton;
import org.example.Components.CustomPasswordField;
import org.example.Components.CustomTextField;
import org.example.Components.RoundedPanel;
import org.example.Components.CustomToastMessage;
import org.example.DTO.ChucNangDTO;
import org.example.DTO.nhanVienDTO;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class LoginGUI extends JFrame {
    private CustomTextField usernameField;
    private CustomPasswordField passwordField;
    private JLabel errorLabel;

    public LoginGUI() {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("ComboBox.buttonStyle", "icon-only");
            UIManager.put("ComboBox.buttonBackground", Color.WHITE);
            UIManager.put("ComboBox.buttonArrowColor", Color.BLACK);
            UIManager.put("PasswordField.showRevealButton", true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
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
        panel.setPreferredSize(new Dimension(450, 450));

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

        // Label và ô nhập tên đăng nhập
        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(60, 60, 60));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 15, 5, 15);
        panel.add(usernameLabel, gbc);

        usernameField = new CustomTextField();
        usernameField.setPlaceholder("Nhập số điện thoại hoặc email");
        usernameField.setPreferredSize(new Dimension(200, 40));
        usernameField.setFont(new Font("Roboto", Font.PLAIN, 16));
        gbc.gridy = 2;
        gbc.ipadx = 40;
        gbc.insets = new Insets(0, 15, 12, 15);
        panel.add(usernameField.getContainer(), gbc);

        // Label và ô nhập mật khẩu
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(60, 60, 60));
        gbc.gridy = 3;
        gbc.insets = new Insets(12, 15, 5, 15);
        panel.add(passwordLabel, gbc);

        passwordField = new CustomPasswordField();
        passwordField.setPlaceholder("Nhập mật khẩu");
        passwordField.setPreferredSize(new Dimension(200, 40));
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 16));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 15, 12, 15);
        panel.add(passwordField.getContainer(), gbc);

        // Thêm label hiển thị lỗi
        errorLabel = new JLabel("");
        errorLabel.setForeground(new Color(255, 80, 80));
        errorLabel.setFont(new Font("Roboto", Font.ITALIC, 14));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setVisible(false);
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 15, 0, 15);
        panel.add(errorLabel, gbc);
        gbc.insets = new Insets(12, 15, 12, 15);


        // Nút Đăng nhập
        FlatSVGIcon loginIcon = new FlatSVGIcon("Icons/login.svg", 20, 20);
        CustomButton loginButton = new CustomButton("Đăng nhập hệ thống", loginIcon);
        loginButton.setFont(new Font("Roboto", Font.BOLD, 16));
        loginButton.setPreferredSize(new Dimension(200, 45));
        loginButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        loginButton.addActionListener(e -> handleLogin());

        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        // Thêm panel chính vào JFrame
        add(panel, new GridBagConstraints());

        // Thêm sự kiện Enter cho các trường nhập liệu
        usernameField.addActionListener(e -> passwordField.requestFocus());
        passwordField.addActionListener(e -> handleLogin());

        setVisible(true);
    }

    /**
     * Xử lý đăng nhập
     */
    private void handleLogin() {
        // Reset trạng thái lỗi
        usernameField.setState(CustomTextField.State.DEFAULT);
        passwordField.setState(CustomPasswordField.State.DEFAULT);
        errorLabel.setVisible(false);

        // Lấy thông tin đăng nhập
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Kiểm tra thông tin đăng nhập
        if (username.isEmpty()) {
            usernameField.setState(CustomTextField.State.INVALID);
            usernameField.setErrorMessage("Vui lòng nhập tên đăng nhập");
            usernameField.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordField.setState(CustomPasswordField.State.INVALID);
            passwordField.setErrorMessage("Vui lòng nhập mật khẩu");
            passwordField.requestFocus();
            return;
        }

        // Thực hiện đăng nhập sử dụng LoginBUS
        nhanVienDTO nhanVien = LoginBUS.login(username, password);

        if (nhanVien != null) {
            // Đăng nhập thành công

            // Lấy tên chức vụ của người dùng
            String tenChucVu = LoginBUS.getCurrentUserRole();

            MenuFrame menuFrame = new MenuFrame();

            // Cập nhật menu người dùng với thông tin người dùng đã đăng nhập
            menuFrame.updateUserMenu();

            CustomToastMessage.showSuccessToast(this, "Đăng nhập thành công!");

            // Lấy danh sách chức năng của người dùng
            ArrayList<ChucNangDTO> dsChucNang = PhanQuyenBUS.layDanhSachChucNang();
            for (ChucNangDTO chucNang : dsChucNang) {
                // Ẩn các chức năng không có quyền
                if (!LoginBUS.hasPermission(chucNang.getMaChucNang())) {
                    menuFrame.hideMenuItem(chucNang.getTenChucNang());
                }
                // Ẩn các hành động nếu chỉ có quyền xem
                if (LoginBUS.getPermissionLevel(chucNang.getMaChucNang()) == 1) {
                    menuFrame.hideActionPanel(chucNang.getTenChucNang());
                }
            }

            // nếu là chức vụ thì ẩn thêm action
            if (LoginBUS.getPermissionLevel("CN05") == 0) {
                menuFrame.hideActionPanel("Quản lý phiếu nhập");
            }

            menuFrame.setVisible(true);
            dispose();



        } else {
            // Đăng nhập thất bại
            usernameField.setState(CustomTextField.State.INVALID);
            passwordField.setState(CustomPasswordField.State.INVALID);
            errorLabel.setText("Tên đăng nhập hoặc mật khẩu không đúng");
            errorLabel.setVisible(true);
            CustomToastMessage.showErrorToast(this, "Đăng nhập thất bại!");
        }
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
