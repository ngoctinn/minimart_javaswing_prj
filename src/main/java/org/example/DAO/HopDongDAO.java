package org.example.DAO;

import org.example.DTO.hopDongDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Lớp DAO xử lý các thao tác CSDL liên quan đến hợp đồng nhân viên
 * Đại diện cho bảng HOPDONG trong CSDL
 */
public class HopDongDAO implements DAOInterface<hopDongDTO> {

    /**
     * Thêm một hợp đồng mới vào CSDL
     * @param hopDong Đối tượng hợp đồng cần thêm
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int insert(hopDongDTO hopDong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "INSERT INTO HOPDONG (maHopDong, ngayBD, ngayKT, luongCB, maNV, trangThai, hinhThucLamViec) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int result = JDBCUtil.executePreparedUpdate(sql,
                hopDong.getMaHopDong(),
                hopDong.getNgayBD(),
                hopDong.getNgayKT(),
                hopDong.getLuongCB(),
                hopDong.getMaNV(),
                hopDong.isTrangThai(),
                hopDong.getHinhThucLamViec());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Cập nhật thông tin hợp đồng trong CSDL
     * @param hopDong Đối tượng hợp đồng cần cập nhật
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int update(hopDongDTO hopDong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE HOPDONG SET ngayBD = ?, ngayKT = ?, luongCB = ?, maNV = ?, trangThai = ?, hinhThucLamViec = ? WHERE maHopDong = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                hopDong.getNgayBD(),
                hopDong.getNgayKT(),
                hopDong.getLuongCB(),
                hopDong.getMaNV(),
                hopDong.isTrangThai(),
                hopDong.getHinhThucLamViec(),
                hopDong.getMaHopDong());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Xóa hợp đồng khỏi CSDL (cập nhật trạng thái thành false)
     * @param hopDong Đối tượng hợp đồng cần xóa
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int delete(hopDongDTO hopDong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL (xóa logic bằng cách cập nhật trạng thái)
        String sql = "UPDATE HOPDONG SET trangThai = 0 WHERE maHopDong = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, hopDong.getMaHopDong());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy danh sách tất cả hợp đồng từ CSDL
     * @return ArrayList chứa tất cả hợp đồng
     */
    @Override
    public ArrayList<hopDongDTO> selectAll() {
        ArrayList<hopDongDTO> dsHopDong = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOPDONG";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                hopDongDTO hopDong = new hopDongDTO();
                hopDong.setMaHopDong(resultSet.getString("maHopDong"));
                hopDong.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                hopDong.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                hopDong.setLuongCB(resultSet.getDouble("luongCB"));
                hopDong.setMaNV(resultSet.getString("maNV"));
                hopDong.setTrangThai(resultSet.getBoolean("trangThai"));
                hopDong.setHinhThucLamViec(resultSet.getString("hinhThucLamViec"));

                dsHopDong.add(hopDong);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHopDong;
    }

    /**
     * Lấy danh sách hợp đồng đang hoạt động (trangThai = 1)
     * @return ArrayList chứa hợp đồng đang hoạt động
     */
    public ArrayList<hopDongDTO> layDanhSachHopDong() {
        ArrayList<hopDongDTO> dsHopDong = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOPDONG WHERE trangThai = 1";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                hopDongDTO hopDong = new hopDongDTO();
                hopDong.setMaHopDong(resultSet.getString("maHopDong"));
                hopDong.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                hopDong.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                hopDong.setLuongCB(resultSet.getDouble("luongCB"));
                hopDong.setMaNV(resultSet.getString("maNV"));
                hopDong.setTrangThai(resultSet.getBoolean("trangThai"));
                hopDong.setHinhThucLamViec(resultSet.getString("hinhThucLamViec"));

                dsHopDong.add(hopDong);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHopDong;
    }

    /**
     * Tìm hợp đồng theo mã hợp đồng
     * @param hopDong Đối tượng hợp đồng chứa mã hợp đồng cần tìm
     * @return Đối tượng hợp đồng tìm được, null nếu không tìm thấy
     */
    @Override
    public hopDongDTO selectById(hopDongDTO hopDong) {
        hopDongDTO result = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOPDONG WHERE maHopDong = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, hopDong.getMaHopDong());

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                result = new hopDongDTO();
                result.setMaHopDong(resultSet.getString("maHopDong"));
                result.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                result.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                result.setLuongCB(resultSet.getDouble("luongCB"));
                result.setMaNV(resultSet.getString("maNV"));
                result.setTrangThai(resultSet.getBoolean("trangThai"));
                result.setHinhThucLamViec(resultSet.getString("hinhThucLamViec"));
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Tìm hợp đồng theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList chứa các hợp đồng thỏa điều kiện
     */
    @Override
    public ArrayList<hopDongDTO> selectByCondition(String condition) {
        ArrayList<hopDongDTO> dsHopDong = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOPDONG WHERE " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                hopDongDTO hopDong = new hopDongDTO();
                hopDong.setMaHopDong(resultSet.getString("maHopDong"));
                hopDong.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                hopDong.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                hopDong.setLuongCB(resultSet.getDouble("luongCB"));
                hopDong.setMaNV(resultSet.getString("maNV"));
                hopDong.setTrangThai(resultSet.getBoolean("trangThai"));
                hopDong.setHinhThucLamViec(resultSet.getString("hinhThucLamViec"));

                dsHopDong.add(hopDong);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHopDong;
    }

    /**
     * Tìm kiếm hợp đồng theo mã nhân viên hoặc mã hợp đồng
     * @param keyword Từ khóa tìm kiếm
     * @return ArrayList chứa các hợp đồng tìm thấy
     */
    public ArrayList<hopDongDTO> timKiemHopDong(String keyword) {
        ArrayList<hopDongDTO> dsHopDong = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOPDONG WHERE trangThai = 1 AND (maHopDong LIKE ? OR maNV LIKE ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                hopDongDTO hopDong = new hopDongDTO();
                hopDong.setMaHopDong(resultSet.getString("maHopDong"));
                hopDong.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                hopDong.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                hopDong.setLuongCB(resultSet.getDouble("luongCB"));
                hopDong.setMaNV(resultSet.getString("maNV"));
                hopDong.setTrangThai(resultSet.getBoolean("trangThai"));
                hopDong.setHinhThucLamViec(resultSet.getString("hinhThucLamViec"));

                dsHopDong.add(hopDong);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHopDong;
    }

    /**
     * Tạo mã hợp đồng mới tự động tăng
     * @return Mã hợp đồng mới
     */
    public String generateNextMaHopDong() {
        String maHopDong = "HD001";
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT MAX(maHopDong) AS MaxMaHopDong FROM HOPDONG";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next() && resultSet.getString("MaxMaHopDong") != null) {
                String maxMaHopDong = resultSet.getString("MaxMaHopDong");
                int soMaHopDong = Integer.parseInt(maxMaHopDong.substring(2)) + 1;
                maHopDong = "HD" + String.format("%03d", soMaHopDong);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maHopDong;
    }

    /**
     * Lấy hợp đồng theo mã nhân viên
     * @param maNV Mã nhân viên
     * @return Đối tượng hợp đồng của nhân viên, null nếu không tìm thấy
     */
    public hopDongDTO layHopDongTheoMaNV(String maNV) {
        hopDongDTO result = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOPDONG WHERE maNV = ? AND trangThai = 1";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, maNV);

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                result = new hopDongDTO();
                result.setMaHopDong(resultSet.getString("maHopDong"));
                result.setNgayBD(resultSet.getDate("ngayBD").toLocalDate());
                result.setNgayKT(resultSet.getDate("ngayKT").toLocalDate());
                result.setLuongCB(resultSet.getDouble("luongCB"));
                result.setMaNV(resultSet.getString("maNV"));
                result.setTrangThai(resultSet.getBoolean("trangThai"));
                result.setHinhThucLamViec(resultSet.getString("hinhThucLamViec"));
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Kiểm tra hợp đồng có hết hạn chưa
     * @param maHopDong Mã hợp đồng cần kiểm tra
     * @return true nếu hợp đồng đã hết hạn, false nếu còn hiệu lực
     */
    public boolean kiemTraHopDongHetHan(String maHopDong) {
        boolean hetHan = false;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOPDONG WHERE maHopDong = ? AND trangThai = 1";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, maHopDong);

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                LocalDate ngayKT = resultSet.getDate("ngayKT").toLocalDate();
                hetHan = ngayKT.isBefore(LocalDate.now());
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hetHan;
    }

    /**
     * Phương thức main để test các chức năng của DAO
     */
    public static void main(String[] args) {
        // Tạo đối tượng DAO
        HopDongDAO hopDongDAO = new HopDongDAO();

        // Test lấy danh sách hợp đồng
        System.out.println("=== Danh sách hợp đồng ===");
        ArrayList<hopDongDTO> dsHopDong = hopDongDAO.selectAll();
        for (hopDongDTO hopDong : dsHopDong) {
            System.out.println(hopDong.getMaHopDong() + " - " + hopDong.getMaNV() + " - " +
                    hopDong.getLuongCB() + " - " + hopDong.getNgayBD() + " - " + hopDong.getNgayKT());
        }

        // Test tạo mã hợp đồng mới
        System.out.println("\n=== Mã hợp đồng mới ===");
        System.out.println("Mã hợp đồng mới: " + hopDongDAO.generateNextMaHopDong());
    }
}