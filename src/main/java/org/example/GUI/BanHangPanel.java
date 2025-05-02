package org.example.GUI;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.SanPhamBUS;
import org.example.Components.*;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Dialogs.ThanhToanDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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

    // Bottom panel components
    private JTabbedPane tabbedPane;
    private JPanel productsPanel;
    private int tabCounter = 1;

    // Giỏ hàng
    private List<List<CartItem>> cartItemsByTab = new ArrayList<>();
    private double[] totalAmountByTab = new double[100]; // Giả sử không quá 100 tab

    // Định dạng tiền tệ
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private Color priceColor = new Color(0, 100, 180); // Màu xanh cho giá tiền

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
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new BorderLayout());
        bottomPanelRight.setLayout(new BorderLayout());
    }

    /**
     * Thiết lập panel trên cùng
     */
    private void setupTopPanel() {
        // Ô tìm kiếm
        searchField = new CustomTextField("Tìm kiếm khách hàng...");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        topPanel.add(searchField);

        // Nút tìm kiếm
        searchButton = new CustomButton("Tìm");
        searchButton.setPreferredSize(new Dimension(70, 30));
        topPanel.add(searchButton);

        // Panel khoảng cách để đẩy nút thêm sang phải
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        spacerPanel.setPreferredSize(new Dimension(680, 30));
        topPanel.add(spacerPanel);

        // Nút thêm hóa đơn mới
        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg", 16, 16);
        addTabButton = new CustomButton("Thêm hóa đơn", addIcon);
        addTabButton.setPreferredSize(new Dimension(150, 30));
        addTabButton.addActionListener(e -> addNewTab());
        topPanel.add(addTabButton);
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
    private static class CartItem {
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

        // ComboBox chọn loại sản phẩm
        String[] loaiSanPham = {"Tất cả", "Thức uống", "Đồ ăn", "Khác"};
        CustomCombobox<String> loaiSanPhamComboBox = new CustomCombobox<>(loaiSanPham);
        loaiSanPhamComboBox.setPreferredSize(new Dimension(150, 30));
        loaiSanPhamComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
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

            ThanhToanDialog dialog = new ThanhToanDialog(SwingUtilities.getWindowAncestor(this), totalAmount);
            dialog.setVisible(true);
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

        // Thêm tên sản phẩm
        JLabel nameLabel = new JLabel(sanPham.getTenSP());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

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
                item.setQuantity(item.getQuantity() + 1);
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

        // Thêm tên sản phẩm
        JLabel nameLabel = new JLabel(sanPham.getTenSP(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(new Color(50, 50, 50));
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
                updateTotalAmount(currentTab, totalLabel);
            }
        });

        plusButton.addActionListener(e -> {
            int qty = cartItem.getQuantity();
            cartItem.setQuantity(qty + 1);
            updateTotalAmount(currentTab, totalLabel);
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
    }

    // WrapLayout class để hiển thị sản phẩm tự động xuống dòng
    private class WrapLayout extends FlowLayout {
        private Dimension preferredLayoutSize;

        public WrapLayout() {
            super();
        }

        public WrapLayout(int align) {
            super(align);
        }

        public WrapLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            Dimension minimum = layoutSize(target, false);
            minimum.width -= (getHgap() + 1);
            return minimum;
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getWidth();
                
                if (targetWidth == 0)
                    targetWidth = Integer.MAX_VALUE;

                int hgap = getHgap();
                int vgap = getVgap();
                Insets insets = target.getInsets();
                int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
                int maxWidth = targetWidth - horizontalInsetsAndGap;

                Dimension dim = new Dimension(0, 0);
                int rowWidth = 0;
                int rowHeight = 0;

                int nmembers = target.getComponentCount();

                for (int i = 0; i < nmembers; i++) {
                    Component m = target.getComponent(i);

                    if (m.isVisible()) {
                        Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                        if (rowWidth + d.width > maxWidth) {
                            addRow(dim, rowWidth, rowHeight);
                            rowWidth = 0;
                            rowHeight = 0;
                        }

                        if (rowWidth != 0) {
                            rowWidth += hgap;
                        }

                        rowWidth += d.width;
                        rowHeight = Math.max(rowHeight, d.height);
                    }
                }

                addRow(dim, rowWidth, rowHeight);

                dim.width += horizontalInsetsAndGap;
                dim.height += insets.top + insets.bottom + vgap * 2;

                return dim;
            }
        }

        private void addRow(Dimension dim, int rowWidth, int rowHeight) {
            dim.width = Math.max(dim.width, rowWidth);

            if (dim.height > 0) {
                dim.height += getVgap();
            }

            dim.height += rowHeight;
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
