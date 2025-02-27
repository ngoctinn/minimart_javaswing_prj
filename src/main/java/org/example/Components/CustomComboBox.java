package org.example.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomComboBox extends JComboBox<String> {
    private String placeholder;
    private Color placeholderColor = new Color(150, 150, 150);
    private Color borderColor = new Color(66, 133, 244);
    private int borderThickness = 2;

    public CustomComboBox(String placeholder) {
        this.placeholder = placeholder;
        setModel(new PlaceholderComboBoxModel<>(placeholder));
        setRenderer(new CustomComboBoxRenderer(placeholder, placeholderColor));
        setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = super.createArrowButton();
                button.setBackground(Color.WHITE);
                button.setBorder(BorderFactory.createEmptyBorder());
                return button;
            }
        });
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setBackground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ viền
        if (isFocusOwner()) {
            g2.setColor(borderColor);
        } else {
            g2.setColor(new Color(200, 200, 200));
        }
        g2.fillRect(0, getHeight() - borderThickness, getWidth(), borderThickness);
    }

    public void setPlaceholderColor(Color color) {
        this.placeholderColor = color;
        setRenderer(new CustomComboBoxRenderer(placeholder, placeholderColor));
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public void setBorderThickness(int thickness) {
        this.borderThickness = thickness;
        repaint();
    }

    private static class PlaceholderComboBoxModel<E> extends DefaultComboBoxModel<E> {
        private E placeholder;

        public PlaceholderComboBoxModel(E placeholder) {
            this.placeholder = placeholder;
            addElement(placeholder);
        }

        @Override
        public void addElement(E element) {
            if (getSize() == 1 && getElementAt(0).equals(placeholder)) {
                removeElement(placeholder);
            }
            super.addElement(element);
        }

        @Override
        public void removeElement(Object obj) {
            super.removeElement(obj);
            if (getSize() == 0) {
                addElement(placeholder);
            }
        }
    }

    private static class CustomComboBoxRenderer extends DefaultListCellRenderer {
        private String placeholder;
        private Color placeholderColor;

        public CustomComboBoxRenderer(String placeholder, Color placeholderColor) {
            this.placeholder = placeholder;
            this.placeholderColor = placeholderColor;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value != null && value.equals(placeholder)) {
                if (index == -1) {
                    setText(placeholder);
                    setForeground(placeholderColor);
                } else {
                    setText("");
                }
            } else {
                setForeground(list.getForeground());
            }
            return c;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Custom ComboBox Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        frame.getContentPane().setBackground(Color.WHITE);

        CustomComboBox comboBox = new CustomComboBox("Select an option");
        comboBox.addItem("Option 1");
        comboBox.addItem("Option 2");
        comboBox.addItem("Option 3");
        comboBox.setPreferredSize(new Dimension(200, 35));

        CustomComboBox comboBox2 = new CustomComboBox("Choose a country");
        comboBox2.addItem("USA");
        comboBox2.addItem("Canada");
        comboBox2.addItem("UK");
        comboBox2.setPreferredSize(new Dimension(200, 35));

        comboBox.setPlaceholderColor(new Color(180, 180, 180));
        comboBox.setBorderColor(new Color(0, 150, 136));
        comboBox.setBorderThickness(2);

        frame.add(comboBox);
        frame.add(comboBox2);
        frame.setVisible(true);
    }
}