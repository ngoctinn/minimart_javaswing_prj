package org.example.GUI.Panels.giaoDichPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.KhuyenMaiBUS;
import org.example.Components.*;
import org.example.DTO.khuyenMaiDTO;
import org.example.GUI.Dialogs.ThemKhuyenMaiDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class KhuyenMaiPanel extends JPanel {
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
    private CustomTable khuyenMaiTable;

    private JPanel mainButtonsPanel;
    private JPanel importExportPanel;

    public KhuyenMaiPanel() {
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
        JLabel title = new JLabel("Khuyến mãi");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));

        // Search field
        searchField = new CustomTextField("Nhập tên khuyến mãi cần tìm");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        // hành động tìm kiếm
        searchField.addActionListener(e -> {
            String searchText = searchField.getText();
            if (!searchText.isEmpty()) {
                ArrayList<khuyenMaiDTO> dsKhuyenMai = KhuyenMaiBUS.timKiemKhuyenMai(searchText);
                DefaultTableModel model = (DefaultTableModel) khuyenMaiTable.getModel();
                model.setRowCount(0); // Clear existing rows
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                for (khuyenMaiDTO khuyenMai : dsKhuyenMai) {
                    Object[] rowData = new Object[]{
                            khuyenMai.getMaKM(),
                            khuyenMai.getTenKM(),
                            khuyenMai.getDieuKien(),
                            khuyenMai.getNgayBD().format(formatter),
                            khuyenMai.getNgayKT().format(formatter),
                            khuyenMai.getPhanTram() + "%"
                    };
                    model.addRow(rowData);
                }
            } else {
                refreshKhuyenMaiTable();
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
                ArrayList<khuyenMaiDTO> dsKhuyenMai = KhuyenMaiBUS.timKiemKhuyenMai(searchText);
                DefaultTableModel model = (DefaultTableModel) khuyenMaiTable.getModel();
                model.setRowCount(0); // Clear existing rows
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                for (khuyenMaiDTO khuyenMai : dsKhuyenMai) {
                    Object[] rowData = new Object[]{
                            khuyenMai.getMaKM(),
                            khuyenMai.getTenKM(),
                            khuyenMai.getDieuKien(),
                            khuyenMai.getNgayBD().format(formatter),
                            khuyenMai.getNgayKT().format(formatter),
                            khuyenMai.getPhanTram() + "%"
                    };
                    model.addRow(rowData);
                }
            } else {
                refreshKhuyenMaiTable();
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
            refreshKhuyenMaiTable();
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
            // Open the dialog to add a new promotion
            ThemKhuyenMaiDialog dialog = new ThemKhuyenMaiDialog();

            // Refresh the table after dialog is closed
            if (dialog.isClosed()) {
                refreshKhuyenMaiTable();
            }
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
            int selectedRow = khuyenMaiTable.getSelectedRow();
            if (selectedRow != -1) {
                String maKM = khuyenMaiTable.getValueAt(selectedRow, 0).toString();
                khuyenMaiDTO selectedKhuyenMai = KhuyenMaiBUS.layKhuyenMaiTheoMa(maKM);
                if (selectedKhuyenMai != null) {
                    ThemKhuyenMaiDialog dialog = new ThemKhuyenMaiDialog(selectedKhuyenMai);
                    if (dialog.isClosed()) {
                        refreshKhuyenMaiTable();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
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
            int selectedRow = khuyenMaiTable.getSelectedRow();
            if (selectedRow != -1) {
                String maKM = khuyenMaiTable.getValueAt(selectedRow, 0).toString();
                khuyenMaiDTO selectedKhuyenMai = new khuyenMaiDTO();
                selectedKhuyenMai.setMaKM(maKM);

                // Show confirmation dialog
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khuyến mãi này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    KhuyenMaiBUS.xoaKhuyenMai(selectedKhuyenMai);
                    refreshKhuyenMaiTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
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
            // Xuất Excel functionality would go here
            JOptionPane.showMessageDialog(this, "Chức năng xuất Excel đang được phát triển.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        // Import button
        FlatSVGIcon importIcon = new FlatSVGIcon("Icons/import.svg", 16, 16);
        importButton = new CustomButton("", importIcon);
        importButton.setPreferredSize(new Dimension(50, 30));
        importButton.setMaximumSize(new Dimension(50, 30));
        importButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        importExportPanel.add(importButton);
        // hành động nhập excel
        importButton.addActionListener(e -> {
            // Nhập Excel functionality would go here
            JOptionPane.showMessageDialog(this, "Chức năng nhập Excel đang được phát triển.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add all button panels to the action panel
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(mainButtonsPanel);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(importExportPanel);
    }

    private void setupBottomPanel() {
        // Add padding to the bottom panel
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create promotion table panel
        JPanel tablePanel = createKhuyenMaiTablePanel();

        // Add to bottom panel
        bottomPanel.add(tablePanel, BorderLayout.CENTER);
    }

    private JPanel createKhuyenMaiTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Table data
        String[] columnNames = {"Mã KM", "Tên khuyến mãi", "Điều kiện", "Ngày bắt đầu", "Ngày kết thúc", "Phần trăm"};
        ArrayList<khuyenMaiDTO> dsKhuyenMai = KhuyenMaiBUS.layDanhSachKhuyenMai();
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (khuyenMaiDTO khuyenMai : dsKhuyenMai) {
            Object[] rowData = new Object[]{
                    khuyenMai.getMaKM(),
                    khuyenMai.getTenKM(),
                    khuyenMai.getDieuKien(),
                    khuyenMai.getNgayBD().format(formatter),
                    khuyenMai.getNgayKT().format(formatter),
                    khuyenMai.getPhanTram() + "%"
            };
            model.addRow(rowData);
        }

        // Create table
        khuyenMaiTable = new CustomTable(model);
        JScrollPane tableScrollPane = new JScrollPane(khuyenMaiTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    public void refreshKhuyenMaiTable() {
        // Get updated list of promotions
        ArrayList<khuyenMaiDTO> dsKhuyenMai = KhuyenMaiBUS.layDanhSachKhuyenMai();

        // Create new model with updated data
        String[] columnNames = {"Mã KM", "Tên khuyến mãi", "Điều kiện", "Ngày bắt đầu", "Ngày kết thúc", "Phần trăm"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (khuyenMaiDTO khuyenMai : dsKhuyenMai) {
            Object[] rowData = new Object[]{
                    khuyenMai.getMaKM(),
                    khuyenMai.getTenKM(),
                    khuyenMai.getDieuKien(),
                    khuyenMai.getNgayBD().format(formatter),
                    khuyenMai.getNgayKT().format(formatter),
                    khuyenMai.getPhanTram() + "%"
            };
            model.addRow(rowData);
        }

        // Update the table model
        khuyenMaiTable.setModel(model);
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
            frame.add(new KhuyenMaiPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
