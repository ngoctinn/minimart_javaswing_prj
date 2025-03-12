package org.example.Test;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.util.function.BiConsumer;

public class ClosableTabsExample {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
            //test
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FlatLaf Closable Tabs");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 350);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.putClientProperty("JTabbedPane.tabClosable", true);

            // ✅ Định nghĩa callback hợp lệ cho sự kiện đóng tab
            tabbedPane.putClientProperty("JTabbedPane.tabCloseCallback", (BiConsumer<JTabbedPane, Integer>) (pane, tabIndex) -> {
                pane.remove(tabIndex);
            });

            // Thêm 3 tab ban đầu
            for (int i = 1; i <= 3; i++) {
                addNewTab(tabbedPane, "Tab " + i);
            }

            // Panel chứa tabbedPane và nút "New Tab"
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(tabbedPane, BorderLayout.CENTER);

            // Nút "New Tab"
            JButton newTabButton = new JButton("New Tab");
            newTabButton.addActionListener(e -> addNewTab(tabbedPane, "New Tab"));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(newTabButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);

            frame.add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void addNewTab(JTabbedPane tabbedPane, String title) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Nội dung của " + title));

        tabbedPane.addTab(title, panel);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1); // Chuyển ngay đến tab mới
    }
}
