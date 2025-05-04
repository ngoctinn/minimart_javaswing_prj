package org.example.DAO;

import org.example.DTO.hopDongDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class HopDongDAO implements DAOInterface<hopDongDTO> {

    @Override
    public int insert(hopDongDTO hopDong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "INSERT INTO HOPDONG (maHopDong, ngayBD, ngayKT, luongCB, maNV, trangThai, loaiHopDong) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int result = JDBCUtil.executePreparedUpdate(sql,
                hopDong.getMaHopDong(),
                hopDong.getNgayBD(),
                hopDong.getNgayKT(),
                hopDong.getLuongCB(),
                hopDong.getMaNV(),
                hopDong.isTrangThai(),
                hopDong.getLoaiHopDong());
        return result;
    }

    @Override
    public int update(hopDongDTO hopDong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "UPDATE HOPDONG SET ngayBD = ?, ngayKT = ?, luongCB = ?, maNV = ?, trangThai = ?, loaiHopDong = ? WHERE maHopDong = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                hopDong.getNgayBD(),
                hopDong.getNgayKT(),
                hopDong.getLuongCB(),
                hopDong.getMaNV(),
                hopDong.isTrangThai(),
                hopDong.getLoaiHopDong(),
                hopDong.getMaHopDong());
        return result;
    }

    @Override
    public int delete(hopDongDTO hopDong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "UPDATE HOPDONG SET trangThai = 0 WHERE maHopDong = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, hopDong.getMaHopDong());
        return result;
    }

    @Override
    public ArrayList<hopDongDTO> selectAll() {
        ArrayList<hopDongDTO> dsHopDong = new ArrayList<>();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM HOPDONG";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            while (resultSet.next()) {
                hopDongDTO hopDong = new hopDongDTO();
                hopDong.setMaHopDong(resultSet.getString("maHopDong"));
                hopDong.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                hopDong.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                hopDong.setLuongCB(resultSet.getDouble("luongCB"));
                hopDong.setMaNV(resultSet.getString("maNV"));
                hopDong.setTrangThai(resultSet.getBoolean("trangThai"));
                hopDong.setLoaiHopDong(resultSet.getString("loaiHopDong"));
                dsHopDong.add(hopDong);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHopDong;
    }

    /**
     * Lấy danh sách hợp đồng đang hoạt động (trangThai = 1)
     * @return ArrayList<hopDongDTO> danh sách hợp đồng đang hoạt động
     */
    public ArrayList<hopDongDTO> layDanhSachHopDong() {
        ArrayList<hopDongDTO> dsHopDong = new ArrayList<>();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM HOPDONG WHERE trangThai = 1";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            while (resultSet.next()) {
                hopDongDTO hopDong = new hopDongDTO();
                hopDong.setMaHopDong(resultSet.getString("maHopDong"));
                hopDong.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                hopDong.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                hopDong.setLuongCB(resultSet.getDouble("luongCB"));
                hopDong.setMaNV(resultSet.getString("maNV"));
                hopDong.setTrangThai(resultSet.getBoolean("trangThai"));
                hopDong.setLoaiHopDong(resultSet.getString("loaiHopDong"));
                dsHopDong.add(hopDong);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHopDong;
    }

    @Override
    public hopDongDTO selectById(hopDongDTO hopDong) {
        hopDongDTO result = null;
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM HOPDONG WHERE maHopDong = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, hopDong.getMaHopDong());
            if (resultSet.next()) {
                result = new hopDongDTO();
                result.setMaHopDong(resultSet.getString("maHopDong"));
                result.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                result.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                result.setLuongCB(resultSet.getDouble("luongCB"));
                result.setMaNV(resultSet.getString("maNV"));
                result.setTrangThai(resultSet.getBoolean("trangThai"));
                result.setLoaiHopDong(resultSet.getString("loaiHopDong"));
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<hopDongDTO> selectByCondition(String condition) {
        ArrayList<hopDongDTO> dsHopDong = new ArrayList<>();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM HOPDONG WHERE " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            while (resultSet.next()) {
                hopDongDTO hopDong = new hopDongDTO();
                hopDong.setMaHopDong(resultSet.getString("maHopDong"));
                hopDong.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                hopDong.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                hopDong.setLuongCB(resultSet.getDouble("luongCB"));
                hopDong.setMaNV(resultSet.getString("maNV"));
                hopDong.setTrangThai(resultSet.getBoolean("trangThai"));
                hopDong.setLoaiHopDong(resultSet.getString("loaiHopDong"));
                dsHopDong.add(hopDong);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHopDong;
    }

    /**
     * Tìm kiếm hợp đồng theo mã nhân viên hoặc mã hợp đồng
     * @param keyword Từ khóa tìm kiếm
     * @return ArrayList<hopDongDTO> danh sách hợp đồng tìm thấy
     */
    public ArrayList<hopDongDTO> timKiemHopDong(String keyword) {
        ArrayList<hopDongDTO> dsHopDong = new ArrayList<>();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM HOPDONG WHERE trangThai = 1 AND (maHopDong LIKE ? OR maNV LIKE ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hopDongDTO hopDong = new hopDongDTO();
                hopDong.setMaHopDong(resultSet.getString("maHopDong"));
                hopDong.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                hopDong.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                hopDong.setLuongCB(resultSet.getDouble("luongCB"));
                hopDong.setMaNV(resultSet.getString("maNV"));
                hopDong.setTrangThai(resultSet.getBoolean("trangThai"));
                hopDong.setLoaiHopDong(resultSet.getString("loaiHopDong"));
                dsHopDong.add(hopDong);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHopDong;
    }

    /**
     * Tạo mã hợp đồng mới tự động tăng
     * @return String mã hợp đồng mới
     */
    public String generateNextMaHopDong() {
        String maHopDong = "HD001";
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT MAX(maHopDong) AS MaxMaHopDong FROM HOPDONG";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getString("MaxMaHopDong") != null) {
                String maxMaHopDong = resultSet.getString("MaxMaHopDong");
                int soMaHopDong = Integer.parseInt(maxMaHopDong.substring(2)) + 1;
                maHopDong = "HD" + String.format("%03d", soMaHopDong);
            }

            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maHopDong;
    }

    /**
     * Lấy hợp đồng theo mã nhân viên
     * @param maNV Mã nhân viên
     * @return hopDongDTO hợp đồng của nhân viên
     */
    public hopDongDTO layHopDongTheoMaNV(String maNV) {
        hopDongDTO result = null;
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM HOPDONG WHERE maNV = ? AND trangThai = 1";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, maNV);
            if (resultSet.next()) {
                result = new hopDongDTO();
                result.setMaHopDong(resultSet.getString("maHopDong"));
                result.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                result.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                result.setLuongCB(resultSet.getDouble("luongCB"));
                result.setMaNV(resultSet.getString("maNV"));
                result.setTrangThai(resultSet.getBoolean("trangThai"));
                result.setLoaiHopDong(resultSet.getString("loaiHopDong"));
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Main để test
    public static void main(String[] args) {
        HopDongDAO hopDongDAO = new HopDongDAO();

        // Test lấy danh sách hợp đồng
        ArrayList<hopDongDTO> dsHopDong = hopDongDAO.selectAll();
        for (hopDongDTO hopDong : dsHopDong) {
            System.out.println(hopDong.getMaHopDong() + " - " + hopDong.getMaNV() + " - " +
                    hopDong.getLuongCB() + " - " + hopDong.getNgayBD() + " - " + hopDong.getNgayKT());
        }

        // Test tạo mã hợp đồng mới
        System.out.println("Mã hợp đồng mới: " + hopDongDAO.generateNextMaHopDong());
    }
}
