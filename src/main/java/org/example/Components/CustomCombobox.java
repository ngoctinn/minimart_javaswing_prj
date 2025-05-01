package org.example.Components;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.util.Vector;

/**
 * CustomCombobox là một lớp kế thừa từ JComboBox với giao diện tùy chỉnh,
 * bao gồm placeholder và bo góc, sử dụng FlatLaf.
 */
public class CustomCombobox<E> extends JComboBox<E> {

    private String placeholder = "";
    // Sử dụng màu chữ bị vô hiệu hóa của Label làm màu placeholder cho nhất quán
    private Color placeholderColor = UIManager.getColor("Label.disabledForeground");

    /**
     * Constructor mặc định.
     */
    public CustomCombobox() {
        super();
        init();
    }

    /**
     * Constructor với ComboBoxModel.
     * @param aModel Model dữ liệu cho combobox.
     */
    public CustomCombobox(ComboBoxModel<E> aModel) {
        super(aModel);
        init();
    }

    /**
     * Constructor với mảng các mục.
     * @param items Mảng các mục để hiển thị.
     */
    public CustomCombobox(E[] items) {
        super(items);
        init();
    }

    /**
     * Constructor với Vector các mục.
     * @param items Vector các mục để hiển thị.
     */
    public CustomCombobox(Vector<E> items) {
        super(items);
        init();
    }

    /**
     * Khởi tạo các thuộc tính giao diện.
     */
    private void init() {
        // Sử dụng thuộc tính FlatLaf để bo góc (arc: bán kính bo góc)
        putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        // Sử dụng renderer tùy chỉnh để hiển thị placeholder
        setRenderer(new PlaceholderRenderer());
        // Thiết lập kích thước mặc định tương tự CustomTextField
        setPreferredSize(new Dimension(220, 32));
        // Đảm bảo không có mục nào được chọn ban đầu để placeholder hiển thị
        setSelectedIndex(-1);
    }

    /**
     * Lấy văn bản placeholder.
     * @return Văn bản placeholder.
     */
    public String getPlaceholder() {
        return placeholder;
    }

    /**
     * Thiết lập văn bản placeholder.
     * @param placeholder Văn bản placeholder mới.
     */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        // Cập nhật lại giao diện để hiển thị placeholder nếu cần
        repaint();
    }

    /**
     * Renderer tùy chỉnh để hiển thị placeholder khi không có mục nào được chọn.
     */
    private class PlaceholderRenderer extends BasicComboBoxRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            // Gọi phương thức của lớp cha để xử lý các trạng thái cơ bản (chọn, hover, ...)
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Nếu không có mục nào được chọn (index = -1) và có placeholder
            if (value == null && index == -1 && placeholder != null && !placeholder.isEmpty()) {
                setText(placeholder);
                setForeground(placeholderColor); // Đặt màu cho placeholder
            } else {
                // Đặt màu chữ bình thường cho các mục khác
                // Màu chữ khi được chọn hoặc không được chọn lấy từ UIManager cho nhất quán
                setForeground(isSelected ? UIManager.getColor("ComboBox.selectionForeground") : UIManager.getColor("ComboBox.foreground"));
                // Xử lý trường hợp mục là null (nếu có)
                 if (value == null) {
                     setText(""); // Hiển thị chuỗi rỗng cho mục null
                 }
            }

            // Thêm padding tương tự CustomTextField để nhất quán về giao diện
            setBorder(new EmptyBorder(8, 12, 8, 12));

            return this;
        }
    }

    /**
     * Hàm main để kiểm thử CustomCombobox.
     */
    public static void main(String[] args) {
        // Áp dụng giao diện FlatLaf Light theme
        try {
            // Thay đổi từ FlatDarkLaf sang FlatLightLaf
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Failed to initialize LaF");
            e.printStackTrace();
        }

        // Tạo cửa sổ JFrame
        JFrame frame = new JFrame("Custom Combobox Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        // frame.getContentPane().setBackground(UIManager.getColor("Panel.background")); // Đặt nền nếu cần

        // Dữ liệu mẫu
        String[] countries = {"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Australia", "Austria"};

        // Tạo CustomCombobox với placeholder
        CustomCombobox<String> comboBox1 = new CustomCombobox<>(countries);
        comboBox1.setPlaceholder("- Select -");
        // comboBox1.setSelectedIndex(-1); // Đã gọi trong init()

        // Tạo CustomCombobox khác
        CustomCombobox<String> comboBox2 = new CustomCombobox<>();
        comboBox2.addItem("Option 1");
        comboBox2.addItem("Option 2");
        comboBox2.addItem("Option 3");
        comboBox2.setPlaceholder("Choose an option");
        // comboBox2.setSelectedIndex(-1); // Đã gọi trong init()

        // Thêm label và combobox vào frame (label nằm ngoài combobox theo thiết kế)
        frame.add(new JLabel("Choose a country:"));
        frame.add(comboBox1);
        frame.add(new JLabel("Another choice:"));
        frame.add(comboBox2);

        // Hiển thị cửa sổ
        frame.pack(); // Tự động điều chỉnh kích thước cửa sổ
        frame.setLocationRelativeTo(null); // Căn giữa cửa sổ
        frame.setVisible(true);
    }
}