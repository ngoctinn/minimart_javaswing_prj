package org.example.GUI.Panels.doiTacPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.GUI.Dialogs.ThemKhachHangDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;


public class KhachHangPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;
    
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
    private JSpinner startDateSpinner, endDateSpinner;
    private JList<String> nhomKhachHangList;
    private JList<String> nguoiTaoList;
    private CustomTable khachHangTable;
    private CustomButton filterDateButton;

    public KhachHangPanel() {
        initGUI();
    }

    public void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanelLeft();
        setupBottomPanelRight();

        // Add panels to main panel with proper constraints
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);
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
        
        // Add components to the top panel
        topPanel.add(titlePanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(actionPanel, BorderLayout.EAST);
        
        // Action buttons will be added to actionPanel
        setupActionButtons(actionPanel);
    }

    private void setupActionButtons(JPanel actionPanel) {
        // Create a panel for the refresh button
        JPanel refreshPanel = new JPanel();
        refreshPanel.setLayout(new BoxLayout(refreshPanel, BoxLayout.X_AXIS));
        refreshPanel.setBackground(Color.WHITE);
        
        // Create a panel for the main action buttons
        JPanel mainButtonsPanel = new JPanel();
        mainButtonsPanel.setLayout(new BoxLayout(mainButtonsPanel, BoxLayout.X_AXIS));
        mainButtonsPanel.setBackground(Color.WHITE);
        
        // Create a panel for the import/export buttons
        JPanel importExportPanel = new JPanel();
        importExportPanel.setLayout(new BoxLayout(importExportPanel, BoxLayout.X_AXIS));
        importExportPanel.setBackground(Color.WHITE);
        
        // Refresh button
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setPreferredSize(new Dimension(50, 30));
        refreshButton.setMaximumSize(new Dimension(50, 30));
        refreshButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        refreshPanel.add(refreshButton);
        refreshPanel.add(Box.createHorizontalStrut(10));
        
        // Add button
        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg", 16, 16);
        addButton = new CustomButton("Thêm", addIcon);
        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.setMaximumSize(new Dimension(100, 30));
        addButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        addButton.addActionListener(e -> {
            ThemKhachHangDialog themKhachHangDialog = new ThemKhachHangDialog();
            themKhachHangDialog.setVisible(true);
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
        actionPanel.add(refreshPanel);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(mainButtonsPanel);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(importExportPanel);
    }

    private void setupBottomPanelLeft() {
        setupDateFilterPanel();
        setupNhomKhachHangPanel();
        setupNguoiTaoPanel();
    }
    
    private void setupDateFilterPanel() {
        // Date filter panel
        JPanel datePanel = createTitledPanel("Ngày Tạo", 230, 170);
        datePanel.setLayout(null);
        bottomPanelLeft.add(datePanel);
        
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
        filterDateButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        datePanel.add(filterDateButton);
    }



    private void setupNhomKhachHangPanel() {
        // Nhóm khách hàng panel
        JPanel nhomKhachHangPanel = createTitledPanel("Nhóm Khách Hàng", 230, 210);
        nhomKhachHangPanel.setLayout(null);
        bottomPanelLeft.add(nhomKhachHangPanel);

        // Nhóm khách hàng list
        String[] nhomKhachHangData = {"Tây Bán Bom", "Tín Víp Pro", "Thư Bồ Tín", "An Má Bé Sol", "HURRYKHANG", "Jack Bỏ Con"};
        nhomKhachHangList = createScrollableList(nhomKhachHangData);
        JScrollPane scrollPane = createScrollPane(nhomKhachHangList, 200, 130);
        scrollPane.setBounds(15, 25, 200, 130);
        nhomKhachHangPanel.add(scrollPane);

        // Button to add customer group could be added here if needed
    }

    private void setupNguoiTaoPanel() {
        // Người tạo panel
        JPanel nguoiTaoPanel = createTitledPanel("Người Tạo", 230, 210);
        nguoiTaoPanel.setLayout(null);
        bottomPanelLeft.add(nguoiTaoPanel);

        // Người tạo list
        String[] nguoiTaoData = {"Tây Bán Bom", "Tín Víp Pro", "Thư Bồ Tín", "An Má Bé Sol", "HURRYKHANG", "Jack Bỏ Con"};
        nguoiTaoList = createScrollableList(nguoiTaoData);
        JScrollPane scrollPane = createScrollPane(nguoiTaoList, 200, 130);
        scrollPane.setBounds(15, 25, 200, 130);
        nguoiTaoPanel.add(scrollPane);

        // Button to add creator could be added here if needed
    }

    private void setupBottomPanelRight() {
        // Table data
        String[] columnNames = {"Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại", "Tổng Bán"};
        Object[][] data = {
                {"KH000005", "Anh Giang - Kim Mã", "", 1858000},
                {"KH000004", "Anh Hoàng - Sài Gòn", "", 1286000},
                {"KH000003", "Tuấn - Hà Nội", "", 1243000},
                {"KH000002", "Phạm Thu Hương", "", 1358000},
                {"KH000001", "Nguyễn Văn Hải", "", 656000}
        };

        // Create table
        khachHangTable = new CustomTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(khachHangTable);
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
