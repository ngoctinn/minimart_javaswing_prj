package org.example.BUS;

import org.example.DAO.ChiTietHoaDonDAO;
import org.example.DTO.chiTietHoaDonDTO;
import org.example.DTO.hoaDonDTO;

import java.util.ArrayList;

/**
 * Lớp xử lý logic nghiệp vụ liên quan đến chi tiết hóa đơn
 */
public class ChiTietHoaDonBUS {
    private static ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();

    /**
     * Lấy danh sách tất cả chi tiết hóa đơn
     * @return ArrayList<chiTietHoaDonDTO> danh sách tất cả chi tiết hóa đơn
     */
    public static ArrayList<chiTietHoaDonDTO> layDanhSachChiTietHoaDon() {
        return chiTietHoaDonDAO.selectAll();
    }

    /**
     * Thêm chi tiết hóa đơn mới
     * @param chiTiet chiTietHoaDonDTO đối tượng chi tiết hóa đơn cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public static int themChiTietHoaDon(chiTietHoaDonDTO chiTiet) {
        // Kiểm tra dữ liệu hợp lệ
        String error = kiemTraDuLieuHopLe(chiTiet);
        if (error != null) {
            System.out.println("Lỗi: " + error);
            return 0;
        }

        // Thêm chi tiết hóa đơn vào CSDL
        int result = chiTietHoaDonDAO.insert(chiTiet);

        // Nếu thêm thành công, cập nhật tổng tiền hóa đơn
        if (result > 0) {
            capNhatTongTienHoaDon(chiTiet.getMaHoaDon());
        }

        return result;
    }

    /**
     * Cập nhật thông tin chi tiết hóa đơn
     * @param chiTiet chiTietHoaDonDTO đối tượng chi tiết hóa đơn cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatChiTietHoaDon(chiTietHoaDonDTO chiTiet) {
        // Kiểm tra dữ liệu hợp lệ
        String error = kiemTraDuLieuHopLe(chiTiet);
        if (error != null) {
            System.out.println("Lỗi: " + error);
            return 0;
        }

        // Lấy thông tin chi tiết cũ
        chiTietHoaDonDTO chiTietCu = chiTietHoaDonDAO.selectById(chiTiet);
        if (chiTietCu == null) {
            return 0;
        }

        // Cập nhật chi tiết hóa đơn
        int result = chiTietHoaDonDAO.update(chiTiet);

        // Nếu cập nhật thành công, cập nhật tổng tiền hóa đơn
        if (result > 0) {
            capNhatTongTienHoaDon(chiTiet.getMaHoaDon());
        }

        return result;
    }

    /**
     * Xóa chi tiết hóa đơn
     * @param chiTiet chiTietHoaDonDTO đối tượng chi tiết hóa đơn cần xóa
     * @return int số dòng bị ảnh hưởng
     */
    public static int xoaChiTietHoaDon(chiTietHoaDonDTO chiTiet) {
        // Lấy thông tin chi tiết trước khi xóa
        chiTietHoaDonDTO chiTietCu = chiTietHoaDonDAO.selectById(chiTiet);
        if (chiTietCu == null) {
            return 0;
        }

        String maHoaDon = chiTietCu.getMaHoaDon();

        // Xóa chi tiết hóa đơn
        int result = chiTietHoaDonDAO.delete(chiTiet);

        // Nếu xóa thành công, cập nhật tổng tiền hóa đơn
        if (result > 0) {
            capNhatTongTienHoaDon(maHoaDon);
        }

        return result;
    }

    /**
     * Lấy danh sách chi tiết hóa đơn theo mã hóa đơn
     * @param maHoaDon String mã hóa đơn
     * @return ArrayList<chiTietHoaDonDTO> danh sách chi tiết hóa đơn
     */
    public static ArrayList<chiTietHoaDonDTO> layChiTietHoaDonTheoMaHD(String maHoaDon) {
        if (maHoaDon == null || maHoaDon.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String condition = "maHoaDon = '" + maHoaDon + "'";
        return chiTietHoaDonDAO.selectByCondition(condition);
    }

    /**
     * Lấy danh sách chi tiết hóa đơn theo mã sản phẩm
     * @param maSP String mã sản phẩm
     * @return ArrayList<chiTietHoaDonDTO> danh sách chi tiết hóa đơn
     */
    public static ArrayList<chiTietHoaDonDTO> layChiTietHoaDonTheoMaSP(String maSP) {
        if (maSP == null || maSP.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String condition = "maSP = '" + maSP + "'";
        return chiTietHoaDonDAO.selectByCondition(condition);
    }

    /**
     * Tính tổng tiền của một hóa đơn
     * @param maHoaDon String mã hóa đơn
     * @return double tổng tiền
     */
    public static double tinhTongTienHoaDon(String maHoaDon) {
        ArrayList<chiTietHoaDonDTO> dsChiTiet = layChiTietHoaDonTheoMaHD(maHoaDon);
        double tongTien = 0;
        
        for (chiTietHoaDonDTO chiTiet : dsChiTiet) {
            tongTien += chiTiet.getThanhTien();
        }
        
        return tongTien;
    }

    /**
     * Cập nhật tổng tiền hóa đơn
     * @param maHoaDon String mã hóa đơn
     * @return boolean true nếu thành công, false nếu thất bại
     */
    public static boolean capNhatTongTienHoaDon(String maHoaDon) {
        // Tính tổng tiền từ các chi tiết hóa đơn
        double tongTien = tinhTongTienHoaDon(maHoaDon);
        
        // Lấy thông tin hóa đơn
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        hoaDonDTO hoaDon = hoaDonBUS.timHoaDonTheoMa(maHoaDon);
        
        if (hoaDon == null) {
            return false;
        }
        
        // Cập nhật tổng tiền
        hoaDon.setTongTien(tongTien);
        return hoaDonBUS.capNhatHoaDon(hoaDon);
    }

    /**
     * Kiểm tra dữ liệu chi tiết hóa đơn hợp lệ
     * @param chiTiet chiTietHoaDonDTO đối tượng chi tiết hóa đơn cần kiểm tra
     * @return String thông báo lỗi, null nếu hợp lệ
     */
    public static String kiemTraDuLieuHopLe(chiTietHoaDonDTO chiTiet) {
        // Kiểm tra mã hóa đơn
        if (chiTiet.getMaHoaDon() == null || chiTiet.getMaHoaDon().trim().isEmpty()) {
            return "Mã hóa đơn không được để trống";
        }

        // Kiểm tra mã sản phẩm
        if (chiTiet.getMaSP() == null || chiTiet.getMaSP().trim().isEmpty()) {
            return "Mã sản phẩm không được để trống";
        }

        // Kiểm tra số lượng
        if (chiTiet.getSoLuong() <= 0) {
            return "Số lượng phải lớn hơn 0";
        }

        // Kiểm tra giá bán
        if (chiTiet.getGiaBan() <= 0) {
            return "Giá bán phải lớn hơn 0";
        }

        // Kiểm tra hóa đơn tồn tại
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        hoaDonDTO hoaDon = hoaDonBUS.timHoaDonTheoMa(chiTiet.getMaHoaDon());
        if (hoaDon == null) {
            return "Hóa đơn không tồn tại";
        }

        return null; // Dữ liệu hợp lệ
    }

    /**
     * Thêm nhiều chi tiết hóa đơn cùng lúc
     * @param dsChiTiet ArrayList<chiTietHoaDonDTO> danh sách chi tiết hóa đơn cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public static int themNhieuChiTietHoaDon(ArrayList<chiTietHoaDonDTO> dsChiTiet) {
        if (dsChiTiet == null || dsChiTiet.isEmpty()) {
            return 0;
        }
        
        int count = 0;
        String maHoaDon = dsChiTiet.get(0).getMaHoaDon();
        
        for (chiTietHoaDonDTO chiTiet : dsChiTiet) {
            int result = themChiTietHoaDon(chiTiet);
            if (result > 0) {
                count++;
            }
        }
        
        // Cập nhật tổng tiền hóa đơn sau khi thêm tất cả chi tiết
        if (count > 0) {
            capNhatTongTienHoaDon(maHoaDon);
        }
        
        return count;
    }

    /**
     * Phương thức main để test các chức năng của BUS
     */
    public static void main(String[] args) {
        //test thêm nhiều chi tiết hoá đơn
        ArrayList<chiTietHoaDonDTO> dsChiTiet = new ArrayList<>();
        dsChiTiet.add(new chiTietHoaDonDTO("HD001", "SP001", 1, 100000));
        dsChiTiet.add(new chiTietHoaDonDTO("HD001", "SP002", 2, 200000));
        themNhieuChiTietHoaDon(dsChiTiet);
        System.out.println("=== Danh sách chi tiết hóa đơn ===");
        ArrayList<chiTietHoaDonDTO> dsChiTietHoaDon = layChiTietHoaDonTheoMaHD("HD001");
        for (chiTietHoaDonDTO ct : dsChiTietHoaDon) {
            System.out.println(ct);
        }
    }
}