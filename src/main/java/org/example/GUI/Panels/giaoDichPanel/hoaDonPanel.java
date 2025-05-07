package org.example.GUI.Panels.giaoDichPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.ChiTietHoaDonBUS;
import org.example.BUS.HoaDonBUS;
import org.example.BUS.KhachHangBUS;
import org.example.BUS.NhanVienBUS;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.DTO.KhachHangDTO;
import org.example.DTO.chiTietHoaDonDTO;
import org.example.DTO.hoaDonDTO;
import org.example.DTO.nhanVienDTO;
import org.example.GUI.MenuFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class hoaDonPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;

    // Top panel components
    private PlaceholderTextField searchField;
    private CustomButton searchButton, refreshButton, addButton, editButton, deleteButton, importButton, exportButton;

    // Bottom panel components
    private JSpinner startDateSpinner, endDateSpinner;
    private JList<String> userList;
    private CustomTable hoaDonTable;
    private CustomTable chiTietHoaDonTable;
    private CustomButton filterDateButton;

    //panel hành động
    private JPanel mainButtonsPanel;
    private JPanel importExportPanel;

    public hoaDonPanel() {
        initGUI();
        // Lấy dữ liệu ban đầu
        refreshHoaDonTable();
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

    //=====phương thức để ẩn panel hành động====================
    public void hideActionPanel() {
        mainButtonsPanel.setVisible(true);
        importExportPanel.setVisible(true);
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
        bottomPanelLeft = new RoundedPanel(20);
        bottomPanelRight = new RoundedPanel(20);

        // Set background colors
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(new Color(225, 225, 225));
        bottomPanelLeft.setBackground(Color.WHITE);
        bottomPanelRight.setBackground(Color.WHITE);

        // Set panel sizes
        bottomPanelLeft.setPreferredSize(new Dimension(250, 0)); // Fixed width for left panel

        // Set layouts
        topPanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new FlowLayout());
        bottomPanelRight.setLayout(new BoxLayout(bottomPanelRight, BoxLayout.Y_AXIS));
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
        JLabel title = new JLabel("Hoá Đơn");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));

        // Search field
        searchField = new PlaceholderTextField("Nhập mã hoá đơn cần tìm");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));

        // Search button
        searchButton = new CustomButton("Tìm");
        searchButton.setPreferredSize(new Dimension(70, 30));
        searchButton.setMaximumSize(new Dimension(70, 30));
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                timKiemHoaDon(keyword);
            } else {
                refreshHoaDonTable();
            }
        });
        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(5));

        // Refresh button
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setPreferredSize(new Dimension(50, 30));
        refreshButton.setMaximumSize(new Dimension(50, 30));
        refreshButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        refreshButton.addActionListener(e -> refreshHoaDonTable());
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
        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuFrame menuFrame = new MenuFrame();
                menuFrame.showPanel("banHang");
            }
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
        deleteButton = new CustomButton("Hủy", deleteIcon);
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

    //======================CÀI ĐẶT PANEL DƯỚI TRÁI=================================
    private void setupBottomPanelLeft() {
        setupDateFilterPanel();
        setupUserFilterPanel();
        setupCustomerFilterPanel();
    }

    private void setupDateFilterPanel() {
        JPanel datePanel = createTitledPanel("Thời Gian", 230, 180);
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(datePanel);

        // Date picker for start and end dates
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner = new JSpinner(new SpinnerDateModel());

        // Set date format
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy");
        JSpinner.DateEditor endEditor = new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy");
        startDateSpinner.setEditor(startEditor);
        endDateSpinner.setEditor(endEditor);

        // Set preferred size
        startDateSpinner.setPreferredSize(new Dimension(200, 30));
        endDateSpinner.setPreferredSize(new Dimension(200, 30));

        // Add components to the date panel
        datePanel.add(Box.createVerticalStrut(1));
        datePanel.add(startDateSpinner);
        datePanel.add(Box.createVerticalStrut(1));
        datePanel.add(endDateSpinner);

        // Filter button
        filterDateButton = new CustomButton("Lọc");
        filterDateButton.setPreferredSize(new Dimension(100, 30));
        filterDateButton.setMaximumSize(new Dimension(100, 30));
        filterDateButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        filterDateButton.addActionListener(e -> locTheoNgay());

        datePanel.add(Box.createVerticalStrut(5));
        datePanel.add(filterDateButton);
    }



    private void setupUserFilterPanel() {
        JPanel userPanel = createTitledPanel("Nhân Viên", 230, 150);
        userPanel.setLayout(new BorderLayout(5, 5));
        bottomPanelLeft.add(userPanel);

        // Tạo danh sách người dùng
        ArrayList<nhanVienDTO> danhSachNhanVien = NhanVienBUS.layDanhSachNhanVien();
        String[] users = new String[danhSachNhanVien.size() + 1];
        users[0] = "- Tất cả -";
        for (int i = 0; i < danhSachNhanVien.size(); i++) {
            users[i + 1] = danhSachNhanVien.get(i).getHoTen();
        }

        userList = new JList<>(users);
        userList.setLayoutOrientation(JList.VERTICAL);
        userList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userList.setVisibleRowCount(6);

        // Thêm sự kiện khi chọn người dùng
        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && userList.getSelectedIndex() != -1) {
                    String selectedUser = userList.getSelectedValue();
                    locTheoNguoiDung(selectedUser);
                }
            }
        });

        JScrollPane listScroller = new JScrollPane(userList);
        listScroller.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        userPanel.add(listScroller, BorderLayout.CENTER);
    }

    //======================CÀI ĐẶT PANEL DƯỚI PHẢI=================================
    private void setupBottomPanelRight() {
        // Change layout to BorderLayout
        bottomPanelRight.setLayout(new BorderLayout(0, 10));

        // Tạo panel cho bảng hóa đơn
        JPanel hoaDonTablePanel = createHoaDonTablePanel();

        // Tạo panel cho bảng chi tiết hóa đơn
        JPanel chiTietTablePanel = createChiTietTablePanel();

        // Tạo JSplitPane để chia đôi màn hình
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, hoaDonTablePanel, chiTietTablePanel);
        splitPane.setResizeWeight(0.6); // 60% cho bảng hóa đơn, 40% cho bảng chi tiết
        splitPane.setDividerSize(5);
        splitPane.setBorder(null);

        // Add panels to bottomPanelRight
        bottomPanelRight.add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Tạo panel chứa bảng hóa đơn
     */
    private JPanel createHoaDonTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Table data
        String[] columnNames = {"Mã hóa đơn", "Thời gian", "Khách hàng", "Tổng tiền hàng", "Giảm giá", "Khách đã trả"};

        // Tạo model cho bảng hóa đơn
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        // Lấy dữ liệu hóa đơn từ cơ sở dữ liệu
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        hoaDonBUS.docDanhSachHoaDon();
        ArrayList<hoaDonDTO> dsHoaDon = hoaDonBUS.getDsHoaDon();

        // Nếu không có dữ liệu, sử dụng dữ liệu mẫu
        if (dsHoaDon == null || dsHoaDon.isEmpty()) {
            Object[][] data = {
                    {"HD000046", "17/02/2025 23:36", "Tuấn - Hà Nội", 2580000, 0, 2580000},
                    {"HD000045", "16/02/2025 23:35", "Phạm Thu Hương", 55000, 0, 55000},
                    {"HD000044", "15/02/2025 23:35", "Tuấn - Hà Nội", 190000, 0, 190000},
                    {"HD000043", "14/02/2025 23:33", "Anh Hoàng - Sài Gòn", 195000, 0, 195000},
                    {"HD000042", "13/02/2025 23:32", "Anh Hoàng - Sài Gòn", 145000, 0, 145000},
                    {"HD000041", "12/02/2025 23:31", "Tuấn - Hà Nội", 150000, 0, 150000},
                    {"HD000040", "11/02/2025 23:30", "Anh Hoàng - Sài Gòn", 135000, 0, 135000},
                    {"HD000039", "10/02/2025 23:30", "Phạm Thu Hương", 190000, 0, 190000},
                    {"HD000038", "09/02/2025 23:29", "Anh Hoàng - Sài Gòn", 55000, 0, 55000},
                    {"HD000037", "08/02/2025 23:29", "Anh Hoàng - Sài Gòn", 55000, 0, 55000},
                    {"HD000036", "07/02/2025 23:26", "Tuấn - Hà Nội", 275000, 0, 275000},
                    {"HD000035", "06/02/2025 23:25", "Phạm Thu Hương", 95000, 0, 95000},
                    {"HD000034", "05/02/2025 23:24", "Phạm Thu Hương", 110000, 0, 110000},
            };
            model = new DefaultTableModel(data, columnNames);
        } else {
            // Thêm dữ liệu từ danh sách hóa đơn vào model
            for (hoaDonDTO hoaDon : dsHoaDon) {
                // TODO: Lấy tên khách hàng từ mã khách hàng
                String tenKhachHang = "Khách hàng"; // Cần thay thế bằng tên khách hàng thực tế

                Object[] row = {
                    hoaDon.getMaHoaDon(),
                    hoaDon.getThoiGianLap().toString(),
                    tenKhachHang,
                    hoaDon.getTongTien(),
                    0, // Giảm giá - cần tính toán dựa trên mã khuyến mãi
                    hoaDon.getTongTien() // Khách đã trả - giả sử bằng tổng tiền
                };
                model.addRow(row);
            }
        }
        hoaDonTable = new CustomTable(model);
        JScrollPane tableScrollPane = new JScrollPane(hoaDonTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Thêm sự kiện khi chọn hóa đơn
        hoaDonTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && hoaDonTable.getSelectedRow() != -1) {
                    String maHoaDon = hoaDonTable.getValueAt(hoaDonTable.getSelectedRow(), 0).toString();
                    updateChiTietHoaDonTable(maHoaDon);
                }
            }
        });

        return tablePanel;
    }

    /**
     * Tạo panel chứa bảng chi tiết hóa đơn
     */
    private JPanel createChiTietTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Tạo tiêu đề cho bảng chi tiết
        JLabel titleLabel = new JLabel("Chi tiết hóa đơn");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tablePanel.add(titleLabel, BorderLayout.NORTH);

        // Tạo bảng chi tiết hóa đơn với dữ liệu trống ban đầu
        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn vị tính", "Đơn giá", "Thành tiền"};
        Object[][] data = {}; // Dữ liệu trống ban đầu

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        chiTietHoaDonTable = new CustomTable(model);
        JScrollPane tableScrollPane = new JScrollPane(chiTietHoaDonTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    /**
     * Cập nhật bảng chi tiết hóa đơn dựa trên mã hóa đơn được chọn
     * @param maHoaDon Mã hóa đơn được chọn
     */
    private void updateChiTietHoaDonTable(String maHoaDon) {
        // Tạo model mới cho bảng chi tiết
        DefaultTableModel model = (DefaultTableModel) chiTietHoaDonTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        // Lấy dữ liệu từ cơ sở dữ liệu thông qua BUS
        ArrayList<chiTietHoaDonDTO> dsChiTiet = ChiTietHoaDonBUS.layChiTietHoaDonTheoMaHD(maHoaDon);

        // Nếu không có dữ liệu, sử dụng dữ liệu mẫu
        if (dsChiTiet == null || dsChiTiet.isEmpty()) {
            dsChiTiet = taoChiTietHoaDonMau(maHoaDon);
        }

        // Thêm dữ liệu vào bảng
        for (chiTietHoaDonDTO chiTiet : dsChiTiet) {
            Object[] rowData = {
                chiTiet.getMaSP(),
                chiTiet.getTenSP(),
                chiTiet.getSoLuong(),
                chiTiet.getDonViTinh(),
                chiTiet.getGiaBanFormatted(),
                chiTiet.getThanhTienFormatted()
            };
            model.addRow(rowData);
        }
    }

    /**
     * Làm mới bảng hóa đơn
     */
    private void refreshHoaDonTable() {
        // Lấy dữ liệu hóa đơn từ cơ sở dữ liệu
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        hoaDonBUS.docDanhSachHoaDon();
        ArrayList<hoaDonDTO> dsHoaDon = hoaDonBUS.getDsHoaDon();

        // Tạo model mới cho bảng hóa đơn
        DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        // Nếu không có dữ liệu, sử dụng dữ liệu mẫu
        if (dsHoaDon == null || dsHoaDon.isEmpty()) {
            Object[][] data = {
                    {"HD000046", "17/02/2025 23:36", "Tuấn - Hà Nội", 2580000, 0, 2580000},
                    {"HD000045", "16/02/2025 23:35", "Phạm Thu Hương", 55000, 0, 55000},
                    {"HD000044", "15/02/2025 23:35", "Tuấn - Hà Nội", 190000, 0, 190000},
                    {"HD000043", "14/02/2025 23:33", "Anh Hoàng - Sài Gòn", 195000, 0, 195000},
                    {"HD000042", "13/02/2025 23:32", "Anh Hoàng - Sài Gòn", 145000, 0, 145000},
                    {"HD000041", "12/02/2025 23:31", "Tuấn - Hà Nội", 150000, 0, 150000},
                    {"HD000040", "11/02/2025 23:30", "Anh Hoàng - Sài Gòn", 135000, 0, 135000},
                    {"HD000039", "10/02/2025 23:30", "Phạm Thu Hương", 190000, 0, 190000},
                    {"HD000038", "09/02/2025 23:29", "Anh Hoàng - Sài Gòn", 55000, 0, 55000},
                    {"HD000037", "08/02/2025 23:29", "Anh Hoàng - Sài Gòn", 55000, 0, 55000},
                    {"HD000036", "07/02/2025 23:26", "Tuấn - Hà Nội", 275000, 0, 275000},
                    {"HD000035", "06/02/2025 23:25", "Phạm Thu Hương", 95000, 0, 95000},
                    {"HD000034", "05/02/2025 23:24", "Phạm Thu Hương", 110000, 0, 110000},
            };
            for (Object[] row : data) {
                model.addRow(row);
            }
        } else {
            // Thêm dữ liệu từ danh sách hóa đơn vào model
            for (hoaDonDTO hoaDon : dsHoaDon) {
                // TODO: Lấy tên khách hàng từ mã khách hàng
                String tenKhachHang = "Khách hàng"; // Cần thay thế bằng tên khách hàng thực tế

                Object[] row = {
                    hoaDon.getMaHoaDon(),
                    hoaDon.getThoiGianLap().toString(),
                    tenKhachHang,
                    hoaDon.getTongTien(),
                    0, // Giảm giá - cần tính toán dựa trên mã khuyến mãi
                    hoaDon.getTongTien() // Khách đã trả - giả sử bằng tổng tiền
                };
                model.addRow(row);
            }
        }

        // Xóa dữ liệu trong bảng chi tiết
        DefaultTableModel chiTietModel = (DefaultTableModel) chiTietHoaDonTable.getModel();
        chiTietModel.setRowCount(0);
    }

    /**
     * Tạo dữ liệu mẫu cho chi tiết hóa đơn
     * @param maHoaDon Mã hóa đơn
     * @return Danh sách chi tiết hóa đơn mẫu
     */
    private ArrayList<chiTietHoaDonDTO> taoChiTietHoaDonMau(String maHoaDon) {
        ArrayList<chiTietHoaDonDTO> dsChiTiet = new ArrayList<>();

        // Tạo dữ liệu mẫu khác nhau cho từng hóa đơn
        if (maHoaDon.equals("HD000046")) {
            dsChiTiet.add(new chiTietHoaDonDTO(maHoaDon, "SP001", 2, 1200000, "iPhone 13", "Chiếc"));
            dsChiTiet.add(new chiTietHoaDonDTO(maHoaDon, "SP005", 1, 180000, "Tai nghe Bluetooth", "Chiếc"));
        } else if (maHoaDon.equals("HD000045")) {
            dsChiTiet.add(new chiTietHoaDonDTO(maHoaDon, "SP010", 1, 55000, "Ốp lưng iPhone", "Chiếc"));
        } else if (maHoaDon.equals("HD000044")) {
            dsChiTiet.add(new chiTietHoaDonDTO(maHoaDon, "SP008", 1, 190000, "Sạc dự phòng", "Chiếc"));
        } else if (maHoaDon.equals("HD000043")) {
            dsChiTiet.add(new chiTietHoaDonDTO(maHoaDon, "SP007", 1, 150000, "Chuột không dây", "Chiếc"));
            dsChiTiet.add(new chiTietHoaDonDTO(maHoaDon, "SP009", 1, 45000, "Bàn di chuột", "Chiếc"));
        } else {
            // Mặc định cho các hóa đơn khác
            dsChiTiet.add(new chiTietHoaDonDTO(maHoaDon, "SP002", 1, 95000, "Ốp lưng Samsung", "Chiếc"));
            dsChiTiet.add(new chiTietHoaDonDTO(maHoaDon, "SP003", 1, 50000, "Cáp sạc Type-C", "Chiếc"));
        }

        return dsChiTiet;
    }

    /**
     * Tìm kiếm hóa đơn theo từ khóa
     * @param keyword Từ khóa tìm kiếm
     */
    private void timKiemHoaDon(String keyword) {
        // Tạo model mới cho bảng hóa đơn
        DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        // Lấy dữ liệu hóa đơn từ cơ sở dữ liệu
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        ArrayList<hoaDonDTO> dsHoaDon = hoaDonBUS.timKiemHoaDon(keyword);

        // Nếu không có dữ liệu, hiển thị thông báo
        if (dsHoaDon == null || dsHoaDon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn nào phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Thêm dữ liệu từ danh sách hóa đơn vào model
        for (hoaDonDTO hoaDon : dsHoaDon) {
            // TODO: Lấy tên khách hàng từ mã khách hàng
            String tenKhachHang = "Khách hàng"; // Cần thay thế bằng tên khách hàng thực tế

            Object[] row = {
                hoaDon.getMaHoaDon(),
                hoaDon.getThoiGianLap().toString(),
                tenKhachHang,
                hoaDon.getTongTien(),
                0, // Giảm giá - cần tính toán dựa trên mã khuyến mãi
                hoaDon.getTongTien() // Khách đã trả - giả sử bằng tổng tiền
            };
            model.addRow(row);
        }

        // Xóa dữ liệu trong bảng chi tiết
        DefaultTableModel chiTietModel = (DefaultTableModel) chiTietHoaDonTable.getModel();
        chiTietModel.setRowCount(0);
    }

    /**
     * Lọc hóa đơn theo khoảng thời gian
     */
    private void locTheoNgay() {
        // Lấy ngày bắt đầu và ngày kết thúc từ spinner
        Date tuNgay = (Date) startDateSpinner.getValue();
        Date denNgay = (Date) endDateSpinner.getValue();

        // Kiểm tra ngày hợp lệ
        if (tuNgay.after(denNgay)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không thể sau ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển đổi Date sang LocalDateTime
        Calendar calTuNgay = Calendar.getInstance();
        calTuNgay.setTime(tuNgay);
        calTuNgay.set(Calendar.HOUR_OF_DAY, 0);
        calTuNgay.set(Calendar.MINUTE, 0);
        calTuNgay.set(Calendar.SECOND, 0);

        Calendar calDenNgay = Calendar.getInstance();
        calDenNgay.setTime(denNgay);
        calDenNgay.set(Calendar.HOUR_OF_DAY, 23);
        calDenNgay.set(Calendar.MINUTE, 59);
        calDenNgay.set(Calendar.SECOND, 59);

        // Chuyển đổi sang LocalDateTime
        LocalDateTime tuNgayLDT = LocalDateTime.ofInstant(calTuNgay.toInstant(), calTuNgay.getTimeZone().toZoneId());
        LocalDateTime denNgayLDT = LocalDateTime.ofInstant(calDenNgay.toInstant(), calDenNgay.getTimeZone().toZoneId());

        // Lấy dữ liệu hóa đơn từ cơ sở dữ liệu
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        ArrayList<hoaDonDTO> dsHoaDon = hoaDonBUS.layHoaDonTheoKhoangThoiGian(tuNgayLDT, denNgayLDT);

        // Tạo model mới cho bảng hóa đơn
        DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        // Nếu không có dữ liệu, hiển thị thông báo
        if (dsHoaDon == null || dsHoaDon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn nào trong khoảng thời gian này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Thêm dữ liệu từ danh sách hóa đơn vào model
        for (hoaDonDTO hoaDon : dsHoaDon) {
            // TODO: Lấy tên khách hàng từ mã khách hàng
            String tenKhachHang = "Khách hàng"; // Cần thay thế bằng tên khách hàng thực tế

            Object[] row = {
                hoaDon.getMaHoaDon(),
                hoaDon.getThoiGianLap().toString(),
                tenKhachHang,
                hoaDon.getTongTien(),
                0, // Giảm giá - cần tính toán dựa trên mã khuyến mãi
                hoaDon.getTongTien() // Khách đã trả - giả sử bằng tổng tiền
            };
            model.addRow(row);
        }

        // Xóa dữ liệu trong bảng chi tiết
        DefaultTableModel chiTietModel = (DefaultTableModel) chiTietHoaDonTable.getModel();
        chiTietModel.setRowCount(0);
    }



    /**
     * Lọc hóa đơn theo người dùng
     * @param tenNguoiDung Tên người dùng cần lọc
     */
    private void locTheoNguoiDung(String tenNguoiDung) {
        // Nếu chọn "Tất cả", hiển thị tất cả hóa đơn
        if (tenNguoiDung.equals("- Tất cả -")) {
            refreshHoaDonTable();
            return;
        }

        // Tạo model mới cho bảng hóa đơn
        DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        // Lấy dữ liệu hóa đơn từ cơ sở dữ liệu
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        hoaDonBUS.docDanhSachHoaDon();
        ArrayList<hoaDonDTO> dsHoaDon = hoaDonBUS.getDsHoaDon();

        // Lấy danh sách nhân viên để tìm mã nhân viên từ tên
        ArrayList<nhanVienDTO> danhSachNhanVien = NhanVienBUS.layDanhSachNhanVien();
        String maNV = null;

        // Tìm mã nhân viên từ tên
        for (nhanVienDTO nv : danhSachNhanVien) {
            if (nv.getHoTen().equals(tenNguoiDung)) {
                maNV = nv.getMaNV();
                break;
            }
        }

        if (maNV == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Lọc hóa đơn theo mã nhân viên
        ArrayList<hoaDonDTO> dsHoaDonLoc = new ArrayList<>();
        for (hoaDonDTO hoaDon : dsHoaDon) {
            if (hoaDon.getMaNV() != null && hoaDon.getMaNV().equals(maNV)) {
                dsHoaDonLoc.add(hoaDon);
            }
        }

        // Nếu không có dữ liệu, hiển thị thông báo
        if (dsHoaDonLoc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn nào của nhân viên này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Thêm dữ liệu từ danh sách hóa đơn vào model
        for (hoaDonDTO hoaDon : dsHoaDonLoc) {
            // Lấy tên khách hàng từ mã khách hàng
            String tenKhachHang = "Khách hàng";
            if (hoaDon.getMaKH() != null && !hoaDon.getMaKH().isEmpty()) {
                KhachHangBUS khachHangBUS = new KhachHangBUS();
                KhachHangDTO khachHang = khachHangBUS.layKhachHangTheoMa(hoaDon.getMaKH());
                if (khachHang != null) {
                    tenKhachHang = khachHang.getHoTen();
                }
            }

            Object[] row = {
                hoaDon.getMaHoaDon(),
                hoaDon.getThoiGianLap().toString(),
                tenKhachHang,
                hoaDon.getTongTien(),
                0, // Giảm giá - cần tính toán dựa trên mã khuyến mãi
                hoaDon.getTongTien() // Khách đã trả - giả sử bằng tổng tiền
            };
            model.addRow(row);
        }

        // Xóa dữ liệu trong bảng chi tiết
        DefaultTableModel chiTietModel = (DefaultTableModel) chiTietHoaDonTable.getModel();
        chiTietModel.setRowCount(0);
    }

    /**
     * Lọc hóa đơn theo khách hàng
     * @param maKH Mã khách hàng cần lọc
     */
    public void locTheoKhachHang(String maKH) {
        // Tạo model mới cho bảng hóa đơn
        DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        // Lấy dữ liệu hóa đơn từ cơ sở dữ liệu
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        hoaDonBUS.docDanhSachHoaDon();
        ArrayList<hoaDonDTO> dsHoaDon = hoaDonBUS.getDsHoaDon();

        // Lọc theo mã khách hàng
        ArrayList<hoaDonDTO> dsHoaDonLoc = new ArrayList<>();
        for (hoaDonDTO hoaDon : dsHoaDon) {
            if (hoaDon.getMaKH() != null && hoaDon.getMaKH().equals(maKH)) {
                dsHoaDonLoc.add(hoaDon);
            }
        }

        // Nếu không có dữ liệu, hiển thị thông báo
        if (dsHoaDonLoc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn nào của khách hàng này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Lấy thông tin khách hàng
        KhachHangBUS khachHangBUS = new KhachHangBUS();
        KhachHangDTO khachHang = khachHangBUS.layKhachHangTheoMa(maKH);
        String tenKhachHang = (khachHang != null) ? khachHang.getHoTen() : "Khách hàng";

        // Thêm dữ liệu từ danh sách hóa đơn vào model
        for (hoaDonDTO hoaDon : dsHoaDonLoc) {
            Object[] row = {
                hoaDon.getMaHoaDon(),
                hoaDon.getThoiGianLap().toString(),
                tenKhachHang,
                hoaDon.getTongTien(),
                0, // Giảm giá - cần tính toán dựa trên mã khuyến mãi
                hoaDon.getTongTien() // Khách đã trả - giả sử bằng tổng tiền
            };
            model.addRow(row);
        }

        // Xóa dữ liệu trong bảng chi tiết
        DefaultTableModel chiTietModel = (DefaultTableModel) chiTietHoaDonTable.getModel();
        chiTietModel.setRowCount(0);
    }

    /**
     * Thiết lập panel lọc theo khách hàng
     */
    private void setupCustomerFilterPanel() {
        JPanel customerPanel = createTitledPanel("Khách Hàng", 230, 100);
        customerPanel.setLayout(new BorderLayout(5, 5));
        bottomPanelLeft.add(customerPanel);

        // Tạo panel chứa các thành phần tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBackground(Color.WHITE);

        // Tạo trường tìm kiếm khách hàng
        PlaceholderTextField customerSearchField = new PlaceholderTextField("Nhập mã hoặc tên khách hàng");
        customerSearchField.setPreferredSize(new Dimension(200, 30));
        customerSearchField.setMaximumSize(new Dimension(200, 30));
        customerSearchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Tạo nút tìm kiếm
        CustomButton customerSearchButton = new CustomButton("Tìm");
        customerSearchButton.setPreferredSize(new Dimension(80, 30));
        customerSearchButton.setMaximumSize(new Dimension(80, 30));
        customerSearchButton.setButtonColors(CustomButton.ButtonColors.BLUE);

        // Thêm sự kiện cho nút tìm kiếm
        customerSearchButton.addActionListener(e -> {
            String keyword = customerSearchField.getText().trim();
            if (!keyword.isEmpty()) {
                timKiemVaLocTheoKhachHang(keyword);
            }
        });

        // Tạo panel chứa trường tìm kiếm và nút tìm kiếm
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.add(customerSearchField);
        inputPanel.add(Box.createHorizontalStrut(5));
        inputPanel.add(customerSearchButton);

        // Thêm các thành phần vào panel tìm kiếm
        searchPanel.add(Box.createVerticalStrut(5));
        searchPanel.add(inputPanel);
        searchPanel.add(Box.createVerticalStrut(5));

        // Thêm panel tìm kiếm vào panel khách hàng
        customerPanel.add(searchPanel, BorderLayout.CENTER);
    }

    /**
     * Tìm kiếm khách hàng và lọc hóa đơn theo khách hàng
     * @param keyword Từ khóa tìm kiếm
     */
    private void timKiemVaLocTheoKhachHang(String keyword) {
        // Tìm kiếm khách hàng
        KhachHangBUS khachHangBUS = new KhachHangBUS();
        ArrayList<KhachHangDTO> dsKhachHang = khachHangBUS.timKiemKhachHang(keyword);

        // Nếu không tìm thấy khách hàng nào
        if (dsKhachHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Nếu tìm thấy nhiều khách hàng, hiển thị hộp thoại chọn khách hàng
        KhachHangDTO selectedKhachHang;
        if (dsKhachHang.size() > 1) {
            // Tạo mảng tên khách hàng để hiển thị trong hộp thoại
            String[] tenKhachHang = new String[dsKhachHang.size()];
            for (int i = 0; i < dsKhachHang.size(); i++) {
                tenKhachHang[i] = dsKhachHang.get(i).getMaKH() + " - " + dsKhachHang.get(i).getHoTen();
            }

            // Hiển thị hộp thoại chọn khách hàng
            String selected = (String) JOptionPane.showInputDialog(
                    this,
                    "Chọn khách hàng:",
                    "Tìm thấy nhiều khách hàng",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    tenKhachHang,
                    tenKhachHang[0]);

            // Nếu người dùng không chọn khách hàng nào
            if (selected == null) {
                return;
            }

            // Lấy mã khách hàng từ chuỗi đã chọn
            String maKH = selected.split(" - ")[0];
            selectedKhachHang = khachHangBUS.layKhachHangTheoMa(maKH);
        } else {
            // Nếu chỉ tìm thấy một khách hàng
            selectedKhachHang = dsKhachHang.get(0);
        }

        // Lọc hóa đơn theo khách hàng đã chọn
        locTheoKhachHang(selectedKhachHang.getMaKH());
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

    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        radioButton.setBackground(Color.WHITE);
        return radioButton;
    }

    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new hoaDonPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
