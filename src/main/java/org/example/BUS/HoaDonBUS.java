package org.example.BUS;

import org.example.DAO.HoaDonDAO;
import org.example.DTO.hoaDonDTO;
import org.example.DTO.chiTietHoaDonDTO;
import org.example.DTO.KhachHangDTO;
import org.example.DTO.khuyenMaiDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp xử lý logic nghiệp vụ liên quan đến hóa đơn
 */
public class HoaDonBUS {
    private static HoaDonDAO hoaDonDAO = new HoaDonDAO();

    /**
     * Lấy danh sách tất cả hóa đơn
     * @return ArrayList<hoaDonDTO> danh sách tất cả hóa đơn
     */
    public static ArrayList<hoaDonDTO> selectAll() {
        return hoaDonDAO.selectAll();
    }

    /**
     * Lấy danh sách hóa đơn đang hoạt động
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn đang hoạt động
     */
    public static ArrayList<hoaDonDTO> layDanhSachHoaDon() {
        return hoaDonDAO.layHoaDonTheoTrangThai(true);
    }

    /**
     * Thêm hóa đơn mới
     * @param hoaDon hoaDonDTO đối tượng hóa đơn cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public static int themHoaDon(hoaDonDTO hoaDon) {
        // Kiểm tra dữ liệu hợp lệ
        String error = kiemTraDuLieuHopLe(hoaDon);
        if (error != null) {
            System.out.println("Lỗi: " + error);
            return 0;
        }

        return hoaDonDAO.insert(hoaDon);
    }

    /**
     * Cập nhật thông tin hóa đơn
     * @param hoaDon hoaDonDTO đối tượng hóa đơn cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatHoaDon(hoaDonDTO hoaDon) {
        // Kiểm tra dữ liệu hợp lệ
        String error = kiemTraDuLieuHopLe(hoaDon);
        if (error != null) {
            System.out.println("Lỗi: " + error);
            return 0;
        }

        return hoaDonDAO.update(hoaDon);
    }

    /**
     * Xóa hóa đơn (cập nhật trạng thái)
     * @param hoaDon hoaDonDTO đối tượng hóa đơn cần xóa
     * @return int số dòng bị ảnh hưởng
     */
    public static int xoaHoaDon(hoaDonDTO hoaDon) {
        return hoaDonDAO.delete(hoaDon);
    }

    /**
     * Tìm kiếm hóa đơn theo từ khóa
     * @param keyword String từ khóa tìm kiếm
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn tìm thấy
     */
    public static ArrayList<hoaDonDTO> timKiemHoaDon(String keyword) {
        return hoaDonDAO.timKiemHoaDon(keyword);
    }

    /**
     * Lấy thông tin hóa đơn theo mã
     * @param maHoaDon String mã hóa đơn
     * @return hoaDonDTO đối tượng hóa đơn, null nếu không tìm thấy
     */
    public static hoaDonDTO layHoaDonTheoMa(String maHoaDon) {
        hoaDonDTO hoaDon = new hoaDonDTO();
        hoaDon.setMaHoaDon(maHoaDon);
        return hoaDonDAO.selectById(hoaDon);
    }

    /**
     * Tạo mã hóa đơn mới tự động tăng
     * @return String mã hóa đơn mới
     */
    public static String taoMaHoaDonMoi() {
        return hoaDonDAO.taoMaHoaDonMoi();
    }

    /**
     * Kiểm tra dữ liệu hóa đơn hợp lệ
     * @param hoaDon hoaDonDTO đối tượng hóa đơn cần kiểm tra
     * @return String thông báo lỗi, null nếu hợp lệ
     */
    public static String kiemTraDuLieuHopLe(hoaDonDTO hoaDon) {
        // Kiểm tra mã hóa đơn
        if (hoaDon.getMaHoaDon() == null || hoaDon.getMaHoaDon().trim().isEmpty()) {
            return "Mã hóa đơn không được để trống";
        }

        // Kiểm tra mã nhân viên
        if (hoaDon.getMaNV() == null || hoaDon.getMaNV().trim().isEmpty()) {
            return "Mã nhân viên không được để trống";
        }

        // Kiểm tra ngày lập
        if (hoaDon.getThoiGianLap() == null) {
            return "Ngày lập không được để trống";
        }

        // Kiểm tra tổng tiền
        if (hoaDon.getTongTien() < 0) {
            return "Tổng tiền không được âm";
        }

        return null; // Dữ liệu hợp lệ
    }

    /**
     * Thêm hóa đơn và chi tiết hóa đơn trong một giao dịch
     * @param hoaDon hoaDonDTO đối tượng hóa đơn cần thêm
     * @return boolean true nếu thành công, false nếu thất bại
     */
    public static boolean themHoaDonVaChiTiet(hoaDonDTO hoaDon) {
        // Kiểm tra dữ liệu hợp lệ
        String error = kiemTraDuLieuHopLe(hoaDon);
        if (error != null) {
            System.out.println("Lỗi: " + error);
            return false;
        }

        return hoaDonDAO.themHoaDonVaChiTiet(hoaDon);
    }

    /**
     * Tạo hóa đơn mới với thông tin cơ bản
     * @param maNV String mã nhân viên
     * @param maKH String mã khách hàng (có thể null nếu khách vãng lai)
     * @param maKM String mã khuyến mãi (có thể null nếu không áp dụng)
     * @return hoaDonDTO đối tượng hóa đơn mới
     */
    public static hoaDonDTO taoHoaDonMoi(String maNV, String maKH, String maKM) {
        hoaDonDTO hoaDon = new hoaDonDTO();
        
        // Tạo mã hóa đơn mới
        hoaDon.setMaHoaDon(taoMaHoaDonMoi());
        
        // Thiết lập thông tin cơ bản
        hoaDon.setMaNV(maNV);
        hoaDon.setMaKH(maKH);
        hoaDon.setMaKM(maKM);
        hoaDon.setThoiGianLap(java.time.LocalDateTime.now());
        hoaDon.setTongTien(0);
        hoaDon.setTrangThai(true);
        hoaDon.setChiTietHoaDon(new ArrayList<>());
        
        return hoaDon;
    }

    /**
     * Lấy danh sách hóa đơn theo khoảng thời gian
     * @param tuNgay LocalDate ngày bắt đầu
     * @param denNgay LocalDate ngày kết thúc
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn
     */
    public static ArrayList<hoaDonDTO> layHoaDonTheoKhoangThoiGian(LocalDate tuNgay, LocalDate denNgay) {
        return hoaDonDAO.layHoaDonTheoKhoangThoiGian(tuNgay, denNgay);
    }

    /**
     * Lấy danh sách hóa đơn theo khách hàng
     * @param maKH String mã khách hàng
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn
     */
    public static ArrayList<hoaDonDTO> layHoaDonTheoKhachHang(String maKH) {
        return hoaDonDAO.layHoaDonTheoKhachHang(maKH);
    }

    /**
     * Lấy danh sách hóa đơn theo nhân viên
     * @param maNV String mã nhân viên
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn
     */
    public static ArrayList<hoaDonDTO> layHoaDonTheoNhanVien(String maNV) {
        return hoaDonDAO.layHoaDonTheoNhanVien(maNV);
    }

    /**
     * Lấy danh sách hóa đơn theo khuyến mãi
     * @param maKM String mã khuyến mãi
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn
     */
    public static ArrayList<hoaDonDTO> layHoaDonTheoKhuyenMai(String maKM) {
        return hoaDonDAO.layHoaDonTheoKhuyenMai(maKM);
    }

    /**
     * Áp dụng khuyến mãi cho hóa đơn
     * @param hoaDon hoaDonDTO đối tượng hóa đơn
     * @param maKM String mã khuyến mãi
     * @return boolean true nếu thành công, false nếu thất bại
     */
    public static boolean apDungKhuyenMai(hoaDonDTO hoaDon, String maKM) {
        // Kiểm tra khuyến mãi có tồn tại và còn hiệu lực không
        if (maKM != null && !maKM.trim().isEmpty()) {
            khuyenMaiDTO khuyenMai = KhuyenMaiBUS.layKhuyenMaiTheoMa(maKM);
            if (khuyenMai == null || !khuyenMai.isActive()) {
                return false;
            }
            
            // Cập nhật mã khuyến mãi cho hóa đơn
            hoaDon.setMaKM(maKM);
            return capNhatHoaDon(hoaDon) > 0;
        }
        
        // Nếu không có mã khuyến mãi, xóa khuyến mãi hiện tại
        hoaDon.setMaKM(null);
        return capNhatHoaDon(hoaDon) > 0;
    }

    /**
     * Tính tổng tiền sau khuyến mãi
     * @param hoaDon hoaDonDTO đối tượng hóa đơn
     * @return double tổng tiền sau khuyến mãi
     */
    public static double tinhTongTienSauKhuyenMai(hoaDonDTO hoaDon) {
        double tongTien = hoaDon.getTongTien();
        
        // Nếu có áp dụng khuyến mãi
        if (hoaDon.getMaKM() != null && !hoaDon.getMaKM().trim().isEmpty()) {
            khuyenMaiDTO khuyenMai = KhuyenMaiBUS.layKhuyenMaiTheoMa(hoaDon.getMaKM());
            if (khuyenMai != null && khuyenMai.isActive()) {
                // Kiểm tra điều kiện áp dụng khuyến mãi (nếu có)
                if (khuyenMai.getDieuKien() == null || tongTien >= Double.parseDouble(khuyenMai.getDieuKien())) {
                    // Tính giảm giá theo phần trăm
                    double giamGia = tongTien * khuyenMai.getPhanTram() / 100;
                    tongTien -= giamGia;
                }
            }
        }
        
        return tongTien;
    }

    /**
     * Cập nhật điểm tích lũy cho khách hàng
     * @param hoaDon hoaDonDTO đối tượng hóa đơn
     * @return boolean true nếu thành công, false nếu thất bại
     */
    public static boolean capNhatDiemTichLuy(hoaDonDTO hoaDon) {
        // Nếu không có khách hàng, không cần cập nhật điểm
        if (hoaDon.getMaKH() == null || hoaDon.getMaKH().trim().isEmpty()) {
            return true;
        }
        
        // Lấy thông tin khách hàng
        KhachHangDTO khachHang = new KhachHangBUS().layKhachHangTheoMa(hoaDon.getMaKH());
        if (khachHang == null) {
            return false;
        }
        
        // Tính điểm tích lũy (ví dụ: cứ 100,000 VND được 1 điểm)
        int diemThem = (int)(hoaDon.getTongTien() / 100000);
        khachHang.setDiemTichLuy(khachHang.getDiemTichLuy() + diemThem);
        
        // Cập nhật điểm tích lũy cho khách hàng
        return new KhachHangBUS().capNhatKhachHang(khachHang) > 0;
    }

    /**
     * Hoàn tất hóa đơn (thêm hóa đơn, cập nhật điểm tích lũy)
     * @param hoaDon hoaDonDTO đối tượng hóa đơn
     * @return boolean true nếu thành công, false nếu thất bại
     */
    public static boolean hoanTatHoaDon(hoaDonDTO hoaDon) {
        // Thêm hóa đơn và chi tiết
        boolean themHoaDonThanhCong = themHoaDonVaChiTiet(hoaDon);
        if (!themHoaDonThanhCong) {
            return false;
        }
        
        // Cập nhật điểm tích lũy cho khách hàng
        return capNhatDiemTichLuy(hoaDon);
    }
}
