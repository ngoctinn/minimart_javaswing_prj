package org.example.GUI.Dialogs;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.BUS.ChucVuBUS;
import org.example.Components.*;
// Bỏ import không cần thiết
// import org.example.Utils.diaChiPanelAPI;
import org.example.DTO.ChucVuDTO;
import org.example.DTO.nhanVienDTO;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
// Bỏ import không cần thiết
// import java.time.format.DateTimeFormatter;

public class ThemNhanVienDialog extends JDialog {
    // Thay đổi khai báo diaChiPanel và trangThaiComboBox
    private CustomTextField maNVField, tenNVField, emailField, soDTField, maHDField, diaChiField;
    private CustomCombobox<String> tenCVComboBox;
    private CustomPasswordField matKhauField;
    private JRadioButton gioiTinhNam, gioiTinhNu;
    private JSpinner ngaySinhSpinner;
    private CustomButton luuButton, huyButton;
    private boolean isEditMode = false;
    private nhanVienDTO nhanVienEdit;

    public ThemNhanVienDialog() {
        this.isEditMode = false;
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("ComboBox.buttonStyle", "icon-only");
            UIManager.put("ComboBox.buttonBackground", Color.WHITE);
            UIManager.put("ComboBox.buttonArrowColor", Color.BLACK);
            UIManager.put("PasswordField.showRevealButton", true);
            initGUI();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    
    public ThemNhanVienDialog(nhanVienDTO nhanVien) {
        this.isEditMode = true;
        this.nhanVienEdit = nhanVien;
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
        // Nếu đang ở chế độ sửa, cần điền thông tin nhân viên vào form
        if (isEditMode && nhanVienEdit != null) {
            fillFormWithEmployeeData();
        }
        setSize(800, 500);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(true);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        // Tiêu đề
        JLabel title = new JLabel(isEditMode ? "SỬA NHÂN VIÊN" : "THÊM NHÂN VIÊN", SwingConstants.CENTER);
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

        // Nếu đang ở chế độ sửa, cần điền thông tin nhân viên vào form sau khi các thành phần đã được khởi tạo
        if (isEditMode && nhanVienEdit != null) {
            fillFormWithEmployeeData();
        }

        pack(); // Gọi pack() sau khi đã thêm và điền dữ liệu
        setLocationRelativeTo(null); // Căn giữa sau khi pack()
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

        // Tạo panel bên trái (5 trường)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.fill = GridBagConstraints.HORIZONTAL;
        leftGbc.insets = new Insets(6, 2, 6, 2);
        leftGbc.weightx = 1.0;

        // Thêm các thành phần bên trái
        maNVField = new CustomTextField("Mã tự động (vd: NV001)");
        addFormRow(leftPanel, "Mã nhân viên", maNVField.getContainer(), 0, leftGbc); // Row 0
        maNVField.setEnabled(!isEditMode);

        tenNVField = new CustomTextField("Nhập tên nhân viên");
        addFormRow(leftPanel, "Tên nhân viên", tenNVField.getContainer(), 1, leftGbc); // Row 1

        // Ngày sinh
        SpinnerDateModel dateModel = new SpinnerDateModel();
        ngaySinhSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(ngaySinhSpinner, "dd/MM/yyyy");
        ngaySinhSpinner.setEditor(dateEditor);
        addFormRow(leftPanel, "Ngày sinh", ngaySinhSpinner, 2, leftGbc); // Row 2

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
        addFormRow(leftPanel, "Giới tính", gioiTinhPanel, 3, leftGbc); // Row 3

        // Số điện thoại
        soDTField = new CustomTextField("0123456789");
        addFormRow(leftPanel, "Số điện thoại", soDTField.getContainer(), 4, leftGbc); // Row 4


        // Tạo panel bên phải (5 trường)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightGbc.insets = new Insets(6, 2, 6, 2);
        rightGbc.weightx = 1.0;

        // Thêm các thành phần bên phải
        // Email (Chuyển sang phải)
        addFormRow(rightPanel, "Email", emailField = new CustomTextField("example@gmail.com"), 0, rightGbc); // Row 0

        // Mật khẩu (Chuyển sang phải)
        matKhauField = new CustomPasswordField("Nhập mật khẩu");
        addFormRow(rightPanel, "Mật khẩu", matKhauField.getContainer(), 1, rightGbc); // Row 1


        // tên chức vụ
        // lấy danh sách chức vụ từ cơ sở dữ liệu

        ArrayList<ChucVuDTO> listCV = ChucVuBUS.layDanhSachChucVu();
        String[] tenCVArray = new String[listCV.size()];
        for (int i = 0; i < listCV.size(); i++) {
            tenCVArray[i] = listCV.get(i).getTenCV();
        }

        addFormRow(rightPanel, "Mã chức vụ", tenCVComboBox = new CustomCombobox<>(tenCVArray), 3, rightGbc); // Row 3

        // Mã hợp đồng
        addFormRow(rightPanel, "Mã hợp đồng", maHDField = new CustomTextField("Nhập mã hợp đồng"), 4, rightGbc); // Row 4

        // Địa chỉ - Sử dụng CustomTextField
        addFormRow(rightPanel, "Địa chỉ", diaChiField = new CustomTextField("Nhập địa chỉ"), 5, rightGbc); // Row 5

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
        
        luuButton = new CustomButton(isEditMode ? "Cập nhật" : "Lưu");
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

    /**
     * Điền thông tin nhân viên vào form khi ở chế độ sửa
     */
    private void fillFormWithEmployeeData() {
        if (nhanVienEdit != null) {
            // Điền thông tin từ đối tượng nhanVienEdit vào các trường nhập liệu
            maNVField.setText(nhanVienEdit.getMaNV());
            tenNVField.setText(nhanVienEdit.getTenNV());

            // Thiết lập ngày sinh
            if (nhanVienEdit.getNgaySinh() != null) {
                java.util.Date date = java.util.Date.from(nhanVienEdit.getNgaySinh().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
                ngaySinhSpinner.setValue(date);
            }

            // Thiết lập giới tính
            if ("Nam".equals(nhanVienEdit.getGioiTinh())) {
                gioiTinhNam.setSelected(true);
            } else {
                gioiTinhNu.setSelected(true);
            }

            // Thiết lập số điện thoại
            soDTField.setText(String.valueOf(nhanVienEdit.getSoDT()));

            // Thiết lập email (đã chuyển sang phải)
            emailField.setText(nhanVienEdit.getEmail());

            // Thiết lập mật khẩu (đã chuyển sang phải)
            matKhauField.setText(nhanVienEdit.getMatKhau());

            // Thiết lập mã chức vụ và mã hợp đồng
            tenCVComboBox.setSelectedItem(nhanVienEdit.getMaCV());
            maHDField.setText(nhanVienEdit.getMaHD());

            // Thiết lập địa chỉ
            diaChiField.setText(nhanVienEdit.getDiaChi());
        }
    }

    private void handleSave() {
        // Code xử lý lưu dữ liệu nhân viên
        // Lấy giá trị từ các trường nhập liệu
        String maNV = maNVField.getText();
        String tenNV = tenNVField.getText();
        java.util.Date date = (java.util.Date) ngaySinhSpinner.getValue();
        LocalDate ngaySinh = LocalDate.ofInstant(date.toInstant(), java.time.ZoneId.systemDefault());
        String gioiTinh = gioiTinhNam.isSelected() ? "Nam" : "Nữ";
        
        // Lấy địa chỉ từ diaChiField
        String diaChi = diaChiField.getText();
        // Bỏ phần lấy địa chỉ từ diaChiPanel
        /*
        if (diaChiPanel.getSelectedProvince() != null && diaChiPanel.getSelectedDistrict() != null && diaChiPanel.getSelectedWard() != null) {
            diaChi = diaChiPanel.getSelectedWard().getName() + ", " +
                    diaChiPanel.getSelectedDistrict().getName() + ", " +
                    diaChiPanel.getSelectedProvince().getName();
        } else {
            // Lấy địa chỉ từ DTO nếu đang sửa và người dùng chưa chọn lại
            if (isEditMode && nhanVienEdit != null && nhanVienEdit.getDiaChi() != null && !nhanVienEdit.getDiaChi().isEmpty()) {
                 diaChi = nhanVienEdit.getDiaChi();
            } else {
                 diaChi = ""; // Hoặc giá trị mặc định nếu không có địa chỉ cũ và không chọn mới
            }
        }
        */

        String email = emailField.getText(); // Lấy email (đã ở panel phải)
        double soDT = 0;
        try {
            soDT = Double.parseDouble(soDTField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String matKhau = new String(matKhauField.getPassword()); // Lấy mật khẩu (đã ở panel phải)
        String tenCV = (String) tenCVComboBox.getSelectedItem(); // Lấy tên chức vụ từ combobox
        // chuyển tên chức vụ thành mã chức vụ
        String maCV = "";
        for (ChucVuDTO cv : ChucVuBUS.layDanhSachChucVu()) {
            if (cv.getTenCV().equals(tenCV)) {
                maCV = cv.getMaCV();
                break;
            }
        }
        String maHD = maHDField.getText();

        // Kiểm tra dữ liệu
        if (tenNV.isEmpty() || email.isEmpty() || matKhau.isEmpty() || diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo đối tượng nhanVienDTO từ dữ liệu form
        nhanVienDTO nhanVienDTO = new nhanVienDTO();
        nhanVienDTO.setMaNV(maNV);
        nhanVienDTO.setTenNV(tenNV);
        nhanVienDTO.setNgaySinh(ngaySinh);
        nhanVienDTO.setGioiTinh(gioiTinh);
        nhanVienDTO.setDiaChi(diaChi); // Gán địa chỉ từ diaChiField
        nhanVienDTO.setEmail(email);
        nhanVienDTO.setSoDT(soDT);
        nhanVienDTO.setMatKhau(matKhau);
        nhanVienDTO.setMaCV(maCV);
        nhanVienDTO.setMaHD(maHD);

        // Thực hiện lưu hoặc cập nhật dữ liệu
        int result = 0;
        if (isEditMode) {
            // TODO: Thêm code cập nhật dữ liệu vào cơ sở dữ liệu
            // result = NhanVienBUS.capNhatNhanVien(nhanVienDTO);
            
            JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // TODO: Thêm code lưu dữ liệu vào cơ sở dữ liệu
            // result = NhanVienBUS.themNhanVien(nhanVienDTO);
            
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        
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