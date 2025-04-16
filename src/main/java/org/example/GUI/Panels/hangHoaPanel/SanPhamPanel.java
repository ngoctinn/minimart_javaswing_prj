package org.example.GUI.Panels.hangHoaPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.SanPhamBUS;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Dialogs.ThemSanPhamDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;


public class SanPhamPanel extends JPanel {
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
    private JRadioButton radioGiaTangDan;
    private JRadioButton radioGiaGiamDan;
    private CustomTable hangHoaTable;
    private CustomButton themLoaiSanPhamButton;
    private CustomButton themNhaCungCapButton;

    public SanPhamPanel() {
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
        JLabel title = new JLabel("Danh mục sản phẩm");
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
            new ThemSanPhamDialog(this);
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
        setupSapXepTheoGiaPanel(); // Add new panel for price sorting
        setupLuaChonHienThiPanel();
    }

    private void setupNhomHangPanel() {
        // Nhóm hàng panel - reduced height from 210 to 180
        JPanel loaiSanPhamPanel = createTitledPanel("Loại sản phẩm", 230, 250);

        // Change from BorderLayout to a more flexible layout
        loaiSanPhamPanel.setLayout(new BoxLayout(loaiSanPhamPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(loaiSanPhamPanel);

        // Nhóm hàng list
        String[] loaiSanPhamData = {"Bánh kẹo", "Thực phẩm khô", "Thực phẩm tươi", "Đồ uống","Bia","Đồ gia dụng", "Hàng hóa khác", "Thực phẩm chức năng", "Thực phẩm bổ sung", "Thực phẩm dinh dưỡng"};
        loaiSanPhamList = createScrollableList(loaiSanPhamData);
        JScrollPane scrollPane = createScrollPane(loaiSanPhamList, 200, 180);
        scrollPane.setBounds(15, 25, 200, 180);
        loaiSanPhamPanel.add(scrollPane);

        // Add some vertical space between the list and button
        loaiSanPhamPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Create a panel to hold the button and center it horizontally
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setMaximumSize(new Dimension(230, 35)); // Control the height

        // Button with smaller size
        themLoaiSanPhamButton = new CustomButton("Thêm loại sản phẩm");
        themLoaiSanPhamButton.setPreferredSize(new Dimension(200, 30)); // Smaller button size
        buttonPanel.add(themLoaiSanPhamButton);

        loaiSanPhamPanel.add(buttonPanel);
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
    
    private void setupSapXepTheoGiaPanel() {
        // Sắp xếp theo giá panel
        JPanel sapXepPanel = createTitledPanel("Sắp xếp theo giá", 230, 80);
        sapXepPanel.setLayout(new BoxLayout(sapXepPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(sapXepPanel);

        // Radio buttons
        radioGiaTangDan = createRadioButton("Giá tăng dần");
        radioGiaGiamDan = createRadioButton("Giá giảm dần");

        // Group radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioGiaTangDan);
        buttonGroup.add(radioGiaGiamDan);

        // Add to panel
        sapXepPanel.add(radioGiaTangDan);
        sapXepPanel.add(radioGiaGiamDan);
    }

    private void setupBottomPanelRight() {
        // Change layout to BorderLayout
        bottomPanelRight.setLayout(new BorderLayout(0, 10));

        // Upper part - Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Table data - added price column
        String[] columnNames = {"Mã sản phẩm", "Hình ảnh", "Tên sản phẩm", "Loại sản phẩm", "Giá bán", "Tồn kho" };
        ArrayList<SanPhamDTO> danhSachSanPham = new SanPhamBUS().layDanhSachSanPham();
        // Create a custom table model that can handle different data types
        DefaultTableModel model = new DefaultTableModel(null, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                // Column 1 (index 1) is the image column
                return column == 1 ? ImageIcon.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        // Add data to the model
        for (SanPhamDTO sanPham : danhSachSanPham) {
            // Load and scale the image
            ImageIcon imageIcon = null;
            try {
                String imagePath = sanPham.getHinhAnh();
                if (imagePath != null && !imagePath.isEmpty()) {
                    // Try to load from resources first
                    URL imageUrl = getClass().getClassLoader().getResource("Images/Products/" + imagePath);

                    if (imageUrl != null) {
                        // Load and scale the image from resources
                        ImageIcon originalIcon = new ImageIcon(imageUrl);
                        Image image = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(image);
                    } else {
                        // Fallback to file system if not found in resources
                        File imageFile = new File("d:\\Projects\\minimart_javaswing_prj\\src\\main\\resources\\Images\\Products", imagePath);
                        if (imageFile.exists()) {
                            ImageIcon originalIcon = new ImageIcon(imageFile.getAbsolutePath());
                            Image image = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                            imageIcon = new ImageIcon(image);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Create row data with the image
            Object[] rowData = {
                    sanPham.getMaSP(),
                    imageIcon, // ImageIcon object instead of string path
                    sanPham.getTenSP(),
                    sanPham.getMaLSP(),
                    String.valueOf(sanPham.getGiaBan()),
                    String.valueOf(sanPham.getTonKho())
            };
            model.addRow(rowData);
        }


        // Create table
        hangHoaTable = new CustomTable(model);
        hangHoaTable.setRowHeight(70);
        JScrollPane tableScrollPane = new JScrollPane(hangHoaTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);


        // Add panels to bottomPanelRight
        bottomPanelRight.add(tablePanel, BorderLayout.CENTER);
    }

    //refresh table
    public void refreshSanPhamTable() {
        // Clear the existing rows
        DefaultTableModel model = (DefaultTableModel) hangHoaTable.getModel();
        model.setRowCount(0);

        // Fetch the updated data
        ArrayList<SanPhamDTO> danhSachSanPham = new SanPhamBUS().layDanhSachSanPham();

        // Add the updated data to the table
        for (SanPhamDTO sanPham : danhSachSanPham) {
            // Load and scale the image
            ImageIcon imageIcon = null;
            try {
                String imagePath = sanPham.getHinhAnh();
                if (imagePath != null && !imagePath.isEmpty()) {
                    // Try to load from resources first
                    URL imageUrl = getClass().getClassLoader().getResource("Images/Products/" + imagePath);

                    if (imageUrl != null) {
                        // Load and scale the image from resources
                        ImageIcon originalIcon = new ImageIcon(imageUrl);
                        Image image = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(image);
                    } else {
                        // Fallback to file system if not found in resources
                        File imageFile = new File("d:\\Projects\\minimart_javaswing_prj\\src\\main\\resources\\Images\\Products", imagePath);
                        if (imageFile.exists()) {
                            ImageIcon originalIcon = new ImageIcon(imageFile.getAbsolutePath());
                            Image image = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                            imageIcon = new ImageIcon(image);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Create row data with the image
            Object[] rowData = {
                    sanPham.getMaSP(),
                    imageIcon, // ImageIcon object instead of string path
                    sanPham.getTenSP(),
                    sanPham.getMaLSP(),
                    String.valueOf(sanPham.getGiaBan()),
                    String.valueOf(sanPham.getTonKho())
            };
            model.addRow(rowData);
        }
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
            frame.add(new SanPhamPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
