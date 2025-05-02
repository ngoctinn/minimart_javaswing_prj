package org.example.GUI.Panels.nhanVienPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.ChucVuBUS;
import org.example.Components.*;
import org.example.DTO.ChucVuDTO;
import org.example.DTO.PhanQuyenDTO;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

import static org.example.Components.CustomToastMessage.showErrorToast;
import static org.example.Components.CustomToastMessage.showSuccessToast;

public class ChucVuPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;
    private RoundedPanel formPanel;
    private RoundedPanel permissionPanel;

    // Top panel components
    private CustomTextField searchField;
    private CustomButton searchButton;
    private CustomButton refreshButton;
    private CustomButton addButton;
    private CustomButton editButton;
    private CustomButton deleteButton;
    private CustomButton saveButton;
    private CustomButton cancelButton;

    // Form components
    private CustomTextField maChucVuField;
    private CustomTextField tenChucVuField;

    // Permission components
    private ButtonGroup[] permissionGroups;
    private JRadioButton[] noAccessButtons;
    private JRadioButton[] viewOnlyButtons;
    private JRadioButton[] editButtons;

    // Table components
    private CustomTable chucVuTable;

    // State
    private boolean isEditing = false;
    private ChucVuDTO currentChucVu = null;

    public ChucVuPanel() {
        initGUI();
    }

    /**
     * Khởi tạo giao diện cho ChucVuPanel
     */
    public void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanelLeft();
        setupBottomPanelRight();
        setupFormPanel();
        setupPermissionPanel();
        setupEventHandlers();

        // Add panels to main panel with proper constraints
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);

        // Load data
        refreshChucVuTable();
    }

    /**
     * Thiết lập các sự kiện cho các thành phần giao diện
     */
    private void setupEventHandlers() {
        // Thiết lập tất cả các sự kiện ở đây
        searchButton.addActionListener(e -> handleSearchButton());
        refreshButton.addActionListener(e -> handleRefreshButton());
        addButton.addActionListener(e -> handleAddButton());
        editButton.addActionListener(e -> handleEditButton());
        deleteButton.addActionListener(e -> handleDeleteButton());
        saveButton.addActionListener(e -> handleSaveButton());
        cancelButton.addActionListener(e -> handleCancelButton());
        searchField.addActionListener(e -> handleSearchButton());

        // Thiết lập sự kiện cho bảng chức vụ
        chucVuTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && chucVuTable.getSelectedRow() != -1) {
                handleChucVuSelection();
            }
        });
    }

    /**
     * Xử lý khi người dùng chọn một chức vụ từ bảng
     */
    private void handleChucVuSelection() {
        int selectedRow = chucVuTable.getSelectedRow();
        if (selectedRow != -1) {
            String maCV = (String) chucVuTable.getValueAt(selectedRow, 0);
            String tenCV = (String) chucVuTable.getValueAt(selectedRow, 1);

            // Hiển thị thông tin chức vụ lên form
            maChucVuField.setText(maCV);
            tenChucVuField.setText(tenCV);

            currentChucVu = new ChucVuDTO(tenCV, maCV);

            // Thiết lập các quyền cho chức vụ
            loadPermissions(maCV);
        }
    }

    /**
     * Làm mới bảng chức vụ
     */
    public void refreshChucVuTable() {
        DefaultTableModel model = (DefaultTableModel) chucVuTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu hiện tại

        // Lấy danh sách chức vụ từ cơ sở dữ liệu
        ArrayList<ChucVuDTO> danhSachChucVu = ChucVuBUS.layDanhSachChucVu();
        loadChucVuData(model, danhSachChucVu);

        // Reset form
        resetForm();
    }

    /**
     * Load dữ liệu chức vụ vào bảng
     */
    private void loadChucVuData(DefaultTableModel model, ArrayList<ChucVuDTO> danhSachChucVu) {
        for (ChucVuDTO chucVu : danhSachChucVu) {
            model.addRow(new Object[]{
                chucVu.getMaCV(),
                chucVu.getTenCV()
            });
        }
    }

    /**
     * Xử lý nút tìm kiếm
     */
    private void handleSearchButton() {
        String keyword = searchField.getText().trim();
        if (!keyword.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) chucVuTable.getModel();
            model.setRowCount(0);

            // Dữ liệu mẫu để test
            ArrayList<ChucVuDTO> danhSachChucVu = new ArrayList<>();
            danhSachChucVu.add(new ChucVuDTO("Giám đốc", "CV001"));
            danhSachChucVu.add(new ChucVuDTO("Quản lý", "CV002"));
            danhSachChucVu.add(new ChucVuDTO("Nhân viên bán hàng", "CV003"));
            danhSachChucVu.add(new ChucVuDTO("Nhân viên kho", "CV004"));

            // Lọc theo từ khóa
            ArrayList<ChucVuDTO> ketQuaTimKiem = new ArrayList<>();
            for (ChucVuDTO chucVu : danhSachChucVu) {
                if (chucVu.getMaCV().toLowerCase().contains(keyword.toLowerCase()) ||
                    chucVu.getTenCV().toLowerCase().contains(keyword.toLowerCase())) {
                    ketQuaTimKiem.add(chucVu);
                }
            }

            loadChucVuData(model, ketQuaTimKiem);
        } else {
            refreshChucVuTable();
        }
    }

    /**
     * Xử lý nút làm mới
     */
    private void handleRefreshButton() {
        refreshChucVuTable();
    }

    /**
     * Xử lý nút thêm
     */
    private void handleAddButton() {
        resetForm();
        setFormEditable(true);
        isEditing = false;

        // Generate mã chức vụ tự động
        String maCV = ChucVuBUS.generateNextMaLSP();
        maChucVuField.setText(maCV);
    }

    /**
     * Xử lý nút sửa
     */
    private void handleEditButton() {
        int selectedRow = chucVuTable.getSelectedRow();
        if (selectedRow != -1) {
            setFormEditable(true);
            isEditing = true;
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ để sửa.");
        }
    }

    /**
     * Xử lý nút xóa
     */
    private void handleDeleteButton() {
        int selectedRow = chucVuTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa chức vụ này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String maCV = (String) chucVuTable.getValueAt(selectedRow, 0);

                // Xóa chức vụ (giả lập)
                int result = 1; // Giả sử xóa thành công

                if (result > 0) {
                    showSuccessToast(this, "Xóa chức vụ thành công");
                    // Xóa quyền của chức vụ
                    // PhanQuyenBUS.xoaQuyenTheoChucVu(maCV);

                    // Làm mới bảng
                    refreshChucVuTable();
                } else {
                    showErrorToast(this, "Xóa chức vụ thất bại");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ để xóa.");
        }
    }

    /**
     * Xử lý nút lưu
     */
    private void handleSaveButton() {
        if (validateForm()) {
            String maCV = maChucVuField.getText().trim();
            String tenCV = tenChucVuField.getText().trim();

            ChucVuDTO chucVu = new ChucVuDTO(tenCV, maCV);

            int result;
            if (isEditing) {
                // result = ChucVuBUS.capNhatChucVu(chucVu);
                result = 1; // Temporary for testing
                if (result > 0) {
                    showSuccessToast(this, "Cập nhật chức vụ thành công");
                } else {
                    showErrorToast(this, "Cập nhật chức vụ thất bại");
                    return;
                }
            } else {
                // result = ChucVuBUS.themChucVu(chucVu);
                result = 1; // Temporary for testing
                if (result > 0) {
                    showSuccessToast(this, "Thêm chức vụ thành công");
                } else {
                    showErrorToast(this, "Thêm chức vụ thất bại");
                    return;
                }
            }

            // Lưu phân quyền
            savePermissions(maCV);

            // Làm mới bảng và form
            refreshChucVuTable();
            setFormEditable(false);
        }
    }

    /**
     * Xử lý nút hủy
     */
    private void handleCancelButton() {
        resetForm();
        setFormEditable(false);
    }

    /**
     * Reset form về trạng thái ban đầu
     */
    private void resetForm() {
        maChucVuField.setText("");
        tenChucVuField.setText("");
        currentChucVu = null;
        isEditing = false;

        // Reset các radio button về trạng thái mặc định (không quyền)
        if (noAccessButtons != null && viewOnlyButtons != null && editButtons != null) {
            for (int i = 0; i < noAccessButtons.length; i++) {
                noAccessButtons[i].setSelected(true);
                viewOnlyButtons[i].setSelected(false);
                editButtons[i].setSelected(false);
            }
        }
    }

    /**
     * Thiết lập trạng thái có thể chỉnh sửa cho form
     */
    private void setFormEditable(boolean editable) {
        tenChucVuField.setEditable(editable);
        tenChucVuField.setState(CustomTextField.State.DEFAULT);

        // Kích hoạt/vô hiệu hóa các radio button phân quyền
        if (noAccessButtons != null && viewOnlyButtons != null && editButtons != null) {
            for (int i = 0; i < noAccessButtons.length; i++) {
                noAccessButtons[i].setEnabled(editable);
                viewOnlyButtons[i].setEnabled(editable);
                editButtons[i].setEnabled(editable);
            }
        }

        // Kích hoạt/vô hiệu hóa các nút
        saveButton.setEnabled(editable);
        saveButton.setButtonColors(editable ? CustomButton.ButtonColors.GREEN : CustomButton.ButtonColors.DISABLE);
        cancelButton.setEnabled(editable);
        cancelButton.setButtonColors(editable ? CustomButton.ButtonColors.RED : CustomButton.ButtonColors.DISABLE);
        addButton.setEnabled(!editable);
        addButton.setButtonColors(editable ? CustomButton.ButtonColors.DISABLE : CustomButton.ButtonColors.BLUE);
        editButton.setEnabled(!editable);
        editButton.setButtonColors(editable ? CustomButton.ButtonColors.DISABLE : CustomButton.ButtonColors.GREEN);
        deleteButton.setEnabled(!editable);
        deleteButton.setButtonColors(editable ? CustomButton.ButtonColors.DISABLE : CustomButton.ButtonColors.RED);
    }

    /**
     * Kiểm tra dữ liệu nhập vào form
     */
    private boolean validateForm() {
        String tenCV = tenChucVuField.getText().trim();

        if (tenCV.isEmpty()) {
            showErrorToast(this, "Vui lòng nhập tên chức vụ");
            return false;
        }

        return true;
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
        formPanel = new RoundedPanel(20);
        permissionPanel = new RoundedPanel(20);

        // Set background colors
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(new Color(225, 225, 225));
        bottomPanelLeft.setBackground(Color.WHITE);
        bottomPanelRight.setBackground(Color.WHITE);
        formPanel.setBackground(Color.WHITE);
        permissionPanel.setBackground(Color.WHITE);

        // Set panel sizes
        topPanel.setPreferredSize(new Dimension(0, 60));
        bottomPanelLeft.setPreferredSize(new Dimension(500, 0));

        // Set layouts
        topPanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new BorderLayout(10, 0));
        bottomPanelLeft.setLayout(new BorderLayout());
        bottomPanelRight.setLayout(new BorderLayout(0, 10));
    }

    //======================CÀI ĐẶT PANEL TRÊN=================================
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
        JLabel title = new JLabel("Quản Lý Chức Vụ");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));

        // Search field
        searchField = new CustomTextField("Tìm kiếm theo mã, tên chức vụ");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setMaximumSize(new Dimension(300, 30));
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

    //======================CÀI ĐẶT CÁC NÚT HÀNH ĐỘNG=================================
    private void setupActionButtons(JPanel actionPanel) {
        // Create a panel for the main action buttons
        JPanel mainButtonsPanel = new JPanel();
        mainButtonsPanel.setLayout(new BoxLayout(mainButtonsPanel, BoxLayout.X_AXIS));
        mainButtonsPanel.setBackground(Color.WHITE);

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

        // Add all button panels to the action panel
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(mainButtonsPanel);
    }
    //======================CÀI ĐẶT PANEL DƯỚI BÊN TRÁI=================================
    private void setupBottomPanelLeft() {
        // Tạo border có tiêu đề cho panel
        bottomPanelLeft.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Danh Sách Chức Vụ",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));

        // Tạo bảng chức vụ
        String[] columnNames = {"Mã Chức Vụ", "Tên Chức Vụ"};

        ArrayList<ChucVuDTO> danhSachChucVu = ChucVuBUS.layDanhSachChucVu();
        Object[][] data = new Object[danhSachChucVu.size()][2];
        for (int i = 0; i < danhSachChucVu.size(); i++) {
            data[i][0] = danhSachChucVu.get(i).getMaCV();
            data[i][1] = danhSachChucVu.get(i).getTenCV();
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        };

        chucVuTable = new CustomTable(model);
        chucVuTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Tạo JScrollPane cho bảng
        JScrollPane scrollPane = new JScrollPane(chucVuTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Thêm bảng vào panel
        bottomPanelLeft.add(scrollPane, BorderLayout.CENTER);
    }

    //======================CÀI ĐẶT PANEL DƯỚI BÊN PHẢI=================================
    private void setupBottomPanelRight() {
        // Thêm formPanel và permissionPanel vào bottomPanelRight
        bottomPanelRight.add(formPanel, BorderLayout.NORTH);
        bottomPanelRight.add(permissionPanel, BorderLayout.CENTER);
    }

    //======================CÀI ĐẶT PANEL FORM=================================
    private void setupFormPanel() {
        // Tạo border có tiêu đề cho panel
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Thông Tin Chức Vụ",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));

        // Thiết lập layout cho panel
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Mã chức vụ
        JLabel maChucVuLabel = new JLabel("Mã chức vụ:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(maChucVuLabel, gbc);

        maChucVuField = new CustomTextField("Mã chức vụ tự động");
        maChucVuField.setState(CustomTextField.State.DISABLED);
        maChucVuField.setEditable(false); // Mã chức vụ không cho phép chỉnh sửa
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        formPanel.add(maChucVuField, gbc);

        // Tên chức vụ
        JLabel tenChucVuLabel = new JLabel("Tên chức vụ:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(tenChucVuLabel, gbc);

        tenChucVuField = new CustomTextField("Nhập tên chức vụ (vd: Quản lý)");
        tenChucVuField.setState(CustomTextField.State.DISABLED);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        formPanel.add(tenChucVuField, gbc);

        // Panel chứa các nút Lưu và Hủy
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        // Nút Lưu
        saveButton = new CustomButton("Lưu");
        saveButton.setButtonColors(CustomButton.ButtonColors.DISABLE);
        saveButton.setEnabled(false); // Ban đầu vô hiệu hóa
        buttonPanel.add(saveButton);

        // Nút Hủy
        cancelButton = new CustomButton("Hủy");
        cancelButton.setButtonColors(CustomButton.ButtonColors.DISABLE);
        cancelButton.setEnabled(false);// Ban đầu vô hiệu hóa
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        formPanel.add(buttonPanel, gbc);

        // Thiết lập kích thước cho formPanel
        formPanel.setPreferredSize(new Dimension(0, 150));
    }

    //======================CÀI ĐẶT PANEL PHÂN QUYỀN=================================
    private void setupPermissionPanel() {
        // Tạo border có tiêu đề cho panel
        permissionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Phân Quyền",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 16),
                Color.BLACK
        ));

        // Thiết lập layout cho panel
        permissionPanel.setLayout(new BorderLayout());
        permissionPanel.setBorder(BorderFactory.createCompoundBorder(
                permissionPanel.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Danh sách các chức năng cần phân quyền
        String[] functionNames = {
            "Quản lý sản phẩm",
            "Quản lý loại sản phẩm",
            "Quản lý nhân viên",
            "Quản lý chức vụ",
            "Quản lý khách hàng",
            "Quản lý nhà cung cấp",
            "Quản lý hóa đơn",
            "Quản lý nhập hàng",
            "Xem báo cáo",
            "Quản lý khuyến mãi",
            "Quản lý kho",
            "Quản lý tài khoản"
        };

        // Panel lưới để chứa các chức năng và radio buttons - giảm khoảng cách giữa các cột
        JPanel gridPanel = new JPanel(new GridLayout(functionNames.length + 1, 4, 1, 2));
        gridPanel.setBackground(Color.WHITE);

        // Header row với font chữ lớn hơn
        JLabel functionHeader = new JLabel("Chức năng", JLabel.LEFT);
        functionHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        JLabel noAccessHeader = new JLabel("Không quyền", JLabel.CENTER);
        noAccessHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        JLabel viewOnlyHeader = new JLabel("Chỉ xem", JLabel.CENTER);
        viewOnlyHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        JLabel editHeader = new JLabel("Chỉnh sửa", JLabel.CENTER);
        editHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));

        gridPanel.add(functionHeader);
        gridPanel.add(noAccessHeader);
        gridPanel.add(viewOnlyHeader);
        gridPanel.add(editHeader);

        // Mảng lưu các nhóm radio button cho từng chức năng
        permissionGroups = new ButtonGroup[functionNames.length];
        noAccessButtons = new JRadioButton[functionNames.length];
        viewOnlyButtons = new JRadioButton[functionNames.length];
        editButtons = new JRadioButton[functionNames.length];

        // Tạo các radio button cho từng chức năng
        for (int i = 0; i < functionNames.length; i++) {
            // Tạo một dòng cho mỗi chức năng với font chữ lớn hơn
            JLabel functionLabel = new JLabel(functionNames[i], JLabel.LEFT);
            functionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            gridPanel.add(functionLabel);

            // Radio button cho "Không quyền"
            noAccessButtons[i] = new JRadioButton();
            noAccessButtons[i].setBackground(Color.WHITE);
            noAccessButtons[i].setSelected(true); // Mặc định là không quyền
            noAccessButtons[i].setEnabled(false); // Ban đầu vô hiệu hóa
            noAccessButtons[i].setHorizontalAlignment(JRadioButton.CENTER);
            gridPanel.add(noAccessButtons[i]);

            // Radio button cho "Chỉ xem"
            viewOnlyButtons[i] = new JRadioButton();
            viewOnlyButtons[i].setBackground(Color.WHITE);
            viewOnlyButtons[i].setEnabled(false); // Ban đầu vô hiệu hóa
            viewOnlyButtons[i].setHorizontalAlignment(JRadioButton.CENTER);
            gridPanel.add(viewOnlyButtons[i]);

            // Radio button cho "Chỉnh sửa"
            editButtons[i] = new JRadioButton();
            editButtons[i].setBackground(Color.WHITE);
            editButtons[i].setEnabled(false); // Ban đầu vô hiệu hóa
            editButtons[i].setHorizontalAlignment(JRadioButton.CENTER);
            gridPanel.add(editButtons[i]);

            // Nhóm các radio button lại với nhau
            permissionGroups[i] = new ButtonGroup();
            permissionGroups[i].add(noAccessButtons[i]);
            permissionGroups[i].add(viewOnlyButtons[i]);
            permissionGroups[i].add(editButtons[i]);
        }

        // Thêm panel lưới vào permission panel với cấu hình không scroll ngang
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        permissionPanel.add(scrollPane);
    }

    /**
     * Lưu các phân quyền của chức vụ
     * @param maCV Mã chức vụ cần lưu phân quyền
     */
    private void savePermissions(String maCV) {
        // Danh sách các chức năng cần phân quyền
        String[] functionNames = {
            "Quản lý sản phẩm",
            "Quản lý loại sản phẩm",
            "Quản lý nhân viên",
            "Quản lý chức vụ",
            "Quản lý khách hàng",
            "Quản lý nhà cung cấp",
            "Quản lý hóa đơn",
            "Quản lý nhập hàng",
            "Xem báo cáo",
            "Quản lý khuyến mãi",
            "Quản lý kho",
            "Quản lý tài khoản"
        };

        // Xóa tất cả các quyền hiện có của chức vụ này
        // PhanQuyenBUS.xoaQuyenTheoChucVu(maCV);

        // Lưu các quyền mới
        for (int i = 0; i < functionNames.length; i++) {
            String quyen = "khongQuyen";

            if (viewOnlyButtons[i].isSelected()) {
                quyen = "chiXem";
            } else if (editButtons[i].isSelected()) {
                quyen = "chinhSua";
            }

            if (quyen == "chiXem" || quyen == "chinhSua") {
                // Tạo mã quyền cho chức năng này
                String maQuyen = "QUY" + String.format("%03d", i + 1);

                // Tạo đối tượng PhanQuyenDTO
                PhanQuyenDTO phanQuyen = new PhanQuyenDTO(
                );
                phanQuyen.setMaPhanQuyen(maQuyen);
                phanQuyen.setMaCV(maCV);
                phanQuyen.setModule(functionNames[i]);
                phanQuyen.setPhanQuyen(quyen);

                // Lưu vào CSDL
                // PhanQuyenBUS.themQuyen(phanQuyen);
            }
        }
    }

    /**
     * Load các phân quyền của chức vụ
     * @param maCV Mã chức vụ cần load phân quyền
     */
    private void loadPermissions(String maCV) {
        // Reset về trạng thái mặc định (không quyền)
        for (int i = 0; i < noAccessButtons.length; i++) {
            noAccessButtons[i].setSelected(true);
        }

        // Lấy danh sách quyền của chức vụ
        // ArrayList<PhanQuyenDTO> dsQuyen = PhanQuyenBUS.layDanhSachQuyenTheoChucVu(maCV);

        // Danh sách mẫu để test
        ArrayList<PhanQuyenDTO> dsQuyen = new ArrayList<>();

        // Hiển thị các quyền đã được phân
        for (PhanQuyenDTO quyen : dsQuyen) {
            // Tìm index của chức năng này trong mảng
            String tenQuyen = quyen.getModule();
            int index = -1;
            
            // Danh sách các chức năng cần phân quyền
            String[] functionNames = {
                "Quản lý sản phẩm", 
                "Quản lý loại sản phẩm", 
                "Quản lý nhân viên",
                "Quản lý chức vụ", 
                "Quản lý khách hàng", 
                "Quản lý nhà cung cấp",
                "Quản lý hóa đơn", 
                "Quản lý nhập hàng", 
                "Xem báo cáo",
                "Quản lý khuyến mãi", 
                "Quản lý kho", 
                "Quản lý tài khoản"
            };
            
            for (int i = 0; i < functionNames.length; i++) {
                if (functionNames[i].equals(tenQuyen)) {
                    index = i;
                    break;
                }
            }
            
            if (index != -1) {
                // Thiết lập radio button tương ứng
                if (quyen.isTrangThai()) {
                    // Quyền chỉnh sửa
                    editButtons[index].setSelected(true);
                } else {
                    // Quyền chỉ xem
                    viewOnlyButtons[index].setSelected(true);
                }
            }
        }
    }
    
    /**
     * Phương thức chính để chạy thử ChucVuPanel
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        JFrame frame = new JFrame("Quản Lý Chức Vụ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        
        ChucVuPanel chucVuPanel = new ChucVuPanel();
        frame.getContentPane().add(chucVuPanel);
        
        frame.setVisible(true);
    }
}
