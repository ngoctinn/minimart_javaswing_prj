package org.example.BUS;

import org.example.DAO.ChiTietHoaDonDAO;
import org.example.DAO.HoaDonDAO;
import org.example.DAO.LoaiSanPhamDAO;
import org.example.DAO.SanPhamDAO;
import org.example.DTO.LoaiSanPhamDTO;
import org.example.DTO.SanPhamDTO;
import org.example.DTO.chiTietHoaDonDTO;
import org.example.DTO.hoaDonDTO;
import org.example.Utils.PhuongThucBoSung;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Lớp xử lý thống kê dữ liệu
 */
public class ThongKeBUS {
    private static final SanPhamDAO sanPhamDAO = new SanPhamDAO();
    private static final HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private static final ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    private static final LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();

    //============================= THỐNG KÊ TỒN KHO =============================

    /**
     * Lấy danh sách sản phẩm có số lượng tồn kho thấp (dưới mức ngưỡng)
     * @param threshold Ngưỡng tồn kho thấp
     * @return ArrayList<SanPhamDTO> Danh sách sản phẩm tồn kho thấp
     */
    public static ArrayList<SanPhamDTO> getSanPhamTonKhoThap(int threshold) {
        ArrayList<SanPhamDTO> dsSanPham = sanPhamDAO.layDanhSachSanPham();
        ArrayList<SanPhamDTO> dsSanPhamTonKhoThap = new ArrayList<>();

        for (SanPhamDTO sanPham : dsSanPham) {
            if (sanPham.getTonKho() <= threshold && sanPham.getTonKho() > 0) {
                dsSanPhamTonKhoThap.add(sanPham);
            }
        }

        // Sắp xếp theo số lượng tồn kho tăng dần
        Collections.sort(dsSanPhamTonKhoThap, Comparator.comparingInt(SanPhamDTO::getTonKho));

        return dsSanPhamTonKhoThap;
    }

    /**
     * Lấy danh sách sản phẩm đã hết hàng (tồn kho = 0)
     * @return ArrayList<SanPhamDTO> Danh sách sản phẩm hết hàng
     */
    public static ArrayList<SanPhamDTO> getSanPhamHetHang() {
        ArrayList<SanPhamDTO> dsSanPham = sanPhamDAO.layDanhSachSanPham();
        ArrayList<SanPhamDTO> dsSanPhamHetHang = new ArrayList<>();

        for (SanPhamDTO sanPham : dsSanPham) {
            if (sanPham.getTonKho() == 0) {
                dsSanPhamHetHang.add(sanPham);
            }
        }

        return dsSanPhamHetHang;
    }

    /**
     * Tính tổng giá trị tồn kho của cửa hàng
     * @return double Tổng giá trị tồn kho
     */
    public static double getTongGiaTriTonKho() {
        ArrayList<SanPhamDTO> dsSanPham = sanPhamDAO.layDanhSachSanPham();
        double tongGiaTri = 0;

        for (SanPhamDTO sanPham : dsSanPham) {
            tongGiaTri += sanPham.getGiaBan() * sanPham.getTonKho();
        }

        return tongGiaTri;
    }


    //============================= THỐNG KÊ SẢN PHẨM BÁN CHẠY =============================

    /**
     * Lấy danh sách sản phẩm bán chạy nhất trong khoảng thời gian
     * @param tuNgay Thời gian bắt đầu
     * @param denNgay Thời gian kết thúc
     * @param limit Số lượng sản phẩm muốn lấy
     * @return List<Map.Entry<SanPhamDTO, Integer>> Danh sách sản phẩm và số lượng đã bán
     */
    public static List<Map.Entry<SanPhamDTO, Integer>> getSanPhamBanChay(
            LocalDateTime tuNgay, LocalDateTime denNgay, int limit) {
        // Lấy danh sách hóa đơn trong khoảng thời gian
        ArrayList<hoaDonDTO> dsHoaDon = hoaDonDAO.layHoaDonTheoKhoangThoiGian(tuNgay, denNgay);
        Map<String, Integer> soLuongBanTheoMaSP = new HashMap<>();

        // Tính tổng số lượng bán cho từng sản phẩm
        for (hoaDonDTO hoaDon : dsHoaDon) {
            if (hoaDon.isTrangThai()) { // Chỉ tính hóa đơn đang hoạt động
                ArrayList<chiTietHoaDonDTO> dsChiTiet = chiTietHoaDonDAO.selectByCondition(
                        "maHoaDon = '" + hoaDon.getMaHoaDon() + "'");

                for (chiTietHoaDonDTO chiTiet : dsChiTiet) {
                    String maSP = chiTiet.getMaSP();
                    int soLuong = chiTiet.getSoLuong();
                    soLuongBanTheoMaSP.put(maSP, soLuongBanTheoMaSP.getOrDefault(maSP, 0) + soLuong);
                }
            }
        }

        // Chuyển đổi từ mã sản phẩm sang đối tượng sản phẩm
        Map<SanPhamDTO, Integer> sanPhamVaSoLuong = new HashMap<>();
        for (Map.Entry<String, Integer> entry : soLuongBanTheoMaSP.entrySet()) {
            SanPhamDTO sanPham = sanPhamDAO.selectById(entry.getKey());
            if (sanPham != null) {
                sanPhamVaSoLuong.put(sanPham, entry.getValue());
            }
        }

        // Sắp xếp theo số lượng bán giảm dần và giới hạn số lượng kết quả
        List<Map.Entry<SanPhamDTO, Integer>> danhSachSanPhamBanChay = sanPhamVaSoLuong.entrySet()
                .stream()
                .sorted(Map.Entry.<SanPhamDTO, Integer>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toList());

        return danhSachSanPhamBanChay;
    }

    /**
     * Lấy danh sách sản phẩm bán chạy nhất trong tháng hiện tại
     * @param limit Số lượng sản phẩm muốn lấy
     * @return List<Map.Entry<SanPhamDTO, Integer>> Danh sách sản phẩm và số lượng đã bán
     */
    public static List<Map.Entry<SanPhamDTO, Integer>> getSanPhamBanChayTrongThang(int limit) {
        YearMonth thisMonth = YearMonth.now();
        LocalDateTime tuNgay = thisMonth.atDay(1).atStartOfDay();
        LocalDateTime denNgay = thisMonth.atEndOfMonth().atTime(LocalTime.MAX);

        return getSanPhamBanChay(tuNgay, denNgay, limit);
    }

    /**
     * Lấy danh sách sản phẩm có doanh thu cao nhất trong khoảng thời gian
     * @param tuNgay Thời gian bắt đầu
     * @param denNgay Thời gian kết thúc
     * @param limit Số lượng sản phẩm muốn lấy
     * @return List<Map.Entry<SanPhamDTO, Double>> Danh sách sản phẩm và doanh thu
     */
    public static List<Map.Entry<SanPhamDTO, Double>> getSanPhamDoanhThuCaoNhat(
            LocalDateTime tuNgay, LocalDateTime denNgay, int limit) {
        // Lấy danh sách hóa đơn trong khoảng thời gian
        ArrayList<hoaDonDTO> dsHoaDon = hoaDonDAO.layHoaDonTheoKhoangThoiGian(tuNgay, denNgay);
        Map<String, Double> doanhThuTheoMaSP = new HashMap<>();

        // Tính tổng doanh thu cho từng sản phẩm
        for (hoaDonDTO hoaDon : dsHoaDon) {
            if (hoaDon.isTrangThai()) { // Chỉ tính hóa đơn đang hoạt động
                ArrayList<chiTietHoaDonDTO> dsChiTiet = chiTietHoaDonDAO.selectByCondition(
                        "maHoaDon = '" + hoaDon.getMaHoaDon() + "'");

                for (chiTietHoaDonDTO chiTiet : dsChiTiet) {
                    String maSP = chiTiet.getMaSP();
                    double thanhTien = chiTiet.getThanhTien();
                    doanhThuTheoMaSP.put(maSP, doanhThuTheoMaSP.getOrDefault(maSP, 0.0) + thanhTien);
                }
            }
        }

        // Chuyển đổi từ mã sản phẩm sang đối tượng sản phẩm
        Map<SanPhamDTO, Double> sanPhamVaDoanhThu = new HashMap<>();
        for (Map.Entry<String, Double> entry : doanhThuTheoMaSP.entrySet()) {
            SanPhamDTO sanPham = sanPhamDAO.selectById(entry.getKey());
            if (sanPham != null) {
                sanPhamVaDoanhThu.put(sanPham, entry.getValue());
            }
        }

        // Sắp xếp theo doanh thu giảm dần và giới hạn số lượng kết quả
        List<Map.Entry<SanPhamDTO, Double>> danhSachSanPhamDoanhThuCao = sanPhamVaDoanhThu.entrySet()
                .stream()
                .sorted(Map.Entry.<SanPhamDTO, Double>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toList());

        return danhSachSanPhamDoanhThuCao;
    }

    //============================= THỐNG KÊ DOANH THU =============================

    /**
     * Tính tổng doanh thu trong khoảng thời gian
     * @param tuNgay Thời gian bắt đầu
     * @param denNgay Thời gian kết thúc
     * @return double Tổng doanh thu
     */
    public static double getTongDoanhThu(LocalDateTime tuNgay, LocalDateTime denNgay) {
        ArrayList<hoaDonDTO> dsHoaDon = hoaDonDAO.layHoaDonTheoKhoangThoiGian(tuNgay, denNgay);
        double tongDoanhThu = 0;

        for (hoaDonDTO hoaDon : dsHoaDon) {
            if (hoaDon.isTrangThai()) { // Chỉ tính hóa đơn đang hoạt động
                tongDoanhThu += hoaDon.getTongTien();
            }
        }

        return tongDoanhThu;
    }

    /**
     * Tính doanh thu theo ngày trong khoảng thời gian
     * @param tuNgay Thời gian bắt đầu
     * @param denNgay Thời gian kết thúc
     * @return Map<String, Double> Map với key là ngày (dd/MM/yyyy), value là doanh thu
     */
    public static Map<String, Double> getDoanhThuTheoNgay(LocalDateTime tuNgay, LocalDateTime denNgay) {
        ArrayList<hoaDonDTO> dsHoaDon = hoaDonDAO.layHoaDonTheoKhoangThoiGian(tuNgay, denNgay);
        Map<String, Double> doanhThuTheoNgay = new LinkedHashMap<>(); // LinkedHashMap để giữ thứ tự
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Khởi tạo map với tất cả các ngày trong khoảng thời gian
        LocalDate currentDate = tuNgay.toLocalDate();
        LocalDate endDate = denNgay.toLocalDate();

        while (!currentDate.isAfter(endDate)) {
            doanhThuTheoNgay.put(currentDate.format(formatter), 0.0);
            currentDate = currentDate.plusDays(1);
        }

        // Tính doanh thu cho từng ngày
        for (hoaDonDTO hoaDon : dsHoaDon) {
            if (hoaDon.isTrangThai()) { // Chỉ tính hóa đơn đang hoạt động
                String ngay = hoaDon.getThoiGianLap().toLocalDate().format(formatter);
                doanhThuTheoNgay.put(ngay, doanhThuTheoNgay.getOrDefault(ngay, 0.0) + hoaDon.getTongTien());
            }
        }

        return doanhThuTheoNgay;
    }

    /**
     * Tính doanh thu theo tháng trong khoảng thời gian
     * @param tuThang Tháng bắt đầu (MM/yyyy)
     * @param denThang Tháng kết thúc (MM/yyyy)
     * @return Map<String, Double> Map với key là tháng (MM/yyyy), value là doanh thu
     */
    public static Map<String, Double> getDoanhThuTheoThang(String tuThang, String denThang) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth tuYearMonth = YearMonth.parse(tuThang, formatter);
        YearMonth denYearMonth = YearMonth.parse(denThang, formatter);

        LocalDateTime tuNgay = tuYearMonth.atDay(1).atStartOfDay();
        LocalDateTime denNgay = denYearMonth.atEndOfMonth().atTime(LocalTime.MAX);

        ArrayList<hoaDonDTO> dsHoaDon = hoaDonDAO.layHoaDonTheoKhoangThoiGian(tuNgay, denNgay);
        Map<String, Double> doanhThuTheoThang = new LinkedHashMap<>(); // LinkedHashMap để giữ thứ tự

        // Khởi tạo map với tất cả các tháng trong khoảng thời gian
        YearMonth currentYearMonth = tuYearMonth;
        while (!currentYearMonth.isAfter(denYearMonth)) {
            doanhThuTheoThang.put(currentYearMonth.format(formatter), 0.0);
            currentYearMonth = currentYearMonth.plusMonths(1);
        }

        // Tính doanh thu cho từng tháng
        for (hoaDonDTO hoaDon : dsHoaDon) {
            if (hoaDon.isTrangThai()) { // Chỉ tính hóa đơn đang hoạt động
                String thang = hoaDon.getThoiGianLap().format(DateTimeFormatter.ofPattern("MM/yyyy"));
                doanhThuTheoThang.put(thang, doanhThuTheoThang.getOrDefault(thang, 0.0) + hoaDon.getTongTien());
            }
        }

        return doanhThuTheoThang;
    }


    /**
     * Định dạng số tiền theo định dạng tiền tệ Việt Nam
     * @param amount Số tiền cần định dạng
     * @return String Chuỗi đã định dạng
     */
    public static String formatCurrency(double amount) {
        return PhuongThucBoSung.formatMoneyVND(amount);
    }

    /**
     * Phương thức test các chức năng thống kê
     */
    public static void main(String[] args) {
        // Test thống kê tồn kho
        System.out.println("===== THỐNG KÊ TỒN KHO =====");
        System.out.println("Sản phẩm tồn kho thấp (dưới 10): " + getSanPhamTonKhoThap(10).size());
        System.out.println("Sản phẩm hết hàng: " + getSanPhamHetHang().size());
        System.out.println("Tổng giá trị tồn kho: " + formatCurrency(getTongGiaTriTonKho()));

        // Test thống kê sản phẩm bán chạy
        System.out.println("\n===== THỐNG KÊ SẢN PHẨM BÁN CHẠY =====");
        LocalDateTime tuNgay = LocalDateTime.now().minusMonths(1);
        LocalDateTime denNgay = LocalDateTime.now();
        List<Map.Entry<SanPhamDTO, Integer>> dsSanPhamBanChay = getSanPhamBanChay(tuNgay, denNgay, 5);
        System.out.println("Top 5 sản phẩm bán chạy trong 1 tháng qua:");
        for (Map.Entry<SanPhamDTO, Integer> entry : dsSanPhamBanChay) {
            System.out.println(entry.getKey().getTenSP() + ": " + entry.getValue() + " " + entry.getKey().getDonVi());
        }

        // Test thống kê doanh thu
        System.out.println("\n===== THỐNG KÊ DOANH THU =====");
        System.out.println("Tổng doanh thu trong 1 tháng qua: " + formatCurrency(getTongDoanhThu(tuNgay, denNgay)));

        // Test doanh thu theo tháng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        String thangTruoc = tuNgay.format(formatter);
        String thangHienTai = denNgay.format(formatter);
        Map<String, Double> doanhThuTheoThang = getDoanhThuTheoThang(thangTruoc, thangHienTai);
        System.out.println("Doanh thu theo tháng:");
        for (Map.Entry<String, Double> entry : doanhThuTheoThang.entrySet()) {
            System.out.println(entry.getKey() + ": " + formatCurrency(entry.getValue()));
        }
    }
}
