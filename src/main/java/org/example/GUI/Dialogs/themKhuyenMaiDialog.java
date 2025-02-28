package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import javax.swing.SpinnerDateModel;
public class themKhuyenMaiDialog extends JDialog {
    public themKhuyenMaiDialog() {
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
        this.setSize(400, 370);
        this.getContentPane().setBackground(new Color(245, 245, 245));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setModal(true);
        this.setLayout(null);

        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 50, 350, 230);
        panel.setLayout(null);

        // Tạo tiêu đề
        JLabel title = new JLabel("Thêm khuyến mãi");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(25, 10, 200, 30);

        // Mã khuyến mãi
        JLabel maKhuyenMaiLabel = new JLabel("Mã KM");
        maKhuyenMaiLabel.setBounds(20, 20, 100, 30);
        maKhuyenMaiLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField maKhuyenMaiField = new CustomTexField("Mã tự động (vd: KM001)");
        maKhuyenMaiField.setBounds(130, 20, 200, 30);

        // Tên khuyến mãi
        JLabel tenKhuyenMaiLabel = new JLabel("Tên");
        tenKhuyenMaiLabel.setBounds(20, 60, 100, 30);
        tenKhuyenMaiLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField tenKhuyenMaiField = new CustomTexField("Nhập tên khuyến mãi");
        tenKhuyenMaiField.setBounds(130, 60, 200, 30);

        // Phần trăm khuyến mãi
        JLabel phanTramLabel = new JLabel("Giá trị KM (%)");
        phanTramLabel.setBounds(20, 100, 100, 30);
        phanTramLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 100, 1);
        JSpinner phanTramSpinner = new JSpinner(model);
        phanTramSpinner.setBounds(130, 100, 200, 30);



// Ngày bắt đầu
        JLabel ngayBatDauLabel = new JLabel("Ngày bắt đầu");
        ngayBatDauLabel.setBounds(20, 140, 100, 30);
        ngayBatDauLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        SpinnerDateModel modelBatDau = new SpinnerDateModel();
        JSpinner ngayBatDauSpinner = new JSpinner(modelBatDau);
        ngayBatDauSpinner.setBounds(130, 140, 200, 30);
        ngayBatDauSpinner.setEditor(new JSpinner.DateEditor(ngayBatDauSpinner, "dd/MM/yyyy"));

// Ngày kết thúc
        JLabel ngayKetThucLabel = new JLabel("Ngày kết thúc");
        ngayKetThucLabel.setBounds(20, 180, 100, 30);
        ngayKetThucLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        SpinnerDateModel modelKetThuc = new SpinnerDateModel();
        JSpinner ngayKetThucSpinner = new JSpinner(modelKetThuc);
        ngayKetThucSpinner.setBounds(130, 180, 200, 30);
        ngayKetThucSpinner.setEditor(new JSpinner.DateEditor(ngayKetThucSpinner, "dd/MM/yyyy"));

// Thêm vào panel
        panel.add(ngayBatDauLabel);
        panel.add(ngayBatDauSpinner);
        panel.add(ngayKetThucLabel);
        panel.add(ngayKetThucSpinner);


        // Thêm vào panel
        panel.add(phanTramLabel);
        panel.add(phanTramSpinner);

        //Button lưu
        CustomButton luuButton = new CustomButton("Lưu");
        luuButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        luuButton.setBounds(260, 290, 110, 30);

        //Button hủy
        CustomButton huyButton = new CustomButton("Hủy");
        huyButton.setButtonColors(CustomButton.ButtonColors.RED);
        huyButton.setBounds(140, 290, 110, 30);


        this.add(title);
        this.add(panel);

        panel.add(maKhuyenMaiLabel);
        panel.add(maKhuyenMaiField);
        panel.add(tenKhuyenMaiLabel);
        panel.add(tenKhuyenMaiField);
        panel.add(phanTramLabel);
        panel.add(phanTramSpinner);
        panel.add(ngayBatDauLabel);
        panel.add(ngayBatDauSpinner);
        panel.add(ngayKetThucLabel);
        panel.add(ngayKetThucSpinner);


        this.add(luuButton);
        this.add(huyButton);
        this.setVisible(true);
    }

    // Main để test giao diện
    public static void main(String[] args) {
        new themKhuyenMaiDialog();
    }
}
