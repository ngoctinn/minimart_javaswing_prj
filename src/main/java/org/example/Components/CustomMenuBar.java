package org.example.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class CustomMenuBar {
    private static final Color HOVER_COLOR = new Color(230, 240, 255);
    private static final Color SELECTED_COLOR = new Color(0, 102, 204);
    private JButton lastSelectedButton = null;
    private JPanel mainContent;
    private CardLayout cardLayout;
    private Map<String, JPanel> panelMap;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomMenuBar().createAndShowGUI());
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Custom Menu Bar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Tạo menu panel
        JPanel menuPanel = createMenuPanel();
        frame.add(menuPanel, BorderLayout.NORTH);

        // Tạo panel chính chứa các trang
        mainContent = new JPanel();
        cardLayout = new CardLayout();
        mainContent.setLayout(cardLayout);
        frame.add(mainContent, BorderLayout.CENTER);

        // Khởi tạo các panel và lưu vào Map
        panelMap = new HashMap<>();
        panelMap.put("Tổng quan", new OverviewPanel());
        panelMap.put("Hàng hóa", new GoodsPanel());
        panelMap.put("Giao dịch", new TransactionPanel());
        panelMap.put("Đối tác", new PartnersPanel());
        panelMap.put("Nhân viên", new EmployeesPanel());
        panelMap.put("Số quỹ", new FundPanel());
        panelMap.put("Báo cáo", new ReportPanel());
        panelMap.put("Bán Online", new OnlineSalesPanel());

        // Thêm các panel vào mainContent
        for (Map.Entry<String, JPanel> entry : panelMap.entrySet()) {
            mainContent.add(entry.getKey(), entry.getValue());
        }

        frame.setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        String[] menuItems = {"Tổng quan", "Hàng hóa", "Giao dịch", "Đối tác",
                "Nhân viên", "Số quỹ", "Báo cáo", "Bán Online"};

        for (String item : menuItems) {
            JButton button = createMenuButton(item);
            panel.add(button);
            panel.add(Box.createHorizontalStrut(10)); // Khoảng cách giữa các nút
        }

        return panel;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(8, 12, 8, 12));

        // Hiệu ứng hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button != lastSelectedButton) {
                    button.setBackground(HOVER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button != lastSelectedButton) {
                    button.setBackground(Color.WHITE);
                }
            }
        });

        // Hiệu ứng toggle khi click để chuyển panel
        button.addActionListener(e -> {
            if (lastSelectedButton != null) {
                lastSelectedButton.setBackground(Color.WHITE);
                lastSelectedButton.setForeground(Color.BLACK);
            }
            button.setBackground(SELECTED_COLOR);
            button.setForeground(Color.WHITE);
            lastSelectedButton = button;

            // Chuyển panel
            cardLayout.show(mainContent, text);
        });

        return button;
    }
}
