package org.example.GUI.Panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class tongQuanPanel extends JPanel {
    // UI components
    private JLabel titleLabel;
    private JLabel logoLabel;
    private JTextArea introTextArea;
    private JPanel teamInfoPanel;
    private float opacity = 0.0f; // For fade-in animation

    // Colors
    private final Color PRIMARY_COLOR = new Color(0, 102, 204); // New primary color
    private final Color BACKGROUND_COLOR = new Color(240, 242, 245); // Soft gray
    private final Color ACCENT_COLOR = new Color(255, 235, 153); // Softer yellow
    private final Color TEXT_COLOR = new Color(33, 33, 33); // Dark gray

    public tongQuanPanel() {
        initGUI();
    }

    private void initGUI() {
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Top panel (Logo + Title)
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setOpaque(false);
        logoLabel = createLogoLabel();
        topPanel.add(logoLabel, BorderLayout.WEST);
        titleLabel = createTitleLabel();
        topPanel.add(titleLabel, BorderLayout.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        add(topPanel, gbc);

        // Center panel (Introduction)
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        introTextArea = createIntroTextArea();
        JScrollPane introScrollPane = new JScrollPane(introTextArea);
        introScrollPane.setBorder(null);
        introScrollPane.setOpaque(false);
        introScrollPane.getViewport().setOpaque(false);
        centerPanel.add(introScrollPane, BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        centerPanel.setBackground(new Color(255, 255, 255, 200));
        gbc.gridy = 1;
        gbc.weighty = 0.5;
        add(centerPanel, gbc);

        // Bottom panel (Team Info)
        teamInfoPanel = createTeamInfoPanel();
        gbc.gridy = 2;
        gbc.weighty = 0.3;
        add(teamInfoPanel, gbc);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Gradient background
        GradientPaint gradient = new GradientPaint(0, 0, BACKGROUND_COLOR, 0, getHeight(), new Color(220, 230, 240));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }



    private JLabel createLogoLabel() {
        JLabel logo = new JLabel();
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Images/SGU-LOGO.png"));
            if (originalIcon.getIconWidth() == -1) {
                logo.setText("SGU Logo");
                logo.setFont(new Font("Roboto", Font.BOLD, 20));
                logo.setForeground(PRIMARY_COLOR);
            } else {
                Image img = originalIcon.getImage();
                Image resizedImg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                logo.setIcon(new ImageIcon(resizedImg));
            }
        } catch (Exception e) {
            logo.setText("SGU Logo");
            logo.setFont(new Font("Roboto", Font.BOLD, 18));
            logo.setForeground(PRIMARY_COLOR);
        }
        logo.setBorder(new EmptyBorder(10, 10, 10, 10));
        return logo;
    }

    private JLabel createTitleLabel() {
        JLabel title = new JLabel("Hệ thống Quản lý Siêu Thị Mini");
        title.setFont(new Font("Roboto", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, PRIMARY_COLOR, 0, getHeight(), new Color(0, 80, 160));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        titlePanel.add(title, BorderLayout.CENTER);

        JLabel titleContainer = new JLabel();
        titleContainer.setLayout(new BorderLayout());
        titleContainer.add(titlePanel);
        return titleContainer;
    }

    private JTextArea createIntroTextArea() {
        JTextArea intro = new JTextArea();
        intro.setText("Hệ thống Quản lý Siêu Thị Mini là giải pháp toàn diện giúp quản lý hiệu quả các hoạt động của siêu thị nhỏ. "
                + "Hệ thống cung cấp các tính năng quản lý hàng hóa, nhân viên, khách hàng, bán hàng và báo cáo thống kê.\n\n"
                + "Với giao diện thân thiện và dễ sử dụng, hệ thống giúp tối ưu hóa quy trình làm việc, giảm thiểu sai sót "
                + "và nâng cao hiệu quả kinh doanh. Hệ thống được phát triển bởi nhóm sinh viên Trường Đại học Sài Gòn "
                + "như một dự án học tập và nghiên cứu.");
        intro.setFont(new Font("Roboto", Font.PLAIN, 16));
        intro.setForeground(TEXT_COLOR);
        intro.setOpaque(false);
        intro.setWrapStyleWord(true);
        intro.setLineWrap(true);
        intro.setEditable(false);
        return intro;
    }

    private JPanel createTeamInfoPanel() {
        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.Y_AXIS));
        teamPanel.setBackground(new Color(255, 255, 255, 220));
        teamPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel teamTitle = new JLabel("Nhóm thực hiện:");
        teamTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        teamTitle.setForeground(PRIMARY_COLOR);
        teamTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel member1 = createTeamMemberLabel("Nguyễn Ngọc Tín - 3121410041");
        JLabel member2 = createTeamMemberLabel("Nguyễn Thị Tuyết Thư - 3121410017");
        JLabel member3 = createTeamMemberLabel("Nguyễn Đức Tây - 3121410038");

        teamPanel.add(teamTitle);
        teamPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        teamPanel.add(member1);
        teamPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        teamPanel.add(member2);
        teamPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        teamPanel.add(member3);


        return teamPanel;
    }

    private JLabel createTeamMemberLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Roboto", Font.PLAIN, 16));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return label;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame("Test tongQuanPanel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null);
            frame.add(new tongQuanPanel());
            frame.setVisible(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}