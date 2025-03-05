package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomPasswordField;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;
import org.example.Utils.diaChiPanelAPI;

import javax.swing.*;
import java.awt.*;
import javax.swing.SpinnerDateModel;

public class themNhanVienDialog extends JDialog {
    public themNhanVienDialog() {
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
        this.setSize(790, 480);
        this.getContentPane().setBackground(new Color(245, 245, 245));
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setLayout(null);

        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 50, 740, 320);
        panel.setLayout(null);

        // Tạo tiêu đề
        JLabel title = new JLabel("Thêm nhân viên");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(25, 10, 200, 30);

        // Mã nhân viên
        JLabel maNhanVienLabel = new JLabel("Mã nhân viên");
        maNhanVienLabel.setBounds(20, 20, 100, 30);
        maNhanVienLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField maNhanVienField = new CustomTexField("Mã tự động (vd: NV001)");
        maNhanVienField.setBounds(130, 20, 200, 30);

        //Mật khẩu
        JLabel matKhauLabel = new JLabel("Mật khẩu");
        matKhauLabel.setBounds(20, 60, 100, 30);
        matKhauLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomPasswordField matKhauField = new CustomPasswordField("Nhập mật khẩu");
        matKhauField.setBounds(130, 60, 200, 30);

        //Họ tên
        JLabel hoTenLabel = new JLabel("Họ và tên");
        hoTenLabel.setBounds(20, 100, 100, 30);
        hoTenLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField hoTenField = new CustomTexField("Nguyễn Văn A");
        hoTenField.setBounds(130, 100, 200, 30);

        // Chức vụ
        JLabel chucVuLabel = new JLabel("Chức vụ");
        chucVuLabel.setBounds(20, 140, 100, 30);
        chucVuLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JComboBox<String> chucVuComboBox = new JComboBox<>();
        chucVuComboBox.addItem("Thủ kho");
        chucVuComboBox.addItem("Bán hàng");
        chucVuComboBox.addItem("Quản lý");
        chucVuComboBox.setBounds(130, 140, 200, 30);

        //Ngày sinh
        JLabel ngaySinhLabel = new JLabel("Ngày sinh");
        ngaySinhLabel.setBounds(20, 180, 100, 30);
        ngaySinhLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner ngaySinhField = new JSpinner(model);
        ngaySinhField.setBounds(130, 180, 200, 30);
        ngaySinhField.setEditor(new JSpinner.DateEditor(ngaySinhField, "dd/MM/yyyy"));


        //Giới tính
        JLabel gioiTinhLabel = new JLabel("Giới tính");
        gioiTinhLabel.setBounds(20, 220, 100, 30);
        gioiTinhLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JRadioButton gioiTinhNam = new JRadioButton("Nam");
        JRadioButton gioiTinhNu = new JRadioButton("Nữ");
        ButtonGroup gioiTinhGroup = new ButtonGroup();
        gioiTinhGroup.add(gioiTinhNam);
        gioiTinhGroup.add(gioiTinhNu);
        gioiTinhNam.setBounds(130, 220, 100, 30);
        gioiTinhNu.setBounds(230, 220, 100, 30);

        //Địa chỉ
        diaChiPanelAPI diaChiPanel = new diaChiPanelAPI();
        diaChiPanel.setBounds(390, 10, 340, 170);

        //Số điện thoại
        JLabel soDienThoaiLabel = new JLabel("Số điện thoại");
        soDienThoaiLabel.setBounds(400, 180, 100, 30);
        soDienThoaiLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField soDienThoaiField = new CustomTexField("0123456789");
        soDienThoaiField.setBounds(520, 180, 200, 30);

        //Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(400, 220, 100, 30);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField emailField = new CustomTexField("example@gmail.com");
        emailField.setBounds(520, 220, 200, 30);

        // Trạng thái
        JLabel trangThaiLabel = new JLabel("Trạng thái");
        trangThaiLabel.setBounds(20, 260, 100, 30);
        trangThaiLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JComboBox<String> trangThaiComboBox = new JComboBox<>();
        trangThaiComboBox.addItem("Đang làm việc");
        trangThaiComboBox.addItem("Đã nghỉ việc");
        trangThaiComboBox.addItem("Nghỉ hưu");
        trangThaiComboBox.addItem("Nghỉ thai sản");
        trangThaiComboBox.addItem("Nghỉ phép");
        trangThaiComboBox.addItem("Nghỉ không lương");
        trangThaiComboBox.setBounds(130, 260, 200, 30);




        //Button lưu
        CustomButton luuButton = new CustomButton("Lưu");
        luuButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        luuButton.setBounds(650, 380, 110, 30);

        //Button hủy
        CustomButton huyButton = new CustomButton("Hủy");
        huyButton.setButtonColors(CustomButton.ButtonColors.RED);
        huyButton.setBounds(530, 380, 110, 30);




        this.add(title);
        this.add(panel);
        panel.add(maNhanVienLabel);
        panel.add(maNhanVienField);
        panel.add(matKhauLabel);
        panel.add(matKhauField);
        panel.add(hoTenLabel);
        panel.add(hoTenField);
        panel.add(chucVuLabel);
        panel.add(chucVuComboBox);
        panel.add(ngaySinhLabel);
        panel.add(ngaySinhField);
        panel.add(gioiTinhLabel);
        panel.add(gioiTinhNam);
        panel.add(gioiTinhNu);
        panel.add(diaChiPanel);
        panel.add(soDienThoaiLabel);
        panel.add(soDienThoaiField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(trangThaiLabel);
        panel.add(trangThaiComboBox);



        this.add(luuButton);
        this.add(huyButton);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        new themNhanVienDialog();
    }
}
