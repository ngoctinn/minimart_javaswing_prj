package org.example.test.ducTayTest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainPOSInterface extends JFrame {
    private JPanel productPanel;
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JLabel totalLabel;
    private JButton checkoutButton;
    private double total = 0.0;
    private final DecimalFormat currencyFormat = new DecimalFormat("#,###,###");
    
    public MainPOSInterface() {
        setTitle("POS Pro System - Bán Hàng");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 768));
        
        // Main layout
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Top panel with info
        JPanel topPanel = createTopPanel();
        
        // Left panel for products
        JPanel leftPanel = createProductPanel();
        
        // Right panel for cart and checkout
        JPanel rightPanel = createCartPanel();
        
        // Bottom panel for status and shortcuts
        JPanel bottomPanel = createBottomPanel();
        
        // Add panels to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 152, 219));
        panel.setPreferredSize(new Dimension(0, 70));
        
        // Logo and store info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false);
        
        JLabel logoLabel = new JLabel("POS Pro");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setForeground(Color.WHITE);
        
        infoPanel.add(logoLabel);
        
        // Current date/time and user
        JPanel dateUserPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        dateUserPanel.setOpaque(false);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        JLabel dateTimeLabel = new JLabel(sdf.format(new Date()));
        dateTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateTimeLabel.setForeground(Color.WHITE);
        
        JLabel userLabel = new JLabel("Người dùng: Admin");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        
        dateUserPanel.add(dateTimeLabel);
        dateUserPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        dateUserPanel.add(userLabel);
        
        panel.add(infoPanel, BorderLayout.WEST);
        panel.add(dateUserPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Danh Mục Sản Phẩm"));
        
        // Category tabs
        JTabbedPane categoryTabs = new JTabbedPane();
        
        // Products panel with grid layout
        productPanel = new JPanel(new GridLayout(0, 4, 5, 5));
        JScrollPane scrollPane = new JScrollPane(productPanel);
        
        // Add some sample products
        addSampleProducts();
        
        categoryTabs.addTab("Tất Cả", scrollPane);
        categoryTabs.addTab("Đồ Uống", new JPanel());
        categoryTabs.addTab("Thức Ăn", new JPanel());
        categoryTabs.addTab("Khác", new JPanel());
        
        panel.add(categoryTabs, BorderLayout.CENTER);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Tìm kiếm: "));
        JTextField searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Tìm");
        searchPanel.add(searchButton);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        return panel;
    }
    
    private void addSampleProducts() {
        String[] productNames = {
            "Cà phê", "Trà sữa", "Nước cam", "Sinh tố", 
            "Bánh mì", "Sandwich", "Mì xào", "Cơm rang", 
            "Gà rán", "Pizza", "Hamburger", "Hot dog",
            "Salad", "Sushi", "Bánh ngọt", "Kem"
        };
        
        double[] prices = {
            25000, 30000, 35000, 40000,
            20000, 35000, 45000, 40000,
            60000, 85000, 55000, 45000,
            50000, 70000, 25000, 15000
        };
        
        for (int i = 0; i < productNames.length; i++) {
            ProductButton button = new ProductButton(productNames[i], prices[i]);
            productPanel.add(button);
        }
    }
    
    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setPreferredSize(new Dimension(400, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY),
                new EmptyBorder(5, 10, 5, 10)
        ));
        
        // Cart table
        String[] columns = {"Sản Phẩm", "SL", "Đơn Giá", "Thành Tiền"};
        cartTableModel = new DefaultTableModel(columns, 0);
        cartTable = new JTable(cartTableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setPreferredSize(new Dimension(380, 300));
        
        // Action buttons
        JPanel actionPanel = new JPanel(new GridLayout(1, 3, 5, 0));
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa Hết");
        JButton quantityButton = new JButton("Số Lượng");
        
        actionPanel.add(deleteButton);
        actionPanel.add(clearButton);
        actionPanel.add(quantityButton);
        
        // Total and checkout panel
        JPanel totalPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        
        JPanel subtotalPanel = new JPanel(new BorderLayout());
        subtotalPanel.add(new JLabel("Tổng cộng:"), BorderLayout.WEST);
        totalLabel = new JLabel("0 VNĐ");
        totalLabel.setHorizontalAlignment(JLabel.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        subtotalPanel.add(totalLabel, BorderLayout.EAST);
        
        JPanel discountPanel = new JPanel(new BorderLayout());
        discountPanel.add(new JLabel("Giảm giá:"), BorderLayout.WEST);
        JLabel discountLabel = new JLabel("0 VNĐ");
        discountLabel.setHorizontalAlignment(JLabel.RIGHT);
        discountPanel.add(discountLabel, BorderLayout.EAST);
        
        JPanel finalTotalPanel = new JPanel(new BorderLayout());
        finalTotalPanel.add(new JLabel("Thanh toán:"), BorderLayout.WEST);
        JLabel finalTotalLabel = new JLabel("0 VNĐ");
        finalTotalLabel.setHorizontalAlignment(JLabel.RIGHT);
        finalTotalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        finalTotalLabel.setForeground(new Color(231, 76, 60));
        finalTotalPanel.add(finalTotalLabel, BorderLayout.EAST);
        
        totalPanel.add(subtotalPanel);
        totalPanel.add(discountPanel);
        totalPanel.add(finalTotalPanel);
        
        // Checkout button
        checkoutButton = new JButton("THANH TOÁN");
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        checkoutButton.setBackground(new Color(46, 204, 113));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setPreferredSize(new Dimension(0, 50));
        
        // Action listeners
        deleteButton.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow >= 0) {
                double amountToRemove = Double.parseDouble(cartTableModel.getValueAt(selectedRow, 3).toString());
                cartTableModel.removeRow(selectedRow);
                updateTotal(-amountToRemove);
            }
        });
        
        clearButton.addActionListener(e -> {
            cartTableModel.setRowCount(0);
            updateTotal(-total);
        });
        
        checkoutButton.addActionListener(e -> {
            if (cartTableModel.getRowCount() > 0) {
                PaymentScreen paymentScreen = new PaymentScreen(this, total);
                paymentScreen.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Giỏ hàng trống, vui lòng thêm sản phẩm.");
            }
        });
        
        // Add components to panel
        panel.add(new JLabel("GIỎ HÀNG HIỆN TẠI", JLabel.CENTER), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout(0, 10));
        southPanel.add(actionPanel, BorderLayout.NORTH);
        southPanel.add(totalPanel, BorderLayout.CENTER);
        southPanel.add(checkoutButton, BorderLayout.SOUTH);
        
        panel.add(southPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(0, 30));
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
        
        JLabel statusLabel = new JLabel(" Sẵn sàng");
        panel.add(statusLabel, BorderLayout.WEST);
        
        JPanel shortcutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        shortcutPanel.add(new JLabel("F1: Trợ giúp | "));
        shortcutPanel.add(new JLabel("F2: Tìm kiếm | "));
        shortcutPanel.add(new JLabel("F3: Sản phẩm | "));
        shortcutPanel.add(new JLabel("F4: Thanh toán"));
        
        panel.add(shortcutPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    public void updateTotal(double amount) {
        total += amount;
        totalLabel.setText(currencyFormat.format(total) + " VNĐ");
    }
    
    // Product Button inner class
    private class ProductButton extends JButton {
        private final String productName;
        private final double price;
        
        public ProductButton(String name, double price) {
            this.productName = name;
            this.price = price;
            
            setLayout(new BorderLayout());
            
            JLabel nameLabel = new JLabel(name, JLabel.CENTER);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            
            JLabel priceLabel = new JLabel(currencyFormat.format(price) + " VNĐ", JLabel.CENTER);
            priceLabel.setForeground(Color.BLUE);
            
            JPanel textPanel = new JPanel(new GridLayout(2, 1));
            textPanel.setOpaque(false);
            textPanel.add(nameLabel);
            textPanel.add(priceLabel);
            
            add(textPanel, BorderLayout.CENTER);
            setPreferredSize(new Dimension(150, 100));
            setFocusPainted(false);
            
            addActionListener(e -> addToCart());
        }
        
        private void addToCart() {
            // Check if product is already in the cart
            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                if (cartTableModel.getValueAt(i, 0).equals(productName)) {
                    // Product exists, update quantity
                    int currentQty = Integer.parseInt(cartTableModel.getValueAt(i, 1).toString());
                    cartTableModel.setValueAt(currentQty + 1, i, 1);
                    double newTotal = price * (currentQty + 1);
                    cartTableModel.setValueAt(newTotal, i, 3);
                    updateTotal(price);
                    return;
                }
            }
            
            // Product is not in cart, add it
            Object[] row = {productName, 1, price, price};
            cartTableModel.addRow(row);
            updateTotal(price);
        }
    }
}