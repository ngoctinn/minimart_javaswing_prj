package org.example.GUI.Panels;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

/**
 * Class để test giao diện tongQuanPanel
 */
public class TestTongQuanPanel {
    public static void main(String[] args) {
        try {
            // Thiết lập look and feel
            UIManager.setLookAndFeel(new FlatLightLaf());
            
            // Tạo JFrame
            JFrame frame = new JFrame("Test Tổng Quan Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null);
            
            // Thêm tongQuanPanel vào frame
            tongQuanPanel panel = new tongQuanPanel();
            frame.add(panel);
            
            // Hiển thị frame
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
