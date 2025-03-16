package org.example.GUI.Dialogs;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

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
    private ButtonGroup paymentMethodGroup;
    private JPanel cashPanel;
    private JPanel transferPanel;
    private JPanel amountPanel;

    // Các biến lưu trữ thông tin thanh toán
    private static double totalAmount = 0;
    private double discount = 0;
    private double amountPaid = 0;

    // Định dạng số tiền
    private final DecimalFormat formatter = new DecimalFormat("#,###");

    // Màu sắc chủ đạo
    private final Color primaryColor = new Color(0, 102, 204);
    private final Color accentColor = new Color(240, 240, 240);

    /**
     * Khởi tạo dialog thanh toán
     * @param owner Cửa sổ cha
     * @param totalAmount Tổng số tiền cần thanh toán
     */
    public ThanhToanDialog(Window owner, double totalAmount) {
        super(owner, "Thanh Toán", ModalityType.APPLICATION_MODAL);
        setSize(450, 650);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
        this.totalAmount = totalAmount;
        this.amountPaid = totalAmount - discount;

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

        // Thêm thông tin khách hàng
        mainPanel.add(createCustomerPanel());

        // Thêm thông tin số tiền
        JPanel amountPanelContainer = createAmountPanelContainer();
        mainPanel.add(amountPanelContainer);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Thêm phương thức thanh toán
        JPanel methodPanel = createPaymentMethodPanel();
        mainPanel.add(methodPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Thêm panel thanh toán tiền mặt
        JPanel cashPanelContainer = createCashPanel();
        mainPanel.add(cashPanelContainer);

        // Thêm panel thanh toán chuyển khoản
        transferPanel = createTransferPanel();
        transferPanel.setVisible(false);
        mainPanel.add(transferPanel);

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
     * Tạo panel phương thức thanh toán
     * @return JPanel phương thức thanh toán
     */
    private JPanel createPaymentMethodPanel() {
        JPanel methodPanel = new JPanel();
        methodPanel.setOpaque(false);
        methodPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                "Phương thức thanh toán"));
        methodPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        paymentMethodGroup = new ButtonGroup();
        JRadioButton cashButton = new JRadioButton("Tiền mặt");
        JRadioButton transferButton = new JRadioButton("Chuyển khoản");

        cashButton.setFont(new Font("Arial", Font.BOLD, 13));
        cashButton.setForeground(new Color(51, 51, 51));
        cashButton.setOpaque(false);

        transferButton.setFont(new Font("Arial", Font.BOLD, 13));
        transferButton.setForeground(new Color(51, 51, 51));
        transferButton.setOpaque(false);

        paymentMethodGroup.add(cashButton);
        paymentMethodGroup.add(transferButton);

        methodPanel.add(cashButton);
        methodPanel.add(transferButton);

        // Thêm sự kiện cho nút thanh toán tiền mặt
        cashButton.addActionListener(e -> {
            cashPanel.getParent().setVisible(true);
            transferPanel.setVisible(false);
            // Chỉnh lại GridLayout cho amountPanel khi chọn thanh toán tiền mặt
            updateAmountPanelForCashPayment();
        });

        // Thêm sự kiện cho nút thanh toán chuyển khoản
        transferButton.addActionListener(e -> {
            cashPanel.getParent().setVisible(false);
            transferPanel.setVisible(true);
            // Chỉnh lại GridLayout cho amountPanel khi chọn chuyển khoản
            updateAmountPanelForTransfer();
        });

        // Mặc định chọn thanh toán tiền mặt
        cashButton.setSelected(true);

        return methodPanel;
    }

    /**
     * Cập nhật panel thông tin số tiền cho thanh toán tiền mặt
     */
    private void updateAmountPanelForCashPayment() {
        Font labelFont = new Font("Arial", Font.BOLD, 13);

        amountPanel.removeAll();
        amountPanel.setLayout(new GridLayout(5, 2, 10, 10));

        // Tổng tiền hàng
        JLabel totalTextLabel = new JLabel("Tổng tiền hàng:");
        totalTextLabel.setFont(labelFont);
        totalTextLabel.setForeground(new Color(51, 51, 51));

        // Giảm giá
        JLabel discountTextLabel = new JLabel("Giảm giá:");
        discountTextLabel.setFont(labelFont);
        discountTextLabel.setForeground(new Color(51, 51, 51));

        // Khách cần trả
        JLabel amountDueTextLabel = new JLabel("Khách cần trả:");
        amountDueTextLabel.setFont(labelFont);
        amountDueTextLabel.setForeground(new Color(51, 51, 51));

        // Khách thanh toán
        JLabel amountPaidTextLabel = new JLabel("Khách thanh toán:");
        amountPaidTextLabel.setFont(labelFont);
        amountPaidTextLabel.setForeground(new Color(51, 51, 51));

        // Tiền thừa
        JLabel changeTextLabel = new JLabel("Tiền thừa trả khách:");
        changeTextLabel.setFont(labelFont);
        changeTextLabel.setForeground(new Color(51, 51, 51));

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

        amountPanel.revalidate();
        amountPanel.repaint();
    }

    /**
     * Cập nhật panel thông tin số tiền cho thanh toán chuyển khoản
     */
    private void updateAmountPanelForTransfer() {
        Font labelFont = new Font("Arial", Font.BOLD, 13);

        amountPanel.removeAll();
        amountPanel.setLayout(new GridLayout(3, 2, 10, 10));

        // Tổng tiền hàng
        JLabel totalTextLabel = new JLabel("Tổng tiền hàng:");
        totalTextLabel.setFont(labelFont);
        totalTextLabel.setForeground(new Color(51, 51, 51));

        // Giảm giá
        JLabel discountTextLabel = new JLabel("Giảm giá:");
        discountTextLabel.setFont(labelFont);
        discountTextLabel.setForeground(new Color(51, 51, 51));

        // Khách cần trả
        JLabel amountDueTextLabel = new JLabel("Khách cần trả:");
        amountDueTextLabel.setFont(labelFont);
        amountDueTextLabel.setForeground(new Color(51, 51, 51));

        amountPanel.add(totalTextLabel);
        amountPanel.add(totalLabel);
        amountPanel.add(discountTextLabel);
        amountPanel.add(discountLabel);
        amountPanel.add(amountDueTextLabel);
        amountPanel.add(amountDueLabel);

        amountPanel.revalidate();
        amountPanel.repaint();
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
     * Tạo panel thanh toán chuyển khoản
     * @return JPanel thanh toán chuyển khoản
     */
    private JPanel createTransferPanel() {
        JPanel transferPanel = new JPanel(new BorderLayout());
        transferPanel.setOpaque(false);
        transferPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel qrLabel = new JLabel(new FlatSVGIcon("Images/QRBank.svg", 200, 200));
        JLabel scanInstructionLabel = new JLabel("Quét mã QR để thanh toán", SwingConstants.CENTER);
        scanInstructionLabel.setFont(new Font("Arial", Font.BOLD, 13));
        scanInstructionLabel.setForeground(primaryColor);

        transferPanel.add(qrLabel, BorderLayout.CENTER);
        transferPanel.add(scanInstructionLabel, BorderLayout.SOUTH);

        return transferPanel;
    }

    /**
     * Tạo panel nút xác nhận thanh toán
     * @return JPanel nút xác nhận thanh toán
     */
    private JPanel createPaymentButtonPanel() {
        CustomButton paymentButton = new CustomButton("Xác Nhận Thanh Toán");
        paymentButton.setFont(new Font("Arial", Font.BOLD, 14));
        paymentButton.setPreferredSize(new Dimension(400, 40));

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
     * Thiết lập tên khách hàng
     * @param name Tên khách hàng
     */
    public void setCustomerName(String name) {
        customerNameLabel.setText("Khách hàng: " + name);
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
}