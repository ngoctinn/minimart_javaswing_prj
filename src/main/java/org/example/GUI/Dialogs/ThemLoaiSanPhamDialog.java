package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class ThemLoaiSanPhamDialog extends JDialog {
    private CustomTexField maLoaiField, tenLoaiField;
    private CustomButton luuButton, huyButton;

    public ThemLoaiSanPhamDialog() {
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
        JLabel title = new JLabel("THÊM LOẠI SẢN PHẨM", SwingConstants.CENTER);
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
        addFormRow(panel, "Mã loại", maLoaiField = new CustomTexField("Mã tự động (vd: LSP001)"), 0, gbc);
        addFormRow(panel, "Tên loại", tenLoaiField = new CustomTexField("Nhập tên loại (vd: Đồ uống)"), 1, gbc);

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
        if (validateInput()) {
            JOptionPane.showMessageDialog(this, "Đã lưu thông tin loại sản phẩm!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
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
        SwingUtilities.invokeLater(ThemLoaiSanPhamDialog::new);
    }
}