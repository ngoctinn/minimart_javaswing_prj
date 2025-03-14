package org.example.GUI.Dialogs;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class ThanhToanDialog extends JFrame {
    public JComboBox<String> sellerComboBox;
    private JLabel totalLabel;
    private JLabel discountLabel;
    private JLabel amountDueLabel;
    private JLabel amountPaidLabel;
    private ButtonGroup paymentMethodGroup;
    private JPanel cashPanel;
    private JPanel transferPanel;
    private static double totalAmount = 0;
    private double discount = 0;
    private DecimalFormat formatter = new DecimalFormat("#,###");

    public ThanhToanDialog(Window windowAncestor, double totalAmount) {
        setTitle("Thanh Toán");
        setSize(400, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // lựa chọn người bán
        JPanel sellerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sellerComboBox = new JComboBox<>(new String[]{"Tây Bán Bom", "Tín Víp Pro", "Thư Bồ Tín", "An Má Bé Sol", "HURRYKHANG", "Jack Bỏ Con"});
        sellerComboBox.setPreferredSize(new Dimension(200, 30));
        sellerPanel.add(sellerComboBox);
        mainPanel.add(sellerPanel);

        // Font chỉnh sửa
        Font labelFont = new Font("Arial", Font.BOLD, 15);

        // Amount information
        JPanel amountPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        JLabel totalTextLabel = new JLabel("Tổng tiền hàng:");
        totalTextLabel.setFont(labelFont);
        totalLabel = new JLabel(formatter.format(this.totalAmount) + " đ", SwingConstants.RIGHT);
        totalLabel.setFont(labelFont);

        JLabel discountTextLabel = new JLabel("Giảm giá:");
        discountTextLabel.setFont(labelFont);
        discountLabel = new JLabel(formatter.format(discount) + " đ", SwingConstants.RIGHT);
        discountLabel.setFont(labelFont);

        JLabel amountDueTextLabel = new JLabel("Khách cần trả:");
        amountDueTextLabel.setFont(labelFont);
        amountDueLabel = new JLabel(formatter.format(this.totalAmount - discount) + " đ", SwingConstants.RIGHT);
        amountDueLabel.setFont(labelFont);

        JLabel amountPaidTextLabel = new JLabel("Khách thanh toán:");
        amountPaidTextLabel.setFont(labelFont);
        amountPaidLabel = new JLabel(formatter.format(this.totalAmount - discount) + " đ", SwingConstants.RIGHT);
        amountPaidLabel.setFont(labelFont);

        amountPanel.add(totalTextLabel);
        amountPanel.add(totalLabel);
        amountPanel.add(discountTextLabel);
        amountPanel.add(discountLabel);
        amountPanel.add(amountDueTextLabel);
        amountPanel.add(amountDueLabel);
        amountPanel.add(amountPaidTextLabel);
        amountPanel.add(amountPaidLabel);


        // Payment method
        JPanel methodPanel = new JPanel(new FlowLayout());
        paymentMethodGroup = new ButtonGroup();
        JRadioButton cashButton = new JRadioButton("Tiền mặt");
        JRadioButton transferButton = new JRadioButton("Chuyển khoản");

        paymentMethodGroup.add(cashButton);
        paymentMethodGroup.add(transferButton);

        methodPanel.add(cashButton);
        methodPanel.add(transferButton);

        // Cash panel
        cashPanel = new JPanel(new FlowLayout());
        cashPanel.setLayout(new GridLayout(0, 3, 10, 10)); // Hiển thị theo cột, tối đa 3 cột một hàng
        Integer[] denominations = {1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000};
        for (Integer denom : denominations) {
            JButton btn = new JButton(formatter.format(denom) + " đ");
            btn.addActionListener(e -> handleCashButtonClick(denom));
            cashPanel.add(btn);
        }
        cashPanel.setVisible(false);

        // Transfer panel
        transferPanel = new JPanel(new BorderLayout());
        JLabel qrLabel = new JLabel(new FlatSVGIcon("Images/QRBank.svg", 300, 300));
        transferPanel.add(qrLabel, BorderLayout.CENTER);
        transferPanel.setVisible(false);

        mainPanel.add(transferPanel);
        add(mainPanel);

        cashButton.addActionListener(e -> {
            cashPanel.setVisible(true);
            transferPanel.setVisible(false);
        });

        transferButton.addActionListener(e -> {
            cashPanel.setVisible(false);
            transferPanel.setVisible(true);
        });

        // Payment button
        JButton paymentButton = new JButton("Thanh Toán");
        paymentButton.setFont(new Font("Arial", Font.BOLD, 12));
        paymentButton.setBackground(new Color(0, 102, 255));
        paymentButton.setForeground(Color.WHITE);
        paymentButton.setPreferredSize(new Dimension(300, 40));

        JPanel paymentButtonPanel = new JPanel(new BorderLayout());
        paymentButtonPanel.add(paymentButton, BorderLayout.SOUTH);

        // Add components to main panel
        mainPanel.add(amountPanel);
        mainPanel.add(methodPanel);
        mainPanel.add(cashPanel);
        mainPanel.add(transferPanel);
        mainPanel.add(paymentButtonPanel);

        add(mainPanel);
    }

    private void handleCashButtonClick(int amount) {
        double currentPaid = Double.parseDouble(amountPaidLabel.getText()
                .replace("Khách thanh toán: ", "")
                .replace(" đ", "")
                .replace(",", ""));
        currentPaid += amount;
        amountPaidLabel.setText(" " + formatter.format(currentPaid) + " đ");
    }

    public void setTotalAmount(double amount) {
        this.totalAmount = amount;
        updateLabels();
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        updateLabels();
    }

    private void updateLabels() {
        totalLabel.setText("Tổng tiền hàng: " + formatter.format(totalAmount) + " đ");
        discountLabel.setText("Giảm giá: " + formatter.format(discount) + " đ");
        amountDueLabel.setText("Khách cần trả: " + formatter.format(totalAmount - discount) + " đ");
        amountPaidLabel.setText("Khách thanh toán: " + formatter.format(totalAmount - discount) + " đ");
    }
}