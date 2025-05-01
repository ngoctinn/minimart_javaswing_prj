package org.example.GUI.Panels.doiTacPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.*;
import org.example.GUI.Dialogs.ThemNCCDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class nhaCungCapPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;

    // Top panel components
    private CustomTextField searchField;
    private CustomButton searchButton;
    private CustomButton refreshButton;
    private CustomButton addButton;
    private CustomButton editButton;
    private CustomButton deleteButton;
    private CustomButton importButton;
    private CustomButton exportButton;

    // Bottom panel components
    private JRadioButton radioButtonshow1;
    private JRadioButton radioButtonshow2;
    private JRadioButton radioButtonshow3;
    private JList<String> nhomNCCList;
    private CustomTable nhaCungCapTable;

    public nhaCungCapPanel() {
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
        JLabel title = new JLabel("Nhà Cung Cấp");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        title.setBounds(10, 10, 220, 30);
        topPanel.add(title);

        // Search field
        searchField = new CustomTextField("Nhập tên nhà cung cấp cần tìm");
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
        refreshButton.setBounds(660, 12, 50, 30);
        refreshButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        topPanel.add(refreshButton);

        // Add button
        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg", 16, 16);
        addButton = new CustomButton("Thêm", addIcon);
        addButton.setBounds(820, 12, 100, 30);
        addButton.setButtonColors(CustomButton.ButtonColors.BLUE);
        addButton.addActionListener(e -> {
            new ThemNCCDialog();
        });
        topPanel.add(addButton);

        // Edit button
        FlatSVGIcon editIcon = new FlatSVGIcon("Icons/edit.svg", 20, 20);
        editButton = new CustomButton("Sửa", editIcon);
        editButton.setBounds(930, 12, 100, 30);
        editButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        topPanel.add(editButton);

        // Delete button
        FlatSVGIcon deleteIcon = new FlatSVGIcon("Icons/delete.svg", 20, 20);
        deleteButton = new CustomButton("Xóa", deleteIcon);
        deleteButton.setBounds(1040, 12, 100, 30);
        deleteButton.setButtonColors(CustomButton.ButtonColors.RED);
        topPanel.add(deleteButton);

        // Export button
        FlatSVGIcon exportIcon = new FlatSVGIcon("Icons/excel.svg", 16, 16);
        exportButton = new CustomButton("", exportIcon);
        exportButton.setBounds(1150, 12, 50, 30);
        exportButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        topPanel.add(exportButton);

        // Import button
        FlatSVGIcon importIcon = new FlatSVGIcon("Icons/import.svg", 16, 16);
        importButton = new CustomButton("", importIcon);
        importButton.setBounds(1210, 12, 50, 30);
        importButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        topPanel.add(importButton);
    }

    private void setupBottomPanelLeft() {
        setupTrangThaiPanel();
    }

    private void setupTrangThaiPanel() {
        // Trạng thái panel
        JPanel trangThaiPanel = createTitledPanel("Trạng Thái", 230, 100);
        trangThaiPanel.setLayout(new BoxLayout(trangThaiPanel, BoxLayout.Y_AXIS));
        bottomPanelLeft.add(trangThaiPanel);

        // Radio buttons
        radioButtonshow1 = createRadioButton("Tất Cả");
        radioButtonshow2 = createRadioButton("Đang Hoạt Động");
        radioButtonshow3 = createRadioButton("Ngừng Hoạt Động");

        // Group radio buttons
        ButtonGroup buttonGroupshow = new ButtonGroup();
        buttonGroupshow.add(radioButtonshow1);
        buttonGroupshow.add(radioButtonshow2);
        buttonGroupshow.add(radioButtonshow3);

        // Add to panel
        trangThaiPanel.add(radioButtonshow1);
        trangThaiPanel.add(radioButtonshow2);
        trangThaiPanel.add(radioButtonshow3);
    }



    private void setupBottomPanelRight() {
        // Table data
        String[] columnNames = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Điện thoại", "Email", "Tổng mua"};
        Object[][] data = {
                {"NCC001", "Đức Tây ICool", "092392383", "0934564574ductay@gmail.com", 0},
                {"NCC005", "Cửa hàng Đại Việt", "", "", 0},
                {"NCC003", "Công ty Pharmedic", "", "", 0},
                {"NCC004", "Đại lý Hồng Phúc", "", "", 0},
                {"NCC001", "Công ty TNHH Citigo", "", "", 0},
                {"NCC002", "Công ty Hoàng Gia", "", "", 0},
        };

        // Create table
        nhaCungCapTable = new CustomTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(nhaCungCapTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        bottomPanelRight.add(tableScrollPane);
    }

    // Helper methods
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

    private JList<String> createScrollableList(String[] data) {
        JList<String> list = new JList<>(data);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return list;
    }

    private JScrollPane createScrollPane(JComponent component, int width, int height) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(width, height));
        return scrollPane;
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return radioButton;
    }

    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new nhaCungCapPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
