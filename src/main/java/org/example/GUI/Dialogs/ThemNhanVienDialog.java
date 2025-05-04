package org.example.GUI.Dialogs;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.BUS.ChucVuBUS;
import org.example.BUS.NhanVienBUS;
import org.example.Components.*;
import org.example.DTO.ChucVuDTO;
import org.example.DTO.nhanVienDTO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ThemNhanVienDialog extends JDialog {
    private CustomTextField maNVField, tenNVField, emailField, soDTField, diaChiField;
    private CustomCombobox<String> tenCVComboBox;
    private CustomPasswordField matKhauField;
    private JRadioButton gioiTinhNam, gioiTinhNu;
    private JSpinner ngaySinhSpinner;
    private CustomButton luuButton, huyButton;
    private boolean isEditMode = false;
    private nhanVienDTO nhanVienEdit;
    private boolean isClosed = false;

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
        setSize(450, 680);
        getContentPane().setBackground(new Color(250, 250, 250));
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
            SwingUtilities.invokeLater(this::fillFormWithEmployeeData);
        }

        setLocationRelativeTo(null); // Căn giữa sau khi pack()
        setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.weightx = 1.0;
        gbc.gridwidth = 1;

        // Thêm các thành phần theo hàng dọc
        int row = 0;

        // Mã nhân viên
        maNVField = new CustomTextField("Mã tự động (vd: NV001)");
        maNVField.setState(CustomTextField.State.DISABLED);
        maNVField.setEnabled(false);
        maNVField.setEditable(false);
        String maNV = NhanVienBUS.taoMaNhanVienMoi();
        maNVField.setText(maNV);
        addFormRow(panel, "Mã nhân viên", maNVField.getContainer(), row++, gbc);
        maNVField.setEnabled(!isEditMode);

        // Tên nhân viên
        tenNVField = new CustomTextField("Nhập tên nhân viên");
        tenNVField.requestFocus();
        addFormRow(panel, "Tên nhân viên", tenNVField.getContainer(), row++, gbc);

        // Ngày sinh
        SpinnerDateModel dateModel = new SpinnerDateModel();
        ngaySinhSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(ngaySinhSpinner, "dd/MM/yyyy");
        ngaySinhSpinner.setEditor(dateEditor);
        addFormRow(panel, "Ngày sinh", ngaySinhSpinner, row++, gbc);

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
        addFormRow(panel, "Giới tính", gioiTinhPanel, row++, gbc);

        // Số điện thoại
        soDTField = new CustomTextField("0123456789");
        addFormRow(panel, "Số điện thoại", soDTField.getContainer(), row++, gbc);

        // Email
        emailField = new CustomTextField("example@gmail.com");
        addFormRow(panel, "Email", emailField.getContainer(), row++, gbc);

        // Mật khẩu
        matKhauField = new CustomPasswordField("Nhập mật khẩu");
        addFormRow(panel, "Mật khẩu", matKhauField.getContainer(), row++, gbc);

        // Chức vụ
        ArrayList<ChucVuDTO> listCV = ChucVuBUS.layDanhSachChucVu();
        String[] tenCVArray = new String[listCV.size()];
        for (int i = 0; i < listCV.size(); i++) {
            tenCVArray[i] = listCV.get(i).getTenCV();
        }
        tenCVComboBox = new CustomCombobox<>(tenCVArray);
        addFormRow(panel, "Chức vụ", tenCVComboBox, row++, gbc);

        // Địa chỉ
        diaChiField = new CustomTextField("Nhập địa chỉ");
        addFormRow(panel, "Địa chỉ", diaChiField.getContainer(), row++, gbc);

        return panel;
    }

    private void addFormRow(JPanel panel, String labelText, Component component, int row, GridBagConstraints gbc) {
        // Thêm nhãn bên trái
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel(labelText), gbc);

        // Thêm component bên phải
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;

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
            tenNVField.setText(nhanVienEdit.getHoTen());

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
            soDTField.setText(nhanVienEdit.getSDT());

            // Thiết lập email (đã chuyển sang phải)
            emailField.setText(nhanVienEdit.getEmail());

            // Thiết lập mật khẩu (đã chuyển sang phải)
            matKhauField.setText(nhanVienEdit.getMatKhau());

            // Thiết lập mã chức vụ
            // Tìm tên chức vụ từ mã chức vụ
            String tenCV = "";
            for (ChucVuDTO cv : ChucVuBUS.layDanhSachChucVu()) {
                if (cv.getMaCV().equals(nhanVienEdit.getMaCV())) {
                    tenCV = cv.getTenCV();
                    break;
                }
            }
            tenCVComboBox.setSelectedItem(tenCV);

            // Thiết lập địa chỉ
            diaChiField.setText(nhanVienEdit.getDiaChi());
        }
    }

    private void handleSave() {
        if (validateInput()) {
            try {
                // Lấy giá trị từ các trường nhập liệu
                String maNV = maNVField.getText().trim();
                String tenNV = tenNVField.getText().trim();
                java.util.Date date = (java.util.Date) ngaySinhSpinner.getValue();
                LocalDate ngaySinh = LocalDate.ofInstant(date.toInstant(), java.time.ZoneId.systemDefault());
                String gioiTinh = gioiTinhNam.isSelected() ? "Nam" : "Nữ";
                String diaChi = diaChiField.getText().trim();
                String email = emailField.getText().trim();
                String SDT = soDTField.getText().trim();
                String matKhau = new String(matKhauField.getPassword());
                String tenCV = (String) tenCVComboBox.getSelectedItem();

                // Chuyển tên chức vụ thành mã chức vụ
                String maCV = "";
                for (ChucVuDTO cv : ChucVuBUS.layDanhSachChucVu()) {
                    if (cv.getTenCV().equals(tenCV)) {
                        maCV = cv.getMaCV();
                        break;
                    }
                }

                // Tạo đối tượng nhanVienDTO từ dữ liệu form
                nhanVienDTO nhanVienDTO = new nhanVienDTO();
                nhanVienDTO.setMaNV(maNV);
                nhanVienDTO.setHoTen(tenNV);
                nhanVienDTO.setNgaySinh(ngaySinh);
                nhanVienDTO.setGioiTinh(gioiTinh);
                nhanVienDTO.setDiaChi(diaChi);
                nhanVienDTO.setEmail(email);
                nhanVienDTO.setSDT(SDT);
                nhanVienDTO.setMatKhau(matKhau);
                nhanVienDTO.setTrangThai(true); // Mặc định là hoạt động
                nhanVienDTO.setMaCV(maCV);


                // Thực hiện lưu hoặc cập nhật dữ liệu
                int result = 0;
                if (isEditMode) {
                    // Cập nhật dữ liệu vào cơ sở dữ liệu
                    result = NhanVienBUS.capNhatNhanVien(nhanVienDTO);
                    if (result > 0) {
                        CustomToastMessage.showSuccessToast(this, "Cập nhật nhân viên thành công!");
                        isClosed = true;
                        this.dispose();
                    } else {
                        CustomToastMessage.showErrorToast(this, "Cập nhật nhân viên thất bại!");
                    }
                } else {
                    // Tạo mã nhân viên mới nếu đang thêm mới
                    if (maNV.isEmpty()) {
                        nhanVienDTO.setMaNV(NhanVienBUS.taoMaNhanVienMoi());
                    }
                    // Lưu dữ liệu vào cơ sở dữ liệu
                    result = NhanVienBUS.themNhanVien(nhanVienDTO);
                    if (result > 0) {
                        CustomToastMessage.showSuccessToast(this, "Thêm nhân viên thành công!");
                        isClosed = true;
                        this.dispose();
                    } else {
                        CustomToastMessage.showErrorToast(this, "Thêm nhân viên thất bại!");
                    }
                }
            } catch (Exception ex) {
                CustomToastMessage.showErrorToast(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private boolean validateInput() {
        boolean isValid = true;
        Component firstInvalidField = null;

        // Reset trạng thái lỗi
        tenNVField.setState(CustomTextField.State.DEFAULT);
        soDTField.setState(CustomTextField.State.DEFAULT);
        emailField.setState(CustomTextField.State.DEFAULT);
        diaChiField.setState(CustomTextField.State.DEFAULT);
        matKhauField.setState(CustomPasswordField.State.DEFAULT);
        tenCVComboBox.setBorder(UIManager.getBorder("ComboBox.border")); // reset viền

        // Kiểm tra tên nhân viên
        if (tenNVField.getText().trim().isEmpty()) {
            tenNVField.setState(CustomTextField.State.INVALID);
            tenNVField.setErrorMessage("Tên nhân viên không được để trống");
            if (firstInvalidField == null) firstInvalidField = tenNVField;
            isValid = false;
        }

        // Kiểm tra số điện thoại
        String sdt = soDTField.getText().trim();
        if (sdt.isEmpty()) {
            soDTField.setState(CustomTextField.State.INVALID);
            soDTField.setErrorMessage("Số điện thoại không được để trống");
            if (firstInvalidField == null) firstInvalidField = soDTField;
            isValid = false;
        } else if (!sdt.matches("\\d+")) {
            soDTField.setState(CustomTextField.State.INVALID);
            soDTField.setErrorMessage("Số điện thoại phải là số");
            if (firstInvalidField == null) firstInvalidField = soDTField;
            isValid = false;
        }

        // Kiểm tra email
        String email = emailField.getText().trim();
        if (email.isEmpty() || email.equals("example@gmail.com")) {
            emailField.setState(CustomTextField.State.INVALID);
            emailField.setErrorMessage("Email không được để trống");
            if (firstInvalidField == null) firstInvalidField = emailField;
            isValid = false;
        } else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            emailField.setState(CustomTextField.State.INVALID);
            emailField.setErrorMessage("Email không đúng định dạng");
            if (firstInvalidField == null) firstInvalidField = emailField;
            isValid = false;
        }

        // Kiểm tra địa chỉ
        String diaChi = diaChiField.getText().trim();
        if (diaChi.isEmpty() || diaChi.equals("Nhập địa chỉ")) {
            diaChiField.setState(CustomTextField.State.INVALID);
            diaChiField.setErrorMessage("Địa chỉ không được để trống");
            if (firstInvalidField == null) firstInvalidField = diaChiField;
            isValid = false;
        }

        // Kiểm tra mật khẩu
        String matKhau = new String(matKhauField.getPassword()).trim();
        if (matKhau.isEmpty()) {
            matKhauField.setState(CustomPasswordField.State.INVALID);
            matKhauField.setErrorMessage("Mật khẩu không được để trống");
            if (firstInvalidField == null) firstInvalidField = matKhauField;
            isValid = false;
        } else if (matKhau.length() < 6) {
            matKhauField.setState(CustomPasswordField.State.INVALID);
            matKhauField.setErrorMessage("Mật khẩu phải có ít nhất 6 ký tự");
            if (firstInvalidField == null) firstInvalidField = matKhauField;
            isValid = false;
        }

        // Kiểm tra chức vụ
        if (tenCVComboBox.getSelectedItem() == null) {
            tenCVComboBox.setBorder(BorderFactory.createLineBorder(Color.RED));
            if (firstInvalidField == null) firstInvalidField = tenCVComboBox;
            isValid = false;
        }

        // Focus vào ô đầu tiên bị lỗi (nếu có)
        if (firstInvalidField != null) {
            firstInvalidField.requestFocus();
        }

        return isValid;
    }

    private void handleCancel() {
        // Code xử lý hủy
        isClosed = true;
        this.dispose();
    }

    public boolean isClosed() {
        return isClosed;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ThemNhanVienDialog::new);
    }
}