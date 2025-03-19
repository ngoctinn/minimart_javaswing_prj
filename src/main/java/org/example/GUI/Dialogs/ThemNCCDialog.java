package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;
import org.example.Utils.diaChiPanelAPI;

import javax.swing.*;
import java.awt.*;

public class ThemNCCDialog extends JDialog {
    private CustomTexField maNCCField, tenNCCField, soDienThoaiField, emailField;
    private diaChiPanelAPI diaChiPanel;
    private CustomButton luuButton, huyButton;

    public ThemNCCDialog() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            UIManager.put("ComboBox.buttonStyle", "icon-only");
            UIManager.put("ComboBox.buttonBackground", Color.WHITE);
            UIManager.put("ComboBox.buttonArrowColor", Color.BLACK);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        initGUI();
    }

    private void initGUI() {
        setSize(400, 515);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setLayout(null);
        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 50, 360, 370);
        panel.setLayout(null);

        // Tiêu đề
        JLabel title = new JLabel("THÊM NHÀ CUNG CẤP", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(0, 10, getWidth(), 30);
        title.setForeground(new Color(0,102,204));
        add(title);

        // Tạo các thành phần
        createComponents(panel);

        // Thêm sự kiện cho các thành phần
        addEventListeners();

        add(panel);
        setVisible(true);
    }

    private void createComponents(JPanel panel) {
        // Mã nhà cung cấp
        panel.add(createLabel("Mã NCC", 20, 20));
        maNCCField = new CustomTexField("Mã tự động (vd: NCC001)");
        maNCCField.setBounds(130, 20, 200, 30);
        panel.add(maNCCField);

        // Tên nhà cung cấp
        panel.add(createLabel("Tên NCC", 20, 60));
        tenNCCField = new CustomTexField("Nhập tên nhà cung cấp");
        tenNCCField.setBounds(130, 60, 200, 30);
        panel.add(tenNCCField);

        // Địa chỉ
        diaChiPanel = new diaChiPanelAPI();
        diaChiPanel.setBounds(10, 100, 340, 170);
        panel.add(diaChiPanel);

        // Số điện thoại
        panel.add(createLabel("Số điện thoại", 20, 270));
        soDienThoaiField = new CustomTexField("0123456789");
        soDienThoaiField.setBounds(130, 270, 200, 30);
        panel.add(soDienThoaiField);

        // Email
        panel.add(createLabel("Email", 20, 310));
        emailField = new CustomTexField("example@gmail.com");
        emailField.setBounds(130, 310, 200, 30);
        panel.add(emailField);

        // Button lưu
        luuButton = new CustomButton("Lưu");
        luuButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        luuButton.setBounds(260, 430, 110, 30);
        add(luuButton);

        // Button hủy
        huyButton = new CustomButton("Hủy");
        huyButton.setButtonColors(CustomButton.ButtonColors.RED);
        huyButton.setBounds(140, 430, 110, 30);
        add(huyButton);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 100, 30);
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        return label;
    }

    // Xử lý sự kiện
    private void addEventListeners() {
        luuButton.addActionListener(e -> handleSave());
        huyButton.addActionListener(e -> handleCancel());
    }

    private void handleSave() {
        // Code xử lý lưu dữ liệu
        if (validateInput()) {
            // Thực hiện lưu dữ liệu
            JOptionPane.showMessageDialog(this, "Đã lưu thông tin nhà cung cấp!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private boolean validateInput() {
        // Kiểm tra tên nhà cung cấp
        if (tenNCCField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên nhà cung cấp không được để trống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra số điện thoại
        String phone = soDienThoaiField.getText().trim();
        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!phone.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ (phải có 10-11 chữ số)",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra email
        String email = emailField.getText().trim();
        if (!email.isEmpty() && !email.matches("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Địa chỉ email không hợp lệ",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void handleCancel() {
        // Code xử lý hủy
        dispose();
    }

    // Main để test giao diện
    public static void main(String[] args) {
        new ThemNCCDialog();
    }
}