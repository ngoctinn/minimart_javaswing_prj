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


public class khachHangPanel extends JPanel {
    // UI Components
    private PlaceholderTextField searchField;
    private CustomButton searchButton, refreshButton, addButton, editButton, deleteButton, importButton, exportButton;
    private JSpinner startDateSpinner, endDateSpinner;
    private JList<String> nhomKhachHangList;
    private JList<String> nguoiTaoList;
    private CustomTable khachHangTable;
    private CustomButton filterDateButton;

    public khachHangPanel() {
        initGUI();
    }

    public void initGUI() {
        setupMainPanel();

        // Create sub-panels
        RoundedPanel topPanel = createTopPanel();
        RoundedPanel bottomPanel = createBottomPanel();

        // Add panels to main panel
        this.add(topPanel);
        this.add(bottomPanel);
    }

    private void setupMainPanel() {
        this.setLayout(new FlowLayout());
        this.setBackground(new Color(225, 225, 225));
        this.setVisible(true);
    }

    private RoundedPanel createTopPanel() {
        RoundedPanel topPanel = new RoundedPanel(20);
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(1270, 50));
        topPanel.setLayout(null);

        // Add components to top panel
        addTitleToPanel(topPanel);
        addSearchComponentsToPanel(topPanel);
        addActionButtonsToPanel(topPanel);

        return topPanel;
    }

    private void addTitleToPanel(RoundedPanel panel) {
        JLabel title = new JLabel("Khách Hàng");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        title.setBounds(10, 10, 220, 30);
        panel.add(title);
    }

    private void addSearchComponentsToPanel(RoundedPanel panel) {
        searchField = new PlaceholderTextField("Nhập khách hàng cần tìm");
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchField.setBounds(270, 12, 300, 30);
        panel.add(searchField);

        searchButton = new CustomButton("Tìm");
        searchButton.setBounds(580, 12, 70, 30);
        panel.add(searchButton);
    }

    private void addActionButtonsToPanel(RoundedPanel panel) {
        // Refresh button
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setBounds(660, 12, 50, 30);
        refreshButton.setButtonColors(CustomButton.ButtonColors.BLUE);  // Changed from GRAY to BLUE
        panel.add(refreshButton);

        // Add button - Added text and adjusted size
        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg", 16, 16);
        addButton = new CustomButton("Thêm", addIcon);  // Added text label
        addButton.setBounds(820, 12, 100, 30);  // Increased width from 50 to 100
        addButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        addButton.addActionListener(e -> {
            ThemKhachHangDialog themKhachHangDialog = new ThemKhachHangDialog();
            themKhachHangDialog.setVisible(true);
        });
        panel.add(addButton);

        // Edit button - Added text and adjusted size
        FlatSVGIcon editIcon = new FlatSVGIcon("Icons/edit.svg", 20, 20);
        editButton = new CustomButton("Sửa", editIcon);  // Added text label
        editButton.setBounds(930, 12, 100, 30);  // Increased width from 50 to 100
        editButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        panel.add(editButton);

        // Delete button - Added text and adjusted size
        FlatSVGIcon deleteIcon = new FlatSVGIcon("Icons/delete.svg", 20, 20);
        deleteButton = new CustomButton("Xóa", deleteIcon);  // Added text label
        deleteButton.setBounds(1040, 12, 100, 30);  // Increased width from 50 to 100
        deleteButton.setButtonColors(CustomButton.ButtonColors.RED);
        panel.add(deleteButton);

        // Export button
        FlatSVGIcon exportIcon = new FlatSVGIcon("Icons/excel.svg", 16, 16);
        exportButton = new CustomButton("", exportIcon);
        exportButton.setBounds(1150, 12, 50, 30);
        exportButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        panel.add(exportButton);

        // Import button
        FlatSVGIcon importIcon = new FlatSVGIcon("Icons/import.svg", 16, 16);
        importButton = new CustomButton("", importIcon);
        importButton.setBounds(1210, 12, 50, 30);
        importButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        panel.add(importButton);
    }

    private RoundedPanel createBottomPanel() {
        RoundedPanel bottomPanel = new RoundedPanel(20);
        bottomPanel.setBackground(new Color(225, 225, 225));
        bottomPanel.setPreferredSize(new Dimension(1270, 900));
        bottomPanel.setLayout(new BorderLayout(5, 0));

        RoundedPanel bottomPanelLeft = createFilterPanel();
        RoundedPanel bottomPanelRight = createTablePanel();

        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);

        return bottomPanel;
    }

    private RoundedPanel createFilterPanel() {
        RoundedPanel filterPanel = new RoundedPanel(20);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filterPanel.setPreferredSize(new Dimension(1270 * 20 / 100, 900));

        // Add filter components
        filterPanel.add(createDateFilterPanel());
        filterPanel.add(createNhomKhachHangPanel());
        filterPanel.add(createNguoiTaoPanel());

        return filterPanel;
    }

    private JPanel createDateFilterPanel() {
        JPanel datePanel = new JPanel();
        datePanel.setBackground(Color.WHITE);
        datePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Ngày Tạo",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));
        datePanel.setPreferredSize(new Dimension(220, 170));
        datePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // Start date components
        JLabel startLabel = new JLabel("Ngày bắt đầu:");
        startLabel.setPreferredSize(new Dimension(200, 20));
        startLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        SpinnerDateModel startModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        startDateSpinner = new JSpinner(startModel);
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy"));
        startDateSpinner.setPreferredSize(new Dimension(200, 25));

        // End date components
        JLabel endLabel = new JLabel("Ngày kết thúc:");
        endLabel.setPreferredSize(new Dimension(200, 20));
        endLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        SpinnerDateModel endModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        endDateSpinner = new JSpinner(endModel);
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy"));
        endDateSpinner.setPreferredSize(new Dimension(200, 25));

        // Filter button
        filterDateButton = new CustomButton("Lọc");
        filterDateButton.setPreferredSize(new Dimension(195, 20));
        filterDateButton.setButtonColors(CustomButton.ButtonColors.GRAY);

        datePanel.add(startLabel);
        datePanel.add(startDateSpinner);
        datePanel.add(endLabel);
        datePanel.add(endDateSpinner);
        datePanel.add(filterDateButton);

        return datePanel;
    }

    private JPanel createNhomKhachHangPanel() {
        JPanel nhomKhachHangPanel = new JPanel();
        nhomKhachHangPanel.setBackground(Color.WHITE);
        nhomKhachHangPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Nhóm Khách Hàng",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));
        nhomKhachHangPanel.setLayout(new BorderLayout(5, 5));
        nhomKhachHangPanel.setPreferredSize(new Dimension(220, 180));

        nhomKhachHangList = new JList<>(new String[]{"Tây Bán Bom", "Tín Víp Pro", "Thư Bồ Tín", "An Má Bé Sol", "HURRYKHANG", "Jack Bỏ Con"});
        nhomKhachHangList.setLayoutOrientation(JList.VERTICAL);
        nhomKhachHangList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nhomKhachHangList.setVisibleRowCount(6);

        JScrollPane listScroller = new JScrollPane(nhomKhachHangList);
        listScroller.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        nhomKhachHangPanel.add(listScroller, BorderLayout.CENTER);

        return nhomKhachHangPanel;
    }

    private JPanel createNguoiTaoPanel() {
        JPanel nguoiTaoPanel = new JPanel();
        nguoiTaoPanel.setBackground(Color.WHITE);
        nguoiTaoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Người Tạo",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));
        nguoiTaoPanel.setLayout(new BorderLayout(5, 5));
        nguoiTaoPanel.setPreferredSize(new Dimension(220, 180));

        nguoiTaoList = new JList<>(new String[]{"Tây Bán Bom", "Tín Víp Pro", "Thư Bồ Tín", "An Má Bé Sol", "HURRYKHANG", "Jack Bỏ Con"});
        nguoiTaoList.setLayoutOrientation(JList.VERTICAL);
        nguoiTaoList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nguoiTaoList.setVisibleRowCount(6);

        JScrollPane listScroller = new JScrollPane(nguoiTaoList);
        listScroller.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        nguoiTaoPanel.add(listScroller, BorderLayout.CENTER);

        return nguoiTaoPanel;
    }

    private RoundedPanel createTablePanel() {
        RoundedPanel tablePanel = new RoundedPanel(20);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setPreferredSize(new Dimension(1270 * 80 / 100, 900));

        // Create table with data
        String[] columnNames = {"Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại", "Tổng Bán"};
        Object[][] data = {
                {"KH000005", "Anh Giang - Kim Mã", "", 1858000},
                {"KH000004", "Anh Hoàng - Sài Gòn", "", 1286000},
                {"KH000003", "Tuấn - Hà Nội", "", 1243000},
                {"KH000002", "Phạm Thu Hương", "", 1358000},
                {"KH000001", "Nguyễn Văn Hải", "", 656000}
        };

        khachHangTable = new CustomTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(khachHangTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        tablePanel.add(tableScrollPane);

        return tablePanel;
    }

    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new khachHangPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
