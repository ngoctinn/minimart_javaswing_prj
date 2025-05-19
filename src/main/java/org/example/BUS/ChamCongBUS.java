package org.example.BUS;

import org.example.DAO.ChamCongDAO;
import org.example.DTO.chamCongDTO;
import org.example.DTO.nhanVienDTO;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Lớp xử lý logic nghiệp vụ liên quan đến chấm công
 */
public class ChamCongBUS {
    private static ChamCongDAO chamCongDAO = new ChamCongDAO();

    /**
     * Lấy danh sách tất cả bản ghi chấm công
     * @return ArrayList<chamCongDTO> danh sách tất cả bản ghi chấm công
     */
    public static ArrayList<chamCongDTO> selectAll() {
        return chamCongDAO.selectAll();
    }

    /**
     * Thêm một bản ghi chấm công mới
     * @param chamCong chamCongDTO đối tượng chấm công cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public static int themChamCong(chamCongDTO chamCong) {
        // Kiểm tra dữ liệu hợp lệ
        String error = kiemTraDuLieuHopLe(chamCong);
        if (error != null) {
            System.out.println("Lỗi: " + error);
            return 0;
        }
        return chamCongDAO.insert(chamCong);
    }

    /**
     * Cập nhật thông tin chấm công
     * @param chamCong chamCongDTO đối tượng chấm công cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatChamCong(chamCongDTO chamCong) {
        // Kiểm tra dữ liệu hợp lệ
        String error = kiemTraDuLieuHopLe(chamCong);
        if (error != null) {
            System.out.println("Lỗi: " + error);
            return 0;
        }
        return chamCongDAO.update(chamCong);
    }

    /**
     * Xóa một bản ghi chấm công
     * @param chamCong chamCongDTO đối tượng chấm công cần xóa
     * @return int số dòng bị ảnh hưởng
     */
    public static int xoaChamCong(chamCongDTO chamCong) {
        return chamCongDAO.delete(chamCong);
    }

    /**
     * Tìm kiếm chấm công theo mã nhân viên
     * @param maNV String mã nhân viên cần tìm
     * @return ArrayList<chamCongDTO> danh sách chấm công của nhân viên
     */
    public static ArrayList<chamCongDTO> timKiemTheoMaNV(String maNV) {
        return chamCongDAO.selectByCondition("maNV = '" + maNV + "'");
    }

    /**
     * Tìm kiếm chấm công theo ngày
     * @param ngay LocalDate ngày cần tìm
     * @return ArrayList<chamCongDTO> danh sách chấm công trong ngày
     */
    public static ArrayList<chamCongDTO> timKiemTheoNgay(LocalDate ngay) {
        return chamCongDAO.selectByCondition("thoiGianChamCong = '" + ngay + "'");
    }

    /**
     * Kiểm tra nhân viên đã chấm công trong ngày chưa
     * @param maNV String mã nhân viên
     * @param ngay LocalDate ngày cần kiểm tra
     * @return boolean true nếu đã chấm công, false nếu chưa
     */
    public static boolean daChamCong(String maNV, LocalDate ngay) {
        chamCongDTO chamCong = new chamCongDTO();
        chamCong.setMaNV(maNV);
        chamCong.setThoiGianChamCong(ngay);
        return chamCongDAO.selectById(chamCong) != null;
    }

    /**
     * Kiểm tra dữ liệu chấm công hợp lệ
     * @param chamCong chamCongDTO đối tượng chấm công cần kiểm tra
     * @return String thông báo lỗi nếu có, null nếu hợp lệ
     */
    private static String kiemTraDuLieuHopLe(chamCongDTO chamCong) {
        // Kiểm tra mã nhân viên
        if (chamCong.getMaNV() == null || chamCong.getMaNV().trim().isEmpty()) {
            return "Mã nhân viên không được để trống";
        }

        // Kiểm tra thời gian chấm công
        if (chamCong.getThoiGianChamCong() == null) {
            return "Thời gian chấm công không được để trống";
        }

        // Kiểm tra không cho phép chấm công trong tương lai
        if (chamCong.getThoiGianChamCong().isAfter(LocalDate.now())) {
            return "Không thể chấm công cho ngày trong tương lai";
        }

        return null; // Dữ liệu hợp lệ
    }

    /**
     * Lấy thông tin nhân viên theo mã
     * @param maNV String mã nhân viên cần lấy thông tin
     * @return nhanVienDTO đối tượng nhân viên tìm được, null nếu không tìm thấy
     */
    public static nhanVienDTO layThongTinNhanVien(String maNV) {
        return NhanVienBUS.layNhanVienTheoMa(maNV);
    }
}
