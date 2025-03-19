package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class ThemKhuyenMaiDialog extends JDialog {
    private CustomTexField maKhuyenMaiField, tenKhuyenMaiField;
    private JSpinner phanTramSpinner, ngayBatDauSpinner, ngayKetThucSpinner;
    private CustomButton luuButton, huyButton;

    public ThemKhuyenMaiDialog() {
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
        setSize(400, 370);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setLayout(null);

        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 50, 350, 230);
        panel.setLayout(null);

        // Tiêu đề
        JLabel title = new JLabel("THÊM KHUYẾN MÃI", SwingConstants.CENTER);
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
        // Mã khuyến mãi
        panel.add(createLabel("Mã KM", 20, 20));
        maKhuyenMaiField = new CustomTexField("Mã tự động (vd: KM001)");
        maKhuyenMaiField.setBounds(130, 20, 200, 30);
        panel.add(maKhuyenMaiField);

        // Tên khuyến mãi
        panel.add(createLabel("Tên", 20, 60));
        tenKhuyenMaiField = new CustomTexField("Nhập tên khuyến mãi");
        tenKhuyenMaiField.setBounds(130, 60, 200, 30);
        panel.add(tenKhuyenMaiField);

        // Phần trăm khuyến mãi
        panel.add(createLabel("Giá trị KM (%)", 20, 100));
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 100, 1);
        phanTramSpinner = new JSpinner(model);
        phanTramSpinner.setBounds(130, 100, 200, 30);
        panel.add(phanTramSpinner);

        // Ngày bắt đầu
        panel.add(createLabel("Ngày bắt đầu", 20, 140));
        SpinnerDateModel modelBatDau = new SpinnerDateModel();
        ngayBatDauSpinner = new JSpinner(modelBatDau);
        ngayBatDauSpinner.setBounds(130, 140, 200, 30);
        ngayBatDauSpinner.setEditor(new JSpinner.DateEditor(ngayBatDauSpinner, "dd/MM/yyyy"));
        panel.add(ngayBatDauSpinner);

        // Ngày kết thúc
        panel.add(createLabel("Ngày kết thúc", 20, 180));
        SpinnerDateModel modelKetThuc = new SpinnerDateModel();
        ngayKetThucSpinner = new JSpinner(modelKetThuc);
        ngayKetThucSpinner.setBounds(130, 180, 200, 30);
        ngayKetThucSpinner.setEditor(new JSpinner.DateEditor(ngayKetThucSpinner, "dd/MM/yyyy"));
        panel.add(ngayKetThucSpinner);

        // Button lưu
        luuButton = new CustomButton("Lưu");
        luuButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        luuButton.setBounds(260, 290, 110, 30);
        add(luuButton);

        // Button hủy
        huyButton = new CustomButton("Hủy");
        huyButton.setButtonColors(CustomButton.ButtonColors.RED);
        huyButton.setBounds(140, 290, 110, 30);
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
            JOptionPane.showMessageDialog(this, "Đã lưu thông tin khuyến mãi!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private boolean validateInput() {
        // Kiểm tra tên khuyến mãi
        if (tenKhuyenMaiField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khuyến mãi không được để trống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ngày bắt đầu và kết thúc
        java.util.Date ngayBatDau = (java.util.Date) ngayBatDauSpinner.getValue();
        java.util.Date ngayKetThuc = (java.util.Date) ngayKetThucSpinner.getValue();

        if (ngayKetThuc.before(ngayBatDau)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu",
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
        new ThemKhuyenMaiDialog();
    }
}