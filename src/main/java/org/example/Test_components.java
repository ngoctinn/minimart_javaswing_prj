package org.example;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.example.Components.CustomButton;
import org.example.Components.CustomTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Test_components {
    public static void main(String[] args) {
        // Thiết lập Look and Feel của FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tạo JFrame chính
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FlatLaf Components Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 500);
            frame.setFont(new Font("Arial", Font.PLAIN, 14));
            frame.setLayout(new FlowLayout());

            // Tạo JMenuBar và CustomMenu
            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenu editMenu = new JMenu("Edit");

            // Thêm các mục vào menu
            fileMenu.add(new JMenuItem("Open"));
            fileMenu.add(new JMenuItem("Save"));
            fileMenu.add(new JMenuItem("Exit"));

            editMenu.add(new JMenuItem("Cut"));
            editMenu.add(new JMenuItem("Copy"));
            editMenu.add(new JMenuItem("Paste"));

            // Thêm menu vào menuBar
            menuBar.add(fileMenu);
            menuBar.add(editMenu);

            // Các components cơ bản
            JButton button = new JButton("FlatLaf Button");
            button.setBackground(new Color(0, 120, 215));
            button.setForeground(Color.WHITE);

            // Các components khác
            CustomButton customButton = new CustomButton("Custom Button", new ImageIcon("src/main/resources/delete (1).png"));

            // button màu đỏ
            CustomButton redButton = new CustomButton("Red Button", new ImageIcon("src/main/resources/delete (1).png"));
            redButton.setButtonColor(
                    new Color(231, 76, 60), // Normal
                    new Color(236, 112, 99), // Hover
                    new Color(192, 57, 43)    // Pressed
            );

            // Button màu xanh lá cây
            CustomButton greenButton = new CustomButton("Green Button", new ImageIcon("src/main/resources/delete (1).png"));
            greenButton.setButtonColor(
                    new Color(46, 204, 113), // Normal
                    new Color(88, 214, 141), // Hover
                    new Color(40, 180, 99)    // Pressed
            );

            // button màu xám
            CustomButton grayButton = new CustomButton("Gray Button", new ImageIcon("src/main/resources/delete (1).png"));
            grayButton.setButtonColor(
                    new Color(120, 120, 120), // Normal
                    new Color(160, 160, 160), // Hover
                    new Color(80, 80, 80)    // Pressed
            );
            // sử lý sự kiện khi click vào button
            grayButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, "You clicked the Gray Button!");
            });

            // button màu trắng viền đen, chữ đen, hover màu xanh, press màu xám
            CustomButton whiteButton = new CustomButton("White Button", new ImageIcon("src/main/resources/delete (1).png"));
            whiteButton.setButtonColor(
                    new Color(255, 255, 255), // Normal
                    new Color(0, 150, 255), // Hover
                    new Color(160, 160, 160)    // Pressed
            );
            whiteButton.setForeground(Color.BLACK);

            Object[] columnNames = {"ID", "Tên", "Tuổi", "Email", "Địa chỉ", "SĐT", "Bộ phận", "Chức vụ", "Lương", "Ngày gia nhập"};

            Object[][] data = {
                    {1, "Nguyễn Văn A", 25, "a@example.com", "Hà Nội", "0123456789", "Kinh doanh", "Nhân viên", 5000, "2023-01-15"},
                    {2, "Lê Thị B", 30, "b@example.com", "TP. HCM", "0987654321", "IT", "Trưởng phòng", 12000, "2022-05-20"},
                    {3, "Trần Văn C", 28, "c@example.com", "Đà Nẵng", "0369857412", "Marketing", "Nhân viên", 7000, "2021-08-10"},
                    {4, "Hoàng Minh D", 35, "d@example.com", "Hải Phòng", "0912345678", "Nhân sự", "Giám đốc", 20000, "2019-03-25"},
                    {5, "Phạm Thu E", 27, "e@example.com", "Cần Thơ", "0896543210", "Kinh doanh", "Nhân viên", 6000, "2020-07-05"},
                    {6, "Đặng Văn F", 31, "f@example.com", "Nha Trang", "0765432198", "IT", "Nhân viên", 8000, "2021-11-30"},
                    {7, "Ngô Thị G", 29, "g@example.com", "Huế", "0345678921", "Tài chính", "Trưởng phòng", 15000, "2022-02-18"},
                    {8, "Lý Minh H", 26, "h@example.com", "Bình Dương", "0976543214", "Marketing", "Nhân viên", 7500, "2023-06-12"},
                    {9, "Vũ Văn I", 40, "i@example.com", "Đồng Nai", "0887654321", "Nhân sự", "Phó giám đốc", 18000, "2018-09-29"},
                    {10, "Tô Thanh J", 33, "j@example.com", "Quảng Ninh", "0909876543", "Kế toán", "Nhân viên", 7200, "2020-12-03"}
            };


// Tạo bảng
            CustomTable table = new CustomTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);

            // Tạo thanh menu
            JMenu menu = new JMenu("Menu");
            menu.add(new JMenuItem("Item 1"));
            menu.add(new JMenuItem("Item 2"));
            menu.add(new JMenuItem("Item 3"));
            menu.add(new JMenuItem("Item 4"));
            menu.add(new JMenuItem("Item 5"));

            menu.add(new JMenuItem("Item 6"));
            menu.add(new JMenuItem("Item 7"));
            menu.add(new JMenuItem("Item 8"));


            JCheckBox checkBox = new JCheckBox("FlatLaf CheckBox");
            JRadioButton radioButton = new JRadioButton("FlatLaf RadioButton");
            JTextField textField = new JTextField("FlatLaf TextField");
            JPasswordField passwordField = new JPasswordField("password");
            JTextArea textArea = new JTextArea("FlatLaf TextArea", 3, 20);
            JComboBox<String> comboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1));
            JSlider slider = new JSlider(0, 100, 50);
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue(70);

            // tạo menu

            // Thêm components vào frame
            frame.setJMenuBar(menuBar);
            frame.add(button);
            frame.add(customButton);
            frame.add(redButton);
            frame.add(greenButton);
            frame.add(grayButton);
            frame.add(whiteButton);
            frame.add(scrollPane);
            frame.add(checkBox);
            frame.add(radioButton);
            frame.add(textField);
            frame.add(passwordField);
            frame.add(new JScrollPane(textArea));
            frame.add(comboBox);
            frame.add(spinner);
            frame.add(slider);
            frame.add(progressBar);

            // Hiển thị JFrame
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
