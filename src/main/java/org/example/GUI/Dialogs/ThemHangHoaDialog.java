package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class ThemHangHoaDialog extends JDialog {
    private CustomTexField maHangHoaField, tenHangHoaField, donViTinhField;
    private JComboBox<String> loaiHangHoaComboBox, trangThaiComboBox;
    private CustomButton chonHinhAnhButton, luuButton, huyButton;

    public ThemHangHoaDialog() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            UIManager.put("ComboBox.buttonStyle", "icon-only");
            UIManager.put("ComboBox.buttonBackground", Color.WHITE);
            UIManager.put("ComboBox.buttonArrowColor", Color.BLACK);
            initGUI();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


    }

    private void initGUI() {
        setSize(400, 550);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setLayout(null);

        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 50, 350, 400);
        panel.setLayout(null);

        // Tiêu đề
        JLabel title = new JLabel("Thêm hàng hóa", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(0, 10, getWidth(), 30);
        add(title);

        // Tạo các thành phần
        createComponents(panel);

        // Thêm sự kiện cho các thành phần
        addEventListeners();

        add(panel);
        setVisible(true);
    }

    private void createComponents(JPanel panel) {
        panel.add(createLabel("Mã hàng hóa", 20, 20));
        maHangHoaField = new CustomTexField("Mã tự động (vd: HH001)");
        maHangHoaField.setBounds(130, 20, 200, 30);
        panel.add(maHangHoaField);

        panel.add(createLabel("Tên hàng hóa", 20, 60));
        tenHangHoaField = new CustomTexField("Nhập tên (vd: Coca Cola)");
        tenHangHoaField.setBounds(130, 60, 200, 30);
        panel.add(tenHangHoaField);

        panel.add(createLabel("Loại hàng hóa", 20, 100));
        loaiHangHoaComboBox = new JComboBox<>(new String[]{"Thức ăn", "Đồ uống", "Hàng hóa khác"});
        loaiHangHoaComboBox.setBounds(130, 100, 200, 30);
        panel.add(loaiHangHoaComboBox);

        panel.add(createLabel("Đơn vị tính", 20, 140));
        donViTinhField = new CustomTexField("Nhập đơn vị tính (vd: chai)");
        donViTinhField.setBounds(130, 140, 200, 30);
        panel.add(donViTinhField);

        panel.add(createLabel("Trạng thái", 20, 180));
        trangThaiComboBox = new JComboBox<>(new String[]{"Đang kinh doanh", "Ngừng kinh doanh"});
        trangThaiComboBox.setBounds(130, 180, 200, 30);
        panel.add(trangThaiComboBox);

        panel.add(createLabel("Hình ảnh", 20, 220));
        JPanel hinhAnhPanel = new JPanel();
        hinhAnhPanel.setBackground(new Color(225, 225, 225));
        hinhAnhPanel.setBounds(130, 220, 200, 100);
        panel.add(hinhAnhPanel);

        chonHinhAnhButton = new CustomButton("Chọn hình ảnh");
        chonHinhAnhButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        chonHinhAnhButton.setBounds(130, 330, 200, 30);
        panel.add(chonHinhAnhButton);

        // Button lưu
        luuButton = new CustomButton("Lưu");
        luuButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        luuButton.setBounds(260, 460, 110, 30);
        add(luuButton);

        // Button hủy
        huyButton = new CustomButton("Hủy");
        huyButton.setButtonColors(CustomButton.ButtonColors.RED);
        huyButton.setBounds(140, 460, 110, 30);
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
        chonHinhAnhButton.addActionListener(e -> handleChooseImage());
    }

    private void handleSave() {
        // Code xử lý lưu dữ liệu
    }

    private void handleCancel() {
        // Code xử lý hủy
        this.dispose();
    }

    private void handleChooseImage() {
        // Code xử lý chọn hình ảnh
    }

    public static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            // Cấu hình UI trước khi giao diện được tạo
            UIManager.put("RootPane.background", new Color(245, 245, 245));
            UIManager.put("TitlePane.foreground", Color.BLACK);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        setLookAndFeel(); // Đặt LookAndFeel trước
        SwingUtilities.invokeLater(ThemHangHoaDialog::new);
    }
}
