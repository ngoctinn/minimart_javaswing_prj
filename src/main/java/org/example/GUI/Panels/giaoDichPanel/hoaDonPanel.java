package org.example.GUI.Panels.giaoDichPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.GUI.MenuFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

public class hoaDonPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;
    
    // Top panel components
    private PlaceholderTextField searchField;
    private CustomButton searchButton, refreshButton, addButton, editButton, deleteButton, importButton, exportButton;
    
    // Bottom panel components
    private JSpinner startDateSpinner, endDateSpinner;
    private JRadioButton processingRadioButton, completedRadioButton;
    private JList<String> userList;
    private CustomTable hoaDonTable;
    private CustomButton filterDateButton;

    public hoaDonPanel() {
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
        JLabel title = new JLabel("Hoá Đơn");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));
        
        // Search field
        searchField = new PlaceholderTextField("Nhập mã hoá đơn cần tìm");
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
        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuFrame menuFrame = new MenuFrame();
                menuFrame.showPanel("banHang");
            }
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
        deleteButton = new CustomButton("Hủy", deleteIcon);
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

    //======================CÀI ĐẶT PANEL DƯỚI TRÁI=================================
    private void setupBottomPanelLeft() {
        setupDateFilterPanel();
        setupStatusFilterPanel();
        setupUserFilterPanel();
    }

    private void setupDateFilterPanel() {
        JPanel datePanel = createTitledPanel("Thời Gian", 230, 180);
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(datePanel);

        // Date picker for start and end dates
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner = new JSpinner(new SpinnerDateModel());

        // Set date format
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy");
        JSpinner.DateEditor endEditor = new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy");
        startDateSpinner.setEditor(startEditor);
        endDateSpinner.setEditor(endEditor);

        // Set preferred size
        startDateSpinner.setPreferredSize(new Dimension(200, 30));
        endDateSpinner.setPreferredSize(new Dimension(200, 30));

        // Add components to the date panel
        datePanel.add(Box.createVerticalStrut(1));
        datePanel.add(startDateSpinner);
        datePanel.add(Box.createVerticalStrut(1));
        datePanel.add(endDateSpinner);

        // Filter button
        filterDateButton = new CustomButton("Lọc");
        filterDateButton.setPreferredSize(new Dimension(100, 30));
        filterDateButton.setMaximumSize(new Dimension(100, 30));
        filterDateButton.setButtonColors(CustomButton.ButtonColors.BLUE);

        datePanel.add(Box.createVerticalStrut(5));
        datePanel.add(filterDateButton);
    }

    private void setupStatusFilterPanel() {
        JPanel statusPanel = createTitledPanel("Trạng Thái", 230, 100);
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(statusPanel);

        processingRadioButton = createRadioButton("Đang Xử Lý");
        completedRadioButton = createRadioButton("Hoàn Thành");
        
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(processingRadioButton);
        statusGroup.add(completedRadioButton);
        
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.setBackground(Color.WHITE);
        radioPanel.add(processingRadioButton);
        radioPanel.add(completedRadioButton);
        
        statusPanel.add(Box.createVerticalStrut(5));
        statusPanel.add(radioPanel);
        statusPanel.add(Box.createVerticalStrut(5));
    }

    private void setupUserFilterPanel() {
        JPanel userPanel = createTitledPanel("Người Tạo", 230, 180);
        userPanel.setLayout(new BorderLayout(5, 5));
        bottomPanelLeft.add(userPanel);

        userList = new JList<>(new String[]{"Nguyễn Đức Tây", "Nguyễn Ngọc Tín", "Nguyễn Thị Tuyết Thư","Đinh Hữu An","Ngô Gia Khang","Nguyễn Văn A","Nguyễn Văn B","Nguyễn Văn C"});
        userList.setLayoutOrientation(JList.VERTICAL);
        userList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userList.setVisibleRowCount(6);

        JScrollPane listScroller = new JScrollPane(userList);
        listScroller.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        userPanel.add(listScroller, BorderLayout.CENTER);
    }

    //======================CÀI ĐẶT PANEL DƯỚI PHẢI=================================
    private void setupBottomPanelRight() {
        // Change layout to BorderLayout
        bottomPanelRight.setLayout(new BorderLayout(0, 10));

        // Upper part - Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Table data
        String[] columnNames = {"Mã hóa đơn", "Thời gian", "Khách hàng", "Tổng tiền hàng", "Giảm giá", "Khách đã trả"};
        Object[][] data = {
                {"HD000046", "17/02/2025 23:36", "Tuấn - Hà Nội", 2580000, 0, 2580000},
                {"HD000045", "16/02/2025 23:35", "Phạm Thu Hương", 55000, 0, 55000},
                {"HD000044", "15/02/2025 23:35", "Tuấn - Hà Nội", 190000, 0, 190000},
                {"HD000043", "14/02/2025 23:33", "Anh Hoàng - Sài Gòn", 195000, 0, 195000},
                {"HD000042", "13/02/2025 23:32", "Anh Hoàng - Sài Gòn", 145000, 0, 145000},
                {"HD000041", "12/02/2025 23:31", "Tuấn - Hà Nội", 150000, 0, 150000},
                {"HD000040", "11/02/2025 23:30", "Anh Hoàng - Sài Gòn", 135000, 0, 135000},
                {"HD000039", "10/02/2025 23:30", "Phạm Thu Hương", 190000, 0, 190000},
                {"HD000038", "09/02/2025 23:29", "Anh Hoàng - Sài Gòn", 55000, 0, 55000},
                {"HD000037", "08/02/2025 23:29", "Anh Hoàng - Sài Gòn", 55000, 0, 55000},
                {"HD000036", "07/02/2025 23:26", "Tuấn - Hà Nội", 275000, 0, 275000},
                {"HD000035", "06/02/2025 23:25", "Phạm Thu Hương", 95000, 0, 95000},
                {"HD000034", "05/02/2025 23:24", "Phạm Thu Hương", 110000, 0, 110000},
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        hoaDonTable = new CustomTable(model);
        JScrollPane tableScrollPane = new JScrollPane(hoaDonTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add panels to bottomPanelRight
        bottomPanelRight.add(tablePanel, BorderLayout.CENTER);
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

    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        radioButton.setBackground(Color.WHITE);
        return radioButton;
    }

    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new hoaDonPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
