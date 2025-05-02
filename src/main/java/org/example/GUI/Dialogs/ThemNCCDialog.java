package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTextField;
import org.example.Components.RoundedPanel;
import org.example.DTO.nhaCungCapDTO;

import javax.swing.*;
import java.awt.*;

public class ThemNCCDialog extends JDialog {
    private CustomTextField maNCCField, tenNCCField, soDienThoaiField, diaChiField;
    private CustomButton luuButton, huyButton;
    private boolean isEditMode = false;
    private nhaCungCapDTO nhaCungCapEdit;

    public ThemNCCDialog() {
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
    
    public ThemNCCDialog(nhaCungCapDTO nhaCungCap) {
        this.isEditMode = true;
        this.nhaCungCapEdit = nhaCungCap;
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
        // Xóa dòng gọi fillFormWithSupplierData() ở đây
        // if (isEditMode && nhaCungCapEdit != null) {
        //     fillFormWithSupplierData();
        // }
        
        setSize(600, 500);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(true);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel(isEditMode ? "SỬA NHÀ CUNG CẤP" : "THÊM NHÀ CUNG CẤP", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0,102,204));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        RoundedPanel mainPanel = new RoundedPanel(20);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridBagLayout());
        
        JPanel formPanel = createFormPanel();
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.fill = GridBagConstraints.BOTH;
        mainGbc.weightx = 1.0;
        mainGbc.weighty = 1.0;
        mainGbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(formPanel, mainGbc);
        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        addEventListeners();

        // Di chuyển đoạn code này xuống đây, sau khi các trường đã được khởi tạo
        if (isEditMode && nhaCungCapEdit != null) {
            fillFormWithSupplierData();
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 1.0;

        maNCCField = new CustomTextField("Mã tự động (vd: NCC001)");
        String maNCC = org.example.BUS.NhaCungCapBUS.generateNextMaNCC();
        maNCCField.setText(maNCC);
        maNCCField.setState(CustomTextField.State.DISABLED);
        maNCCField.setEnabled(false);
        addFormRow(panel, "Mã NCC", maNCCField.getContainer(), 0, gbc);
        
        tenNCCField = new CustomTextField("Nhập tên nhà cung cấp");
        addFormRow(panel, "Tên NCC", tenNCCField.getContainer(), 1, gbc);
        
        soDienThoaiField = new CustomTextField("0123456789");
        addFormRow(panel, "Số điện thoại", soDienThoaiField.getContainer(), 2, gbc);
        
        diaChiField = new CustomTextField("Nhập địa chỉ đầy đủ");
        addFormRow(panel, "Địa chỉ", diaChiField.getContainer(), 3, gbc);

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
    
    private void fillFormWithSupplierData() {
        if (nhaCungCapEdit != null) {
            maNCCField.setText(nhaCungCapEdit.getMaNCC());
            tenNCCField.setText(nhaCungCapEdit.getTenNCC());
            soDienThoaiField.setText(nhaCungCapEdit.getSDT());
            diaChiField.setText(nhaCungCapEdit.getDiaChi());
        }
    }

    private void handleSave() {
        if (validateInput()) {
            String maNCC = maNCCField.getText();
            String tenNCC = tenNCCField.getText();
            String soDienThoai = soDienThoaiField.getText();
            String diaChi = diaChiField.getText();
            
            if (tenNCC.isEmpty() || soDienThoai.isEmpty() || diaChi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            nhaCungCapDTO nhaCungCapDTO = new nhaCungCapDTO();
            nhaCungCapDTO.setMaNCC(maNCC);
            nhaCungCapDTO.setTenNCC(tenNCC);
            nhaCungCapDTO.setSDT(soDienThoai);
            nhaCungCapDTO.setDiaChi(diaChi);
            nhaCungCapDTO.setTrangThai(true);
            
            int result;
            if (isEditMode) {
                result = org.example.BUS.NhaCungCapBUS.capNhatNhaCungCap(nhaCungCapDTO);
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                result = org.example.BUS.NhaCungCapBUS.themNhaCungCap(nhaCungCapDTO);
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean validateInput() {
        if (tenNCCField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên nhà cung cấp không được để trống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sdt = soDienThoaiField.getText().trim();
        if (sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!sdt.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại chỉ được chứa các chữ số",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (diaChiField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void handleCancel() {
        dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ThemNCCDialog::new);
    }
}