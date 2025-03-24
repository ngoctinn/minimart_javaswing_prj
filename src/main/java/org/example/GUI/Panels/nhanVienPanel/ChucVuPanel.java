package org.example.GUI.Panels.nhanVienPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ChucVuPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;

    // Top panel components
    private PlaceholderTextField searchField;
    private CustomButton searchButton;
    private CustomButton refreshButton;
    private CustomButton addButton;
    private CustomButton editButton;
    private CustomButton deleteButton;
    private CustomButton importButton;
    private CustomButton exportButton;

    // Bottom panel components
    private JPanel leftPanel;
    private JPanel rightPanel;
    private CustomTable chucVuTable;
    private JRadioButton radioTatCa;
    private JRadioButton radioHoatDong;
    private JRadioButton radioKhongHoatDong;

    // Form fields
    private JTextField txtMaChucVu;
    private JTextField txtTenChucVu;

    // Quyền hạn - Comboboxes for permissions
    private JComboBox<String> cmbSanPham;
    private JComboBox<String> cmbLoaiSanPham; 
    private JComboBox<String> cmbNhaCungCap;
    private JComboBox<String> cmbBanHang;
    private JComboBox<String> cmbNhapHang;
    private JComboBox<String> cmbNhanVien;
    private JComboBox<String> cmbChamCong;
    private JComboBox<String> cmbBangLuong;

    // Permission options
    private final String[] permissionOptions = {"Không có quyền", "Chỉ xem", "Có thể chỉnh sửa"};

    public ChucVuPanel() {
        initGUI();
    }

    private void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanel();

        // Add panels to main panel with proper constraints
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
    }

    private void setupMainPanel() {
        // Set up layout and background for main panel
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(new Color(225, 225, 225));
        this.setVisible(true);
    }

    private void createPanels() {
        // Create sub-panels
        topPanel = new RoundedPanel(20);
        bottomPanel = new RoundedPanel(20);

        // Set background colors
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(Color.WHITE);

        // Set layouts
        topPanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new BorderLayout());
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
        JLabel title = new JLabel("Quản lý chức vụ");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));

        // Search field
        searchField = new PlaceholderTextField("Nhập tên chức vụ cần tìm");
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

        // Action buttons will be added to actionPanel
        setupActionButtons(actionPanel);
    }

    private void setupActionButtons(JPanel actionPanel) {
        // Create a panel for the main action buttons
        JPanel mainButtonsPanel = new JPanel();
        mainButtonsPanel.setLayout(new BoxLayout(mainButtonsPanel, BoxLayout.X_AXIS));
        mainButtonsPanel.setBackground(Color.WHITE);

        // Create a panel for the import/export buttons
        JPanel importExportPanel = new JPanel();
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

    private void setupBottomPanel() {
        // Add padding to the bottom panel
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a main content panel with split layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setBorder(null);
        splitPane.setBackground(Color.WHITE);
        splitPane.setDividerSize(5);
        splitPane.setResizeWeight(0.4); // 40% left, 60% right

        // Create left and right panels
        leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(Color.WHITE);

        rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(Color.WHITE);

        // Setup left panel (list of positions)
        setupLeftPanel();

        // Setup right panel (position details and permissions)
        setupRightPanel();

        // Add panels to split pane
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        // Add split pane to bottom panel
        bottomPanel.add(splitPane, BorderLayout.CENTER);
    }

    private void setupLeftPanel() {
        // Add filter panel at the top of left panel
        JPanel filterPanel = createFilterPanel();
        leftPanel.add(filterPanel, BorderLayout.NORTH);

        // Create category table panel
        JPanel tablePanel = createChucVuTablePanel();
        leftPanel.add(tablePanel, BorderLayout.CENTER);
    }

    private void setupRightPanel() {
        // Create a main panel to hold all components with proper spacing
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        // Create detail form panel
        JPanel formPanel = createFormPanel();
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Create permissions panel
        JPanel permissionsPanel = createPermissionsPanel();
        permissionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Button panel at the bottom
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to main panel with spacing
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(permissionsPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        // Add scroll capability to handle overflow
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        rightPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = createTitledPanel("Lựa chọn hiển thị", 0, 80);
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        // Radio buttons
        radioTatCa = createRadioButton("Tất cả");
        radioHoatDong = createRadioButton("Đang hoạt động");
        radioKhongHoatDong = createRadioButton("Không hoạt động");

        // Group radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioTatCa);
        buttonGroup.add(radioHoatDong);
        buttonGroup.add(radioKhongHoatDong);

        // Set default selection
        radioTatCa.setSelected(true);

        // Add to panel
        filterPanel.add(radioTatCa);
        filterPanel.add(radioHoatDong);
        filterPanel.add(radioKhongHoatDong);

        return filterPanel;
    }

    private JPanel createChucVuTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Danh sách chức vụ",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));

        // Table data - removed Lương cơ bản column
        String[] columnNames = {"Mã chức vụ", "Tên chức vụ", "Trạng thái"};
        Object[][] data = {
                {"CV001", "Quản lý", "Đang hoạt động"},
                {"CV002", "Nhân viên bán hàng", "Đang hoạt động"},
                {"CV003", "Nhân viên kho", "Đang hoạt động"},
                {"CV004", "Kế toán", "Đang hoạt động"},
                {"CV005", "Bảo vệ", "Đang hoạt động"},
                {"CV006", "Nhân viên IT", "Không hoạt động"}
        };

        // Create table
        chucVuTable = new CustomTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(chucVuTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Add selection listener to update right panel when position is selected
        chucVuTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && chucVuTable.getSelectedRow() != -1) {
                    updateFormData(chucVuTable.getSelectedRow());
                }
            }
        });

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createFormPanel() {
        JPanel panel = createTitledPanel("Thông tin chức vụ", 0, 120);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Labels
        JLabel lblMaChucVu = new JLabel("Mã chức vụ:");
        lblMaChucVu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel lblTenChucVu = new JLabel("Tên chức vụ:");
        lblTenChucVu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Text fields
        txtMaChucVu = new JTextField();
        txtMaChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMaChucVu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        txtTenChucVu = new JTextField();
        txtTenChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTenChucVu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Add components using GridBagLayout
        // First row
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        panel.add(lblMaChucVu, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.35;
        panel.add(txtMaChucVu, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.15;
        panel.add(lblTenChucVu, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.35;
        panel.add(txtTenChucVu, gbc);

        return panel;
    }

    private JPanel createPermissionsPanel() {
        JPanel panel = createTitledPanel("Phân quyền chức năng", 0, 0);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // Initialize all comboboxes
        cmbSanPham = createPermissionComboBox();
        cmbLoaiSanPham = createPermissionComboBox();
        cmbNhaCungCap = createPermissionComboBox();
        cmbBanHang = createPermissionComboBox();
        cmbNhapHang = createPermissionComboBox();
        cmbNhanVien = createPermissionComboBox();
        cmbChamCong = createPermissionComboBox();
        cmbBangLuong = createPermissionComboBox();

        // First column of permissions
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        JPanel productPanel = createComboPermissionGroup("Sản phẩm", cmbSanPham);
        panel.add(productPanel, gbc);

        gbc.gridy = 1;
        JPanel categoryPanel = createComboPermissionGroup("Loại sản phẩm", cmbLoaiSanPham);
        panel.add(categoryPanel, gbc);

        gbc.gridy = 2;
        JPanel supplierPanel = createComboPermissionGroup("Nhà cung cấp", cmbNhaCungCap);
        panel.add(supplierPanel, gbc);

        gbc.gridy = 3;
        JPanel employeePanel = createComboPermissionGroup("Nhân viên", cmbNhanVien);
        panel.add(employeePanel, gbc);

        // Second column of permissions
        gbc.gridx = 1;
        gbc.gridy = 0;
        JPanel salePanel = createComboPermissionGroup("Bán hàng", cmbBanHang);
        panel.add(salePanel, gbc);

        gbc.gridy = 1;
        JPanel purchasePanel = createComboPermissionGroup("Nhập hàng", cmbNhapHang);
        panel.add(purchasePanel, gbc);

        gbc.gridy = 2;
        JPanel attendancePanel = createComboPermissionGroup("Chấm công", cmbChamCong);
        panel.add(attendancePanel, gbc);

        gbc.gridy = 3;
        JPanel salaryPanel = createComboPermissionGroup("Xem bảng lương", cmbBangLuong);
        panel.add(salaryPanel, gbc);

        return panel;
    }

    private JComboBox<String> createPermissionComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(permissionOptions);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(200, 30));
        return comboBox;
    }

    private JPanel createComboPermissionGroup(String title, JComboBox<String> comboBox) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                title,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14),
                Color.BLACK
        ));

        panel.add(comboBox, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));
        panel.setBackground(Color.WHITE);

        CustomButton saveButton = new CustomButton("Lưu");
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.setButtonColors(CustomButton.ButtonColors.BLUE);

        CustomButton resetButton = new CustomButton("Làm mới");
        resetButton.setPreferredSize(new Dimension(120, 40));
        resetButton.setButtonColors(CustomButton.ButtonColors.GREEN);

        panel.add(saveButton);
        panel.add(resetButton);

        return panel;
    }

    private void updateFormData(int row) {
        // Update form fields based on selected row
        txtMaChucVu.setText(chucVuTable.getValueAt(row, 0).toString());
        txtTenChucVu.setText(chucVuTable.getValueAt(row, 1).toString());

        // Set permissions based on role (example logic)
        boolean isManager = txtTenChucVu.getText().toLowerCase().contains("quản lý");

        // Set default values for comboboxes
        if (isManager) {
            // Managers have full access
            cmbSanPham.setSelectedIndex(2);  // Có thể chỉnh sửa
            cmbLoaiSanPham.setSelectedIndex(2);
            cmbNhaCungCap.setSelectedIndex(2);
            cmbBanHang.setSelectedIndex(2);
            cmbNhapHang.setSelectedIndex(2);
            cmbNhanVien.setSelectedIndex(2);
            cmbChamCong.setSelectedIndex(2);
            cmbBangLuong.setSelectedIndex(2);
        } else if (txtTenChucVu.getText().toLowerCase().contains("bán hàng")) {
            // Sales staff
            cmbSanPham.setSelectedIndex(1);  // Chỉ xem
            cmbLoaiSanPham.setSelectedIndex(1);
            cmbNhaCungCap.setSelectedIndex(1);
            cmbBanHang.setSelectedIndex(2);  // Có thể chỉnh sửa
            cmbNhapHang.setSelectedIndex(0);  // Không có quyền
            cmbNhanVien.setSelectedIndex(0);
            cmbChamCong.setSelectedIndex(0);
            cmbBangLuong.setSelectedIndex(0);
        } else if (txtTenChucVu.getText().toLowerCase().contains("kho")) {
            // Warehouse staff
            cmbSanPham.setSelectedIndex(1);  // Chỉ xem
            cmbLoaiSanPham.setSelectedIndex(1);
            cmbNhaCungCap.setSelectedIndex(1);
            cmbBanHang.setSelectedIndex(0);  // Không có quyền
            cmbNhapHang.setSelectedIndex(2);  // Có thể chỉnh sửa
            cmbNhanVien.setSelectedIndex(0);
            cmbChamCong.setSelectedIndex(0);
            cmbBangLuong.setSelectedIndex(0);
        } else {
            // Default for other roles - minimal access
            cmbSanPham.setSelectedIndex(0);  // Không có quyền
            cmbLoaiSanPham.setSelectedIndex(0);
            cmbNhaCungCap.setSelectedIndex(0);
            cmbBanHang.setSelectedIndex(0);
            cmbNhapHang.setSelectedIndex(0);
            cmbNhanVien.setSelectedIndex(0);
            cmbChamCong.setSelectedIndex(0);
            cmbBangLuong.setSelectedIndex(0);
        }
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

        if (width > 0 && height > 0) {
            panel.setPreferredSize(new Dimension(width, height));
        }

        return panel;
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        radioButton.setBackground(Color.WHITE);
        return radioButton;
    }

    // Test method
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.add(new ChucVuPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
