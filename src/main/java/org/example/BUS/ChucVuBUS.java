package org.example.BUS;

import org.example.DAO.ChucVuDAO;
import org.example.DTO.ChucVuDTO;

import java.util.ArrayList;

public class ChucVuBUS {
    private static ChucVuDAO chucVuDAO = new ChucVuDAO();
    
    /**
     * Lấy danh sách tất cả chức vụ
     * @return ArrayList<ChucVuDTO> danh sách chức vụ
     */
    public static ArrayList<ChucVuDTO> layDanhSachChucVu() {
        return chucVuDAO.layDanhSachChucVu();
    }
    
    /**
     * Thêm một chức vụ mới
     * @param chucVu ChucVuDTO chức vụ cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public static int themChucVu(ChucVuDTO chucVu) {
        return chucVuDAO.themChucVu(chucVu);
    }
    
    /**
     * Cập nhật thông tin chức vụ
     * @param chucVu ChucVuDTO chức vụ cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatChucVu(ChucVuDTO chucVu) {
        return chucVuDAO.capNhatChucVu(chucVu);
    }
    
    /**
     * Xóa một chức vụ
     * @param maCV String mã chức vụ cần xóa
     * @return int số dòng bị ảnh hưởng
     */
    public static int xoaChucVu(String maCV) {
        return chucVuDAO.xoaChucVu(maCV);
    }
    
    /**
     * Tìm kiếm chức vụ theo từ khóa
     * @param keyword String từ khóa tìm kiếm
     * @return ArrayList<ChucVuDTO> danh sách chức vụ tìm thấy
     */
    public static ArrayList<ChucVuDTO> timKiemChucVu(String keyword) {
        return chucVuDAO.timKiemChucVu(keyword);
    }
    
    /**
     * Tạo mã chức vụ mới tự động
     * @return String mã chức vụ mới
     */
    public static String generateNextMaCV() {
        String maCV = chucVuDAO.layMaChucVuCuoiCung();
        
        // Tách phần số từ mã
        String prefix = "CV";
        String numberStr = maCV.substring(2);
        
        // Chuyển phần số thành số nguyên và tăng lên 1
        int number = Integer.parseInt(numberStr) + 1;
        
        // Format lại mã mới với số 0 đệm phía trước
        return String.format("%s%03d", prefix, number);
    }
}
