package org.example.GUI.Panels.hangHoaPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.GUI.Dialogs.ThemHangHoaDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DanhMucPanel extends JPanel {
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
    private JList<String> nhomHangList;
    private JList<String> nhaCungCapList;
    private JRadioButton radioTatCa;
    private JRadioButton radioHangDangKinhDoanh;
    private JRadioButton radioHangNgungKinhDoanh;
    private CustomTable hangHoaTable;

    public DanhMucPanel() {
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
        JLabel title = new JLabel("Danh mục hàng hoá");
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
        addButton.addActionListener(e -> new ThemHangHoaDialog());
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
        setupNhomHangPanel();
        setupNhaCungCapPanel();
        setupLuaChonHienThiPanel();
    }

    private void setupNhomHangPanel() {
        // Nhóm hàng panel
        JPanel nhomHangPanel = createTitledPanel("Nhóm hàng", 230, 200);
        bottomPanelLeft.add(nhomHangPanel);

        // Nhóm hàng list
        String[] nhomHangData = {"Nhóm 1", "Nhóm 2", "Nhóm 3", "Nhóm 4", "Nhóm 5", "Nhóm 6", "Nhóm 7", "Nhóm 8",
                "Nhóm 9", "Nhóm 10", "Nhóm 11", "Nhóm 12", "Nhóm 13", "Nhóm 14", "Nhóm 15",
                "Nhóm 16", "Nhóm 17", "Nhóm 18", "Nhóm 19", "Nhóm 20"};
        nhomHangList = createScrollableList(nhomHangData);
        nhomHangPanel.add(createScrollPane(nhomHangList, 200, 150));
    }

    private void setupNhaCungCapPanel() {
        // Nhà cung cấp panel
        JPanel nhaCungCapPanel = createTitledPanel("Nhà cung cấp", 230, 200);
        bottomPanelLeft.add(nhaCungCapPanel);

        // Nhà cung cấp list
        String[] nhaCungCapData = {"Nhà cung cấp 1", "Nhà cung cấp 2", "Nhà cung cấp 3", "Nhà cung cấp 4",
                "Nhà cung cấp 5", "Nhà cung cấp 6", "Nhà cung cấp 7", "Nhà cung cấp 8",
                "Nhà cung cấp 9", "Nhà cung cấp 10", "Nhà cung cấp 11", "Nhà cung cấp 12",
                "Nhà cung cấp 13", "Nhà cung cấp 14", "Nhà cung cấp 15", "Nhà cung cấp 16",
                "Nhà cung cấp 17", "Nhà cung cấp 18", "Nhà cung cấp 19", "Nhà cung cấp 20"};
        nhaCungCapList = createScrollableList(nhaCungCapData);
        nhaCungCapPanel.add(createScrollPane(nhaCungCapList, 200, 150));
    }

    private void setupLuaChonHienThiPanel() {
        // Lựa chọn hiển thị panel
        JPanel luaChonPanel = createTitledPanel("Lựa chọn hiển thị", 230, 110);
        luaChonPanel.setLayout(new BoxLayout(luaChonPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(luaChonPanel);

        // Radio buttons
        radioTatCa = createRadioButton("Tất cả");
        radioHangDangKinhDoanh = createRadioButton("Hàng đang kinh doanh");
        radioHangNgungKinhDoanh = createRadioButton("Hàng ngừng kinh doanh");

        // Group radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioTatCa);
        buttonGroup.add(radioHangDangKinhDoanh);
        buttonGroup.add(radioHangNgungKinhDoanh);

        // Add to panel
        luaChonPanel.add(radioTatCa);
        luaChonPanel.add(radioHangDangKinhDoanh);
        luaChonPanel.add(radioHangNgungKinhDoanh);
    }

    private void setupBottomPanelRight() {
        // Table data
        String[] columnNames = {"Mã hàng", "Tên hàng", "Nhóm hàng", "Nhà cung cấp", "Giá bán", "Giá vốn", "Tồn kho", "Khách đặt"};
        Object[][] data = {
                {"SP000025", "Hộp phở bò", "Nhóm 1", "Nhà cung cấp 1", 10000, 8000, 192, 0},
                {"SP000024", "Mì bò hầm", "Nhóm 2", "Nhà cung cấp 2", 15000, 14000, 0, 0},
                {"SP000023", "Thịt bò khô", "Nhóm 3", "Nhà cung cấp 3", 45000, 44000, 0, 0},
        };

        // Create table
        hangHoaTable = new CustomTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(hangHoaTable);
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

    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return radioButton;
    }

    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new DanhMucPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
