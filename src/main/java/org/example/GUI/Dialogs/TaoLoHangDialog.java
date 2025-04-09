package org.example.GUI.Dialogs;

import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaoLoHangDialog extends JDialog {
    private JLabel batchIdLabel;
    private JComboBox<String> supplierComboBox;
    private JSpinner ngaySanXuatSpinner, hanSuDungSpinner;
    private CustomButton luuButton, huyButton;
    private boolean confirmed = false;
    private String supplier;
    private Date ngaySanXuat;
    private Date hanSuDung;

    public TaoLoHangDialog(Window owner, String batchId) {
        super(owner, "Tạo lô hàng mới", ModalityType.APPLICATION_MODAL);
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            UIManager.put("ComboBox.buttonStyle", "icon-only");
            UIManager.put("ComboBox.buttonBackground", Color.WHITE);
            UIManager.put("ComboBox.buttonArrowColor", Color.BLACK);
            initGUI(batchId);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void initGUI(String batchId) {
        setSize(450, 400);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        // Tiêu đề
        JLabel title = new JLabel("TẠO LÔ HÀNG MỚI", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0, 102, 204));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // Main panel
        RoundedPanel mainPanel = new RoundedPanel(20);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridBagLayout());

        // Create form panel with components
        JPanel formPanel = createFormPanel(batchId);
        mainPanel.add(formPanel, new GridBagConstraints());
        add(mainPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        // Add event listeners
        addEventListeners();
    }

    private JPanel createFormPanel(String batchId) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 1.0;

        // Mã lô hàng
        JLabel batchIdTitleLabel = new JLabel("Mã lô hàng:");
        batchIdTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        panel.add(batchIdTitleLabel, gbc);

        batchIdLabel = new JLabel(batchId);
        batchIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(batchIdLabel, gbc);

        // Ngày sản xuất
        JLabel ngaySanXuatLabel = new JLabel("Ngày sản xuất:");
        ngaySanXuatLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(ngaySanXuatLabel, gbc);

        // Spinner cho ngày sản xuất
        SpinnerDateModel ngaySanXuatModel = new SpinnerDateModel(new Date(), null, new Date(), Calendar.DAY_OF_MONTH);
        ngaySanXuatSpinner = new JSpinner(ngaySanXuatModel);
        ngaySanXuatSpinner.setEditor(new JSpinner.DateEditor(ngaySanXuatSpinner, "dd/MM/yyyy"));
        ngaySanXuatSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(ngaySanXuatSpinner, gbc);

        // Hạn sử dụng
        JLabel hanSuDungLabel = new JLabel("Hạn sử dụng:");
        hanSuDungLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(hanSuDungLabel, gbc);

        // Spinner cho hạn sử dụng
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 6); // Mặc định hạn sử dụng là 6 tháng sau
        SpinnerDateModel hanSuDungModel = new SpinnerDateModel(cal.getTime(), new Date(), null, Calendar.DAY_OF_MONTH);
        hanSuDungSpinner = new JSpinner(hanSuDungModel);
        hanSuDungSpinner.setEditor(new JSpinner.DateEditor(hanSuDungSpinner, "dd/MM/yyyy"));
        hanSuDungSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(hanSuDungSpinner, gbc);

        return panel;
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

    private void addEventListeners() {
        luuButton.addActionListener(e -> handleSave());
        huyButton.addActionListener(e -> handleCancel());
    }

    private void handleSave() {
        // Kiểm tra ngày sản xuất và hạn sử dụng
        Date ngaySX = (Date) ngaySanXuatSpinner.getValue();
        Date hanSD = (Date) hanSuDungSpinner.getValue();

        if (ngaySX.after(new Date())) {
            JOptionPane.showMessageDialog(this, "Ngày sản xuất không thể sau ngày hiện tại!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (hanSD.before(ngaySX)) {
            JOptionPane.showMessageDialog(this, "Hạn sử dụng không thể trước ngày sản xuất!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lưu thông tin
        this.supplier = (String) supplierComboBox.getSelectedItem();
        this.ngaySanXuat = ngaySX;
        this.hanSuDung = hanSD;
        this.confirmed = true;

        // Đóng dialog
        this.dispose();
    }

    private void handleCancel() {
        this.confirmed = false;
        this.dispose();
    }

    // Getters
    public boolean isConfirmed() {
        return confirmed;
    }

    public String getSupplier() {
        return supplier;
    }

    public Date getNgaySanXuat() {
        return ngaySanXuat;
    }

    public Date getHanSuDung() {
        return hanSuDung;
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setVisible(true);
            TaoLoHangDialog dialog = new TaoLoHangDialog(frame, "LH001");
            dialog.setVisible(true);
            frame.dispose();
        });
    }
}