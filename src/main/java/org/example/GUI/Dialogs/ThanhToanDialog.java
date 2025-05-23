package org.example.GUI.Dialogs;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.BUS.ChiTietHoaDonBUS;
import org.example.BUS.HoaDonBUS;
import org.example.BUS.KhuyenMaiBUS;
import org.example.BUS.SanPhamBUS;
import org.example.Components.CustomButton;
import org.example.Components.CustomCombobox;
import org.example.DTO.chiTietHoaDonDTO;
import org.example.DTO.hoaDonDTO;
import org.example.DTO.khuyenMaiDTO;
import org.example.GUI.BanHangPanel;
import org.example.GUI.BanHangPanel.CartItem;
import org.example.Utils.PDFGenerator;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Dialog hiển thị thông tin thanh toán hóa đơn
 * Cho phép người dùng chọn phương thức thanh toán và nhập số tiền
 */
public class ThanhToanDialog extends JDialog {
    // Các thành phần giao diện
    private JLabel customerNameLabel;
    private JLabel totalLabel;
    private JLabel discountLabel;
    private JLabel amountDueLabel;
    private JLabel amountPaidLabel;
    private JLabel changeLabel;
    private JPanel cashPanel;
    private JPanel amountPanel;
    private CustomCombobox<String> khuyenMaiCombobox;
    private ArrayList<khuyenMaiDTO> dsKhuyenMai;

    // Các biến lưu trữ thông tin thanh toán
    private static double totalAmount = 0;
    private double discount = 0;
    private double amountPaid = 0;
    private String selectedPromotionCode = null;

    // Thông tin hóa đơn
    private String maKhachHang = null;
    private String maNhanVien = null;
    private List<CartItem> cartItems = null;

    // Định dạng số tiền
    private final DecimalFormat formatter = new DecimalFormat("#,###");

    // Màu sắc chủ đạo
    private final Color primaryColor = new Color(0, 102, 204);
    private final Color accentColor = new Color(240, 240, 240);

     /**
     * Khởi tạo dialog thanh toán
     * @param owner Cửa sổ cha
     * @param totalAmount Tổng số tiền cần thanh toán
     * @param maKhachHang Mã khách hàng
     * @param maNhanVien Mã nhân viên
     * @param cartItems Danh sách sản phẩm trong giỏ hàng
     */
    public ThanhToanDialog(Window owner, double totalAmount, String maKhachHang, String maNhanVien, List<CartItem> cartItems) {
        super(owner, "Thanh Toán", ModalityType.APPLICATION_MODAL);
        setSize(450, 650);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
        this.totalAmount = totalAmount;
        this.amountPaid = 0;
        this.maKhachHang = maKhachHang;
        this.maNhanVien = maNhanVien;
        this.cartItems = cartItems;

        initializeUI();
    }

    /**
     * Khởi tạo giao diện người dùng
     */
    private void initializeUI() {
        // Panel chính với nền trắng
        JPanel mainPanel = createMainPanel();

        // Thêm tiêu đề
        mainPanel.add(createHeaderPanel());

        // kích thước
        setSize(450, 700);

        // Thêm thông tin khách hàng
        mainPanel.add(createCustomerPanel());

        // Thêm panel khuyến mãi
        JPanel khuyenMaiPanel = createPromotionPanel();
        mainPanel.add(khuyenMaiPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Thêm thông tin số tiền
        JPanel amountPanelContainer = createAmountPanelContainer();
        mainPanel.add(amountPanelContainer);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Thêm panel thanh toán tiền mặt
        JPanel cashPanelContainer = createCashPanel();
        mainPanel.add(cashPanelContainer);

        // Thêm nút xác nhận thanh toán
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createPaymentButtonPanel());

        add(mainPanel);

        // Cập nhật nhãn ban đầu
        updateLabels();
    }

    /**
     * Tạo panel chính
     * @return JPanel chính
     */
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return mainPanel;
    }

    /**
     * Tạo panel tiêu đề
     * @return JPanel tiêu đề
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("HÓA ĐƠN THANH TOÁN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(primaryColor);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        return headerPanel;
    }

    /**
     * Tạo panel thông tin khách hàng
     * @return JPanel thông tin khách hàng
     */
    private JPanel createCustomerPanel() {
        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customerPanel.setOpaque(false);
        customerNameLabel = new JLabel("Khách hàng: Nguyễn Văn A");
        customerNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        customerNameLabel.setForeground(new Color(51, 51, 51));
        customerPanel.add(customerNameLabel);
        return customerPanel;
    }

    /**
     * Tạo panel hiển thị thông tin số tiền
     * @return JPanel thông tin số tiền
     */
    private JPanel createAmountPanelContainer() {
        Font labelFont = new Font("Arial", Font.BOLD, 13);

        JPanel amountPanelContainer = new JPanel(new BorderLayout());
        amountPanelContainer.setOpaque(false);
        amountPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        amountPanel.setOpaque(false);
        amountPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // Tổng tiền hàng
        JLabel totalTextLabel = new JLabel("Tổng tiền hàng:");
        totalTextLabel.setFont(labelFont);
        totalTextLabel.setForeground(new Color(51, 51, 51));
        totalLabel = new JLabel(formatter.format(this.totalAmount) + " đ", SwingConstants.RIGHT);
        totalLabel.setFont(labelFont);
        totalLabel.setForeground(new Color(51, 51, 51));

        // Giảm giá
        JLabel discountTextLabel = new JLabel("Giảm giá:");
        discountTextLabel.setFont(labelFont);
        discountTextLabel.setForeground(new Color(51, 51, 51));
        discountLabel = new JLabel(formatter.format(discount) + " đ", SwingConstants.RIGHT);
        discountLabel.setFont(labelFont);
        discountLabel.setForeground(new Color(51, 51, 51));

        // Khách cần trả
        JLabel amountDueTextLabel = new JLabel("Khách cần trả:");
        amountDueTextLabel.setFont(labelFont);
        amountDueTextLabel.setForeground(new Color(51, 51, 51));
        amountDueLabel = new JLabel(formatter.format(this.totalAmount - discount) + " đ", SwingConstants.RIGHT);
        amountDueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        amountDueLabel.setForeground(primaryColor);

        // Khách thanh toán
        JLabel amountPaidTextLabel = new JLabel("Khách thanh toán:");
        amountPaidTextLabel.setFont(labelFont);
        amountPaidTextLabel.setForeground(new Color(51, 51, 51));
        amountPaidLabel = new JLabel(formatter.format(this.amountPaid) + " đ", SwingConstants.RIGHT);
        amountPaidLabel.setFont(labelFont);
        amountPaidLabel.setForeground(new Color(51, 51, 51));

        // Tiền thừa
        JLabel changeTextLabel = new JLabel("Tiền thừa trả khách:");
        changeTextLabel.setFont(labelFont);
        changeTextLabel.setForeground(new Color(51, 51, 51));
        changeLabel = new JLabel("0 đ", SwingConstants.RIGHT);
        changeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        changeLabel.setForeground(new Color(0, 153, 0));

        // Thêm các thành phần vào panel
        amountPanel.add(totalTextLabel);
        amountPanel.add(totalLabel);
        amountPanel.add(discountTextLabel);
        amountPanel.add(discountLabel);
        amountPanel.add(amountDueTextLabel);
        amountPanel.add(amountDueLabel);
        amountPanel.add(amountPaidTextLabel);
        amountPanel.add(amountPaidLabel);
        amountPanel.add(changeTextLabel);
        amountPanel.add(changeLabel);

        amountPanelContainer.add(amountPanel, BorderLayout.CENTER);
        return amountPanelContainer;
    }


    /**
     * Tạo panel thanh toán tiền mặt
     * @return JPanel thanh toán tiền mặt
     */
    private JPanel createCashPanel() {
        JPanel cashPanelContainer = new JPanel(new BorderLayout());
        cashPanelContainer.setOpaque(false);
        cashPanelContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                "Mệnh giá"));

        cashPanel = new JPanel();
        cashPanel.setOpaque(false);
        cashPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        cashPanel.setLayout(new GridLayout(0, 3, 10, 10));

        // Thêm các nút mệnh giá tiền
        Integer[] denominations = {1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000};
        for (Integer denom : denominations) {
            CustomButton btn = new CustomButton(formatter.format(denom) + " đ");
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.setButtonColors(CustomButton.ButtonColors.GRAY);
            btn.setPreferredSize(new Dimension(50, 20));
            btn.addActionListener(e -> handleCashButtonClick(denom));
            cashPanel.add(btn);
        }

        // Thêm nút xoá
        CustomButton deleteBtn = new CustomButton("Xoá");
        deleteBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        deleteBtn.setButtonColors(CustomButton.ButtonColors.RED);
        deleteBtn.setPreferredSize(new Dimension(50, 20));
        deleteBtn.addActionListener(e -> {
            amountPaid = 0;
            updateAmountPaidAndChange();
        });
        cashPanel.add(deleteBtn);

        cashPanelContainer.add(cashPanel, BorderLayout.CENTER);
        return cashPanelContainer;
    }


    /**
     * Tạo panel nút xác nhận thanh toán
     * @return JPanel nút xác nhận thanh toán
     */
    private JPanel createPaymentButtonPanel() {
        CustomButton paymentButton = new CustomButton("Xác Nhận Thanh Toán");
        paymentButton.setFont(new Font("Arial", Font.BOLD, 14));
        paymentButton.setPreferredSize(new Dimension(400, 40));

        // Thêm sự kiện khi nhấn nút thanh toán
        paymentButton.addActionListener(e -> {
            // Kiểm tra số tiền đã nhập
            double amountDue = totalAmount - discount;
            if (amountPaid < amountDue) {
                JOptionPane.showMessageDialog(this, "Số tiền thanh toán không đủ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Xử lý thanh toán
            hoaDonDTO hoaDon = processPayment();
            if (hoaDon != null) {
                JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // Hiển thị hộp thoại xác nhận in hóa đơn
                int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có muốn in hóa đơn không?",
                    "In hóa đơn",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {
                    // Hiển thị hộp thoại chọn nơi lưu file PDF
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Lưu hóa đơn PDF");
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Files", "pdf"));

                    int result = fileChooser.showSaveDialog(this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        // Lấy đường dẫn file đã chọn
                        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        // Thêm đuôi .pdf nếu chưa có
                        if (!filePath.toLowerCase().endsWith(".pdf")) {
                            filePath += ".pdf";
                        }

                        // Lấy danh sách chi tiết hóa đơn
                        ArrayList<chiTietHoaDonDTO> dsChiTiet = ChiTietHoaDonBUS.layChiTietHoaDonTheoMaHD(hoaDon.getMaHoaDon());

                        // Tạo file PDF
                        boolean success = PDFGenerator.taoHoaDonPDF(hoaDon, dsChiTiet, filePath);

                        if (success) {
                            // Mở file PDF sau khi tạo thành công
                            try {
                                if (Desktop.isDesktopSupported()) {
                                    Desktop.getDesktop().open(new java.io.File(filePath));
                                }
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Không thể mở file PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }

                setResult(true); // Đánh dấu thanh toán thành công
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(paymentButton);

        JPanel paymentButtonPanel = new JPanel(new BorderLayout());
        paymentButtonPanel.setOpaque(false);
        paymentButtonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        paymentButtonPanel.add(buttonPanel, BorderLayout.CENTER);

        return paymentButtonPanel;
    }

    /**
     * Xử lý sự kiện khi nhấn nút mệnh giá tiền
     * @param amount Số tiền được chọn
     */
    private void handleCashButtonClick(int amount) {
        amountPaid += amount;
        updateAmountPaidAndChange();
    }

    /**
     * Cập nhật số tiền khách trả và tiền thừa
     */
    private void updateAmountPaidAndChange() {
        amountPaidLabel.setText(formatter.format(amountPaid) + " đ");
        double amountDue = totalAmount - discount;
        double change = amountPaid - amountDue;

        if (change >= 0) {
            changeLabel.setText(formatter.format(change) + " đ");
            changeLabel.setForeground(new Color(0, 153, 0));
        } else {
            changeLabel.setText("- " + formatter.format(Math.abs(change)) + " đ");
            changeLabel.setForeground(new Color(204, 0, 0));
        }
    }

    /**
     * Thiết lập tổng số tiền
     * @param amount Tổng số tiền
     */
    public void setTotalAmount(double amount) {
        this.totalAmount = amount;
        this.amountPaid = amount - discount;
        updateLabels();
    }

    /**
     * Thiết lập số tiền giảm giá
     * @param discount Số tiền giảm giá
     */
    public void setDiscount(double discount) {
        this.discount = discount;
        this.amountPaid = totalAmount - discount;
        updateLabels();
    }

    /**
     * Tạo panel chứa combobox khuyến mãi
     * @return JPanel chứa combobox khuyến mãi
     */
    private JPanel createPromotionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                "Khuyến mãi"));

        JPanel contentPanel = new JPanel(new BorderLayout(10, 0));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Label
        JLabel khuyenMaiLabel = new JLabel("Áp dụng khuyến mãi:");
        khuyenMaiLabel.setFont(new Font("Arial", Font.BOLD, 13));
        contentPanel.add(khuyenMaiLabel, BorderLayout.WEST);

        // Lấy danh sách khuyến mãi có hiệu lực
        dsKhuyenMai = KhuyenMaiBUS.layDanhSachKhuyenMaiHieuLuc();
        String[] khuyenMaiItems = new String[dsKhuyenMai.size() + 1];
        khuyenMaiItems[0] = "Không áp dụng khuyến mãi";

        for (int i = 0; i < dsKhuyenMai.size(); i++) {
            khuyenMaiDTO km = dsKhuyenMai.get(i);
            khuyenMaiItems[i + 1] = km.getTenKM() + " (" + km.getPhanTram() + "%)";
        }

        // Combobox khuyến mãi
        khuyenMaiCombobox = new CustomCombobox<>(khuyenMaiItems);
        khuyenMaiCombobox.setPreferredSize(new Dimension(250, 30));
        khuyenMaiCombobox.setSelectedIndex(0); // Mặc định không chọn khuyến mãi

        // Thêm sự kiện khi chọn khuyến mãi
        khuyenMaiCombobox.addActionListener(e -> {
            int selectedIndex = khuyenMaiCombobox.getSelectedIndex();
            if (selectedIndex > 0) {
                // Nếu chọn khuyến mãi (không phải mục đầu tiên)
                khuyenMaiDTO km = dsKhuyenMai.get(selectedIndex - 1);
                // Tính giảm giá dựa trên phần trăm khuyến mãi
                discount = totalAmount * (km.getPhanTram() / 100.0);
                selectedPromotionCode = km.getMaKM();
            } else {
                // Nếu chọn "Không áp dụng khuyến mãi"
                discount = 0;
                selectedPromotionCode = null;
            }
            // Cập nhật lại các nhãn hiển thị số tiền
            amountPaid = totalAmount - discount;
            updateLabels();
        });

        contentPanel.add(khuyenMaiCombobox, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Thiết lập tên khách hàng
     * @param name Tên khách hàng
     */
    public void setCustomerName(String name) {
        customerNameLabel.setText("Khách hàng: " + name);
    }

    /**
     * Lấy mã khuyến mãi đã chọn
     * @return String mã khuyến mãi, null nếu không chọn
     */
    public String getSelectedPromotionCode() {
        return selectedPromotionCode;
    }

    /**
     * Cập nhật tất cả các nhãn hiển thị số tiền
     */
    private void updateLabels() {
        totalLabel.setText(formatter.format(totalAmount) + " đ");
        discountLabel.setText(formatter.format(discount) + " đ");
        amountDueLabel.setText(formatter.format(totalAmount - discount) + " đ");
        amountPaidLabel.setText(formatter.format(amountPaid) + " đ");
        updateAmountPaidAndChange();
    }

    // Biến lưu trạng thái kết quả thanh toán
    private boolean paymentSuccessful = false;

    /**
     * Thiết lập kết quả thanh toán
     * @param success true nếu thanh toán thành công, false nếu thất bại
     */
    private void setResult(boolean success) {
        this.paymentSuccessful = success;
    }

    /**
     * Kiểm tra xem thanh toán có thành công không
     * @return true nếu thanh toán thành công, false nếu thất bại
     */
    public boolean isPaymentSuccessful() {
        return paymentSuccessful;
    }

    /**
     * Xử lý thanh toán hóa đơn
     * @return Đối tượng hóa đơn nếu thanh toán thành công, null nếu thất bại
     */
    private hoaDonDTO processPayment() {
        try {
            // Tạo hóa đơn mới
            HoaDonBUS hoaDonBUS = new HoaDonBUS();
            hoaDonDTO hoaDon = hoaDonBUS.taoHoaDonMoi(maKhachHang, maNhanVien, selectedPromotionCode);

            if (hoaDon == null) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tạo hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Thêm chi tiết hóa đơn
            ArrayList<chiTietHoaDonDTO> dsChiTiet = new ArrayList<>();

            for (CartItem item : cartItems) {
                chiTietHoaDonDTO chiTiet = new chiTietHoaDonDTO();
                chiTiet.setMaHoaDon(hoaDon.getMaHoaDon());
                chiTiet.setMaSP(item.getSanPham().getMaSP());
                chiTiet.setSoLuong(item.getQuantity());
                chiTiet.setGiaBan(item.getSanPham().getGiaBan());

                dsChiTiet.add(chiTiet);


                // in ra
            }

            // Thêm tất cả chi tiết hóa đơn
            int result = ChiTietHoaDonBUS.themNhieuChiTietHoaDon(dsChiTiet);

            if (result <= 0) {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm chi tiết hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            return hoaDon;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThanhToanDialog dialog = new ThanhToanDialog(null, 100000, "KH001", "NV001", new ArrayList<>());
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        });
    }
}