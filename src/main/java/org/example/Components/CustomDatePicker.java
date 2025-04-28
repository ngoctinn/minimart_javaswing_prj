package org.example.Components;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomDatePicker extends DatePicker {
    
    public CustomDatePicker() {
        super();
        setupVietnameseStyle();
    }
    
    public CustomDatePicker(LocalDate date) {

        setupVietnameseStyle();
    }
    
    private void setupVietnameseStyle() {
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
        
        // Áp dụng cài đặt
        this.setSettings(dateSettings);
        
        // Tùy chỉnh kích thước
        this.setPreferredSize(new Dimension(200, 30));
        this.getComponentDateTextField().setPreferredSize(new Dimension(150, 30));
        this.getComponentToggleCalendarButton().setPreferredSize(new Dimension(30, 30));
    }
    
    // Phương thức tiện ích để lấy ngày dưới dạng chuỗi theo định dạng Việt Nam
    public String getDateStringVN() {
        if (this.getDate() == null) {
            return "";
        }
        return this.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    
    // Phương thức tiện ích để đặt ngày từ chuỗi định dạng Việt Nam
    public void setDateFromStringVN(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            this.clear();
            return;
        }
        
        try {
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            this.setDate(date);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Định dạng ngày không hợp lệ. Vui lòng sử dụng định dạng dd/MM/yyyy", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame("Test CustomDatePicker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
            panel.setBackground(Color.WHITE);
            
            JLabel label = new JLabel("Chọn ngày:");
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            CustomDatePicker datePicker = new CustomDatePicker();
            datePicker.setDate(LocalDate.now());
            
            JButton showDateButton = new JButton("Hiển thị ngày đã chọn");
            showDateButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            showDateButton.addActionListener(e -> {
                if (datePicker.getDate() != null) {
                    JOptionPane.showMessageDialog(frame, 
                        "Ngày đã chọn: " + datePicker.getDateStringVN(), 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, 
                        "Chưa chọn ngày", 
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            });
            
            panel.add(label);
            panel.add(datePicker);
            panel.add(showDateButton);
            
            frame.add(panel);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
