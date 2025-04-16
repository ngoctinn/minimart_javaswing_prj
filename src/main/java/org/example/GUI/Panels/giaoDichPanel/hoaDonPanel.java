package org.example.GUI.Panels.giaoDichPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.GUI.MenuFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

public class hoaDonPanel extends JPanel {
    // UI Components
    private PlaceholderTextField searchField;
    private CustomButton searchButton, refreshButton, addButton, editButton, deleteButton, importButton, exportButton;
    private JSpinner startDateSpinner, endDateSpinner;
    private JRadioButton processingRadioButton, completedRadioButton;
    private JList<String> userList;
    private CustomTable hoaDonTable;
    private CustomButton filterDateButton;

    public hoaDonPanel() {
        initGUI();
    }

    public void initGUI() {
        setupMainPanel();

        // Create sub-panels
        RoundedPanel topPanel = createTopPanel();
        RoundedPanel bottomPanel = createBottomPanel();

        // Add panels to main panel
        this.add(topPanel);
        this.add(bottomPanel);
    }

    private void setupMainPanel() {
        this.setLayout(new FlowLayout());
        this.setBackground(new Color(225, 225, 225));
        this.setVisible(true);
    }

    private RoundedPanel createTopPanel() {
        RoundedPanel topPanel = new RoundedPanel(20);
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(1270, 50));
        topPanel.setLayout(null);

        // Add components to top panel
        addTitleToPanel(topPanel);
        addSearchComponentsToPanel(topPanel);
        addActionButtonsToPanel(topPanel);

        return topPanel;
    }

    private void addTitleToPanel(RoundedPanel panel) {
        JLabel title = new JLabel("Hoá Đơn");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        title.setBounds(10, 10, 220, 30);
        panel.add(title);
    }

    private void addSearchComponentsToPanel(RoundedPanel panel) {
        searchField = new PlaceholderTextField("Nhập mã hoá đơn cần tìm");
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchField.setBounds(270, 12, 300, 30);
        panel.add(searchField);

        searchButton = new CustomButton("Tìm");
        searchButton.setBounds(580, 12, 70, 30);
        panel.add(searchButton);
    }

    private void addActionButtonsToPanel(RoundedPanel panel) {
        // Refresh button
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setBounds(660, 12, 50, 30);
        refreshButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        panel.add(refreshButton);

        // Add button - Adjusted to match DanhMucPanel layout
        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg", 16, 16);
        addButton = new CustomButton("Thêm", addIcon);
        addButton.setBounds(820, 12, 100, 30);
        addButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        //MenuFrame hiển thị panel bán hàng
        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuFrame menuFrame = new MenuFrame();
                menuFrame.showPanel("banHang");
            }
        });
        panel.add(addButton);

        // Edit button - Adjusted position
        FlatSVGIcon editIcon = new FlatSVGIcon("Icons/edit.svg", 20, 20);
        editButton = new CustomButton("Sửa", editIcon);
        editButton.setBounds(930, 12, 100, 30);
        editButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        panel.add(editButton);

        // Delete button - Adjusted position
        FlatSVGIcon deleteIcon = new FlatSVGIcon("Icons/delete.svg", 20, 20);
        deleteButton = new CustomButton("Xóa", deleteIcon);
        deleteButton.setBounds(1040, 12, 100, 30);
        deleteButton.setButtonColors(CustomButton.ButtonColors.RED);
        panel.add(deleteButton);

        // Export button - Adjusted position
        FlatSVGIcon exportIcon = new FlatSVGIcon("Icons/excel.svg", 16, 16);
        exportButton = new CustomButton("", exportIcon);
        exportButton.setBounds(1150, 12, 50, 30);
        exportButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        panel.add(exportButton);

        // Import button - Adjusted position
        FlatSVGIcon importIcon = new FlatSVGIcon("Icons/import.svg", 16, 16);
        importButton = new CustomButton("", importIcon);
        importButton.setBounds(1210, 12, 50, 30);
        importButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        panel.add(importButton);
    }

    private RoundedPanel createBottomPanel() {
        RoundedPanel bottomPanel = new RoundedPanel(20);
        bottomPanel.setBackground(new Color(225, 225, 225));
        bottomPanel.setPreferredSize(new Dimension(1270, 900));
        bottomPanel.setLayout(new BorderLayout(5, 0));

        RoundedPanel bottomPanelLeft = createFilterPanel();
        RoundedPanel bottomPanelRight = createTablePanel();

        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);

        return bottomPanel;
    }

    private RoundedPanel createFilterPanel() {
        RoundedPanel filterPanel = new RoundedPanel(20);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filterPanel.setPreferredSize(new Dimension(1270 * 20 / 100, 900));

        // Add filter components
        filterPanel.add(createDateFilterPanel());
        filterPanel.add(createStatusFilterPanel());
        filterPanel.add(createUserFilterPanel());

        return filterPanel;
    }

    private JPanel createDateFilterPanel() {
        JPanel datePanel = new JPanel();
        datePanel.setBackground(Color.WHITE);
        datePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Thời Gian",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));
        datePanel.setPreferredSize(new Dimension(220, 180));
        datePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // Start date components
        JLabel startLabel = new JLabel("Ngày bắt đầu:");
        startLabel.setPreferredSize(new Dimension(200, 20));
        startLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        SpinnerDateModel startModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        startDateSpinner = new JSpinner(startModel);
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy"));
        startDateSpinner.setPreferredSize(new Dimension(200, 25));

        // End date components
        JLabel endLabel = new JLabel("Ngày kết thúc:");
        endLabel.setPreferredSize(new Dimension(200, 20));
        endLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        SpinnerDateModel endModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        endDateSpinner = new JSpinner(endModel);
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy"));
        endDateSpinner.setPreferredSize(new Dimension(200, 25));

        // Filter button
        filterDateButton = new CustomButton("Lọc hoá đơn");
        filterDateButton.setPreferredSize(new Dimension(195, 25));
        filterDateButton.setButtonColors(CustomButton.ButtonColors.GRAY);

        datePanel.add(startLabel);
        datePanel.add(startDateSpinner);
        datePanel.add(endLabel);
        datePanel.add(endDateSpinner);
        datePanel.add(filterDateButton);

        return datePanel;
    }

    private JPanel createStatusFilterPanel() {
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Trạng Thái",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setPreferredSize(new Dimension(220, 100));

        processingRadioButton = new JRadioButton("Đang Xử Lý");
        processingRadioButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        processingRadioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        processingRadioButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));

        completedRadioButton = new JRadioButton("Hoàn Thành");
        completedRadioButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        completedRadioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        completedRadioButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));

        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(processingRadioButton);
        statusGroup.add(completedRadioButton);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.setBackground(Color.WHITE);
        radioPanel.add(processingRadioButton);
        radioPanel.add(completedRadioButton);

        statusPanel.add(Box.createVerticalStrut(5));
        statusPanel.add(radioPanel);
        statusPanel.add(Box.createVerticalStrut(5));

        return statusPanel;
    }

    private JPanel createUserFilterPanel() {
        JPanel userPanel = new JPanel();
        userPanel.setBackground(Color.WHITE);
        userPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Người Tạo",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));
        userPanel.setLayout(new BorderLayout(5, 5));
        userPanel.setPreferredSize(new Dimension(220, 180));

        userList = new JList<>(new String[]{"Nguyễn Đức Tây", "Nguyễn Ngọc Tín", "Nguyễn Thị Tuyết Thư", "An Má Bé Sol", "HURRYKHANG", "Jack Bỏ Con"});
        userList.setLayoutOrientation(JList.VERTICAL);
        userList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userList.setVisibleRowCount(6);

        JScrollPane listScroller = new JScrollPane(userList);
        listScroller.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        userPanel.add(listScroller, BorderLayout.CENTER);

        return userPanel;
    }

    private RoundedPanel createTablePanel() {
        RoundedPanel tablePanel = new RoundedPanel(20);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setPreferredSize(new Dimension(1270 * 80 / 100, 900));

        // Create table with data
        String[] columnNames = {"Mã hóa đơn", "Thời gian", "Khách hàng", "Tổng tiền hàng", "Giảm giá", "Khách đã trả"};
        Object[][] data = {
                {"HD000046", "17/02/2025 23:36", "Tuấn - Hà Nội", 2580000, 0, 2580000},
                {"HD000045", "16/02/2025 23:35", "Phạm Thu Hương", 55000, 0, 55000},
                {"HD000044", "15/02/2025 23:35", "Tuấn - Hà Nội", 190000, 0, 190000},
                {"HD000043", "14/02/2025 23:33", "Anh Hoàng - Sài Gòn", 195000, 0, 195000},
                {"HD000042", "13/02/2025 23:32", "Anh Hoàng - Sài Gòn", 145000, 0, 145000},
                {"HD000041", "12/02/2025 23:31", "Tuấn - Hà Nội", 150000, 0, 150000},
                {"HD000040", "11/02/2025 23:30", "Anh Hoàng - Sài Gòn", 135000, 0, 135000},
                {"HD000039", "10/02/2025 23:30", "Phạm Thu Hương", 190000, 0, 190000},
                {"HD000038", "09/02/2025 23:29", "Anh Hoàng - Sài Gòn", 55000, 0, 55000},
                {"HD000037", "08/02/2025 23:29", "Anh Hoàng - Sài Gòn", 55000, 0, 55000},
                {"HD000036", "07/02/2025 23:26", "Tuấn - Hà Nội", 275000, 0, 275000},
                {"HD000035", "06/02/2025 23:25", "Phạm Thu Hương", 95000, 0, 95000},
                {"HD000034", "05/02/2025 23:24", "Phạm Thu Hương", 110000, 0, 110000},
        };

        hoaDonTable = new CustomTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(hoaDonTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        tablePanel.add(tableScrollPane);

        return tablePanel;
    }

    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new hoaDonPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
