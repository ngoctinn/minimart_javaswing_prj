package org.example.GUI.Panels.nhanVienPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.HopDongBUS;
import org.example.Components.CustomButton;
import org.example.Components.CustomCombobox;
import org.example.Components.CustomTable;
import org.example.Components.CustomTextField;
import org.example.Components.RoundedPanel;
import org.example.DTO.hopDongDTO;
import org.example.GUI.Dialogs.ThemHopDongDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Panel quản lý hợp đồng nhân viên
 */
public class HopDongPanel extends JPanel {
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

    // Bottom panel components
    private CustomCombobox<String> trangThaiComboBox;
    private CustomTable hopDongTable;

    /**
     * Constructor
     */
    public HopDongPanel() {
        initGUI();
    }

    /**
     * Khởi tạo giao diện
     */
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

        // Load dữ liệu mẫu
        loadSampleData();
    }

    //====================== XỬ LÝ SỰ KIỆN=================================
    /**
     * Thiết lập các sự kiện cho các thành phần
     */
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

        // Thiết lập sự kiện cho combobox trạng thái
        trangThaiComboBox.addItemListener(e -> handleTrangThaiSelection());
    }

    // CÁC PHƯƠNG THỨC XỬ LÝ SỰ KIỆN
    /**
     * Xử lý sự kiện khi nhấn nút Thêm
     */
    private void handleAddButton() {
        // Xử lý thêm hợp đồng
        ThemHopDongDialog dialog = new ThemHopDongDialog();
        // Sau khi dialog đóng, cập nhật lại dữ liệu
        if (dialog.isClosed()) {
            loadSampleData(); // Thay bằng loadData() khi có dữ liệu thật
        }
    }

    /**
     * Xử lý sự kiện khi nhấn nút Sửa
     */
    private void handleEditButton() {
        // Xử lý sửa hợp đồng
        int selectedRow = hopDongTable.getSelectedRow();
        if (selectedRow != -1) {
            String maHopDong = (String) hopDongTable.getValueAt(selectedRow, 0);
            String maNV = (String) hopDongTable.getValueAt(selectedRow, 1);
            String luongCBStr = ((String) hopDongTable.getValueAt(selectedRow, 2)).replace(".", "").replace(",", "");
            double luongCB = Double.parseDouble(luongCBStr);

            // Chuyển đổi chuỗi ngày thành LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate ngayBD = LocalDate.parse((String) hopDongTable.getValueAt(selectedRow, 3), formatter);
            LocalDate ngayKT = LocalDate.parse((String) hopDongTable.getValueAt(selectedRow, 4), formatter);

            // Lấy loại hợp đồng
            String loaiHopDong = (String) hopDongTable.getValueAt(selectedRow, 5);

            // Tạo đối tượng hợp đồng với trạng thái mặc định là true (hoạt động)
            hopDongDTO hopDong = new hopDongDTO(maHopDong, ngayBD, ngayKT, luongCB, maNV, true, loaiHopDong);

            // Mở dialog sửa hợp đồng
            ThemHopDongDialog dialog = new ThemHopDongDialog(hopDong);

            // Sau khi dialog đóng, cập nhật lại dữ liệu
            if (dialog.isClosed()) {
                loadSampleData(); // Thay bằng loadData() khi có dữ liệu thật
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hợp đồng cần sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Xử lý sự kiện khi nhấn nút Xóa
     */
    private void handleDeleteButton() {
        // Xử lý xóa hợp đồng
        int selectedRow = hopDongTable.getSelectedRow();
        if (selectedRow != -1) {
            String maHopDong = (String) hopDongTable.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa hợp đồng " + maHopDong + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Tạo đối tượng hợp đồng chỉ với mã hợp đồng để xóa
                hopDongDTO hopDong = new hopDongDTO();
                hopDong.setMaHopDong(maHopDong);

                // Gọi phương thức xóa từ BUS
                int result = HopDongBUS.xoaHopDong(hopDong);

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Đã xóa hợp đồng: " + maHopDong, "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadSampleData(); // Thay bằng loadData() khi có dữ liệu thật
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa hợp đồng thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hợp đồng cần xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Xử lý sự kiện khi nhấn nút Làm mới
     */
    private void handleRefreshButton() {
        // Xử lý làm mới bảng hợp đồng
        searchField.setText("");
        loadSampleData();
        JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Xử lý sự kiện khi nhấn nút Tìm kiếm
     */
    private void handleSearchButton() {
        // Xử lý tìm kiếm hợp đồng
        String keyword = searchField.getText().trim();
        if (!keyword.isEmpty()) {
            // Gọi phương thức tìm kiếm từ BUS
            ArrayList<hopDongDTO> ketQuaTimKiem = HopDongBUS.timKiemHopDong(keyword);
            if (ketQuaTimKiem.size() > 0) {
                updateTableData(ketQuaTimKiem);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hợp đồng phù hợp", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            loadSampleData(); // Thay bằng loadData() khi có dữ liệu thật
        }
    }

    /**
     * Xử lý sự kiện khi nhấn nút Nhập
     */
    private void handleImportButton() {
        // Xử lý nhập hợp đồng từ file
        JOptionPane.showMessageDialog(this, "Chức năng nhập dữ liệu đang được phát triển", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Xử lý sự kiện khi nhấn nút Xuất
     */
    private void handleExportButton() {
        // Xử lý xuất hợp đồng ra file
        JOptionPane.showMessageDialog(this, "Chức năng xuất dữ liệu đang được phát triển", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Xử lý sự kiện khi chọn trạng thái
     */
    private void handleTrangThaiSelection() {
        // Xử lý khi thay đổi loại hợp đồng
        String selectedLoaiHopDong = (String) trangThaiComboBox.getSelectedItem();

        // Lọc dữ liệu theo loại hợp đồng
        ArrayList<hopDongDTO> danhSachHopDong = createSampleData(); // Thay bằng loadData() khi có dữ liệu thật
        ArrayList<hopDongDTO> filteredList = new ArrayList<>();

        if ("Tất cả".equals(selectedLoaiHopDong)) {
            filteredList = danhSachHopDong;
        } else {
            // Lọc theo loại hợp đồng
            for (hopDongDTO hopDong : danhSachHopDong) {
                if (selectedLoaiHopDong.equals(hopDong.getLoaiHopDong())) {
                    filteredList.add(hopDong);
                }
            }
        }

        // Cập nhật bảng
        updateTableData(filteredList);
    }

    //======================CÀI ĐẶT PANEL CHÍNH=================================
    /**
     * Thiết lập panel chính
     */
    private void setupMainPanel() {
        // Set up layout and background for main panel
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(new Color(225, 225, 225));
        this.setVisible(true);
    }

    //======================CÀI ĐẶT CÁC PANEL CON=================================
    /**
     * Tạo các panel con
     */
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
        bottomPanelRight.setLayout(new BorderLayout());
    }

    /**
     * Thiết lập panel trên cùng
     */
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
        JLabel title = new JLabel("Quản Lý Hợp Đồng");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));

        // Search field
        searchField = new CustomTextField("Nhập mã hợp đồng hoặc mã nhân viên");
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

    /**
     * Thiết lập các nút chức năng
     * @param actionPanel Panel chứa các nút chức năng
     */
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

    /**
     * Thiết lập panel bên trái dưới cùng
     */
    private void setupBottomPanelLeft() {
        setupTrangThaiPanel();
    }

    /**
     * Thiết lập panel chọn loại hợp đồng
     */
    private void setupTrangThaiPanel() {
        // Loại hợp đồng panel
        JPanel trangThaiPanel = createTitledPanel("Loại Hợp Đồng", 230, 70);
        trangThaiPanel.setLayout(new BorderLayout());
        bottomPanelLeft.add(trangThaiPanel);

        // Loại hợp đồng combobox
        String[] trangThaiData = {
                "Tất cả",
                "Chính thức",
                "Thử việc",
                "Thời vụ",
                "Hợp tác"
        };

        trangThaiComboBox = new CustomCombobox<>(trangThaiData);
        trangThaiComboBox.setSelectedIndex(0);
        trangThaiPanel.add(trangThaiComboBox, BorderLayout.NORTH);
        trangThaiPanel.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
    }

    /**
     * Thiết lập panel bên phải dưới cùng
     */
    private void setupBottomPanelRight() {
        // Table data
        String[] columnNames = {"Mã Hợp Đồng", "Mã NV", "Lương Cơ Bản", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Loại Hợp Đồng"};
        Object[][] data = {};

        // Create table
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        hopDongTable = new CustomTable(model);
        JScrollPane tableScrollPane = new JScrollPane(hopDongTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        bottomPanelRight.add(tableScrollPane, BorderLayout.CENTER);
    }

    /**
     * Tạo panel có tiêu đề
     * @param title Tiêu đề của panel
     * @param width Chiều rộng
     * @param height Chiều cao
     * @return Panel có tiêu đề
     */
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

    /**
     * Load dữ liệu mẫu vào bảng
     */
    private void loadSampleData() {
        // Tạo dữ liệu mẫu
        ArrayList<hopDongDTO> danhSachHopDong = createSampleData();

        // Cập nhật bảng
        updateTableData(danhSachHopDong);
    }

    /**
     * Tạo dữ liệu mẫu
     * @return Danh sách hợp đồng mẫu
     */
    private ArrayList<hopDongDTO> createSampleData() {
        ArrayList<hopDongDTO> danhSachHopDong = new ArrayList<>();

        // Tạo một số hợp đồng mẫu
        danhSachHopDong.add(new hopDongDTO("HD001", LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), 10000000, "NV001", true, "Chính thức"));
        danhSachHopDong.add(new hopDongDTO("HD002", LocalDate.of(2023, 2, 1), LocalDate.of(2024, 2, 1), 12000000, "NV002", true, "Thử việc"));
        danhSachHopDong.add(new hopDongDTO("HD003", LocalDate.of(2023, 3, 1), LocalDate.of(2024, 3, 1), 15000000, "NV003", true, "Chính thức"));
        danhSachHopDong.add(new hopDongDTO("HD004", LocalDate.of(2023, 4, 1), LocalDate.of(2024, 4, 1), 9000000, "NV004", true, "Thời vụ"));
        danhSachHopDong.add(new hopDongDTO("HD005", LocalDate.of(2023, 5, 1), LocalDate.of(2024, 5, 1), 11000000, "NV005", true, "Hợp tác"));

        return danhSachHopDong;
    }

    /**
     * Cập nhật dữ liệu bảng
     * @param danhSachHopDong Danh sách hợp đồng
     */
    private void updateTableData(ArrayList<hopDongDTO> danhSachHopDong) {
        // Lấy model của bảng
        DefaultTableModel model = (DefaultTableModel) hopDongTable.getModel();

        // Xóa dữ liệu cũ
        model.setRowCount(0);

        // Định dạng ngày tháng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Thêm dữ liệu mới
        for (hopDongDTO hopDong : danhSachHopDong) {
            // Thêm dòng mới vào bảng
            model.addRow(new Object[]{
                hopDong.getMaHopDong(),
                hopDong.getMaNV(),
                String.format("%,.0f", hopDong.getLuongCB()),
                hopDong.getNgayBD().format(formatter),
                hopDong.getNgayKT().format(formatter),
                hopDong.getLoaiHopDong()
            });
        }
    }

    /**
     * Phương thức main để chạy thử panel
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame("Quản Lý Hợp Đồng");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.add(new HopDongPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}