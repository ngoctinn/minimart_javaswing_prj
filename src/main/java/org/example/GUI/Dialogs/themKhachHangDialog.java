package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomPasswordField;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import javax.swing.SpinnerDateModel;

public class themKhachHangDialog extends JDialog {
    public themKhachHangDialog () {
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
        this.setSize(790, 440);
        this.getContentPane().setBackground(new Color(245, 245, 245));
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setLayout(null);

        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 50, 740, 280);
        panel.setLayout(null);

        // Tạo tiêu đề
        JLabel title = new JLabel("Thêm khách hàng");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(25, 10, 200, 30);

        // Mã khách hàng
        JLabel maKhachHangLabel = new JLabel("Mã KH");
        maKhachHangLabel.setBounds(20, 20, 100, 30);
        maKhachHangLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField maKhachHangField = new CustomTexField("Mã tự động (vd: KH001)");
        maKhachHangField.setBounds(130, 20, 200, 30);

        //Điểm tích lũy
        JLabel diemLabel = new JLabel("Điểm tích lũy");
        diemLabel.setBounds(20, 60, 100, 30);
        diemLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField diemField = new CustomTexField("0");
        diemField.setBounds(130, 60, 200, 30);

        //Họ tên
        JLabel hoTenLabel = new JLabel("Họ và tên");
        hoTenLabel.setBounds(20, 100, 100, 30);
        hoTenLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomTexField hoTenField = new CustomTexField("Nguyễn Văn A");
        hoTenField.setBounds(130, 100, 200, 30);

        //Ngày sinh
        JLabel ngaySinhLabel = new JLabel("Ngày sinh");
        ngaySinhLabel.setBounds(20, 180, 100, 30);
        ngaySinhLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner ngaySinhSpinner = new JSpinner(model);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(ngaySinhSpinner, "dd/MM/yyyy");
        ngaySinhSpinner.setEditor(dateEditor);
        ngaySinhSpinner.setBounds(130, 180, 200, 30);


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
        JLabel diaChiLabel = new JLabel("Địa chỉ");
        diaChiLabel.setBounds(400, 20, 100, 30);
        diaChiLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JPanel diaChiPanel = new JPanel();
        diaChiPanel.setBounds(400, 50, 320, 125);
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
        trangThaiLabel.setBounds(20, 140, 100, 30);
        trangThaiLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JComboBox<String> trangThaiComboBox = new JComboBox<>();
        trangThaiComboBox.addItem("Đang hoạt động");
        trangThaiComboBox.addItem("Ngưng hoạt động");
        trangThaiComboBox.addItem("Chưa xác minh");
        trangThaiComboBox.setBounds(130, 140, 200, 30);




        //Button lưu
        CustomButton luuButton = new CustomButton("Lưu");
        luuButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        luuButton.setBounds(650, 340, 110, 30);

        //Button hủy
        CustomButton huyButton = new CustomButton("Hủy");
        huyButton.setButtonColors(CustomButton.ButtonColors.RED);
        huyButton.setBounds(530, 340, 110, 30);




        this.add(title);
        this.add(panel);
        panel.add(maKhachHangLabel);
        panel.add(maKhachHangField);
        panel.add(diemLabel);
        panel.add(diemField);
        panel.add(hoTenLabel);
        panel.add(hoTenField);
        panel.add(ngaySinhLabel);
        panel.add(ngaySinhSpinner);
        panel.add(gioiTinhLabel);
        panel.add(gioiTinhNam);
        panel.add(gioiTinhNu);
        panel.add(diaChiLabel);
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
        new themKhachHangDialog();
    }
}
