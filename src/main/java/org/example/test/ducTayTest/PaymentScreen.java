package org.example.test.ducTayTest;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class PaymentScreen extends JDialog {
    private final double totalAmount;
    private final MainPOSInterface parent;
    private final DecimalFormat currencyFormat = new DecimalFormat("#,###,###");
    
    private JTextField amountField;
    private JTextField changeField;
    
    public PaymentScreen(MainPOSInterface parent, double total) {
        super(parent, "Thanh Toán", true);
        this.parent = parent;
        this.totalAmount = total;
        
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top panel with title
        JLabel titleLabel = new JLabel("THANH TOÁN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Center panel with payment info
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        
        // Payment methods
        JPanel methodsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        
        JButton cashButton = new JButton("Tiền Mặt");
        cashButton.setBackground(new Color(52, 152, 219));
        cashButton.setForeground(Color.WHITE);
        cashButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton cardButton = new JButton("Thẻ Tín Dụng");
        cardButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton qrButton = new JButton("QR Code");
        qrButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        methodsPanel.add(cashButton);
        methodsPanel.add(cardButton);
        methodsPanel.add(qrButton);
        
        // Payment information
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel totalLabel = new JLabel("Tổng cộng:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel totalValueLabel = new JLabel(currencyFormat.format(totalAmount) + " VNĐ");
        totalValueLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalValueLabel.setHorizontalAlignment(JLabel.RIGHT);
        
        JLabel amountLabel = new JLabel("Tiền khách đưa:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        amountField = new JTextField();
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));
        amountField.setHorizontalAlignment(JTextField.RIGHT);
        
        JLabel changeLabel = new JLabel("Tiền thối lại:");
        changeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        changeField = new JTextField();
        changeField.setEditable(false);
        changeField.setFont(new Font("Arial", Font.PLAIN, 16));
        changeField.setHorizontalAlignment(JTextField.RIGHT);
        
        infoPanel.add(totalLabel);
        infoPanel.add(totalValueLabel);
        infoPanel.add(amountLabel);
        infoPanel.add(amountField);
        infoPanel.add(changeLabel);
        infoPanel.add(changeField);
        
        centerPanel.add(methodsPanel, BorderLayout.NORTH);
        centerPanel.add(infoPanel, BorderLayout.CENTER);
        
        // Bottom panel with action buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        
        JButton cancelButton = new JButton("Hủy");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(100, 40));
        
        JButton confirmButton = new JButton("Xác Nhận");
        confirmButton.setBackground(new Color(46, 204, 113));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setPreferredSize(new Dimension(120, 40));
        
        bottomPanel.add(cancelButton);
        bottomPanel.add(confirmButton);
        
        // Add action listeners
        amountField.addActionListener(e -> calculateChange());
        
        cashButton.addActionListener(e -> {
            // Cash payment selected
            amountField.setEnabled(true);
            amountField.requestFocus();
        });
        
        cardButton.addActionListener(e -> {
            // Card payment selected
            amountField.setText(currencyFormat.format(totalAmount));
            amountField.setEnabled(false);
            changeField.setText("0");
        });
        
        qrButton.addActionListener(e -> {
            // QR payment selected
            amountField.setText(currencyFormat.format(totalAmount));
            amountField.setEnabled(false);
            changeField.setText("0");
        });
        
        cancelButton.addActionListener(e -> dispose());
        
        confirmButton.addActionListener(e -> {
            if (amountField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền thanh toán");
                return;
            }
            
            if (amountField.isEnabled()) {
                try {
                    // For cash payments, check if sufficient amount
                    double amount = Double.parseDouble(amountField.getText().replace(",", ""));
                    if (amount < totalAmount) {
                        JOptionPane.showMessageDialog(this, "Số tiền khách đưa không đủ");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ");
                    return;
                }
            }
            
            // Process the payment and show receipt
            JOptionPane.showMessageDialog(this, 
                    "Thanh toán thành công!\nIn hóa đơn...", 
                    "Thành Công", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear the cart and close
            dispose();
            // Reset the POS interface
            // This would be implemented in a real application
        });
        
        // Add panels to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void calculateChange() {
        try {
            // Remove commas and convert to double
            double amount = Double.parseDouble(amountField.getText().replace(",", ""));
            double change = amount - totalAmount;
            
            if (change >= 0) {
                changeField.setText(currencyFormat.format(change));
            } else {
                changeField.setText("Thiếu " + currencyFormat.format(Math.abs(change)));
            }
        } catch (NumberFormatException e) {
            changeField.setText("Lỗi số tiền");
        }
    }
}