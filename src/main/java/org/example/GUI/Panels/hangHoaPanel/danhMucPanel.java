package org.example.GUI.Panels.hangHoaPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.RoundedPanel;
import org.example.GUI.Dialogs.ThemHangHoaDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class danhMucPanel extends JPanel {
    public danhMucPanel() {
        initGUI();
    }

    public void initGUI() {

        // thiết lập layout và background cho panel
        this.setLayout(new FlowLayout());
        this.setBackground(new Color(225,225,225));
        this.setVisible(true);

        // tạo các panel con
        RoundedPanel topPanel = new RoundedPanel(20);
        RoundedPanel bottomPanel = new RoundedPanel(20);
        RoundedPanel bottomPanelLeft = new RoundedPanel(20);
        RoundedPanel bottomPanelRight = new RoundedPanel(20);

        // thiết lập background cho các panel
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(new Color(225,225,225));
        bottomPanelLeft.setBackground(Color.WHITE);
        bottomPanelRight.setBackground(Color.WHITE);

        // thiết lập kích thước cho các panel
        topPanel.setPreferredSize(new Dimension(1270, 50));
        bottomPanel.setPreferredSize(new Dimension(1270, 900));

        // thiết lập layout cho các panel
        topPanel.setLayout(null);
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new FlowLayout());
        bottomPanelRight.setLayout(new BoxLayout(bottomPanelRight, BoxLayout.Y_AXIS));

        // thiết lập kích thước cho bottomPanelLeft và bottomPanelRight
        bottomPanelLeft.setPreferredSize(new Dimension(1270 * 20 / 100, 900));
        bottomPanelRight.setPreferredSize(new Dimension(1270 * 80 / 100, 900));

        //============Các thành phần của topPanel================
        //label và textfield
        JLabel title = new JLabel("Danh mục hàng hoá");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        title.setBounds(10, 10, 220, 30);
        topPanel.add(title);

        //search field
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchField.setBounds(270, 12, 300, 30);
        topPanel.add(searchField);

        //search button
        CustomButton searchButton = new CustomButton("Tìm");
        searchButton.setBounds(580, 12, 70, 30);
        topPanel.add(searchButton);

        //Refresh, Thêm, sửa , xoá, import, export
        FlatSVGIcon refreshIcon = new FlatSVGIcon("Icons/refresh.svg", 20, 20);
        CustomButton refreshButton = new CustomButton("", refreshIcon);
        refreshButton.setBounds(900, 12, 50, 30);
        refreshButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        topPanel.add(refreshButton);

        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg",16,16);
        CustomButton addButton = new CustomButton("", addIcon);
        addButton.setBounds(960, 12, 50, 30);
        addButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        topPanel.add(addButton);

        // sự kiện khi click vào nút thêm
        addButton.addActionListener(e -> {
            // code xử lý khi click vào nút thêm
            new ThemHangHoaDialog();
        });

        FlatSVGIcon editIcon = new FlatSVGIcon("Icons/edit.svg", 20, 20);
        CustomButton editButton = new CustomButton("", editIcon);
        editButton.setBounds(1020, 12, 50, 30);
        editButton.setButtonColors(CustomButton.ButtonColors.YELLOW);
        topPanel.add(editButton);

        FlatSVGIcon deleteIcon = new FlatSVGIcon("Icons/delete.svg", 20, 20);
        CustomButton deleteButton = new CustomButton("", deleteIcon);
        deleteButton.setBounds(1080, 12, 50, 30);
        deleteButton.setButtonColors(CustomButton.ButtonColors.RED);
        topPanel.add(deleteButton);

        FlatSVGIcon importIcon = new FlatSVGIcon("Icons/import.svg",16,16);
        CustomButton importButton = new CustomButton("", importIcon);
        importButton.setBounds(1200, 12, 50, 30);
        importButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        topPanel.add(importButton);

        FlatSVGIcon exportIcon = new FlatSVGIcon("Icons/excel.svg",16,16);
        CustomButton exportButton = new CustomButton("", exportIcon);
        exportButton.setBounds(1140, 12, 50, 30);
        exportButton.setButtonColors(CustomButton.ButtonColors.GRAY);
        topPanel.add(exportButton);
        //============End các thành phần của topPanel================

        //============Các thành phần của bottomPanelLeft================
        //Tạo 3 panel con trong bottomPanelLeft
        JPanel bottomPanelLeft1 = new JPanel();
        bottomPanelLeft1.setBackground(Color.WHITE);
        bottomPanelLeft1.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),  // Viền ngoài
                "Nhóm hàng",  // Tiêu đề
                TitledBorder.DEFAULT_JUSTIFICATION,  // Căn lề mặc định
                TitledBorder.DEFAULT_POSITION,  // Vị trí mặc định
                new Font("Segoe UI", Font.BOLD, 15),  // Font chữ
                Color.BLACK  // Màu chữ
        ));

        bottomPanelLeft1.setPreferredSize(new Dimension(230, 200));
        bottomPanelLeft.add(bottomPanelLeft1);

        JList<String> list = new JList<>(new String[]{"Nhóm 1", "Nhóm 2", "Nhóm 3", "Nhóm 4", "Nhóm 5", "Nhóm 6", "Nhóm 7", "Nhóm 8", "Nhóm 9", "Nhóm 10", "Nhóm 11", "Nhóm 12", "Nhóm 13", "Nhóm 14", "Nhóm 15", "Nhóm 16", "Nhóm 17", "Nhóm 18", "Nhóm 19", "Nhóm 20"});
        list.setLayoutOrientation(JList.VERTICAL);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setBorder(BorderFactory.createEmptyBorder());
        listScroller.setPreferredSize(new Dimension(200, 150));
        bottomPanelLeft1.add(listScroller);

        JPanel bottomPanelLeft2 = new JPanel();
        bottomPanelLeft2.setBackground(Color.WHITE);
        bottomPanelLeft2.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),  // Viền ngoài
                "Nhà cung cấp",  // Tiêu đề
                TitledBorder.DEFAULT_JUSTIFICATION,  // Căn lề mặc định
                TitledBorder.DEFAULT_POSITION,  // Vị trí mặc định
                new Font("Segoe UI", Font.BOLD, 15),  // Font chữ
                Color.BLACK  // Màu chữ
        ));

        bottomPanelLeft2.setPreferredSize(new Dimension(230, 200));
        bottomPanelLeft.add(bottomPanelLeft2);

        JList<String> list2 = new JList<>(new String[]{"Nhà cung cấp 1", "Nhà cung cấp 2", "Nhà cung cấp 3", "Nhà cung cấp 4", "Nhà cung cấp 5", "Nhà cung cấp 6", "Nhà cung cấp 7", "Nhà cung cấp 8", "Nhà cung cấp 9", "Nhà cung cấp 10", "Nhà cung cấp 11", "Nhà cung cấp 12", "Nhà cung cấp 13", "Nhà cung cấp 14", "Nhà cung cấp 15", "Nhà cung cấp 16", "Nhà cung cấp 17", "Nhà cung cấp 18", "Nhà cung cấp 19", "Nhà cung cấp 20"});
        list2.setLayoutOrientation(JList.VERTICAL);
        list2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JScrollPane listScroller2 = new JScrollPane(list2);
        listScroller2.setBorder(BorderFactory.createEmptyBorder());
        listScroller2.setPreferredSize(new Dimension(200, 150));
        bottomPanelLeft2.add(listScroller2);

        JPanel bottomPanelLeft3 = new JPanel();
        bottomPanelLeft3.setBackground(Color.WHITE);
        bottomPanelLeft3.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),  // Viền ngoài
                "Lựa chọn hiển thị",  // Tiêu đề
                TitledBorder.DEFAULT_JUSTIFICATION,  // Căn lề mặc định
                TitledBorder.DEFAULT_POSITION,  // Vị trí mặc định
                new Font("Segoe UI", Font.BOLD, 15),  // Font chữ
                Color.BLACK  // Màu chữ
        ));
        bottomPanelLeft3.setLayout(new BoxLayout(bottomPanelLeft3, BoxLayout.Y_AXIS));

        bottomPanelLeft3.setPreferredSize(new Dimension(230, 100));
        bottomPanelLeft.add(bottomPanelLeft3);

        JRadioButton radioButton1 = new JRadioButton("Tất cả");
        radioButton1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JRadioButton radioButton2 = new JRadioButton("Hàng đang kinh doanh");
        radioButton2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JRadioButton radioButton3 = new JRadioButton("Hàng ngừng kinh doanh");
        radioButton3.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);

        bottomPanelLeft3.add(radioButton1);
        bottomPanelLeft3.add(radioButton2);
        bottomPanelLeft3.add(radioButton3);
        //============End các thành phần của bottomPanelLeft================

        //============Các thành phần của bottomPanelRight================
        String[] columnNames = {"Mã hàng", "Tên hàng", "Nhóm hàng", "Nhà cung cấp", "Giá bán", "Giá vốn", "Tồn kho", "Khách đặt"};
        Object[][] data = {
                {"SP000025", "Hộp phở bò", "Nhóm 1", "Nhà cung cấp 1", 10000, 8000, 192, 0},
                {"SP000024", "Mì bò hầm", "Nhóm 2", "Nhà cung cấp 2", 15000, 14000, 0, 0},
                {"SP000023", "Thịt bò khô", "Nhóm 3", "Nhà cung cấp 3", 45000, 44000, 0, 0},
        };
        CustomTable table = new CustomTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        bottomPanelRight.add(tableScrollPane);

        //============End các thành phần của bottomPanelRight================

        //add các panel con vào panel chính
        this.add(topPanel);
        this.add(bottomPanel);
        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);
    }

    // Hàm main để test giao diện
    public static void main(String[] args) {

        // dùng flatlaf OSMacOSLookAndFeel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new danhMucPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    }
}
