package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;
import org.example.Utils.diaChiPanelAPI;

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
        panel.setBounds(20, 50, 360, 370);
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
        diaChiPanelAPI diaChiPanel = new diaChiPanelAPI();
        diaChiPanel.setBounds(10, 100, 340, 170);

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
