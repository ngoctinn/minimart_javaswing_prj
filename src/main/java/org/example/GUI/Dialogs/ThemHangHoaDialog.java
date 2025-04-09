package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class ThemHangHoaDialog extends JDialog {
    private CustomTexField maHangHoaField, tenHangHoaField, donViTinhField, giaBanField;
    private JComboBox<String> loaiHangHoaComboBox, trangThaiComboBox;
    private CustomButton chonHinhAnhButton, luuButton, huyButton;

    public ThemHangHoaDialog() {
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
        setSize(400, 550);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(true);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        // Tiêu đề
        JLabel title = new JLabel("THÊM HÀNG HOÁ", SwingConstants.CENTER);
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
        addFormRow(panel, "Mã hàng hóa", maHangHoaField = new CustomTexField("Mã tự động (vd: HH001)"), 0, gbc);
        addFormRow(panel, "Tên hàng hóa", tenHangHoaField = new CustomTexField("Nhập tên (vd: Coca Cola)"), 1, gbc);
        
        loaiHangHoaComboBox = new JComboBox<>(new String[]{"Thức ăn", "Đồ uống", "Hàng hóa khác"});
        addFormRow(panel, "Loại hàng hóa", loaiHangHoaComboBox, 2, gbc);
        
        addFormRow(panel, "Đơn vị tính", donViTinhField = new CustomTexField("Nhập đơn vị tính (vd: chai)"), 3, gbc);
        
        addFormRow(panel, "Giá bán", giaBanField = new CustomTexField("Nhập giá bán (vd: 15000)"), 4, gbc);
        
        trangThaiComboBox = new JComboBox<>(new String[]{"Đang kinh doanh", "Ngừng kinh doanh"});
        addFormRow(panel, "Trạng thái", trangThaiComboBox, 5, gbc);

        // Image panel
        JPanel hinhAnhPanel = new JPanel();
        hinhAnhPanel.setBackground(new Color(225, 225, 225));
        hinhAnhPanel.setPreferredSize(new Dimension(200, 100));
        addFormRow(panel, "Hình ảnh", hinhAnhPanel, 6, gbc);

        chonHinhAnhButton = new CustomButton("Chọn hình ảnh");
        chonHinhAnhButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        gbc.gridy = 7;
        gbc.gridx = 1;
        panel.add(chonHinhAnhButton, gbc);

        return panel;
    }

    private void addFormRow(JPanel panel, String labelText, Component component, int row, GridBagConstraints gbc) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        panel.add(createLabel(labelText), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
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
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        return label;
    }

    private void addEventListeners() {
        luuButton.addActionListener(e -> handleSave());
        huyButton.addActionListener(e -> handleCancel());
        chonHinhAnhButton.addActionListener(e -> handleChooseImage());
    }

    private void handleSave() {
        // Code xử lý lưu dữ liệu
    }

    private void handleCancel() {
        // Code xử lý hủy
        this.dispose();
    }

    private void handleChooseImage() {
        // Code xử lý chọn hình ảnh
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ThemHangHoaDialog::new);
    }
}

