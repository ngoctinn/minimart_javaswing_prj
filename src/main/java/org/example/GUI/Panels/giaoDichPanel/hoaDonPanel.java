package org.example.GUI.Panels.giaoDichPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.RoundedPanel;
import org.example.GUI.Dialogs.ThemHangHoaDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;


public class hoaDonPanel extends JPanel {
    public hoaDonPanel() {
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
        JLabel title = new JLabel("Hoá Đơn");
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
                "Thời Gian",  // Tiêu đề
                TitledBorder.DEFAULT_JUSTIFICATION,  // Căn lề mặc định
                TitledBorder.DEFAULT_POSITION,  // Vị trí mặc định
                new Font("Segoe UI", Font.BOLD, 15),  // Font chữ
                Color.BLACK  // Màu chữ
        ));

        bottomPanelLeft1.setPreferredSize(new Dimension(230, 100));
        bottomPanelLeft.add(bottomPanelLeft1);

        JLabel startLabel = new JLabel("Ngày bắt đầu:");
        SpinnerDateModel startModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner startSpinner = new JSpinner(startModel);
        startSpinner.setEditor(new JSpinner.DateEditor(startSpinner, "dd/MM/yyyy"));

        // Spinner Ngày kết thúc
        JLabel endLabel = new JLabel("Ngày kết thúc:");
        SpinnerDateModel endModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner endSpinner = new JSpinner(endModel);
        endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, "dd/MM/yyyy"));

        // Thêm vào panel
        bottomPanelLeft1.add(startLabel);
        bottomPanelLeft1.add(startSpinner);
        bottomPanelLeft1.add(endLabel);
        bottomPanelLeft1.add(endSpinner);

        JPanel bottomPanelLeft2 = new JPanel();
        bottomPanelLeft2.setBackground(Color.WHITE);
        bottomPanelLeft2.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),  // Viền ngoài
                "Trạng Thái",  // Tiêu đề
                TitledBorder.DEFAULT_JUSTIFICATION,  // Căn lề mặc định
                TitledBorder.DEFAULT_POSITION,  // Vị trí mặc định
                new Font("Segoe UI", Font.BOLD, 15),  // Font chữ
                Color.BLACK  // Màu chữ
        ));

        bottomPanelLeft2.setLayout(new BoxLayout(bottomPanelLeft2, BoxLayout.Y_AXIS));

        bottomPanelLeft2.setPreferredSize(new Dimension(230 , 100));
        bottomPanelLeft.add(bottomPanelLeft2);

        JRadioButton radioButtonshow1 = new JRadioButton("Đang Xử Lý");
        radioButtonshow1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JRadioButton radioButtonshow2 = new JRadioButton("Hoàn Thành");
        radioButtonshow2.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        ButtonGroup buttonGroupshow = new ButtonGroup();
        buttonGroupshow.add(radioButtonshow1);
        buttonGroupshow.add(radioButtonshow2);

        bottomPanelLeft2.add(radioButtonshow1);
        bottomPanelLeft2.add(radioButtonshow2);

        JPanel bottomPanelLeft3 = new JPanel();
        bottomPanelLeft3.setBackground(Color.WHITE);
        bottomPanelLeft3.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),  // Viền ngoài
                "Người Tạo",  // Tiêu đề
                TitledBorder.DEFAULT_JUSTIFICATION,  // Căn lề mặc định
                TitledBorder.DEFAULT_POSITION,  // Vị trí mặc định
                new Font("Segoe UI", Font.BOLD, 15),  // Font chữ
                Color.BLACK  // Màu chữ
        ));
        bottomPanelLeft3.setLayout(new BoxLayout(bottomPanelLeft3, BoxLayout.Y_AXIS));

        bottomPanelLeft3.setPreferredSize(new Dimension(230, 150));
        bottomPanelLeft.add(bottomPanelLeft3);

        JList<String> list = new JList<>(new String[]{"Tây Bán Bom", "Tín Víp Pro", "Thư Bồ Tín", "An Má Bé Sol", "HURRYKHANG","Jack Bỏ Con"});
        list.setLayoutOrientation(JList.VERTICAL);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setBorder(BorderFactory.createEmptyBorder());
        listScroller.setPreferredSize(new Dimension(200, 150));
        bottomPanelLeft3.add(listScroller);
        //============End các thành phần của bottomPanelLeft================

        //============Các thành phần của bottomPanelRight================
        // Tạo tiêu đề cột
        String[] columnNames = {"Mã hóa đơn", "Thời gian", "Khách hàng", "Tổng tiền hàng", "Giảm giá", "Khách đã trả"};

        // Dữ liệu hóa đơn
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
            frame.add(new org.example.GUI.Panels.hangHoaPanel.danhMucPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    }
}
