package org.example;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.example.Components.CustomButton;

import javax.swing.*;
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
            JTable table = new JTable(new Object[][]{{"A", "B"}, {"C", "D"}}, new Object[]{"Col 1", "Col 2"});
            JScrollPane tableScrollPane = new JScrollPane(table);

            // Thêm components vào frame
            frame.setJMenuBar(menuBar);
            frame.add(button);
            frame.add(customButton);
            frame.add(redButton);
            frame.add(greenButton);
            frame.add(checkBox);
            frame.add(radioButton);
            frame.add(textField);
            frame.add(passwordField);
            frame.add(new JScrollPane(textArea));
            frame.add(comboBox);
            frame.add(spinner);
            frame.add(slider);
            frame.add(progressBar);
            frame.add(tableScrollPane);

            // Hiển thị JFrame
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
