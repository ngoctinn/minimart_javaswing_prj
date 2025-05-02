package org.example.GUI.Panels.doiTacPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.KhachHangBUS;
import org.example.Components.*;
import org.example.DAO.KhachHangDAO;
import org.example.DTO.KhachHangDTO;
import org.example.GUI.Dialogs.ThemKhachHangDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class KhachHangPanel extends JPanel {
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
    private CustomTable khachHangTable;
    private CustomTable hoaDonTable;

    public KhachHangPanel() {
        initGUI();
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

        // Thiết lập sự kiện cho bảng khách hàng
        khachHangTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && khachHangTable.getSelectedRow() != -1) {
                    // TODO: Implement action when customer selection changes
                    String customerId = khachHangTable.getValueAt(khachHangTable.getSelectedRow(), 0).toString();
                    updateInvoiceTable(customerId);
                }
            }
        });
    }

    // CÁC PHƯƠNG THỨC XỬ LÝ SỰ KIỆN
    private void handleAddButton() {
        // TODO: Implement add customer action
        ThemKhachHangDialog dialog = new ThemKhachHangDialog();
        // Refresh the customer table after adding a new customer
        if (dialog.isClosed()){
            refreshKhachHangTable();
        }

    }

    private void handleEditButton() {
        // TODO: Implement edit customer action
        int selectedRow = khachHangTable.getSelectedRow();
        if (selectedRow != -1) {
            String maKH = (String) khachHangTable.getValueAt(selectedRow, 0);
            String tenKH = (String) khachHangTable.getValueAt(selectedRow, 1);
            String diaChi = (String) khachHangTable.getValueAt(selectedRow, 2);
            int diemTichLuy = (int) khachHangTable.getValueAt(selectedRow, 3);
            String gioiTinh = (String) khachHangTable.getValueAt(selectedRow, 4);
            String sdt = (String) khachHangTable.getValueAt(selectedRow, 5);
            String email = (String) khachHangTable.getValueAt(selectedRow, 6);
            LocalDate ngaySinh = (LocalDate) khachHangTable.getValueAt(selectedRow, 7);


            KhachHangDTO khachHangDTO = new KhachHangDTO();
            khachHangDTO.setMaKH(maKH);
            khachHangDTO.setHoTen(tenKH);
            khachHangDTO.setDiaChi(diaChi);
            khachHangDTO.setDiemTichLuy(diemTichLuy);
            khachHangDTO.setGioiTinh(gioiTinh);
            khachHangDTO.setSDT(sdt);
            khachHangDTO.setEmail(email);
            khachHangDTO.setNgaySinh(ngaySinh);
            // mở hộp thoại sửa thông tin khách hàng
            ThemKhachHangDialog suaKhachHangDialog = new ThemKhachHangDialog(khachHangDTO);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa.");
        }
    }

    private void handleDeleteButton() {
        // TODO: Implement delete customer action
        int selectedRow = khachHangTable.getSelectedRow();
        if (selectedRow != -1) {
            // String maKH = (String) khachHangTable.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Implement delete logic here
                KhachHangBUS khachHangBUS = new KhachHangBUS();
                KhachHangDTO khachHangDTO = new KhachHangDTO();
                khachHangDTO.setMaKH((String) khachHangTable.getValueAt(selectedRow, 0));
                String maKH = (String) khachHangTable.getValueAt(selectedRow, 0);
                khachHangBUS.xoaKhachHang(khachHangDTO);
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công.");

                refreshKhachHangTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa.");
        }
    }

    private void handleRefreshButton() {
        // TODO: Implement refresh action
        refreshKhachHangTable();
    }

    private void handleSearchButton() {
        // TODO: Implement search action
        String searchText = searchField.getText().trim();
        if (!searchText.isEmpty()) {
            // Implement search logic here
             ArrayList<KhachHangDTO> searchResults = new KhachHangBUS().timKiemKhachHang(searchText);
             DefaultTableModel model = (DefaultTableModel) khachHangTable.getModel();
             model.setRowCount(0); // Clear existing rows
             loadKhachHangData(model, searchResults);
        } else {
            refreshKhachHangTable(); // If search text is empty, refresh the table
        }
    }

    private void handleImportButton() {
        // Hiển thị hộp thoại chọn file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx", "xls"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // TODO: Xử lý nhập dữ liệu từ file Excel
            // File selectedFile = fileChooser.getSelectedFile();
            // Gọi phương thức nhập dữ liệu từ file Excel
            JOptionPane.showMessageDialog(this, "Chức năng đang được phát triển", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleExportButton() {
        // Hiển thị hộp thoại lưu file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu danh sách khách hàng");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // TODO: Xử lý xuất dữ liệu ra file Excel
            // File selectedFile = fileChooser.getSelectedFile();
            // Nếu file không có đuôi .xlsx, thêm vào
            // if (!selectedFile.getName().toLowerCase().endsWith(".xlsx")) {
            //     selectedFile = new File(selectedFile.getAbsolutePath() + ".xlsx");
            // }
            // Gọi phương thức xuất dữ liệu ra file Excel
            JOptionPane.showMessageDialog(this, "Chức năng đang được phát triển", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
        topPanel = new RoundedPanel(20);
        bottomPanel = new RoundedPanel(20);

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
        JLabel title = new JLabel("Quản lý khách hàng");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));

        // Search field
        searchField = new CustomTextField("Nhập khách hàng cần tìm");
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
        searchPanel.add(Box.createHorizontalStrut(5));

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
        // Thêm padding cho panel dưới
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Tạo layout cho panel dưới
        bottomPanel.setLayout(new BorderLayout(10, 10));
        
        // Tạo panel chứa bảng khách hàng
        JPanel khachHangTablePanel = new JPanel(new BorderLayout());
        khachHangTablePanel.setBackground(Color.WHITE);
        
        // Tạo tiêu đề cho bảng khách hàng
        JLabel khachHangTableTitle = new JLabel("Danh sách khách hàng");
        khachHangTableTitle.setFont(new Font("Roboto", Font.BOLD, 16));
        khachHangTableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        khachHangTablePanel.add(khachHangTableTitle, BorderLayout.NORTH);
        
        // Tạo bảng khách hàng
        String[] khachHangColumns = {"Mã KH", "Họ tên", "Địa chỉ", "Điểm tích lũy", "Giới tính", "Số điện thoại", "Email", "Ngày sinh"};
        DefaultTableModel khachHangModel = new DefaultTableModel(khachHangColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        };
        
        khachHangTable = new CustomTable();
        khachHangTable.setModel(khachHangModel);
        JScrollPane khachHangScrollPane = new JScrollPane(khachHangTable);
        khachHangTablePanel.add(khachHangScrollPane, BorderLayout.CENTER);
        
        // Tạo panel chứa bảng hóa đơn
        JPanel hoaDonTablePanel = new JPanel(new BorderLayout());
        hoaDonTablePanel.setBackground(Color.WHITE);
        
        // Tạo tiêu đề cho bảng hóa đơn
        JLabel hoaDonTableTitle = new JLabel("Lịch sử mua hàng");
        hoaDonTableTitle.setFont(new Font("Roboto", Font.BOLD, 16));
        hoaDonTableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        hoaDonTablePanel.add(hoaDonTableTitle, BorderLayout.NORTH);
        
        // Tạo bảng hóa đơn
        String[] hoaDonColumns = {"Mã HD", "Ngày lập", "Tổng tiền", "Trạng thái"};
        DefaultTableModel hoaDonModel = new DefaultTableModel(hoaDonColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        };
        
        hoaDonTable = new CustomTable();
        hoaDonTable.setModel(hoaDonModel);
        JScrollPane hoaDonScrollPane = new JScrollPane(hoaDonTable);
        hoaDonTablePanel.add(hoaDonScrollPane, BorderLayout.CENTER);
        
        // Tạo panel chia đôi
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, khachHangTablePanel, hoaDonTablePanel);
        splitPane.setResizeWeight(0.6); // 60% cho bảng khách hàng, 40% cho bảng hóa đơn
        splitPane.setDividerSize(5);
        splitPane.setBorder(null);
        
        bottomPanel.add(splitPane, BorderLayout.CENTER);
        
        // Làm mới bảng khách hàng
        refreshKhachHangTable();
    }

    private void refreshKhachHangTable() {
        // Lấy danh sách khách hàng từ BUS
        ArrayList<KhachHangDTO> dsKhachHang = new KhachHangBUS().layDanhSachKhachHang();
        
        // Cập nhật bảng khách hàng
        DefaultTableModel model = (DefaultTableModel) khachHangTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu hiện có
        loadKhachHangData(model, dsKhachHang);
    }
    
    private void loadKhachHangData(DefaultTableModel model, ArrayList<KhachHangDTO> dsKhachHang) {
        for (KhachHangDTO khachHang : dsKhachHang) {
            Object[] row = new Object[]{
                khachHang.getMaKH(),
                khachHang.getHoTen(),
                khachHang.getDiaChi(),
                khachHang.getDiemTichLuy(),
                khachHang.getGioiTinh(),
                khachHang.getSDT(),
                khachHang.getEmail(),
                khachHang.getNgaySinh()
            };
            model.addRow(row);
        }
    }
    
    private void updateInvoiceTable(String maKH) {
        // TODO: Cập nhật bảng hóa đơn dựa trên mã khách hàng
        // Đây là nơi bạn sẽ hiển thị các hóa đơn của khách hàng đã chọn
        // Ví dụ:
        // ArrayList<HoaDonDTO> dsHoaDon = new HoaDonBUS().layDanhSachHoaDonTheoKhachHang(maKH);
        // DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
        // model.setRowCount(0);
        // for (HoaDonDTO hoaDon : dsHoaDon) {
        //     Object[] row = new Object[]{
        //         hoaDon.getMaHD(),
        //         hoaDon.getNgayLap(),
        //         hoaDon.getTongTien(),
        //         hoaDon.getTrangThai()
        //     };
        //     model.addRow(row);
        // }
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return radioButton;
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

        // Use preferred size only if width and height are specified
        // Let layout managers handle sizing otherwise
        if (width > 0 && height > 0) {
            panel.setPreferredSize(new Dimension(width, height));
        }

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

    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame("Test KhachHangPanel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300, 800); // Adjusted size for better view
            frame.setLocationRelativeTo(null);
            frame.add(new KhachHangPanel());
            // frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Optional: maximize frame
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void showSuccessToast(Component parent, String message) {
        // Hiển thị thông báo thành công
        new CustomToastMessage(parent, message, CustomToastMessage.SUCCESS, 2000).showToast();
    }
}

