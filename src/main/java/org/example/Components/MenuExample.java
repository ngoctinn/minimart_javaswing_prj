package org.example.Components;

import javax.swing.*;
import java.awt.event.*;

public class MenuExample extends JFrame {

    public MenuExample() {
        // Thiết lập JFrame
        setTitle("Ví dụ về JMenu trong Java Swing");
        setSize(400, 300);
        setLocationRelativeTo(null); // Hiển thị cửa sổ giữa màn hình
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Tạo JMenuBar
        JMenuBar menuBar = new JMenuBar();

        // Add các button vào menu
        CustomButton btn = new CustomButton("Button 1");
        CustomButton btn2 = new CustomButton("Button 2");
        CustomButton btn3 = new CustomButton("Button 3");
        menuBar.add(btn);
        menuBar.add(btn2);
        menuBar.add(btn3);




        // Tạo JMenu (ví dụ: File, Edit)
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        // Tạo các JMenuItem cho menu File
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Thêm các JMenuItem vào JMenu "File"
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator(); // Thêm đường phân cách
        fileMenu.add(exitItem);

        // Thêm ActionListener cho JMenuItem "Exit" để đóng ứng dụng khi được chọn
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Thêm các JMenu vào JMenuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        // Thiết lập JMenuBar cho JFrame
        setJMenuBar(menuBar);

        // (Tùy chọn) Thêm các thành phần khác vào JFrame
        JPanel panel = new JPanel();
        add(panel);
    }

    public static void main(String[] args) {
        // Sử dụng SwingUtilities.invokeLater để đảm bảo việc khởi tạo GUI chạy trên Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MenuExample().setVisible(true);
            }
        });
    }
}
