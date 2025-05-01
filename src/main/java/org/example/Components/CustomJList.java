package org.example.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * CustomJList là một lớp kế thừa từ JList với giao diện tùy chỉnh và ngăn cách đẹp giữa các item.
 */
public class CustomJList<E> extends JList<E> {

    public CustomJList() {
        super();
        init();
    }

    public CustomJList(ListModel<E> dataModel) {
        super(dataModel);
        init();
    }

    public CustomJList(E[] listData) {
        super(listData);
        init();
    }

    private void init() {
        setCellRenderer(new CustomListCellRenderer<>());
        setOpaque(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Renderer hiện đại cho từng item trong JList.
     */
    private static class CustomListCellRenderer<E> extends JPanel implements ListCellRenderer<E> {
        private final JLabel label;

        public CustomListCellRenderer() {
            setLayout(new BorderLayout());
            setOpaque(true);
            label = new JLabel();
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            add(label, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends E> list, E value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            label.setText(value != null ? value.toString() : "");

            // Bo góc FlatLaf
            putClientProperty("FlatLaf.style", "arc: 10");

            // Màu nền & chữ
            if (isSelected) {
                setBackground(UIManager.getColor("List.selectionBackground"));
                label.setForeground(UIManager.getColor("List.selectionForeground"));
            } else {
                setBackground(UIManager.getColor("List.background"));
                label.setForeground(UIManager.getColor("List.foreground"));
            }

            // Thêm đường viền dưới (ngăn cách giữa các item)
            Color separatorColor = new Color(220, 220, 220); // Xám nhạt
            int bottomBorder = (index < list.getModel().getSize() - 1) ? 1 : 0;

            setBorder(BorderFactory.createCompoundBorder(
                    new MatteBorder(0, 0, bottomBorder, 0, separatorColor),
                    new EmptyBorder(8, 12, 8, 12)
            ));

            return this;
        }
    }

    /**
     * Hàm main để kiểm thử CustomJList.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Failed to initialize LaF");
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Custom JList Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        String[] data = {"📦 Mục 1", "📝 Mục 2", "📄 Mục 3 dài hơn", "🔧 Mục 4", "📁 Mục 5"};
        CustomJList<String> customList = new CustomJList<>(data);

        JScrollPane scrollPane = new JScrollPane(customList);
        scrollPane.putClientProperty("FlatLaf.style", "arc: 10");
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(320, 240);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
