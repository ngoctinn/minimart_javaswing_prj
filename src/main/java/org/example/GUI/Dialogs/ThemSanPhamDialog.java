package org.example.GUI.Dialogs;

import org.example.BUS.LoaiSanPhamBUS;
import org.example.BUS.SanPhamBUS;
import org.example.Components.CustomButton;
// Thêm import CustomCombobox
import org.example.Components.CustomCombobox;
// Sửa import từ CustomTexField thành CustomTextField
import org.example.Components.CustomTextField;
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
    // Sửa kiểu dữ liệu từ CustomTexField thành CustomTextField
    private CustomTextField maHangHoaField, tenHangHoaField, donViTinhField, giaBanField;
    // Thay đổi kiểu dữ liệu của loaiHangHoaComboBox
    private CustomCombobox<String> loaiHangHoaComboBox;
    private CustomButton chonHinhAnhButton, luuButton, huyButton, themLoaiButton;
    private SanPhamPanel parentPanel;
    private boolean isEditMode = false;
    private SanPhamDTO sanPhamEdit;
    private JPanel hinhAnhPanel;
    private File selectedFile;

    public ThemSanPhamDialog(SanPhamPanel parentPanel) {
        this.parentPanel = parentPanel;
        this.isEditMode = false;
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            // Xóa các dòng UIManager.put không cần thiết cho CustomCombobox
            // UIManager.put("ComboBox.buttonStyle", "icon-only");
            // UIManager.put("ComboBox.buttonBackground", Color.WHITE);
            // UIManager.put("ComboBox.buttonArrowColor", Color.BLACK);
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
            // Xóa các dòng UIManager.put không cần thiết cho CustomCombobox
            // UIManager.put("ComboBox.buttonStyle", "icon-only");
            // UIManager.put("ComboBox.buttonBackground", Color.WHITE);
            // UIManager.put("ComboBox.buttonArrowColor", Color.BLACK);
            initGUI();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void initGUI() {
        setSize(400, 620);
        getContentPane().setBackground(new Color(250, 250, 250));
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
        // Sửa khởi tạo từ CustomTexField thành CustomTextField
        addFormRow(panel, "Mã hàng hóa", maHangHoaField = new CustomTextField("Mã tự động (vd: HH001)"), 0, gbc);
        maHangHoaField.setEnabled(false);

        if (isEditMode) {
            // Điền thông tin sản phẩm cần sửa
            maHangHoaField.setText(sanPhamEdit.getMaSP());
            // Sửa khởi tạo từ CustomTexField thành CustomTextField
            addFormRow(panel, "Tên hàng hóa", tenHangHoaField = new CustomTextField(""), 1, gbc);
            tenHangHoaField.setText(sanPhamEdit.getTenSP());

            ArrayList<LoaiSanPhamDTO> dsLoaiSP = LoaiSanPhamBUS.layDanhSachLoaiSanPham();
            String[] loaiSPNames = new String[dsLoaiSP.size()];
            for (int i = 0; i < dsLoaiSP.size(); i++) {
                loaiSPNames[i] = dsLoaiSP.get(i).getTenLSP();
            }
            // Thay đổi khởi tạo JComboBox thành CustomCombobox
            loaiHangHoaComboBox = new CustomCombobox<>(loaiSPNames);
            loaiHangHoaComboBox.setPlaceholder("- Chọn loại hàng hóa -"); // Thêm placeholder

            // Cần thiết lập loại hàng hóa dựa trên maLSP
            // Tìm và chọn đúng loại sản phẩm cho sản phẩm đang sửa
            String maLSPCanChon = sanPhamEdit.getMaLSP();
            for (LoaiSanPhamDTO lsp : dsLoaiSP) {
                if (lsp.getMaLSP().equals(maLSPCanChon)) {
                    loaiHangHoaComboBox.setSelectedItem(lsp.getTenLSP());
                    break;
                }
            }

            // Tạo panel chứa combobox và nút thêm mới
            JPanel loaiHangHoaPanel = new JPanel(new BorderLayout(5, 0));
            loaiHangHoaPanel.setBackground(Color.WHITE);
            loaiHangHoaPanel.add(loaiHangHoaComboBox, BorderLayout.CENTER);
            
            // Tạo nút thêm mới loại sản phẩm
            themLoaiButton = new CustomButton("+");
            themLoaiButton.setButtonColors(CustomButton.ButtonColors.BLUE);
            themLoaiButton.setPreferredSize(new Dimension(40, 32));
            themLoaiButton.setToolTipText("Thêm mới loại sản phẩm");
            loaiHangHoaPanel.add(themLoaiButton, BorderLayout.EAST);
            
            addFormRow(panel, "Loại hàng hóa", loaiHangHoaPanel, 2, gbc);
            
            // Sửa khởi tạo từ CustomTexField thành CustomTextField
            addFormRow(panel, "Đơn vị tính", donViTinhField = new CustomTextField(""), 3, gbc);
            donViTinhField.setText(sanPhamEdit.getDonVi());

            // Sửa khởi tạo từ CustomTexField thành CustomTextField
            addFormRow(panel, "Giá bán", giaBanField = new CustomTextField(""), 4, gbc);
            giaBanField.setText(String.valueOf(sanPhamEdit.getGiaBan()));


        } else {
            // Tạo mã sản phẩm tự động
            String maSanPham = SanPhamBUS.generateNextMaSP();
            maHangHoaField.setText(maSanPham);
            maHangHoaField.setState(CustomTextField.State.DISABLED);

            // Sửa khởi tạo từ CustomTexField thành CustomTextField
            tenHangHoaField = new CustomTextField("Nhập tên hàng hóa (vd: Coca Cola)");
            addFormRow(panel, "Tên hàng hóa", tenHangHoaField.getContainer(), 1, gbc);

            ArrayList<LoaiSanPhamDTO> dsLoaiSP = LoaiSanPhamBUS.layDanhSachLoaiSanPham();
            String[] loaiSPNames = new String[dsLoaiSP.size()];
            for (int i = 0; i < dsLoaiSP.size(); i++) {
                loaiSPNames[i] = dsLoaiSP.get(i).getTenLSP();
            }

            // Thay đổi khởi tạo JComboBox thành CustomCombobox
            loaiHangHoaComboBox = new CustomCombobox<>(loaiSPNames);
            loaiHangHoaComboBox.setPlaceholder("- Chọn loại hàng hóa -"); // Thêm placeholder

            // Tạo panel chứa combobox và nút thêm mới
            JPanel loaiHangHoaPanel = new JPanel(new BorderLayout(5, 0));
            loaiHangHoaPanel.setBackground(Color.WHITE);
            loaiHangHoaPanel.add(loaiHangHoaComboBox, BorderLayout.CENTER);
            
            // Tạo nút thêm mới loại sản phẩm
            themLoaiButton = new CustomButton("+");
            themLoaiButton.setButtonColors(CustomButton.ButtonColors.BLUE);
            themLoaiButton.setPreferredSize(new Dimension(40, 32));
            themLoaiButton.setToolTipText("Thêm mới loại sản phẩm");
            loaiHangHoaPanel.add(themLoaiButton, BorderLayout.EAST);
            
            addFormRow(panel, "Loại hàng hóa", loaiHangHoaPanel, 2, gbc);
            
            // Sửa khởi tạo từ CustomTexField thành CustomTextField
            donViTinhField = new CustomTextField("Nhập đơn vị tính (vd: chai)");
            addFormRow(panel, "Đơn vị tính", donViTinhField.getContainer(), 3, gbc);

            // Sửa khởi tạo từ CustomTexField thành CustomTextField
            giaBanField = new CustomTextField("Nhập giá bán (vd: 10000)");
            addFormRow(panel, "Giá bán", giaBanField.getContainer(), 4, gbc);

        }

        // Image panel
        hinhAnhPanel = new JPanel();
        hinhAnhPanel.setBackground(new Color(255, 255, 255));
        hinhAnhPanel.setPreferredSize(new Dimension(120, 120));
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
        themLoaiButton.addActionListener(e -> handleThemLoaiSanPham());
    }
    
    private void handleThemLoaiSanPham() {
        // Mở dialog thêm mới loại sản phẩm
        ThemLoaiSanPhamDialog themLoaiDialog = new ThemLoaiSanPhamDialog();
        themLoaiDialog.setLocationRelativeTo(this);
        // Sau khi thêm loại sản phẩm mới, cập nhật lại danh sách loại sản phẩm trong combobox
        refreshLoaiSanPhamComboBox();
    }

    // Phương thức để cập nhật lại danh sách loại sản phẩm trong combobox
    public void refreshLoaiSanPhamComboBox() {
        ArrayList<LoaiSanPhamDTO> dsLoaiSP = LoaiSanPhamBUS.layDanhSachLoaiSanPham();
        String[] loaiSPNames = new String[dsLoaiSP.size()];
        for (int i = 0; i < dsLoaiSP.size(); i++) {
            loaiSPNames[i] = dsLoaiSP.get(i).getTenLSP();
        }
        loaiHangHoaComboBox.setModel(new DefaultComboBoxModel<>(loaiSPNames));
        // Chọn loại sản phẩm mới thêm (thường là phần tử cuối cùng)
        if (loaiSPNames.length > 0) {
            loaiHangHoaComboBox.setSelectedIndex(loaiSPNames.length - 1);
        }
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
        boolean isValid = true;

        // Kiểm tra tên hàng hóa
        if (tenHangHoaField.getText().trim().isEmpty()) {
            tenHangHoaField.setState(CustomTextField.State.INVALID);
            tenHangHoaField.setErrorMessage("Tên sản phẩm không được để trống");
            tenHangHoaField.setPreferredSize(new Dimension(220, 32));
            isValid = false;
        }

        // Kiểm tra đơn vị tính
        if (donViTinhField.getText().trim().isEmpty()) {
            donViTinhField.setState(CustomTextField.State.INVALID);
            donViTinhField.setErrorMessage("Đơn vị tính không được để trống");
            isValid = false;
        }

        // Kiểm tra giá bán
        try {
            double giaBan = Double.parseDouble(giaBanField.getText().trim());
            if (giaBan <= 0) {
                giaBanField.setState(CustomTextField.State.INVALID);
                giaBanField.setErrorMessage("Giá bán phải lớn hơn 0");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            giaBanField.setState(CustomTextField.State.INVALID);
            giaBanField.setErrorMessage("Giá bán phải là một số hợp lệ");
            isValid = false;
        }

        // Kiểm tra loại hàng hóa (vẫn dùng JOptionPane vì CustomCombobox chưa có trạng thái lỗi)
        if (loaiHangHoaComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại hàng hóa",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            // Có thể thêm hiệu ứng viền đỏ cho combobox nếu muốn
            // loaiHangHoaComboBox.setBorder(BorderFactory.createLineBorder(Color.RED));
            isValid = false;
        } else {
            // Reset viền nếu trước đó bị lỗi (nếu có áp dụng viền đỏ)
            // loaiHangHoaComboBox.setBorder(UIManager.getBorder("ComboBox.border"));
        }

        return isValid;
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
