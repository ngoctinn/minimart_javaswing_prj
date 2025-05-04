package org.example.DAO;

import org.example.DTO.khuyenMaiDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Lớp DAO xử lý các thao tác với bảng KHUYENMAI trong CSDL
 */
public class KhuyenMaiDAO implements DAOInterface<khuyenMaiDTO> {

    /**
     * Thêm một khuyến mãi mới vào CSDL
     * @param khuyenMai Đối tượng khuyến mãi cần thêm
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int insert(khuyenMaiDTO khuyenMai) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "INSERT INTO KHUYENMAI (maKM, tenKM, dieuKien, ngayBD, ngayKT, phanTram, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int result = JDBCUtil.executePreparedUpdate(sql,
                khuyenMai.getMaKM(),
                khuyenMai.getTenKM(),
                khuyenMai.getDieuKien(),
                khuyenMai.getNgayBD(),
                khuyenMai.getNgayKT(),
                khuyenMai.getPhanTram(),
                khuyenMai.isTrangThai());
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Cập nhật thông tin khuyến mãi trong CSDL
     * @param khuyenMai Đối tượng khuyến mãi cần cập nhật
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int update(khuyenMaiDTO khuyenMai) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "UPDATE KHUYENMAI SET tenKM = ?, dieuKien = ?, ngayBD = ?, ngayKT = ?, phanTram = ?, trangThai = ? WHERE maKM = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                khuyenMai.getTenKM(),
                khuyenMai.getDieuKien(),
                khuyenMai.getNgayBD(),
                khuyenMai.getNgayKT(),
                khuyenMai.getPhanTram(),
                khuyenMai.isTrangThai(),
                khuyenMai.getMaKM());
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Xóa khuyến mãi khỏi CSDL (cập nhật trạng thái)
     * @param khuyenMai Đối tượng khuyến mãi cần xóa
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int delete(khuyenMaiDTO khuyenMai) {
        Connection connection = JDBCUtil.getConnection();
        String sql = "UPDATE KHUYENMAI SET trangThai = 0 WHERE maKM = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, khuyenMai.getMaKM());
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy danh sách tất cả khuyến mãi từ CSDL
     * @return ArrayList<khuyenMaiDTO> danh sách khuyến mãi
     */
    @Override
    public ArrayList<khuyenMaiDTO> selectAll() {
        ArrayList<khuyenMaiDTO> dsKhuyenMai = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM KHUYENMAI";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            while (resultSet.next()) {
                khuyenMaiDTO khuyenMai = new khuyenMaiDTO();
                khuyenMai.setMaKM(resultSet.getString("maKM"));
                khuyenMai.setTenKM(resultSet.getString("tenKM"));
                khuyenMai.setDieuKien(resultSet.getString("dieuKien"));
                khuyenMai.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                khuyenMai.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                khuyenMai.setPhanTram(resultSet.getDouble("phanTram"));
                khuyenMai.setTrangThai(resultSet.getBoolean("trangThai"));

                dsKhuyenMai.add(khuyenMai);
            }

            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKhuyenMai;
    }

    /**
     * Lấy danh sách khuyến mãi đang hoạt động
     * @return ArrayList<khuyenMaiDTO> danh sách khuyến mãi đang hoạt động
     */
    public ArrayList<khuyenMaiDTO> layDanhSachKhuyenMai() {
        ArrayList<khuyenMaiDTO> dsKhuyenMai = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM KHUYENMAI WHERE trangThai = 1";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            while (resultSet.next()) {
                khuyenMaiDTO khuyenMai = new khuyenMaiDTO();
                khuyenMai.setMaKM(resultSet.getString("maKM"));
                khuyenMai.setTenKM(resultSet.getString("tenKM"));
                khuyenMai.setDieuKien(resultSet.getString("dieuKien"));
                khuyenMai.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                khuyenMai.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                khuyenMai.setPhanTram(resultSet.getDouble("phanTram"));
                khuyenMai.setTrangThai(resultSet.getBoolean("trangThai"));

                dsKhuyenMai.add(khuyenMai);
            }

            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKhuyenMai;
    }

    /**
     * Lấy thông tin khuyến mãi theo mã khuyến mãi
     * @param khuyenMai Đối tượng khuyến mãi chứa mã khuyến mãi cần tìm
     * @return khuyenMaiDTO Đối tượng khuyến mãi tìm thấy, null nếu không tìm thấy
     */
    @Override
    public khuyenMaiDTO selectById(khuyenMaiDTO khuyenMai) {
        khuyenMaiDTO result = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM KHUYENMAI WHERE maKM = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, khuyenMai.getMaKM());

            if (resultSet.next()) {
                result = new khuyenMaiDTO();
                result.setMaKM(resultSet.getString("maKM"));
                result.setTenKM(resultSet.getString("tenKM"));
                result.setDieuKien(resultSet.getString("dieuKien"));
                result.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                result.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                result.setPhanTram(resultSet.getDouble("phanTram"));
                result.setTrangThai(resultSet.getBoolean("trangThai"));
            }

            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách khuyến mãi theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList<khuyenMaiDTO> danh sách khuyến mãi thỏa điều kiện
     */
    @Override
    public ArrayList<khuyenMaiDTO> selectByCondition(String condition) {
        ArrayList<khuyenMaiDTO> dsKhuyenMai = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM KHUYENMAI WHERE " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            while (resultSet.next()) {
                khuyenMaiDTO khuyenMai = new khuyenMaiDTO();
                khuyenMai.setMaKM(resultSet.getString("maKM"));
                khuyenMai.setTenKM(resultSet.getString("tenKM"));
                khuyenMai.setDieuKien(resultSet.getString("dieuKien"));
                khuyenMai.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                khuyenMai.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                khuyenMai.setPhanTram(resultSet.getDouble("phanTram"));
                khuyenMai.setTrangThai(resultSet.getBoolean("trangThai"));

                dsKhuyenMai.add(khuyenMai);
            }

            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKhuyenMai;
    }

    /**
     * Tìm kiếm khuyến mãi theo từ khóa
     * @param keyword Từ khóa tìm kiếm
     * @return ArrayList<khuyenMaiDTO> danh sách khuyến mãi tìm thấy
     */
    public ArrayList<khuyenMaiDTO> timKiemKhuyenMai(String keyword) {
        ArrayList<khuyenMaiDTO> dsKhuyenMai = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM KHUYENMAI WHERE (maKM LIKE ? OR tenKM LIKE ?) AND trangThai = 1";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, "%" + keyword + "%", "%" + keyword + "%");

            while (resultSet.next()) {
                khuyenMaiDTO khuyenMai = new khuyenMaiDTO();
                khuyenMai.setMaKM(resultSet.getString("maKM"));
                khuyenMai.setTenKM(resultSet.getString("tenKM"));
                khuyenMai.setDieuKien(resultSet.getString("dieuKien"));
                khuyenMai.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                khuyenMai.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                khuyenMai.setPhanTram(resultSet.getDouble("phanTram"));
                khuyenMai.setTrangThai(resultSet.getBoolean("trangThai"));

                dsKhuyenMai.add(khuyenMai);
            }

            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKhuyenMai;
    }

    /**
     * Lấy danh sách khuyến mãi đang có hiệu lực
     * @return ArrayList<khuyenMaiDTO> danh sách khuyến mãi đang có hiệu lực
     */
    public ArrayList<khuyenMaiDTO> layDanhSachKhuyenMaiHieuLuc() {
        ArrayList<khuyenMaiDTO> dsKhuyenMai = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            LocalDate today = LocalDate.now();
            String sql = "SELECT * FROM KHUYENMAI WHERE trangThai = 1 AND ngayBD <= ? AND ngayKT >= ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, today, today);

            while (resultSet.next()) {
                khuyenMaiDTO khuyenMai = new khuyenMaiDTO();
                khuyenMai.setMaKM(resultSet.getString("maKM"));
                khuyenMai.setTenKM(resultSet.getString("tenKM"));
                khuyenMai.setDieuKien(resultSet.getString("dieuKien"));
                khuyenMai.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                khuyenMai.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                khuyenMai.setPhanTram(resultSet.getDouble("phanTram"));
                khuyenMai.setTrangThai(resultSet.getBoolean("trangThai"));

                dsKhuyenMai.add(khuyenMai);
            }

            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKhuyenMai;
    }

    /**
     * Tạo mã khuyến mãi mới tự động tăng
     * @return String mã khuyến mãi mới
     */
    public String taoMaKhuyenMaiMoi() {
        String maKM = "KM001";
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT MAX(maKM) AS MaxMaKM FROM KHUYENMAI";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getString("MaxMaKM") != null) {
                String maxMaKM = resultSet.getString("MaxMaKM");
                int soMaKM = Integer.parseInt(maxMaKM.substring(2)) + 1;
                maKM = "KM" + String.format("%03d", soMaKM);
            }

            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maKM;
    }
}
