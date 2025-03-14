package org.example.GUI;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.Components.CustomButton;
import org.example.Components.PlaceholderTextField;
import org.example.Components.RoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BanHangPanel extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;

    // Top panel components
    private PlaceholderTextField searchField;
    private CustomButton searchButton;
    private CustomButton addTabButton;
    private CustomButton refreshButton;

    // Bottom panel components
    private JTabbedPane tabbedPane;
    private JPanel productsPanel;
    private int tabCounter = 1;

    public BanHangPanel() {
        initGUI();
    }

    private void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanelLeft();
        setupBottomPanelRight();

        // Add panels to main panel
        this.add(topPanel);
        this.add(bottomPanel);
        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);
    }

    private void setupMainPanel() {
        // Set up layout and background for main panel
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
        bottomPanel.setPreferredSize(new Dimension(1270, 700
        ));
        bottomPanelLeft.setPreferredSize(new Dimension(1270 * 60 / 100, 900));
        bottomPanelRight.setPreferredSize(new Dimension(1270 * 40 / 100, 900));

        // Set layouts
        topPanel.setLayout(null);
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new BorderLayout());
        bottomPanelRight.setLayout(new BorderLayout());
    }

    private void setupTopPanel() {
        // Search field
        searchField = new PlaceholderTextField("Tìm kiếm khách hàng...");
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchField.setBounds(20, 12, 300, 30);
        topPanel.add(searchField);

        // Search button
        searchButton = new CustomButton("Tìm");
        searchButton.setBounds(330, 12, 70, 30);
        topPanel.add(searchButton);

        // Add new tab button
        FlatSVGIcon addIcon = new FlatSVGIcon("Icons/cong.svg", 16, 16);
        addTabButton = new CustomButton("Thêm hóa đơn", addIcon);
        addTabButton.setBounds(1090, 12, 150, 30);
        addTabButton.addActionListener(e -> addNewTab());
        topPanel.add(addTabButton);

    }

    private void setupBottomPanelLeft() {
        // Create closable tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.putClientProperty("JTabbedPane.tabClosable", true);
        tabbedPane.putClientProperty("JTabbedPane.tabCloseCallback",
                (java.util.function.BiConsumer<JTabbedPane, Integer>) (tabPane, tabIndex) -> {
                    if (tabIndex > 0) { // Don't close the first tab
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
        bottomPanelRight.setLayout(new BorderLayout());

        // Tạo panel chứa ô tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 25));
        searchPanel.setBackground(Color.WHITE);

        // Ô nhập tìm kiếm
        PlaceholderTextField searchField = new PlaceholderTextField("Tìm kiếm sản phẩm...");
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        searchPanel.add(searchField, BorderLayout.CENTER);
        bottomPanelRight.add(searchPanel, BorderLayout.NORTH);

        // Panel chứa danh sách sản phẩm
        productsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        productsPanel.setBackground(Color.WHITE);
        productsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thêm sản phẩm vào danh sách
        addSampleProducts();

        // Bọc productsPanel trong JScrollPane
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        bottomPanelRight.add(scrollPane, BorderLayout.CENTER);

        // Thêm nút thanh toán ở dưới cùng
        CustomButton btnThanhToan = new CustomButton("Thanh toán");
        btnThanhToan.setPreferredSize(new Dimension(100, 45));

        // Thêm panel chứa nút thanh toán vào bottomPanelRight
        JPanel thanhToanPanel = new JPanel(new BorderLayout());
        thanhToanPanel.add(btnThanhToan, BorderLayout.CENTER);
        thanhToanPanel.setBackground(Color.WHITE);
        thanhToanPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        bottomPanelRight.add(thanhToanPanel, BorderLayout.SOUTH);
    }



    private void addSampleProducts() {
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
        products.add("Trà đào");
        products.add("Nước suối");
        products.add("Bia");
        products.add("Rượu");
        products.add("Nước ngọt");
        products.add("Nước lọc");
        products.add("Nước ép trái cây");

        for (String product : products) {
            JButton productButton = new JButton(product);
            productButton.setPreferredSize(new Dimension(100, 200));
            productButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "Đã chọn: " + product);
            });
            productsPanel.add(productButton);
        }
    }

    // Helper methods
    private JPanel createTitledPanel(String title, int width, int height) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                title,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 15),
                Color.BLACK
        ));
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }

    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new BanHangPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}