package org.example.GUI.Panels.giaoDichPanel;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomCombobox;
import org.example.Components.CustomTextField;
import org.example.Components.RoundedPanel;
import org.example.GUI.Dialogs.ThemNCCDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class nhapHangPanel extends JPanel {
    // region Khai báo các thành phần giao diện
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel bottomPanelLeft;
    private JPanel bottomPanelRight;
    //end region

    // Các thành phần của topPanel
    private CustomTextField searchField;
    private CustomButton searchButton;
    private CustomButton refreshButton;


    // Các thành phẩn của bottomPanelLeft
    private JPanel subPanelTop;
    private JPanel subPanelCenter;
    private JPanel subPanelBottom;
    private CustomCombobox<String> supplierComboBox;

    // Các thành phần của bottomPanelRight
    private JPanel productListPanel;
    private JScrollPane productScrollPane;

    // Danh sách sản phẩm mẫu
    private List<SanPhamInfo> danhSachSanPham;

    public nhapHangPanel() {
        initGUI();
    }

    private void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanelLeft();
        setupBottomPanelRight();

        // Tạo dữ liệu mẫu và hiển thị
        createSampleProducts();
        displayProductsInRightPanel();

        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);
    }

    //Thiết lập thuộc tính cho panel chính
    private void setupMainPanel() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(new java.awt.Color(225, 225, 225));
        this.setVisible(true);
    }

    //Tạo và thiết lập các panel con
    private void createPanels() {
        // Tạo các panel con
        // Tạo các panel con với góc bo tròn 20px
        topPanel = new RoundedPanel(20);
        bottomPanel = new RoundedPanel(20);
        bottomPanelLeft = new RoundedPanel(20);
        bottomPanelRight = new RoundedPanel(20);

        // Thiết lập màu nền cho các panel
        topPanel.setBackground(Color.WHITE);  // Màu trắng cho panel trên
        bottomPanel.setBackground(new Color(225, 225, 225)); // Màu xám nhạt cho panel dưới
        bottomPanelLeft.setBackground(Color.WHITE);  // Màu trắng cho panel trái
        bottomPanelRight.setBackground(Color.WHITE); // Màu trắng cho panel phải

        // Thiết lập kích thước cho các panel
        topPanel.setPreferredSize(new Dimension(1270, 50));    // Panel trên cao 50px
        bottomPanel.setPreferredSize(new Dimension(1270, 900)); // Panel dưới cao 900px

        // Thiết lập tỷ lệ panel dưới - 70% bên trái, 30% bên phải
        bottomPanelLeft.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.7), 0));
        bottomPanelRight.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.3), 0));

        // Thiết lập layout cho các panel
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Các thành phần căn trái, cách nhau 10px
        bottomPanel.setLayout(new BorderLayout(5, 0));  // Layout BorderLayout với khoảng cách ngang 5px
        bottomPanelLeft.setLayout(new BorderLayout());  // Layout BorderLayout cho panel trái
        bottomPanelRight.setLayout(new BorderLayout()); // Layout BorderLayout cho panel phải
    }

    private void setupTopPanel() {
        // Tiêu đề
        JLabel title = new JLabel("Nhâp Hàng");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        topPanel.add(title);

        // ô tìm kiếm
        searchField = new CustomTextField("Tìm kiếm sản phẩm...");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        topPanel.add(searchField);

        // nút tìm kiếm
        searchButton = new CustomButton("Tìm");
        searchButton.setPreferredSize(new Dimension(70, 30));
        topPanel.add(searchButton);

        // nút làm mới
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 16, 16);
        refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setPreferredSize(new Dimension(30, 30));
        topPanel.add(refreshButton);
    }


    //====================setupBottomPanelLeft================================
    private void setupBottomPanelLeft() {
        // Tạo các thành phần của bottomPanelLeft
        subPanelTop = new JPanel();
        subPanelCenter = new JPanel();
        subPanelBottom = new JPanel();

        //layout
        subPanelTop.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        // set layout cho subPanelCenter là BoxLayout theo chiều dọc
        subPanelCenter.setLayout(new BoxLayout(subPanelCenter, BoxLayout.Y_AXIS));
        subPanelBottom.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        //color
        subPanelTop.setBackground(Color.WHITE);
        subPanelCenter.setBackground(Color.WHITE);
        subPanelBottom.setBackground(Color.WHITE);

        // gồm 3 panel con
        setupSubPanelTop();
        setupSubPanelCenter();
        setupSubPanelBottom();

        bottomPanelLeft.add(subPanelTop, BorderLayout.NORTH);
        bottomPanelLeft.add(new JScrollPane(subPanelCenter), BorderLayout.CENTER);
        bottomPanelLeft.add(subPanelBottom, BorderLayout.SOUTH);
    }

    private void setupSubPanelTop() {
        // Label chọn nhà cung cấp
        JLabel supplierLabel = new JLabel("Chọn nhà cung cấp:");
        supplierLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        subPanelTop.add(supplierLabel);
        subPanelTop.add(Box.createHorizontalStrut(10));

        // Combobox chọn nhà cung cấp
        String[] suppliers = {"Nhà cung cấp 1", "Nhà cung cấp 2", "Nhà cung cấp 3"};
        supplierComboBox = new CustomCombobox<>(suppliers);
        supplierComboBox.setPlaceholder("- Chọn nhà cung cấp -");
        supplierComboBox.setPreferredSize(new Dimension(200, 30));
        subPanelTop.add(supplierComboBox);

        // Tạo nút thêm nhà cung cấp mới
        FlatSVGIcon createSupplierIcon = new FlatSVGIcon("Icons/cong.svg", 10, 10); // Biểu tượng dấu cộng
        CustomButton createSupplierButton = new CustomButton("", createSupplierIcon);
        createSupplierButton.setPreferredSize(new Dimension(30, 30));
        createSupplierButton.setMaximumSize(new Dimension(30, 30)); // Giới hạn chiều cao tối đa
        createSupplierButton.addActionListener(e -> {
            // Mở dialog thêm nhà cung cấp mới
            new ThemNCCDialog();
            // TODO: Cập nhật lại danh sách nhà cung cấp sau khi thêm
        });
        subPanelTop.add(createSupplierButton);
    }
    private void setupSubPanelCenter() {
        // Tạo panel chứa các cột trong bảng chi tiết phiếu nhập
        JPanel headerCartPanel = new JPanel();
        headerCartPanel.setLayout(new BoxLayout(headerCartPanel, BoxLayout.X_AXIS));
        headerCartPanel.setBackground(new Color(245, 245, 245)); // Màu nền xám nhạt
        headerCartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)), // Viền dưới 2px màu xám
                BorderFactory.createEmptyBorder(10, 15, 10, 15) // Padding 10px trên dưới, 15px trái phải
        ));

        // Định nghĩa font và màu sắc cho các tiêu đề cột
        Font headerFont = new Font("Segoe UI", Font.BOLD, 14);
        Color headerColor = new Color(60, 60, 60); // Màu chữ xám đậm

        // Tạo các panel con cho từng cột với tỷ lệ cố định
        JPanel codePanel = new JPanel(new BorderLayout());
        JPanel namePanel = new JPanel(new BorderLayout());
        JPanel qtyPanel = new JPanel(new BorderLayout());
        JPanel pricePanel = new JPanel(new BorderLayout());
        JPanel totalPanel = new JPanel(new BorderLayout());
        JPanel batchPanel = new JPanel(new BorderLayout());

        // Đặt màu nền cho các panel con
        codePanel.setBackground(new Color(245, 245, 245));
        namePanel.setBackground(new Color(245, 245, 245));
        qtyPanel.setBackground(new Color(245, 245, 245));
        pricePanel.setBackground(new Color(245, 245, 245));
        totalPanel.setBackground(new Color(245, 245, 245));
        batchPanel.setBackground(new Color(245, 245, 245));

        // Đặt kích thước cho các panel con
        codePanel.setPreferredSize(new Dimension(100, 40));
        namePanel.setPreferredSize(new Dimension(250, 40));
        qtyPanel.setPreferredSize(new Dimension(80, 40));
        pricePanel.setPreferredSize(new Dimension(100, 40));
        totalPanel.setPreferredSize(new Dimension(100, 40));
        batchPanel.setPreferredSize(new Dimension(150, 40));

        // Tạo các nhãn tiêu đề cho từng cột
        // Cột 1: Mã sản phẩm
        JLabel codeHeader = new JLabel("Mã SP", JLabel.LEFT);
        codeHeader.setFont(headerFont);
        codeHeader.setForeground(headerColor);

        // Cột 2: Tên sản phẩm
        JLabel nameHeader = new JLabel("Tên sản phẩm", JLabel.LEFT);
        nameHeader.setFont(headerFont);
        nameHeader.setForeground(headerColor);

        // Cột 3: Số lượng
        JLabel qtyHeader = new JLabel("Số lượng", JLabel.LEFT);
        qtyHeader.setFont(headerFont);
        qtyHeader.setForeground(headerColor);

        // Cột 4: Đơn giá
        JLabel priceHeader = new JLabel("Đơn giá", JLabel.LEFT);
        priceHeader.setFont(headerFont);
        priceHeader.setForeground(headerColor);

        // Cột 5: Thành tiền
        JLabel totalHeader = new JLabel("Thành tiền", JLabel.LEFT);
        totalHeader.setFont(headerFont);
        totalHeader.setForeground(headerColor);

        // Cột 6: Lô hàng
        JLabel batchHeader = new JLabel("Lô hàng", JLabel.LEFT);
        batchHeader.setFont(headerFont);
        batchHeader.setForeground(headerColor);

        // Thêm các nhãn vào panel con tương ứng
        codePanel.add(codeHeader, BorderLayout.CENTER);
        namePanel.add(nameHeader, BorderLayout.CENTER);
        qtyPanel.add(qtyHeader, BorderLayout.CENTER);
        pricePanel.add(priceHeader, BorderLayout.CENTER);
        totalPanel.add(totalHeader, BorderLayout.CENTER);
        batchPanel.add(batchHeader, BorderLayout.CENTER);

        // Thêm các panel con vào headerCartPanel
        headerCartPanel.add(codePanel);
        headerCartPanel.add(Box.createHorizontalStrut(10)); // Khoảng cách giữa các cột
        headerCartPanel.add(namePanel);
        headerCartPanel.add(Box.createHorizontalStrut(10));
        headerCartPanel.add(qtyPanel);
        headerCartPanel.add(Box.createHorizontalStrut(10));
        headerCartPanel.add(pricePanel);
        headerCartPanel.add(Box.createHorizontalStrut(10));
        headerCartPanel.add(totalPanel);
        headerCartPanel.add(Box.createHorizontalStrut(10));
        headerCartPanel.add(batchPanel);

        // Đảm bảo headerCartPanel có chiều rộng tối đa
        headerCartPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, headerCartPanel.getPreferredSize().height));

        subPanelCenter.add(headerCartPanel);
    }

    private void setupSubPanelBottom() {
        // Tạo panel chứa các nút điều khiển
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        controlPanel.setBackground(Color.WHITE);

        // Nút Hủy
        CustomButton cancelButton = new CustomButton("Hủy");
        cancelButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.setForeground(Color.WHITE);

        // Nút Lưu
        CustomButton saveButton = new CustomButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 35));
        saveButton.setForeground(Color.WHITE);

        // Thêm các nút vào panel
        controlPanel.add(cancelButton);
        controlPanel.add(saveButton);

        subPanelBottom.add(controlPanel);
    }

    //==================END=======================

    private void setupBottomPanelRight() {
        // Tạo panel chứa danh sách sản phẩm
        productListPanel = new JPanel();
        productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
        productListPanel.setBackground(Color.WHITE);

        // Tạo JScrollPane để có thể cuộn danh sách sản phẩm
        productScrollPane = new JScrollPane(productListPanel);
        productScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        productScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Thêm tiêu đề cho panel bên phải
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel headerLabel = new JLabel("Danh sách sản phẩm");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerPanel.add(headerLabel);

        // Đảm bảo headerPanel có chiều rộng tối đa
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, headerPanel.getPreferredSize().height));

        // Thêm các thành phần vào bottomPanelRight
        bottomPanelRight.setLayout(new BorderLayout());
        bottomPanelRight.add(headerPanel, BorderLayout.NORTH);
        bottomPanelRight.add(productScrollPane, BorderLayout.CENTER);
    }

    /**
     * Tạo danh sách sản phẩm mẫu
     */
    private void createSampleProducts() {
        danhSachSanPham = new ArrayList<>();

        // Thêm các sản phẩm mẫu
        danhSachSanPham.add(new SanPhamInfo("SP001", "Sữa tươi Vinamilk 180ml", "Thùng 48 hộp"));
        danhSachSanPham.add(new SanPhamInfo("SP002", "Mì gói Hảo Hảo tôm chua cay", "Thùng 30 gói"));
        danhSachSanPham.add(new SanPhamInfo("SP003", "Nước giải khát Coca Cola 330ml", "Thùng 24 lon"));
        danhSachSanPham.add(new SanPhamInfo("SP004", "Bánh Oreo vị Chocolate", "Hộp 10 gói"));
        danhSachSanPham.add(new SanPhamInfo("SP005", "Dầu ăn Neptune 1L", "Thùng 12 chai"));
        danhSachSanPham.add(new SanPhamInfo("SP006", "Nước mắm Nam Ngư 500ml", "Thùng 24 chai"));
        danhSachSanPham.add(new SanPhamInfo("SP007", "Bột giặt Omo 800g", "Thùng 12 gói"));
        danhSachSanPham.add(new SanPhamInfo("SP008", "Nước rửa chén Sunlight 400g", "Thùng 24 chai"));
        danhSachSanPham.add(new SanPhamInfo("SP009", "Kem đánh răng Colgate 200g", "Thùng 24 tuýp"));
        danhSachSanPham.add(new SanPhamInfo("SP010", "Sữa tắm Dove 500ml", "Thùng 12 chai"));
    }

    /**
     * Hiển thị danh sách sản phẩm ở panel bên phải
     */
    private void displayProductsInRightPanel() {
        for (SanPhamInfo sanPham : danhSachSanPham) {
            JPanel productPanel = createProductPanel(sanPham);
            productListPanel.add(productPanel);
            productListPanel.add(Box.createVerticalStrut(5)); // Khoảng cách giữa các sản phẩm
        }
    }

    /**
     * Tạo panel hiển thị thông tin sản phẩm
     * @param sanPham Thông tin sản phẩm cần hiển thị
     * @return JPanel chứa thông tin sản phẩm
     */
    private JPanel createProductPanel(SanPhamInfo sanPham) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Panel chứa thông tin sản phẩm
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        // Mã sản phẩm
        JLabel codeLabel = new JLabel(sanPham.getMaSP());
        codeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        codeLabel.setForeground(new Color(100, 100, 100));

        // Tên sản phẩm
        JLabel nameLabel = new JLabel(sanPham.getTenSP());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Đơn vị tính
        JLabel unitLabel = new JLabel(sanPham.getDonViTinh());
        unitLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        unitLabel.setForeground(new Color(100, 100, 100));

        // Thêm các thành phần vào infoPanel
        infoPanel.add(codeLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(unitLabel);

        // Nút thêm sản phẩm
        CustomButton addButton = new CustomButton("Thêm");
        addButton.setPreferredSize(new Dimension(80, 30));
        addButton.setMaximumSize(new Dimension(80, 30)); // Giới hạn chiều cao tối đa
        addButton.setBackground(new Color(13, 110, 253)); // Màu xanh
        addButton.setForeground(Color.WHITE);

        // Thêm action listener cho nút thêm
        addButton.addActionListener(e -> {
            // Tạo panel sản phẩm đã chọn và thêm vào bên trái
            JPanel selectedProductPanel = createSelectedProductPanel(sanPham);
            addSanPhamToLeftPanel(selectedProductPanel);
        });

        // Thêm các thành phần vào panel chính
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.EAST);

        // Đảm bảo panel có chiều rộng tối đa
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));

        return panel;
    }

    /**
     * Tạo panel hiển thị thông tin sản phẩm đã chọn
     * @param sanPham Thông tin sản phẩm đã chọn
     * @return JPanel chứa thông tin sản phẩm đã chọn
     */
    private JPanel createSelectedProductPanel(SanPhamInfo sanPham) {
        JPanel cartItemPanel = new JPanel();
        cartItemPanel.setLayout(new BoxLayout(cartItemPanel, BoxLayout.X_AXIS));
        cartItemPanel.setBackground(Color.WHITE);
        cartItemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)), // Viền dưới màu xám
                BorderFactory.createEmptyBorder(10, 15, 10, 15) // Padding 10px trên dưới, 15px trái phải
        ));

        // Tạo các panel con cho từng cột với tỷ lệ cố định
        JPanel codePanel = new JPanel(new BorderLayout());
        JPanel namePanel = new JPanel(new BorderLayout());
        JPanel qtyPanel = new JPanel(new BorderLayout());
        JPanel pricePanel = new JPanel(new BorderLayout());
        JPanel totalPanel = new JPanel(new BorderLayout());
        JPanel batchPanel = new JPanel(new BorderLayout());

        // Đặt màu nền cho các panel con
        codePanel.setBackground(Color.WHITE);
        namePanel.setBackground(Color.WHITE);
        qtyPanel.setBackground(Color.WHITE);
        pricePanel.setBackground(Color.WHITE);
        totalPanel.setBackground(Color.WHITE);
        batchPanel.setBackground(Color.WHITE);

        // Đặt kích thước cho các panel con
        codePanel.setPreferredSize(new Dimension(100, 30));
        namePanel.setPreferredSize(new Dimension(250, 30));
        qtyPanel.setPreferredSize(new Dimension(80, 30));
        pricePanel.setPreferredSize(new Dimension(100, 30));
        totalPanel.setPreferredSize(new Dimension(100, 30));
        batchPanel.setPreferredSize(new Dimension(150, 30));

        // Cột 1: Mã sản phẩm
        JLabel codeLabel = new JLabel(sanPham.getMaSP(), JLabel.LEFT);
        codeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        codePanel.add(codeLabel, BorderLayout.CENTER);

        // Cột 2: Tên sản phẩm
        JLabel nameLabel = new JLabel(sanPham.getTenSP(), JLabel.LEFT);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        namePanel.add(nameLabel, BorderLayout.CENTER);

        // Cột 3: Số lượng
        CustomTextField qtyField = new CustomTextField("");
        qtyField.setText("1");
        qtyField.setHorizontalAlignment(JTextField.LEFT);
        qtyPanel.add(qtyField, BorderLayout.CENTER);

        // Cột 4: Đơn giá
        CustomTextField priceField = new CustomTextField("");
        priceField.setText("10000");
        priceField.setHorizontalAlignment(JTextField.LEFT);
        pricePanel.add(priceField, BorderLayout.CENTER);

        // Cột 5: Thành tiền
        JLabel totalItemLabel = new JLabel("10000", JLabel.LEFT);
        totalItemLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalPanel.add(totalItemLabel, BorderLayout.CENTER);

        // Cột 6: Lô hàng
        JPanel batchContentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        batchContentPanel.setBackground(Color.WHITE);

        String[] batches = {"Chọn lô hàng", "LH1001", "LH1002", "LH1003"};
        CustomCombobox<String> batchComboBox = new CustomCombobox<>(batches);
        batchComboBox.setPreferredSize(new Dimension(100, 30));
        batchContentPanel.add(batchComboBox);

        // Nút thêm lô hàng
        FlatSVGIcon createBatchIcon = new FlatSVGIcon("Icons/cong.svg", 10, 10);
        CustomButton createBatchButton = new CustomButton("", createBatchIcon);
        createBatchButton.setPreferredSize(new Dimension(30, 30));
        createBatchButton.setMaximumSize(new Dimension(30, 30)); // Giới hạn chiều cao tối đa
        createBatchButton.addActionListener(e -> {
            // Mở dialog thêm lô hàng mới
        });
        batchContentPanel.add(createBatchButton);
        batchPanel.add(batchContentPanel, BorderLayout.CENTER);

        // Thêm các panel con vào cartItemPanel
        cartItemPanel.add(codePanel);
        cartItemPanel.add(Box.createHorizontalStrut(10)); // Khoảng cách giữa các cột
        cartItemPanel.add(namePanel);
        cartItemPanel.add(Box.createHorizontalStrut(10));
        cartItemPanel.add(qtyPanel);
        cartItemPanel.add(Box.createHorizontalStrut(10));
        cartItemPanel.add(pricePanel);
        cartItemPanel.add(Box.createHorizontalStrut(10));
        cartItemPanel.add(totalPanel);
        cartItemPanel.add(Box.createHorizontalStrut(10));
        cartItemPanel.add(batchPanel);

        // Đảm bảo panel có chiều rộng tối đa
        cartItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, cartItemPanel.getPreferredSize().height));

        return cartItemPanel;
    }

    /**
     * Thêm sản phẩm vào khu vực bên trái
     * @param sanPhamPanel Panel chứa thông tin sản phẩm cần thêm
     */
    public void addSanPhamToLeftPanel(JPanel sanPhamPanel) {
        // Thêm panel sản phẩm vào subPanelCenter
        subPanelCenter.add(sanPhamPanel);

        // Cập nhật giao diện
        subPanelCenter.revalidate();
        subPanelCenter.repaint();
    }

    /**
     * Lớp lưu trữ thông tin sản phẩm
     */
    private static class SanPhamInfo {
        private String maSP;
        private String tenSP;
        private String donViTinh;

        public SanPhamInfo(String maSP, String tenSP, String donViTinh) {
            this.maSP = maSP;
            this.tenSP = tenSP;
            this.donViTinh = donViTinh;
        }

        public String getMaSP() {
            return maSP;
        }

        public String getTenSP() {
            return tenSP;
        }

        public String getDonViTinh() {
            return donViTinh;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            JFrame frame = new JFrame("Tạo Phiếu Nhập");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.add(new nhapHangPanel());
            frame.setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
            frame.setVisible(true);
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
