package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.CustomPasswordField;
import org.example.Components.RoundedPanel;
import org.example.Utils.diaChiPanelAPI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ThemNhanVienDialog extends JDialog {
    private CustomTexField maNVField, tenNVField, emailField, soDTField, maCVField, maHDField;
    private CustomPasswordField matKhauField;
    private JComboBox<String> trangThaiComboBox;
    private JRadioButton gioiTinhNam, gioiTinhNu;
    private JSpinner ngaySinhSpinner;
    private diaChiPanelAPI diaChiPanel;
    private CustomButton luuButton, huyButton;

    public ThemNhanVienDialog() {
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
        JLabel title = new JLabel("THÊM NHÂN VIÊN", SwingConstants.CENTER);
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
        addFormRow(leftPanel, "Mã nhân viên", maNVField = new CustomTexField("Mã tự động (vd: NV001)"), 0, leftGbc);
        addFormRow(leftPanel, "Tên nhân viên", tenNVField = new CustomTexField("Nhập tên nhân viên"), 1, leftGbc);
        
        // Ngày sinh
        SpinnerDateModel dateModel = new SpinnerDateModel();
        ngaySinhSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(ngaySinhSpinner, "dd/MM/yyyy");
        ngaySinhSpinner.setEditor(dateEditor);
        addFormRow(leftPanel, "Ngày sinh", ngaySinhSpinner, 2, leftGbc);
        
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
        addFormRow(leftPanel, "Giới tính", gioiTinhPanel, 3, leftGbc);
        
        // Số điện thoại
        addFormRow(leftPanel, "Số điện thoại", soDTField = new CustomTexField("0123456789"), 4, leftGbc);
        
        // Email
        addFormRow(leftPanel, "Email", emailField = new CustomTexField("example@gmail.com"), 5, leftGbc);
        
        // Mật khẩu
        addFormRow(leftPanel, "Mật khẩu", matKhauField = new CustomPasswordField("Nhập mật khẩu"), 6, leftGbc);

        // Tạo panel bên phải
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightGbc.insets = new Insets(6, 2, 6, 2);
        rightGbc.weightx = 1.0;

        // Thêm các thành phần bên phải
        
        // Trạng thái
        trangThaiComboBox = new JComboBox<>(new String[]{"Đang làm việc", "Đã nghỉ việc"});
        addFormRow(rightPanel, "Trạng thái", trangThaiComboBox, 1, rightGbc);
        
        // Mã chức vụ
        addFormRow(rightPanel, "Mã chức vụ", maCVField = new CustomTexField("Nhập mã chức vụ"), 2, rightGbc);
        
        // Mã hợp đồng
        addFormRow(rightPanel, "Mã hợp đồng", maHDField = new CustomTexField("Nhập mã hợp đồng"), 3, rightGbc);
        
        // Thêm panel địa chỉ
        diaChiPanel = new diaChiPanelAPI();
        rightGbc.gridx = 0;
        rightGbc.gridy = 4; 
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
        luuButton.addActionListener(e -> handleSave());
        huyButton.addActionListener(e -> handleCancel());
    }

    private void handleSave() {
        // Code xử lý lưu dữ liệu nhân viên
        // Lấy giá trị từ các trường nhập liệu
        String maNV = maNVField.getText();
        String tenNV = tenNVField.getText();
        java.util.Date date = (java.util.Date) ngaySinhSpinner.getValue();
        LocalDate ngaySinh = LocalDate.ofInstant(date.toInstant(), java.time.ZoneId.systemDefault());
        String gioiTinh = gioiTinhNam.isSelected() ? "Nam" : "Nữ";
        String diaChi = "Địa chỉ từ diaChiPanel"; // Cần xử lý lấy địa chỉ từ diaChiPanel
        String email = emailField.getText();
        int trangThai = trangThaiComboBox.getSelectedIndex();
        double soDT = 0;
        try {
            soDT = Double.parseDouble(soDTField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String matKhau = new String(matKhauField.getPassword());
        String maCV = maCVField.getText();
        String maHD = maHDField.getText();
        
        // Kiểm tra dữ liệu
        if (tenNV.isEmpty() || email.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Thực hiện lưu dữ liệu
        // TODO: Thêm code lưu dữ liệu vào cơ sở dữ liệu
        
        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }

    private void handleCancel() {
        // Code xử lý hủy
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ThemNhanVienDialog::new);
    }
}