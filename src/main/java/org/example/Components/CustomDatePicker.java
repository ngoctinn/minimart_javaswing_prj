package org.example.Components;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.io.File;
import java.net.URL;

public class CustomDatePicker extends JPanel {
    
    /**
     * Enum định nghĩa các trạng thái của DatePicker
     */
    public enum State {
        DEFAULT, FOCUS, VALID, INVALID, DISABLED
    }
    
    private DatePicker datePicker;
    private JLabel errorLabel;
    private State currentState = State.DEFAULT;
    private String errorMessage = "";
    
    private Color defaultBorderColor = new Color(200, 200, 200);
    private Color focusBorderColor = new Color(100, 150, 255);
    private Color validBorderColor = new Color(50, 200, 50);
    private Color invalidBorderColor = new Color(255, 80, 80);
    private Color disabledBorderColor = new Color(220, 220, 220);
    private Color errorColor = new Color(255, 80, 80);
    private Color invalidBackgroundColor = new Color(255, 240, 240);
    private int cornerRadius = 8; // Bán kính bo góc
    
    public CustomDatePicker() {
        this(null);
    }
    
    public CustomDatePicker(LocalDate date) {
        setLayout(new BorderLayout(0, 2));
        setOpaque(false);
        setPreferredSize(new Dimension(200, 32));
        
        // Tạo DatePicker với cài đặt tiếng Việt
        setupDatePicker(date);
        
        // Tạo label hiển thị lỗi
        errorLabel = new JLabel();
        errorLabel.setForeground(errorColor);
        errorLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        errorLabel.setVisible(false);
        
        // Thêm các thành phần vào panel
        add(createDatePickerPanel(), BorderLayout.CENTER);
        add(errorLabel, BorderLayout.SOUTH);
        
        // Thiết lập trạng thái mặc định
        setState(State.DEFAULT);
    }
    
    private JPanel createDatePickerPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Vẽ nền với góc bo tròn
                Shape roundRect = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
                g2d.setClip(roundRect);
                
                // Thiết lập màu nền dựa trên trạng thái
                Color background = Color.WHITE;
                if (currentState == State.INVALID) {
                    background = invalidBackgroundColor;
                } else if (currentState == State.DISABLED) {
                    background = new Color(245, 245, 245);
                }
                
                g2d.setColor(background);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
                
                // Vẽ viền với góc bo tròn
                g2d.setClip(null);
                Color borderColor = defaultBorderColor;
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
            }
        };
        
        panel.setOpaque(false);
        panel.add(datePicker, BorderLayout.CENTER);
        return panel;
    }
    
    private void setupDatePicker(LocalDate initialDate) {
        // Tạo cài đặt cho DatePicker với ngôn ngữ tiếng Việt
        DatePickerSettings dateSettings = new DatePickerSettings(new Locale("vi", "VN"));
        
        // Định dạng hiển thị ngày tháng kiểu Việt Nam (dd/MM/yyyy)
        dateSettings.setFormatForDatesCommonEra("dd/MM/yyyy");
        
        // Tùy chỉnh font chữ
        Font vietnameseFont = new Font("Segoe UI", Font.PLAIN, 14);
        dateSettings.setFontCalendarDateLabels(vietnameseFont);
        dateSettings.setFontCalendarWeekdayLabels(new Font("Segoe UI", Font.BOLD, 14));
        dateSettings.setFontMonthAndYearMenuLabels(new Font("Segoe UI", Font.BOLD, 14));
        dateSettings.setFontTodayLabel(new Font("Segoe UI", Font.BOLD, 14));
        dateSettings.setFontClearLabel(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Tùy chỉnh văn bản
        dateSettings.setTranslationClear("Xóa");
        dateSettings.setTranslationToday("Hôm nay");
        
        // Tùy chỉnh giao diện
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundCalendarPanelLabelsOnHover, new Color(220, 240, 255));
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundTodayLabel, new Color(100, 150, 255));
        dateSettings.setColor(DatePickerSettings.DateArea.TextTodayLabel, Color.WHITE);
        
        // Bỏ viền mặc định
        dateSettings.setBorderCalendarPopup(BorderFactory.createEmptyBorder());
        dateSettings.setBorderPropertiesList(null);
        
        // Tạo DatePicker với các cài đặt
        datePicker = new DatePicker(dateSettings);
        
        // Đặt ngày ban đầu nếu có
        if (initialDate != null) {
            datePicker.setDate(initialDate);
        }
        
        // Tùy chỉnh giao diện DatePicker
        datePicker.setOpaque(false);
        datePicker.getComponentDateTextField().setOpaque(false);
        datePicker.getComponentDateTextField().setBorder(new EmptyBorder(8, 12, 8, 12));
        datePicker.getComponentDateTextField().setFont(vietnameseFont);
        
        // Điều chỉnh nút toggle calendar
        JButton toggleButton = datePicker.getComponentToggleCalendarButton();
        toggleButton.setOpaque(false);
        toggleButton.setBorder(null);
        toggleButton.setContentAreaFilled(false);
        
        // Thay đổi icon cho nút toggle calendar
        try {
            // Thử tải icon từ resources trước
            ImageIcon calendarIcon = null;
            
            // Kiểm tra đường dẫn tương đối
            String iconPath = "src/main/resources/Icons/calendar.png";
            File iconFile = new File(iconPath);
            if (iconFile.exists()) {
                calendarIcon = new ImageIcon(iconPath);
            } else {
                // Nếu không tìm thấy file, thử tải từ classloader
                URL iconUrl = getClass().getResource("/Icons/calendar.png");
                if (iconUrl != null) {
                    calendarIcon = new ImageIcon(iconUrl);
                }
            }
            
            // Nếu không tìm thấy icon từ resources, tạo icon bằng code
            if (calendarIcon == null || calendarIcon.getIconWidth() <= 0) {
                calendarIcon = createCalendarIcon();
            } else {
                // Resize icon nếu cần thiết
                Image image = calendarIcon.getImage();
                Image resizedImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                calendarIcon = new ImageIcon(resizedImage);
            }
            
            toggleButton.setIcon(calendarIcon);
            toggleButton.setText("");
        } catch (Exception e) {
            System.err.println("Không thể tải icon lịch: " + e.getMessage());
            try {
                // Fallback: sử dụng icon được tạo bằng code
                toggleButton.setIcon(createCalendarIcon());
                toggleButton.setText("");
            } catch (Exception ex) {
                System.err.println("Không thể tạo icon lịch: " + ex.getMessage());
            }
        }
        
        // Thêm FocusListener để cập nhật trạng thái
        datePicker.getComponentDateTextField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (currentState != State.DISABLED) {
                    setState(State.FOCUS);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (currentState == State.FOCUS) {
                    setState(State.DEFAULT);
                }
            }
        });
    }
    
    /**
     * Tạo biểu tượng lịch tùy chỉnh
     */
    private ImageIcon createCalendarIcon() {
        // Vẽ biểu tượng lịch trực tiếp bằng Java2D
        int width = 18;
        int height = 18;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Thiết lập rendering hints để làm mịn đường vẽ
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Vẽ đường viền của calendar
        g2d.setColor(new Color(100, 150, 255));
        g2d.fillRoundRect(0, 2, width, height - 2, 3, 3);
        
        // Vẽ phần đầu của calendar
        g2d.setColor(new Color(80, 130, 235));
        g2d.fillRect(0, 0, width, 4);
        
        // Vẽ các phần móc treo ở trên đầu
        g2d.setColor(new Color(80, 130, 235));
        g2d.fillRect(4, 0, 2, 3);
        g2d.fillRect(width - 6, 0, 2, 3);
        
        // Vẽ phần bên trong calendar
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(2, 4, width - 4, height - 6, 2, 2);
        
        // Vẽ các dòng chữ giả lập trong calendar
        g2d.setColor(new Color(200, 200, 200));
        g2d.fillRect(4, 8, width - 8, 1);
        g2d.fillRect(4, 12, width - 8, 1);
        
        g2d.dispose();
        
        return new ImageIcon(image);
    }
    
    /**
     * Thiết lập trạng thái của CustomDatePicker
     * @param state Trạng thái mới
     */
    public void setState(State state) {
        this.currentState = state;
        
        switch (state) {
            case DISABLED:
                datePicker.setEnabled(false);
                errorLabel.setVisible(false);
                break;
            case VALID:
                datePicker.setEnabled(true);
                errorLabel.setVisible(false);
                break;
            case INVALID:
                datePicker.setEnabled(true);
                errorLabel.setText(errorMessage);
                errorLabel.setVisible(true);
                break;
            case FOCUS:
            case DEFAULT:
                datePicker.setEnabled(true);
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
     * Lấy ngày đã chọn
     * @return LocalDate ngày đã chọn
     */
    public LocalDate getDate() {
        return datePicker.getDate();
    }
    
    /**
     * Đặt ngày
     * @param date Ngày cần đặt
     */
    public void setDate(LocalDate date) {
        datePicker.setDate(date);
    }
    
    /**
     * Xóa ngày đã chọn
     */
    public void clear() {
        datePicker.clear();
    }
    
    /**
     * Thiết lập văn bản cho trường ngày
     * @param text Văn bản cần thiết lập
     */
    public void setText(String text) {
        datePicker.getComponentDateTextField().setText(text);
    }
    
    /**
     * Lấy ngày dưới dạng chuỗi theo định dạng Việt Nam
     * @return String ngày định dạng dd/MM/yyyy
     */
    public String getDateStringVN() {
        if (this.getDate() == null) {
            return "";
        }
        return this.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    
    /**
     * Đặt ngày từ chuỗi định dạng Việt Nam
     * @param dateString Chuỗi ngày định dạng dd/MM/yyyy
     */
    public void setDateFromStringVN(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            this.clear();
            return;
        }
        
        try {
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            this.setDate(date);
        } catch (Exception e) {
            setErrorMessage("Định dạng ngày không hợp lệ (dd/MM/yyyy)");
            setState(State.INVALID);
        }
    }
    
    /**
     * Thiết lập bán kính bo góc
     * @param radius Bán kính bo góc
     */
    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }
    
    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            JFrame frame = new JFrame("Test CustomDatePicker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            
            JPanel panel = new JPanel(new GridLayout(5, 1, 0, 20));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setBackground(Color.WHITE);
            
            // DatePicker với trạng thái mặc định
            JLabel label1 = new JLabel("Trạng thái mặc định:");
            CustomDatePicker datePicker1 = new CustomDatePicker(LocalDate.now());
            
            // DatePicker với trạng thái focus
            JLabel label2 = new JLabel("Trạng thái focus:");
            CustomDatePicker datePicker2 = new CustomDatePicker(LocalDate.now());
            datePicker2.setState(CustomDatePicker.State.FOCUS);
            
            // DatePicker với trạng thái hợp lệ
            JLabel label3 = new JLabel("Trạng thái hợp lệ:");
            CustomDatePicker datePicker3 = new CustomDatePicker(LocalDate.now());
            datePicker3.setState(CustomDatePicker.State.VALID);
            
            // DatePicker với trạng thái lỗi
            JLabel label4 = new JLabel("Trạng thái lỗi:");
            CustomDatePicker datePicker4 = new CustomDatePicker(LocalDate.now());
            datePicker4.setErrorMessage("Ngày không hợp lệ");
            datePicker4.setState(CustomDatePicker.State.INVALID);
            
            // DatePicker với trạng thái bị vô hiệu hóa
            JLabel label5 = new JLabel("Trạng thái vô hiệu hóa:");
            CustomDatePicker datePicker5 = new CustomDatePicker(LocalDate.now());
            datePicker5.setState(CustomDatePicker.State.DISABLED);
            
            panel.add(createRowPanel(label1, datePicker1));
            panel.add(createRowPanel(label2, datePicker2));
            panel.add(createRowPanel(label3, datePicker3));
            panel.add(createRowPanel(label4, datePicker4));
            panel.add(createRowPanel(label5, datePicker5));
            
            frame.add(panel);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    
    private static JPanel createRowPanel(JLabel label, Component component) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);
        panel.add(label, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }
}
