package org.example.GUI.Panels.hangHoaPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.GUI.Dialogs.ThemSanPhamDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;


public class ThietLapGiaPanel extends JPanel {
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;
    private PlaceholderTextField searchField;
    private CustomButton searchButton;
    private CustomButton refreshButton;
    private CustomButton addButton;
    private CustomButton editButton;
    private CustomButton deleteButton;
    private CustomButton importButton;
    private CustomButton exportButton;
    private JList<String> nhomHangList;

    public ThietLapGiaPanel() {
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
        bottomPanelLeft.setPreferredSize(new Dimension(1270 * 20 / 100, 900));
        bottomPanelRight.setPreferredSize(new Dimension(1270 * 80 / 100, 900));

        // Set layouts
        topPanel.setLayout(null);
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new FlowLayout());
        bottomPanelRight.setLayout(new BoxLayout(bottomPanelRight, BoxLayout.Y_AXIS));
    }

    private void setupTopPanel() {
        // Title label
        JLabel title = new JLabel("Thiếp Lập Giá");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        title.setBounds(10, 10, 220, 30);
        topPanel.add(title);

        // Search field
        searchField = new PlaceholderTextField("Nhập tên sản phẩm cần tìm");
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchField.setBounds(270, 12, 300, 30);
        topPanel.add(searchField);

        // Search button
        searchButton = new CustomButton("Tìm");
        searchButton.setBounds(580, 12, 70, 30);
        topPanel.add(searchButton);

        // Action buttons
        setupActionButtons();
    }

    private void setupActionButtons() {
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
        setupThoiGianPanel();
        setupTonKhoPanel();
        setupNhomHangPanel();
        setupGiaBanPanel();
    }

    private void setupThoiGianPanel() {
        JPanel thoiGianPanel = createTitledPanel("Thời Gian", 230, 100);
        bottomPanelLeft.add(thoiGianPanel);

        JLabel startLabel = new JLabel("Ngày bắt đầu:");
        SpinnerDateModel startModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner startSpinner = new JSpinner(startModel);
        startSpinner.setEditor(new JSpinner.DateEditor(startSpinner, "dd/MM/yyyy"));

        JLabel endLabel = new JLabel("Ngày kết thúc:");
        SpinnerDateModel endModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner endSpinner = new JSpinner(endModel);
        endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, "dd/MM/yyyy"));

        thoiGianPanel.add(startLabel);
        thoiGianPanel.add(startSpinner);
        thoiGianPanel.add(endLabel);
        thoiGianPanel.add(endSpinner);
    }

    private void setupTonKhoPanel() {
        JPanel tonKhoPanel = createTitledPanel("Tồn Kho", 230, 100);
        tonKhoPanel.setLayout(new BoxLayout(tonKhoPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(tonKhoPanel);

        JRadioButton radioButtonshow1 = new JRadioButton("Còn Hàng Trong Kho");
        radioButtonshow1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JRadioButton radioButtonshow2 = new JRadioButton("Hết Hàng Trong Kho");
        radioButtonshow2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JRadioButton radioButtonshow3 = new JRadioButton("Tất Cả");
        radioButtonshow3.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        ButtonGroup buttonGroupshow = new ButtonGroup();
        buttonGroupshow.add(radioButtonshow1);
        buttonGroupshow.add(radioButtonshow2);
        buttonGroupshow.add(radioButtonshow3);

        tonKhoPanel.add(radioButtonshow1);
        tonKhoPanel.add(radioButtonshow2);
        tonKhoPanel.add(radioButtonshow3);
    }

    private void setupNhomHangPanel() {
        JPanel nhomHangPanel = createTitledPanel("Nhóm Hàng", 230, 150);
        nhomHangPanel.setLayout(new BoxLayout(nhomHangPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(nhomHangPanel);

        String[] nhomHangData = {"Thuốc Lá", "Sữa", "Nước Ngọt", "Kẹo", "Mỹ Phẩm", "Tất Cả"};
        nhomHangList = new JList<>(nhomHangData);
        nhomHangList.setLayoutOrientation(JList.VERTICAL);
        nhomHangList.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JScrollPane listScroller = new JScrollPane(nhomHangList);
        listScroller.setBorder(BorderFactory.createEmptyBorder());
        listScroller.setPreferredSize(new Dimension(200, 150));
        nhomHangPanel.add(listScroller);
    }

    private void setupGiaBanPanel() {
        JPanel giaBanPanel = createTitledPanel("Giá Bán", 230, 130);
        giaBanPanel.setLayout(new BoxLayout(giaBanPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(giaBanPanel);

        JRadioButton radioButtonshow4 = new JRadioButton("Thấp Hơn Hoặc Bằng 10k");
        radioButtonshow4.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JRadioButton radioButtonshow5 = new JRadioButton("Thấp Hơn Hoặc Bằng 100k");
        radioButtonshow5.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JRadioButton radioButtonshow6 = new JRadioButton("Thấp Hơn Hoặc Bằng 1 Triệu");
        radioButtonshow6.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JRadioButton radioButtonshow7 = new JRadioButton("Tất Cả");
        radioButtonshow7.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        ButtonGroup buttonGroupshow1 = new ButtonGroup();
        buttonGroupshow1.add(radioButtonshow4);
        buttonGroupshow1.add(radioButtonshow5);
        buttonGroupshow1.add(radioButtonshow6);
        buttonGroupshow1.add(radioButtonshow7);

        giaBanPanel.add(radioButtonshow4);
        giaBanPanel.add(radioButtonshow5);
        giaBanPanel.add(radioButtonshow6);
        giaBanPanel.add(radioButtonshow7);
    }

    private void setupBottomPanelRight() {
        // Tạo tiêu đề cột
        String[] columnNames = {"Mã Hàng", "Tên Hàng", "Giá Vốn", "Giá Nhập Cuối", "Bảng Giá Chung"};

        // Dữ liệu hóa đơn
        Object[][] data = {};
        CustomTable table = new CustomTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);
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

    // Hàm main để test giao diện
    public static void main(String[] args) {
        // dùng flatlaf OSMacOSLookAndFeel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new SanPhamPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
