package org.example.test;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;
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
        leftPanel.add(titleLabel);

        // Panel nhóm hàng
        RoundedPanel groupPanel = new RoundedPanel(30);
        groupPanel.setLayout(new BorderLayout());
        groupPanel.setBorder(BorderFactory.createTitledBorder("Nhóm hàng"));

        // Ô tìm kiếm nhóm hàng
        searchField = new JTextField("Tìm kiếm nhóm hàng");
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Tìm kiếm nhóm hàng")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Tìm kiếm nhóm hàng");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterList(searchField.getText());
            }
        });
        groupPanel.add(searchField, BorderLayout.NORTH);

        // Danh sách nhóm hàng
        listModel = new DefaultListModel<>();
        categories.forEach(listModel::addElement);
        categoryList = new JList<>(listModel);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setFont(new Font("Arial", Font.PLAIN, 14));
        groupPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);
        leftPanel.add(groupPanel);

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