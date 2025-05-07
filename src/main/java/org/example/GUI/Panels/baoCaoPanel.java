package org.example.GUI.Panels;

import org.example.BUS.ThongKeBUS;
import org.example.DTO.SanPhamDTO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class baoCaoPanel extends JPanel {
    private ThongKeBUS thongKeBUS;
    private JPanel chartPanel;
    private JPanel controlPanel;
    private JComboBox<String> chartTypeComboBox;
    private JComboBox<String> timeRangeComboBox;
    private JButton generateButton;
    private JPanel summaryPanel;

    public baoCaoPanel() {
        thongKeBUS = new ThongKeBUS();
        initGUI();
    }

    public void initGUI() {
        this.setLayout(new BorderLayout());
        
        // Panel điều khiển
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        
        // Combobox loại biểu đồ
        JLabel chartTypeLabel = new JLabel("Loại biểu đồ:");
        chartTypeComboBox = new JComboBox<>(new String[]{"Biểu đồ doanh thu", "Biểu đồ sản phẩm bán chạy", "Biểu đồ tồn kho"});
        
        // Combobox khoảng thời gian
        JLabel timeRangeLabel = new JLabel("Thời gian:");
        timeRangeComboBox = new JComboBox<>(new String[]{"Hôm nay", "7 ngày qua", "30 ngày qua", "Năm nay"});
        
        // Nút tạo biểu đồ
        generateButton = new JButton("Tạo biểu đồ");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateChart();
                updateSummaryPanel();
            }
        });
        
        // Thêm các thành phần vào panel điều khiển
        controlPanel.add(chartTypeLabel);
        controlPanel.add(chartTypeComboBox);
        controlPanel.add(timeRangeLabel);
        controlPanel.add(timeRangeComboBox);
        controlPanel.add(generateButton);
        
        // Panel biểu đồ
        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        
        // Tạo biểu đồ mặc định
        JPanel defaultChartPanel = createDefaultChart();
        chartPanel.add(defaultChartPanel, BorderLayout.CENTER);
        
        // Tạo panel thông tin tổng quan
        summaryPanel = createSummaryPanel();
        
        // Thêm các panel vào panel chính
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(chartPanel, BorderLayout.CENTER);
        this.add(summaryPanel, BorderLayout.SOUTH);
        
        this.setVisible(true);
    }
    
    private JPanel createDefaultChart() {
        // Tạo dữ liệu cho biểu đồ doanh thu theo ngày trong 7 ngày qua
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Lấy dữ liệu doanh thu 7 ngày qua từ ThongKeBUS
        LocalDateTime tuNgay = LocalDateTime.now().minusDays(7).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime denNgay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        Map<String, Double> doanhThuTheoNgay = ThongKeBUS.getDoanhThuTheoNgay(tuNgay, denNgay);
        
        // Thêm dữ liệu vào dataset
        for (Map.Entry<String, Double> entry : doanhThuTheoNgay.entrySet()) {
            dataset.addValue(entry.getValue() / 1000, "Doanh thu", entry.getKey());
        }
        
        // Tạo biểu đồ
        JFreeChart barChart = ChartFactory.createBarChart(
                "Thống kê doanh thu 7 ngày qua",
                "Ngày", "Doanh thu (nghìn đồng)",
                dataset, PlotOrientation.VERTICAL,
                true, true, false);
        
        // Tạo panel chứa biểu đồ
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 500));
        
        return chartPanel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Lấy dữ liệu thống kê từ ThongKeBUS
        LocalDateTime tuNgay = LocalDateTime.now().minusDays(30).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime denNgay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        double tongDoanhThu = ThongKeBUS.getTongDoanhThu(tuNgay, denNgay);
        
        // Lấy số lượng hóa đơn (giả sử có phương thức này trong ThongKeBUS)
        int soHoaDon = 0;
        try {
            // Đếm số hóa đơn từ doanh thu theo ngày
            Map<String, Double> doanhThuTheoNgay = ThongKeBUS.getDoanhThuTheoNgay(tuNgay, denNgay);
            for (Double value : doanhThuTheoNgay.values()) {
                if (value > 0) {
                    soHoaDon++;
                }
            }
        } catch (Exception e) {
            soHoaDon = 0;
        }
        
        // Lấy sản phẩm bán chạy nhất
        String sanPhamBanChay = "Không có dữ liệu";
        try {
            List<Map.Entry<SanPhamDTO, Integer>> dsSanPhamBanChay = ThongKeBUS.getSanPhamBanChayTrongThang(1);
            if (!dsSanPhamBanChay.isEmpty()) {
                sanPhamBanChay = dsSanPhamBanChay.get(0).getKey().getTenSP();
            }
        } catch (Exception e) {
            sanPhamBanChay = "Không có dữ liệu";
        }
        
        // Lấy số lượng sản phẩm tồn kho thấp
        int sanPhamTonKhoThap = ThongKeBUS.getSanPhamTonKhoThap(10).size();
        
        // Tạo các panel thông tin
        panel.add(createInfoPanel("Tổng doanh thu (30 ngày)", ThongKeBUS.formatCurrency(tongDoanhThu), new Color(46, 204, 113)));
        panel.add(createInfoPanel("Số đơn hàng", String.valueOf(soHoaDon), new Color(52, 152, 219)));
        panel.add(createInfoPanel("Sản phẩm bán chạy", sanPhamBanChay, new Color(155, 89, 182)));
        panel.add(createInfoPanel("SP tồn kho thấp", String.valueOf(sanPhamTonKhoThap), new Color(230, 126, 34)));
        
        return panel;
    }
    
    private JPanel createInfoPanel(String title, String value, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void generateChart() {
        String chartType = (String) chartTypeComboBox.getSelectedItem();
        String timeRange = (String) timeRangeComboBox.getSelectedItem();
        
        // Xóa biểu đồ cũ
        chartPanel.removeAll();
        
        // Tạo biểu đồ mới dựa trên lựa chọn
        JPanel newChartPanel;
        
        if (chartType.equals("Biểu đồ doanh thu")) {
            newChartPanel = createDoanhThuChart(timeRange);
        } else if (chartType.equals("Biểu đồ sản phẩm bán chạy")) {
            newChartPanel = createSanPhamBanChayChart(timeRange);
        } else {
            newChartPanel = createTonKhoChart();
        }
        
        // Thêm biểu đồ mới vào panel
        chartPanel.add(newChartPanel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
    private JPanel createDoanhThuChart(String timeRange) {
        // Tạo dữ liệu cho biểu đồ doanh thu
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Xác định khoảng thời gian
        LocalDateTime tuNgay, denNgay;
        String title;
        
        switch (timeRange) {
            case "Hôm nay":
                tuNgay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
                denNgay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
                title = "Thống kê doanh thu hôm nay";
                break;
            case "7 ngày qua":
                tuNgay = LocalDateTime.now().minusDays(7).withHour(0).withMinute(0).withSecond(0);
                denNgay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
                title = "Thống kê doanh thu 7 ngày qua";
                break;
            case "30 ngày qua":
                tuNgay = LocalDateTime.now().minusDays(30).withHour(0).withMinute(0).withSecond(0);
                denNgay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
                title = "Thống kê doanh thu 30 ngày qua";
                break;
            default: // Năm nay
                tuNgay = LocalDateTime.of(LocalDate.now().withDayOfYear(1), LocalTime.MIN);
                denNgay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
                title = "Thống kê doanh thu năm nay";
                break;
        }
        
        // Lấy dữ liệu doanh thu từ ThongKeBUS
        Map<String, Double> doanhThuTheoNgay;
        
        if (timeRange.equals("Năm nay")) {
            // Lấy doanh thu theo tháng cho cả năm
            String tuThang = String.format("%02d/%d", 1, LocalDate.now().getYear());
            String denThang = String.format("%02d/%d", 12, LocalDate.now().getYear());
            doanhThuTheoNgay = ThongKeBUS.getDoanhThuTheoThang(tuThang, denThang);
        } else {
            // Lấy doanh thu theo ngày
            doanhThuTheoNgay = ThongKeBUS.getDoanhThuTheoNgay(tuNgay, denNgay);
        }
        
        // Thêm dữ liệu vào dataset
        for (Map.Entry<String, Double> entry : doanhThuTheoNgay.entrySet()) {
            dataset.addValue(entry.getValue() / 1000, "Doanh thu", entry.getKey());
        }
        
        // Tạo biểu đồ
        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                timeRange.equals("Năm nay") ? "Tháng" : "Ngày", 
                "Doanh thu (nghìn đồng)",
                dataset, PlotOrientation.VERTICAL,
                true, true, false);
        
        // Tạo panel chứa biểu đồ
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 500));
        
        return chartPanel;
    }
    
    private JPanel createSanPhamBanChayChart(String timeRange) {
        // Tạo dữ liệu cho biểu đồ sản phẩm bán chạy
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        // Xác định khoảng thời gian
        LocalDateTime tuNgay, denNgay;
        String title;
        
        switch (timeRange) {
            case "Hôm nay":
                tuNgay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
                denNgay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
                title = "Top 5 sản phẩm bán chạy hôm nay";
                break;
            case "7 ngày qua":
                tuNgay = LocalDateTime.now().minusDays(7).withHour(0).withMinute(0).withSecond(0);
                denNgay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
                title = "Top 5 sản phẩm bán chạy 7 ngày qua";
                break;
            case "30 ngày qua":
                tuNgay = LocalDateTime.now().minusDays(30).withHour(0).withMinute(0).withSecond(0);
                denNgay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
                title = "Top 5 sản phẩm bán chạy 30 ngày qua";
                break;
            default: // Năm nay
                tuNgay = LocalDateTime.of(LocalDate.now().withDayOfYear(1), LocalTime.MIN);
                denNgay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
                title = "Top 5 sản phẩm bán chạy năm nay";
                break;
        }
        
        // Lấy dữ liệu sản phẩm bán chạy từ ThongKeBUS
        List<Map.Entry<SanPhamDTO, Integer>> dsSanPhamBanChay = ThongKeBUS.getSanPhamBanChay(tuNgay, denNgay, 5);
        
        // Thêm dữ liệu vào dataset
        for (Map.Entry<SanPhamDTO, Integer> entry : dsSanPhamBanChay) {
            dataset.setValue(entry.getKey().getTenSP(), entry.getValue());
        }
        
        // Nếu không có dữ liệu, thêm một mục "Không có dữ liệu"
        if (dataset.getItemCount() == 0) {
            dataset.setValue("Không có dữ liệu", 1);
        }
        
        // Tạo biểu đồ
        JFreeChart pieChart = ChartFactory.createPieChart(
                title,
                dataset,
                true, true, false);
        
        // Tạo panel chứa biểu đồ
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(800, 500));
        
        return chartPanel;
    }
    
    private JPanel createTonKhoChart() {
        // Tạo dữ liệu cho biểu đồ tồn kho
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Lấy danh sách sản phẩm tồn kho thấp từ ThongKeBUS
        ArrayList<SanPhamDTO> dsSanPhamTonKhoThap = ThongKeBUS.getSanPhamTonKhoThap(10);
        
        // Thêm dữ liệu vào dataset
        for (SanPhamDTO sanPham : dsSanPhamTonKhoThap) {
            dataset.addValue(sanPham.getTonKho(), "Tồn kho", sanPham.getTenSP());
        }
        
        // Nếu không có dữ liệu, thêm một mục "Không có dữ liệu"
        if (dataset.getRowCount() == 0) {
            dataset.addValue(0, "Tồn kho", "Không có dữ liệu");
        }
        
        // Tạo biểu đồ
        JFreeChart barChart = ChartFactory.createBarChart(
                "Sản phẩm tồn kho thấp (dưới 10)",
                "Sản phẩm", "Số lượng tồn",
                dataset, PlotOrientation.VERTICAL,
                true, true, false);
        
        // Tạo panel chứa biểu đồ
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 500));
        
        return chartPanel;
    }
    
    private void updateSummaryPanel() {
        // Xóa panel cũ
        this.remove(summaryPanel);
        
        // Tạo panel mới
        summaryPanel = createSummaryPanel();
        
        // Thêm panel mới
        this.add(summaryPanel, BorderLayout.SOUTH);
        
        // Cập nhật giao diện
        this.revalidate();
        this.repaint();
    }

    // main
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new baoCaoPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
