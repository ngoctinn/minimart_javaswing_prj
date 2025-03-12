package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class themHangHoaDialog extends JDialog {
    public themHangHoaDialog() {
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

    public void initGUI() {
        this.setSize(400, 550);
        this.getContentPane().setBackground(new Color(245, 245, 245));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setModal(true);
        this.setLayout(null);

        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 50, 350, 400);
        panel.setLayout(null);

        // Tạo tiêu đề
        JLabel title = new JLabel("Thêm hàng hóa");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(25, 10, 200, 30);

        // Mã hàng hóa
        JLabel maHangHoaLabel = new JLabel("Mã hàng hóa");
        maHangHoaLabel.setBounds(20, 20, 100, 30);
        maHangHoaLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField maHangHoaField = new CustomTexField("Mã tự động (vd: HH001)");
        maHangHoaField.setBounds(130, 20, 200, 30);

        // Tên hàng hóa
        JLabel tenHangHoaLabel = new JLabel("Tên hàng hóa");
        tenHangHoaLabel.setBounds(20, 60, 100, 30);
        tenHangHoaLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField tenHangHoaField = new CustomTexField("Nhập tên (vd: Coca Cola)");
        tenHangHoaField.setBounds(130, 60, 200, 30);

        // Loại hàng hóa
        JLabel loaiHangHoaLabel = new JLabel("Loại hàng hóa");
        loaiHangHoaLabel.setBounds(20, 100, 100, 30);
        loaiHangHoaLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JComboBox<String> loaiHangHoaComboBox = new JComboBox<>();
        loaiHangHoaComboBox.addItem("Thức ăn");
        loaiHangHoaComboBox.addItem("Đồ uống");
        loaiHangHoaComboBox.addItem("Hàng hóa khác");
        loaiHangHoaComboBox.setBounds(130, 100, 200, 30);

        //Đơn vị tính
        JLabel donViTinhLabel = new JLabel("Đơn vị tính");
        donViTinhLabel.setBounds(20, 140, 100, 30);
        donViTinhLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField donViTinhField = new CustomTexField("Nhập đơn vị tính (vd: chai)");
        donViTinhField.setBounds(130, 140, 200, 30);

        // Trạng thái
        JLabel trangThaiLabel = new JLabel("Trạng thái");
        trangThaiLabel.setBounds(20, 180, 100, 30);
        trangThaiLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JComboBox<String> trangThaiComboBox = new JComboBox<>();
        trangThaiComboBox.addItem("Đang kinh doanh");
        trangThaiComboBox.addItem("Ngừng kinh doanh");
        trangThaiComboBox.setBounds(130, 180, 200, 30);

        // Hình ảnh
        JLabel hinhAnhLabel = new JLabel("Hình ảnh");
        hinhAnhLabel.setBounds(20, 220, 100, 30);
        hinhAnhLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JPanel hinhAnhPanel = new JPanel();
        hinhAnhPanel.setBackground(new Color(225, 225, 225));
        hinhAnhPanel.setBounds(130, 220, 200, 100);
        CustomButton chonHinhAnhButton = new CustomButton("Chọn hình ảnh");
        chonHinhAnhButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        chonHinhAnhButton.setBounds(130, 330, 200, 30);

        //Button lưu
        CustomButton luuButton = new CustomButton("Lưu");
        luuButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        luuButton.setBounds(260, 460, 110, 30);

        //Button hủy
        CustomButton huyButton = new CustomButton("Hủy");
        huyButton.setButtonColors(CustomButton.ButtonColors.RED);
        huyButton.setBounds(140, 460, 110, 30);
















        this.add(title);
        this.add(panel);
        panel.add(maHangHoaLabel);
        panel.add(maHangHoaField);
        panel.add(tenHangHoaLabel);
        panel.add(tenHangHoaField);
        panel.add(loaiHangHoaLabel);
        panel.add(loaiHangHoaComboBox);
        panel.add(donViTinhLabel);
        panel.add(donViTinhField);
        panel.add(trangThaiLabel);
        panel.add(trangThaiComboBox);
        panel.add(hinhAnhLabel);
        panel.add(hinhAnhPanel);
        panel.add(chonHinhAnhButton);
        this.add(luuButton);
        this.add(huyButton);
        this.setVisible(true);
    }

    // Main để test giao diện
    public static void main(String[] args) {
        new themHangHoaDialog();
    }
}
