package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;
import org.example.Utils.diaChiPanelAPI;

import javax.swing.*;
import java.awt.*;

public class ThemNCCDialog extends JDialog {
    private CustomTexField maNCCField, tenNCCField, soDienThoaiField, emailField;
    private diaChiPanelAPI diaChiPanel;
    private CustomButton luuButton, huyButton;

    public ThemNCCDialog() {
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
        setSize(600, 500);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(true);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        // Tiêu đề
        JLabel title = new JLabel("THÊM NHÀ CUNG CẤP", SwingConstants.CENTER);
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
        addFormRow(leftPanel, "Mã NCC", maNCCField = new CustomTexField("Mã tự động (vd: NCC001)"), 0, leftGbc);
        addFormRow(leftPanel, "Tên NCC", tenNCCField = new CustomTexField("Nhập tên nhà cung cấp"), 1, leftGbc);
        addFormRow(leftPanel, "Số điện thoại", soDienThoaiField = new CustomTexField("0123456789"), 2, leftGbc);
        addFormRow(leftPanel, "Email", emailField = new CustomTexField("example@gmail.com"), 3, leftGbc);

        // Tạo panel bên phải
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightGbc.insets = new Insets(6, 2, 6, 2);
        rightGbc.weightx = 1.0;

        // Thêm panel địa chỉ vào bên phải
        
        // Thêm panel địa chỉ
        diaChiPanel = new diaChiPanelAPI();
        rightGbc.gridx = 0;
        rightGbc.gridy = 2; 
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
        // Code xử lý lưu dữ liệu
        if (validateInput()) {
            // Thực hiện lưu dữ liệu
            JOptionPane.showMessageDialog(this, "Đã lưu thông tin nhà cung cấp!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private boolean validateInput() {
        // Kiểm tra tên nhà cung cấp
        if (tenNCCField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên nhà cung cấp không được để trống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra số điện thoại
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

        return true;
    }

    private void handleCancel() {
        // Code xử lý hủy
        dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ThemNCCDialog::new);
    }
    
}