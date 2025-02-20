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
}

