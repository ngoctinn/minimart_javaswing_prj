package org.example.GUI.Panels.nhanVienPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.DTO.chamCongDTO;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import static org.example.Components.CustomToastMessage.showErrorToast;
import static org.example.Components.CustomToastMessage.showSuccessToast;

public class ChamCongPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel leftPanel;
    private RoundedPanel rightPanel;

    // Top panel components
    private PlaceholderTextField searchField;
    private CustomButton searchButton;
    private CustomButton refreshButton;
    private JComboBox<String> monthSelector;
    private JComboBox<String> yearSelector;

    // Left panel components
    private CustomTable nhanVienTable;
    private CustomButton chamCongButton;
    private CustomButton xuatBaoCaoButton;

    // Right panel components
    private JPanel calendarPanel;
    private JPanel infoPanel;
    private JLabel nhanVienInfoLabel;
    private JLabel tongGioLamLabel;
    private JLabel tongNgayLamLabel;
    private JLabel luongThuongLabel;
    private JLabel luongKhauTruLabel;
    private JLabel luongThucNhanLabel;
    
    // Data
    private String selectedMaNV;
    private int selectedMonth;
    private int selectedYear;
    private LocalDate currentDate = LocalDate.now();

    public ChamCongPanel() {
        // Initialize default month and year
        selectedMonth = currentDate.getMonthValue();
        selectedYear = currentDate.getYear();
        
        initGUI();
    }

    private void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupLeftPanel();
        setupRightPanel();
        setupEventHandlers();

        // Add panels to main panel with proper constraints
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.CENTER);
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
        leftPanel = new RoundedPanel(20);
        rightPanel = new RoundedPanel(20);

        // Set background colors
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(new Color(225, 225, 225));
        leftPanel.setBackground(Color.WHITE);
        rightPanel.setBackground(Color.WHITE);

        // Set panel sizes
        leftPanel.setPreferredSize(new Dimension(400, 0)); // Fixed width for left panel

        // Set layouts
        topPanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new BorderLayout(5, 0));
        leftPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BorderLayout());
    }

    private void setupTopPanel() {
        // Add padding to the top panel
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create sub-panels for organizing components
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setBackground(Color.WHITE);
        
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(Color.WHITE);
        
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
        datePanel.setBackground(Color.WHITE);
        
        // Title label
        JLabel title = new JLabel("Chấm công nhân viên");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));
        
        // Search field
        searchField = new PlaceholderTextField("Nhập mã hoặc tên nhân viên cần tìm");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        
        // Search button
        searchButton = new CustomButton("Tìm");
        searchButton.setPreferredSize(new Dimension(70, 30));
        searchButton.setMaximumSize(new Dimension(70, 30));
        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(5));

        // Refresh button
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setPreferredSize(new Dimension(50, 30));
        refreshButton.setMaximumSize(new Dimension(50, 30));
        refreshButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        searchPanel.add(refreshButton);
        searchPanel.add(Box.createHorizontalStrut(10));
        
        // Month and Year selectors
        JLabel monthLabel = new JLabel("Tháng:");
        monthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        datePanel.add(monthLabel);
        datePanel.add(Box.createHorizontalStrut(5));
        
        // Month selector
        String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        monthSelector = new JComboBox<>(months);
        monthSelector.setSelectedIndex(currentDate.getMonthValue() - 1); // Set current month
        monthSelector.setPreferredSize(new Dimension(70, 30));
        monthSelector.setMaximumSize(new Dimension(70, 30));
        datePanel.add(monthSelector);
        datePanel.add(Box.createHorizontalStrut(10));
        
        JLabel yearLabel = new JLabel("Năm:");
        yearLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        datePanel.add(yearLabel);
        datePanel.add(Box.createHorizontalStrut(5));
        
        // Year selector
        String[] years = new String[10];
        int currentYear = currentDate.getYear();
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(currentYear - 5 + i);
        }
        yearSelector = new JComboBox<>(years);
        yearSelector.setSelectedItem(String.valueOf(currentYear)); // Set current year
        yearSelector.setPreferredSize(new Dimension(90, 30));
        yearSelector.setMaximumSize(new Dimension(90, 30));
        datePanel.add(yearSelector);
        
        // Add components to the top panel
        topPanel.add(titlePanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(datePanel, BorderLayout.EAST);
    }

    private void setupLeftPanel() {
        // Set padding for left panel
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Danh sách nhân viên");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titlePanel.add(titleLabel, BorderLayout.WEST);
        
        // Create table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        
        // Create table with employee data
        String[] columnNames = {"Mã NV", "Tên nhân viên", "Chức vụ"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        
        // TODO: Replace with actual data from database
        // Mock data for now
        Object[][] sampleData = {
            {"NV001", "Nguyễn Văn A", "Nhân viên bán hàng"},
            {"NV002", "Trần Thị B", "Thu ngân"},
            {"NV003", "Lê Văn C", "Quản lý"},
            {"NV004", "Phạm Thị D", "Kho"}
        };
        
        for (Object[] row : sampleData) {
            model.addRow(row);
        }
        
        nhanVienTable = new CustomTable(model);
        JScrollPane scrollPane = new JScrollPane(nhanVienTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        // Chấm công button
        FlatSVGIcon chamCongIcon = new FlatSVGIcon("Icons/chamcong.svg", 16, 16);
        chamCongButton = new CustomButton("Chấm công", chamCongIcon);
        chamCongButton.setPreferredSize(new Dimension(150, 40));
        chamCongButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        buttonPanel.add(chamCongButton);
        
        // Xuất báo cáo button
        FlatSVGIcon xuatBaoCaoIcon = new FlatSVGIcon("Icons/excel.svg", 16, 16);
        xuatBaoCaoButton = new CustomButton("Xuất báo cáo", xuatBaoCaoIcon);
        xuatBaoCaoButton.setPreferredSize(new Dimension(150, 40));
        xuatBaoCaoButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        buttonPanel.add(xuatBaoCaoButton);
        
        // Add components to left panel
        leftPanel.add(titlePanel, BorderLayout.NORTH);
        leftPanel.add(tablePanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupRightPanel() {
        // Set padding for right panel
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setLayout(new BorderLayout(0, 10));
        
        // Create info panel for selected employee
        setupInfoPanel();
        
        // Create calendar panel for attendance
        setupCalendarPanel();
        
        // Add panels to right panel
        rightPanel.add(infoPanel, BorderLayout.NORTH);
        rightPanel.add(calendarPanel, BorderLayout.CENTER);
    }
    
    private void setupInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Thông tin chấm công",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));
        infoPanel.setLayout(new GridLayout(3, 2, 10, 10));
        infoPanel.setPreferredSize(new Dimension(0, 120));
        
        // Employee info label
        nhanVienInfoLabel = new JLabel("Nhân viên: Chưa chọn");
        nhanVienInfoLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        // Working hours label
        tongGioLamLabel = new JLabel("Tổng giờ làm: 0");
        tongGioLamLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        // Working days label
        tongNgayLamLabel = new JLabel("Số ngày làm: 0/30");
        tongNgayLamLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        // Bonus label
        luongThuongLabel = new JLabel("Lương thưởng: 0 VNĐ");
        luongThuongLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        // Deduction label
        luongKhauTruLabel = new JLabel("Khấu trừ: 0 VNĐ");
        luongKhauTruLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        
        // Net salary label
        luongThucNhanLabel = new JLabel("Thực nhận: 0 VNĐ");
        luongThucNhanLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        // Add components to info panel
        infoPanel.add(nhanVienInfoLabel);
        infoPanel.add(tongNgayLamLabel);
        infoPanel.add(tongGioLamLabel);
        infoPanel.add(luongThuongLabel);
        infoPanel.add(luongKhauTruLabel);
        infoPanel.add(luongThucNhanLabel);
    }
    
    private void setupCalendarPanel() {
        calendarPanel = new JPanel();
        calendarPanel.setBackground(Color.WHITE);
        calendarPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Lịch chấm công tháng " + selectedMonth + "/" + selectedYear,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                new Color(50, 100, 150)
        ));
        
        // Generate calendar for current month and year
        updateCalendarPanel(selectedMonth, selectedYear);
    }
    
    private void updateCalendarPanel(int month, int year) {
        // Clear existing content
        calendarPanel.removeAll();
        
        // Update panel title
        ((TitledBorder) calendarPanel.getBorder()).setTitle("Lịch chấm công tháng " + month + "/" + year);
        
        // Calendar grid layout (7 days a week + header row)
        calendarPanel.setLayout(new BorderLayout());
        
        // Create header panel with day names
        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        headerPanel.setBackground(new Color(240, 245, 250));
        
        String[] dayNames = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
        Color[] headerColors = {
            new Color(255, 200, 200), // Sunday - light red
            new Color(220, 240, 255), // Monday - light blue
            new Color(220, 240, 255), // Tuesday
            new Color(220, 240, 255), // Wednesday
            new Color(220, 240, 255), // Thursday
            new Color(220, 240, 255), // Friday
            new Color(255, 230, 210)  // Saturday - light orange
        };
        
        for (int i = 0; i < dayNames.length; i++) {
            JLabel dayLabel = new JLabel(dayNames[i], SwingConstants.CENTER);
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            dayLabel.setOpaque(true);
            dayLabel.setBackground(headerColors[i]);
            dayLabel.setForeground(i == 0 || i == 6 ? new Color(180, 0, 0) : new Color(0, 80, 120));
            dayLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 0, 5, 0)
            ));
            headerPanel.add(dayLabel);
        }
        
        // Create days panel
        JPanel daysPanel = new JPanel();
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        int firstDayOfMonth = LocalDate.of(year, month, 1).getDayOfWeek().getValue() % 7; // 0 for Sunday, 6 for Saturday
        
        // Calculate rows needed (max 6 rows)
        int rows = (int) Math.ceil((daysInMonth + firstDayOfMonth) / 7.0);
        daysPanel.setLayout(new GridLayout(rows, 7));
        
        // Add empty cells for days before first day of month
        for (int i = 0; i < firstDayOfMonth; i++) {
            JPanel emptyCell = new JPanel();
            emptyCell.setBackground(new Color(245, 245, 245));
            emptyCell.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
            daysPanel.add(emptyCell);
        }
        
        // Add cells for each day of the month
        for (int day = 1; day <= daysInMonth; day++) {
            JPanel dayCell = createDayCell(day, month, year);
            daysPanel.add(dayCell);
        }
        
        // Add empty cells for days after last day of month
        int remainingCells = rows * 7 - (daysInMonth + firstDayOfMonth);
        for (int i = 0; i < remainingCells; i++) {
            JPanel emptyCell = new JPanel();
            emptyCell.setBackground(new Color(245, 245, 245));
            emptyCell.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
            daysPanel.add(emptyCell);
        }
        
        // Add components to calendar panel
        calendarPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Wrap days panel in a scroll pane for better viewing
        JScrollPane scrollPane = new JScrollPane(daysPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        calendarPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh panel
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }
    
    private JPanel createDayCell(int day, int month, int year) {
        JPanel dayCell = new JPanel(new BorderLayout());
        
        // Check if this day is a weekend
        LocalDate date = LocalDate.of(year, month, day);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        boolean isWeekend = (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
        boolean isToday = date.equals(LocalDate.now());
        
        // Set background color based on day type
        Color bgColor;
        if (isToday) {
            bgColor = new Color(230, 240, 255); // Light blue for today
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            bgColor = new Color(255, 240, 240); // Light red for Sunday
        } else if (dayOfWeek == DayOfWeek.SATURDAY) {
            bgColor = new Color(255, 245, 230); // Light orange for Saturday
        } else {
            bgColor = new Color(250, 250, 255); // Very light blue for weekdays
        }
        dayCell.setBackground(bgColor);
        
        // Day number panel (top of cell)
        JPanel dayNumberPanel = new JPanel(new BorderLayout());
        dayNumberPanel.setBackground(isToday ? new Color(100, 150, 220) : bgColor);
        
        // Day number label
        JLabel dayLabel = new JLabel(String.valueOf(day));
        dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dayLabel.setFont(new Font("Segoe UI", isToday ? Font.BOLD : Font.PLAIN, 14));
        dayLabel.setForeground(isToday ? Color.WHITE : 
                              (isWeekend ? new Color(180, 0, 0) : new Color(50, 50, 50)));
        dayLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        dayNumberPanel.add(dayLabel, BorderLayout.CENTER);
        
        // Attendance status panel (center of cell)
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(bgColor);
        
        // TODO: Check from database if employee has attendance for this day
        // For now, use a random value
        boolean hasAttendance = Math.random() > 0.5;
        
        if (hasAttendance) {
            // Create a more visually appealing attendance indicator
            JPanel checkPanel = new JPanel();
            checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));
            checkPanel.setBackground(bgColor);
            
            // Checkmark icon
            JLabel checkLabel = new JLabel("Đã chấm công");
            checkLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            checkLabel.setForeground(new Color(0, 150, 0));
            checkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Hours worked label (example)
            JLabel hoursLabel = new JLabel("8h");
            hoursLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            hoursLabel.setForeground(new Color(80, 80, 80));
            hoursLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            checkPanel.add(Box.createVerticalGlue());
            checkPanel.add(checkLabel);
            checkPanel.add(hoursLabel);
            checkPanel.add(Box.createVerticalGlue());
            
            statusPanel.add(checkPanel, BorderLayout.CENTER);
        }
        
        // Add components to day cell
        dayCell.add(dayNumberPanel, BorderLayout.NORTH);
        dayCell.add(statusPanel, BorderLayout.CENTER);
        
        // Add border to the cell
        dayCell.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        
        // Add hover effect and click listener if this is not a weekend
        if (!isWeekend) {
            dayCell.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (selectedMaNV != null) {
                        dayCell.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    dayCell.setBackground(bgColor);
                    statusPanel.setBackground(bgColor);
                    dayCell.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (selectedMaNV != null) {
                        toggleAttendance(selectedMaNV, day, month, year);
                        // Update cell appearance
                        statusPanel.removeAll();
                        boolean hasAttendance = Math.random() > 0.5; // TODO: Get real attendance status
                        
                        if (hasAttendance) {
                            JPanel checkPanel = new JPanel();
                            checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));
                            checkPanel.setBackground(dayCell.getBackground());
                            
                            JLabel checkLabel = new JLabel("đã chấm công");
                            checkLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
                            checkLabel.setForeground(new Color(0, 150, 0));
                            checkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                            
                            JLabel hoursLabel = new JLabel("8h");
                            hoursLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                            hoursLabel.setForeground(new Color(80, 80, 80));
                            hoursLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                            
                            checkPanel.add(Box.createVerticalGlue());
                            checkPanel.add(checkLabel);
                            checkPanel.add(hoursLabel);
                            checkPanel.add(Box.createVerticalGlue());
                            
                            statusPanel.add(checkPanel, BorderLayout.CENTER);
                        }
                        
                        statusPanel.revalidate();
                        statusPanel.repaint();
                    } else {
                        showErrorToast(ChamCongPanel.this, "Vui lòng chọn nhân viên trước");
                    }
                }
            });
        } else {
            // Add a subtle indicator for weekend days
            JLabel weekendLabel = new JLabel(dayOfWeek == DayOfWeek.SUNDAY ? "CN" : "T7");
            weekendLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
            weekendLabel.setForeground(new Color(150, 150, 150));
            weekendLabel.setHorizontalAlignment(SwingConstants.CENTER);
            statusPanel.add(weekendLabel, BorderLayout.SOUTH);
        }
        
        return dayCell;
    }

    private void toggleAttendance(String maNV, int day, int month, int year) {
        // TODO: Implement toggle attendance logic
        // This would connect to your BUS layer to handle the data
        showSuccessToast(this, "Đã chấm công cho nhân viên " + maNV + " ngày " + day + "/" + month + "/" + year);
    }

    private void setupEventHandlers() {
        // Table selection listener
        nhanVienTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && nhanVienTable.getSelectedRow() != -1) {
                selectedMaNV = (String) nhanVienTable.getValueAt(nhanVienTable.getSelectedRow(), 0);
                String tenNV = (String) nhanVienTable.getValueAt(nhanVienTable.getSelectedRow(), 1);
                updateSelectedEmployee(selectedMaNV, tenNV);
            }
        });
        
        // Month selector listener
        monthSelector.addActionListener(e -> {
            selectedMonth = Integer.parseInt((String) monthSelector.getSelectedItem());
            updateCalendarPanel(selectedMonth, selectedYear);
            updateAttendanceInfo(selectedMaNV, selectedMonth, selectedYear);
        });
        
        // Year selector listener
        yearSelector.addActionListener(e -> {
            selectedYear = Integer.parseInt((String) yearSelector.getSelectedItem());
            updateCalendarPanel(selectedMonth, selectedYear);
            updateAttendanceInfo(selectedMaNV, selectedMonth, selectedYear);
        });
        
        // Search button listener
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                // TODO: Implement search logic
                showSuccessToast(this, "Đang tìm kiếm: " + searchText);
            }
        });
        
        // Refresh button listener
        refreshButton.addActionListener(e -> {
            searchField.setText("");
            refreshEmployeeTable();
        });
        
        // Chấm công button listener
        chamCongButton.addActionListener(e -> {
            if (selectedMaNV != null) {
                // TODO: Implement manual attendance input dialog
                showSuccessToast(this, "Đã chấm công cho nhân viên " + selectedMaNV);
            } else {
                showErrorToast(this, "Vui lòng chọn nhân viên trước");
            }
        });
        
        // Xuất báo cáo button listener
        xuatBaoCaoButton.addActionListener(e -> {
            // TODO: Implement export report logic
            showSuccessToast(this, "Đang xuất báo cáo chấm công tháng " + selectedMonth + "/" + selectedYear);
        });
    }

    private void updateSelectedEmployee(String maNV, String tenNV) {
        nhanVienInfoLabel.setText("Nhân viên: " + tenNV + " (" + maNV + ")");
        updateAttendanceInfo(maNV, selectedMonth, selectedYear);
    }
    
    private void updateAttendanceInfo(String maNV, int month, int year) {
        if (maNV == null) return;
        
        // TODO: Get actual data from database
        // For now, use mock data
        int soGioLam = (int) (Math.random() * 200);
        int soNgayLam = (int) (Math.random() * 30);
        float luongThuong = (float) (Math.random() * 1000000);
        float khauTru = (float) (Math.random() * 500000);
        float luongThucNhan = luongThuong - khauTru;
        
        // Update labels
        tongGioLamLabel.setText("Tổng giờ làm: " + soGioLam);
        tongNgayLamLabel.setText("Số ngày làm: " + soNgayLam + "/30");
        luongThuongLabel.setText("Lương thưởng: " + String.format("%,.0f", luongThuong) + " VNĐ");
        luongKhauTruLabel.setText("Khấu trừ: " + String.format("%,.0f", khauTru) + " VNĐ");
        luongThucNhanLabel.setText("Thực nhận: " + String.format("%,.0f", luongThucNhan) + " VNĐ");
        
        // Update calendar
        updateCalendarPanel(month, year);
    }
    
    private void refreshEmployeeTable() {
        // TODO: Implement refresh logic to update the table with the latest data
        showSuccessToast(this, "Đã làm mới danh sách nhân viên");
    }

    // Main method for testing
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame("Chấm Công Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ChamCongPanel());
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
