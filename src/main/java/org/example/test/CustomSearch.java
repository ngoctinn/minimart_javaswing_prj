import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomSearch extends JPanel {
    private JTextField searchField;
    private JComboBox<String> comboBox;

    public CustomSearch() {
        setLayout(new BorderLayout(5, 5));
        setPreferredSize(new Dimension(300, 35));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        setBackground(Color.WHITE);

        // Icon kính lúp
        JLabel searchIcon = new JLabel("\uD83D\uDD0D"); // Unicode kính lúp 🔍
        searchIcon.setBorder(new EmptyBorder(0, 5, 0, 5));

        // Text field nhập tìm kiếm
        searchField = new JTextField("Theo mã, tên hàng");
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(null);
        searchField.setOpaque(false);

        // Xử lý placeholder (hiển thị mờ, nhập vào thì ẩn)
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Theo mã, tên hàng")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Theo mã, tên hàng");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        // Dropdown chỉ hiển thị mũi tên, không hiển thị danh sách
        comboBox = new JComboBox<>();
        comboBox.setFocusable(false);
        comboBox.setBorder(null);
        comboBox.setOpaque(false);

        // Tùy chỉnh JComboBox để chỉ hiện mũi tên
        comboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("\u25BC"); // Unicode mũi tên ▼
                button.setBorder(null);
                button.setContentAreaFilled(false);
                return button;
            }
        });

        // Panel chứa text + icon
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
        textPanel.setOpaque(false);
        textPanel.add(searchIcon, BorderLayout.WEST);
        textPanel.add(searchField, BorderLayout.CENTER);

        // Thêm vào panel chính
        add(textPanel, BorderLayout.CENTER);
        add(comboBox, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom Search Box");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 100);
            frame.setLayout(new FlowLayout());

            CustomSearch searchBox = new CustomSearch();
            frame.add(searchBox);

            frame.setVisible(true);
        });
    }
}
//cách để gọi hàm CustomSearch trong class huhu
//CustomSearch searchField = new CustomSearch();
//searchPanel.add(searchField, BorderLayout.CENTER);