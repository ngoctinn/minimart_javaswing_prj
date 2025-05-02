package org.example.GUI.Panels.doiTacPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.*;
import org.example.DTO.nhaCungCapDTO;
import org.example.GUI.Dialogs.ThemNCCDialog;
import org.example.BUS.NhaCungCapBUS;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class nhaCungCapPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;

    // Top panel components
    private CustomTextField searchField;
    private CustomButton searchButton;
    private CustomButton refreshButton;
    private CustomButton addButton;
    private CustomButton editButton;
    private CustomButton deleteButton;
    private CustomButton importButton;
    private CustomButton exportButton;

    // Bottom panel components
    private CustomTable nhaCungCapTable;

    public nhaCungCapPanel() {
        initGUI();
        loadNhaCungCapData();
    }

    public void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanel();
        setupEventHandlers();

        // Add panels to main panel with proper constraints
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
    }

    //====================== XỬ LÝ SỰ KIỆN=================================
    private void setupEventHandlers() {
        // Thiết lập tất cả các sự kiện ở đây
        addButton.addActionListener(e -> handleAddButton());
        editButton.addActionListener(e -> handleEditButton());
        deleteButton.addActionListener(e -> handleDeleteButton());
        refreshButton.addActionListener(e -> handleRefreshButton());
        searchButton.addActionListener(e -> handleSearchButton());
        importButton.addActionListener(e -> handleImportButton());
        exportButton.addActionListener(e -> handleExportButton());
        searchField.addActionListener(e -> handleSearchButton());
    }

    // CÁC PHƯƠNG THỨC XỬ LÝ SỰ KIỆN
    private void handleAddButton() {
        // Xử lý thêm nhà cung cấp
        new ThemNCCDialog();
        loadNhaCungCapData();
    }

    private void handleEditButton() {
        // Xử lý sửa nhà cung cấp
        int selectedRow = nhaCungCapTable.getSelectedRow();
        if (selectedRow != -1) {
            String maNCC = nhaCungCapTable.getValueAt(selectedRow, 0).toString();
            String tenNCC = nhaCungCapTable.getValueAt(selectedRow, 1).toString();
            String diaChi = nhaCungCapTable.getValueAt(selectedRow, 2).toString();
            String sdt = nhaCungCapTable.getValueAt(selectedRow, 3).toString();
            
            nhaCungCapDTO ncc = new nhaCungCapDTO(maNCC, tenNCC, diaChi, sdt, true);
            new ThemNCCDialog(ncc);
            loadNhaCungCapData();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleDeleteButton() {
        // Xử lý xóa nhà cung cấp
        int selectedRow = nhaCungCapTable.getSelectedRow();
        if (selectedRow != -1) {
            String maNCC = nhaCungCapTable.getValueAt(selectedRow, 0).toString();
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xóa nhà cung cấp này không?", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                int result = NhaCungCapBUS.xoaNhaCungCap(maNCC);
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadNhaCungCapData();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleRefreshButton() {
        // Xử lý làm mới bảng nhà cung cấp
        searchField.setText("");
        loadNhaCungCapData();
    }

    private void handleSearchButton() {
        // Xử lý tìm kiếm nhà cung cấp
        String keyword = searchField.getText().trim();
        if (!keyword.isEmpty()) {
            ArrayList<nhaCungCapDTO> ketQuaTimKiem = NhaCungCapBUS.timKiemNhaCungCap(keyword);
            updateTableData(ketQuaTimKiem);
        } else {
            loadNhaCungCapData();
        }
    }

    private void handleImportButton() {
        // Xử lý nhập nhà cung cấp từ file
        JOptionPane.showMessageDialog(this, "Chức năng đang được phát triển!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleExportButton() {
        // Xử lý xuất nhà cung cấp ra file
        boolean success = NhaCungCapBUS.xuatExcel();
        if (success) {
            JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Phương thức load dữ liệu nhà cung cấp
    private void loadNhaCungCapData() {
        ArrayList<nhaCungCapDTO> danhSachNCC = NhaCungCapBUS.layDanhSachNhaCungCap();
        updateTableData(danhSachNCC);
    }

    // Phương thức cập nhật dữ liệu bảng
    private void updateTableData(ArrayList<nhaCungCapDTO> danhSachNCC) {
        DefaultTableModel model = (DefaultTableModel) nhaCungCapTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu hiện tại
        
        for (nhaCungCapDTO ncc : danhSachNCC) {
            model.addRow(new Object[]{
                ncc.getMaNCC(),
                ncc.getTenNCC(),
                ncc.getDiaChi(),
                ncc.getSDT()
            });
        }
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
        topPanel = new RoundedPanel(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Vẽ nền panel
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                
                // Vẽ đổ bóng
                int shadowSize = 5;
                for (int i = 0; i < shadowSize; i++) {
                    g2d.setColor(new Color(0, 0, 0, (int)(38 * (1 - (float)i / shadowSize))));
                    g2d.drawRoundRect(0, i, getWidth() - 1, getHeight() - 1, 20, 20);
                }
                g2d.dispose();
            }
        };
        
        bottomPanel = new RoundedPanel(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Vẽ nền panel
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                
                // Vẽ đổ bóng
                int shadowSize = 5;
                for (int i = 0; i < shadowSize; i++) {
                    g2d.setColor(new Color(0, 0, 0, (int)(38 * (1 - (float)i / shadowSize))));
                    g2d.drawRoundRect(0, i, getWidth() - 1, getHeight() - 1, 20, 20);
                }
                g2d.dispose();
            }
        };

        // Set background colors
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(Color.WHITE);

        // Set layouts
        topPanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new BorderLayout());
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
        JLabel title = new JLabel("Nhà Cung Cấp");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));
        
        // Search field
        searchField = new CustomTextField("Nhập tên nhà cung cấp cần tìm");
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

    //======================CÀI ĐẶT PANEL DƯỚI=================================
    private void setupBottomPanel() {
        // Table data
        String[] columnNames = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Số điện thoại"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        nhaCungCapTable = new CustomTable(model);
        JScrollPane tableScrollPane = new JScrollPane(nhaCungCapTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        bottomPanel.add(tableScrollPane, BorderLayout.CENTER);
    }

    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new nhaCungCapPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
