package org.example.test;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;

public class ProductManagement extends JPanel {
    public ProductManagement() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel();
        createLeftPanel(leftPanel); // Invoke createLeftPanel

        JPanel rightPanel = new JPanel();
        createRightPanel(rightPanel); // Invoke createRightPanel

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    private void createLeftPanel(JPanel leftPanel) {
        leftPanel.setPreferredSize(new Dimension(200, 600));
        leftPanel.setBackground(new Color(240, 240, 240));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        JPanel groupPanel = new JPanel();
        JPanel timePanel = new JPanel();



        leftPanel.add(titlePanel); // Add to leftPanel
        leftPanel.add(groupPanel); // Add to leftPanel
        leftPanel.add(timePanel); // Add to leftPanel
    }

    private void createRightPanel(JPanel rightPanel) {
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.LIGHT_GRAY);

        JPanel topPanel = new JPanel();
        createTopPanel(topPanel); // Invoke createTopPanel

        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Mã hàng", "Tên hàng", "Giá bán", "Giá vốn", "Tồn kho", "Khách đặt"};
        Object[][] data = {
                {"SP000025", "Hộp phở bò", 10000, 8000, 192, 0},
                {"SP000024", "Mì bò hầm", 15000, 14000, 0, 0},
                {"SP000023", "Thịt bò khô", 45000, 44000, 0, 0},
                {"SP000022", "Rượu men 500ml", 55000, 50000, 0, 0},
                {"SP000021", "Rượu Chivas Regal 12", 670000, 620000, 0, 0}
        };
        JScrollPane tableScrollPane = new JScrollPane(new CustomTable(data, columnNames));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(tablePanel, BorderLayout.CENTER);
    }

    private void createTopPanel(JPanel topPanel) {
        topPanel.setLayout(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);

        JPanel searchPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        buttonPanel.setBackground(Color.WHITE);

        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/doitac.svg", 16, 16);
        FlatSVGIcon importIcon = new FlatSVGIcon("Icons/doitac.svg", 16, 16);
        FlatSVGIcon exportIcon = new FlatSVGIcon("Icons/doitac.svg", 16, 16);
        FlatSVGIcon optionsIcon = new FlatSVGIcon("Icons/doitac.svg", 16, 16);

        JTextField searchFieldTop = new JTextField("Theo mã, tên hàng");
        searchPanel.add(searchFieldTop);


        CustomButton btnAdd = new CustomButton("", addIcon);
        btnAdd.setButtonColors(CustomButton.ButtonColors.GREEN);
        CustomButton btnImport = new CustomButton("", importIcon);
        btnImport.setButtonColors(CustomButton.ButtonColors.GREEN);
        CustomButton btnExport = new CustomButton("", exportIcon);
        btnExport.setButtonColors(CustomButton.ButtonColors.GREEN);
        CustomButton btnOptions = new CustomButton("", optionsIcon);
        btnOptions.setButtonColors(CustomButton.ButtonColors.GREEN);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnImport);
        buttonPanel.add(btnExport);
        buttonPanel.add(btnOptions);

        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Product Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(new ProductManagement());
        frame.setVisible(true);
    }
}
