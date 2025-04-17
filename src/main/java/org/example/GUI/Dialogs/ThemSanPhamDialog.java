package org.example.GUI.Dialogs;

import org.example.BUS.LoaiSanPhamBUS;
import org.example.BUS.SanPhamBUS;
import org.example.Components.CustomButton;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;
import org.example.DTO.LoaiSanPhamDTO;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Panels.hangHoaPanel.SanPhamPanel;

import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import static org.example.Components.CustomToastMessage.showSuccessToast;

public class ThemSanPhamDialog extends JDialog {
    private CustomTexField maHangHoaField, tenHangHoaField, donViTinhField, giaBanField;
    private JComboBox<String> loaiHangHoaComboBox, trangThaiComboBox;
    private CustomButton chonHinhAnhButton, luuButton, huyButton;
    private SanPhamPanel parentPanel;
    private boolean isEditMode = false;
    private SanPhamDTO sanPhamEdit;
    private JPanel hinhAnhPanel;
    private File selectedFile;

    public ThemSanPhamDialog(SanPhamPanel parentPanel) {
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
    
    public ThemSanPhamDialog(SanPhamPanel parentPanel, SanPhamDTO sanPham) {
        this.parentPanel = parentPanel;
        this.isEditMode = true;
        this.sanPhamEdit = sanPham;
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
        JLabel title = new JLabel(isEditMode ? "SỬA HÀNG HOÁ" : "THÊM HÀNG HOÁ", SwingConstants.CENTER);
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
        maHangHoaField.setEnabled(false);
        
        if (isEditMode) {
            // Điền thông tin sản phẩm cần sửa
            maHangHoaField.setText(sanPhamEdit.getMaSP());
            addFormRow(panel, "Tên hàng hóa", tenHangHoaField = new CustomTexField(""), 1, gbc);
            tenHangHoaField.setText(sanPhamEdit.getTenSP());

            ArrayList<LoaiSanPhamDTO> dsLoaiSP = LoaiSanPhamBUS.layDanhSachLoaiSanPham();
            String[] loaiSPNames = new String[dsLoaiSP.size()];
            for (int i = 0; i < dsLoaiSP.size(); i++) {
                loaiSPNames[i] = dsLoaiSP.get(i).getTenLSP();
            }
            loaiHangHoaComboBox = new JComboBox<>(loaiSPNames);

            // Cần thiết lập loại hàng hóa dựa trên maLSP
            addFormRow(panel, "Loại hàng hóa", loaiHangHoaComboBox, 2, gbc);
            
            addFormRow(panel, "Đơn vị tính", donViTinhField = new CustomTexField(""), 3, gbc);
            donViTinhField.setText(sanPhamEdit.getDonVi());
            
            addFormRow(panel, "Giá bán", giaBanField = new CustomTexField(""), 4, gbc);
            giaBanField.setText(String.valueOf(sanPhamEdit.getGiaBan()));


        } else {
            // Tạo mã sản phẩm tự động
            String maSanPham = SanPhamBUS.generateNextMaSP();
            maHangHoaField.setText(maSanPham);
            
            addFormRow(panel, "Tên hàng hóa", tenHangHoaField = new CustomTexField("Nhập tên (vd: Coca Cola)"), 1, gbc);

            ArrayList<LoaiSanPhamDTO> dsLoaiSP = LoaiSanPhamBUS.layDanhSachLoaiSanPham();
            String[] loaiSPNames = new String[dsLoaiSP.size()];
            for (int i = 0; i < dsLoaiSP.size(); i++) {
                loaiSPNames[i] = dsLoaiSP.get(i).getTenLSP();
            }


            loaiHangHoaComboBox = new JComboBox<>(loaiSPNames);

            addFormRow(panel, "Loại hàng hóa", loaiHangHoaComboBox, 2, gbc);
            
            addFormRow(panel, "Đơn vị tính", donViTinhField = new CustomTexField("Nhập đơn vị tính (vd: chai)"), 3, gbc);
            
            addFormRow(panel, "Giá bán", giaBanField = new CustomTexField("Nhập giá bán (vd: 15000)"), 4, gbc);
        }

        // Image panel
        hinhAnhPanel = new JPanel();
        hinhAnhPanel.setBackground(new Color(255, 255, 255));
        hinhAnhPanel.setPreferredSize(new Dimension(150, 150));
        hinhAnhPanel.setLayout(new BorderLayout());
        addFormRow(panel, "Hình ảnh", hinhAnhPanel, 6, gbc);

        chonHinhAnhButton = new CustomButton("Chọn hình ảnh");
        chonHinhAnhButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        gbc.gridy = 7;
        gbc.gridx = 1;
        panel.add(chonHinhAnhButton, gbc);

        // Hiển thị hình ảnh nếu đang ở chế độ sửa
        if (isEditMode && sanPhamEdit.getHinhAnh() != null && !sanPhamEdit.getHinhAnh().isEmpty()) {
            String imagePath = "src/main/resources/Images/Products/" + sanPhamEdit.getHinhAnh();
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                selectedFile = imageFile;
                displayImageOnPanel(imageFile);
            }
        }

        // Hiển thị hình ảnh mặc định nếu ở chế độ thêm mới
        if (!isEditMode) {
            String defaultImagePath = "src/main/resources/Images/Products/sample.png";
            File defaultImageFile = new File(defaultImagePath);
            if (defaultImageFile.exists()) {
                displayImageOnPanel(defaultImageFile);
            }
        }


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
        
        luuButton = new CustomButton(isEditMode ? "Cập nhật" : "Lưu");
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
        if (validateInput()) {
            String maSanPham = maHangHoaField.getText().trim();
            String tenSanPham = tenHangHoaField.getText().trim();
            String donViTinh = donViTinhField.getText().trim();
            String tenLSP = loaiHangHoaComboBox.getSelectedItem().toString();
            String maLSP = "";
            // Check if selectedFile is null and handle it
            String hinhAnh = selectedFile != null ? selectedFile.getName() : "sample.png";
            ArrayList<LoaiSanPhamDTO> dsLoaiSP = LoaiSanPhamBUS.layDanhSachLoaiSanPham();
            for (LoaiSanPhamDTO lsp : dsLoaiSP) {
                if (lsp.getTenLSP().equals(tenLSP)) {
                    maLSP = lsp.getMaLSP();
                    break;
                }
            }
            double giaBan = 0;
            try {
                giaBan = Double.parseDouble(giaBanField.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá bán phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int result = 0;



            if (isEditMode) {
                // Cập nhật sản phẩm

                SanPhamDTO sanPhamDTO = new SanPhamDTO(maSanPham, tenSanPham, true, 0, hinhAnh, donViTinh, maLSP, giaBan);

                result = SanPhamBUS.capNhatSanPham(sanPhamDTO);
                if (result > 0) {
                    showSuccessToast(parentPanel, "Cập nhật sản phẩm thành công");
                    if (parentPanel != null) {
                        parentPanel.refreshSanPhamTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thất bại", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }

                dispose();
            } else {
                // Thêm mới sản phẩm
                SanPhamDTO sanPhamDTO = new SanPhamDTO(maSanPham, tenSanPham, true, 0, hinhAnh, donViTinh, maLSP, giaBan);
                result = SanPhamBUS.themSanPham(sanPhamDTO);

                if (result > 0) {
                    showSuccessToast(parentPanel, "Thêm sản phẩm thành công");
                    if (parentPanel != null) {
                        parentPanel.refreshSanPhamTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }


                dispose();
            }
        }
    }

    private void handleCancel() {
        // Code xử lý hủy
        this.dispose();
    }

    private void handleChooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn hình ảnh");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Chỉ cho phép chọn file hình ảnh
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Hình ảnh", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            String selectedFilePath = selectedFile.getAbsolutePath();

            // Đường dẫn đích trong thư mục dự án
            String destinationDir = "src/main/resources/Images/Products";
            File destinationFolder = new File(destinationDir);
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs(); // Tạo thư mục nếu chưa có
            }

            File destinationFile = new File(destinationFolder, selectedFile.getName());

            try {
                // Sao chép file vào thư mục đích
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Hiển thị hình ảnh lên panel
                displayImageOnPanel(destinationFile);

                JOptionPane.showMessageDialog(this, "Đã chọn và sao chép hình ảnh: " + destinationFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi sao chép hình ảnh: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Phương thức mới để hiển thị hình ảnh lên panel
    // Phương thức mới để hiển thị hình ảnh lên panel
    private void displayImageOnPanel(File imageFile) {
        if (imageFile != null && imageFile.exists()) {
            ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());
            // Sử dụng kích thước cố định thay vì kích thước của panel
            int width = 150;
            int height = 150;
            Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            hinhAnhPanel.removeAll();
            hinhAnhPanel.add(imageLabel, BorderLayout.CENTER);
            hinhAnhPanel.revalidate();
            hinhAnhPanel.repaint();
        }
    }


    private boolean validateInput() {
        if (tenHangHoaField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên hàng hóa không được để trống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (donViTinhField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Đơn vị tính không được để trống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            double giaBan = Double.parseDouble(giaBanField.getText().trim());
            if (giaBan <= 0) {
                JOptionPane.showMessageDialog(this, "Giá bán phải lớn hơn 0",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá bán phải là số",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    // main để test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThemSanPhamDialog dialog = new ThemSanPhamDialog(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        });
    }
}

