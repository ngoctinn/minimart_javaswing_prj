package org.example.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * CustomTextField là một lớp kế thừa từ JTextField với các trạng thái tùy chỉnh
 * bao gồm DEFAULT, FOCUS, VALID, INVALID, và DISABLED
 */
public class CustomTextField extends JTextField {

    /**
     * Enum định nghĩa các trạng thái của TextField
     */
    public enum State {
        DEFAULT, FOCUS, VALID, INVALID, DISABLED
    }

    private State currentState = State.DEFAULT;
    private String placeholder = "";
    private String errorMessage = "";
    private JLabel errorLabel;
    private JPanel container;
    private Color defaultBorderColor = new Color(200, 200, 200);
    private Color focusBorderColor = new Color(100, 150, 255);
    private Color validBorderColor = new Color(50, 200, 50);
    private Color invalidBorderColor = new Color(255, 80, 80);
    private Color disabledBorderColor = new Color(220, 220, 220);
    private Color placeholderColor = new Color(180, 180, 180);
    private Color errorColor = new Color(255, 80, 80);
    private Color invalidBackgroundColor = new Color(255, 240, 240);
    private int cornerRadius = 8; // Bán kính bo góc

    /**
     * Constructor mặc định
     */
    public CustomTextField() {
        this("");
    }

    /**
     * Constructor với placeholder
     * @param placeholder Văn bản gợi ý hiển thị khi trường trống
     */
    public CustomTextField(String placeholder) {
        super();
        this.placeholder = placeholder;
        
        // Thiết lập thuộc tính cho textfield
        setOpaque(false);
        
        // Thiết lập giao diện ban đầu
        setupUI();
        
        // Thêm các sự kiện
        addEventListeners();
    }

    /**
     * Thiết lập giao diện ban đầu
     */
    private void setupUI() {
        // Tạo container chứa textfield và error label
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

        //thiết lập kích thước mặc định
        setPreferredSize(new Dimension(220, 32));
        
        // Tạo label hiển thị lỗi
        errorLabel = new JLabel();
        errorLabel.setForeground(errorColor);
        // in nghiêng
        errorLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        errorLabel.setVisible(false);
        
        // Thiết lập border và màu nền mặc định
        updateBorderForState(currentState);
        
        // Thêm các thành phần vào container
        JPanel textFieldPanel = new JPanel(new BorderLayout());
        textFieldPanel.setOpaque(false);
        textFieldPanel.add(this, BorderLayout.CENTER);
        
        container.add(textFieldPanel, BorderLayout.CENTER);
        container.add(errorLabel, BorderLayout.SOUTH);
    }

    /**
     * Thêm các sự kiện cho textfield
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
                setBackground(Color.WHITE);
                break;
            case VALID:
                setBackground(Color.WHITE);
                break;
            case INVALID:
                setBackground(invalidBackgroundColor);
                break;
            case DISABLED:
                setBackground(new Color(245, 245, 245));
                break;
            default: // DEFAULT
                setBackground(Color.WHITE);
                break;
        }
    }

    /**
     * Thiết lập trạng thái của textfield
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
     * Lấy container chứa textfield và error label
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
                borderColor = focusBorderColor;
                break;
            case VALID:
                borderColor = validBorderColor;
                break;
            case INVALID:
                borderColor = invalidBorderColor;
                break;
            case DISABLED:
                borderColor = disabledBorderColor;
                break;
            default: // DEFAULT
                borderColor = defaultBorderColor;
                break;
        }
        g2d.setColor(borderColor);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        
        g2d.dispose();
        
        super.paintComponent(g);
        
        // Vẽ placeholder nếu không có text và không focus
        if (getText().isEmpty() && !hasFocus() && !placeholder.isEmpty() && isEnabled()) {
            g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setColor(placeholderColor);
            
            FontMetrics fm = g2d.getFontMetrics();
            int x = getInsets().left;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            
            g2d.drawString(placeholder, x, y);
            g2d.dispose();
        }
    }

    // hàm main để test
    public static void main(String[] args) {
       // thêm 4 textfield vào 1 frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        CustomTextField textField1 = new CustomTextField("Placeholder 1");
        frame.add(textField1.getContainer());

        CustomTextField textField2 = new CustomTextField("Placeholder 2");
        frame.add(textField2.getContainer());

        CustomTextField textField3 = new CustomTextField("Placeholder 3");
        frame.add(textField3.getContainer());

        CustomTextField textField4 = new CustomTextField("Placeholder 4");
        textField4.setErrorMessage("Error message");
        textField4.setState(State.DISABLED);
        frame.add(textField4.getContainer());

        CustomTextField textField5 = new CustomTextField("Placeholder 5");
        textField5.setErrorMessage("Error message");
        textField5.setState(State.INVALID);
        frame.add(textField5.getContainer());

        frame.pack();
        frame.setVisible(true);

    
    }
}