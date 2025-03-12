package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomPasswordField;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;
import org.example.Utils.diaChiPanelAPI;

import javax.swing.*;
import java.awt.*;
import javax.swing.SpinnerDateModel;

public class ThemNhanVienDialog extends JDialog {
    private CustomTexField maNhanVienField, hoTenField, soDienThoaiField, emailField;
    private CustomPasswordField matKhauField;
    private JComboBox<String> chucVuComboBox, trangThaiComboBox;
    private JSpinner ngaySinhField;
    private JRadioButton gioiTinhNam, gioiTinhNu;
    private diaChiPanelAPI diaChiPanel;
    private CustomButton luuButton, huyButton;

    public ThemNhanVienDialog() {
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
        setSize(790, 480);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setLayout(null);

        RoundedPanel panel = new RoundedPanel(20);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 50, 740, 320);
        panel.setLayout(null);

        // Tiêu đề
        JLabel title = new JLabel("Thêm nhân viên", SwingConstants.CENTER);
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
        // Cột bên trái
        createLeftComponents(panel);

        // Cột bên phải
        createRightComponents(panel);

        // Button lưu
        luuButton = new CustomButton("Lưu");
        luuButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        luuButton.setBounds(650, 380, 110, 30);
        add(luuButton);

        // Button hủy
        huyButton = new CustomButton("Hủy");
        huyButton.setButtonColors(CustomButton.ButtonColors.RED);
        huyButton.setBounds(530, 380, 110, 30);
        add(huyButton);
    }

    private void createLeftComponents(JPanel panel) {
        // Mã nhân viên
        panel.add(createLabel("Mã nhân viên", 20, 20));
        maNhanVienField = new CustomTexField("Mã tự động (vd: NV001)");
        maNhanVienField.setBounds(130, 20, 200, 30);
        panel.add(maNhanVienField);

        // Mật khẩu
        panel.add(createLabel("Mật khẩu", 20, 60));
        matKhauField = new CustomPasswordField("Nhập mật khẩu");
        matKhauField.setBounds(130, 60, 200, 30);
        panel.add(matKhauField);

        // Họ tên
        panel.add(createLabel("Họ và tên", 20, 100));
        hoTenField = new CustomTexField("Nguyễn Văn A");
        hoTenField.setBounds(130, 100, 200, 30);
        panel.add(hoTenField);

        // Chức vụ
        panel.add(createLabel("Chức vụ", 20, 140));
        chucVuComboBox = new JComboBox<>(new String[]{"Thủ kho", "Bán hàng", "Quản lý"});
        chucVuComboBox.setBounds(130, 140, 200, 30);
        panel.add(chucVuComboBox);

        // Ngày sinh
        panel.add(createLabel("Ngày sinh", 20, 180));
        SpinnerDateModel model = new SpinnerDateModel();
        ngaySinhField = new JSpinner(model);
        ngaySinhField.setBounds(130, 180, 200, 30);
        ngaySinhField.setEditor(new JSpinner.DateEditor(ngaySinhField, "dd/MM/yyyy"));
        panel.add(ngaySinhField);

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

        // Trạng thái
        panel.add(createLabel("Trạng thái", 20, 260));
        trangThaiComboBox = new JComboBox<>(new String[]{
                "Đang làm việc", "Đã nghỉ việc", "Nghỉ hưu",
                "Nghỉ thai sản", "Nghỉ phép", "Nghỉ không lương"
        });
        trangThaiComboBox.setBounds(130, 260, 200, 30);
        panel.add(trangThaiComboBox);
    }

    private void createRightComponents(JPanel panel) {
        // Địa chỉ
        diaChiPanel = new diaChiPanelAPI();
        diaChiPanel.setBounds(390, 10, 340, 170);
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
            // Thu thập thông tin nhân viên
            String maNV = maNhanVienField.getText();
            String matKhau = new String(matKhauField.getPassword());
            String hoTen = hoTenField.getText();
            String chucVu = (String) chucVuComboBox.getSelectedItem();
            java.util.Date ngaySinh = (java.util.Date) ngaySinhField.getValue();
            String gioiTinh = gioiTinhNam.isSelected() ? "Nam" : "Nữ";
            String soDienThoai = soDienThoaiField.getText();
            String email = emailField.getText();
            String trangThai = (String) trangThaiComboBox.getSelectedItem();

            // TODO: Lưu thông tin nhân viên vào cơ sở dữ liệu

            JOptionPane.showMessageDialog(this, "Đã lưu thông tin nhân viên thành công!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private boolean validateInput() {
        // Kiểm tra mật khẩu
        if (matKhauField.getPassword().length < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra họ tên
        if (hoTenField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống",
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

    public static void main(String[] args) {
        new ThemNhanVienDialog();
    }
}