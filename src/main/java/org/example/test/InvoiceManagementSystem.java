package org.example.test;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.BorderLayout;

public class InvoiceManagementSystem extends JFrame {
    private JTable table;
    private JPanel leftPanel, mainPanel;
    private JTextField searchField;

    public InvoiceManagementSystem() {
        setTitle("Hóa đơn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create main components
        createLeftPanel();
        createMainPanel();

        // Add components to frame
        add(leftPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        setSize(1200, 700);
        setLocationRelativeTo(null);
    }

    private void createLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(200, 0));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(Color.WHITE);

        // Time section
        JPanel timePanel = createSection("Thời gian");
        JRadioButton thisMonthRadio = new JRadioButton("Tháng này");
        JRadioButton otherRadio = new JRadioButton("Lựa chọn khác");
        ButtonGroup timeGroup = new ButtonGroup();
        timeGroup.add(thisMonthRadio);
        timeGroup.add(otherRadio);
        timePanel.add(thisMonthRadio);
        timePanel.add(otherRadio);

        // Status section
        JPanel statusPanel = createSection("Trạng thái");
        JCheckBox processing = new JCheckBox("Đang xử lý");
        JCheckBox completed = new JCheckBox("Hoàn thành");
        JCheckBox undelivered = new JCheckBox("Không giao được");
        JCheckBox cancelled = new JCheckBox("Đã hủy");
        statusPanel.add(processing);
        statusPanel.add(completed);
        statusPanel.add(undelivered);
        statusPanel.add(cancelled);

        // Creator section
        JPanel creatorPanel = createSection("Người tạo");
        creatorPanel.add(new JLabel("Chọn người tạo"));

        // Seller section
        JPanel sellerPanel = createSection("Người bán");
        sellerPanel.add(new JLabel("Chọn người bán"));

        // Add all sections to left panel
        leftPanel.add(timePanel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(statusPanel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(creatorPanel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(sellerPanel);
    }

    private JPanel createSection(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        // Create top toolbar
        JPanel toolbar = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Thêm mới");
        JButton mergeButton = new JButton("Gộp đơn");
        JButton fileButton = new JButton("File");
        
        addButton.setBackground(new Color(40, 167, 69));
        addButton.setForeground(Color.WHITE);
        
        buttonPanel.add(addButton);
        buttonPanel.add(mergeButton);
        buttonPanel.add(fileButton);

        toolbar.add(searchField, BorderLayout.WEST);
        toolbar.add(buttonPanel, BorderLayout.EAST);

        // Create table
        String[] columns = {
            "", "⭐", "Mã hóa đơn", "Thời gian", "Mã tra hàng", 
            "Khách hàng", "Tổng tiền hàng", "Giảm giá", "Khách đã trả"
        };
        
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add sample data
        addSampleData(model);

        // Customize table
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setRowHeight(30);

        mainPanel.add(toolbar, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void addSampleData(DefaultTableModel model) {
        Object[][] data = {
            {false, "☆", "HD000046", "17/02/2025 23:36", "", "Tuấn - Hà Nội", "55,000", "0", "55,000"},
            {false, "☆", "HD000045", "16/02/2025 23:35", "", "Phạm Thu Hương", "190,000", "0", "190,000"},
            // Add more sample data as needed
        };

        for (Object[] row : data) {
            model.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new InvoiceManagementSystem().setVisible(true);
        });
    }
}