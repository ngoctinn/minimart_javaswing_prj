package org.example.GUI.Panels.doiTacPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.GUI.Dialogs.ThemKhachHangDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
    private JPanel leftPanel;
    private JPanel rightPanel;
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
        searchField.addActionListener(e -> {
            // TODO: Implement search action on Enter key press
        });
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));

        // Search button
        searchButton = new CustomButton("Tìm");
        searchButton.setPreferredSize(new Dimension(70, 30));
        searchButton.setMaximumSize(new Dimension(70, 30));
        searchButton.addActionListener(e -> {
            // TODO: Implement search action on button click
        });
        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(5));

        // Refresh button
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setPreferredSize(new Dimension(50, 30));
        refreshButton.setMaximumSize(new Dimension(50, 30));
        refreshButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        refreshButton.addActionListener(e -> {
            // TODO: Implement refresh action
        });
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
        addButton.addActionListener(e -> {
             // TODO: Implement add customer action
              ThemKhachHangDialog themKhachHangDialog = new ThemKhachHangDialog();
        });
        mainButtonsPanel.add(addButton);
        mainButtonsPanel.add(Box.createHorizontalStrut(5));

        // Edit button
        FlatSVGIcon editIcon = new FlatSVGIcon("Icons/edit.svg", 20, 20);
        editButton = new CustomButton("Sửa", editIcon);
        editButton.setPreferredSize(new Dimension(100, 30));
        editButton.setMaximumSize(new Dimension(100, 30));
        editButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        editButton.addActionListener(e -> {
            // TODO: Implement edit customer action
        });
        mainButtonsPanel.add(editButton);
        mainButtonsPanel.add(Box.createHorizontalStrut(5));

        // Delete button
        FlatSVGIcon deleteIcon = new FlatSVGIcon("Icons/delete.svg", 20, 20);
        deleteButton = new CustomButton("Xóa", deleteIcon);
        deleteButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setMaximumSize(new Dimension(100, 30));
        deleteButton.setButtonColors(CustomButton.ButtonColors.RED);
        deleteButton.addActionListener(e -> {
            // TODO: Implement delete customer action
        });
        mainButtonsPanel.add(deleteButton);
        mainButtonsPanel.add(Box.createHorizontalStrut(5));

        // Export button
        FlatSVGIcon exportIcon = new FlatSVGIcon("Icons/excel.svg", 16, 16);
        exportButton = new CustomButton("", exportIcon);
        exportButton.setPreferredSize(new Dimension(50, 30));
        exportButton.setMaximumSize(new Dimension(50, 30));
        exportButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        exportButton.addActionListener(e -> {
            // TODO: Implement export action
        });
        importExportPanel.add(exportButton);
        importExportPanel.add(Box.createHorizontalStrut(5));

        // Import button
        FlatSVGIcon importIcon = new FlatSVGIcon("Icons/import.svg", 16, 16);
        importButton = new CustomButton("", importIcon);
        importButton.setPreferredSize(new Dimension(50, 30));
        importButton.setMaximumSize(new Dimension(50, 30));
        importButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        importButton.addActionListener(e -> {
            // TODO: Implement import action
        });
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

        // Setup left panel (customers)
        setupLeftCustomerPanel();

        // Setup right panel (invoices)
        setupRightInvoicePanel();

        // Add panels to split pane
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        // Add split pane to bottom panel
        bottomPanel.add(splitPane, BorderLayout.CENTER);
    }

    private void setupLeftCustomerPanel() {
        // Create customer table panel
        JPanel customerTablePanel = createCustomerTablePanel();

        // Add to left panel
        leftPanel.add(customerTablePanel, BorderLayout.CENTER);
    }

    private void setupRightInvoicePanel() {
        // Create a title panel for the invoice list
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Lịch sử mua hàng");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Match LoaiSanPhamPanel font size
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0)); // Match LoaiSanPhamPanel border
        titlePanel.add(titleLabel, BorderLayout.WEST);

        // Create invoice table panel
        JPanel invoiceTablePanel = createInvoiceTablePanel();

        // Add to right panel
        rightPanel.add(titlePanel, BorderLayout.NORTH);
        rightPanel.add(invoiceTablePanel, BorderLayout.CENTER);
    }

    private JPanel createCustomerTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Danh sách khách hàng",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));

        // Table data
        String[] columnNames = {"Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại", "Tổng Bán"};
        Object[][] data = {
                // TODO: Load actual customer data here
                {"KH000005", "Anh Giang - Kim Mã", "0123456789", 1858000},
                {"KH000004", "Anh Hoàng - Sài Gòn", "0987654321", 1286000},
                {"KH000003", "Tuấn - Hà Nội", "0123123123", 1243000},
                {"KH000002", "Phạm Thu Hương", "0456456456", 1358000},
                {"KH000001", "Nguyễn Văn Hải", "0789789789", 656000}
        };

        // Create table
        khachHangTable = new CustomTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(khachHangTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Add selection listener to update right panel when customer is selected
        khachHangTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && khachHangTable.getSelectedRow() != -1) {
                    // TODO: Implement action when customer selection changes
                    // String customerId = khachHangTable.getValueAt(khachHangTable.getSelectedRow(), 0).toString();
                    // updateInvoiceTable(customerId);
                    updateInvoiceTable(""); // Clear or show placeholder
                }
            }
        });

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
        // For now, just clear the table or show a placeholder message

        Object[][] emptyData;
        if (customerId == null || customerId.isEmpty()) {
            emptyData = new Object[][] {}; // Empty table
        } else {
            // Placeholder while actual data loading isn't implemented
            emptyData = new Object[][] {
                    {"", "Đang tải dữ liệu...", "", "", ""}
            };
            // In a real implementation, you would fetch data based on customerId here
        }

        // Update the invoice table with new data
        hoaDonTable.setModel(new DefaultTableModel(
                emptyData,
                new String[] {"Mã hóa đơn", "Ngày tạo", "Nhân viên bán", "Tổng tiền", "Trạng thái"}
        ));
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

    // Removed createRadioButton as it's no longer needed

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

