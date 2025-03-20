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
    private JList<String> loaiSanPhamList;
    private JList<String> nhaCungCapList;
    private JRadioButton radioTatCa;
    private JRadioButton radioHangDangKinhDoanh;
    private JRadioButton radioHangNgungKinhDoanh;
    private CustomTable hangHoaTable;
    private CustomButton themLoaiSanPhamButton;
    private CustomButton themNhaCungCapButton;

    public DanhMucPanel() {
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
        // Set height for top panel
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
        JLabel title = new JLabel("Danh mục hàng hoá");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));
        
        // Search field
        searchField = new PlaceholderTextField("Nhập tên sản phẩm cần tìm");
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

        //Refresh button
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
        addButton.addActionListener(e ->
        {
            new ThemHangHoaDialog();
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

    private void setupBottomPanelLeft() {
        setupNhomHangPanel();
        setupNhaCungCapPanel();
        setupLuaChonHienThiPanel();
    }

    private void setupNhomHangPanel() {
        // Nhóm hàng panel
        JPanel loaiSanPhamPanel = createTitledPanel("Nhóm hàng", 230, 210);
        loaiSanPhamPanel.setLayout(null);
        bottomPanelLeft.add(loaiSanPhamPanel);

        // Nhóm hàng list
        String[] loaiSanPhamData = {"Bánh kẹo", "Thực phẩm khô", "Thực phẩm tươi", "Đồ uống","Bia","Đồ gia dụng", "Hàng hóa khác"};
        loaiSanPhamList = createScrollableList(loaiSanPhamData);
        JScrollPane scrollPane = createScrollPane(loaiSanPhamList, 200, 130);
        scrollPane.setBounds(15, 25, 200, 130);
        loaiSanPhamPanel.add(scrollPane);

        //Btn thêm loại sản phẩm
        themLoaiSanPhamButton = new CustomButton("Thêm loại sản phẩm");
        themLoaiSanPhamButton.setBounds(15, 170, 195, 25);
        loaiSanPhamPanel.add(themLoaiSanPhamButton);
    }

    private void setupNhaCungCapPanel() {
        // Nhà cung cấp panel
        JPanel nhaCungCapPanel = createTitledPanel("Nhà cung cấp", 230, 210);
        nhaCungCapPanel.setLayout(null);
        bottomPanelLeft.add(nhaCungCapPanel);

        // Nhà cung cấp list
        String[] nhaCungCapData = {"Nhà cung cấp 1", "Nhà cung cấp 2", "Nhà cung cấp 3", "Nhà cung cấp 4",
                "Nhà cung cấp 5", "Nhà cung cấp 6", "Nhà cung cấp 7", "Nhà cung cấp 8",
                "Nhà cung cấp 9", "Nhà cung cấp 10", "Nhà cung cấp 11", "Nhà cung cấp 12",
                "Nhà cung cấp 13", "Nhà cung cấp 14", "Nhà cung cấp 15", "Nhà cung cấp 16",
                "Nhà cung cấp 17", "Nhà cung cấp 18", "Nhà cung cấp 19", "Nhà cung cấp 20"};
        nhaCungCapList = createScrollableList(nhaCungCapData);
        JScrollPane scrollPane = createScrollPane(nhaCungCapList, 200, 130);
        scrollPane.setBounds(15, 25, 200, 130);
        nhaCungCapPanel.add(scrollPane);

        //Btn thêm nhà cung cấp
        themNhaCungCapButton = new CustomButton("Thêm nhà cung cấp");
        themNhaCungCapButton.setBounds(15, 170, 195, 25);
        nhaCungCapPanel.add(themNhaCungCapButton);
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
