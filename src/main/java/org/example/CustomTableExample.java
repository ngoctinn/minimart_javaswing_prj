package org.example;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class CustomTableExample extends JFrame {
    public CustomTableExample() {
        setTitle("Custom Table Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        // Tạo tiêu đề cột
        String[] columnNames = {"Mã hóa đơn", "Thời gian", "Mã trả hàng", "Khách hàng", "Tổng tiền hàng", "Giảm giá", "Khách đã trả"};

        // Dữ liệu mẫu
        Object[][] data = {
            {"HD000046", "17/02/2025 23:36", "", "Tuấn - Hà Nội", 2580000, 0, 2580000},
            {"HD000045", "16/02/2025 23:35", "", "Phạm Thu Hương", 55000, 0, 55000},
            {"HD000044", "15/02/2025 23:34", "", "Tuấn - Hà Nội", 195000, 0, 195000},
            {"HD000043", "14/02/2025 23:33", "", "Anh Hoàng - Sài Gòn", 145000, 0, 145000},
            {"HD000042", "13/02/2025 23:32", "", "Anh Hoàng - Sài Gòn", 150000, 0, 150000},
            {"HD000041", "12/02/2025 23:32", "", "Tuấn - Hà Nội", 135000, 0, 135000},
            {"HD000040", "11/02/2025 23:31", "", "Anh Hoàng - Sài Gòn", 190000, 0, 190000},
            {"HD000039", "10/02/2025 23:30", "", "Phạm Thu Hương", 55000, 0, 55000}
        };

        // Tạo model bảng với định dạng số
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column >= 4) return Integer.class; // Cột số
                return String.class; // Cột văn bản
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Căn chỉnh văn bản trong cột số
        DefaultTableCellRenderer rightAlignRenderer = new DefaultTableCellRenderer();
        rightAlignRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(4).setCellRenderer(rightAlignRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(rightAlignRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(rightAlignRenderer);

        // Đưa bảng vào JScrollPane để có thanh cuộn
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CustomTableExample::new);
    }
}
