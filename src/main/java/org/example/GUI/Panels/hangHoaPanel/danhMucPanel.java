package org.example.GUI.Panels.hangHoaPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class danhMucPanel extends JPanel {
    public danhMucPanel() {
        initGUI();
    }

    public void initGUI() {
        this.setLayout(new FlowLayout());
        setBackground(new Color(225,225,225));
        this.setVisible(true);

        RoundedPanel topPanel = new RoundedPanel(20);
        RoundedPanel bottomPanel = new RoundedPanel(20);
        RoundedPanel bottomPanelLeft = new RoundedPanel(20);
        RoundedPanel bottomPanelRight = new RoundedPanel(20);

        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(new Color(225,225,225));
        bottomPanelLeft.setBackground(Color.WHITE);
        bottomPanelRight.setBackground(Color.WHITE);

        topPanel.setPreferredSize(new Dimension(1270, 50));
        bottomPanel.setPreferredSize(new Dimension(1270, 900));

        topPanel.setLayout(null);
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new FlowLayout());

        bottomPanelLeft.setPreferredSize(new Dimension(1270 * 20 / 100, 900));
        bottomPanelRight.setPreferredSize(new Dimension(1270 * 80 / 100, 900));

        //============Các thành phần của topPanel================
        JLabel title = new JLabel("Danh mục hàng hoá");
        title.setFont(new Font("Roboto", Font.BOLD, 23));
        title.setForeground(new Color(0, 0, 0));
        title.setBounds(10, 10, 220, 30);
        topPanel.add(title);

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchField.setBounds(270, 12, 300, 30);
        topPanel.add(searchField);

        CustomButton searchButton = new CustomButton("Tìm");
        searchButton.setBounds(580, 12, 70, 30);
        topPanel.add(searchButton);

        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg",16,16);
        CustomButton addButton = new CustomButton("", addIcon);
        addButton.setBounds(1080, 12, 50, 30);
        addButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        topPanel.add(addButton);

        FlatSVGIcon exportIcon = new FlatSVGIcon("Icons/excel.svg",16,16);
        CustomButton exportButton = new CustomButton("", exportIcon);
        exportButton.setBounds(1140, 12, 50, 30);
        exportButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        topPanel.add(exportButton);

        FlatSVGIcon importIcon = new FlatSVGIcon("Icons/import.svg",16,16);
        CustomButton importButton = new CustomButton("", importIcon);
        importButton.setBounds(1200, 12, 50, 30);
        importButton.setButtonColors(CustomButton.ButtonColors.GREEN);
        topPanel.add(importButton);

        //============Các thành phần của bottomPanelLeft================
        JPanel bottomPanelLeft1 = new JPanel();
        bottomPanelLeft1.setBackground(Color.WHITE);
        bottomPanelLeft1.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),  // Viền ngoài
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
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(200, 150));
        bottomPanelLeft1.add(listScroller);

        this.add(topPanel);
        this.add(bottomPanel);
        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);
    }

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
