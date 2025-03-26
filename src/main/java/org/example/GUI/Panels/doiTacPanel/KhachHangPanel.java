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
import java.awt.*;
import java.util.Calendar;
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
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JSpinner startDateSpinner, endDateSpinner;
    private JList<String> nguoiTaoList;
    private CustomTable khachHangTable;
    private CustomTable hoaDonTable;
    private CustomButton filterDateButton;
    private JRadioButton radioTatCa;
    private JRadioButton radioActive;
    private JRadioButton radioInactive;

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
        addButton.addActionListener(e -> {
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
        // Add filter panel at the top of left panel
        JPanel topLeftPanel = new JPanel(new BorderLayout());
        topLeftPanel.setBackground(Color.WHITE);

        // Setup filter panels
        JPanel filterPanel = createFilterPanel();
        JPanel dateFilterPanel = createDateFilterPanel();

        topLeftPanel.add(filterPanel, BorderLayout.NORTH);
        topLeftPanel.add(dateFilterPanel, BorderLayout.CENTER);

        // Create customer table panel
        JPanel customerTablePanel = createCustomerTablePanel();

        // Add to left panel
        leftPanel.add(topLeftPanel, BorderLayout.NORTH);
        leftPanel.add(customerTablePanel, BorderLayout.CENTER);
    }

    private void setupRightInvoicePanel() {
        // Create a title panel for the invoice list
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Lịch sử mua hàng");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 0));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        // Create invoice table panel
        JPanel invoiceTablePanel = createInvoiceTablePanel();

        // Add to right panel
        rightPanel.add(titlePanel, BorderLayout.NORTH);
        rightPanel.add(invoiceTablePanel, BorderLayout.CENTER);
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = createTitledPanel("Lựa chọn hiển thị", 0, 80);
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        // Radio buttons
        radioTatCa = createRadioButton("Tất cả");
        radioActive = createRadioButton("Đang hoạt động");
        radioInactive = createRadioButton("Không hoạt động");

        // Group radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioTatCa);
        buttonGroup.add(radioActive);
        buttonGroup.add(radioInactive);

        // Set default selection
        radioTatCa.setSelected(true);

        // Add to panel
        filterPanel.add(radioTatCa);
        filterPanel.add(radioActive);
        filterPanel.add(radioInactive);

        return filterPanel;
    }

    private JPanel createDateFilterPanel() {
        // Date filter panel
        JPanel datePanel = createTitledPanel("Ngày Tạo", 0, 170);
        datePanel.setLayout(null);
        
        // Start date components
        JLabel startLabel = new JLabel("Ngày bắt đầu:");
        startLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        startLabel.setBounds(15, 25, 200, 20);
        datePanel.add(startLabel);
        
        SpinnerDateModel startModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        startDateSpinner = new JSpinner(startModel);
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy"));
        startDateSpinner.setBounds(15, 45, 200, 25);
        datePanel.add(startDateSpinner);
        
        // End date components
        JLabel endLabel = new JLabel("Ngày kết thúc:");
        endLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        endLabel.setBounds(15, 75, 200, 20);
        datePanel.add(endLabel);
        
        SpinnerDateModel endModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        endDateSpinner = new JSpinner(endModel);
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy"));
        endDateSpinner.setBounds(15, 95, 200, 25);
        datePanel.add(endDateSpinner);
        
        // Filter button
        filterDateButton = new CustomButton("Lọc");
        filterDateButton.setBounds(15, 130, 200, 25);
        filterDateButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        datePanel.add(filterDateButton);
        
        return datePanel;
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
                    updateInvoiceTable(khachHangTable.getValueAt(khachHangTable.getSelectedRow(), 0).toString());
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
        // This method is called when a customer is selected
        // For demonstration, we'll populate with example data based on the selected customer

        Object[][] invoiceData;

        // Example data based on customer ID
        switch (customerId) {
            case "KH000001":
                invoiceData = new Object[][] {
                        {"HD000001", "12/05/2023", "Nguyễn Văn A", 256000, "Đã thanh toán"},
                        {"HD000002", "18/06/2023", "Trần Thị B", 400000, "Đã thanh toán"}
                };
                break;
            case "KH000002":
                invoiceData = new Object[][] {
                        {"HD000003", "05/03/2023", "Nguyễn Văn A", 358000, "Đã thanh toán"},
                        {"HD000004", "27/04/2023", "Lê Văn C", 500000, "Đã thanh toán"},
                        {"HD000005", "14/07/2023", "Trần Thị B", 500000, "Đã thanh toán"}
                };
                break;
            case "KH000003":
                invoiceData = new Object[][] {
                        {"HD000006", "10/01/2023", "Lê Văn C", 843000, "Đã thanh toán"},
                        {"HD000007", "22/02/2023", "Nguyễn Văn A", 400000, "Đã thanh toán"}
                };
                break;
            case "KH000004":
                invoiceData = new Object[][] {
                        {"HD000008", "15/04/2023", "Trần Thị B", 586000, "Đã thanh toán"},
                        {"HD000009", "28/05/2023", "Lê Văn C", 700000, "Đã thanh toán"}
                };
                break;
            case "KH000005":
                invoiceData = new Object[][] {
                        {"HD000010", "03/02/2023", "Nguyễn Văn A", 458000, "Đã thanh toán"},
                        {"HD000011", "19/03/2023", "Trần Thị B", 700000, "Đã thanh toán"},
                        {"HD000012", "30/06/2023", "Lê Văn C", 700000, "Đã thanh toán"}
                };
                break;
            default:
                invoiceData = new Object[][] {
                        {"", "Chọn khách hàng để xem lịch sử mua hàng", "", "", ""}
                };
        }

        // Update the invoice table with new data
        hoaDonTable.setModel(new javax.swing.table.DefaultTableModel(
                invoiceData,
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
        
        if (width > 0 && height > 0) {
            panel.setPreferredSize(new Dimension(width, height));
        }
        
        return panel;
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        radioButton.setBackground(Color.WHITE);
        return radioButton;
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
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new KhachHangPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}

