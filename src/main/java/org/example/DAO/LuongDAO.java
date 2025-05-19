package org.example.DAO;

import org.example.DTO.LuongDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Lớp DAO xử lý các thao tác CSDL liên quan đến lương nhân viên
 * Đại diện cho bảng LUONG trong CSDL
 */
public class LuongDAO implements DAOInterface<LuongDTO> {

    /**
     * Thêm một bản ghi lương mới vào CSDL
     * @param luong Đối tượng lương cần thêm
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int insert(LuongDTO luong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "INSERT INTO LUONG (maLuong, maNV, tongNgayCong, luongCoBan, luongThuong, luongThucNhan) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        int result = JDBCUtil.executePreparedUpdate(sql,
                luong.getMaLuong(),
                luong.getMaNV(),
                luong.getTongNgayCong(),
                luong.getLuongCoBan(),
                luong.getLuongThuong(),
                luong.getLuongThucNhan());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Cập nhật thông tin lương trong CSDL
     * @param luong Đối tượng lương cần cập nhật
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int update(LuongDTO luong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE LUONG SET maNV = ?, tongNgayCong = ?, luongCoBan = ?, " +
                     "luongThuong = ?, luongThucNhan = ? WHERE maLuong = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                luong.getMaNV(),
                luong.getTongNgayCong(),
                luong.getLuongCoBan(),
                luong.getLuongThuong(),
                luong.getLuongThucNhan(),
                luong.getMaLuong());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Xóa một bản ghi lương khỏi CSDL
     * @param luong Đối tượng lương cần xóa
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int delete(LuongDTO luong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "DELETE FROM LUONG WHERE maLuong = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, luong.getMaLuong());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy tất cả bản ghi lương từ CSDL
     * @return ArrayList chứa tất cả bản ghi lương
     */
    @Override
    public ArrayList<LuongDTO> selectAll() {
        ArrayList<LuongDTO> dsLuong = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM LUONG";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                LuongDTO luong = new LuongDTO();
                luong.setMaLuong(resultSet.getString("maLuong"));
                luong.setMaNV(resultSet.getString("maNV"));
                luong.setTongNgayCong(resultSet.getInt("tongNgayCong"));
                luong.setLuongCoBan(resultSet.getBigDecimal("luongCoBan"));
                luong.setLuongThuong(resultSet.getBigDecimal("luongThuong"));
                luong.setLuongThucNhan(resultSet.getBigDecimal("luongThucNhan"));
                dsLuong.add(luong);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLuong;
    }

    /**
     * Tìm một bản ghi lương theo mã lương
     * @param luong Đối tượng lương chứa mã lương cần tìm
     * @return Đối tượng lương tìm được, null nếu không tìm thấy
     */
    @Override
    public LuongDTO selectById(LuongDTO luong) {
        LuongDTO result = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM LUONG WHERE maLuong = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, luong.getMaLuong());

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                result = new LuongDTO();
                result.setMaLuong(resultSet.getString("maLuong"));
                result.setMaNV(resultSet.getString("maNV"));
                result.setTongNgayCong(resultSet.getInt("tongNgayCong"));
                result.setLuongCoBan(resultSet.getBigDecimal("luongCoBan"));
                result.setLuongThuong(resultSet.getBigDecimal("luongThuong"));
                result.setLuongThucNhan(resultSet.getBigDecimal("luongThucNhan"));
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Tìm các bản ghi lương theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList chứa các bản ghi lương thỏa điều kiện
     */
    @Override
    public ArrayList<LuongDTO> selectByCondition(String condition) {
        ArrayList<LuongDTO> dsLuong = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM LUONG WHERE " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                LuongDTO luong = new LuongDTO();
                luong.setMaLuong(resultSet.getString("maLuong"));
                luong.setMaNV(resultSet.getString("maNV"));
                luong.setTongNgayCong(resultSet.getInt("tongNgayCong"));
                luong.setLuongCoBan(resultSet.getBigDecimal("luongCoBan"));
                luong.setLuongThuong(resultSet.getBigDecimal("luongThuong"));
                luong.setLuongThucNhan(resultSet.getBigDecimal("luongThucNhan"));
                dsLuong.add(luong);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLuong;
    }

    /**
     * Tạo mã lương mới
     * @return String mã lương mới
     */
    public String generateNextMaLuong() {
        String prefix = "L";
        String sql = "SELECT TOP 1 maLuong FROM LUONG ORDER BY maLuong DESC";
        String lastMaLuong = null;
        
        try {
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            if (resultSet.next()) {
                lastMaLuong = resultSet.getString("maLuong");
            }
            
            if (lastMaLuong == null) {
                return prefix + "001";
            }
            
            int number = Integer.parseInt(lastMaLuong.substring(1)) + 1;
            return prefix + String.format("%03d", number);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
} 