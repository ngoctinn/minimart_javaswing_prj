package org.example.GUI.Dialogs;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class ThanhToanDialog extends JDialog {
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
    private static double totalAmount = 0;
    private double discount = 0;
    private double amountPaid = 0;
    private DecimalFormat formatter = new DecimalFormat("#,###");
    private Color primaryColor = new Color(0, 102, 204);
    private Color accentColor = new Color(240, 240, 240);

    public ThanhToanDialog(Window owner, double totalAmount) {
        super(owner, "Thanh Toán", ModalityType.APPLICATION_MODAL);
        setSize(450, 650);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
        this.totalAmount = totalAmount;
        this.amountPaid = totalAmount - discount;

        // Main panel with white background
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header with title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("HÓA ĐƠN THANH TOÁN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(primaryColor);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(headerPanel);

        // Hiển thị tên khách hàng
        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customerPanel.setOpaque(false);
        customerNameLabel = new JLabel("Khách hàng: Nguyễn Văn A");
        customerNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        customerNameLabel.setForeground(new Color(51, 51, 51));
        customerPanel.add(customerNameLabel);
        mainPanel.add(customerPanel);

        // Font chỉnh sửa
        Font labelFont = new Font("Arial", Font.BOLD, 13);

        // Amount information with rounded border
        JPanel amountPanelContainer = new JPanel(new BorderLayout());
        amountPanelContainer.setOpaque(false);
        amountPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        amountPanel.setOpaque(false);
        amountPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel totalTextLabel = new JLabel("Tổng tiền hàng:");
        totalTextLabel.setFont(labelFont);
        totalTextLabel.setForeground(new Color(51, 51, 51));
        totalLabel = new JLabel(formatter.format(this.totalAmount) + " đ", SwingConstants.RIGHT);
        totalLabel.setFont(labelFont);
        totalLabel.setForeground(new Color(51, 51, 51));

        JLabel discountTextLabel = new JLabel("Giảm giá:");
        discountTextLabel.setFont(labelFont);
        discountTextLabel.setForeground(new Color(51, 51, 51));
        discountLabel = new JLabel(formatter.format(discount) + " đ", SwingConstants.RIGHT);
        discountLabel.setFont(labelFont);
        discountLabel.setForeground(new Color(51, 51, 51));

        JLabel amountDueTextLabel = new JLabel("Khách cần trả:");
        amountDueTextLabel.setFont(labelFont);
        amountDueTextLabel.setForeground(new Color(51, 51, 51));
        amountDueLabel = new JLabel(formatter.format(this.totalAmount - discount) + " đ", SwingConstants.RIGHT);
        amountDueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        amountDueLabel.setForeground(primaryColor);

        JLabel amountPaidTextLabel = new JLabel("Khách thanh toán:");
        amountPaidTextLabel.setFont(labelFont);
        amountPaidTextLabel.setForeground(new Color(51, 51, 51));
        amountPaidLabel = new JLabel(formatter.format(this.amountPaid) + " đ", SwingConstants.RIGHT);
        amountPaidLabel.setFont(labelFont);
        amountPaidLabel.setForeground(new Color(51, 51, 51));

        JLabel changeTextLabel = new JLabel("Tiền thừa trả khách:");
        changeTextLabel.setFont(labelFont);
        changeTextLabel.setForeground(new Color(51, 51, 51));
        changeLabel = new JLabel("0 đ", SwingConstants.RIGHT);
        changeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        changeLabel.setForeground(new Color(0, 153, 0));

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
        mainPanel.add(amountPanelContainer);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Payment method with styled radio buttons
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
        mainPanel.add(methodPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Cash panel with styled buttons
        JPanel cashPanelContainer = new JPanel(new BorderLayout());
        cashPanelContainer.setOpaque(false);
        cashPanelContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                "Mệnh giá"));

        cashPanel = new JPanel();
        cashPanel.setOpaque(false);
        cashPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        cashPanel.setLayout(new GridLayout(0, 3, 10, 10));
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
        cashPanelContainer.setVisible(false);
        mainPanel.add(cashPanelContainer);

        // Transfer panel with QR code
        transferPanel = new JPanel(new BorderLayout());
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
        transferPanel.setVisible(false);
        mainPanel.add(transferPanel);

        // Add event listeners for payment method buttons
        cashButton.addActionListener(e -> {
            cashPanelContainer.setVisible(true);
            transferPanel.setVisible(false);
            // Chỉnh lại GridLayout cho amountPanel khi chọn thanh toán tiền mặt
            amountPanel.removeAll();
            amountPanel.setLayout(new GridLayout(5, 2, 10, 10));
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
        });

        transferButton.addActionListener(e -> {
            cashPanelContainer.setVisible(false);
            transferPanel.setVisible(true);
            // Chỉnh lại GridLayout cho amountPanel khi chọn chuyển khoản
            amountPanel.removeAll();
            amountPanel.setLayout(new GridLayout(3, 2, 10, 10));
            amountPanel.add(totalTextLabel);
            amountPanel.add(totalLabel);
            amountPanel.add(discountTextLabel);
            amountPanel.add(discountLabel);
            amountPanel.add(amountDueTextLabel);
            amountPanel.add(amountDueLabel);
            amountPanel.revalidate();
            amountPanel.repaint();
        });

        // Payment button
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

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(paymentButtonPanel);

        add(mainPanel);

        // Set default selection
        cashButton.setSelected(true);
        cashPanelContainer.setVisible(true);

        // Update labels initially
        updateLabels();
    }

    private void handleCashButtonClick(int amount) {
        amountPaid += amount;
        updateAmountPaidAndChange();
    }

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

    public void setTotalAmount(double amount) {
        this.totalAmount = amount;
        this.amountPaid = amount - discount;
        updateLabels();
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        this.amountPaid = totalAmount - discount;
        updateLabels();
    }

    public void setCustomerName(String name) {
        customerNameLabel.setText("Khách hàng: " + name);
    }

    private void updateLabels() {
        totalLabel.setText(formatter.format(totalAmount) + " đ");
        discountLabel.setText(formatter.format(discount) + " đ");
        amountDueLabel.setText(formatter.format(totalAmount - discount) + " đ");
        amountPaidLabel.setText(formatter.format(amountPaid) + " đ");
        updateAmountPaidAndChange();
    }
}