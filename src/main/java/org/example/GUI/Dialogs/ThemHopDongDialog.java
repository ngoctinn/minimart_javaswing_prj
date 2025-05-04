package org.example.GUI.Dialogs;

import com.formdev.flatlaf.FlatLightLaf;
import com.github.lgooddatepicker.components.DatePicker;
import org.example.BUS.HopDongBUS;
import org.example.Components.*;
import org.example.DTO.hopDongDTO;
import org.example.DTO.nhanVienDTO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Dialog thêm mới hoặc chỉnh sửa hợp đồng
 */
public class ThemHopDongDialog extends JDialog {
    // UI Components
    private CustomTextField maHopDongField;
    private CustomTextField maNhanVienField;
    private CustomTextField luongCoBanField;
    private CustomDatePicker ngayBatDauPicker;
    private CustomDatePicker ngayKetThucPicker;
    private CustomButton luuButton;
    private CustomButton huyButton;
    private CustomCombobox<String> loaiHopDongComboBox;

    // Data
    private boolean isEditMode = false;
    private hopDongDTO hopDongEdit;
    private boolean isClosed = false;

    /**
     * Constructor cho chế độ thêm mới
     */
    public ThemHopDongDialog() {
        this.isEditMode = false;
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("ComboBox.buttonStyle", "icon-only");
            UIManager.put("ComboBox.buttonBackground", Color.WHITE);
            UIManager.put("ComboBox.buttonArrowColor", Color.BLACK);
            initGUI();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor cho chế độ chỉnh sửa
     * @param hopDong Đối tượng hợp đồng cần chỉnh sửa
     */
    public ThemHopDongDialog(hopDongDTO hopDong) {
        this.isEditMode = true;
        this.hopDongEdit = hopDong;
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("ComboBox.buttonStyle", "icon-only");
            UIManager.put("ComboBox.buttonBackground", Color.WHITE);
            UIManager.put("ComboBox.buttonArrowColor", Color.BLACK);
            initGUI();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi tạo giao diện
     */
    private void initGUI() {
        setSize(600, 400);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(true);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        // Tiêu đề
        JLabel title = new JLabel(isEditMode ? "SỬA HỢP ĐỒNG" : "THÊM HỢP ĐỒNG", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0, 102, 204));
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

        // Nếu đang ở chế độ sửa, cần điền thông tin hợp đồng vào form
        if (isEditMode && hopDongEdit != null) {
            fillFormWithContractData();
        } else {
            // Tạo mã hợp đồng mới
            generateNewContractId();
        }

        pack(); // Gọi pack() sau khi đã thêm và điền dữ liệu
        setLocationRelativeTo(null); // Căn giữa sau khi pack()
        setVisible(true);
    }

    /**
     * Tạo panel chứa form nhập liệu
     * @return JPanel chứa form nhập liệu
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Thêm các thành phần theo hàng dọc
        int row = 0;

        // Mã hợp đồng
        maHopDongField = new CustomTextField("Mã tự động (vd: HD001)");
        if (isEditMode) {
            maHopDongField.setEnabled(false);
        }
        addFormRow(panel, "Mã hợp đồng", maHopDongField.getContainer(), row++);

        // Mã nhân viên
        maNhanVienField = new CustomTextField("Nhập mã nhân viên");
        addFormRow(panel, "Mã nhân viên", maNhanVienField.getContainer(), row++);

        // Lương cơ bản
        luongCoBanField = new CustomTextField("Nhập lương cơ bản");
        addFormRow(panel, "Lương cơ bản", luongCoBanField.getContainer(), row++);

        // Ngày bắt đầu
        ngayBatDauPicker = new CustomDatePicker();
        ngayBatDauPicker.setDate(LocalDate.now());
        addFormRow(panel, "Ngày bắt đầu", ngayBatDauPicker, row++);

        // Ngày kết thúc
        ngayKetThucPicker = new CustomDatePicker();
        ngayKetThucPicker.setDate(LocalDate.now().plusYears(1)); // Mặc định 1 năm
        addFormRow(panel, "Ngày kết thúc", ngayKetThucPicker, row++);

        // Loại hợp đồng
        String[] loaiHopDongOptions = {"Chính thức", "Thử việc", "Thời vụ", "Hợp tác"};
        loaiHopDongComboBox = new CustomCombobox<>(loaiHopDongOptions);
        addFormRow(panel, "Loại hợp đồng", loaiHopDongComboBox, row++);

        return panel;
    }

    /**
     * Thêm một hàng vào form
     * @param panel Panel chứa form
     * @param labelText Nội dung nhãn
     * @param component Thành phần nhập liệu
     * @param row Vị trí hàng
     */
    private void addFormRow(JPanel panel, String labelText, Component component, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.weightx = 0.3;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel label = createLabel(labelText);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(component, gbc);
    }

    /**
     * Tạo panel chứa các nút
     * @return JPanel chứa các nút
     */
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

    /**
     * Tạo nhãn
     * @param text Nội dung nhãn
     * @return JLabel đã được tạo
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(51, 51, 51));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        return label;
    }

    /**
     * Thêm các sự kiện cho các thành phần
     */
    private void addEventListeners() {
        luuButton.addActionListener(e -> handleSave());
        huyButton.addActionListener(e -> handleCancel());
    }

    /**
     * Điền thông tin hợp đồng vào form khi ở chế độ sửa
     */
    private void fillFormWithContractData() {
        if (hopDongEdit != null) {
            // Điền thông tin từ đối tượng hopDongEdit vào các trường nhập liệu
            maHopDongField.setText(hopDongEdit.getMaHopDong());
            maNhanVienField.setText(hopDongEdit.getMaNV());
            luongCoBanField.setText(String.format("%,.0f", hopDongEdit.getLuongCB()));

            // Thiết lập ngày bắt đầu và ngày kết thúc
            if (hopDongEdit.getNgayBD() != null) {
                ngayBatDauPicker.setDate(hopDongEdit.getNgayBD());
            }

            if (hopDongEdit.getNgayKT() != null) {
                ngayKetThucPicker.setDate(hopDongEdit.getNgayKT());
            }

            // Thiết lập loại hợp đồng
            if (hopDongEdit.getLoaiHopDong() != null) {
                loaiHopDongComboBox.setSelectedItem(hopDongEdit.getLoaiHopDong());
            }
        }
    }

    /**
     * Tạo mã hợp đồng mới
     */
    private void generateNewContractId() {
        // Sử dụng HopDongBUS để tạo mã hợp đồng mới
        String maHopDong = HopDongBUS.generateNextMaHopDong();
        maHopDongField.setText(maHopDong);
    }

    /**
     * Xử lý sự kiện khi nhấn nút Lưu/Cập nhật
     */
    private void handleSave() {
        if (validateInput()) {
            try {
                // Lấy thông tin từ form
                String maHopDong = maHopDongField.getText().trim();
                String maNhanVien = maNhanVienField.getText().trim();
                double luongCoBan = Double.parseDouble(luongCoBanField.getText().trim().replace(",", ""));
                LocalDate ngayBatDau = ngayBatDauPicker.getDate();
                LocalDate ngayKetThuc = ngayKetThucPicker.getDate();
                String loaiHopDong = (String) loaiHopDongComboBox.getSelectedItem();

                // Tạo đối tượng hợp đồng (trạng thái mặc định là true - hoạt động)
                hopDongDTO hopDong = new hopDongDTO(maHopDong, ngayBatDau, ngayKetThuc, luongCoBan, maNhanVien, true, loaiHopDong);

                int result = 0;
                if (isEditMode) {
                    // Cập nhật hợp đồng vào cơ sở dữ liệu
                    result = HopDongBUS.capNhatHopDong(hopDong);

                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Cập nhật hợp đồng thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        isClosed = true;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật hợp đồng thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Thêm hợp đồng mới vào cơ sở dữ liệu
                    result = HopDongBUS.themHopDong(hopDong);

                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Thêm hợp đồng thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        isClosed = true;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm hợp đồng thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Lương cơ bản phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Xử lý sự kiện khi nhấn nút Hủy
     */
    private void handleCancel() {
        dispose();
    }

    /**
     * Kiểm tra dữ liệu nhập
     * @return true nếu dữ liệu hợp lệ, false nếu không
     */
    private boolean validateInput() {
        // Kiểm tra mã hợp đồng
        if (maHopDongField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hợp đồng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra mã nhân viên
        if (maNhanVienField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra lương cơ bản
        if (luongCoBanField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lương cơ bản", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            double luongCoBan = Double.parseDouble(luongCoBanField.getText().trim().replace(",", ""));
            if (luongCoBan <= 0) {
                JOptionPane.showMessageDialog(this, "Lương cơ bản phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lương cơ bản phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ngày bắt đầu
        if (ngayBatDauPicker.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày bắt đầu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ngày kết thúc
        if (ngayKetThucPicker.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày kết thúc", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ngày kết thúc phải sau ngày bắt đầu
        if (ngayKetThucPicker.getDate().isBefore(ngayBatDauPicker.getDate()) ||
            ngayKetThucPicker.getDate().isEqual(ngayBatDauPicker.getDate())) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Kiểm tra xem dialog đã được đóng chưa
     * @return true nếu dialog đã được đóng, false nếu chưa
     */
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * Phương thức main để chạy thử dialog
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ThemHopDongDialog();
        });
    }
}