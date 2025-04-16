package org.example.GUI.Panels.hangHoaPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.LoaiSanPhamBUS;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.DTO.LoaiSanPhamDTO;
import org.example.GUI.Dialogs.ThemLoaiSanPhamDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class LoaiSanPhamPanel extends JPanel {
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
    private CustomTable loaiSanPhamTable;
    private CustomTable sanPhamTable;
    private JRadioButton radioTatCa;
    private JRadioButton radioHoatDong;
    private JRadioButton radioKhongHoatDong;

    public LoaiSanPhamPanel() {
        initGUI();
    }

    private void initGUI() {
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
        JLabel title = new JLabel("Loại sản phẩm");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));

        // Search field
        searchField = new PlaceholderTextField("Nhập tên loại sản phẩm cần tìm");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        // hành động tìm kiếm
        searchField.addActionListener(e -> {
            String searchText = searchField.getText();
            if (!searchText.isEmpty()) {
                ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = LoaiSanPhamBUS.timKiemLoaiSanPham(searchText);
                DefaultTableModel model = (DefaultTableModel) loaiSanPhamTable.getModel();
                model.setRowCount(0); // Clear existing rows
                for (LoaiSanPhamDTO loaiSanPham : dsLoaiSanPham) {
                    Object[] rowData = new Object[]{
                            loaiSanPham.getMaLSP(),
                            loaiSanPham.getTenLSP(),
                            loaiSanPham.getTrangThai() ? "Đang hoạt động" : "Không hoạt động"
                    };
                    model.addRow(rowData);
                }
            } else {
                refreshLoaiSanPhamTable();
            }
        });

        // Search button
        searchButton = new CustomButton("Tìm");
        searchButton.setPreferredSize(new Dimension(70, 30));
        searchButton.setMaximumSize(new Dimension(70, 30));
        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(5));
        // hành động tìm kiếm
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            if (!searchText.isEmpty()) {
                ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = LoaiSanPhamBUS.timKiemLoaiSanPham(searchText);
                DefaultTableModel model = (DefaultTableModel) loaiSanPhamTable.getModel();
                model.setRowCount(0); // Clear existing rows
                for (LoaiSanPhamDTO loaiSanPham : dsLoaiSanPham) {
                    Object[] rowData = new Object[]{
                            loaiSanPham.getMaLSP(),
                            loaiSanPham.getTenLSP(),
                            loaiSanPham.getTrangThai() ? "Đang hoạt động" : "Không hoạt động"
                    };
                    model.addRow(rowData);
                }
            } else {
                refreshLoaiSanPhamTable();
            }
        });

        // Refresh button
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setPreferredSize(new Dimension(50, 30));
        refreshButton.setMaximumSize(new Dimension(50, 30));
        refreshButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        searchPanel.add(refreshButton);
        searchPanel.add(Box.createHorizontalStrut(5));
        // hành động làm mới
        refreshButton.addActionListener(e -> {
            searchField.setText("");
            refreshLoaiSanPhamTable();
        });

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
        mainButtonsPanel.add(addButton);
        mainButtonsPanel.add(Box.createHorizontalStrut(5));
        addButton.addActionListener(e -> new ThemLoaiSanPhamDialog(this));

        // Edit button
        FlatSVGIcon editIcon = new FlatSVGIcon("Icons/edit.svg", 20, 20);
        editButton = new CustomButton("Sửa", editIcon);
        editButton.setPreferredSize(new Dimension(100, 30));
        editButton.setMaximumSize(new Dimension(100, 30));
        editButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        mainButtonsPanel.add(editButton);
        mainButtonsPanel.add(Box.createHorizontalStrut(5));
        // hành động sửa
        editButton.addActionListener(e -> {
            int selectedRow = loaiSanPhamTable.getSelectedRow();
            if (selectedRow != -1) {
                String maLSP = loaiSanPhamTable.getValueAt(selectedRow, 0).toString();
                String tenLSP = loaiSanPhamTable.getValueAt(selectedRow, 1).toString();
                LoaiSanPhamDTO selectedLoaiSanPham = new LoaiSanPhamDTO();
                selectedLoaiSanPham.setMaLSP(maLSP);
                selectedLoaiSanPham.setTenLSP(tenLSP);
                new ThemLoaiSanPhamDialog(this, selectedLoaiSanPham);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn loại sản phẩm để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Delete button
        FlatSVGIcon deleteIcon = new FlatSVGIcon("Icons/delete.svg", 20, 20);
        deleteButton = new CustomButton("Xóa", deleteIcon);
        deleteButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setMaximumSize(new Dimension(100, 30));
        deleteButton.setButtonColors(CustomButton.ButtonColors.RED);
        mainButtonsPanel.add(deleteButton);
        mainButtonsPanel.add(Box.createHorizontalStrut(5));
        // hành động xóa
        deleteButton.addActionListener(e -> {
            int selectedRow = loaiSanPhamTable.getSelectedRow();
            if (selectedRow != -1) {
                String maLSP = loaiSanPhamTable.getValueAt(selectedRow, 0).toString();
                String tenLSP = loaiSanPhamTable.getValueAt(selectedRow, 1).toString();
                LoaiSanPhamDTO selectedLoaiSanPham = new LoaiSanPhamDTO();
                selectedLoaiSanPham.setMaLSP(maLSP);

                // Show confirmation dialog
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa loại sản phẩm này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    LoaiSanPhamBUS.xoaLoaiSanPham(selectedLoaiSanPham);
                    refreshLoaiSanPhamTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn loại sản phẩm để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Export button
        FlatSVGIcon exportIcon = new FlatSVGIcon("Icons/excel.svg", 16, 16);
        exportButton = new CustomButton("", exportIcon);
        exportButton.setPreferredSize(new Dimension(50, 30));
        exportButton.setMaximumSize(new Dimension(50, 30));
        exportButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        importExportPanel.add(exportButton);
        importExportPanel.add(Box.createHorizontalStrut(5));
        // hành động xuất excel
        exportButton.addActionListener(e -> {
            boolean success = LoaiSanPhamBUS.xuatExcel();
            if (success) {
                javax.swing.JOptionPane.showMessageDialog(null, "Xuất file Excel thành công!");
            } else {
                // Người dùng có thể đã hủy việc chọn file, nên không cần hiển thị thông báo lỗi
            }
        });

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

        // Setup left panel (categories)
        setupLeftCategoryPanel();

        // Setup right panel (products)
        setupRightProductPanel();

        // Add panels to split pane
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        // Add split pane to bottom panel
        bottomPanel.add(splitPane, BorderLayout.CENTER);
    }

    private void setupLeftCategoryPanel() {
        // Create category table panel
        JPanel categoryTablePanel = createCategoryTablePanel();

        leftPanel.add(categoryTablePanel, BorderLayout.CENTER);
    }

    private void setupRightProductPanel() {
        // Create a title panel for the product list
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Danh sách sản phẩm thuộc loại");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0 , 5, 0, 0));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        // Create product table panel
        JPanel productTablePanel = createProductTablePanel();

        // Add to right panel
        rightPanel.add(titlePanel, BorderLayout.NORTH);
        rightPanel.add(productTablePanel, BorderLayout.CENTER);
    }


    private JPanel createCategoryTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Table data
        String[] columnNames = {"Mã loại", "Tên loại sản phẩm", "Trạng thái"};
        ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = LoaiSanPhamBUS.layDanhSachLoaiSanPham();
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        for (LoaiSanPhamDTO loaiSanPham : dsLoaiSanPham) {
           Object [] rowData = new Object[] {
                    loaiSanPham.getMaLSP(),
                    loaiSanPham.getTenLSP(),
                    loaiSanPham.getTrangThai() == true ? "Đang hoạt động" : "Không hoạt động"
            };
            model.addRow(rowData);
        }

        // Create table
        loaiSanPhamTable = new CustomTable(model);
        JScrollPane tableScrollPane = new JScrollPane(loaiSanPhamTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Add selection listener to update right panel when category is selected
        loaiSanPhamTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && loaiSanPhamTable.getSelectedRow() != -1) {
                    updateProductTable(loaiSanPhamTable.getValueAt(loaiSanPhamTable.getSelectedRow(), 0).toString());
                }
            }
        });

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    public void refreshLoaiSanPhamTable() {
        // Get updated list of categories
        ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = LoaiSanPhamBUS.layDanhSachLoaiSanPham();

        // Create new model with updated data
        String[] columnNames = {"Mã loại", "Tên loại sản phẩm", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        for (LoaiSanPhamDTO loaiSanPham : dsLoaiSanPham) {
            Object[] rowData = new Object[] {
                loaiSanPham.getMaLSP(),
                loaiSanPham.getTenLSP(),
                loaiSanPham.getTrangThai() ? "Đang hoạt động" : "Không hoạt động"
            };
            model.addRow(rowData);
        }

        // Update the table model
        loaiSanPhamTable.setModel(model);
    }


    private JPanel createProductTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Table data - initialize with empty data
        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Trạng thái"};
        Object[][] dataLoaiSanPham = {}; // Empty initially, will be populated when a category is selected

        // Create table
        sanPhamTable = new CustomTable(dataLoaiSanPham, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(sanPhamTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private void updateProductTable(String categoryId) {
        // This method would be called when a category is selected
        // For now, we'll populate with example data based on the selected category

        Object[][] productData;

        // Example data based on category ID
        switch (categoryId) {
            case "LSP001": // Bánh kẹo
                productData = new Object[][] {
                        {"SP001", "Bánh Oreo", "Đang kinh doanh"},
                        {"SP002", "Kẹo mút Chupa Chups", "Đang kinh doanh"},
                        {"SP003", "Bánh quy Cosy", "Đang kinh doanh"}
                };
                break;
            case "LSP002": // Thực phẩm khô
                productData = new Object[][] {
                        {"SP004", "Mì gói Hảo Hảo", "Đang kinh doanh"},
                        {"SP005", "Cháo ăn liền", "Đang kinh doanh"},
                        {"SP006", "Khô bò", "Ngừng kinh doanh"}
                };
                break;
            case "LSP003": // Thực phẩm tươi
                productData = new Object[][] {
                        {"SP007", "Rau muống", "Đang kinh doanh"},
                        {"SP008", "Thịt bò", "Đang kinh doanh"},
                        {"SP009", "Cá hồi", "Đang kinh doanh"}
                };
                break;
            default:
                productData = new Object[][] {
                        {"", "Chọn loại sản phẩm để xem", ""}
                };
        }

        // Update the product table with new data
        sanPhamTable.setModel(new javax.swing.table.DefaultTableModel(
                productData,
                new String[] {"Mã sản phẩm", "Tên sản phẩm", "Trạng thái"}
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

    // Test method
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new LoaiSanPhamPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
