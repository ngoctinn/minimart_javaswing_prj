package org.example.GUI;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.KhachHangBUS;
import org.example.BUS.KhuyenMaiBUS;
import org.example.BUS.LoginBUS;
import org.example.BUS.LoaiSanPhamBUS;
import org.example.BUS.SanPhamBUS;
import org.example.Components.*;
import org.example.DTO.KhachHangDTO;
import org.example.DTO.LoaiSanPhamDTO;
import org.example.DTO.SanPhamDTO;
import org.example.DTO.chiTietHoaDonDTO;
import org.example.DTO.hoaDonDTO;
import org.example.DTO.khuyenMaiDTO;
import org.example.GUI.Dialogs.ThanhToanDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BanHangPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;

    // Top panel components
    private CustomTextField searchField;
    private CustomButton searchButton;
    private CustomButton addTabButton;
    private RoundedPanel customerInfoPanel; // Panel hiển thị thông tin khách hàng

    // Bottom panel components
    private JTabbedPane tabbedPane;
    private JPanel productsPanel;
    private int tabCounter = 1;

    // Giỏ hàng
    private List<List<CartItem>> cartItemsByTab = new ArrayList<>();
    private double[] totalAmountByTab = new double[100]; // Giả sử không quá 100 tab

    // Hóa đơn cho mỗi tab
    private List<hoaDonDTO> hoaDonByTab = new ArrayList<>();
    private KhachHangDTO selectedCustomer = null;
    private String selectedKhuyenMaiMa = null;

    // Định dạng tiền tệ
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private Color priceColor = new Color(0, 100, 180); // Màu xanh cho giá tiền
    private JLabel diemTichLuyValue;
    private JLabel tenKHValue;

    public BanHangPanel() {
        initGUI();
    }

    /**
     * Khởi tạo giao diện chính
     */
    private void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanelLeft();
        setupBottomPanelRight();

        // Thêm các panel vào panel chính
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel);
        bottomPanel.add(bottomPanelLeft, BorderLayout.CENTER);
        bottomPanel.add(bottomPanelRight, BorderLayout.EAST);
    }

    /**
     * Thiết lập panel chính
     */
    private void setupMainPanel() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(new Color(225, 225, 225));
        this.setVisible(true);
    }

    /**
     * Tạo các panel con
     */
    private void createPanels() {
        // Tạo các panel con
        topPanel = new RoundedPanel(20);
        bottomPanel = new RoundedPanel(20);
        bottomPanelLeft = new RoundedPanel(20);
        bottomPanelRight = new RoundedPanel(20);

        // Thiết lập màu nền
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(new Color(225, 225, 225));
        bottomPanelLeft.setBackground(Color.WHITE);
        bottomPanelRight.setBackground(Color.WHITE);

        // Thiết lập kích thước cho panel bên phải
        bottomPanelRight.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.4), 0));

        // Thiết lập layout
        topPanel.setLayout(new BorderLayout(10, 10));
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new BorderLayout());
        bottomPanelRight.setLayout(new BorderLayout());
    }

    /**
     * Thiết lập panel trên cùng
     */
    private void setupTopPanel() {
        // Panel bên trái chứa ô tìm kiếm và nút tìm kiếm
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftPanel.setOpaque(false);

        // Ô tìm kiếm
        searchField = new CustomTextField("Tìm kiếm khách hàng...");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        leftPanel.add(searchField);

        // Nút tìm kiếm
        searchButton = new CustomButton("Tìm");
        searchButton.setPreferredSize(new Dimension(70, 30));
        searchButton.addActionListener(e -> searchCustomer());
        leftPanel.add(searchButton);

        // Thêm panel bên trái vào topPanel
        topPanel.add(leftPanel, BorderLayout.WEST);

        // Tạo panel hiển thị thông tin khách hàng ở giữa
        customerInfoPanel = createCustomerInfoPanel();
        topPanel.add(customerInfoPanel, BorderLayout.CENTER);

        // Panel bên phải chứa nút thêm hóa đơn mới
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setOpaque(false);

        // Nút thêm hóa đơn mới
        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg", 16, 16);
        addTabButton = new CustomButton("Thêm hóa đơn", addIcon);
        addTabButton.setPreferredSize(new Dimension(150, 30));
        addTabButton.addActionListener(e -> addNewTab());
        rightPanel.add(addTabButton);

        // Thêm panel bên phải vào topPanel
        topPanel.add(rightPanel, BorderLayout.EAST);
    }

    /**
     * Tạo panel hiển thị thông tin khách hàng
     */
    private RoundedPanel createCustomerInfoPanel() {
        RoundedPanel panel = new RoundedPanel(15); // Panel bo góc
        panel.setBackground(new Color(230, 240, 250)); // Màu nền xanh dương pastel sáng
        panel.setLayout(new GridLayout(2, 2, 10, 5)); // Bố cục 2 dòng, 2 cột
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); // Padding bên trong

        // Tạo các JLabel cho thông tin
        JLabel tenKHLabel = new JLabel("Tên khách hàng:");
        tenKHLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tenKHValue = new JLabel("Chưa chọn");
        tenKHValue.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel diemTichLuyLabel = new JLabel("Điểm tích lũy:");
        diemTichLuyLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        diemTichLuyValue = new JLabel("0");
        diemTichLuyValue.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Thêm vào panel
        panel.add(tenKHLabel);
        panel.add(tenKHValue);
        panel.add(diemTichLuyLabel);
        panel.add(diemTichLuyValue);

        // Gắn vào clientProperty để dễ cập nhật sau
        panel.putClientProperty("tenKHValue", tenKHValue);
        panel.putClientProperty("diemTichLuyValue", diemTichLuyValue);

        return panel;
    }

    /**
     * Tìm kiếm khách hàng theo từ khóa
     */
    private void searchCustomer() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Tìm kiếm khách hàng theo số điện thoại
        KhachHangDTO khachHang = KhachHangBUS.layKhachHangTheoSDT(keyword);
        if (khachHang == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Cập nhật thông tin khách hàng
        selectedCustomer = khachHang;
        updateCustomerInfo(khachHang);

        // Cập nhật mã khách hàng cho hóa đơn hiện tại
        int currentTab = tabbedPane.getSelectedIndex();
        if (currentTab >= 0 && currentTab < hoaDonByTab.size()) {
            hoaDonByTab.get(currentTab).setMaKH(khachHang.getMaKH());
        }
    }

    /**
     * Cập nhật thông tin khách hàng trên panel
     */
    private void updateCustomerInfo(KhachHangDTO khachHang) {
        if (khachHang != null) {
            tenKHValue.setText(khachHang.getHoTen());
            diemTichLuyValue.setText(String.valueOf(khachHang.getDiemTichLuy()));
        }
    }

    /**
     * Thiết lập panel bên trái phía dưới
     */
    private void setupBottomPanelLeft() {
        // Tạo tabbed pane có thể đóng tab
        tabbedPane = new JTabbedPane();
        tabbedPane.putClientProperty("JTabbedPane.tabClosable", true);
        tabbedPane.putClientProperty("JTabbedPane.tabCloseCallback",
                (java.util.function.BiConsumer<JTabbedPane, Integer>) (tabPane, tabIndex) -> {
                    if (tabIndex > 0) { // Không đóng tab đầu tiên
                        int confirm = JOptionPane.showConfirmDialog(
                                this,
                                "Bạn có chắc muốn đóng hóa đơn này?",
                                "Xác nhận đóng",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            tabPane.removeTabAt(tabIndex);
                            // Xóa giỏ hàng của tab bị đóng
                            if (tabIndex < cartItemsByTab.size()) {
                                cartItemsByTab.remove(tabIndex);
                                totalAmountByTab[tabIndex] = 0;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Không thể đóng hóa đơn mặc định!",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                });

        // Thêm tab mặc định
        JPanel defaultPanel = createInvoicePanel();
        tabbedPane.addTab("Hóa đơn 1", defaultPanel);

        // Khởi tạo giỏ hàng cho tab đầu tiên
        cartItemsByTab.add(new ArrayList<>());

        // Tạo hóa đơn mới cho tab đầu tiên
        String maNV = LoginBUS.getCurrentUser() != null ? LoginBUS.getCurrentUser().getMaNV() : "NV001";

        bottomPanelLeft.add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Thêm tab hóa đơn mới
     */
    private void addNewTab() {
        tabCounter++;
        String tabTitle = "Hóa đơn " + tabCounter;
        JPanel newPanel = createInvoicePanel();
        tabbedPane.addTab(tabTitle, newPanel);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);

        // Khởi tạo giỏ hàng cho tab mới
        cartItemsByTab.add(new ArrayList<>());

        // Tạo hóa đơn mới cho tab
        String maNV = LoginBUS.getCurrentUser() != null ? LoginBUS.getCurrentUser().getMaNV() : "NV001";
    }

    /**
     * Tạo panel hóa đơn
     */
    private JPanel createInvoicePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Tạo panel chứa giỏ hàng với layout dạng BorderLayout
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBackground(Color.WHITE);

        // Panel chứa tiêu đề các cột
        JPanel headerPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        Font headerFont = new Font("Segoe UI", Font.BOLD, 14);
        Color headerColor = new Color(60,60,60);

        JLabel nameHeader = new JLabel("Tên sản phẩm", SwingConstants.CENTER);
        nameHeader.setFont(headerFont);
        nameHeader.setForeground(headerColor);

        JLabel qtyHeader = new JLabel("Số lượng", SwingConstants.CENTER);
        qtyHeader.setFont(headerFont);
        qtyHeader.setForeground(headerColor);

        JLabel totalHeader = new JLabel("Thành tiền", SwingConstants.CENTER);
        totalHeader.setFont(headerFont);
        totalHeader.setForeground(headerColor);

        JLabel actionHeader = new JLabel("Thao tác", SwingConstants.CENTER);
        actionHeader.setFont(headerFont);
        actionHeader.setForeground(headerColor);

        headerPanel.add(nameHeader);
        headerPanel.add(qtyHeader);
        headerPanel.add(totalHeader);
        headerPanel.add(actionHeader);

        // Panel chính chứa danh sách các sản phẩm trong giỏ hàng
        JPanel cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        cartItemsPanel.setBackground(Color.WHITE);
        cartItemsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Tạo JScrollPane cho danh sách sản phẩm
        JScrollPane scrollPane = new JScrollPane(cartItemsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));

        // Panel hiển thị tổng tiền
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        totalPanel.setBackground(new Color(250, 250, 255));

        JLabel totalLabel = new JLabel("TỔNG TIỀN: " + currencyFormat.format(0), SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLabel.setForeground(priceColor);

        totalPanel.add(totalLabel, BorderLayout.EAST);

        // Thêm tất cả vào cartPanel
        cartPanel.add(headerPanel, BorderLayout.NORTH);
        cartPanel.add(scrollPane, BorderLayout.CENTER);
        cartPanel.add(totalPanel, BorderLayout.SOUTH);
        panel.add(cartPanel, BorderLayout.CENTER);

        // Lưu trữ các component quan trọng vào clientProperty để truy cập sau này
        panel.putClientProperty("cartItemsPanel", cartItemsPanel);
        panel.putClientProperty("totalLabel", totalLabel);

        return panel;
    }

    /**
     * Lớp đại diện cho một mục trong giỏ hàng
     */
    public static class CartItem {
        private SanPhamDTO sanPham;
        private int quantity;
        private JPanel panel;
        private JTextField qtyField;
        private JLabel totalItemLabel;

        public CartItem(SanPhamDTO sanPham, int quantity, JPanel panel, JTextField qtyField, JLabel totalItemLabel) {
            this.sanPham = sanPham;
            this.quantity = quantity;
            this.panel = panel;
            this.qtyField = qtyField;
            this.totalItemLabel = totalItemLabel;
        }

        public SanPhamDTO getSanPham() {
            return sanPham;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
            this.qtyField.setText(String.valueOf(quantity));
            this.totalItemLabel.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(getTotalPrice()));
        }

        public double getTotalPrice() {
            return sanPham.getGiaBan() * quantity;
        }
    }

    /**
     * Thiết lập panel bên phải phía dưới
     */
    private void setupBottomPanelRight() {
        bottomPanelRight.setLayout(new BorderLayout());

        // Tạo panel chứa ô tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchPanel.setBackground(Color.WHITE);

        // Panel bên trái chứa combobox (35%)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension((int)(bottomPanelRight.getPreferredSize().width * 0.35), 30));

        //load dữ liệu loại sản phẩm
        ArrayList<LoaiSanPhamDTO> loaiSanPhamDTOArrayList = new LoaiSanPhamBUS().layDanhSachLoaiSanPham();
        String[] loaiSanPham = new String[loaiSanPhamDTOArrayList.size()];
        for (int i = 0; i < loaiSanPhamDTOArrayList.size(); i++) {
            loaiSanPham[i] = loaiSanPhamDTOArrayList.get(i).getTenLSP();
        }

        CustomCombobox<String> loaiSanPhamComboBox = new CustomCombobox<>(loaiSanPham);
        loaiSanPhamComboBox.setPlaceholder("- Chọn loại sản phẩm -");
        loaiSanPhamComboBox.setPreferredSize(new Dimension(150, 30));
        loaiSanPhamComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        // Thêm sự kiện khi chọn loại sản phẩm
        loaiSanPhamComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedLoaiSP = (String) loaiSanPhamComboBox.getSelectedItem();
                if (selectedLoaiSP != null && !selectedLoaiSP.isEmpty()) {
                    loadProductsByCategory(selectedLoaiSP);
                } else {
                    loadAllProducts(); // Nếu không chọn loại nào, hiển thị tất cả sản phẩm
                }
            }
        });

        leftPanel.add(loaiSanPhamComboBox, BorderLayout.CENTER);

        // Panel bên phải chứa thanh tìm kiếm và các nút (65%)
        JPanel rightPanel = new JPanel(new BorderLayout(5, 0));
        rightPanel.setBackground(Color.WHITE);

        // Ô nhập tìm kiếm sản phẩm
        CustomTextField searchProductField = new CustomTextField("Tìm kiếm sản phẩm...");
        searchProductField.setPreferredSize(new Dimension(200, 30));
        searchProductField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        // Panel chứa nút refresh
        JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 5, 0));
        buttonPanel.setBackground(Color.WHITE);

        // Nút refresh tìm kiếm với icon
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        CustomButton refreshButton = new CustomButton("");
        refreshButton.setIcon(refreshIcon);
        refreshButton.setPreferredSize(new Dimension(40, 30));
        refreshButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        refreshButton.addActionListener(e -> {
            searchProductField.setText("");
            loadAllProducts();
        });

        // Thêm nút refresh vào panel
        buttonPanel.add(refreshButton);

        // Thêm ô tìm kiếm và panel nút vào panel phải
        rightPanel.add(searchProductField, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.EAST);

        // Thêm sự kiện cho ô tìm kiếm
        searchProductField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchText = searchProductField.getText().trim();
                    if (!searchText.isEmpty()) {
                        loadFilteredProducts(searchText);
                    } else {
                        loadAllProducts();
                    }
                }
            }
        });

        // Thêm panel trái và phải vào searchPanel
        searchPanel.add(leftPanel, BorderLayout.WEST);
        searchPanel.add(rightPanel, BorderLayout.CENTER);

        // Thêm searchPanel vào bottomPanelRight
        bottomPanelRight.add(searchPanel, BorderLayout.NORTH);

        // Panel chứa danh sách sản phẩm
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(Color.WHITE);

        productsPanel = new JPanel(new GridBagLayout());
        productsPanel.setBackground(Color.WHITE);
        productsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tải sản phẩm từ database
        loadAllProducts();

        // Đặt productsPanel vào panel trung gian để căn giữa
        JPanel centeringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centeringPanel.setBackground(Color.WHITE);
        centeringPanel.add(productsPanel);

        containerPanel.add(centeringPanel, BorderLayout.CENTER);

        // Bọc containerPanel trong JScrollPane
        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bottomPanelRight.add(scrollPane, BorderLayout.CENTER);

        // Thêm nút thanh toán ở dưới cùng
        CustomButton btnThanhToan = new CustomButton("Thanh toán");
        btnThanhToan.setPreferredSize(new Dimension(100, 45));
        btnThanhToan.addActionListener(e -> {
            int currentTab = tabbedPane.getSelectedIndex();
            double totalAmount = totalAmountByTab[currentTab];

            if (totalAmount <= 0) {
                JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Lấy thông tin khách hàng và nhân viên
            String maKhachHang = selectedCustomer != null ? selectedCustomer.getMaKH() : "KH001"; // Mặc định là khách hàng vãng lai
            String maNhanVien = LoginBUS.getCurrentUser() != null ? LoginBUS.getCurrentUser().getMaNV() : "NV001";

            // Lấy danh sách sản phẩm trong giỏ hàng
            List<CartItem> cartItems = cartItemsByTab.get(currentTab);

            // Hiển thị dialog thanh toán
            ThanhToanDialog dialog = new ThanhToanDialog(SwingUtilities.getWindowAncestor(this), totalAmount, maKhachHang, maNhanVien, cartItems);
            dialog.setVisible(true);

            // Nếu thanh toán thành công, xóa giỏ hàng
            if (dialog.isPaymentSuccessful()) {
                // Xóa sản phẩm trong giỏ hàng
                JPanel invoicePanel = (JPanel) tabbedPane.getComponentAt(currentTab);
                JPanel cartItemsPanel = (JPanel) invoicePanel.getClientProperty("cartItemsPanel");
                JLabel totalLabel = (JLabel) invoicePanel.getClientProperty("totalLabel");

                cartItemsPanel.removeAll();
                cartItemsByTab.get(currentTab).clear();
                totalAmountByTab[currentTab] = 0;
                totalLabel.setText("TỔNG TIỀN: " + currencyFormat.format(0));

                cartItemsPanel.revalidate();
                cartItemsPanel.repaint();

                JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // Thêm panel chứa nút thanh toán vào bottomPanelRight
        JPanel thanhToanPanel = new JPanel(new BorderLayout());
        thanhToanPanel.add(btnThanhToan, BorderLayout.CENTER);
        thanhToanPanel.setBackground(Color.WHITE);
        thanhToanPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        bottomPanelRight.add(thanhToanPanel, BorderLayout.SOUTH);
    }

    /**
     * Tải tất cả sản phẩm từ database
     */
    private void loadAllProducts() {
        productsPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;

        ArrayList<SanPhamDTO> danhSachSanPham = SanPhamBUS.layDanhSachSanPham();

        int row = 0;
        int col = 0;

        for (SanPhamDTO sanPham : danhSachSanPham) {
            JPanel productPanel = createProductPanel(sanPham);

            // Đặt kích thước cố định cho panel sản phẩm
            productPanel.setPreferredSize(new Dimension(150, 200));

            gbc.gridx = col;
            gbc.gridy = row;
            productsPanel.add(productPanel, gbc);

            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }

        productsPanel.revalidate();
        productsPanel.repaint();
    }

    /**
     * Tải sản phẩm theo điều kiện tìm kiếm
     */
    private void loadFilteredProducts(String searchText) {
        productsPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;

        ArrayList<SanPhamDTO> danhSachSanPham = SanPhamBUS.timKiemSanPham(searchText);

        int row = 0;
        int col = 0;

        for (SanPhamDTO sanPham : danhSachSanPham) {
            JPanel productPanel = createProductPanel(sanPham);

            // Đặt kích thước cố định cho panel sản phẩm
            productPanel.setPreferredSize(new Dimension(150, 200));

            gbc.gridx = col;
            gbc.gridy = row;
            productsPanel.add(productPanel, gbc);

            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }

        productsPanel.revalidate();
        productsPanel.repaint();
    }

    /**
     * Tải sản phẩm theo loại sản phẩm
     */
    private void loadProductsByCategory(String tenLoaiSP) {
        productsPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;

        // Lấy mã loại sản phẩm từ tên loại sản phẩm
        String maLoaiSP = "";
        ArrayList<LoaiSanPhamDTO> danhSachLoaiSP = new LoaiSanPhamBUS().layDanhSachLoaiSanPham();
        for (LoaiSanPhamDTO loaiSP : danhSachLoaiSP) {
            if (loaiSP.getTenLSP().equals(tenLoaiSP)) {
                maLoaiSP = loaiSP.getMaLSP();
                break;
            }
        }

        // Lấy danh sách sản phẩm theo mã loại sản phẩm
        ArrayList<SanPhamDTO> danhSachSanPham = SanPhamBUS.layDanhSachSanPhamTheoLoai(maLoaiSP);

        int row = 0;
        int col = 0;

        for (SanPhamDTO sanPham : danhSachSanPham) {
            JPanel productPanel = createProductPanel(sanPham);

            // Đặt kích thước cố định cho panel sản phẩm
            productPanel.setPreferredSize(new Dimension(150, 200));

            gbc.gridx = col;
            gbc.gridy = row;
            productsPanel.add(productPanel, gbc);

            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }

        productsPanel.revalidate();
        productsPanel.repaint();

        System.out.println("Tải sản phẩm theo loại: " + tenLoaiSP + " (" + maLoaiSP + ") - Đã tải " + danhSachSanPham.size() + " sản phẩm");
    }

    /**
     * Tải hình ảnh sản phẩm từ đường dẫn
     */
    private ImageIcon loadProductImage(String imagePath) {
        try {
            String path = "Images/Products/" + imagePath;
            // Tải file hình ảnh
            ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource(path));

            // Thay đổi kích thước hình ảnh để vừa với panel sản phẩm (100x100 pixels)
            Image originalImage = originalIcon.getImage();
            Image resizedImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            System.err.println("Lỗi khi tải hình ảnh từ " + imagePath + ": " + e.getMessage());
            // Trả về hình ảnh mặc định khi có lỗi
            return createDummyImage("Lỗi Hình", Color.RED);
        }
    }

    /**
     * Tạo hình ảnh mẫu cho sản phẩm (dự phòng)
     */
    private ImageIcon createDummyImage(String text, Color color) {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        // Vẽ background
        g2d.setColor(color);
        g2d.fillRect(0, 0, 100, 100);

        // Vẽ viền
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, 99, 99);

        // Vẽ tên sản phẩm
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g2d.drawString(text, (100 - textWidth) / 2, 55);

        g2d.dispose();
        return new ImageIcon(img);
    }

    /**
     * Tạo panel hiển thị sản phẩm
     */
    private JPanel createProductPanel(SanPhamDTO sanPham) {
        // Tạo panel cho sản phẩm với layout dạng BoxLayout
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ nền panel
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

                // Vẽ đổ bóng với opacity nhẹ hơn để tạo chiều sâu tinh tế
                int shadowSize = 5;
                for (int i = 0; i < shadowSize; i++) {
                    // Sử dụng opacity thấp hơn (0.15) để tạo bóng mờ nhẹ
                    g2d.setColor(new Color(0, 0, 0, (int)(15 * (1 - (float)i / shadowSize))));
                    g2d.drawRoundRect(0, i, getWidth() - 1, getHeight() - 1, 10, 10);
                }
                g2d.dispose();
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(150, 200));
        panel.setMaximumSize(new Dimension(150, 200));
        panel.setMinimumSize(new Dimension(150, 200));

        // Thêm hình ảnh
        ImageIcon productImage = loadProductImage(sanPham.getHinhAnh());
        JLabel imageLabel = new JLabel(productImage);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Thêm tên sản phẩm với giới hạn chiều rộng
        String tenSP = sanPham.getTenSP();
        // Giới hạn tên sản phẩm nếu quá dài
        if (tenSP.length() > 15) {
            tenSP = tenSP.substring(0, 15) + "...";
        }
        JLabel nameLabel = new JLabel(tenSP);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setToolTipText(sanPham.getTenSP()); // Hiển thị tên đầy đủ khi hover chuột

        // Thêm giá sản phẩm
        JLabel priceLabel = new JLabel(currencyFormat.format(sanPham.getGiaBan()));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        priceLabel.setForeground(priceColor);

        // Thêm nút thêm vào giỏ hàng
        JButton addButton = new JButton("Thêm vào giỏ");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> addToCart(sanPham));

        // Thêm các thành phần vào panel
        panel.add(Box.createVerticalStrut(5));
        panel.add(imageLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(3));
        panel.add(priceLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addButton);
        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    private void addToCart(SanPhamDTO sanPham) {
        int currentTab = tabbedPane.getSelectedIndex();
        JPanel invoicePanel = (JPanel) tabbedPane.getComponentAt(currentTab);
        JPanel cartItemsPanel = (JPanel) invoicePanel.getClientProperty("cartItemsPanel");
        JLabel totalLabel = (JLabel) invoicePanel.getClientProperty("totalLabel");


        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        List<CartItem> cartItems = cartItemsByTab.get(currentTab);
        for (CartItem item : cartItems) {
            if (item.getSanPham().getMaSP().equals(sanPham.getMaSP())) {
                // Nếu đã có, tăng số lượng lên 1
                int newQuantity = item.getQuantity() + 1;

                // Kiểm tra tồn kho trước khi thêm
                if (!SanPhamBUS.kiemTraTonKho(sanPham.getMaSP(), newQuantity)) {
                    JOptionPane.showMessageDialog(this, "Số lượng sản phẩm trong kho không đủ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                item.setQuantity(newQuantity);


                updateTotalAmount(currentTab, totalLabel);
                return;
            }
        }


        // Nếu chưa có, tạo mới panel cho sản phẩm trong giỏ hàng
        JPanel cartItemPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        cartItemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(8, 5, 8, 5)
        ));
        cartItemPanel.setBackground(Color.WHITE);
        cartItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        // Thêm tên sản phẩm với giới hạn chiều rộng
        String tenSP = sanPham.getTenSP();
        // Giới hạn tên sản phẩm nếu quá dài
        if (tenSP.length() > 25) {
            tenSP = tenSP.substring(0, 22) + "...";
        }
        JLabel nameLabel = new JLabel(tenSP, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(new Color(50, 50, 50));
        nameLabel.setToolTipText(sanPham.getTenSP()); // Hiển thị tên đầy đủ khi hover chuột
        cartItemPanel.add(nameLabel);

        // Thêm phần chọn số lượng
        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        qtyPanel.setBackground(Color.WHITE);

        JButton minusButton = new JButton("-");
        minusButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        minusButton.setPreferredSize(new Dimension(30, 30));
        minusButton.setFocusPainted(false);
        minusButton.setBackground(new Color(240, 240, 240));

        JTextField qtyField = new JTextField("1", 2);
        qtyField.setHorizontalAlignment(JTextField.CENTER);
        qtyField.setFont(new Font("Segoe UI", Font.BOLD, 14));
        qtyField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        qtyField.setPreferredSize(new Dimension(40, 30));

        JButton plusButton = new JButton("+");
        plusButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        plusButton.setPreferredSize(new Dimension(30, 30));
        plusButton.setFocusPainted(false);
        plusButton.setBackground(new Color(240, 240, 240));

        qtyPanel.add(minusButton);
        qtyPanel.add(qtyField);
        qtyPanel.add(plusButton);

        cartItemPanel.add(qtyPanel);

        // Thêm thành tiền
        JLabel totalItemLabel = new JLabel(currencyFormat.format(sanPham.getGiaBan()), SwingConstants.CENTER);
        totalItemLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalItemLabel.setForeground(priceColor);
        cartItemPanel.add(totalItemLabel);

        // Thêm nút xóa
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setBackground(Color.WHITE);

        JButton deleteButton = new JButton("Xóa");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setPreferredSize(new Dimension(60, 25));

        actionPanel.add(deleteButton);
        cartItemPanel.add(actionPanel);

        // Tạo đối tượng CartItem
        CartItem cartItem = new CartItem(sanPham, 1, cartItemPanel, qtyField, totalItemLabel);
        cartItems.add(cartItem);


        // Thêm sự kiện cho các nút
        minusButton.addActionListener(e -> {
            int qty = cartItem.getQuantity();
            if (qty > 1) {
                cartItem.setQuantity(qty - 1);
                // Cập nhật số lượng trong hóa đơn
                updateTotalAmount(currentTab, totalLabel);
            }
        });

        plusButton.addActionListener(e -> {
            int qty = cartItem.getQuantity();
            // Kiểm tra tồn kho trước khi tăng số lượng
            if (SanPhamBUS.kiemTraTonKho(sanPham.getMaSP(), qty + 1)) {
                cartItem.setQuantity(qty + 1);
                updateTotalAmount(currentTab, totalLabel);
            } else {
                JOptionPane.showMessageDialog(this, "Số lượng sản phẩm trong kho không đủ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            cartItemsPanel.remove(cartItemPanel);
            cartItems.remove(cartItem);
            cartItemsPanel.revalidate();
            cartItemsPanel.repaint();
            updateTotalAmount(currentTab, totalLabel);
        });

        // Thêm sản phẩm vào panel giỏ hàng
        cartItemsPanel.add(cartItemPanel);
        cartItemsPanel.revalidate();
        cartItemsPanel.repaint();

        // Cập nhật tổng tiền
        updateTotalAmount(currentTab, totalLabel);
    }

    // Cập nhật tổng tiền của hóa đơn
    private void updateTotalAmount(int tabIndex, JLabel totalLabel) {
        double total = 0;
        List<CartItem> cartItems = cartItemsByTab.get(tabIndex);

        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }

        totalAmountByTab[tabIndex] = total;
        totalLabel.setText("TỔNG TIỀN: " + currencyFormat.format(total));

        // Cập nhật tổng tiền cho hóa đơn
        if (tabIndex < hoaDonByTab.size()) {
            hoaDonByTab.get(tabIndex).setTongTien(total);
        }
    }


    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new BanHangPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
