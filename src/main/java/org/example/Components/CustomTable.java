package org.example.Components;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class CustomTable extends JTable {
    
    // Màu nền xen kẽ cho các dòng
    private Color evenRowColor = new Color(250, 250, 250);
    private Color oddRowColor = new Color(240, 245, 255);
    
    // Màu nền cho dòng được chọn và hover
    private Color selectionColor = new Color(70, 130, 220, 70);
    private Color hoverColor = new Color(220, 240, 255);
    
    // Màu của lưới (grid)
    private Color gridColor = new Color(230, 230, 230);
    
    // Cấu hình cho header
    private Font headerFont = new Font("SansSerif", Font.BOLD, 14);
    private Color headerBackground = new Color(50, 120, 220);
    private Color headerForeground = Color.WHITE;
    
    // Cấu hình cho nội dung
    private Font contentFont = new Font("SansSerif", Font.PLAIN, 13);
    private Color contentForeground = new Color(60, 60, 60);
    
    // Cấu hình bo tròn góc
    private boolean roundedCorners = true;
    private int cornerRadius = 8;
    
    // Theo dõi dòng hover
    private int hoveredRow = -1;

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
        setRowHeight(35);
        setFillsViewportHeight(true);
        setGridColor(gridColor);
        setSelectionBackground(selectionColor);
        setSelectionForeground(contentForeground);
        setFont(contentFont);
        setForeground(contentForeground);
        setShowGrid(true);
        setShowHorizontalLines(true);
        setShowVerticalLines(false);
        setIntercellSpacing(new Dimension(0, 1));
        setRowMargin(2);
        
        // Tùy chỉnh header của bảng
        JTableHeader header = getTableHeader();
        header.setBackground(headerBackground);
        header.setForeground(headerForeground);
        header.setFont(headerFont);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setBorder(BorderFactory.createEmptyBorder());
        
        // Tùy chỉnh renderer cho header
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        // Tùy chỉnh renderer cho nội dung
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        setDefaultRenderer(Object.class, centerRenderer);
        
        // Thêm hiệu ứng hover
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                int row = rowAtPoint(p);
                if (row != hoveredRow) {
                    hoveredRow = row;
                    repaint();
                }
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRow = -1;
                repaint();
            }
        });
        
        // Nếu bạn muốn bảng không thể chỉnh sửa theo mặc định:
        setDefaultEditor(Object.class, null);
        
        // Tùy chỉnh border cho bảng
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    
    // Ghi đè phương thức prepareRenderer để áp dụng màu nền xen kẽ và hiệu ứng hover
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        
        if (c instanceof JComponent) {
            JComponent jc = (JComponent) c;
            
            // Thêm padding cho nội dung
            jc.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
            
            // Áp dụng màu nền dựa trên trạng thái của dòng
            if (isRowSelected(row)) {
                jc.setBackground(selectionColor);
            } else if (row == hoveredRow) {
                jc.setBackground(hoverColor);
            } else {
                jc.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
            }
            
            // Áp dụng bo tròn góc nếu được bật
            if (roundedCorners) {
                // Chỉ áp dụng bo tròn cho các ô ở cạnh
                boolean isFirstColumn = column == 0;
                boolean isLastColumn = column == getColumnCount() - 1;
                boolean isFirstRow = row == 0;
                boolean isLastRow = row == getRowCount() - 1;
                
                if (isFirstColumn && isFirstRow) {
                    // Góc trên bên trái
                    jc.setBorder(new EmptyBorder(5, 10, 2, 5));
                } else if (isLastColumn && isFirstRow) {
                    // Góc trên bên phải
                    jc.setBorder(new EmptyBorder(5, 5, 2, 10));
                } else if (isFirstColumn && isLastRow) {
                    // Góc dưới bên trái
                    jc.setBorder(new EmptyBorder(2, 10, 5, 5));
                } else if (isLastColumn && isLastRow) {
                    // Góc dưới bên phải
                    jc.setBorder(new EmptyBorder(2, 5, 5, 10));
                } else if (isFirstColumn) {
                    // Cạnh trái
                    jc.setBorder(new EmptyBorder(2, 10, 2, 5));
                } else if (isLastColumn) {
                    // Cạnh phải
                    jc.setBorder(new EmptyBorder(2, 5, 2, 10));
                } else if (isFirstRow) {
                    // Cạnh trên
                    jc.setBorder(new EmptyBorder(5, 5, 2, 5));
                } else if (isLastRow) {
                    // Cạnh dưới
                    jc.setBorder(new EmptyBorder(2, 5, 5, 5));
                } else {
                    // Các ô ở giữa
                    jc.setBorder(new EmptyBorder(2, 5, 2, 5));
                }
            }
        }
        
        return c;
    }
    
    // Các phương thức setter để tùy chỉnh màu sắc và giao diện
    public void setEvenRowColor(Color evenRowColor) {
        this.evenRowColor = evenRowColor;
        repaint();
    }
    
    public void setOddRowColor(Color oddRowColor) {
        this.oddRowColor = oddRowColor;
        repaint();
    }
    
    public void setSelectionColor(Color selectionColor) {
        this.selectionColor = selectionColor;
        setSelectionBackground(selectionColor);
        repaint();
    }
    
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
        repaint();
    }
    
    @Override
    public void setGridColor(Color gridColor) {
        super.setGridColor(gridColor);
        this.gridColor = gridColor;
        repaint();
    }
    
    public void setHeaderFont(Font headerFont) {
        this.headerFont = headerFont;
        JTableHeader header = getTableHeader();
        header.setFont(headerFont);
        repaint();
    }
    
    public void setHeaderBackground(Color headerBackground) {
        this.headerBackground = headerBackground;
        JTableHeader header = getTableHeader();
        header.setBackground(headerBackground);
        repaint();
    }
    
    public void setHeaderForeground(Color headerForeground) {
        this.headerForeground = headerForeground;
        JTableHeader header = getTableHeader();
        header.setForeground(headerForeground);
        repaint();
    }
    
    public void setContentFont(Font contentFont) {
        this.contentFont = contentFont;
        setFont(contentFont);
        repaint();
    }
    
    public void setContentForeground(Color contentForeground) {
        this.contentForeground = contentForeground;
        setForeground(contentForeground);
        repaint();
    }
    
    public void setRoundedCorners(boolean roundedCorners) {
        this.roundedCorners = roundedCorners;
        repaint();
    }
    
    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }
    
    // Ghi đè phương thức paint để vẽ bảng với góc bo tròn
    @Override
    public void paint(Graphics g) {
        if (roundedCorners) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2d.dispose();
        }
        super.paint(g);
    }

    public static void main(String[] args) {
        // Tạo frame để demo
        JFrame frame = new JFrame("Custom Table Demo - Modern Style");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.getContentPane().setBackground(new Color(245, 245, 250));

        // 1. Cách 1: Tạo bảng với dữ liệu và tiêu đề có sẵn
        Object[] columnNames = {"ID", "Họ Tên", "Tuổi", "Email", "Địa chỉ"};
        Object[][] data = {
                {"1", "Nguyễn Văn A", 25, "nguyenvana@email.com", "Hà Nội"},
                {"2", "Trần Thị B", 30, "tranthib@email.com", "TP.HCM"},
                {"3", "Lê Văn C", 28, "levanc@email.com", "Đà Nẵng"},
                {"4", "Phạm Thị D", 22, "phamthid@email.com", "Cần Thơ"},
                {"5", "Hoàng Văn E", 35, "hoangvane@email.com", "Hải Phòng"},
                {"6", "Vũ Thị F", 27, "vuthif@email.com", "Hải Dương"},
                {"7", "Đặng Văn G", 31, "dangvang@email.com", "Quảng Ninh"}
        };

        CustomTable table1 = new CustomTable(data, columnNames);

        // 2. Cách 2: Tạo bảng với TableModel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã SP");
        model.addColumn("Tên Sản Phẩm");
        model.addColumn("Giá");
        model.addColumn("Số lượng");

        model.addRow(new Object[]{"SP001", "Laptop Dell XPS 13", 25000000, 10});
        model.addRow(new Object[]{"SP002", "iPhone 14 Pro Max", 28000000, 20});
        model.addRow(new Object[]{"SP003", "iPad Pro M2", 22000000, 15});
        model.addRow(new Object[]{"SP004", "Samsung Galaxy S23", 18000000, 25});
        model.addRow(new Object[]{"SP005", "Tai nghe AirPods Pro", 5000000, 30});

        CustomTable table2 = new CustomTable(model);
        
        // 3. Tùy chỉnh giao diện bảng (đã được mở comment)
        table2.setEvenRowColor(new Color(250, 250, 250));  // Màu cho dòng chẵn
        table2.setOddRowColor(new Color(240, 245, 255));   // Màu cho dòng lẻ
        table2.setSelectionColor(new Color(70, 130, 220, 70)); // Màu khi chọn dòng
        table2.setHoverColor(new Color(220, 240, 255));    // Màu khi hover
        table2.setGridColor(new Color(230, 230, 230));     // Màu đường kẻ

        // Tùy chỉnh header
        table2.setHeaderBackground(new Color(50, 120, 220));  // Màu nền header
        table2.setHeaderForeground(Color.WHITE);           // Màu chữ header
        table2.setHeaderFont(new Font("SansSerif", Font.BOLD, 14)); // Font cho header
        
        // Tùy chỉnh nội dung
        table2.setContentFont(new Font("SansSerif", Font.PLAIN, 13)); // Font cho nội dung
        table2.setContentForeground(new Color(60, 60, 60)); // Màu chữ nội dung
        
        // Bật tính năng bo tròn góc
        table2.setRoundedCorners(true);
        table2.setCornerRadius(8);

        // 4. Đặt bảng vào JScrollPane với viền và padding
        JScrollPane scrollPane = new JScrollPane(table2);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        scrollPane.setBackground(new Color(245, 245, 250));
        scrollPane.getViewport().setBackground(new Color(245, 245, 250));

        // 5. Thêm vào frame và hiển thị
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
        frame.setVisible(true);
    }
}

