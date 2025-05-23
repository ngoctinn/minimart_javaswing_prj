package org.example.GUI.Dialogs;

import com.github.lgooddatepicker.components.DatePicker;
import com.toedter.calendar.JDateChooser;
import org.example.BUS.KhachHangBUS;
import org.example.Components.*;
import org.example.DAO.KhachHangDAO;
import org.example.DTO.KhachHangDTO;
import org.example.GUI.Panels.doiTacPanel.KhachHangPanel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ThemKhachHangDialog extends JDialog {
    private CustomTextField maKhachHangField, diemField, hoTenField;
    private CustomTextField soDienThoaiField, emailField, diaChiField;
    // khời tạo giá trị ngày hiện tại;
    private CustomDatePicker ngaySinhDate;
    private JRadioButton gioiTinhNam, gioiTinhNu;
    private CustomButton luuButton, huyButton;
    private boolean isEditMode = false;
    private KhachHangDTO khachHangEdit;
    private boolean isClosed = false;


    public ThemKhachHangDialog(KhachHangDTO khachHang) {
        this.isEditMode = true;
        this.khachHangEdit = khachHang;
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
    
    // Thêm constructor mới nhận tham chiếu đến KhachHangPanel
    public ThemKhachHangDialog() {
        this.isEditMode = false;
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
    
    // Constructor cho chế độ sửa với tham chiếu đến KhachHangPanel

    private void initGUI() {
        setSize(500, 620);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(true);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        // Tiêu đề
        JLabel title = new JLabel(isEditMode ? "SỬA KHÁCH HÀNG" : "THÊM KHÁCH HÀNG", SwingConstants.CENTER);
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

        setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Thêm các thành phần theo hàng dọc
        int row = 0;
        
        // Mã khách hàng
        maKhachHangField = new CustomTextField("Mã tự động (vd: KH001)");
        maKhachHangField.setState(CustomTextField.State.DISABLED);
        addFormRow(panel, "Mã KH", maKhachHangField.getContainer(), row++, gbc);
        KhachHangBUS khachHangBUS = new KhachHangBUS();
        String maKhachHang = khachHangBUS.generateNextMaKH();
        maKhachHangField.setText(maKhachHang);
        
        // Điểm tích lũy
        diemField = new CustomTextField("Điểm tích lũy mặc định là 0");
        diemField.setState(CustomTextField.State.DISABLED);
        addFormRow(panel, "Điểm tích lũy", diemField.getContainer(), row++, gbc);
        
        // Họ và tên
        hoTenField = new CustomTextField("");
        addFormRow(panel, "Họ và tên", hoTenField.getContainer(), row++, gbc);
        
        // Số điện thoại
        soDienThoaiField = new CustomTextField("0123456789");
        addFormRow(panel, "Số điện thoại", soDienThoaiField.getContainer(), row++, gbc);
        
        // Email
        emailField = new CustomTextField("example@gmail.com");
        addFormRow(panel, "Email", emailField.getContainer(), row++, gbc);
        
        // Địa chỉ (TextField đơn giản thay thế cho diaChiPanelAPI)
        diaChiField = new CustomTextField("Nhập địa chỉ đầy đủ");
        addFormRow(panel, "Địa chỉ", diaChiField.getContainer(), row++, gbc);
        
        // Ngày sinh - Khởi tạo trước khi sử dụng
        ngaySinhDate = new CustomDatePicker();
        // đặt kich thước cho ngày sinh
        ngaySinhDate.setPreferredSize(new Dimension(300, 32));
        String Date = "2000-01-01"; // Ngày mặc định
        LocalDate currentDate = LocalDate.parse(Date);
        ngaySinhDate.setDate(currentDate); // Thiết lập ngày hiện tại
        addFormRow(panel, "Ngày sinh", ngaySinhDate, row++, gbc);
        
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
        
        // Điền thông tin nếu ở chế độ sửa
        if (isEditMode && khachHangEdit != null) {
            maKhachHangField.setText(khachHangEdit.getMaKH());
            hoTenField.setText(khachHangEdit.getHoTen());
            soDienThoaiField.setText(String.valueOf(khachHangEdit.getSDT()));
            emailField.setText(khachHangEdit.getEmail());
            diaChiField.setText(khachHangEdit.getDiaChi());

            diemField.setText(String.valueOf(khachHangEdit.getDiemTichLuy()));
            
            // Thiết lập giới tính
            if (khachHangEdit.getGioiTinh().equalsIgnoreCase("Nam")) {
                gioiTinhNam.setSelected(true);
            } else {
                gioiTinhNu.setSelected(true);
            }
            
            // Thiết lập ngày sinh nếu có
            if (khachHangEdit.getNgaySinh() != null) {
                LocalDate localDate = khachHangEdit.getNgaySinh();
                System.out.println("Ngày sinh: " + localDate);
                ngaySinhDate.setDate(localDate);
            } else {
                ngaySinhDate.setText("Chưa có thông tin");
            }
        }

        return panel;
    }

    private void addFormRow(JPanel panel, String labelText, Component component, int row, GridBagConstraints gbc) {
        gbc.gridy = row;
        
        // Label
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel(labelText), gbc);
        
        // Component
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Đảm bảo component có kích thước phù hợp
        if (component instanceof JTextField || component instanceof JComboBox || component instanceof JSpinner) {
            component.setPreferredSize(new Dimension(300, 30));
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

    private void handleSave() {
        if (validateInput()) {
            try {
                // Lấy thông tin từ form
                String maKhachHang = maKhachHangField.getText().trim();
                String hoTen = hoTenField.getText().trim();
                String soDienThoai = soDienThoaiField.getText().trim();
                String email = emailField.getText().trim();
                String diaChi = diaChiField.getText().trim();
                String diem = "0";
                String gioiTinh = gioiTinhNam.isSelected() ? "Nam" : "Nữ";
                LocalDate ngaySinh = this.ngaySinhDate.getDate();

                
                int result = 0;
                
                if (isEditMode) {
                    // Cập nhật khách hàng
                    // Tạo đối tượng khachHangDTO với thông tin đã cập nhật
                    KhachHangDTO khachHangDTO = new KhachHangDTO(
                        maKhachHang,
                        hoTen,
                        diaChi,
                        Integer.parseInt(diem),
                        soDienThoai,
                        gioiTinh,
                        email,
                        ngaySinh,
                        true // Giữ nguyên trạng thái
                    );
                    
                    KhachHangBUS khachHangBUS = new KhachHangBUS();
                    result = khachHangBUS.capNhatKhachHang(khachHangDTO);
                    
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        isClosed = true;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Thêm mới khách hàng
                    // Tạo đối tượng khachHangDTO với thông tin mới
                    KhachHangDTO khachHangDTO = new KhachHangDTO(
                        maKhachHang,
                        hoTen,
                        diaChi,
                        Integer.parseInt(diem),
                        soDienThoai,
                        gioiTinh,
                        email,
                        ngaySinh,
                        true // Mặc định trạng thái hoạt động
                    );
                    
                    // Gọi phương thức thêm mới từ BUS khi có
                    KhachHangBUS khachHangBUS = new KhachHangBUS();
                    result = khachHangBUS.themKhachHang(khachHangDTO);
                    
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        // Cập nhật lại bảng khách hàng nếu có tham chiếu đến panel cha

                        isClosed = true;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private boolean validateInput() {
        boolean isValid = true;
        Component firstInvalidField = null;

        // Reset trạng thái lỗi nếu cần
        hoTenField.setState(CustomTextField.State.DEFAULT);
        soDienThoaiField.setState(CustomTextField.State.DEFAULT);
        emailField.setState(CustomTextField.State.DEFAULT);
        diaChiField.setState(CustomTextField.State.DEFAULT);
        ngaySinhDate.setState(CustomDatePicker.State.DEFAULT);

        // Kiểm tra họ tên
        if (hoTenField.getText().trim().isEmpty()) {
            hoTenField.setState(CustomTextField.State.INVALID);
            hoTenField.setErrorMessage("Họ tên không được để trống");
            if (firstInvalidField == null) firstInvalidField = hoTenField;
            isValid = false;
        }

        // Kiểm tra số điện thoại
        String sdt = soDienThoaiField.getText().trim();
        if (sdt.isEmpty()) {
            soDienThoaiField.setState(CustomTextField.State.INVALID);
            soDienThoaiField.setErrorMessage("Số điện thoại không được để trống");
            if (firstInvalidField == null) firstInvalidField = soDienThoaiField;
            isValid = false;
        } else {
            try {
                Integer.parseInt(sdt);
            } catch (NumberFormatException e) {
                soDienThoaiField.setState(CustomTextField.State.INVALID);
                soDienThoaiField.setErrorMessage("Số điện thoại phải là số");
                if (firstInvalidField == null) firstInvalidField = soDienThoaiField;
                isValid = false;
            }
        }

        // Kiểm tra email
        String email = emailField.getText().trim();
        if (email.isEmpty() || email.equals("example@gmail.com")) {
            emailField.setState(CustomTextField.State.INVALID);
            emailField.setErrorMessage("Email không được để trống");
            if (firstInvalidField == null) firstInvalidField = emailField;
            isValid = false;
        }

        // Kiểm tra địa chỉ
        String diaChi = diaChiField.getText().trim();
        if (diaChi.isEmpty() || diaChi.equals("Nhập địa chỉ đầy đủ")) {
            diaChiField.setState(CustomTextField.State.INVALID);
            diaChiField.setErrorMessage("Địa chỉ không được để trống");
            if (firstInvalidField == null) firstInvalidField = diaChiField;
            isValid = false;
        }

        // Kiểm tra ngày sinh
        if (ngaySinhDate.getDate() == null) {
            ngaySinhDate.setState(CustomDatePicker.State.INVALID);
            ngaySinhDate.setErrorMessage("Vui lòng chọn ngày sinh");
            if (firstInvalidField == null) firstInvalidField = ngaySinhDate;
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
        dispose();
    }

    public boolean isClosed() {
        return isClosed;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ThemKhachHangDialog::new);
    }
}