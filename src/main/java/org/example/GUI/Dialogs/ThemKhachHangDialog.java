package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;
import org.example.DAO.KhachHangDAO_test;
import org.example.DTO.khachHangDTO;
import org.example.Utils.diaChiPanelAPI;
import org.example.BUS.khachHangBUS_test;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
            initGUI();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void initGUI() {
        setSize(800, 500);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(true);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        // Tiêu đề
        JLabel title = new JLabel("THÊM KHÁCH HÀNG", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0,102,204));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Main panel
        RoundedPanel mainPanel = new RoundedPanel(20);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridBagLayout());

        // Create form panel with components
        JPanel formPanel = createFormPanel();
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.fill = GridBagConstraints.BOTH;
        mainGbc.weightx = 1.0;
        mainGbc.weighty = 1.0;
        mainGbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(formPanel, mainGbc);
        add(mainPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        // Add event listeners
        addEventListeners();

        pack();
        setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Tạo panel bên trái
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.fill = GridBagConstraints.HORIZONTAL;
        leftGbc.insets = new Insets(6, 2, 6, 2);
        leftGbc.weightx = 1.0;

        // Thêm các thành phần bên trái
        addFormRow(leftPanel, "Mã KH", maKhachHangField = new CustomTexField("Mã tự động (vd: KH001)"), 0, leftGbc);
        addFormRow(leftPanel, "Điểm tích lũy", diemField = new CustomTexField("0"), 1, leftGbc);
        addFormRow(leftPanel, "Họ và tên", hoTenField = new CustomTexField("Nguyễn Văn A"), 2, leftGbc);

        trangThaiComboBox = new JComboBox<>(new String[]{
                "Đang hoạt động", "Ngưng hoạt động", "Chưa xác minh"
        });
        addFormRow(leftPanel, "Trạng thái", trangThaiComboBox, 3, leftGbc);

        // Ngày sinh
        SpinnerDateModel model = new SpinnerDateModel();
        ngaySinhSpinner = new JSpinner(model);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(ngaySinhSpinner, "dd/MM/yyyy");
        ngaySinhSpinner.setEditor(dateEditor);
        addFormRow(leftPanel, "Ngày sinh", ngaySinhSpinner, 4, leftGbc);

        // Giới tính
        JPanel gioiTinhPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        gioiTinhPanel.setBackground(Color.WHITE);
        gioiTinhNam = new JRadioButton("Nam");
        gioiTinhNu = new JRadioButton("Nữ");
        ButtonGroup gioiTinhGroup = new ButtonGroup();
        gioiTinhGroup.add(gioiTinhNam);
        gioiTinhGroup.add(gioiTinhNu);
        gioiTinhNam.setSelected(true); // Default selection
        gioiTinhPanel.add(gioiTinhNam);
        gioiTinhPanel.add(gioiTinhNu);
        addFormRow(leftPanel, "Giới tính", gioiTinhPanel, 5, leftGbc);

        // Tạo panel bên phải
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightGbc.insets = new Insets(6, 2, 6, 2);
        rightGbc.weightx = 1.0;

        // Thêm các thành phần bên phải
            // Số điện thoại
        addFormRow(rightPanel, "Số điện thoại", soDienThoaiField = new CustomTexField("0123456789"), 1, rightGbc);

        // Email
        addFormRow(rightPanel, "Email", emailField = new CustomTexField("example@gmail.com"), 2, rightGbc);


        // Thêm tiêu đề cho phần địa chỉ

        // Thêm panel địa chỉ
        diaChiPanel = new diaChiPanelAPI();
        rightGbc.gridx = 0;
        rightGbc.gridy = 3;
        rightGbc.gridwidth = 2;
        rightGbc.gridheight = 3;
        rightGbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(diaChiPanel, rightGbc);

        // Reset gridwidth và gridheight cho các thành phần tiếp theo
        rightGbc.gridwidth = 1;
        rightGbc.gridheight = 1;

        // Thêm cả hai panel vào panel chính
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        panel.add(leftPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        panel.add(rightPanel, gbc);

        return panel;
    }

    private void addFormRow(JPanel panel, String labelText, Component component, int row, GridBagConstraints gbc) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;

        // Đảm bảo component có kích thước phù hợp
        if (component instanceof JTextField || component instanceof JComboBox || component instanceof JSpinner) {
            component.setPreferredSize(new Dimension(component.getPreferredSize().width, 30));
        }

        panel.add(component, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        huyButton = new CustomButton("Hủy");
        huyButton.setButtonColors(CustomButton.ButtonColors.RED);

        luuButton = new CustomButton("Lưu");
        luuButton.setButtonColors(CustomButton.ButtonColors.GREEN);

        buttonPanel.add(huyButton);
        buttonPanel.add(luuButton);

        return buttonPanel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(51, 51, 51));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        return label;
    }

    private void addEventListeners() {
        // Nút lưu: Sẽ xử lý lưu thông tin khách hàng khi được nhấn
        luuButton.addActionListener(e -> {
            if (checkForm()) { // Kiểm tra form có hợp lệ không
                if (saveCustomerData()) { // Nếu lưu thành công thì đóng cửa sổ
                    JOptionPane.showMessageDialog(null, "Lưu khách hàng thành công!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Lưu khách hàng thất bại! Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Nút hủy: Đóng dialog khi được nhấn
        huyButton.addActionListener(e -> dispose());
    }
    private boolean checkForm() {
        return true;
    }

    private boolean saveCustomerData() {
        try {
            // Lấy dữ liệu từ các trường nhập liệu
            String maKH = maKhachHangField.getText();
            int diemTichLuy = Integer.parseInt(diemField.getText());
            String hoTen = hoTenField.getText();
            String diaChi = " ";
            String soNha = diaChiPanel.getSoNha().toString();
            String tenDuong = diaChiPanel.getDuong().toString();
            diaChiPanelAPI.Province tinh = diaChiPanel.getSelectedProvince();
            diaChiPanelAPI.District quan  = diaChiPanel.getSelectedDistrict();
            diaChiPanelAPI.Ward phuong = diaChiPanel.getSelectedWard();
            diaChi = (soNha +", " + tenDuong +", " + phuong + ", " + quan +", " + tinh).toString();
            String sdt = hoTenField.getText();
            String gioiTinh = "";
            if (gioiTinhNam.isSelected()) {
                gioiTinh = "Nam";
            } else if (gioiTinhNu.isSelected()) {
                gioiTinh = "Nữ";
            }
            String email = emailField.getText();
            // Lấy giá trị từ JSpinner
            Date selectedDate = (Date) ngaySinhSpinner.getValue();
            // Chuyển đổi từ java.util.Date sang LocalDate
            LocalDate ngaySinh = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int trangThai = 0;
            String tt = (String) trangThaiComboBox.getSelectedItem();
            if (tt.equals("Đang hoạt động")) {
                trangThai = 1;
            }
            if (tt.equals("Ngưng hoạt động")) {
                trangThai = 0;
            } else trangThai = 2;

            // Tạo đối tượng khách hàng
            khachHangBUS_test khBus = new khachHangBUS_test();
            khachHangDTO newKH = new khachHangDTO(maKH, diemTichLuy, hoTen, diaChi, sdt, gioiTinh, email, ngaySinh, trangThai);
            if (khBus.addData(newKH)) {
                JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công!");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm khách hàng vào cơ thất bại!");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (HeadlessException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ThemKhachHangDialog::new);
    }
}