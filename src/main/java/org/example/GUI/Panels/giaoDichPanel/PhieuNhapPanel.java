package org.example.GUI.Panels.giaoDichPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.PhieuNhapBUS;
import org.example.BUS.ChiTietPhieuNhapBUS;
import org.example.BUS.NhaCungCapBUS;
import org.example.BUS.NhanVienBUS;
import org.example.Components.*;
import org.example.DTO.phieuNhapDTO;
import org.example.DTO.chiTietPhieuNhapDTO;
import org.example.DTO.nhaCungCapDTO;
import org.example.DTO.nhanVienDTO;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PhieuNhapPanel extends JPanel {
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
    private JPanel leftPanel;
    private JPanel rightPanel;
    private CustomTable phieuNhapTable;
    private CustomTable chiTietPhieuNhapTable;

    private JPanel mainButtonsPanel;
    private JPanel importExportPanel;

    public PhieuNhapPanel() {
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

    //=====phương thức để ẩn panel hành động====================
    public void hideActionPanel() {
        mainButtonsPanel.setVisible(true);
        importExportPanel.setVisible(true);
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
        JLabel title = new JLabel("Phiếu nhập");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));

        // Search field
        searchField = new CustomTextField("Nhập mã phiếu nhập cần tìm");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        // hành động tìm kiếm
        searchField.addActionListener(e -> {
            String searchText = searchField.getText();
            if (!searchText.isEmpty()) {
                ArrayList<phieuNhapDTO> dsPhieuNhap = PhieuNhapBUS.timKiemPhieuNhap(searchText);
                DefaultTableModel model = (DefaultTableModel) phieuNhapTable.getModel();
                model.setRowCount(0); // Clear existing rows
                for (phieuNhapDTO phieuNhap : dsPhieuNhap) {
                    // Lấy thông tin nhân viên
                    nhanVienDTO nhanVien = NhanVienBUS.layNhanVienTheoMa(phieuNhap.getMaNV());
                    String tenNhanVien = (nhanVien != null) ? nhanVien.getHoTen() : "";

                    Object[] rowData = new Object[]{
                            phieuNhap.getMaPN(),
                            phieuNhap.getNgayLap().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            phieuNhap.getGioLap().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                            String.format("%,.0f", phieuNhap.getTongTien()) + " VNĐ",
                            phieuNhap.getMaNCC(),
                            tenNhanVien
                    };
                    model.addRow(rowData);
                }
            } else {
                refreshPhieuNhapTable();
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
                ArrayList<phieuNhapDTO> dsPhieuNhap = PhieuNhapBUS.timKiemPhieuNhap(searchText);
                DefaultTableModel model = (DefaultTableModel) phieuNhapTable.getModel();
                model.setRowCount(0); // Clear existing rows
                for (phieuNhapDTO phieuNhap : dsPhieuNhap) {
                    // Lấy thông tin nhân viên
                    nhanVienDTO nhanVien = NhanVienBUS.layNhanVienTheoMa(phieuNhap.getMaNV());
                    String tenNhanVien = (nhanVien != null) ? nhanVien.getHoTen() : "";

                    Object[] rowData = new Object[]{
                            phieuNhap.getMaPN(),
                            phieuNhap.getNgayLap().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            phieuNhap.getGioLap().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                            String.format("%,.0f", phieuNhap.getTongTien()) + " VNĐ",
                            phieuNhap.getMaNCC(),
                            tenNhanVien
                    };
                    model.addRow(rowData);
                }
            } else {
                refreshPhieuNhapTable();
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
            refreshPhieuNhapTable();
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
        mainButtonsPanel = new JPanel();
        mainButtonsPanel.setLayout(new BoxLayout(mainButtonsPanel, BoxLayout.X_AXIS));
        mainButtonsPanel.setBackground(Color.WHITE);

        // Create a panel for the import/export buttons
        importExportPanel = new JPanel();
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
        addButton.addActionListener(e -> {
            // Open the dialog to add a new import receipt
            // ThemPhieuNhapDialog dialog = new ThemPhieuNhapDialog();
            // if (dialog.isClosed()) {
            //     refreshPhieuNhapTable();
            // }
            JOptionPane.showMessageDialog(this, "Chức năng đang được phát triển", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

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
            int selectedRow = phieuNhapTable.getSelectedRow();
            if (selectedRow != -1) {
                String maPN = phieuNhapTable.getValueAt(selectedRow, 0).toString();
                phieuNhapDTO selectedPhieuNhap = PhieuNhapBUS.layPhieuNhapTheoMa(maPN);
                // new ThemPhieuNhapDialog(this, selectedPhieuNhap);
                JOptionPane.showMessageDialog(this, "Chức năng đang được phát triển", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
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
            int selectedRow = phieuNhapTable.getSelectedRow();
            if (selectedRow != -1) {
                String maPN = phieuNhapTable.getValueAt(selectedRow, 0).toString();
                phieuNhapDTO selectedPhieuNhap = PhieuNhapBUS.layPhieuNhapTheoMa(maPN);

                // Show confirmation dialog
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phiếu nhập này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    PhieuNhapBUS.xoaPhieuNhap(selectedPhieuNhap);
                    refreshPhieuNhapTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
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
            // boolean success = PhieuNhapBUS.xuatExcel();
            // if (success) {
            //     javax.swing.JOptionPane.showMessageDialog(null, "Xuất file Excel thành công!");
            // }
            JOptionPane.showMessageDialog(this, "Chức năng đang được phát triển", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
        splitPane.setResizeWeight(0.7);

        // Create left and right panels
        leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(Color.WHITE);

        rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(Color.WHITE);

        // Setup left panel (phiếu nhập)
        setupLeftPhieuNhapPanel();

        // Setup right panel (chi tiết phiếu nhập)
        setupRightChiTietPanel();

        // Add panels to split pane
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        // Add split pane to bottom panel
        bottomPanel.add(splitPane, BorderLayout.CENTER);
    }

    private void setupLeftPhieuNhapPanel() {
        // Create phiếu nhập table panel
        JPanel phieuNhapTablePanel = createPhieuNhapTablePanel();

        leftPanel.add(phieuNhapTablePanel, BorderLayout.CENTER);
    }

    private void setupRightChiTietPanel() {
        // Create a title panel for the chi tiết phiếu nhập list
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Chi tiết phiếu nhập");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0 , 5, 0, 0));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        // Create chi tiết phiếu nhập table panel
        JPanel chiTietTablePanel = createChiTietTablePanel();

        // Add to right panel
        rightPanel.add(titlePanel, BorderLayout.NORTH);
        rightPanel.add(chiTietTablePanel, BorderLayout.CENTER);
    }

    private JPanel createPhieuNhapTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Table data
        String[] columnNames = {"Mã phiếu nhập", "Ngày lập", "Giờ lập", "Tổng tiền", "Mã NCC", "Nhân viên nhập"};
        ArrayList<phieuNhapDTO> dsPhieuNhap = PhieuNhapBUS.layDanhSachPhieuNhap();
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        for (phieuNhapDTO phieuNhap : dsPhieuNhap) {
            // Lấy thông tin nhân viên
            nhanVienDTO nhanVien = NhanVienBUS.layNhanVienTheoMa(phieuNhap.getMaNV());
            String tenNhanVien = (nhanVien != null) ? nhanVien.getHoTen() : "";

            Object [] rowData = new Object[] {
                    phieuNhap.getMaPN(),
                    phieuNhap.getNgayLap().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    phieuNhap.getGioLap().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                    String.format("%,.0f", phieuNhap.getTongTien()) + " VNĐ",
                    phieuNhap.getMaNCC(),
                    tenNhanVien
            };
            model.addRow(rowData);
        }

        // Create table
        phieuNhapTable = new CustomTable(model);
        JScrollPane tableScrollPane = new JScrollPane(phieuNhapTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Add selection listener to update right panel when phiếu nhập is selected
        phieuNhapTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && phieuNhapTable.getSelectedRow() != -1) {
                    updateChiTietTable(phieuNhapTable.getValueAt(phieuNhapTable.getSelectedRow(), 0).toString());
                }
            }
        });

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    public void refreshPhieuNhapTable() {
        // Get updated list of phiếu nhập
        ArrayList<phieuNhapDTO> dsPhieuNhap = PhieuNhapBUS.layDanhSachPhieuNhap();

        // Create new model with updated data
        String[] columnNames = {"Mã phiếu nhập", "Ngày lập", "Giờ lập", "Tổng tiền", "Mã NCC", "Nhân viên nhập"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        for (phieuNhapDTO phieuNhap : dsPhieuNhap) {
            // Lấy thông tin nhân viên
            nhanVienDTO nhanVien = NhanVienBUS.layNhanVienTheoMa(phieuNhap.getMaNV());
            String tenNhanVien = (nhanVien != null) ? nhanVien.getHoTen() : "";

            Object[] rowData = new Object[] {
                phieuNhap.getMaPN(),
                phieuNhap.getNgayLap().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                phieuNhap.getGioLap().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                String.format("%,.0f", phieuNhap.getTongTien()) + " VNĐ",
                phieuNhap.getMaNCC(),
                tenNhanVien
            };
            model.addRow(rowData);
        }

        // Update the table model
        phieuNhapTable.setModel(model);
    }

    private JPanel createChiTietTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Table data - initialize with empty data
        String[] columnNames = {"Mã lô hàng", "Số lượng", "Giá nhập"};
        Object[][] dataChiTiet = {}; // Empty initially, will be populated when a phiếu nhập is selected

        // Create table
        chiTietPhieuNhapTable = new CustomTable(dataChiTiet, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(chiTietPhieuNhapTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private void updateChiTietTable(String maPhieuNhap) {
        // Get chi tiết phiếu nhập for the selected phiếu nhập from the database
        ArrayList<chiTietPhieuNhapDTO> dsChiTiet = ChiTietPhieuNhapBUS.layChiTietPhieuNhapTheoMaPN(maPhieuNhap);

        // Create a new table model
        DefaultTableModel model = new DefaultTableModel(null, new String[] {"Mã lô hàng", "Số lượng", "Giá nhập"});

        // If no chi tiết found, show a message
        if (dsChiTiet.isEmpty()) {
            model.addRow(new Object[] {"", "Không có chi tiết nào", ""});
        } else {
            // Add each chi tiết to the table model
            for (chiTietPhieuNhapDTO chiTiet : dsChiTiet) {
                Object[] rowData = new Object[] {
                    chiTiet.getMaLoHang(),
                    chiTiet.getSoLuong(),
                    String.format("%,.0f", chiTiet.getGiaNhap()) + " VNĐ"
                };
                model.addRow(rowData);
            }
        }

        // Update the chi tiết table with new data
        chiTietPhieuNhapTable.setModel(model);
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

    // Test method
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new PhieuNhapPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}