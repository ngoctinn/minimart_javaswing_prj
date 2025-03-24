package org.example.GUI.Panels.giaoDichPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.GUI.Dialogs.TaoLoHangDialog;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class nhapHangPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;

    // Top panel components
    private PlaceholderTextField searchField;
    private CustomButton searchButton;
    private CustomButton refreshButton;

    // Bottom panel components
    private JTabbedPane tabbedPane;
    private JPanel productsPanel;

    // Định dạng tiền tệ
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private Color priceColor = new Color(0, 100, 180); // Màu xanh cho giá tiền

    // Danh sách sản phẩm nhập
    private List<ImportItem> importItems = new ArrayList<>();
    private double totalAmount = 0;
    private JLabel totalLabel;

    // Thông tin lô hàng hiện tại
    private String currentBatchId = "";
    private String currentSupplier = "";
    private Date currentNgaySanXuat;
    private Date currentHanSuDung;

    public nhapHangPanel() {
        initGUI();
    }

    public void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanelLeft();
        setupBottomPanelRight();

        // Add panels to main panel
        this.add(topPanel);
        this.add(bottomPanel);
        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);
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

        // Set panel sizes
        topPanel.setPreferredSize(new Dimension(1270, 50));
        bottomPanel.setPreferredSize(new Dimension(1270, 900));

        // Set bottom panel proportions - 60% left, 40% right
        bottomPanelLeft.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.6), 0));
        bottomPanelRight.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.4), 0));

        // Set layouts
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new BorderLayout());
        bottomPanelRight.setLayout(new BorderLayout());
    }

    private void setupTopPanel() {
        // Search field
        searchField = new PlaceholderTextField("Tìm kiếm sản phẩm...");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        topPanel.add(searchField);

        // Search button
        searchButton = new CustomButton("Tìm");
        searchButton.setPreferredSize(new Dimension(70, 30));
        topPanel.add(searchButton);

        // Add a spacer panel to push the refresh button to the right
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        spacerPanel.setPreferredSize(new Dimension(680, 30));
        topPanel.add(spacerPanel);

        // Refresh button
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 16, 16);
        refreshButton = new CustomButton("Làm mới", refreshIcon);
        refreshButton.setPreferredSize(new Dimension(150, 30));
        refreshButton.addActionListener(e -> refreshPanel());
        topPanel.add(refreshButton);
    }

    private void refreshPanel() {
        // Clear search field and refresh data
        searchField.setText("");
        JOptionPane.showMessageDialog(
                this,
                "Đã làm mới dữ liệu!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void setupBottomPanelLeft() {
        // Create tabbed pane with 2 fixed tabs
        tabbedPane = new JTabbedPane();

        // Tab 1: Tạo lô hàng mới
        JPanel createBatchPanel = createBatchPanel();
        tabbedPane.addTab("Tạo lô hàng", createBatchPanel);

        // Tab 2: Danh sách hàng nhập
        JPanel importListPanel = createImportListPanel();
        tabbedPane.addTab("Danh sách hàng nhập", importListPanel);

        bottomPanelLeft.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createBatchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Header panel with batch info
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        headerPanel.setBackground(new Color(250, 250, 255));

        // Info panel for batch details
        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 10, 5));
        infoPanel.setBackground(new Color(250, 250, 255));

        // Batch ID
        JLabel batchIdLabel = new JLabel("Mã lô hàng:");
        batchIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel batchIdValueLabel = new JLabel("Chưa có");
        batchIdValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Supplier
        JLabel supplierLabel = new JLabel("Nhà cung cấp:");
        supplierLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel supplierValueLabel = new JLabel("Chưa chọn");
        supplierValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Manufacturing date
        JLabel mfgDateLabel = new JLabel("Ngày sản xuất:");
        mfgDateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel mfgDateValueLabel = new JLabel("--/--/----");
        mfgDateValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Expiry date
        JLabel expDateLabel = new JLabel("Hạn sử dụng:");
        expDateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel expDateValueLabel = new JLabel("--/--/----");
        expDateValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        infoPanel.add(batchIdLabel);
        infoPanel.add(batchIdValueLabel);
        infoPanel.add(supplierLabel);
        infoPanel.add(supplierValueLabel);
        infoPanel.add(mfgDateLabel);
        infoPanel.add(mfgDateValueLabel);
        infoPanel.add(expDateLabel);
        infoPanel.add(expDateValueLabel);

        // Create batch button
        CustomButton createBatchButton = new CustomButton("Tạo lô hàng mới");
        createBatchButton.setPreferredSize(new Dimension(150, 40));
        createBatchButton.addActionListener(e -> {
            // Generate a new batch ID
            String newBatchId = "LH" + System.currentTimeMillis() % 10000;

            // Show dialog to create a new batch
            TaoLoHangDialog dialog = new TaoLoHangDialog(SwingUtilities.getWindowAncestor(this), newBatchId);
            dialog.setVisible(true);

            // If confirmed, update the batch info
            if (dialog.isConfirmed()) {
                currentBatchId = newBatchId;
                currentSupplier = dialog.getSupplier();
                currentNgaySanXuat = dialog.getNgaySanXuat();
                currentHanSuDung = dialog.getHanSuDung();

                // Update the labels
                batchIdValueLabel.setText(currentBatchId);
                supplierValueLabel.setText(currentSupplier);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                mfgDateValueLabel.setText(dateFormat.format(currentNgaySanXuat));
                expDateValueLabel.setText(dateFormat.format(currentHanSuDung));

                JOptionPane.showMessageDialog(
                        this,
                        "Đã tạo lô hàng mới: " + currentBatchId,
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(250, 250, 255));
        buttonPanel.add(createBatchButton);

        headerPanel.add(infoPanel, BorderLayout.CENTER);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // Product addition panel
        JPanel productAddPanel = new JPanel(new BorderLayout());
        productAddPanel.setBackground(Color.WHITE);

        // Tiêu đề
        JLabel titleLabel = new JLabel("CHI TIẾT NHẬP HÀNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        productAddPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel chứa tiêu đề các cột
        JPanel headerCartPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        headerCartPanel.setBackground(new Color(245, 245, 245));
        headerCartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        Font headerFont = new Font("Segoe UI", Font.BOLD, 14);
        Color headerColor = new Color(60, 60, 60);

        JLabel codeHeader = new JLabel("Mã SP", SwingConstants.CENTER);
        codeHeader.setFont(headerFont);
        codeHeader.setForeground(headerColor);

        JLabel nameHeader = new JLabel("Tên sản phẩm", SwingConstants.CENTER);
        nameHeader.setFont(headerFont);
        nameHeader.setForeground(headerColor);

        JLabel qtyHeader = new JLabel("Số lượng", SwingConstants.CENTER);
        qtyHeader.setFont(headerFont);
        qtyHeader.setForeground(headerColor);

        JLabel priceHeader = new JLabel("Đơn giá", SwingConstants.CENTER);
        priceHeader.setFont(headerFont);
        priceHeader.setForeground(headerColor);

        JLabel totalHeader = new JLabel("Thành tiền", SwingConstants.CENTER);
        totalHeader.setFont(headerFont);
        totalHeader.setForeground(headerColor);

        headerCartPanel.add(codeHeader);
        headerCartPanel.add(nameHeader);
        headerCartPanel.add(qtyHeader);
        headerCartPanel.add(priceHeader);
        headerCartPanel.add(totalHeader);

        // Panel chính chứa danh sách các sản phẩm nhập hàng
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(Color.WHITE);
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Tạo JScrollPane cho danh sách sản phẩm
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));

        // Bottom panel with total and buttons
        JPanel bottomActionPanel = new JPanel(new BorderLayout());
        bottomActionPanel.setBackground(Color.WHITE);
        bottomActionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Tổng tiền
        totalLabel = new JLabel("TỔNG TIỀN: " + currencyFormat.format(0), SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalLabel.setForeground(priceColor);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonsPanel.setBackground(Color.WHITE);

        CustomButton saveButton = new CustomButton("Lưu phiếu nhập");
        saveButton.setPreferredSize(new Dimension(150, 40));
        saveButton.addActionListener(e -> {
            if (currentBatchId.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng tạo lô hàng trước khi lưu!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (importItems.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Chưa có sản phẩm nào được nhập!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Đã lưu phiếu nhập hàng thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Chuyển sang tab danh sách hàng nhập
            tabbedPane.setSelectedIndex(1);

            // Reset form
            itemsPanel.removeAll();
            getRevalidate(itemsPanel);
            itemsPanel.repaint();
            importItems.clear();

            currentBatchId = "";
            batchIdValueLabel.setText("Chưa có");
            supplierValueLabel.setText("Chưa chọn");
            mfgDateValueLabel.setText("--/--/----");
            expDateValueLabel.setText("--/--/----");
            totalLabel.setText("TỔNG TIỀN: " + currencyFormat.format(0));
            totalAmount = 0;
        });

        CustomButton cancelButton = new CustomButton("Hủy");
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn hủy phiếu nhập hàng này?",
                    "Xác nhận hủy",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Reset form
                itemsPanel.removeAll();
                itemsPanel.revalidate();
                itemsPanel.repaint();
                importItems.clear();

                currentBatchId = "";
                batchIdValueLabel.setText("Chưa có");
                supplierValueLabel.setText("Chưa chọn");
                mfgDateValueLabel.setText("--/--/----");
                expDateValueLabel.setText("--/--/----");
                totalLabel.setText("TỔNG TIỀN: " + currencyFormat.format(0));
                totalAmount = 0;
            }
        });

        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        bottomActionPanel.add(buttonsPanel, BorderLayout.WEST);
        bottomActionPanel.add(totalLabel, BorderLayout.EAST);

        // Thêm các thành phần vào productAddPanel
        productAddPanel.add(headerCartPanel, BorderLayout.NORTH);
        productAddPanel.add(scrollPane, BorderLayout.CENTER);
        productAddPanel.add(bottomActionPanel, BorderLayout.SOUTH);

        // Add all panels to main panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(productAddPanel, BorderLayout.CENTER);

        // Lưu trữ itemsPanel để truy cập sau này
        panel.putClientProperty("itemsPanel", itemsPanel);

        return panel;
    }

    private static void getRevalidate(JPanel itemsPanel) {
        itemsPanel.revalidate();
    }

    private JPanel createImportListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Tiêu đề
        JLabel titleLabel = new JLabel("DANH SÁCH PHIẾU NHẬP HÀNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Bảng phiếu nhập sử dụng CustomTable
        String[] columnNames = {"Mã phiếu", "Ngày nhập", "Mã lô", "Nhà cung cấp", "Tổng sản phẩm", "Tổng tiền", "Trạng thái"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Thêm dữ liệu mẫu
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Object[][] sampleData = {
                {"PN001", dateFormat.format(new Date()), "LH4321", "NCC001 - Công ty TNHH ABC", 15, currencyFormat.format(3750000), "Hoàn thành"},
                {"PN002", "01/06/2023", "LH2156", "NCC002 - Công ty CP XYZ", 8, currencyFormat.format(1250000), "Hoàn thành"},
                {"PN003", "15/07/2023", "LH9876", "NCC003 - Công ty TNHH DEF", 12, currencyFormat.format(2800000), "Hoàn thành"}
        };

        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }

        CustomTable importTable = new CustomTable(tableModel);
        importTable.setRowHeight(30);
        importTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        importTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        importTable.setSelectionBackground(new Color(230, 240, 250));

        // Center align text in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < importTable.getColumnCount(); i++) {
            importTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScrollPane = new JScrollPane(importTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Bottom action panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        CustomButton viewDetailButton = new CustomButton("Xem chi tiết");
        viewDetailButton.setPreferredSize(new Dimension(120, 35));
        viewDetailButton.addActionListener(e -> {
            int selectedRow = importTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng chọn một phiếu nhập để xem chi tiết!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            String importId = (String) tableModel.getValueAt(selectedRow, 0);
            JOptionPane.showMessageDialog(
                    this,
                    "Xem chi tiết phiếu nhập: " + importId,
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        CustomButton exportButton = new CustomButton("Xuất Excel");
        exportButton.setPreferredSize(new Dimension(120, 35));
        exportButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Xuất danh sách phiếu nhập thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        actionPanel.add(viewDetailButton);
        actionPanel.add(exportButton);

        panel.add(actionPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void setupBottomPanelRight() {
        bottomPanelRight.setLayout(new BorderLayout());

        // Tạo panel chứa ô tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchPanel.setBackground(Color.WHITE);

        // Ô nhập tìm kiếm
        PlaceholderTextField searchProductField = new PlaceholderTextField("Tìm kiếm sản phẩm...");
        searchProductField.setPreferredSize(new Dimension(200, 30));
        searchProductField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        searchPanel.add(searchProductField, BorderLayout.CENTER);
        bottomPanelRight.add(searchPanel, BorderLayout.NORTH);

        // Tiêu đề danh sách sản phẩm
        JLabel titleLabel = new JLabel("DANH SÁCH SẢN PHẨM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(5, 0, 5, 0)
        ));

        // Panel chứa danh sách sản phẩm
        productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        productsPanel.setBackground(Color.WHITE);
        productsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thêm sản phẩm vào danh sách
        addSampleProducts();

        // Bọc productsPanel trong JScrollPane
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Container for title and product list
        JPanel productListContainer = new JPanel(new BorderLayout());
        productListContainer.setBackground(Color.WHITE);
        productListContainer.add(titleLabel, BorderLayout.NORTH);
        productListContainer.add(scrollPane, BorderLayout.CENTER);

        bottomPanelRight.add(productListContainer, BorderLayout.CENTER);
    }

    private void addSampleProducts() {
        // Thêm sản phẩm mẫu vào panel
        String[] productIds = {"SP001", "SP002", "SP003", "SP004", "SP005",
                "SP006", "SP007", "SP008", "SP009", "SP010"};

        String[] productNames = {"Cà phê sữa", "Cà phê đen", "Trà sữa trân châu",
                "Nước ép cam", "Bánh mì thịt", "Sandwich gà",
                "Bánh ngọt chocolate", "Snack khoai tây",
                "Kem vanilla", "Sữa chua dâu"};

        for (int i = 0; i < productNames.length; i++) {
            // Sử dụng ảnh thực tế thay vì tạo ảnh mẫu
            ImageIcon productImage = loadProductImage("src/main/resources/Images/Products/sample.png");
            Product product = new Product(productIds[i], productNames[i], productImage);
            productsPanel.add(createProductPanel(product));
        }
    }

    // Phương thức để load ảnh sản phẩm từ file
    private ImageIcon loadProductImage(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            // Thay đổi kích thước ảnh nếu cần
            if (img != null) {
                Image scaledImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            }
        } catch (IOException e) {
            System.err.println("Không thể tải ảnh từ đường dẫn: " + path);
            e.printStackTrace();
            // Tạo ảnh mặc định nếu không tìm thấy ảnh
            return createDefaultProductImage();
        }
        return createDefaultProductImage();
    }

    // Tạo ảnh mặc định nếu không tìm thấy ảnh
    private ImageIcon createDefaultProductImage() {
        BufferedImage img = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        // Vẽ background
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(0, 0, 80, 80);

        // Vẽ viền
        g2d.setColor(Color.GRAY);
        g2d.drawRect(0, 0, 79, 79);

        // Vẽ thông báo không có ảnh
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("No Image", 15, 45);

        g2d.dispose();
        return new ImageIcon(img);
    }

    // Tạo panel hiển thị sản phẩm
    private JPanel createProductPanel(Product product) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Panel ảnh sản phẩm
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setPreferredSize(new Dimension(80, 80));

        JLabel imageLabel = new JLabel(product.getImage());
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Panel thông tin sản phẩm
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel idLabel = new JLabel(product.getId());
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        idLabel.setForeground(Color.GRAY);

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        infoPanel.add(idLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(nameLabel);

        // Panel nút chọn sản phẩm
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(Color.WHITE);

        CustomButton selectButton = new CustomButton("Chọn");
        selectButton.setPreferredSize(new Dimension(80, 30));
        selectButton.addActionListener(e -> addProductToImport(product));

        actionPanel.add(selectButton);

        // Thêm các thành phần vào panel
        panel.add(imagePanel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.EAST);

        return panel;
    }

    // Thêm sản phẩm vào giỏ hàng
    private void addProductToImport(Product product) {
        // Kiểm tra nếu chưa tạo lô hàng
        if (currentBatchId.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng tạo lô hàng trước khi thêm sản phẩm!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            tabbedPane.setSelectedIndex(0); // Chuyển đến tab tạo lô hàng
            return;
        }

        // Lấy panel danh sách sản phẩm
        JPanel invoicePanel = (JPanel) tabbedPane.getComponentAt(0);
        JPanel itemsPanel = (JPanel) invoicePanel.getClientProperty("itemsPanel");

        // Kiểm tra xem sản phẩm đã có trong danh sách chưa
        for (ImportItem item : importItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                // Nếu đã có, tăng số lượng lên 1
                item.setQuantity(item.getQuantity() + 1);
                updateTotalAmount();
                return;
            }
        }

        // Dialog nhập giá
        String priceStr = JOptionPane.showInputDialog(
                this,
                "Nhập giá nhập cho sản phẩm " + product.getName() + " (VND):",
                "Nhập giá",
                JOptionPane.QUESTION_MESSAGE
        );

        if (priceStr == null || priceStr.trim().isEmpty()) {
            return; // Người dùng hủy
        }

        try {
            double price = Double.parseDouble(priceStr);

            if (price <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Giá phải lớn hơn 0!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Tạo panel hiển thị sản phẩm nhập
            JPanel itemPanel = new JPanel(new GridLayout(1, 5, 10, 0));
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                    BorderFactory.createEmptyBorder(8, 5, 8, 5)
            ));
            itemPanel.setBackground(Color.WHITE);
            itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

            // Mã sản phẩm
            JLabel codeLabel = new JLabel(product.getId(), SwingConstants.CENTER);
            codeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            // Tên sản phẩm
            JLabel nameLabel = new JLabel(product.getName(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

            // Panel số lượng
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

            // Panel giá nhập
            JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            pricePanel.setBackground(Color.WHITE);

            JTextField priceField = new JTextField(priceStr, 8);
            priceField.setHorizontalAlignment(JTextField.CENTER);
            priceField.setFont(new Font("Segoe UI", Font.BOLD, 14));
            priceField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
            pricePanel.add(priceField);

            // Thành tiền
            JLabel totalItemLabel = new JLabel(currencyFormat.format(price), SwingConstants.CENTER);
            totalItemLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            totalItemLabel.setForeground(priceColor);

            // Thêm các thành phần vào itemPanel
            itemPanel.add(codeLabel);
            itemPanel.add(nameLabel);
            itemPanel.add(qtyPanel);
            itemPanel.add(pricePanel);
            itemPanel.add(totalItemLabel);

            // Tạo đối tượng ImportItem
            ImportItem importItem = new ImportItem(product, 1, price, itemPanel, qtyField, priceField, totalItemLabel);
            importItems.add(importItem);

            // Thêm sự kiện cho các nút và field
            minusButton.addActionListener(e -> {
                int qty = importItem.getQuantity();
                if (qty > 1) {
                    importItem.setQuantity(qty - 1);
                    updateTotalAmount();
                }
            });

            plusButton.addActionListener(e -> {
                int qty = importItem.getQuantity();
                importItem.setQuantity(qty + 1);
                updateTotalAmount();
            });

            qtyField.addActionListener(e -> {
                try {
                    int qty = Integer.parseInt(qtyField.getText());
                    if (qty > 0) {
                        importItem.setQuantity(qty);
                    } else {
                        qtyField.setText(String.valueOf(importItem.getQuantity()));
                    }
                    updateTotalAmount();
                } catch (NumberFormatException ex) {
                    qtyField.setText(String.valueOf(importItem.getQuantity()));
                }
            });

            priceField.addActionListener(e -> {
                try {
                    double newPrice = Double.parseDouble(priceField.getText());
                    if (newPrice > 0) {
                        importItem.setPrice(newPrice);
                    } else {
                        priceField.setText(String.valueOf(importItem.getPrice()));
                    }
                    updateTotalAmount();
                } catch (NumberFormatException ex) {
                    priceField.setText(String.valueOf(importItem.getPrice()));
                }
            });

            // Thêm context menu để xóa sản phẩm
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem deleteItem = new JMenuItem("Xóa sản phẩm");
            deleteItem.addActionListener(e -> {
                itemsPanel.remove(itemPanel);
                importItems.remove(importItem);
                itemsPanel.revalidate();
                itemsPanel.repaint();
                updateTotalAmount();
            });
            popupMenu.add(deleteItem);

            // Thêm popup menu vào item panel
            itemPanel.setComponentPopupMenu(popupMenu);

            // Thêm sản phẩm vào danh sách
            itemsPanel.add(itemPanel);
            itemsPanel.revalidate();
            itemsPanel.repaint();

            // Cập nhật tổng tiền
            updateTotalAmount();

            // Thông báo
            JOptionPane.showMessageDialog(
                    this,
                    "Đã thêm sản phẩm " + product.getName() + " vào phiếu nhập!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập số hợp lệ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateTotalAmount() {
        totalAmount = 0;
        for (ImportItem item : importItems) {
            totalAmount += item.getTotalPrice();
        }
        totalLabel.setText("TỔNG TIỀN: " + currencyFormat.format(totalAmount));
    }

    // Lớp Product đại diện cho sản phẩm
    private static class Product {
        private String id;
        private String name;
        private ImageIcon image;

        public Product(String id, String name, ImageIcon image) {
            this.id = id;
            this.name = name;
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public ImageIcon getImage() {
            return image;
        }
    }

    // Lớp ImportItem đại diện cho một mục trong phiếu nhập
    private static class ImportItem {
        private Product product;
        private int quantity;
        private double price; // Giá nhập
        private JPanel panel;
        private JTextField qtyField;
        private JTextField priceField;
        private JLabel totalItemLabel;

        public ImportItem(Product product, int quantity, double price, JPanel panel,
                          JTextField qtyField, JTextField priceField, JLabel totalItemLabel) {
            this.product = product;
            this.quantity = quantity;
            this.price = price;
            this.panel = panel;
            this.qtyField = qtyField;
            this.priceField = priceField;
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
            updateTotalItemPrice();
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
            this.priceField.setText(String.valueOf(price));
            updateTotalItemPrice();
        }

        public double getTotalPrice() {
            return quantity * price;
        }

        private void updateTotalItemPrice() {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            totalItemLabel.setText(currencyFormat.format(getTotalPrice()));
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new nhapHangPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
