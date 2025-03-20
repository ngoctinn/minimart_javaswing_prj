package org.example.GUI;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.GUI.Dialogs.ThanhToanDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
    private PlaceholderTextField searchField;
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

    private void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanelLeft();
        setupBottomPanelRight();

        // Add panels to main panel
        this.add(topPanel,BorderLayout.NORTH);
        this.add(bottomPanel);
        bottomPanel.add(bottomPanelLeft, BorderLayout.CENTER);
        bottomPanel.add(bottomPanelRight, BorderLayout.EAST);
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

        bottomPanelRight.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.4), 0));


        // Set layouts
            topPanel.setLayout(new BorderLayout());
            bottomPanel.setLayout(new BorderLayout(5, 0));
            bottomPanelLeft.setLayout(new BorderLayout());
            bottomPanelRight.setLayout(new BorderLayout());
        }

        private void setupTopPanel() {
            //add padding to the top panel
            topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            // Main container for the left part (using FlowLayout)
            JPanel leftContainer = new JPanel();
            leftContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
            leftContainer.setBackground(Color.WHITE);

            // Right container for the add button
            JPanel rightContainer = new JPanel();
            rightContainer.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            rightContainer.setBackground(Color.WHITE);

            // Create title panel
            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
            titlePanel.setBackground(Color.WHITE);

            JPanel searchPanel = new JPanel();
            searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
            searchPanel.setBackground(Color.WHITE);

            // Search field
            searchField = new PlaceholderTextField("Nhập khách hàng cần tìm");
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

            // Add components to the left container
            leftContainer.add(titlePanel);
            leftContainer.add(searchPanel);

            // Add new tab button to the right container
            FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg", 16, 16);
            addTabButton = new CustomButton("Thêm hóa đơn", addIcon);
            addTabButton.setPreferredSize(new Dimension(150, 30));
            addTabButton.addActionListener(e -> addNewTab());
            rightContainer.add(addTabButton);

            // Add containers to the top panel using BorderLayout
            topPanel.add(leftContainer, BorderLayout.WEST);
            topPanel.add(rightContainer, BorderLayout.EAST);
        }

    private void setupBottomPanelLeft() {
        // Create closable tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.putClientProperty("JTabbedPane.tabClosable", true);
        tabbedPane.putClientProperty("JTabbedPane.tabCloseCallback",
                (java.util.function.BiConsumer<JTabbedPane, Integer>) (tabPane, tabIndex) -> {
                    if (tabIndex > 0) { // Don't close the first tab
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

        // Add default tab
        JPanel defaultPanel = createInvoicePanel();
        tabbedPane.addTab("Hóa đơn 1", defaultPanel);

        // Khởi tạo giỏ hàng cho tab đầu tiên
        cartItemsByTab.add(new ArrayList<>());

        bottomPanelLeft.add(tabbedPane, BorderLayout.CENTER);
    }

    private void addNewTab() {
        tabCounter++;
        String tabTitle = "Hóa đơn " + tabCounter;
        JPanel newPanel = createInvoicePanel();
        tabbedPane.addTab(tabTitle, newPanel);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);

        // Khởi tạo giỏ hàng cho tab mới
        cartItemsByTab.add(new ArrayList<>());
    }

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

    // Lớp đại diện cho một sản phẩm
    private static class Product {
        private String name;
        private double price;
        private ImageIcon image;

        public Product(String name, double price, ImageIcon image) {
            this.name = name;
            this.price = price;
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public ImageIcon getImage() {
            return image;
        }
    }

    // Lớp đại diện cho một mục trong giỏ hàng
    private static class CartItem {
        private Product product;
        private int quantity;
        private JPanel panel;
        private JTextField qtyField;
        private JLabel totalItemLabel;

        public CartItem(Product product, int quantity, JPanel panel, JTextField qtyField, JLabel totalItemLabel) {
            this.product = product;
            this.quantity = quantity;
            this.panel = panel;
            this.qtyField = qtyField;
            this.totalItemLabel = totalItemLabel;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
            this.qtyField.setText(String.valueOf(quantity));
            this.totalItemLabel.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(getTotalPrice()));
        }

        public JPanel getPanel() {
            return panel;
        }

        public double getTotalPrice() {
            return product.getPrice() * quantity;
        }
    }

    private void setupBottomPanelRight() {
        bottomPanelRight.setLayout(new BorderLayout());

        // Tạo panel chứa ô tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 25));
        searchPanel.setBackground(Color.WHITE);

        // Ô nhập tìm kiếm
        PlaceholderTextField searchField = new PlaceholderTextField("Tìm kiếm sản phẩm...");
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        searchPanel.add(searchField, BorderLayout.CENTER);
        bottomPanelRight.add(searchPanel, BorderLayout.NORTH);

        // Panel chứa danh sách sản phẩm
        productsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        productsPanel.setBackground(Color.WHITE);
        productsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thêm sản phẩm vào danh sách
        addSampleProducts();

        // Bọc productsPanel trong JScrollPane
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
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

    private void addSampleProducts() {
        List<Product> products = new ArrayList<>();

        // Tạo danh sách sản phẩm mẫu
        products.add(new Product("Cà phê sữa", 25000, createDummyImage("Cà phê", new Color(139, 69, 19))));
        products.add(new Product("Cà phê đen", 20000, createDummyImage("Cà phê đen", new Color(60, 30, 10))));
        products.add(new Product("Trà sữa trân châu", 35000, createDummyImage("Trà sữa", new Color(230, 210, 190))));
        products.add(new Product("Nước ép cam", 30000, createDummyImage("Cam", Color.ORANGE)));
        products.add(new Product("Bánh mì thịt", 25000, createDummyImage("Bánh mì", new Color(240, 220, 180))));
        products.add(new Product("Sandwich gà", 45000, createDummyImage("Sandwich", new Color(220, 200, 150))));
        products.add(new Product("Bánh ngọt chocolate", 50000, createDummyImage("Bánh ngọt", new Color(102, 51, 0))));
        products.add(new Product("Snack khoai tây", 20000, createDummyImage("Snack", Color.YELLOW)));
        products.add(new Product("Kem vanilla", 35000, createDummyImage("Kem", Color.WHITE)));
        products.add(new Product("Sữa chua dâu", 30000, createDummyImage("Sữa chua", new Color(255, 230, 230))));
        products.add(new Product("Trà đào", 35000, createDummyImage("Trà đào", new Color(255, 200, 150))));
        products.add(new Product("Nước suối", 10000, createDummyImage("Nước", new Color(200, 240, 255))));
        products.add(new Product("Bia Heineken", 25000, createDummyImage("Bia", new Color(220, 180, 100))));
        products.add(new Product("Sinh tố bơ", 40000, createDummyImage("Sinh tố", new Color(120, 180, 80))));
        products.add(new Product("Nước ngọt Coca", 15000, createDummyImage("Coca", new Color(160, 40, 40))));
        products.add(new Product("Matcha đá xay", 45000, createDummyImage("Matcha", new Color(100, 160, 80))));

        // Thêm từng sản phẩm vào giao diện
        for (Product product : products) {
            productsPanel.add(createProductPanel(product));
        }
    }

    // Tạo hình ảnh mẫu cho sản phẩm
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

    // Tạo panel hiển thị sản phẩm
    private JPanel createProductPanel(Product product) {
        // Tạo panel cho sản phẩm với layout dạng BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        panel.setBackground(Color.WHITE);

        // Thêm hình ảnh
        JLabel imageLabel = new JLabel(product.getImage());
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Thêm tên sản phẩm
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Thêm giá sản phẩm
        JLabel priceLabel = new JLabel(currencyFormat.format(product.getPrice()));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        priceLabel.setForeground(priceColor);

        // Thêm nút thêm vào giỏ hàng
        JButton addButton = new JButton("Thêm vào giỏ");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> addToCart(product));

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

    // Thêm sản phẩm vào giỏ hàng
    private void addToCart(Product product) {
        int currentTab = tabbedPane.getSelectedIndex();
        JPanel invoicePanel = (JPanel) tabbedPane.getComponentAt(currentTab);
        JPanel cartItemsPanel = (JPanel) invoicePanel.getClientProperty("cartItemsPanel");
        JLabel totalLabel = (JLabel) invoicePanel.getClientProperty("totalLabel");

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        List<CartItem> cartItems = cartItemsByTab.get(currentTab);
        for (CartItem item : cartItems) {
            if (item.getProduct().getName().equals(product.getName())) {
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
        JLabel nameLabel = new JLabel(product.getName(), SwingConstants.CENTER);
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
        JLabel totalItemLabel = new JLabel(currencyFormat.format(product.getPrice()), SwingConstants.CENTER);
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
        CartItem cartItem = new CartItem(product, 1, cartItemPanel, qtyField, totalItemLabel);
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