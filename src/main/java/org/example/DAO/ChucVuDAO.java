package org.example.DAO;

import org.example.DTO.ChucVuDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChucVuDAO {
    private Connection conn;

    public ChucVuDAO() {
        try {
            conn = JDBCUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy danh sách tất cả chức vụ từ cơ sở dữ liệu
     *
     * @return ArrayList<ChucVuDTO> danh sách chức vụ
     */
    public ArrayList<ChucVuDTO> layDanhSachChucVu() {
        ArrayList<ChucVuDTO> danhSachChucVu = new ArrayList<>();
        String sql = "SELECT * FROM CHUCVU WHERE trangThai = 1";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maCV = rs.getString("MaCV");
                String tenCV = rs.getString("TenCV");

                ChucVuDTO chucVu = new ChucVuDTO(tenCV, maCV);
                danhSachChucVu.add(chucVu);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachChucVu;
    }

    /**
     * Thêm một chức vụ mới vào cơ sở dữ liệu
     *
     * @param chucVu ChucVuDTO chức vụ cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public int themChucVu(ChucVuDTO chucVu) {
        String sql = "INSERT INTO ChucVu (maCV, tenCV, trangThai) VALUES (?, ?, 1)";
        int result = 0;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, chucVu.getMaCV());
            ps.setString(2, chucVu.getTenCV());

            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Cập nhật thông tin chức vụ
     *
     * @param chucVu ChucVuDTO chức vụ cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public int capNhatChucVu(ChucVuDTO chucVu) {
        String sql = "UPDATE CHUCVU SET tenCV = ? WHERE maCV = ?";
        int result = 0;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, chucVu.getTenCV());
            ps.setString(2, chucVu.getMaCV());

            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Xóa một chức vụ khỏi cơ sở dữ liệu
     *
     * @param maCV String mã chức vụ cần xóa
     * @return int số dòng bị ảnh hưởng
     */
    public int xoaChucVu(String maCV) {
        String sql = "UPDATE CHUCVU SET trangThai = 0 WHERE maCV = ?";
        int result = 0;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maCV);

            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Tìm kiếm chức vụ theo từ khóa
     *
     * @param keyword String từ khóa tìm kiếm
     * @return ArrayList<ChucVuDTO> danh sách chức vụ tìm thấy
     */
    public ArrayList<ChucVuDTO> timKiemChucVu(String keyword) {
        ArrayList<ChucVuDTO> danhSachChucVu = new ArrayList<>();
        String sql = "SELECT * FROM CHUCVU WHERE maCV LIKE ? OR TenCV LIKE ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maCV = rs.getString("maCV");
                String tenCV = rs.getString("tenCV");

                ChucVuDTO chucVu = new ChucVuDTO(tenCV, maCV);
                danhSachChucVu.add(chucVu);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachChucVu;
    }

    /**
     * Lấy mã chức vụ cuối cùng để tạo mã mới
     *
     * @return String mã chức vụ cuối cùng
     */
    public String layMaChucVuCuoiCung() {
        String sql = "SELECT TOP 1 MaCV FROM CHUCVU ORDER BY maCV DESC";
        String maCV = "CV000";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maCV = rs.getString("maCV");
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maCV;
    }
}




