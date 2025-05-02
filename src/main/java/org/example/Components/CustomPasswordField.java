package org.example.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.UIManager;

/**
 * CustomPasswordField là một lớp kế thừa từ JPasswordField với các trạng thái tùy chỉnh
 * bao gồm DEFAULT, FOCUS, VALID, INVALID, và DISABLED
 * Sử dụng nút ẩn/hiện mật khẩu mặc định của FlatLaf
 */
public class CustomPasswordField extends JPasswordField {

    /**
     * Enum định nghĩa các trạng thái của PasswordField
     */
    public enum State {
        DEFAULT, FOCUS, VALID, INVALID, DISABLED
    }

    private State currentState = State.DEFAULT;
    private String placeholder = "";
    private String errorMessage = "";
    private JLabel errorLabel;
    private JPanel container;
    private int cornerRadius = 8; // Bán kính bo góc

    /**
     * Constructor mặc định
     */
    public CustomPasswordField() {
        this("");
    }

    /**
     * Constructor với placeholder
     * @param placeholder Văn bản gợi ý hiển thị khi trường trống
     */
    public CustomPasswordField(String placeholder) {
        super();
        this.placeholder = placeholder;
        
        // Thiết lập thuộc tính cho passwordfield
        setOpaque(false);
        
        // Bật tính năng hiển thị nút ẩn/hiện mật khẩu của FlatLaf
        putClientProperty("JPasswordField.showRevealButton", true);
        
        // Thiết lập giao diện ban đầu
        setupUI();
        
        // Thêm các sự kiện
        addEventListeners();
    }

    /**
     * Thiết lập giao diện ban đầu
     */
    private void setupUI() {
        // Tạo container chứa passwordfield và error label
        container = new JPanel(new BorderLayout(0, 2)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.dispose();
            }
        };
        container.setOpaque(false);

        // Thiết lập kích thước mặc định
        setPreferredSize(new Dimension(200, 32));
        
        // Tạo label hiển thị lỗi
        errorLabel = new JLabel();
        errorLabel.setForeground(UIManager.getColor("TextField.errorForeground") != null ? 
                UIManager.getColor("TextField.errorForeground") : new Color(255, 80, 80));
        errorLabel.setFont(new Font(getFont().getFamily(), Font.PLAIN, 11));
        errorLabel.setVisible(false);
        
        // Thiết lập border và màu nền mặc định
        updateBorderForState(currentState);
        
        // Thêm các thành phần vào container
        container.add(this, BorderLayout.CENTER);
        container.add(errorLabel, BorderLayout.SOUTH);
    }

    /**
     * Thêm các sự kiện cho passwordfield
     */
    private void addEventListeners() {
        // Sự kiện focus
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (currentState != State.DISABLED) {
                    if (currentState == State.DEFAULT) {
                        setState(State.FOCUS);
                    }
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (currentState == State.FOCUS) {
                    setState(State.DEFAULT);
                }
                repaint();
            }
        });
        
        // Sự kiện nhập liệu
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                repaint();
            }
        });
    }

    /**
     * Cập nhật viền dựa trên trạng thái hiện tại
     * @param state Trạng thái cần cập nhật
     */
    private void updateBorderForState(State state) {
        setBorder(new EmptyBorder(8, 12, 8, 12));
        
        switch (state) {
            case FOCUS:
                setBackground(UIManager.getColor("TextField.focusedBackground") != null ? 
                        UIManager.getColor("TextField.focusedBackground") : Color.WHITE);
                break;
            case VALID:
                setBackground(Color.WHITE);
                break;
            case INVALID:
                setBackground(UIManager.getColor("TextField.errorBackground") != null ? 
                        UIManager.getColor("TextField.errorBackground") : new Color(255, 240, 240));
                break;
            case DISABLED:
                setBackground(UIManager.getColor("TextField.disabledBackground") != null ? 
                        UIManager.getColor("TextField.disabledBackground") : new Color(245, 245, 245));
                break;
            default: // DEFAULT
                setBackground(UIManager.getColor("TextField.background") != null ? 
                        UIManager.getColor("TextField.background") : Color.WHITE);
                break;
        }
    }

    /**
     * Thiết lập trạng thái của passwordfield
     * @param state Trạng thái mới
     */
    public void setState(State state) {
        this.currentState = state;
        updateBorderForState(state);
        
        switch (state) {
            case DISABLED:
                setEnabled(false);
                errorLabel.setVisible(false);
                break;
            case VALID:
                setEnabled(true);
                errorLabel.setVisible(false);
                break;
            case INVALID:
                setEnabled(true);
                errorLabel.setText(errorMessage);
                errorLabel.setVisible(true);
                break;
            case FOCUS:
            case DEFAULT:
                setEnabled(true);
                errorLabel.setVisible(false);
                break;
        }
        
        repaint();
        container.revalidate();
    }

    /**
     * Thiết lập thông báo lỗi
     * @param errorMessage Thông báo lỗi
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        if (currentState == State.INVALID) {
            errorLabel.setText(errorMessage);
        }
    }

    /**
     * Thiết lập placeholder
     * @param placeholder Văn bản placeholder
     */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        putClientProperty("JTextField.placeholderText", placeholder);
        repaint();
    }

    /**
     * Thiết lập bán kính bo góc
     * @param radius Bán kính bo góc
     */
    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    /**
     * Lấy container chứa passwordfield và error label
     * @return JPanel container
     */
    public JPanel getContainer() {
        return container;
    }

    /**
     * Lấy trạng thái hiện tại
     * @return State trạng thái hiện tại
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Ghi đè phương thức paint để vẽ placeholder và viền bo góc
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Vẽ nền với góc bo tròn
        Shape roundRect = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        g2d.setClip(roundRect);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        
        // Vẽ viền với góc bo tròn
        g2d.setClip(null);
        Color borderColor;
        switch (currentState) {
            case FOCUS:
                borderColor = UIManager.getColor("TextField.focusedBorderColor") != null ? 
                        UIManager.getColor("TextField.focusedBorderColor") : new Color(100, 150, 255);
                break;
            case VALID:
                borderColor = UIManager.getColor("TextField.validBorderColor") != null ? 
                        UIManager.getColor("TextField.validBorderColor") : new Color(50, 200, 50);
                break;
            case INVALID:
                borderColor = UIManager.getColor("TextField.errorBorderColor") != null ? 
                        UIManager.getColor("TextField.errorBorderColor") : new Color(255, 80, 80);
                break;
            case DISABLED:
                borderColor = UIManager.getColor("TextField.disabledBorderColor") != null ? 
                        UIManager.getColor("TextField.disabledBorderColor") : new Color(220, 220, 220);
                break;
            default: // DEFAULT
                borderColor = UIManager.getColor("TextField.borderColor") != null ? 
                        UIManager.getColor("TextField.borderColor") : new Color(220, 220, 220);
                break;
        }
        g2d.setColor(borderColor);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        
        g2d.dispose();
        
        super.paintComponent(g);
    }

    // hàm main để test
    public static void main(String[] args) {
        try {
            // Thiết lập FlatLaf Look and Feel
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            

            // Bật tính năng hiển thị nút ẩn/hiện mật khẩu mặc định
            UIManager.put("PasswordField.showRevealButton", true);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        
        // thêm 4 passwordfield vào 1 frame
        JFrame frame = new JFrame("Test CustomPasswordField");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        CustomPasswordField passwordField1 = new CustomPasswordField("Nhập mật khẩu");
        frame.add(passwordField1.getContainer());
        CustomPasswordField passwordField2 = new CustomPasswordField("Mật khẩu hợp lệ");

        passwordField2.setState(CustomPasswordField.State.VALID);
        frame.add(passwordField2.getContainer());
        CustomPasswordField passwordField3 = new CustomPasswordField("Mật khẩu không hợp lệ");

        passwordField3.setErrorMessage("Mật khẩu phải có ít nhất 8 ký tự");
        passwordField3.setState(CustomPasswordField.State.INVALID);
        frame.add(passwordField3.getContainer());
        CustomPasswordField passwordField4 = new CustomPasswordField("Mật khẩu bị vô hiệu hóa");

        passwordField4.setState(CustomPasswordField.State.DISABLED);
        frame.add(passwordField4.getContainer());

        frame.pack();
        frame.setVisible(true);
    }
}