package org.example.GUI.Panels.nhanVienPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.ChucVuBUS;
import org.example.BUS.NhanVienBUS;
import org.example.Components.*;
import org.example.DTO.ChucVuDTO;
import org.example.DTO.nhanVienDTO;
import org.example.GUI.Dialogs.ThemNhanVienDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class NhanVienPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;

    // Top panel components
    private CustomTextField searchField;
    private CustomButton searchButton;
    private CustomButton refreshButton;
    private CustomButton addButton;
    private CustomButton editButton;
    private CustomButton deleteButton;
    private CustomButton importButton;
    private CustomButton exportButton;

    private JPanel mainButtonsPanel;
    private JPanel importExportPanel;

    // Bottom panel components
    private CustomCombobox<String> chucVuComboBox;
    private CustomTable nhanVienTable;

    public NhanVienPanel() {
        initGUI();
    }

    public void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanelLeft();
        setupBottomPanelRight();
        setupEventHandlers();

        // Add panels to main panel with proper constraints
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);

        // Load dữ liệu nhân viên khi khởi tạo panel
        handleRefreshButton();
    }

    //====================== XỬ LÝ SỰ KIỆN=================================
    // =====================PHƯƠNG THỨC ẨN HÀNH ĐỘNG=========================
    public void hideActionPanel() {
        mainButtonsPanel.setVisible(false);
        importExportPanel.setVisible(false);
    }
    private void setupEventHandlers() {
        // Thiết lập tất cả các sự kiện ở đây
        addButton.addActionListener(e -> handleAddButton());
        editButton.addActionListener(e -> handleEditButton());
        deleteButton.addActionListener(e -> handleDeleteButton());
        refreshButton.addActionListener(e -> handleRefreshButton());
        searchButton.addActionListener(e -> handleSearchButton());
        importButton.addActionListener(e -> handleImportButton());
        exportButton.addActionListener(e -> handleExportButton());
        searchField.addActionListener(e -> handleSearchButton());

        // Thiết lập sự kiện cho danh sách chức vụ
        chucVuComboBox.addItemListener(e -> handleChucVuSelection());
    }

    // CÁC PHƯƠNG THỨC XỬ LÝ SỰ KIỆN
    private void handleAddButton() {
        // Xử lý thêm nhân viên
        ThemNhanVienDialog dialog = new ThemNhanVienDialog();
        if (dialog.isClosed()) {
            // Nếu dialog đã được đóng, cập nhật lại dữ liệu
            CustomToastMessage.showSuccessToast(this,"Thêm nhân viên thành công!");
            handleRefreshButton();
        }else {
            CustomToastMessage.showErrorToast(this,"Thêm nhân viên thất bại!");
        }
    }

    private void handleEditButton() {
        // Xử lý sửa nhân viên
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy mã nhân viên từ dòng được chọn
        String maNV = nhanVienTable.getValueAt(selectedRow, 0).toString();

        // Lấy thông tin nhân viên từ mã
        nhanVienDTO nhanVien = NhanVienBUS.layNhanVienTheoMa(maNV);

        if (nhanVien != null) {
            // Mở dialog sửa nhân viên
            ThemNhanVienDialog dialog = new ThemNhanVienDialog(nhanVien);
            if (dialog.isClosed()) {
                // Nếu dialog đã được đóng, cập nhật lại dữ liệu
                handleRefreshButton();
                CustomToastMessage.showSuccessToast(this,"Cập nhật nhân viên thành công!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteButton() {
        // Xử lý xóa nhân viên
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy mã nhân viên từ dòng được chọn
        String maNV = nhanVienTable.getValueAt(selectedRow, 0).toString();

        // Xác nhận xóa
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhân viên này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Lấy thông tin nhân viên từ mã
            nhanVienDTO nhanVien = NhanVienBUS.layNhanVienTheoMa(maNV);

            if (nhanVien != null) {
                // Xóa nhân viên (cập nhật trạng thái)
                int result = NhanVienBUS.xoaNhanVien(nhanVien);

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    // Cập nhật lại dữ liệu
                    handleRefreshButton();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleRefreshButton() {
        // Xử lý làm mới bảng nhân viên
        DefaultTableModel model = (DefaultTableModel) nhanVienTable.getModel();
        ArrayList<nhanVienDTO> danhSachNhanVien = NhanVienBUS.layDanhSachNhanVien();
        loadNhanVienData(model, danhSachNhanVien);

        // Reset lại trường tìm kiếm
        searchField.setText("");
        searchField.setPlaceholder("Nhập tên nhân viên cần tìm");

        // Reset lại combobox chức vụ
        chucVuComboBox.setSelectedIndex(0);
    }

    private void handleSearchButton() {
        // Xử lý tìm kiếm nhân viên
        String tuKhoa = searchField.getText().trim();
        if (tuKhoa.isEmpty() || tuKhoa.equals("Nhập tên nhân viên cần tìm")) {
            // Nếu không có từ khóa, hiển thị tất cả nhân viên
            handleRefreshButton();
            return;
        }

        // Tìm kiếm nhân viên theo từ khóa
        DefaultTableModel model = (DefaultTableModel) nhanVienTable.getModel();
        ArrayList<nhanVienDTO> ketQuaTimKiem = NhanVienBUS.timKiemNhanVien(tuKhoa);
        loadNhanVienData(model, ketQuaTimKiem);
    }

    private void handleImportButton() {
        // Xử lý nhập nhân viên từ file
    }

    private void handleExportButton() {
        // Xử lý xuất nhân viên ra file
    }

    private void handleChucVuSelection() {
        // Xử lý khi chọn chức vụ
        String tenCV = (String) chucVuComboBox.getSelectedItem();
        if (tenCV == null || tenCV.equals("- Chọn chức vụ -")) {
            // Nếu không chọn chức vụ cụ thể, hiển thị tất cả nhân viên
            handleRefreshButton();
            return;
        }

        // Tìm mã chức vụ từ tên chức vụ
        String maCV = "";
        for (ChucVuDTO cv : ChucVuBUS.layDanhSachChucVu()) {
            if (cv.getTenCV().equals(tenCV)) {
                maCV = cv.getMaCV();
                break;
            }
        }

        if (!maCV.isEmpty()) {
            // Tìm kiếm nhân viên theo chức vụ
            DefaultTableModel model = (DefaultTableModel) nhanVienTable.getModel();
            ArrayList<nhanVienDTO> ketQuaTimKiem = NhanVienBUS.timKiemTheoChucVu(maCV);
            loadNhanVienData(model, ketQuaTimKiem);
        }
    }

    //======================CÀI ĐẶT PANEL CHÍNH=================================
    private void setupMainPanel() {
        // Set up layout and background for main panel
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(new Color(225, 225, 225));
        this.setVisible(true);
    }

    //======================CÀI ĐẶT CÁC PANEL CON=================================
    private void createPanels() {
        // Create sub-panels
        topPanel = new RoundedPanel(20);
        bottomPanel = new RoundedPanel(20);
        bottomPanelLeft = new RoundedPanel(20);
        bottomPanelRight = new RoundedPanel(20);

        // Set background colors
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(new Color(225, 225, 225));
        bottomPanelLeft.setBackground(Color.WHITE);
        bottomPanelRight.setBackground(Color.WHITE);

        // Set panel sizes
        bottomPanelLeft.setPreferredSize(new Dimension(250, 0)); // Fixed width for left panel

        // Set layouts
        topPanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new FlowLayout());
        bottomPanelRight.setLayout(new BoxLayout(bottomPanelRight, BoxLayout.Y_AXIS));
    }

    private void setupTopPanel() {
        // Add padding to the top panel
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create sub-panels for organizing components
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setBackground(Color.WHITE);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(Color.WHITE);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
        actionPanel.setBackground(Color.WHITE);

        // Title label
        JLabel title = new JLabel("Nhân Viên");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));

        // Search field
        searchField = new CustomTextField("Nhập tên nhân viên cần tìm");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));

        // Search button
        searchButton = new CustomButton("Tìm");
        searchButton.setPreferredSize(new Dimension(70, 30));
        searchButton.setMaximumSize(new Dimension(70, 30));
        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(5));

        // Refresh button
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setPreferredSize(new Dimension(50, 30));
        refreshButton.setMaximumSize(new Dimension(50, 30));
        refreshButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        searchPanel.add(refreshButton);
        searchPanel.add(Box.createHorizontalStrut(5));

        // Add components to the top panel
        topPanel.add(titlePanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(actionPanel, BorderLayout.EAST);

        // Action buttons
        setupActionButtons(actionPanel);
    }

    private void setupActionButtons(JPanel actionPanel) {
        // Create a panel for the main action buttons
        mainButtonsPanel = new JPanel();
        mainButtonsPanel.setLayout(new BoxLayout(mainButtonsPanel, BoxLayout.X_AXIS));
        mainButtonsPanel.setBackground(Color.WHITE);

        // Create a panel for the import/export buttons
        importExportPanel = new JPanel();
        importExportPanel.setLayout(new BoxLayout(importExportPanel, BoxLayout.X_AXIS));
        importExportPanel.setBackground(Color.WHITE);

        // Add button
        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg", 16, 16);
        addButton = new CustomButton("Thêm", addIcon);
        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.setMaximumSize(new Dimension(100, 30));
        addButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        mainButtonsPanel.add(addButton);
        mainButtonsPanel.add(Box.createHorizontalStrut(5));

        // Edit button
        FlatSVGIcon editIcon = new FlatSVGIcon("Icons/edit.svg", 20, 20);
        editButton = new CustomButton("Sửa", editIcon);
        editButton.setPreferredSize(new Dimension(100, 30));
        editButton.setMaximumSize(new Dimension(100, 30));
        editButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        mainButtonsPanel.add(editButton);
        mainButtonsPanel.add(Box.createHorizontalStrut(5));

        // Delete button
        FlatSVGIcon deleteIcon = new FlatSVGIcon("Icons/delete.svg", 20, 20);
        deleteButton = new CustomButton("Xóa", deleteIcon);
        deleteButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setMaximumSize(new Dimension(100, 30));
        deleteButton.setButtonColors(CustomButton.ButtonColors.RED);
        mainButtonsPanel.add(deleteButton);
        mainButtonsPanel.add(Box.createHorizontalStrut(5));

        // Export button
        FlatSVGIcon exportIcon = new FlatSVGIcon("Icons/excel.svg", 16, 16);
        exportButton = new CustomButton("", exportIcon);
        exportButton.setPreferredSize(new Dimension(50, 30));
        exportButton.setMaximumSize(new Dimension(50, 30));
        exportButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        importExportPanel.add(exportButton);
        importExportPanel.add(Box.createHorizontalStrut(5));

        // Import button
        FlatSVGIcon importIcon = new FlatSVGIcon("Icons/import.svg", 16, 16);
        importButton = new CustomButton("", importIcon);
        importButton.setPreferredSize(new Dimension(50, 30));
        importButton.setMaximumSize(new Dimension(50, 30));
        importButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        importExportPanel.add(importButton);

        // Add all button panels to the action panel
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(mainButtonsPanel);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(importExportPanel);
    }

    private void setupBottomPanelLeft() {
        setupChucVuPanel();
    }

    private void setupChucVuPanel() {
        // Chức vụ panel
        JPanel chucVuPanel = createTitledPanel("Chức Vụ", 230, 70);
        chucVuPanel.setLayout(new BorderLayout());
        bottomPanelLeft.add(chucVuPanel);

        // Lấy danh sách chức vụ từ CSDL
        ArrayList<ChucVuDTO> dsChucVu = ChucVuBUS.layDanhSachChucVu();
        String[] chucVuData = new String[dsChucVu.size() + 1];
        chucVuData[0] = "- Chọn chức vụ -";
        for (int i = 0; i < dsChucVu.size(); i++) {
            chucVuData[i + 1] = dsChucVu.get(i).getTenCV();
        }

        chucVuComboBox = new CustomCombobox<>(chucVuData);
        chucVuComboBox.setPlaceholder("- Chọn chức vụ -");
        chucVuPanel.add(chucVuComboBox, BorderLayout.NORTH);
        chucVuPanel.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
    }

    private void setupBottomPanelRight() {
        // Table data
        String[] columnNames = {"Mã NV", "Họ Tên", "Ngày Sinh", "Giới Tính", "Địa Chỉ", "Email", "Trạng Thái", "SĐT", "Chức Vụ"};
        Object[][] data = {
                // Sample data goes here
        };

        // Create table
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        nhanVienTable = new CustomTable(model);
        JScrollPane tableScrollPane = new JScrollPane(nhanVienTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        bottomPanelRight.add(tableScrollPane);
    }

    // Helper methods
    private JPanel createTitledPanel(String title, int width, int height) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                title,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }

    // Phương thức để load dữ liệu nhân viên
    private void loadNhanVienData(DefaultTableModel model, ArrayList<nhanVienDTO> danhSachNhanVien) {
        // Xóa dữ liệu cũ trong bảng
        model.setRowCount(0);

        // Thêm dữ liệu mới vào bảng
        for (nhanVienDTO nv : danhSachNhanVien) {
            // Tìm tên chức vụ từ mã chức vụ
            String tenCV = "";
            for (ChucVuDTO cv : ChucVuBUS.layDanhSachChucVu()) {
                if (cv.getMaCV().equals(nv.getMaCV())) {
                    tenCV = cv.getTenCV();
                    break;
                }
            }

            // Thêm dòng mới vào bảng
            model.addRow(new Object[] {
                nv.getMaNV(),
                nv.getHoTen(),
                nv.getNgaySinh(),
                nv.getGioiTinh(),
                nv.getDiaChi(),
                nv.getEmail(),
                nv.isTrangThai() ? "Hoạt động" : "Không hoạt động",
                nv.getSDT(),
                tenCV
            });
        }
    }
}