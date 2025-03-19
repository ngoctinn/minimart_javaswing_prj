package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;
import org.example.Utils.diaChiPanelAPI;

import javax.swing.*;
import java.awt.*;

public class ThemKhachHangDialog extends JDialog {
    private CustomTexField maKhachHangField, diemField, hoTenField;
    private CustomTexField soDienThoaiField, emailField;
    private JSpinner ngaySinhSpinner;
    private JComboBox<String> trangThaiComboBox;
    private JRadioButton gioiTinhNam, gioiTinhNu;
    private diaChiPanelAPI diaChiPanel;
    private CustomButton luuButton, huyButton;

    public ThemKhachHangDialog() {
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
        setSize(790, 440);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setLayout(null);

        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 50, 740, 280);
        panel.setLayout(null);

        // Tiêu đề
        JLabel title = new JLabel("THÊM KHÁCH HÀNG", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(0, 10, getWidth(), 30);
        title.setForeground(new Color(0,102,204));
        add(title);

        // Tạo các thành phần
        createLeftComponents(panel);
        createRightComponents(panel);

        // Thêm sự kiện cho các thành phần
        addEventListeners();

        add(panel);
        setVisible(true);
    }

    private void createLeftComponents(JPanel panel) {
        // Mã khách hàng
        panel.add(createLabel("Mã KH", 20, 20));
        maKhachHangField = new CustomTexField("Mã tự động (vd: KH001)");
        maKhachHangField.setBounds(130, 20, 200, 30);
        panel.add(maKhachHangField);

        // Điểm tích lũy
        panel.add(createLabel("Điểm tích lũy", 20, 60));
        diemField = new CustomTexField("0");
        diemField.setBounds(130, 60, 200, 30);
        panel.add(diemField);

        // Họ tên
        panel.add(createLabel("Họ và tên", 20, 100));
        hoTenField = new CustomTexField("Nguyễn Văn A");
        hoTenField.setBounds(130, 100, 200, 30);
        panel.add(hoTenField);

        // Trạng thái
        panel.add(createLabel("Trạng thái", 20, 140));
        trangThaiComboBox = new JComboBox<>(new String[]{
                "Đang hoạt động", "Ngưng hoạt động", "Chưa xác minh"
        });
        trangThaiComboBox.setBounds(130, 140, 200, 30);
        panel.add(trangThaiComboBox);

        // Ngày sinh
        panel.add(createLabel("Ngày sinh", 20, 180));
        SpinnerDateModel model = new SpinnerDateModel();
        ngaySinhSpinner = new JSpinner(model);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(ngaySinhSpinner, "dd/MM/yyyy");
        ngaySinhSpinner.setEditor(dateEditor);
        ngaySinhSpinner.setBounds(130, 180, 200, 30);
        panel.add(ngaySinhSpinner);

        // Giới tính
        panel.add(createLabel("Giới tính", 20, 220));
        gioiTinhNam = new JRadioButton("Nam");
        gioiTinhNu = new JRadioButton("Nữ");
        ButtonGroup gioiTinhGroup = new ButtonGroup();
        gioiTinhGroup.add(gioiTinhNam);
        gioiTinhGroup.add(gioiTinhNu);
        gioiTinhNam.setBounds(130, 220, 100, 30);
        gioiTinhNu.setBounds(230, 220, 100, 30);
        gioiTinhNam.setSelected(true); // Default selection
        panel.add(gioiTinhNam);
        panel.add(gioiTinhNu);
    }

    private void createRightComponents(JPanel panel) {
        // Địa chỉ
        panel.add(createLabel("Địa chỉ", 400, 20));
        diaChiPanel = new diaChiPanelAPI();
        diaChiPanel.setBounds(390, 50, 340, 170);
        panel.add(diaChiPanel);

        // Số điện thoại
        panel.add(createLabel("Số điện thoại", 400, 180));
        soDienThoaiField = new CustomTexField("0123456789");
        soDienThoaiField.setBounds(520, 180, 200, 30);
        panel.add(soDienThoaiField);

        // Email
        panel.add(createLabel("Email", 400, 220));
        emailField = new CustomTexField("example@gmail.com");
        emailField.setBounds(520, 220, 200, 30);
        panel.add(emailField);

        // Button lưu
        luuButton = new CustomButton("Lưu");
        luuButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        luuButton.setBounds(650, 340, 110, 30);
        add(luuButton);

        // Button hủy
        huyButton = new CustomButton("Hủy");
        huyButton.setButtonColors(CustomButton.ButtonColors.RED);
        huyButton.setBounds(530, 340, 110, 30);
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
        // Nút lưu: Sẽ xử lý lưu thông tin khách hàng khi được nhấn
        luuButton.addActionListener(e -> dispose());

        // Nút hủy: Đóng dialog khi được nhấn
        huyButton.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        new ThemKhachHangDialog();
    }
}