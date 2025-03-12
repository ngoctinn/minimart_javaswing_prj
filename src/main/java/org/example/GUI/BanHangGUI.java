package org.example.GUI;

import org.example.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.extras.components.FlatTabbedPane;
import com.formdev.flatlaf.extras.components.FlatTextField;
import com.formdev.flatlaf.extras.components.FlatButton;
import java.util.ArrayList;
import java.util.List;

public class BanHangGUI extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;
    private FlatTabbedPane tabbedPane;
    private FlatTextField searchField;
    private JPanel productsPanel;
    private int tabCounter = 1;
    private JButton addTabButton;

    public BanHangGUI() {
        initGUI();
    }

    private void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanelLeft();
        setupBottomPanelRight();
    }

    private void setupMainPanel() {
        this.setLayout(new FlowLayout());
        this.setBackground(new Color(225, 225, 225));
        this.setVisible(true);
    }

    private void createPanels() {
        // Create sub-panels
        topPanel = new RoundedPanel(20);
        bottomPanel = new RoundedPanel(20);
        bottomPanelLeft = new RoundedPanel(20);
        bottomPanelRight = new RoundedPanel(20);

        // Set background colors
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(new Color(225, 225, 225));
        bottomPanelLeft.setBackground(Color.WHITE);
        bottomPanelRight.setBackground(Color.WHITE);

        // Set panel sizes
        topPanel.setPreferredSize(new Dimension(1270, 50));
        bottomPanel.setPreferredSize(new Dimension(1270, 900));
        bottomPanelLeft.setPreferredSize(new Dimension(1270 * 60 / 100, 900));
        bottomPanelRight.setPreferredSize(new Dimension(1270 * 40 / 100, 900));

        // Set layouts
        topPanel.setLayout(null);
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new BorderLayout());
        bottomPanelRight.setLayout(new BorderLayout());

        add(topPanel);
        add(bottomPanel);
        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);
    }

    private void setupTopPanel() {
        // Add search field
        searchField = new FlatTextField();
        searchField.setPlaceholderText("Tìm kiếm sản phẩm...");
        searchField.setBounds(20, 10, 300, 30);

        // Add search button
        FlatButton searchButton = new FlatButton();
        searchButton.setText("Tìm kiếm");
        searchButton.setBounds(330, 10, 100, 30);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Đang tìm kiếm: " + searchField.getText());
            }
        });

        // Add new tab button to top panel
        addTabButton = new FlatButton();
        addTabButton.setText("+ Thêm hóa đơn mới");
        addTabButton.setBounds(450, 10, 150, 30);
        addTabButton.addActionListener(e -> addNewTab());

        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(addTabButton);
    }

    private void setupBottomPanelLeft() {
        // Create closable tabbed pane
        tabbedPane = new FlatTabbedPane();
        tabbedPane.putClientProperty("JTabbedPane.tabClosable", true);
        tabbedPane.putClientProperty("JTabbedPane.tabCloseCallback",
                (java.util.function.BiConsumer<JTabbedPane, Integer>) (tabPane, tabIndex) -> {
                    if (tabIndex > 0) { // Don't close the first tab
                        // Hiển thị hộp thoại xác nhận trước khi đóng tab
                        int confirm = JOptionPane.showConfirmDialog(
                                this,
                                "Bạn có chắc muốn đóng hóa đơn này?",
                                "Xác nhận đóng",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            tabPane.removeTabAt(tabIndex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Không thể đóng hóa đơn mặc định!",
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                });

        // Add default tab
        JPanel defaultPanel = createInvoicePanel("Hóa đơn mặc định");
        tabbedPane.addTab("Hóa đơn 1", defaultPanel);

        bottomPanelLeft.add(tabbedPane, BorderLayout.CENTER);
    }

    private void addNewTab() {
        tabCounter++;
        String tabTitle = "Hóa đơn " + tabCounter;
        JPanel newPanel = createInvoicePanel("Hóa đơn mới #" + tabCounter);
        tabbedPane.addTab(tabTitle, newPanel);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);

        // Hiển thị thông báo nhỏ
        JOptionPane.showMessageDialog(
                this,
                "Đã tạo hóa đơn mới: " + tabTitle,
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private JPanel createInvoicePanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private void setupBottomPanelRight() {
        // Create a panel for product buttons with grid layout
        productsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        productsPanel.setBackground(Color.WHITE);

        // Add some sample product buttons
        List<String> products = new ArrayList<>();
        products.add("Cà phê");
        products.add("Trà sữa");
        products.add("Nước ép");
        products.add("Bánh mì");
        products.add("Sandwich");
        products.add("Bánh ngọt");
        products.add("Snack");
        products.add("Kem");
        products.add("Sữa chua");

        for (String product : products) {
            JButton productButton = new JButton(product);
            productButton.setPreferredSize(new Dimension(120, 80));
            productButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "Đã chọn: " + product);
            });
            productsPanel.add(productButton);
        }

        // Add scroll pane for products
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Add title for products section
        JLabel titleLabel = new JLabel("Danh sách sản phẩm");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bottomPanelRight.add(titleLabel, BorderLayout.NORTH);
        bottomPanelRight.add(scrollPane, BorderLayout.CENTER);
    }

    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.add(new BanHangGUI());
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
