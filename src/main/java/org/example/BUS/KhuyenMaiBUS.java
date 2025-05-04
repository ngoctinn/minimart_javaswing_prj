package org.example.BUS;

import org.example.DAO.KhuyenMaiDAO;
import org.example.DTO.khuyenMaiDTO;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Lớp xử lý logic nghiệp vụ liên quan đến khuyến mãi
 */
public class KhuyenMaiBUS {

    /**
     * Lấy danh sách tất cả khuyến mãi
     * @return ArrayList<khuyenMaiDTO> danh sách tất cả khuyến mãi
     */
    public static ArrayList<khuyenMaiDTO> selectAll() {
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        return khuyenMaiDAO.selectAll();
    }

    /**
     * Lấy danh sách khuyến mãi đang hoạt động
     * @return ArrayList<khuyenMaiDTO> danh sách khuyến mãi đang hoạt động
     */
    public static ArrayList<khuyenMaiDTO> layDanhSachKhuyenMai() {
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        return khuyenMaiDAO.layDanhSachKhuyenMai();
    }

    /**
     * Thêm khuyến mãi mới
     * @param khuyenMai khuyenMaiDTO đối tượng khuyến mãi cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public static int themKhuyenMai(khuyenMaiDTO khuyenMai) {
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        return khuyenMaiDAO.insert(khuyenMai);
    }

    /**
     * Cập nhật thông tin khuyến mãi
     * @param khuyenMai khuyenMaiDTO đối tượng khuyến mãi cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatKhuyenMai(khuyenMaiDTO khuyenMai) {
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        return khuyenMaiDAO.update(khuyenMai);
    }

    /**
     * Xóa khuyến mãi (cập nhật trạng thái)
     * @param khuyenMai khuyenMaiDTO đối tượng khuyến mãi cần xóa
     * @return int số dòng bị ảnh hưởng
     */
    public static int xoaKhuyenMai(khuyenMaiDTO khuyenMai) {
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        return khuyenMaiDAO.delete(khuyenMai);
    }

    /**
     * Tìm kiếm khuyến mãi theo từ khóa
     * @param keyword String từ khóa tìm kiếm
     * @return ArrayList<khuyenMaiDTO> danh sách khuyến mãi tìm thấy
     */
    public static ArrayList<khuyenMaiDTO> timKiemKhuyenMai(String keyword) {
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        return khuyenMaiDAO.timKiemKhuyenMai(keyword);
    }

    /**
     * Lấy danh sách khuyến mãi đang có hiệu lực
     * @return ArrayList<khuyenMaiDTO> danh sách khuyến mãi đang có hiệu lực
     */
    public static ArrayList<khuyenMaiDTO> layDanhSachKhuyenMaiHieuLuc() {
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        return khuyenMaiDAO.layDanhSachKhuyenMaiHieuLuc();
    }

    /**
     * Lấy thông tin khuyến mãi theo mã
     * @param maKM String mã khuyến mãi
     * @return khuyenMaiDTO đối tượng khuyến mãi, null nếu không tìm thấy
     */
    public static khuyenMaiDTO layKhuyenMaiTheoMa(String maKM) {
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        khuyenMaiDTO khuyenMai = new khuyenMaiDTO();
        khuyenMai.setMaKM(maKM);
        return khuyenMaiDAO.selectById(khuyenMai);
    }

    /**
     * Tạo mã khuyến mãi mới tự động tăng
     * @return String mã khuyến mãi mới
     */
    public static String taoMaKhuyenMaiMoi() {
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        return khuyenMaiDAO.taoMaKhuyenMaiMoi();
    }

    /**
     * Kiểm tra dữ liệu khuyến mãi hợp lệ
     * @param khuyenMai khuyenMaiDTO đối tượng khuyến mãi cần kiểm tra
     * @return String thông báo lỗi, null nếu hợp lệ
     */
    public static String kiemTraDuLieuKhuyenMai(khuyenMaiDTO khuyenMai) {
        // Kiểm tra mã khuyến mãi
        if (khuyenMai.getMaKM() == null || khuyenMai.getMaKM().trim().isEmpty()) {
            return "Mã khuyến mãi không được để trống";
        }

        // Kiểm tra tên khuyến mãi
        if (khuyenMai.getTenKM() == null || khuyenMai.getTenKM().trim().isEmpty()) {
            return "Tên khuyến mãi không được để trống";
        }

        // Kiểm tra ngày bắt đầu
        if (khuyenMai.getNgayBD() == null) {
            return "Ngày bắt đầu không được để trống";
        }

        // Kiểm tra ngày kết thúc
        if (khuyenMai.getNgayKT() == null) {
            return "Ngày kết thúc không được để trống";
        }

        // Kiểm tra ngày kết thúc phải sau ngày bắt đầu
        if (khuyenMai.getNgayKT().isBefore(khuyenMai.getNgayBD()) || 
            khuyenMai.getNgayKT().isEqual(khuyenMai.getNgayBD())) {
            return "Ngày kết thúc phải sau ngày bắt đầu";
        }

        // Kiểm tra phần trăm giảm giá
        if (khuyenMai.getPhanTram() < 0 || khuyenMai.getPhanTram() > 100) {
            return "Phần trăm giảm giá phải từ 0 đến 100";
        }

        return null; // Dữ liệu hợp lệ
    }

    /**
     * Kiểm tra khuyến mãi có đang có hiệu lực không
     * @param maKM String mã khuyến mãi
     * @return boolean true nếu đang có hiệu lực, false nếu không
     */
    public static boolean kiemTraKhuyenMaiHieuLuc(String maKM) {
        khuyenMaiDTO khuyenMai = layKhuyenMaiTheoMa(maKM);
        if (khuyenMai == null) {
            return false;
        }
        return khuyenMai.isActive();
    }

    /**
     * Lấy danh sách khuyến mãi sắp hết hạn (còn 7 ngày)
     * @return ArrayList<khuyenMaiDTO> danh sách khuyến mãi sắp hết hạn
     */
    public static ArrayList<khuyenMaiDTO> layDanhSachKhuyenMaiSapHetHan() {
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        String condition = "trangThai = 1 AND ngayKT BETWEEN '" + today + "' AND '" + nextWeek + "'";
        return khuyenMaiDAO.selectByCondition(condition);
    }
}
