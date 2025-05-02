package org.example.GUI.Panels.nhanVienPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.ChucVuBUS;
import org.example.BUS.PhanQuyenBUS;
import org.example.Components.*;
import org.example.DTO.ChucVuDTO;
import org.example.DTO.PhanQuyenDTO;
import org.example.DTO.ChucNangDTO;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

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
        // Khởi tạo dữ liệu chức năng mặc định
        PhanQuyenBUS.khoiTaoDuLieuChucNang();
        
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

            // Tìm kiếm chức vụ theo từ khóa
            ArrayList<ChucVuDTO> ketQuaTimKiem = ChucVuBUS.timKiemChucVu(keyword);
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

                // Xóa quyền của chức vụ trước
                PhanQuyenBUS.xoaQuyenTheoChucVu(maCV);

                // Xóa chức vụ
                int result = ChucVuBUS.xoaChucVu(maCV);

                if (result > 0) {
                    showSuccessToast(this, "Xóa chức vụ thành công");
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
                result = ChucVuBUS.capNhatChucVu(chucVu);
                if (result > 0) {
                    // Lưu phân quyền
                    savePermissions(maCV);
                    showSuccessToast(this, "Cập nhật chức vụ thành công");
                } else {
                    showErrorToast(this, "Cập nhật chức vụ thất bại");
                    return;
                }
            } else {
                result = ChucVuBUS.themChucVu(chucVu);
                if (result > 0) {
                    // Lưu phân quyền ngay khi thêm chức vụ mới
                    savePermissions(maCV);
                    showSuccessToast(this, "Thêm chức vụ thành công");
                } else {
                    showErrorToast(this, "Thêm chức vụ thất bại");
                    return;
                }
            }

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
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));

        // Thiết lập layout cho panel
        permissionPanel.setLayout(new BorderLayout());

        // Tạo panel chứa các quyền
        JPanel permissionsContainer = new JPanel();
        permissionsContainer.setLayout(new GridLayout(0, 1, 5, 5));
        permissionsContainer.setBackground(Color.WHITE);

        // Lấy danh sách chức năng từ cơ sở dữ liệu
        ArrayList<ChucNangDTO> danhSachChucNang = PhanQuyenBUS.layDanhSachChucNang();

        // Tạo các radio button cho từng chức năng
        permissionGroups = new ButtonGroup[danhSachChucNang.size()];
        noAccessButtons = new JRadioButton[danhSachChucNang.size()];
        viewOnlyButtons = new JRadioButton[danhSachChucNang.size()];
        editButtons = new JRadioButton[danhSachChucNang.size()];

        // Tạo panel tiêu đề cho các cột
        JPanel headerPanel = new JPanel(new GridLayout(1, 4));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(new JLabel("Chức Năng"));
        headerPanel.add(new JLabel("Không Quyền"));
        headerPanel.add(new JLabel("Chỉ Xem"));
        headerPanel.add(new JLabel("Chỉnh Sửa"));
        permissionsContainer.add(headerPanel);

        // Tạo các radio button cho từng chức năng
        for (int i = 0; i < danhSachChucNang.size(); i++) {
            ChucNangDTO chucNang = danhSachChucNang.get(i);

            JPanel permissionRow = new JPanel(new GridLayout(1, 4));
            permissionRow.setBackground(Color.WHITE);

            // Tạo label cho tên chức năng
            JLabel chucNangLabel = new JLabel(chucNang.getTenChucNang());
            chucNangLabel.setToolTipText(chucNang.getMaChucNang());

            // Tạo các radio button cho các mức quyền
            permissionGroups[i] = new ButtonGroup();

            noAccessButtons[i] = new JRadioButton();
            noAccessButtons[i].setBackground(Color.WHITE);
            noAccessButtons[i].setActionCommand("0");

            viewOnlyButtons[i] = new JRadioButton();
            viewOnlyButtons[i].setBackground(Color.WHITE);
            viewOnlyButtons[i].setActionCommand("1");

            editButtons[i] = new JRadioButton();
            editButtons[i].setBackground(Color.WHITE);
            editButtons[i].setActionCommand("2");

            // Thêm các radio button vào group
            permissionGroups[i].add(noAccessButtons[i]);
            permissionGroups[i].add(viewOnlyButtons[i]);
            permissionGroups[i].add(editButtons[i]);

            // Mặc định là không có quyền
            noAccessButtons[i].setSelected(true);

            // Thêm các thành phần vào hàng
            permissionRow.add(chucNangLabel);
            permissionRow.add(noAccessButtons[i]);
            permissionRow.add(viewOnlyButtons[i]);
            permissionRow.add(editButtons[i]);

            // Thêm hàng vào container
            permissionsContainer.add(permissionRow);
        }

        // Tạo scroll pane cho container
        JScrollPane scrollPane = new JScrollPane(permissionsContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Thêm các nút lưu và hủy
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        saveButton = new CustomButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.setButtonColors(CustomButton.ButtonColors.DISABLE);
        saveButton.setEnabled(false);

        cancelButton = new CustomButton("Hủy");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setButtonColors(CustomButton.ButtonColors.DISABLE);
        cancelButton.setEnabled(false);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Thêm các thành phần vào panel
        permissionPanel.add(scrollPane, BorderLayout.CENTER);
        permissionPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Load phân quyền cho chức vụ
     * @param maCV Mã chức vụ
     */
    private void loadPermissions(String maCV) {
        // Lấy danh sách quyền của chức vụ
        ArrayList<PhanQuyenDTO> danhSachQuyen = PhanQuyenBUS.layDanhSachQuyenTheoChucVu(maCV);

        // Lấy danh sách chức năng
        ArrayList<ChucNangDTO> danhSachChucNang = PhanQuyenBUS.layDanhSachChucNang();

        // Reset tất cả các radio button về trạng thái mặc định (không quyền)
        for (int i = 0; i < noAccessButtons.length; i++) {
            noAccessButtons[i].setSelected(true);
        }

        // Thiết lập quyền cho từng chức năng
        for (PhanQuyenDTO phanQuyen : danhSachQuyen) {
            String maChucNang = phanQuyen.getMaChucNang();
            int quyen = phanQuyen.getQuyen();

            // Tìm vị trí của chức năng trong danh sách
            for (int i = 0; i < danhSachChucNang.size(); i++) {
                if (danhSachChucNang.get(i).getMaChucNang().equals(maChucNang)) {
                    // Thiết lập quyền tương ứng
                    switch (quyen) {
                        case 0:
                            noAccessButtons[i].setSelected(true);
                            break;
                        case 1:
                            viewOnlyButtons[i].setSelected(true);
                            break;
                        case 2:
                            editButtons[i].setSelected(true);
                            break;
                        default:
                            noAccessButtons[i].setSelected(true);
                            break;
                    }
                    break;
                }
            }
        }
    }

    /**
     * Lưu phân quyền cho chức vụ
     * @param maCV Mã chức vụ
     */
    private void savePermissions(String maCV) {
        // Lấy danh sách chức năng
        ArrayList<ChucNangDTO> danhSachChucNang = PhanQuyenBUS.layDanhSachChucNang();
        
        // Tạo danh sách phân quyền mới
        ArrayList<PhanQuyenDTO> danhSachQuyen = new ArrayList<>();
        
        // Lấy quyền cho từng chức năng
        for (int i = 0; i < danhSachChucNang.size(); i++) {
            String maChucNang = danhSachChucNang.get(i).getMaChucNang();
            int quyen = 0; // Mặc định là không quyền
            
            if (viewOnlyButtons[i].isSelected()) {
                quyen = 1; // Chỉ xem
            } else if (editButtons[i].isSelected()) {
                quyen = 2; // Chỉnh sửa
            }
            
            // Tạo đối tượng phân quyền mới
            PhanQuyenDTO phanQuyen = new PhanQuyenDTO(maCV, maChucNang, quyen);
            danhSachQuyen.add(phanQuyen);
        }
        
        // Lưu danh sách phân quyền vào cơ sở dữ liệu
        boolean result = PhanQuyenBUS.capNhatTatCaQuyen(maCV, danhSachQuyen);
        
        if (result) {
            showSuccessToast(this, "Cập nhật phân quyền thành công");
        } else {
            showErrorToast(this, "Cập nhật phân quyền thất bại");
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
