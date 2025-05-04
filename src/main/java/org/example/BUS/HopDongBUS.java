package org.example.BUS;

import org.example.DAO.HopDongDAO;
import org.example.DTO.hopDongDTO;

import java.util.ArrayList;

public class HopDongBUS {

    /**
     * Lấy tất cả hợp đồng
     * @return ArrayList<hopDongDTO> danh sách tất cả hợp đồng
     */
    public static ArrayList<hopDongDTO> selectAll() {
        HopDongDAO hopDongDAO = new HopDongDAO();
        ArrayList<hopDongDTO> dsHopDong = hopDongDAO.selectAll();
        return dsHopDong;
    }

    /**
     * Lấy danh sách hợp đồng đang hoạt động
     * @return ArrayList<hopDongDTO> danh sách hợp đồng đang hoạt động
     */
    public static ArrayList<hopDongDTO> layDanhSachHopDong() {
        HopDongDAO hopDongDAO = new HopDongDAO();
        ArrayList<hopDongDTO> dsHopDong = hopDongDAO.layDanhSachHopDong();
        return dsHopDong;
    }

    /**
     * Thêm hợp đồng mới
     * @param hopDongDTO hợp đồng cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public static int themHopDong(hopDongDTO hopDongDTO) {
        HopDongDAO hopDongDAO = new HopDongDAO();
        return hopDongDAO.insert(hopDongDTO);
    }

    /**
     * Xóa hợp đồng (cập nhật trạng thái = 0)
     * @param hopDongDTO hợp đồng cần xóa
     * @return int số dòng bị ảnh hưởng
     */
    public static int xoaHopDong(hopDongDTO hopDongDTO) {
        HopDongDAO hopDongDAO = new HopDongDAO();
        return hopDongDAO.delete(hopDongDTO);
    }

    /**
     * Cập nhật thông tin hợp đồng
     * @param hopDongDTO hợp đồng cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatHopDong(hopDongDTO hopDongDTO) {
        HopDongDAO hopDongDAO = new HopDongDAO();
        return hopDongDAO.update(hopDongDTO);
    }

    /**
     * Tìm kiếm hợp đồng theo từ khóa
     * @param keyword từ khóa tìm kiếm
     * @return ArrayList<hopDongDTO> danh sách hợp đồng tìm thấy
     */
    public static ArrayList<hopDongDTO> timKiemHopDong(String keyword) {
        HopDongDAO hopDongDAO = new HopDongDAO();
        return hopDongDAO.timKiemHopDong(keyword);
    }

    /**
     * Tạo mã hợp đồng mới tự động tăng
     * @return String mã hợp đồng mới
     */
    public static String generateNextMaHopDong() {
        HopDongDAO hopDongDAO = new HopDongDAO();
        return hopDongDAO.generateNextMaHopDong();
    }

    /**
     * Lấy hợp đồng theo mã nhân viên
     * @param maNV mã nhân viên
     * @return hopDongDTO hợp đồng của nhân viên
     */
    public static hopDongDTO layHopDongTheoMaNV(String maNV) {
        HopDongDAO hopDongDAO = new HopDongDAO();
        return hopDongDAO.layHopDongTheoMaNV(maNV);
    }
}
