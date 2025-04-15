package org.example.GUI.Dialogs;

import org.example.BUS.LoaiSanPhamBUS;
import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;
import org.example.DTO.LoaiSanPhamDTO;
import org.example.GUI.Panels.hangHoaPanel.LoaiSanPhamPanel;

import javax.swing.*;
import java.awt.*;

public class ThemLoaiSanPhamDialog extends JDialog {
    private CustomTexField maLoaiField, tenLoaiField;
    private CustomButton luuButton, huyButton;
    private LoaiSanPhamPanel parentPanel;
    private boolean isEditMode = false;
    private LoaiSanPhamDTO loaiSanPhamEdit;

    public ThemLoaiSanPhamDialog(LoaiSanPhamPanel parentPanel) {
        this.parentPanel = parentPanel;
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
    
    public ThemLoaiSanPhamDialog(LoaiSanPhamPanel parentPanel, LoaiSanPhamDTO loaiSanPham) {
        this.parentPanel = parentPanel;
        this.isEditMode = true;
        this.loaiSanPhamEdit = loaiSanPham;
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
        setSize(400, 250);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(true);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        // Tiêu đề
        JLabel title = new JLabel(isEditMode ? "SỬA LOẠI SẢN PHẨM" : "THÊM LOẠI SẢN PHẨM", SwingConstants.CENTER);
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

        pack();
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
        addFormRow(panel, "Mã loại", maLoaiField = new CustomTexField(""), 0, gbc);
        maLoaiField.setEnabled(false);
        
        if (isEditMode) {
            maLoaiField.setText(loaiSanPhamEdit.getMaLSP());
            addFormRow(panel, "Tên loại", tenLoaiField = new CustomTexField("Nhập tên loại (vd: Đồ uống)"), 1, gbc);
            tenLoaiField.setText(loaiSanPhamEdit.getTenLSP());
        } else {
            String maLoai = LoaiSanPhamBUS.generateNextMaLSP();
            maLoaiField.setText(maLoai);
            addFormRow(panel, "Tên loại", tenLoaiField = new CustomTexField("Nhập tên loại (vd: Đồ uống)"), 1, gbc);
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

    private void handleSave() {
        if (validateInput()) {
            String maLoai = maLoaiField.getText().trim();
            String tenLoai = tenLoaiField.getText().trim();
            int result;
            
            if (isEditMode) {
                // Cập nhật loại sản phẩm
                 LoaiSanPhamDTO loaiSanPhamDTO = new LoaiSanPhamDTO(maLoai, tenLoai, loaiSanPhamEdit.getTrangThai());
                result = LoaiSanPhamBUS.capNhatLoaiSanPham(loaiSanPhamDTO);
                
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật loại sản phẩm thành công", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    if (parentPanel != null) {
                        parentPanel.refreshLoaiSanPhamTable();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật loại sản phẩm thất bại", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Thêm mới loại sản phẩm
                LoaiSanPhamDTO loaiSanPhamDTO = new LoaiSanPhamDTO(maLoai, tenLoai, true);
                result = LoaiSanPhamBUS.themLoaiSanPham(loaiSanPhamDTO);
                
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm loại sản phẩm thành công", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    if (parentPanel != null) {
                        parentPanel.refreshLoaiSanPhamTable();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm loại sản phẩm thất bại", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean validateInput() {
        if (tenLoaiField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên loại sản phẩm không được để trống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void handleCancel() {
        dispose();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ThemLoaiSanPhamDialog(null));
    }
}