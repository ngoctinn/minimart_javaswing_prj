package org.example.GUI.Dialogs;

import org.example.BUS.KhuyenMaiBUS;
import org.example.Components.CustomButton;
import org.example.Components.CustomDatePicker;
import org.example.Components.CustomTextField;
import org.example.Components.RoundedPanel;
import org.example.DTO.khuyenMaiDTO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ThemKhuyenMaiDialog extends JDialog {
    private CustomTextField maKhuyenMaiField, tenKhuyenMaiField, dieuKienField;
    private JSpinner phanTramSpinner;
    private CustomDatePicker ngayBatDauPicker, ngayKetThucPicker;
    private CustomButton luuButton, huyButton;
    private boolean isEditMode = false;
    private khuyenMaiDTO khuyenMaiEdit;

    // biến kiểm tra dialog đóng hay không
    private boolean isClosed = false;

    public ThemKhuyenMaiDialog() {
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

    public ThemKhuyenMaiDialog(khuyenMaiDTO khuyenMai) {
        this.isEditMode = true;
        this.khuyenMaiEdit = khuyenMai;
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
        setSize(450, 450);
        getContentPane().setBackground(new Color(250, 250, 250));
        setLocationRelativeTo(null);
        setResizable(true);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        // Tiêu đề
        JLabel title = new JLabel(isEditMode ? "SỬA KHUYẾN MÃI" : "THÊM KHUYẾN MÃI", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0,102,204));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Main panel
        RoundedPanel mainPanel = new RoundedPanel(20);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridBagLayout());

        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, new GridBagConstraints());
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
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;

        // Add form components
        maKhuyenMaiField = new CustomTextField("");
        maKhuyenMaiField.setEnabled(false);
        addFormRow(panel, "Mã khuyến mãi", maKhuyenMaiField.getContainer(), 0, gbc);

        if (isEditMode) {
            maKhuyenMaiField.setText(khuyenMaiEdit.getMaKM());
            maKhuyenMaiField.setState(CustomTextField.State.DISABLED);

            tenKhuyenMaiField = new CustomTextField("Nhập tên khuyến mãi");
            addFormRow(panel, "Tên khuyến mãi", tenKhuyenMaiField.getContainer(), 1, gbc);
            tenKhuyenMaiField.setText(khuyenMaiEdit.getTenKM());

            dieuKienField = new CustomTextField("Nhập điều kiện áp dụng");
            addFormRow(panel, "Điều kiện", dieuKienField.getContainer(), 2, gbc);
            dieuKienField.setText(khuyenMaiEdit.getDieuKien());

            // Phần trăm khuyến mãi
            SpinnerNumberModel model = new SpinnerNumberModel((double)khuyenMaiEdit.getPhanTram(), 0, 100, 1);
            phanTramSpinner = new JSpinner(model);
            addFormRow(panel, "Giá trị KM (%)", phanTramSpinner, 3, gbc);

            // Ngày bắt đầu
            ngayBatDauPicker = new CustomDatePicker(khuyenMaiEdit.getNgayBD());
            addFormRow(panel, "Ngày bắt đầu", ngayBatDauPicker, 4, gbc);

            // Ngày kết thúc
            ngayKetThucPicker = new CustomDatePicker(khuyenMaiEdit.getNgayKT());
            addFormRow(panel, "Ngày kết thúc", ngayKetThucPicker, 5, gbc);
        } else {
            String maKM = KhuyenMaiBUS.taoMaKhuyenMaiMoi();
            maKhuyenMaiField.setText(maKM);
            maKhuyenMaiField.setState(CustomTextField.State.DISABLED);

            tenKhuyenMaiField = new CustomTextField("Nhập tên khuyến mãi");
            tenKhuyenMaiField.setState(CustomTextField.State.DEFAULT);
            addFormRow(panel, "Tên khuyến mãi", tenKhuyenMaiField.getContainer(), 1, gbc);

            dieuKienField = new CustomTextField("Nhập điều kiện áp dụng");
            dieuKienField.setState(CustomTextField.State.DEFAULT);
            addFormRow(panel, "Điều kiện", dieuKienField.getContainer(), 2, gbc);

            // Phần trăm khuyến mãi
            SpinnerNumberModel model = new SpinnerNumberModel(0.0, 0, 100, 1);
            phanTramSpinner = new JSpinner(model);
            addFormRow(panel, "Giá trị KM (%)", phanTramSpinner, 3, gbc);

            // Ngày bắt đầu
            ngayBatDauPicker = new CustomDatePicker(LocalDate.now());
            addFormRow(panel, "Ngày bắt đầu", ngayBatDauPicker, 4, gbc);

            // Ngày kết thúc
            ngayKetThucPicker = new CustomDatePicker(LocalDate.now().plusDays(30));
            addFormRow(panel, "Ngày kết thúc", ngayKetThucPicker, 5, gbc);
        }

        return panel;
    }

    private void addFormRow(JPanel panel, String labelText, Component component, int row, GridBagConstraints gbc) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;

        // Đảm bảo component có kích thước phù hợp
        if (component instanceof JTextField || component instanceof JComboBox || component instanceof JSpinner) {
            component.setPreferredSize(new Dimension(250, 30));
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
            String maKM = maKhuyenMaiField.getText().trim();
            String tenKM = tenKhuyenMaiField.getText().trim();
            String dieuKien = dieuKienField.getText().trim();
            double phanTram = (Double) phanTramSpinner.getValue();
            LocalDate ngayBD = ngayBatDauPicker.getDate();
            LocalDate ngayKT = ngayKetThucPicker.getDate();
            int result;

            if (isEditMode) {
                // Cập nhật khuyến mãi
                khuyenMaiDTO khuyenMaiDTO = new khuyenMaiDTO(maKM, tenKM, dieuKien, ngayBD, ngayKT, phanTram, khuyenMaiEdit.isTrangThai());
                result = KhuyenMaiBUS.capNhatKhuyenMai(khuyenMaiDTO);

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);

                    isClosed = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thất bại", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Thêm mới khuyến mãi
                khuyenMaiDTO khuyenMaiDTO = new khuyenMaiDTO(maKM, tenKM, dieuKien, ngayBD, ngayKT, phanTram, true);
                result = KhuyenMaiBUS.themKhuyenMai(khuyenMaiDTO);


                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);

                    isClosed = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thất bại", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean validateInput() {
        // Kiểm tra mã khuyến mãi
        maKhuyenMaiField.setState(CustomTextField.State.DISABLED);

        // Kiểm tra tên khuyến mãi
        if (tenKhuyenMaiField.getText().trim().isEmpty() || tenKhuyenMaiField.getText().equals("Nhập tên khuyến mãi")) {
            tenKhuyenMaiField.setState(CustomTextField.State.INVALID);
            tenKhuyenMaiField.setErrorMessage("Tên khuyến mãi không được để trống");
            tenKhuyenMaiField.requestFocus();
            return false;
        }
        tenKhuyenMaiField.setState(CustomTextField.State.DEFAULT);

        // Kiểm tra điều kiện
        if (dieuKienField.getText().trim().isEmpty() || dieuKienField.getText().equals("Nhập điều kiện áp dụng")) {
            dieuKienField.setState(CustomTextField.State.INVALID);
            dieuKienField.setErrorMessage("Điều kiện không được để trống");
            dieuKienField.requestFocus();
            return false;
        }
        dieuKienField.setState(CustomTextField.State.DEFAULT);

        // Kiểm tra ngày bắt đầu và kết thúc
        LocalDate ngayBatDau = ngayBatDauPicker.getDate();
        LocalDate ngayKetThuc = ngayKetThucPicker.getDate();

        if (ngayBatDau == null) {
            ngayBatDauPicker.setState(CustomDatePicker.State.INVALID);
            ngayBatDauPicker.setErrorMessage("Ngày bắt đầu không được để trống");
            return false;
        }
        ngayBatDauPicker.setState(CustomDatePicker.State.DEFAULT);

        if (ngayKetThuc == null) {
            ngayKetThucPicker.setState(CustomDatePicker.State.INVALID);
            ngayKetThucPicker.setErrorMessage("Ngày kết thúc không được để trống");
            return false;
        }
        ngayKetThucPicker.setState(CustomDatePicker.State.DEFAULT);

        if (ngayKetThuc.isBefore(ngayBatDau) || ngayKetThuc.isEqual(ngayBatDau)) {
            ngayKetThucPicker.setState(CustomDatePicker.State.INVALID);
            ngayKetThucPicker.setErrorMessage("Ngày kết thúc phải sau ngày bắt đầu");
            return false;
        }
        ngayKetThucPicker.setState(CustomDatePicker.State.DEFAULT);

        return true;
    }

    private void handleCancel() {
        dispose();
    }

    public boolean isClosed() {
        return isClosed;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ThemKhuyenMaiDialog());
    }
}