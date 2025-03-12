package org.example.GUI.Panels.giaoDichPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.GUI.Dialogs.ThemHangHoaDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class nhapHangPanel extends JPanel {
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
    private JSpinner startSpinner;
    private JSpinner endSpinner;
    private JRadioButton radioPhieuTam;
    private JRadioButton radioDaNhapHang;
    private JRadioButton radioDaHuy;
    private JRadioButton radioTatCa;
    private JList<String> nguoiNhapList;
    private JList<String> nguoiTaoList;
    private CustomTable nhapHangTable;

    public nhapHangPanel() {
        initGUI();
    }

    public void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanelLeft();
        setupBottomPanelRight();

        // Add panels to main panel
        this.add(topPanel);
        this.add(bottomPanel);
        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);
    }

    private void setupMainPanel() {
        // Set up layout and background for main panel
        this.setLayout(new FlowLayout());
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
        topPanel.setPreferredSize(new Dimension(1270, 50));
        bottomPanel.setPreferredSize(new Dimension(1270, 900));

        // Set layouts
        topPanel.setLayout(null);
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new FlowLayout());
        bottomPanelRight.setLayout(new BoxLayout(bottomPanelRight, BoxLayout.Y_AXIS));

        // Set sizes for bottom panels
        bottomPanelLeft.setPreferredSize(new Dimension(1270 * 20 / 100, 900));
        bottomPanelRight.setPreferredSize(new Dimension(1270 * 80 / 100, 900));
    }

    private void setupTopPanel() {
        // Title
        JLabel title = new JLabel("Phiếu Nhập Hàng");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        title.setBounds(10, 10, 220, 30);
        topPanel.add(title);

        // Search field
        searchField = new PlaceholderTextField("Nhập phiếu nhập hàng cần tìm");
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchField.setBounds(270, 12, 300, 30);
        topPanel.add(searchField);

        // Search button
        searchButton = new CustomButton("Tìm");
        searchButton.setBounds(580, 12, 70, 30);
        topPanel.add(searchButton);

        // Refresh button
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setBounds(900, 12, 50, 30);
        refreshButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        topPanel.add(refreshButton);

        // Add button
        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg", 16, 16);
        addButton = new CustomButton("", addIcon);
        addButton.setBounds(960, 12, 50, 30);
        addButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        topPanel.add(addButton);

        // Add button event
        addButton.addActionListener(e -> {
            // Handle add button click
            new ThemHangHoaDialog();
        });

        // Edit button
        FlatSVGIcon editIcon = new FlatSVGIcon("Icons/edit.svg", 20, 20);
        editButton = new CustomButton("", editIcon);
        editButton.setBounds(1020, 12, 50, 30);
        editButton.setButtonColors(CustomButton.ButtonColors.YELLOW);
        topPanel.add(editButton);

        // Delete button
        FlatSVGIcon deleteIcon = new FlatSVGIcon("Icons/delete.svg", 20, 20);
        deleteButton = new CustomButton("", deleteIcon);
        deleteButton.setBounds(1080, 12, 50, 30);
        deleteButton.setButtonColors(CustomButton.ButtonColors.RED);
        topPanel.add(deleteButton);

        // Export button
        FlatSVGIcon exportIcon = new FlatSVGIcon("Icons/excel.svg", 16, 16);
        exportButton = new CustomButton("", exportIcon);
        exportButton.setBounds(1140, 12, 50, 30);
        exportButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        topPanel.add(exportButton);

        // Import button
        FlatSVGIcon importIcon = new FlatSVGIcon("Icons/import.svg", 16, 16);
        importButton = new CustomButton("", importIcon);
        importButton.setBounds(1200, 12, 50, 30);
        importButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        topPanel.add(importButton);
    }

    private void setupBottomPanelLeft() {
        // Time panel
        JPanel timePanel = createTitledPanel("Thời Gian", 230, 100);
        bottomPanelLeft.add(timePanel);

        // Start date
        JLabel startLabel = new JLabel("Ngày bắt đầu:");
        SpinnerDateModel startModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        startSpinner = new JSpinner(startModel);
        startSpinner.setEditor(new JSpinner.DateEditor(startSpinner, "dd/MM/yyyy"));

        // End date
        JLabel endLabel = new JLabel("Ngày kết thúc:");
        SpinnerDateModel endModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        endSpinner = new JSpinner(endModel);
        endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, "dd/MM/yyyy"));

        // Add to panel
        timePanel.add(startLabel);
        timePanel.add(startSpinner);
        timePanel.add(endLabel);
        timePanel.add(endSpinner);

        // Status panel
        JPanel statusPanel = createTitledPanel("Trạng Thái", 230, 150);
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(statusPanel);

        // Radio buttons
        radioPhieuTam = new JRadioButton("Phiếu Tạm");
        radioPhieuTam.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        radioDaNhapHang = new JRadioButton("Đã Nhập Hàng");
        radioDaNhapHang.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        radioDaHuy = new JRadioButton("Đã Hủy");
        radioDaHuy.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        radioTatCa = new JRadioButton("Tất Cả");
        radioTatCa.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        // Group radio buttons
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(radioPhieuTam);
        statusGroup.add(radioDaNhapHang);
        statusGroup.add(radioDaHuy);
        statusGroup.add(radioTatCa);

        // Add to panel
        statusPanel.add(radioPhieuTam);
        statusPanel.add(radioDaNhapHang);
        statusPanel.add(radioDaHuy);
        statusPanel.add(radioTatCa);

        // Người Nhập panel
        JPanel nguoiNhapPanel = createTitledPanel("Người Nhập", 230, 150);
        nguoiNhapPanel.setLayout(new BoxLayout(nguoiNhapPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(nguoiNhapPanel);

        // Người Nhập list
        nguoiNhapList = new JList<>(new String[]{"Tây Bán Bom", "Tín Víp Pro", "Thư Bồ Tín", "An Má Bé Sol", "HURRYKHANG", "Jack Bỏ Con"});
        nguoiNhapList.setLayoutOrientation(JList.VERTICAL);
        nguoiNhapList.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JScrollPane nguoiNhapScroller = new JScrollPane(nguoiNhapList);
        nguoiNhapScroller.setBorder(BorderFactory.createEmptyBorder());
        nguoiNhapScroller.setPreferredSize(new Dimension(200, 150));
        nguoiNhapPanel.add(nguoiNhapScroller);

        // Người Tạo panel
        JPanel nguoiTaoPanel = createTitledPanel("Người Tạo", 230, 150);
        nguoiTaoPanel.setLayout(new BoxLayout(nguoiTaoPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(nguoiTaoPanel);

        // Người Tạo list
        nguoiTaoList = new JList<>(new String[]{"Tây Bán Bom", "Tín Víp Pro", "Thư Bồ Tín", "An Má Bé Sol", "HURRYKHANG", "Jack Bỏ Con"});
        nguoiTaoList.setLayoutOrientation(JList.VERTICAL);
        nguoiTaoList.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JScrollPane nguoiTaoScroller = new JScrollPane(nguoiTaoList);
        nguoiTaoScroller.setBorder(BorderFactory.createEmptyBorder());
        nguoiTaoScroller.setPreferredSize(new Dimension(200, 150));
        nguoiTaoPanel.add(nguoiTaoScroller);
    }

    private void setupBottomPanelRight() {
        // Table columns
        String[] columnNames = {"Mã Nhập Hàng", "Thời Gian", "Nhà Cung Cấp"};

        // Table data
        Object[][] data = {};

        // Create table
        nhapHangTable = new CustomTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(nhapHangTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        bottomPanelRight.add(tableScrollPane);
    }

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

    // Main method for testing
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new nhapHangPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
