package org.example.BUS;

import org.example.DAO.LoHangDAO;
import org.example.DTO.loHangDTO;

import java.util.ArrayList;

/**
 * Lớp xử lý logic nghiệp vụ liên quan đến lô hàng
 */
public class LoHangBUS {

    /**
     * Lấy danh sách tất cả lô hàng
     * @return ArrayList<loHangDTO> danh sách tất cả lô hàng
     */
    public static ArrayList<loHangDTO> selectAll() {
        LoHangDAO loHangDAO = new LoHangDAO();
        return loHangDAO.selectAll();
    }

    /**
     * Lấy danh sách lô hàng đang hoạt động
     * @return ArrayList<loHangDTO> danh sách lô hàng đang hoạt động
     */
    public static ArrayList<loHangDTO> layDanhSachLoHang() {
        LoHangDAO loHangDAO = new LoHangDAO();
        return loHangDAO.layDanhSachLoHang();
    }

    /**
     * Thêm lô hàng mới
     * @param loHang loHangDTO đối tượng lô hàng cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public static int themLoHang(loHangDTO loHang) {
        LoHangDAO loHangDAO = new LoHangDAO();
        return loHangDAO.insert(loHang);
    }

    /**
     * Cập nhật thông tin lô hàng
     * @param loHang loHangDTO đối tượng lô hàng cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatLoHang(loHangDTO loHang) {
        LoHangDAO loHangDAO = new LoHangDAO();
        return loHangDAO.update(loHang);
    }

    /**
     * Xóa lô hàng (cập nhật trạng thái)
     * @param loHang loHangDTO đối tượng lô hàng cần xóa
     * @return int số dòng bị ảnh hưởng
     */
    public static int xoaLoHang(loHangDTO loHang) {
        LoHangDAO loHangDAO = new LoHangDAO();
        return loHangDAO.delete(loHang);
    }

    /**
     * Tìm kiếm lô hàng theo từ khóa
     * @param keyword String từ khóa tìm kiếm
     * @return ArrayList<loHangDTO> danh sách lô hàng tìm thấy
     */
    public static ArrayList<loHangDTO> timKiemLoHang(String keyword) {
        LoHangDAO loHangDAO = new LoHangDAO();
        return loHangDAO.timKiemLoHang(keyword);
    }

    /**
     * Lấy danh sách lô hàng theo mã sản phẩm
     * @param maSP String mã sản phẩm
     * @return ArrayList<loHangDTO> danh sách lô hàng của sản phẩm
     */
    public static ArrayList<loHangDTO> layDanhSachLoHangTheoMaSP(String maSP) {
        LoHangDAO loHangDAO = new LoHangDAO();
        return loHangDAO.layDanhSachLoHangTheoMaSP(maSP);
    }

    /**
     * Lấy thông tin lô hàng theo mã
     * @param maLoHang String mã lô hàng
     * @return loHangDTO đối tượng lô hàng, null nếu không tìm thấy
     */
    public static loHangDTO layLoHangTheoMa(String maLoHang) {
        LoHangDAO loHangDAO = new LoHangDAO();
        loHangDTO loHang = new loHangDTO();
        loHang.setMaLoHang(maLoHang);
        return loHangDAO.selectById(loHang);
    }

    /**
     * Tạo mã lô hàng mới tự động tăng
     * @return String mã lô hàng mới
     */
    public static String taoMaLoHangMoi() {
        LoHangDAO loHangDAO = new LoHangDAO();
        return loHangDAO.taoMaLoHangMoi();
    }

    /**
     * Cập nhật số lượng lô hàng
     * @param maLoHang String mã lô hàng
     * @param soLuong int số lượng mới
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatSoLuong(String maLoHang, int soLuong) {
        LoHangDAO loHangDAO = new LoHangDAO();
        return loHangDAO.capNhatSoLuong(maLoHang, soLuong);
    }

    /**
     * Kiểm tra lô hàng đã hết hạn hay chưa
     * @param maLoHang String mã lô hàng cần kiểm tra
     * @return boolean true nếu đã hết hạn, false nếu chưa hết hạn
     */
    public static boolean kiemTraHetHan(String maLoHang) {
        LoHangDAO loHangDAO = new LoHangDAO();
        return loHangDAO.kiemTraHetHan(maLoHang);
    }

    /**
     * Kiểm tra dữ liệu lô hàng hợp lệ
     * @param loHang loHangDTO đối tượng lô hàng cần kiểm tra
     * @return String thông báo lỗi, null nếu hợp lệ
     */
    public static String kiemTraDuLieuLoHang(loHangDTO loHang) {
        // Kiểm tra mã lô hàng
        if (loHang.getMaLoHang() == null || loHang.getMaLoHang().trim().isEmpty()) {
            return "Mã lô hàng không được để trống";
        }

        // Kiểm tra mã sản phẩm
        if (loHang.getMaSP() == null || loHang.getMaSP().trim().isEmpty()) {
            return "Mã sản phẩm không được để trống";
        }

        // Kiểm tra ngày sản xuất
        if (loHang.getNgaySanXuat() == null) {
            return "Ngày sản xuất không được để trống";
        }

        // Kiểm tra ngày hết hạn
        if (loHang.getNgayHetHan() == null) {
            return "Ngày hết hạn không được để trống";
        }

        // Kiểm tra ngày hết hạn phải sau ngày sản xuất
        if (loHang.getNgayHetHan().isBefore(loHang.getNgaySanXuat()) || 
            loHang.getNgayHetHan().isEqual(loHang.getNgaySanXuat())) {
            return "Ngày hết hạn phải sau ngày sản xuất";
        }

        // Kiểm tra số lượng
        if (loHang.getSoLuong() < 0) {
            return "Số lượng không được âm";
        }

        // Kiểm tra trạng thái
        if (loHang.getTrangThai() == null || loHang.getTrangThai().trim().isEmpty()) {
            return "Trạng thái không được để trống";
        }

        return null; // Dữ liệu hợp lệ
    }
}
