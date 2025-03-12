package org.example.GUI.Panels.hangHoaPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;
import org.example.GUI.Dialogs.ThemHangHoaDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class kiemKhoPanel extends JPanel {
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;

    private PlaceholderTextField searchField;
    private CustomButton searchButton;
    private CustomButton refreshButton;
    private CustomButton addButton;
    private CustomButton editButton;
    private CustomButton deleteButton;
    private CustomButton importButton;
    private CustomButton exportButton;

    private JList<String> userList;

    public kiemKhoPanel() {
        initGUI();
    }

    public void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanelLeft();
        setupBottomPanelRight();

        // Add panels to main panel
        this.add(topPanel);
        this.add(bottomPanel);
        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);
    }

    private void setupMainPanel() {
        // Set up layout and background for main panel
        this.setLayout(new FlowLayout());
        this.setBackground(new Color(225, 225, 225));
        this.setVisible(true);
    }

    private void createPanels() {
        // Create sub-panels
        topPanel = new RoundedPanel(20);
        bottomPanel = new RoundedPanel(20);
        bottomPanelLeft = new RoundedPanel(20);
        bottomPanelRight = new RoundedPanel(20);

        // Set background colors
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(new Color(225, 225, 225));
        bottomPanelLeft.setBackground(Color.WHITE);
        bottomPanelRight.setBackground(Color.WHITE);

        // Set panel sizes
        topPanel.setPreferredSize(new Dimension(1270, 50));
        bottomPanel.setPreferredSize(new Dimension(1270, 900));
        bottomPanelLeft.setPreferredSize(new Dimension(1270 * 20 / 100, 900));
        bottomPanelRight.setPreferredSize(new Dimension(1270 * 80 / 100, 900));

        // Set layouts
        topPanel.setLayout(null);
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new FlowLayout());
        bottomPanelRight.setLayout(new BoxLayout(bottomPanelRight, BoxLayout.Y_AXIS));
    }

    private void setupTopPanel() {
        // Title label
        JLabel title = new JLabel("Phiếu Kiểm Kho");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        title.setBounds(10, 10, 220, 30);
        topPanel.add(title);

        // Search field
        searchField = new PlaceholderTextField("Nhập mã phiếu kiểm kho cần tìm");
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchField.setBounds(270, 12, 300, 30);
        topPanel.add(searchField);

        // Search button
        searchButton = new CustomButton("Tìm");
        searchButton.setBounds(580, 12, 70, 30);
        topPanel.add(searchButton);

        // Action buttons
        setupActionButtons();
    }

    private void setupActionButtons() {
        // Refresh button
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setBounds(900, 12, 50, 30);
        refreshButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        topPanel.add(refreshButton);

        // Add button
        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg", 16, 16);
        addButton = new CustomButton("", addIcon);
        addButton.setBounds(960, 12, 50, 30);
        addButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        addButton.addActionListener(e -> new ThemHangHoaDialog());
        topPanel.add(addButton);

        // Edit button
        FlatSVGIcon editIcon = new FlatSVGIcon("Icons/edit.svg", 20, 20);
        editButton = new CustomButton("", editIcon);
        editButton.setBounds(1020, 12, 50, 30);
        editButton.setButtonColors(CustomButton.ButtonColors.YELLOW);
        topPanel.add(editButton);

        // Delete button
        FlatSVGIcon deleteIcon = new FlatSVGIcon("Icons/delete.svg", 20, 20);
        deleteButton = new CustomButton("", deleteIcon);
        deleteButton.setBounds(1080, 12, 50, 30);
        deleteButton.setButtonColors(CustomButton.ButtonColors.RED);
        topPanel.add(deleteButton);

        // Export button
        FlatSVGIcon exportIcon = new FlatSVGIcon("Icons/excel.svg", 16, 16);
        exportButton = new CustomButton("", exportIcon);
        exportButton.setBounds(1140, 12, 50, 30);
        exportButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        topPanel.add(exportButton);

        // Import button
        FlatSVGIcon importIcon = new FlatSVGIcon("Icons/import.svg", 16, 16);
        importButton = new CustomButton("", importIcon);
        importButton.setBounds(1200, 12, 50, 30);
        importButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        topPanel.add(importButton);
    }

    private void setupBottomPanelLeft() {
        setupTimePanel();
        setupStatusPanel();
        setupCreatorPanel();
    }

    private void setupTimePanel() {
        JPanel timePanel = createTitledPanel("Thời Gian", 230, 100);
        bottomPanelLeft.add(timePanel);

        JLabel startLabel = new JLabel("Ngày bắt đầu:");
        SpinnerDateModel startModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner startSpinner = new JSpinner(startModel);
        startSpinner.setEditor(new JSpinner.DateEditor(startSpinner, "dd/MM/yyyy"));

        JLabel endLabel = new JLabel("Ngày kết thúc:");
        SpinnerDateModel endModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner endSpinner = new JSpinner(endModel);
        endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, "dd/MM/yyyy"));

        timePanel.add(startLabel);
        timePanel.add(startSpinner);
        timePanel.add(endLabel);
        timePanel.add(endSpinner);
    }

    private void setupStatusPanel() {
        JPanel statusPanel = createTitledPanel("Trạng Thái", 230, 130);
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(statusPanel);

        JRadioButton radioButtonTemp = new JRadioButton("Phiếu Tạm");
        radioButtonTemp.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JRadioButton radioButtonBalanced = new JRadioButton("Đã Cân Bằng Kho");
        radioButtonBalanced.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JRadioButton radioButtonCancelled = new JRadioButton("Đã Hủy");
        radioButtonCancelled.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JRadioButton radioButtonAll = new JRadioButton("Tất Cả");
        radioButtonAll.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonTemp);
        buttonGroup.add(radioButtonBalanced);
        buttonGroup.add(radioButtonCancelled);
        buttonGroup.add(radioButtonAll);

        statusPanel.add(radioButtonTemp);
        statusPanel.add(radioButtonBalanced);
        statusPanel.add(radioButtonCancelled);
        statusPanel.add(radioButtonAll);
    }

    private void setupCreatorPanel() {
        JPanel creatorPanel = createTitledPanel("Người Tạo", 230, 150);
        creatorPanel.setLayout(new BoxLayout(creatorPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(creatorPanel);

        String[] userData = {"Tây Bán Bom", "Tín Víp Pro", "Thư Bồ Tín", "An Má Bé Sol", "HURRYKHANG", "Jack Bỏ Con"};
        userList = new JList<>(userData);
        userList.setLayoutOrientation(JList.VERTICAL);
        userList.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JScrollPane listScroller = new JScrollPane(userList);
        listScroller.setBorder(BorderFactory.createEmptyBorder());
        listScroller.setPreferredSize(new Dimension(200, 150));
        creatorPanel.add(listScroller);
    }

    private void setupBottomPanelRight() {
        // Tạo tiêu đề cột
        String[] columnNames = {"Mã Kiếm Kho", "Thời Gian", "Ngày Cân Bằng", "SL Thực Tế", "Tổng Thực Tế", "Ghi Chú"};

        // Dữ liệu hóa đơn
        Object[][] data = {};

        CustomTable table = new CustomTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        bottomPanelRight.add(tableScrollPane);
    }

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

    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new kiemKhoPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
