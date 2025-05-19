package org.example.BUS;

import org.example.DAO.LuongDAO;
import org.example.DTO.LuongDTO;
import org.example.DAO.HopDongDAO;
import org.example.DAO.ChamCongDAO;
import org.example.DTO.hopDongDTO;
import org.example.DTO.chamCongDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Lớp BUS xử lý logic nghiệp vụ liên quan đến lương nhân viên
 */
public class LuongBUS {
    private LuongDAO luongDAO;
    private HopDongDAO hopDongDAO;
    private ChamCongDAO chamCongDAO;

    public LuongBUS() {
        luongDAO = new LuongDAO();
        hopDongDAO = new HopDongDAO();
        chamCongDAO = new ChamCongDAO();
    }

    /**
     * Thêm một bản ghi lương mới
     * @param luong Đối tượng lương cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean themLuong(LuongDTO luong) {
        // Kiểm tra hợp lệ
        if (!validateLuong(luong)) {
            return false;
        }
        
        // Thêm vào CSDL
        return luongDAO.insert(luong) > 0;
    }

    /**
     * Cập nhật thông tin lương
     * @param luong Đối tượng lương cần cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean capNhatLuong(LuongDTO luong) {
        // Kiểm tra hợp lệ
        if (!validateLuong(luong)) {
            return false;
        }
        
        // Cập nhật vào CSDL
        return luongDAO.update(luong) > 0;
    }

    /**
     * Xóa một bản ghi lương
     * @param luong Đối tượng lương cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean xoaLuong(LuongDTO luong) {
        return luongDAO.delete(luong) > 0;
    }

    /**
     * Lấy danh sách tất cả bản ghi lương
     * @return ArrayList chứa tất cả bản ghi lương
     */
    public ArrayList<LuongDTO> layDanhSachLuong() {
        return luongDAO.selectAll();
    }

    /**
     * Tìm kiếm lương theo mã lương
     * @param maLuong Mã lương cần tìm
     * @return Đối tượng lương tìm được, null nếu không tìm thấy
     */
    public LuongDTO timLuongTheoMa(String maLuong) {
        LuongDTO luong = new LuongDTO();
        luong.setMaLuong(maLuong);
        return luongDAO.selectById(luong);
    }

    /**
     * Tìm kiếm lương theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList chứa các bản ghi lương thỏa điều kiện
     */
    public ArrayList<LuongDTO> timLuongTheoDieuKien(String condition) {
        return luongDAO.selectByCondition(condition);
    }

    /**
     * Tạo mã lương mới
     * @return String mã lương mới
     */
    public String taoMaLuongMoi() {
        return luongDAO.generateNextMaLuong();
    }

    /**
     * Tính lương thực nhận cho nhân viên
     * @param maNV Mã nhân viên
     * @param thang Tháng tính lương
     * @param nam Năm tính lương
     * @return Đối tượng lương đã tính
     */
    public LuongDTO tinhLuong(String maNV, int thang, int nam) {
        // Lấy hợp đồng hiện tại của nhân viên
        hopDongDTO hopDong = hopDongDAO.layHopDongTheoMaNV(maNV);
        if (hopDong == null) {
            return null;
        }

        // Lấy số ngày công trong tháng
        int tongNgayCong = chamCongDAO.demSoNgayCong(maNV, thang, nam);

        // Tính lương cơ bản theo ngày công
        BigDecimal luongCoBan = BigDecimal.valueOf(hopDong.getLuongCB())
                .multiply(BigDecimal.valueOf(tongNgayCong))
                .divide(BigDecimal.valueOf(26), 2, BigDecimal.ROUND_HALF_UP);

        // Tính lương thưởng (ví dụ: thưởng 10% nếu đủ 26 ngày công)
        BigDecimal luongThuong = BigDecimal.ZERO;
        if (tongNgayCong >= 26) {
            luongThuong = luongCoBan.multiply(BigDecimal.valueOf(0.1));
        }

        // Tính lương thực nhận
        BigDecimal luongThucNhan = luongCoBan.add(luongThuong);

        // Tạo đối tượng lương
        LuongDTO luong = new LuongDTO();
        luong.setMaLuong(taoMaLuongMoi());
        luong.setMaNV(maNV);
        luong.setTongNgayCong(tongNgayCong);
        luong.setLuongCoBan(luongCoBan);
        luong.setLuongThuong(luongThuong);
        luong.setLuongThucNhan(luongThucNhan);

        return luong;
    }

    /**
     * Kiểm tra tính hợp lệ của dữ liệu lương
     * @param luong Đối tượng lương cần kiểm tra
     * @return true nếu hợp lệ, false nếu không hợp lệ
     */
    private boolean validateLuong(LuongDTO luong) {
        if (luong == null) {
            return false;
        }

        // Kiểm tra mã lương
        if (luong.getMaLuong() == null || luong.getMaLuong().trim().isEmpty()) {
            return false;
        }

        // Kiểm tra mã nhân viên
        if (luong.getMaNV() == null || luong.getMaNV().trim().isEmpty()) {
            return false;
        }

        // Kiểm tra số ngày công
        if (luong.getTongNgayCong() == null || luong.getTongNgayCong() < 0 || luong.getTongNgayCong() > 31) {
            return false;
        }

        // Kiểm tra lương cơ bản
        if (luong.getLuongCoBan() == null || luong.getLuongCoBan().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        // Kiểm tra lương thưởng
        if (luong.getLuongThuong() == null || luong.getLuongThuong().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        // Kiểm tra lương thực nhận
        if (luong.getLuongThucNhan() == null || luong.getLuongThucNhan().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        return true;
    }
}
