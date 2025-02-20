package org.example.Components;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class CustomTable extends JTable {
    
    // Màu nền xen kẽ cho các dòng
    private Color evenRowColor = Color.WHITE;
    private Color oddRowColor = new Color(230, 240, 255);
    
    // Màu nền cho dòng được chọn
    private Color selectionColor = new Color(180, 210, 255);
    
    // Màu của lưới (grid)
    private Color gridColor = new Color(200, 200, 200);
    
    // Cấu hình cho header
    private Font headerFont = new Font("SansSerif", Font.BOLD, 14);
    private Color headerBackground = new Color(60, 120, 200);
    private Color headerForeground = Color.WHITE;

    // Constructor với dữ liệu và tiêu đề cột
    public CustomTable(Object[][] data, Object[] columnNames) {
        super(new DefaultTableModel(data, columnNames));
        initialize();
    }
    
    // Constructor với TableModel
    public CustomTable(TableModel model) {
        super(model);
        initialize();
    }
    
    // Constructor mặc định
    public CustomTable() {
        super();
        initialize();
    }
    
    // Phương thức khởi tạo giao diện bảng
    private void initialize() {
        // Cài đặt thuộc tính cơ bản của bảng
        setRowHeight(30);
        setFillsViewportHeight(true);
        setGridColor(gridColor);
        setSelectionBackground(selectionColor);
        setSelectionForeground(Color.BLACK);
        
        // Tùy chỉnh header của bảng
        JTableHeader header = getTableHeader();
        header.setBackground(headerBackground);
        header.setForeground(headerForeground);
        header.setFont(headerFont);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        // Nếu bạn muốn bảng không thể chỉnh sửa theo mặc định:
        setDefaultEditor(Object.class, null);
    }
    
    // Ghi đè phương thức prepareRenderer để áp dụng màu nền xen kẽ
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (!isRowSelected(row)) {
            c.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
        } else {
            c.setBackground(selectionColor);
        }
        return c;
    }
    
    // Các phương thức setter để tùy chỉnh màu sắc và giao diện
    public void setEvenRowColor(Color evenRowColor) {
        this.evenRowColor = evenRowColor;
    }
    
    public void setOddRowColor(Color oddRowColor) {
        this.oddRowColor = oddRowColor;
    }
    
    public void setSelectionColor(Color selectionColor) {
        this.selectionColor = selectionColor;
        setSelectionBackground(selectionColor);
    }
    
    @Override
    public void setGridColor(Color gridColor) {
        super.setGridColor(gridColor);
        this.gridColor = gridColor;
    }
    
    public void setHeaderFont(Font headerFont) {
        this.headerFont = headerFont;
        JTableHeader header = getTableHeader();
        header.setFont(headerFont);
    }
    
    public void setHeaderBackground(Color headerBackground) {
        this.headerBackground = headerBackground;
        JTableHeader header = getTableHeader();
        header.setBackground(headerBackground);
    }
    
    public void setHeaderForeground(Color headerForeground) {
        this.headerForeground = headerForeground;
        JTableHeader header = getTableHeader();
        header.setForeground(headerForeground);
    }

    public static void main(String[] args) {
        // Tạo frame để demo
        JFrame frame = new JFrame("Custom Table Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        // 1. Cách 1: Tạo bảng với dữ liệu và tiêu đề có sẵn
        Object[] columnNames = {"ID", "Họ Tên", "Tuổi", "Email", "Địa chỉ"};
        Object[][] data = {
                {"1", "Nguyễn Văn A", 25, "nguyenvana@email.com", "Hà Nội"},
                {"2", "Trần Thị B", 30, "tranthib@email.com", "TP.HCM"},
                {"3", "Lê Văn C", 28, "levanc@email.com", "Đà Nẵng"},
                {"4", "Phạm Thị D", 22, "phamthid@email.com", "Cần Thơ"},
                {"5", "Hoàng Văn E", 35, "hoangvane@email.com", "Hải Phòng"}
        };

        CustomTable table1 = new CustomTable(data, columnNames);

        // 2. Cách 2: Tạo bảng với TableModel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã SP");
        model.addColumn("Tên Sản Phẩm");
        model.addColumn("Giá");
        model.addColumn("Số lượng");

        model.addRow(new Object[]{"SP001", "Laptop", 15000000, 10});
        model.addRow(new Object[]{"SP002", "Điện thoại", 8000000, 20});
        model.addRow(new Object[]{"SP003", "Máy tính bảng", 12000000, 15});

        CustomTable table2 = new CustomTable(model);

//        // 3. Tùy chỉnh giao diện bảng
//        table2.setEvenRowColor(new Color(240, 255, 240));  // Màu xanh nhạt cho dòng chẵn
//        table2.setOddRowColor(new Color(255, 255, 255));   // Màu trắng cho dòng lẻ
//        table2.setSelectionColor(new Color(152, 251, 152)); // Màu khi chọn dòng
//        table2.setGridColor(new Color(180, 180, 180));     // Màu đường kẻ

//        // Tùy chỉnh header
//        table2.setHeaderBackground(new Color(0, 100, 0));  // Màu nền header
//        table2.setHeaderForeground(Color.WHITE);           // Màu chữ header
//        table2.setHeaderFont(new Font("Arial", Font.BOLD, 14)); // Font cho header

        // 4. Đặt bảng vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(table2);

        // 5. Thêm vào frame và hiển thị
        frame.add(scrollPane);
        frame.setVisible(true);


    }
}

