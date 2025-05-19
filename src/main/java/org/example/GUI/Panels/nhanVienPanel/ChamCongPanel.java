package org.example.GUI.Panels.nhanVienPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.BUS.NhanVienBUS;
import org.example.DTO.nhanVienDTO;
import org.example.BUS.ChamCongBUS;
import org.example.DTO.chamCongDTO;
import org.example.BUS.LuongBUS;
import org.example.DTO.LuongDTO;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;

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

    // Data
    private String selectedMaNV;
    private int selectedMonth;
    private int selectedYear;
    private LocalDate currentDate = LocalDate.now();

    public ChamCongPanel() {
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

        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.CENTER);
    }

    private void setupMainPanel() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(new Color(225, 225, 225));
    }

    private void createPanels() {
        topPanel = new RoundedPanel(20);
        bottomPanel = new RoundedPanel(20);
        leftPanel = new RoundedPanel(20);
        rightPanel = new RoundedPanel(20);

        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(new Color(225, 225, 225));
        leftPanel.setBackground(Color.WHITE);
        rightPanel.setBackground(Color.WHITE);

        leftPanel.setPreferredSize(new Dimension(400, 0));
        topPanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new BorderLayout(5, 0));
        leftPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BorderLayout());
    }

    private void setupTopPanel() {
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setBackground(Color.WHITE);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(Color.WHITE);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
        datePanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Chấm công nhân viên");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(40));

        searchField = new PlaceholderTextField("Nhập mã hoặc tên nhân viên cần tìm");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));

        searchButton = new CustomButton("Tìm");
        searchButton.setPreferredSize(new Dimension(70, 30));
        searchButton.setMaximumSize(new Dimension(70, 30));
        searchButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(5));

        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setPreferredSize(new Dimension(50, 30));
        refreshButton.setMaximumSize(new Dimension(50, 30));
        refreshButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        searchPanel.add(refreshButton);
        searchPanel.add(Box.createHorizontalStrut(10));

        JLabel monthLabel = new JLabel("Tháng:");
        monthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        datePanel.add(monthLabel);
        datePanel.add(Box.createHorizontalStrut(5));

        String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        monthSelector = new JComboBox<>(months);
        monthSelector.setSelectedIndex(currentDate.getMonthValue() - 1);
        monthSelector.setPreferredSize(new Dimension(70, 30));
        monthSelector.setMaximumSize(new Dimension(70, 30));
        monthSelector.addActionListener(e -> {
            selectedMonth = Integer.parseInt((String) monthSelector.getSelectedItem());
            updateCalendarPanel(selectedMonth, selectedYear);
        });
        datePanel.add(monthSelector);
        datePanel.add(Box.createHorizontalStrut(10));

        JLabel yearLabel = new JLabel("Năm:");
        yearLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        datePanel.add(yearLabel);
        datePanel.add(Box.createHorizontalStrut(5));

        String[] years = new String[10];
        int currentYear = currentDate.getYear();
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(currentYear - 5 + i);
        }
        yearSelector = new JComboBox<>(years);
        yearSelector.setSelectedItem(String.valueOf(currentYear));
        yearSelector.setPreferredSize(new Dimension(90, 30));
        yearSelector.setMaximumSize(new Dimension(90, 30));
        yearSelector.addActionListener(e -> {
            selectedYear = Integer.parseInt((String) yearSelector.getSelectedItem());
            updateCalendarPanel(selectedMonth, selectedYear);
        });
        datePanel.add(yearSelector);

        topPanel.add(titlePanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(datePanel, BorderLayout.EAST);
    }

    private void setupLeftPanel() {
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Danh sách nhân viên");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        String[] columnNames = {"Mã NV", "Tên nhân viên", "Chức vụ"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        loadEmployeeData(model);

        nhanVienTable = new CustomTable(model);
        nhanVienTable.setRowHeight(30);
        nhanVienTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nhanVienTable.setSelectionBackground(new Color(230, 240, 255));
        nhanVienTable.setSelectionForeground(Color.BLACK);

        // Add selection listener
        nhanVienTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = nhanVienTable.getSelectedRow();
                if (selectedRow != -1) {
                    selectedMaNV = (String) nhanVienTable.getValueAt(selectedRow, 0);
                    String tenNV = (String) nhanVienTable.getValueAt(selectedRow, 1);
                    String chucVu = (String) nhanVienTable.getValueAt(selectedRow, 2);
                    nhanVienInfoLabel.setText(String.format("Nhân viên: %s - %s (%s)", selectedMaNV, tenNV, chucVu));
                    // Update the calendar panel when a new employee is selected
                    updateCalendarPanel(selectedMonth, selectedYear);
                } else {
                    // Clear selection if no row is selected
                    selectedMaNV = null;
                    nhanVienInfoLabel.setText("Chưa chọn nhân viên");
                    updateCalendarPanel(selectedMonth, selectedYear); // Update calendar to show no attendance
                }
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < nhanVienTable.getColumnCount(); i++) {
            nhanVienTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(nhanVienTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        FlatSVGIcon chamCongIcon = new FlatSVGIcon("Icons/chamcong.svg", 16, 16);
        chamCongButton = new CustomButton("Chấm công", chamCongIcon);
        chamCongButton.setPreferredSize(new Dimension(150, 40));
        chamCongButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        chamCongButton.addActionListener(e -> {
            if (selectedMaNV == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần chấm công!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Create new attendance record
            chamCongDTO chamCong = new chamCongDTO(selectedMaNV, LocalDate.now());
            
            // Check if already checked in today
            if (ChamCongBUS.daChamCong(selectedMaNV, LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Nhân viên đã được chấm công hôm nay!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Add attendance record
            int result = ChamCongBUS.themChamCong(chamCong);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Chấm công thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                // Refresh calendar to show new attendance
                updateCalendarPanel(selectedMonth, selectedYear);
            } else {
                JOptionPane.showMessageDialog(this, "Chấm công thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(chamCongButton);

        FlatSVGIcon xuatBaoCaoIcon = new FlatSVGIcon("Icons/excel.svg", 16, 16);
        xuatBaoCaoButton = new CustomButton("Xuất bảng lương", xuatBaoCaoIcon);
        xuatBaoCaoButton.setPreferredSize(new Dimension(150, 40));
        xuatBaoCaoButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        xuatBaoCaoButton.addActionListener(e -> {
            if (selectedMaNV == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần tính lương!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy tháng và năm từ combobox
            int thang = Integer.parseInt(monthSelector.getSelectedItem().toString());
            int nam = Integer.parseInt(yearSelector.getSelectedItem().toString());

            // Tính lương cho nhân viên
            LuongBUS luongBUS = new LuongBUS();
            LuongDTO luong = luongBUS.tinhLuong(selectedMaNV, thang, nam);

            if (luong == null) {
                JOptionPane.showMessageDialog(this, "Không thể tính lương cho nhân viên này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Hiển thị dialog thông tin lương
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thông tin lương", true);
            dialog.setLayout(new BorderLayout(10, 10));
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this);

            // Panel chứa thông tin
            JPanel infoPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            infoPanel.setBackground(Color.WHITE);

            // Thêm thông tin vào panel
            infoPanel.add(new JLabel("Mã nhân viên:"));
            infoPanel.add(new JLabel(luong.getMaNV()));
            infoPanel.add(new JLabel("Tổng ngày công:"));
            infoPanel.add(new JLabel(String.valueOf(luong.getTongNgayCong())));
            infoPanel.add(new JLabel("Lương cơ bản:"));
            infoPanel.add(new JLabel(String.format("%,.0f VNĐ", luong.getLuongCoBan())));
            infoPanel.add(new JLabel("Lương thưởng:"));
            infoPanel.add(new JLabel(String.format("%,.0f VNĐ", luong.getLuongThuong())));
            infoPanel.add(new JLabel("Lương thực nhận:"));
            infoPanel.add(new JLabel(String.format("%,.0f VNĐ", luong.getLuongThucNhan())));

            // Panel chứa nút
            JPanel buttonDialogPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonDialogPanel.setBackground(Color.WHITE);
            CustomButton closeButton = new CustomButton("Đóng");
            closeButton.setButtonColors(CustomButton.ButtonColors.BLUE);
            closeButton.addActionListener(ev -> dialog.dispose());
            buttonDialogPanel.add(closeButton);

            // Thêm các panel vào dialog
            dialog.add(infoPanel, BorderLayout.CENTER);
            dialog.add(buttonDialogPanel, BorderLayout.SOUTH);

            // Hiển thị dialog
            dialog.setVisible(true);

            // Lưu thông tin lương vào CSDL
            if (luongBUS.themLuong(luong)) {
                JOptionPane.showMessageDialog(this, "Đã lưu thông tin lương thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Không thể lưu thông tin lương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(xuatBaoCaoButton);

        leftPanel.add(titlePanel, BorderLayout.NORTH);
        leftPanel.add(tablePanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadEmployeeData(DefaultTableModel model) {
        model.setRowCount(0);
        for (nhanVienDTO nv : NhanVienBUS.layDanhSachNhanVien()) {
            model.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getMaCV()
            });
        }
    }

    private void setupRightPanel() {
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setLayout(new BorderLayout(0, 10));

        setupInfoPanel();
        setupCalendarPanel();

        rightPanel.add(infoPanel, BorderLayout.NORTH);
        rightPanel.add(calendarPanel, BorderLayout.CENTER);
    }

    private void setupInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nhanVienInfoLabel = new JLabel("Chưa chọn nhân viên");
        nhanVienInfoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        infoPanel.add(nhanVienInfoLabel);
        infoPanel.add(Box.createVerticalStrut(10));

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
        updateCalendarPanel(selectedMonth, selectedYear);
    }

    private void updateCalendarPanel(int month, int year) {
        calendarPanel.removeAll();
        ((TitledBorder) calendarPanel.getBorder()).setTitle("Lịch chấm công tháng " + month + "/" + year);
        calendarPanel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        headerPanel.setBackground(new Color(240, 245, 250));

        String[] dayNames = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
        Color[] headerColors = {
                new Color(255, 200, 200),
                new Color(220, 240, 255),
                new Color(220, 240, 255),
                new Color(220, 240, 255),
                new Color(220, 240, 255),
                new Color(220, 240, 255),
                new Color(255, 230, 210)
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

        JPanel daysPanel = new JPanel();
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        int firstDayOfMonth = LocalDate.of(year, month, 1).getDayOfWeek().getValue() % 7;

        int rows = (int) Math.ceil((daysInMonth + firstDayOfMonth) / 7.0);
        daysPanel.setLayout(new GridLayout(rows, 7));

        for (int i = 0; i < firstDayOfMonth; i++) {
            JPanel emptyCell = new JPanel();
            emptyCell.setBackground(new Color(245, 245, 245));
            emptyCell.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
            daysPanel.add(emptyCell);
        }

        for (int day = 1; day <= daysInMonth; day++) {
            JPanel dayCell = createDayCell(day);
            daysPanel.add(dayCell);
        }

        int remainingCells = rows * 7 - (daysInMonth + firstDayOfMonth);
        for (int i = 0; i < remainingCells; i++) {
            JPanel emptyCell = new JPanel();
            emptyCell.setBackground(new Color(245, 245, 245));
            emptyCell.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
            daysPanel.add(emptyCell);
        }

        calendarPanel.add(headerPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(daysPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        calendarPanel.add(scrollPane, BorderLayout.CENTER);

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private JPanel createDayCell(int day) {
        JPanel dayPanel = new JPanel();
        dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
        dayPanel.setBackground(Color.WHITE);
        dayPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel dayLabel = new JLabel(String.valueOf(day));
        dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dayPanel.add(dayLabel);

        // Add check mark if employee has attendance record for this day
        if (selectedMaNV != null) {
            LocalDate checkDate = LocalDate.of(selectedYear, selectedMonth, day);
            if (ChamCongBUS.daChamCong(selectedMaNV, checkDate)) {
                FlatSVGIcon checkIcon = new FlatSVGIcon("Icons/check.svg", 16, 16);
                JLabel checkLabel = new JLabel(checkIcon);
                checkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                dayPanel.add(checkLabel);
            }
        }

        return dayPanel;
    }

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