package org.example.GUI.Panels.doiTacPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.KhachHangBUS;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.DAO.KhachHangDAO;
import org.example.DTO.KhachHangDTO;
import org.example.GUI.Dialogs.ThemKhachHangDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class KhachHangPanel extends JPanel {
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
    private CustomTable khachHangTable;
    private CustomTable hoaDonTable;

    public KhachHangPanel() {
        initGUI();
    }

    public void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanel();
        setupEventHandlers();

        // Add panels to main panel with proper constraints
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
    }
    
    //====================== XỬ LÝ SỰ KIỆN=================================
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

        // Thiết lập sự kiện cho bảng khách hàng
        khachHangTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && khachHangTable.getSelectedRow() != -1) {
                    // TODO: Implement action when customer selection changes
                    String customerId = khachHangTable.getValueAt(khachHangTable.getSelectedRow(), 0).toString();
                    updateInvoiceTable(customerId);
                }
            }
        });
    }

    // CÁC PHƯƠNG THỨC XỬ LÝ SỰ KIỆN
    private void handleAddButton() {
        // TODO: Implement add customer action
        ThemKhachHangDialog dialog = new ThemKhachHangDialog();
        // Refresh the customer table after adding a new customer
        if (dialog.isClosed()){
            refreshKhachHangTable();
        }

    }

    private void handleEditButton() {
        // TODO: Implement edit customer action
        int selectedRow = khachHangTable.getSelectedRow();
        if (selectedRow != -1) {
            String maKH = (String) khachHangTable.getValueAt(selectedRow, 0);
            String tenKH = (String) khachHangTable.getValueAt(selectedRow, 1);
            String diaChi = (String) khachHangTable.getValueAt(selectedRow, 2);
            int diemTichLuy = (int) khachHangTable.getValueAt(selectedRow, 3);
            String gioiTinh = (String) khachHangTable.getValueAt(selectedRow, 4);
            String sdt = (String) khachHangTable.getValueAt(selectedRow, 5);
            String email = (String) khachHangTable.getValueAt(selectedRow, 6);
            LocalDate ngaySinh = (LocalDate) khachHangTable.getValueAt(selectedRow, 7);


            KhachHangDTO khachHangDTO = new KhachHangDTO();
            khachHangDTO.setMaKH(maKH);
            khachHangDTO.setHoTen(tenKH);
            khachHangDTO.setDiaChi(diaChi);
            khachHangDTO.setDiemTichLuy(diemTichLuy);
            khachHangDTO.setGioiTinh(gioiTinh);
            khachHangDTO.setSDT(sdt);
            khachHangDTO.setEmail(email);
            khachHangDTO.setNgaySinh(ngaySinh);
            // mở hộp thoại sửa thông tin khách hàng
            ThemKhachHangDialog suaKhachHangDialog = new ThemKhachHangDialog(khachHangDTO);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa.");
        }
    }

    private void handleDeleteButton() {
        // TODO: Implement delete customer action
        int selectedRow = khachHangTable.getSelectedRow();
        if (selectedRow != -1) {
            // String maKH = (String) khachHangTable.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Implement delete logic here
                KhachHangBUS khachHangBUS = new KhachHangBUS();
                KhachHangDTO khachHangDTO = new KhachHangDTO();
                khachHangDTO.setMaKH((String) khachHangTable.getValueAt(selectedRow, 0));
                String maKH = (String) khachHangTable.getValueAt(selectedRow, 0);
                khachHangBUS.xoaKhachHang(khachHangDTO);
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công.");

                refreshKhachHangTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa.");
        }
    }

    private void handleRefreshButton() {
        // TODO: Implement refresh action
        refreshKhachHangTable();
    }

    private void handleSearchButton() {
        // TODO: Implement search action
        String searchText = searchField.getText().trim();
        if (!searchText.isEmpty()) {
            // Implement search logic here
            // ArrayList<KhachHangDTO> searchResults = new KhachHangBUS().timKiemKhachHang(searchText);
            // DefaultTableModel model = (DefaultTableModel) khachHangTable.getModel();
            // model.setRowCount(0); // Clear existing rows
            // loadKhachHangData(model, searchResults);
        } else {
            refreshKhachHangTable(); // If search text is empty, refresh the table
        }
    }

    private void handleImportButton() {
        // TODO: Implement import action
    }

    private void handleExportButton() {
        // TODO: Implement export action
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

        // Set background colors
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(Color.WHITE);

        // Set layouts
        topPanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new BorderLayout());
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
        JLabel title = new JLabel("Quản lý khách hàng");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));

        // Search field
        searchField = new PlaceholderTextField("Nhập khách hàng cần tìm");
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

    //======================CÀI ĐẶT CÁC NÚT HÀNH ĐỘNG=================================
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

    //======================CÀI ĐẶT PANEL DƯỚI=================================
    private void setupBottomPanel() {
        // Add padding to the bottom panel
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Sử dụng BoxLayout theo chiều dọc thay vì JSplitPane
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        // Create customer panel
        JPanel customerPanel = new JPanel(new BorderLayout(10, 10));
        customerPanel.setBackground(Color.WHITE);

        // Create invoice panel
        JPanel invoicePanel = new JPanel(new BorderLayout(10, 10));
        invoicePanel.setBackground(Color.WHITE);

        // Setup customer panel
        JPanel customerTablePanel = createCustomerTablePanel();
        customerPanel.add(customerTablePanel, BorderLayout.CENTER);

        // Setup invoice panel
        // Create a title panel for the invoice list
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Lịch sử mua hàng");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 0));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        // Create invoice table panel
        JPanel invoiceTablePanel = createInvoiceTablePanel();
        
        invoicePanel.add(titlePanel, BorderLayout.NORTH);
        invoicePanel.add(invoiceTablePanel, BorderLayout.CENTER);

        // Add panels to bottom panel with proper sizing
        customerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 300));
        customerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        invoicePanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 200));
        invoicePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        bottomPanel.add(customerPanel);
        bottomPanel.add(Box.createVerticalStrut(10)); // Khoảng cách giữa hai panel
        bottomPanel.add(invoicePanel);
    }

    // Các phương thức setupLeftCustomerPanel và setupRightInvoicePanel đã được tích hợp vào setupBottomPanel

    private JPanel createCustomerTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Danh sách khách hàng");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 0));
        tablePanel.add(titleLabel, BorderLayout.NORTH);

        // Table data
        String[] columnNames = {"Mã Khách Hàng", "Tên Khách Hàng","Địa Chỉ","Điểm Tích Lũy","Giới tính", "Số Điện Thoại","Email","Ngày sinh","Tổng Chi Tiêu"};
        KhachHangBUS khachHangBUS = new KhachHangBUS();
        ArrayList<KhachHangDTO> danhSachKhachHang = khachHangBUS.layDanhSachKhachHang();
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        loadKhachHangData(model, danhSachKhachHang);
        // Create table
        khachHangTable = new CustomTable(model);

        JScrollPane tableScrollPane = new JScrollPane(khachHangTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createInvoiceTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Table data - initialize with empty data
        String[] columnNames = {"Mã hóa đơn", "Ngày tạo", "Nhân viên bán", "Tổng tiền", "Trạng thái"};
        Object[][] data = {}; // Empty initially, will be populated when a customer is selected

        // Create table
        hoaDonTable = new CustomTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(hoaDonTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private void updateInvoiceTable(String customerId) {
        // TODO: Implement logic to fetch and display invoices for the selected customerId
        // For now, just display sample data based on the customer ID

        Object[][] invoiceData;
        if (customerId == null || customerId.isEmpty()) {
            invoiceData = new Object[][] {}; // Empty table
        } else {
            // Sample data for demonstration purposes
            switch (customerId) {
                case "KH001":
                    invoiceData = new Object[][] {
                            {"HD001", "15/05/2023", "Nguyễn Văn A", "450000", "Đã thanh toán"},
                            {"HD008", "22/06/2023", "Trần Thị B", "350000", "Đã thanh toán"},
                            {"HD015", "10/07/2023", "Nguyễn Văn A", "700000", "Đã thanh toán"}
                    };
                    break;
                case "KH002":
                    invoiceData = new Object[][] {
                            {"HD003", "05/05/2023", "Trần Thị B", "780000", "Đã thanh toán"},
                            {"HD012", "18/06/2023", "Lê Văn C", "578000", "Đã thanh toán"}
                    };
                    break;
                case "KH003":
                    invoiceData = new Object[][] {
                            {"HD005", "10/05/2023", "Nguyễn Văn A", "243000", "Đã thanh toán"},
                            {"HD010", "25/05/2023", "Trần Thị B", "500000", "Đã thanh toán"},
                            {"HD018", "15/07/2023", "Lê Văn C", "500000", "Đã thanh toán"}
                    };
                    break;
                case "KH004":
                    invoiceData = new Object[][] {
                            {"HD002", "02/05/2023", "Lê Văn C", "486000", "Đã thanh toán"},
                            {"HD009", "24/05/2023", "Nguyễn Văn A", "800000", "Đã thanh toán"}
                    };
                    break;
                case "KH005":
                    invoiceData = new Object[][] {
                            {"HD004", "08/05/2023", "Trần Thị B", "658000", "Đã thanh toán"},
                            {"HD011", "01/06/2023", "Lê Văn C", "700000", "Đã thanh toán"},
                            {"HD016", "12/07/2023", "Nguyễn Văn A", "500000", "Đã thanh toán"}
                    };
                    break;
                default:
                    invoiceData = new Object[][] {
                            {"HD000", "01/01/2023", "Nhân viên", "100000", "Đã thanh toán"}
                    };
                    break;
            }
        }

        // Update the invoice table with new data
        hoaDonTable.setModel(new DefaultTableModel(
                invoiceData,
                new String[] {"Mã hóa đơn", "Ngày tạo", "Nhân viên bán", "Tổng tiền", "Trạng thái"}
        ));
    }

    // Phương thức mới để tải dữ liệu khách hàng vào model
    private void loadKhachHangData(DefaultTableModel model, ArrayList<?> danhSachKhachHang) {
        // TODO: Implement loading customer data
        for (Object obj : danhSachKhachHang) {
            if (obj instanceof KhachHangDTO) {
                KhachHangDTO khachHang = (KhachHangDTO) obj;
                model.addRow(new Object[]{
                        khachHang.getMaKH(),
                        khachHang.getHoTen(),
                        khachHang.getDiaChi(),
                        khachHang.getDiemTichLuy(),
                        khachHang.getGioiTinh(),
                        khachHang.getSDT(),
                        khachHang.getEmail(),
                        khachHang.getNgaySinh(),
                });
            }
        }
    }

    // Phương thức làm mới bảng khách hàng
    public void refreshKhachHangTable() {
        // TODO: Implement refresh logic
        // Clear the existing rows
        DefaultTableModel model = (DefaultTableModel) khachHangTable.getModel();
        model.setRowCount(0);

        // Fetch the updated data
        ArrayList<KhachHangDTO> danhSachKhachHang = new KhachHangBUS().layDanhSachKhachHang();

        // Add the updated data to the table
        loadKhachHangData(model, danhSachKhachHang);

        //in thông báo ra đã refresh để test
        System.out.println("đã cập nhật");
        
        // For now, just add the sample data back with full information
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return radioButton;
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

        // Use preferred size only if width and height are specified
        // Let layout managers handle sizing otherwise
        if (width > 0 && height > 0) {
            panel.setPreferredSize(new Dimension(width, height));
        }

        return panel;
    }

    private JList<String> createScrollableList(String[] data) {
        JList<String> list = new JList<>(data);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return list;
    }

    private JScrollPane createScrollPane(JComponent component, int width, int height) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(width, height));
        return scrollPane;
    }

    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame("Test KhachHangPanel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300, 800); // Adjusted size for better view
            frame.setLocationRelativeTo(null);
            frame.add(new KhachHangPanel());
            // frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Optional: maximize frame
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}

