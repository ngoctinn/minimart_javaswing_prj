package org.example.DAO;

import org.example.DTO.chamCongDTO;
import org.example.DTO.hopDongDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Lớp DAO xử lý các thao tác CSDL liên quan đến chấm công
 * Đại diện cho bảng CHAMCONG trong CSDL
 */
public class ChamCongDAO implements DAOInterface<chamCongDTO> {

    /**
     * Thêm một bản ghi chấm công mới vào CSDL
     * @param chamCong Đối tượng chấm công cần thêm
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int insert(chamCongDTO chamCong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "INSERT INTO CHAMCONG (maNV, thoiGianChamCong) VALUES (?, ?)";
        int result = JDBCUtil.executePreparedUpdate(sql,
                chamCong.getMaNV(),
                chamCong.getThoiGianChamCong());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Cập nhật thông tin chấm công trong CSDL
     * @param chamCong Đối tượng chấm công cần cập nhật
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int update(chamCongDTO chamCong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE CHAMCONG SET thoiGianChamCong = ? WHERE maNV = ? AND thoiGianChamCong = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                chamCong.getThoiGianChamCong(),
                chamCong.getMaNV(),
                chamCong.getThoiGianChamCong());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Xóa một bản ghi chấm công khỏi CSDL
     * @param chamCong Đối tượng chấm công cần xóa
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int delete(chamCongDTO chamCong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "DELETE FROM CHAMCONG WHERE maNV = ? AND thoiGianChamCong = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                chamCong.getMaNV(),
                chamCong.getThoiGianChamCong());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy tất cả bản ghi chấm công từ CSDL
     * @return ArrayList chứa tất cả bản ghi chấm công
     */
    @Override
    public ArrayList<chamCongDTO> selectAll() {
        ArrayList<chamCongDTO> dsChamCong = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM CHAMCONG";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                chamCongDTO chamCong = new chamCongDTO();
                chamCong.setMaNV(resultSet.getString("maNV"));
                chamCong.setThoiGianChamCong(resultSet.getDate("thoiGianChamCong").toLocalDate());
                dsChamCong.add(chamCong);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsChamCong;
    }

    /**
     * Tìm một bản ghi chấm công theo mã nhân viên và thời gian chấm công
     * @param chamCong Đối tượng chấm công cần tìm
     * @return Đối tượng chấm công nếu tìm thấy, null nếu không tìm thấy
     */
    @Override
    public chamCongDTO selectById(chamCongDTO chamCong) {
        chamCongDTO result = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM CHAMCONG WHERE maNV = ? AND thoiGianChamCong = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql,
                    chamCong.getMaNV(),
                    chamCong.getThoiGianChamCong());

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                result = new chamCongDTO();
                result.setMaNV(resultSet.getString("maNV"));
                result.setThoiGianChamCong(resultSet.getDate("thoiGianChamCong").toLocalDate());
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Tìm các bản ghi chấm công theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList chứa các bản ghi chấm công thỏa điều kiện
     */
    @Override
    public ArrayList<chamCongDTO> selectByCondition(String condition) {
        ArrayList<chamCongDTO> dsChamCong = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM CHAMCONG WHERE " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                chamCongDTO chamCong = new chamCongDTO();
                chamCong.setMaNV(resultSet.getString("maNV"));
                chamCong.setThoiGianChamCong(resultSet.getDate("thoiGianChamCong").toLocalDate());
                dsChamCong.add(chamCong);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsChamCong;
    }

    /**
     * Đếm số ngày công của một nhân viên trong tháng và năm cụ thể
     * @param maNV Mã nhân viên cần đếm ngày công
     * @param thang Tháng cần đếm (1-12)
     * @param nam Năm cần đếm
     * @return Số ngày công của nhân viên trong tháng và năm đã cho
     */
    public int demSoNgayCong(String maNV, int thang, int nam) {
        int soNgayCong = 0;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT COUNT(*) as soNgayCong FROM CHAMCONG " +
                        "WHERE maNV = ? AND MONTH(thoiGianChamCong) = ? AND YEAR(thoiGianChamCong) = ?";
            
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, maNV, thang, nam);

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                soNgayCong = resultSet.getInt("soNgayCong");
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return soNgayCong;
    }
}