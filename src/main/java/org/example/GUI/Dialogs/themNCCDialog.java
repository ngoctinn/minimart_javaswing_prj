package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class themNCCDialog extends JDialog {
    public themNCCDialog() {
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
        this.setSize(400, 515);
        this.getContentPane().setBackground(new Color(245, 245, 245));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setModal(true);
        this.setLayout(null);

        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 50, 350, 370);
        panel.setLayout(null);

        // Tạo tiêu đề
        JLabel title = new JLabel("Thêm nhà cung cấp");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(25, 10, 200, 30);

        // Mã nhà cung cấp
        JLabel maNCCLabel = new JLabel("Mã NCC");
        maNCCLabel.setBounds(20, 20, 100, 30);
        maNCCLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField maNCCField = new CustomTexField("Mã tự động (vd: NCC001)");
        maNCCField.setBounds(130, 20, 200, 30);

        // Tên nhà cung cấp
        JLabel tenNCCLabel = new JLabel("Tên NCC");
        tenNCCLabel.setBounds(20, 60, 100, 30);
        tenNCCLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField tenNCCField = new CustomTexField("Nhập tên nhà cung cấp");
        tenNCCField.setBounds(130, 60, 200, 30);

        //Địa chỉ
        JLabel diaChiLabel = new JLabel("Địa chỉ");
        diaChiLabel.setBounds(20, 100, 100, 30);
        diaChiLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JPanel diaChiPanel = new JPanel();
        diaChiPanel.setBounds(20, 130, 320, 125);
        diaChiPanel.setLayout(null);
        CustomTexField soNhaField = new CustomTexField("Số nhà");
        soNhaField.setBounds(10, 10, 100, 30);
        CustomTexField duongField = new CustomTexField("Đường (vd: Lê Lợi)");
        duongField.setBounds(120, 10, 180, 30);
        JLabel quanLabel = new JLabel("Quận/Huyện:");
        quanLabel.setBounds(10, 50, 100, 30);
        JComboBox<String> quanComboBox = new JComboBox<>();
        quanComboBox.addItem("Quận 1");
        quanComboBox.addItem("Quận 2");
        quanComboBox.addItem("Quận 3");
        quanComboBox.addItem("Quận 4");
        quanComboBox.addItem("Quận 5");
        quanComboBox.addItem("Quận 6");
        quanComboBox.addItem("Quận 7");
        quanComboBox.addItem("Quận 8");
        quanComboBox.addItem("Quận 9");
        quanComboBox.addItem("Quận 10");
        quanComboBox.addItem("Quận 11");
        quanComboBox.addItem("Quận 12");
        quanComboBox.addItem("Bình Tân");
        quanComboBox.addItem("Bình Thạnh");
        quanComboBox.addItem("Gò Vấp");
        quanComboBox.addItem("Phú Nhuận");
        quanComboBox.addItem("Tân Bình");
        quanComboBox.addItem("Tân Phú");
        quanComboBox.addItem("Thủ Đức");
        quanComboBox.addItem("Củ Chi");
        quanComboBox.addItem("Hóc Môn");
        quanComboBox.addItem("Bình Chánh");
        quanComboBox.addItem("Nhà Bè");
        quanComboBox.addItem("Cần Giờ");
        quanComboBox.setBounds(120, 50, 180, 30);
        JLabel phuongLabel = new JLabel("Phường:");
        phuongLabel.setBounds(10, 90, 100, 30);
        JComboBox<String> phuongComboBox = new JComboBox<>();
        phuongComboBox.setBounds(120, 90, 180, 30);
        quanComboBox.addActionListener(e -> {
            String quan = (String) quanComboBox.getSelectedItem();
            switch (quan) {
                case "Quận 1":
                    phuongComboBox.removeAllItems();
                    phuongComboBox.addItem("Phường Bến Nghé");
                    phuongComboBox.addItem("Phường Bến Thành");
                    phuongComboBox.addItem("Phường Cầu Kho");
                    phuongComboBox.addItem("Phường Cầu Ông Lãnh");
                    phuongComboBox.addItem("Phường Cô Giang");
                    phuongComboBox.addItem("Phường Đa Kao");
                    phuongComboBox.addItem("Phường Nguyễn Cư Trinh");
                    phuongComboBox.addItem("Phường Nguyễn Thái Bình");
                    phuongComboBox.addItem("Phường Phạm Ngũ Lão");
                    phuongComboBox.addItem("Phường Tân Định");
                    phuongComboBox.addItem("Phường Đa Kao");
                    break;
                case "Quận 2":
                    phuongComboBox.removeAllItems();
                    phuongComboBox.addItem("Phường An Phú");
                    phuongComboBox.addItem("Phường Bình An");
                    phuongComboBox.addItem("Phường Bình Khánh");
                    phuongComboBox.addItem("Phường Bình Trưng Đông");
                    phuongComboBox.addItem("Phường Bình Trưng Tây");
                    phuongComboBox.addItem("Phường Cát Lái");
                    phuongComboBox.addItem("Phường Thảo Điền");
                    phuongComboBox.addItem("Phường Thạnh Mỹ Lợi");
                    break;
                case "Quận 3":
                    phuongComboBox.removeAllItems();
                    phuongComboBox.addItem("Phường 1");
                    phuongComboBox.addItem("Phường 2");
                    phuongComboBox.addItem("Phường 3");
                    phuongComboBox.addItem("Phường 4");
                    phuongComboBox.addItem("Phường 5");
                    phuongComboBox.addItem("Phường 6");
                    phuongComboBox.addItem("Phường 7");
                    phuongComboBox.addItem("Phường 8");
                    phuongComboBox.addItem("Phường 9");
                    phuongComboBox.addItem("Phường 10");
                    phuongComboBox.addItem("Phường 11");
                    phuongComboBox.addItem("Phường 12");
                    phuongComboBox.addItem("Phường 13");
                    phuongComboBox.addItem("Phường 14");
                    break;
                case "Quận 4":
                    phuongComboBox.removeAllItems();
                    phuongComboBox.addItem("Phường 1");
                    phuongComboBox.addItem("Phường 2");
                    phuongComboBox.addItem("Phường 3");
                    phuongComboBox.addItem("Phường 4");
                    phuongComboBox.addItem("Phường 5");
                    phuongComboBox.addItem("Phường 6");
                    phuongComboBox.addItem("Phường 7");
                    phuongComboBox.addItem("Phường 8");
                    phuongComboBox.addItem("Phường 9");
                    phuongComboBox.addItem("Phường 10");
                    phuongComboBox.addItem("Phường 11");
                    phuongComboBox.addItem("Phường 12");
                    phuongComboBox.addItem("Phường 13");
                    phuongComboBox.addItem("Phường 14");
                    break;
                case "Quận 5":
                    phuongComboBox.removeAllItems();
                    phuongComboBox.addItem("Phường 1");
                    phuongComboBox.addItem("Phường 2");
                    phuongComboBox.addItem("Phường 3");
                    phuongComboBox.addItem("Phường 4");
                    phuongComboBox.addItem("Phường 5");
                    phuongComboBox.addItem("Phường 6");
                    phuongComboBox.addItem("Phường 7");
                    phuongComboBox.addItem("Phường 8");
                    phuongComboBox.addItem("Phường 9");
                    phuongComboBox.addItem("Phường 10");
                    phuongComboBox.addItem("Phường 11");
                    phuongComboBox.addItem("Phường 12");
                    phuongComboBox.addItem("Phường 13");
                    phuongComboBox.addItem("Phường 14");
                    break;
                default:
                    phuongComboBox.removeAllItems();
                    break;
            }
        });
        diaChiPanel.add(soNhaField);
        diaChiPanel.add(duongField);
        diaChiPanel.add(quanLabel);
        diaChiPanel.add(quanComboBox);
        diaChiPanel.add(phuongLabel);
        diaChiPanel.add(phuongComboBox);

        //Số điện thoại
        JLabel soDienThoaiLabel = new JLabel("Số điện thoại");
        soDienThoaiLabel.setBounds(20, 270, 100, 30);
        soDienThoaiLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField soDienThoaiField = new CustomTexField("0123456789");
        soDienThoaiField.setBounds(130, 270, 200, 30);

        //Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(20, 310, 100, 30);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField emailField = new CustomTexField("example@gmail.com");
        emailField.setBounds(130, 310, 200, 30);

        //Button lưu
        CustomButton luuButton = new CustomButton("Lưu");
        luuButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        luuButton.setBounds(260, 430, 110, 30);

        //Button hủy
        CustomButton huyButton = new CustomButton("Hủy");
        huyButton.setButtonColors(CustomButton.ButtonColors.RED);
        huyButton.setBounds(140, 430, 110, 30);


        this.add(title);
        this.add(panel);
        panel.add(maNCCLabel);
        panel.add(maNCCField);
        panel.add(tenNCCLabel);
        panel.add(tenNCCField);
        panel.add(diaChiLabel);
        panel.add(diaChiPanel);
        panel.add(soDienThoaiLabel);
        panel.add(soDienThoaiField);
        panel.add(emailLabel);
        panel.add(emailField);

        this.add(luuButton);
        this.add(huyButton);
        this.setVisible(true);
    }

    // Main để test giao diện
    public static void main(String[] args) {
        new themNCCDialog();
    }
}
