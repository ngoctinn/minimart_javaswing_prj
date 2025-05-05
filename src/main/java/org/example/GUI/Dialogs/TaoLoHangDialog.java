package org.example.GUI.Dialogs;

import org.example.BUS.LoHangBUS;
import org.example.Components.CustomButton;
import org.example.Components.CustomDatePicker;
import org.example.Components.CustomTable;
import org.example.Components.CustomTextField;
import org.example.Components.RoundedPanel;
import org.example.DTO.SanPhamDTO;
import org.example.DTO.loHangDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Dialog tạo lô hàng mới
 * Cho phép người dùng tạo lô hàng mới với các thông tin: mã lô hàng, mã sản phẩm, ngày sản xuất, ngày hết hạn, số lượng
 * Mã lô hàng và mã sản phẩm được lấy tự động từ panel khác
 */
public class TaoLoHangDialog extends JDialog {
    // UI Components
    private CustomTextField maLoHangField;
    private CustomTextField tenSanPhamField;
    private CustomDatePicker ngaySanXuatPicker;
    private CustomDatePicker ngayHetHanPicker;
    private CustomButton luuButton;
    private CustomButton huyButton;
    private CustomButton themVaoBangButton;
    private CustomButton hoanTatButton;

    // Thành phần bảng lô hàng
    private CustomTable loHangTable;
    private DefaultTableModel loHangTableModel;
    private List<loHangDTO> danhSachLoHang = new ArrayList<>();

    // Data
    private boolean confirmed = false;
    private String maLoHang;
    private String maSanPham;
    private SanPhamDTO sanPhamInfo;

    /**
     * Constructor với thông tin sản phẩm
     * @param owner Cửa sổ cha
     * @param sanPhamInfo Thông tin sản phẩm
     */
    public TaoLoHangDialog(Window owner, SanPhamDTO sanPhamInfo) {
        super(owner, "Tạo lô hàng mới", ModalityType.APPLICATION_MODAL);
        this.maLoHang = LoHangBUS.taoMaLoHangMoi(); // Tạo mã lô hàng mới tự động
        this.maSanPham = sanPhamInfo.getMaSP();
        this.sanPhamInfo = sanPhamInfo;

        // Load danh sách lô hàng hiện có của sản phẩm này
        loadExistingBatches();

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

    /**
     * Khởi tạo giao diện
     */
    private void initGUI() {
        setSize(900, 700); // Tăng kích thước dialog
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // Tiêu đề
        JLabel titleLabel = new JLabel("TẠO LÔ HÀNG MỚI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form panel
        RoundedPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Table panel
        RoundedPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Thêm sự kiện
        addEventListeners();

        // Hiển thị dialog
        setVisible(true);
    }

    /**
     * Tạo panel chứa form nhập liệu
     * @return RoundedPanel panel chứa form
     */
    private RoundedPanel createFormPanel() {
        RoundedPanel panel = new RoundedPanel(15);
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tiêu đề form
        JLabel formTitle = new JLabel("Thông tin lô hàng");
        formTitle.setFont(new Font("Arial", Font.BOLD, 16));
        formTitle.setForeground(new Color(0, 102, 204));
        panel.add(formTitle, BorderLayout.NORTH);

        // Panel chứa các trường nhập liệu (2 cột)
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.weightx = 0.5; // Đặt weightx = 0.5 để các cột có chiều rộng bằng nhau

        // Cột 1 - Mã lô hàng (không cho phép chỉnh sửa)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        fieldsPanel.add(new JLabel("Mã lô hàng:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        // Sử dụng TextField thay vì Label
        maLoHangField = new CustomTextField(maLoHang);
        maLoHangField.setState(CustomTextField.State.DISABLED);
        String maLoHang = LoHangBUS.taoMaLoHangMoi();
        maLoHangField.setText(maLoHang);
        fieldsPanel.add(maLoHangField.getContainer(), gbc);

        // Cột 1 - Ngày sản xuất
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        fieldsPanel.add(new JLabel("Ngày sản xuất:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        ngaySanXuatPicker = new CustomDatePicker();
        ngaySanXuatPicker.setDate(LocalDate.now());
        fieldsPanel.add(ngaySanXuatPicker, gbc);

        // Cột 2 - Tên sản phẩm (không cho phép chỉnh sửa)
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        fieldsPanel.add(new JLabel("Tên sản phẩm:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        // Sử dụng TextField thay vì Label
        tenSanPhamField = new CustomTextField();
        tenSanPhamField.setText(sanPhamInfo.getTenSP());
        tenSanPhamField.setState(CustomTextField.State.DISABLED);
        fieldsPanel.add(tenSanPhamField.getContainer(), gbc);

        // Cột 2 - Ngày hết hạn
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        fieldsPanel.add(new JLabel("Ngày hết hạn:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        ngayHetHanPicker = new CustomDatePicker();
        ngayHetHanPicker.setDate(LocalDate.now().plusMonths(6)); // Mặc định 6 tháng
        fieldsPanel.add(ngayHetHanPicker, gbc);



        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        themVaoBangButton = new CustomButton("Thêm vào bảng");
        themVaoBangButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        themVaoBangButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(themVaoBangButton);

        // Thêm các panel vào form panel
        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Tạo panel chứa bảng lô hàng
     * @return RoundedPanel panel chứa bảng
     */
    private RoundedPanel createTablePanel() {
        RoundedPanel tableRoundedPanel = new RoundedPanel(15);
        tableRoundedPanel.setBackground(Color.WHITE);
        tableRoundedPanel.setLayout(new BorderLayout(10, 10));
        tableRoundedPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tiêu đề bảng
        JLabel tableTitle = new JLabel("Danh sách lô hàng");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 16));
        tableTitle.setForeground(new Color(0, 102, 204));
        tableRoundedPanel.add(tableTitle, BorderLayout.NORTH);

        // Tạo model cho bảng
        String[] columnNames = {"Mã lô hàng", "Mã sản phẩm", "Ngày sản xuất", "Ngày hết hạn", "Số lượng", "Trạng thái"};
        loHangTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Định dạng các cột
                switch (columnIndex) {
                    case 2: // Ngày sản xuất
                    case 3: // Ngày hết hạn
                        return LocalDate.class;
                    case 4: // Số lượng
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };

        // Tạo bảng với chiều cao lớn hơn
        loHangTable = new CustomTable(loHangTableModel);
        loHangTable.setRowHeight(35); // Tăng chiều cao hàng
        JScrollPane scrollPane = new JScrollPane(loHangTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(800, 350)); // Tăng chiều cao bảng
        tableRoundedPanel.add(scrollPane, BorderLayout.CENTER);

        // Tạo panel chứa các nút
        JPanel tableButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tableButtonPanel.setBackground(Color.WHITE);

        huyButton = new CustomButton("Hủy");
        huyButton.setButtonColors(CustomButton.ButtonColors.RED);
        huyButton.setPreferredSize(new Dimension(100, 40));
        tableButtonPanel.add(huyButton);

        hoanTatButton = new CustomButton("Đóng");
        hoanTatButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        hoanTatButton.setPreferredSize(new Dimension(120, 40));
        tableButtonPanel.add(hoanTatButton);

        tableRoundedPanel.add(tableButtonPanel, BorderLayout.SOUTH);

        return tableRoundedPanel;
    }

    /**
     * Thêm các sự kiện cho các nút
     */
    private void addEventListeners() {
        huyButton.addActionListener(e -> handleCancel());
        themVaoBangButton.addActionListener(e -> handleAddToBatch());
        hoanTatButton.addActionListener(e -> handleComplete());
    }


    /**
     * Xử lý sự kiện khi nhấn nút Hủy
     */
    private void handleCancel() {
        this.confirmed = false;
        this.dispose();
    }

    /**
     * Xử lý sự kiện khi nhấn nút Hoàn tất
     * Đóng form mà không thực hiện thêm vào CSDL (đã thêm khi nhấn nút Thêm vào bảng)
     */
    private void handleComplete() {
        // Kiểm tra xem đã có lô hàng nào chưa
        if (danhSachLoHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có lô hàng nào được tạo!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Đóng dialog
        this.confirmed = true;
        this.dispose();
    }

    /**
     * Xử lý sự kiện khi nhấn nút Thêm vào bảng
     * Thêm lô hàng vào bảng và lưu vào CSDL luôn
     */
    private void handleAddToBatch() {
        // Kiểm tra dữ liệu nhập vào
        if (!validateInput()) {
            return;
        }

        // Lấy dữ liệu từ form
        LocalDate ngaySanXuat = ngaySanXuatPicker.getDate();
        LocalDate ngayHetHan = ngayHetHanPicker.getDate();
        int soLuong = 0; // Số lượng sẽ được cập nhật sau khi nhập hàng

        // Tạo đối tượng loHangDTO
        String trangThai = "Đang hoạt động"; // Trạng thái mặc định
        loHangDTO loHang = new loHangDTO();
        loHang.setMaLoHang(maLoHang);
        loHang.setMaSP(maSanPham);
        loHang.setNgaySanXuat(ngaySanXuat);
        loHang.setNgayHetHan(ngayHetHan);
        loHang.setSoLuong(soLuong);
        loHang.setTrangThai(trangThai);

        // Kiểm tra xem mã lô hàng đã tồn tại trong danh sách chưa
        if (isBatchCodeExists(maLoHang)) {
            JOptionPane.showMessageDialog(this, "Mã lô hàng đã tồn tại trong danh sách!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            // Tạo mã lô hàng mới
            maLoHang = LoHangBUS.taoMaLoHangMoi();
            maLoHangField.setText(maLoHang);
            return;
        }

        // Kiểm tra dữ liệu hợp lệ trước khi thêm vào CSDL
        String errorMessage = LoHangBUS.kiemTraDuLieuLoHang(loHang);
        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + errorMessage,
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Thêm lô hàng vào CSDL
        int result = LoHangBUS.themLoHang(loHang);
        if (result <= 0) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm lô hàng: " + loHang.getMaLoHang(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Thêm vào danh sách hiển thị
        danhSachLoHang.add(loHang);

        // Cập nhật bảng
        updateLoHangTable();

        // Reset form và tạo mã lô hàng mới
        resetForm();
        generateNewBatchCode();

        // Hiển thị thông báo thành công
        JOptionPane.showMessageDialog(this, "Đã thêm lô hàng vào cơ sở dữ liệu thành công!",
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Kiểm tra dữ liệu nhập vào
     * @return boolean true nếu dữ liệu hợp lệ, false nếu không
     */
    private boolean validateInput() {
        // Kiểm tra ngày sản xuất và ngày hết hạn
        LocalDate ngaySanXuat = ngaySanXuatPicker.getDate();
        LocalDate ngayHetHan = ngayHetHanPicker.getDate();

        if (ngaySanXuat == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sản xuất!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (ngayHetHan == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày hết hạn!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (ngayHetHan.isBefore(ngaySanXuat) || ngayHetHan.isEqual(ngaySanXuat)) {
            JOptionPane.showMessageDialog(this, "Ngày hết hạn phải sau ngày sản xuất!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Cập nhật bảng lô hàng
     */
    private void updateLoHangTable() {
        // Xóa tất cả dữ liệu cũ
        loHangTableModel.setRowCount(0);

        // Thêm dữ liệu mới
        for (loHangDTO loHang : danhSachLoHang) {
            Object[] row = {
                    loHang.getMaLoHang(),
                    loHang.getMaSP(),
                    loHang.getNgaySanXuat().toString(),
                    loHang.getNgayHetHan().toString(),
                    loHang.getSoLuong(),
                    loHang.getTrangThai()
            };
            loHangTableModel.addRow(row);
        }
    }

    /**
     * Reset form về trạng thái ban đầu
     */
    private void resetForm() {
        ngaySanXuatPicker.setDate(LocalDate.now());
        ngayHetHanPicker.setDate(LocalDate.now().plusMonths(6));
    }

    /**
     * Tạo mã lô hàng mới
     */
    private void generateNewBatchCode() {
        maLoHang = LoHangBUS.taoMaLoHangMoi();
        maLoHangField.setText(maLoHang);
    }

    /**
     * Load danh sách lô hàng hiện có của sản phẩm
     */
    private void loadExistingBatches() {
        try {
            // Lấy danh sách lô hàng theo mã sản phẩm
            ArrayList<loHangDTO> existingBatches = LoHangBUS.layDanhSachLoHangTheoMaSP(maSanPham);

            // Thêm vào danh sách hiển thị
            if (existingBatches != null && !existingBatches.isEmpty()) {
                danhSachLoHang.addAll(existingBatches);

                // Cập nhật bảng
                SwingUtilities.invokeLater(this::updateLoHangTable);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu lô hàng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Kiểm tra xem mã lô hàng đã tồn tại trong danh sách chưa
     * @param maLoHang Mã lô hàng cần kiểm tra
     * @return true nếu đã tồn tại, false nếu chưa tồn tại
     */
    private boolean isBatchCodeExists(String maLoHang) {
        for (loHangDTO loHang : danhSachLoHang) {
            if (loHang.getMaLoHang().equals(maLoHang)) {
                return true;
            }
        }
        return false;
    }

    // Getters
    public boolean isConfirmed() {
        return confirmed;
    }

    public List<loHangDTO> getDanhSachLoHang() {
        return danhSachLoHang;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Tạo một đối tượng SanPhamDTO mẫu
            SanPhamDTO sanPhamInfo = new SanPhamDTO("SP001", "Sản phẩm mẫu", true, 0, "", "", "", 0.0);
            new TaoLoHangDialog(null, sanPhamInfo);
        });
    }
}