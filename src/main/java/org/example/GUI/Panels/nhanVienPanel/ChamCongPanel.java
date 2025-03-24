package org.example.GUI.Panels.nhanVienPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChamCongPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    
    // Top panel components
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JLabel timeLabel;
    private PlaceholderTextField employeeIdField;
    private JTextArea noteArea;
    private CustomButton checkInButton;
    private CustomButton checkOutButton;
    private Timer clockTimer;
    
    // Bottom panel components
    private CustomTable attendanceTable;
    private JScrollPane scrollPane;
    
    // Colors
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color BACKGROUND_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = new Color(44, 62, 80);
    
    public ChamCongPanel() {
        initGUI();
        startClock();
    }

    public void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanel();

        // Add panels to main panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
    }
    
    private void setupMainPanel() {
        // Set up layout and background for main panel
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(new Color(225, 225, 225));
        this.setBorder(new EmptyBorder(15, 15, 15, 15));
        this.setVisible(true);
    }
    
    private void createPanels() {
        // Create sub-panels using RoundedPanel
        topPanel = new RoundedPanel(20);
        bottomPanel = new RoundedPanel(20);
        
        // Set background colors
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(Color.WHITE);
        
        // Set layouts
        topPanel.setLayout(new BorderLayout(10, 10));
        bottomPanel.setLayout(new BorderLayout(10, 10));
        
        // Add padding
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }
    
    private void setupTopPanel() {
        // Create sub-panels for organizing components
        JPanel titleDatePanel = new JPanel(new BorderLayout(10, 0));
        titleDatePanel.setBackground(Color.WHITE);
        
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        // Title section
        titleLabel = new JLabel("QUẢN LÝ CHẤM CÔNG");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        
        // Date and time section
        JPanel dateTimePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        dateTimePanel.setBackground(Color.WHITE);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(new Date());
        dateLabel = new JLabel("Ngày: " + currentDate);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        timeLabel = new JLabel("Giờ: --:--:--");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        dateTimePanel.add(dateLabel);
        dateTimePanel.add(Box.createHorizontalStrut(10));
        dateTimePanel.add(timeLabel);
        
        titleDatePanel.add(titleLabel, BorderLayout.WEST);
        titleDatePanel.add(dateTimePanel, BorderLayout.EAST);
        
        // Input section using GridBagLayout for better control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Employee ID label
        JLabel employeeIdLabel = new JLabel("Mã nhân viên:");
        employeeIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        inputPanel.add(employeeIdLabel, gbc);
        
        // Employee ID field
        employeeIdField = new PlaceholderTextField("Nhập mã nhân viên");
        employeeIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.85;
        inputPanel.add(employeeIdField, gbc);
        
        // Note label
        JLabel noteLabel = new JLabel("Ghi chú:");
        noteLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.15;
        inputPanel.add(noteLabel, gbc);
        
        // Note area (using JTextArea instead of PlaceholderTextField)
        noteArea = new JTextArea(3, 20);
        noteArea.setFont(new Font("Arial", Font.PLAIN, 14));
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.85;
        inputPanel.add(noteScrollPane, gbc);
        
        // Button panel (right-aligned)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        // Check In button
        FlatSVGIcon checkinIcon = new FlatSVGIcon("Icons/check-in.svg", 16, 16);
        checkInButton = new CustomButton("Check In", checkinIcon);
        checkInButton.setPreferredSize(new Dimension(130, 40));
        checkInButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        
        // Check Out button
        FlatSVGIcon checkoutIcon = new FlatSVGIcon("Icons/check-out.svg", 16, 16);
        checkOutButton = new CustomButton("Check Out", checkoutIcon);
        checkOutButton.setPreferredSize(new Dimension(130, 40));
        checkOutButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        
        buttonPanel.add(checkInButton);
        buttonPanel.add(checkOutButton);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(buttonPanel, gbc);
        
        // Add all components to top panel
        topPanel.add(titleDatePanel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.CENTER);
    }
    
    private void setupBottomPanel() {
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                bottomPanel.getBorder(),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR),
                        "Lịch sử chấm công",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        PRIMARY_COLOR)
        ));
        
        // Create table model
        String[] columns = {"STT", "Mã NV", "Tên nhân viên", "Ngày", "Giờ vào", "Giờ ra", "Tổng giờ", "Ghi chú"};
        Object[][] data = {
                {"1", "NV001", "Nguyễn Văn A", "01/07/2023", "08:00", "17:00", "8.0", ""},
                {"2", "NV002", "Trần Thị B", "01/07/2023", "08:30", "17:30", "8.0", ""}
        };
        
        // Create custom table
        attendanceTable = new CustomTable(data, columns);
        scrollPane = new JScrollPane(attendanceTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Add table to bottom panel
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    // Start the real-time clock
    private void startClock() {
        clockTimer = new Timer(1000, e -> updateClock());
        clockTimer.start();
    }
    
    // Update the clock display
    private void updateClock() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = timeFormat.format(new Date());
        timeLabel.setText("Giờ: " + currentTime);
    }
    
    // Clean up resources when panel is removed
    public void cleanup() {
        if (clockTimer != null && clockTimer.isRunning()) {
            clockTimer.stop();
        }
    }
    
    // Utility method for creating titled panels
    private JPanel createTitledPanel(String title, int width, int height) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                title,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }
}
