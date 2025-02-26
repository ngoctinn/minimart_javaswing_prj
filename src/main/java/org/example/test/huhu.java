package org.example.test;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
import org.example.Components.CustomTexField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.BorderLayout;

public class huhu extends JPanel {
    // Danh sách nhóm hàng
    private DefaultListModel<String> listModel;
    private JList<String> categoryList;
    private JTextField searchField;
    private List<String> categories = List.of("Tất cả", "Thuốc lá", "Sữa", "Nước ngọt", "Mỹ phẩm", "Kẹo bánh", "Đồ uống có cồn", "Thực phẩm ăn liền");

    public huhu() {
        // Thiết lập bố cục chính
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Panel bên trái
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(200, 600));

        // Tiêu đề danh mục
        JLabel titleLabel = new JLabel("Hàng hóa");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Panel nhóm hàng
        RoundedPanel groupPanel = new RoundedPanel(30);
        groupPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));







// Tiêu đề và nút "Thêm" và "Ẩn/Hiện"
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleGroupLabel = new JLabel("Nhóm hàng");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

// Panel chứa các nút
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Thêm");
        JButton hideShowButton = new JButton("Ẩn/Hiện");

// Thêm các nút vào buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(hideShowButton);

// Thêm titleLabel và buttonPanel vào titlePanel
        titlePanel.add(titleGroupLabel, BorderLayout.WEST);
        titlePanel.add(buttonPanel, BorderLayout.EAST);

// Thêm titlePanel vào groupPanel
        groupPanel.add(titlePanel, BorderLayout.NORTH);

// Ô tìm kiếm nhóm hàng
        CustomTexField searchField = new CustomTexField("Tìm kiếm nhóm hàng");
        searchField.setPreferredSize(new Dimension(200, 35));


// Thêm searchField vào groupPanel
        groupPanel.add(searchField, BorderLayout.CENTER);

// Danh sách nhóm hàng
        listModel = new DefaultListModel<>();
        categories.forEach(listModel::addElement);
        categoryList = new JList<>(listModel);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setFont(new Font("Arial", Font.PLAIN, 14));
        groupPanel.add(new JScrollPane(categoryList), BorderLayout.SOUTH);


        //Panel thời gian tạo
        JPanel timePanel = new JPanel();
        JLabel label = new JLabel("Thời gian tạo");
        label.setBounds(10, 10, 160, 25);
        timePanel.add(label);

        JRadioButton allTimeButton = new JRadioButton("Toàn thời gian");
        allTimeButton.setBounds(20, 40, 200, 25);
        allTimeButton.setSelected(true);
        timePanel.add(allTimeButton);

        JRadioButton otherOptionsButton = new JRadioButton("Lựa chọn khác");
        otherOptionsButton.setBounds(20, 70, 200, 25);
        timePanel.add(otherOptionsButton);

        ButtonGroup group = new ButtonGroup();
        group.add(allTimeButton);
        group.add(otherOptionsButton);

        JButton upButton = new JButton("↑");
        upButton.setBounds(230, 40, 50, 25);
        timePanel.add(upButton);

        JButton downButton = new JButton("↓");
        downButton.setBounds(290, 40, 50, 25);
        timePanel.add(downButton);
// Thêm groupPanel vào leftPanel
        leftPanel.add(titleLabel);
        leftPanel.add(groupPanel);
        leftPanel.add(timePanel);


        // Panel bên phải
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Thanh tìm kiếm trên cùng
        JPanel topPanel = new JPanel(new BorderLayout());
        JTextField searchFieldTop = new JTextField("Theo mã, tên hàng");
        topPanel.add(searchFieldTop, BorderLayout.WEST);

        // Thanh công cụ
        JPanel toolPanel = new JPanel();
        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/doitac.svg", 16, 16);
        FlatSVGIcon importIcon = new FlatSVGIcon("Icons/doitac.svg", 16, 16);
        FlatSVGIcon exportIcon = new FlatSVGIcon("Icons/doitac.svg", 16, 16);
        FlatSVGIcon optionsIcon = new FlatSVGIcon("Icons/doitac.svg", 16, 16);

        CustomButton btnAdd = new CustomButton("", addIcon);
        btnAdd.setButtonColors(CustomButton.ButtonColors.GREEN);
        CustomButton btnImport = new CustomButton("", importIcon);
        btnImport.setButtonColors(CustomButton.ButtonColors.GREEN);
        CustomButton btnExport = new CustomButton("", exportIcon);
        btnExport.setButtonColors(CustomButton.ButtonColors.GREEN);
        CustomButton btnOptions = new CustomButton("", optionsIcon);
        btnOptions.setButtonColors(CustomButton.ButtonColors.GREEN);

        toolPanel.add(btnAdd);
        toolPanel.add(btnImport);
        toolPanel.add(btnExport);
        toolPanel.add(btnOptions);
        topPanel.add(toolPanel, BorderLayout.EAST);

        // Bảng dữ liệu hàng hóa
        String[] columnNames = {"Mã hàng", "Tên hàng", "Giá bán", "Giá vốn", "Tồn kho", "Khách đặt"};
        Object[][] data = {
                {"SP000025", "Hộp phở bò", 10000, 8000, 192, 0},
                {"SP000024", "Mì bò hầm", 15000, 14000, 0, 0},
                {"SP000023", "Thịt bò khô", 45000, 44000, 0, 0},
                {"SP000022", "Rượu men 500ml", 55000, 50000, 0, 0},
                {"SP000021", "Rượu Chivas Regal 12", 670000, 620000, 0, 0}
        };
        JScrollPane tableScrollPane = new JScrollPane(new CustomTable(data, columnNames));

        // Thêm các thành phần vào panel bên phải
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Thêm panel trái và phải vào giao diện chính
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    // Bộ lọc danh sách nhóm hàng
    private void filterList(String query) {
        List<String> filtered = categories.stream()
                .filter(item -> item.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        listModel.clear();
        filtered.forEach(listModel::addElement);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý Hàng Hóa");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setContentPane(new huhu());
            frame.setVisible(true);
        });
    }
}