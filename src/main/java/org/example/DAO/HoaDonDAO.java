package org.example.DAO;

import org.example.DTO.hoaDonDTO;
import org.example.DTO.chiTietHoaDonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Lớp DAO xử lý các thao tác CSDL liên quan đến hóa đơn
 * Đại diện cho bảng HOADON trong CSDL
 */
public class HoaDonDAO implements DAOInterface<hoaDonDTO> {

    /**
     * Thêm một hóa đơn mới vào CSDL
     * @param hoaDon Đối tượng hóa đơn cần thêm
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int insert(hoaDonDTO hoaDon) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "INSERT INTO HOADON (maHoaDon, tongTien, maKH, maNV, maKM, thoiGianLap, trangThai) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        int result = JDBCUtil.executePreparedUpdate(sql,
                hoaDon.getMaHoaDon(),
                hoaDon.getTongTien(),
                hoaDon.getMaKH(),
                hoaDon.getMaNV(),
                hoaDon.getMaKM(),
                hoaDon.getThoiGianLap(),
                hoaDon.isTrangThai());
        
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Cập nhật thông tin hóa đơn trong CSDL
     * @param hoaDon Đối tượng hóa đơn cần cập nhật
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int update(hoaDonDTO hoaDon) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE HOADON SET tongTien = ?, maKH = ?, maNV = ?, maKM = ?, thoiGianLap = ?, trangThai = ? " +
                     "WHERE maHoaDon = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                hoaDon.getTongTien(),
                hoaDon.getMaKH(),
                hoaDon.getMaNV(),
                hoaDon.getMaKM(),
                hoaDon.getThoiGianLap(),
                hoaDon.isTrangThai(),
                hoaDon.getMaHoaDon());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Xóa hóa đơn khỏi CSDL (cập nhật trạng thái thành false)
     * @param hoaDon Đối tượng hóa đơn cần xóa
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int delete(hoaDonDTO hoaDon) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL (xóa logic bằng cách cập nhật trạng thái)
        String sql = "UPDATE HOADON SET trangThai = 0 WHERE maHoaDon = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, hoaDon.getMaHoaDon());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy danh sách tất cả hóa đơn từ CSDL
     * @return ArrayList chứa tất cả hóa đơn
     */
    @Override
    public ArrayList<hoaDonDTO> selectAll() {
        ArrayList<hoaDonDTO> dsHoaDon = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOADON";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                hoaDonDTO hoaDon = new hoaDonDTO();
                hoaDon.setMaHoaDon(resultSet.getString("maHoaDon"));
                hoaDon.setTongTien(resultSet.getDouble("tongTien"));
                hoaDon.setMaKH(resultSet.getString("maKH"));
                hoaDon.setMaNV(resultSet.getString("maNV"));
                hoaDon.setMaKM(resultSet.getString("maKM"));
                hoaDon.setThoiGianLap(resultSet.getTimestamp("thoiGianLap").toLocalDateTime());
                hoaDon.setTrangThai(resultSet.getBoolean("trangThai"));

                dsHoaDon.add(hoaDon);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }

    /**
     * Lấy danh sách hóa đơn đang hoạt động (trangThai = 1)
     * @return ArrayList chứa hóa đơn đang hoạt động
     */
    public ArrayList<hoaDonDTO> layDanhSachHoaDon() {
        ArrayList<hoaDonDTO> dsHoaDon = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOADON WHERE trangThai = 1";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                hoaDonDTO hoaDon = new hoaDonDTO();
                hoaDon.setMaHoaDon(resultSet.getString("maHoaDon"));
                hoaDon.setTongTien(resultSet.getDouble("tongTien"));
                hoaDon.setMaKH(resultSet.getString("maKH"));
                hoaDon.setMaNV(resultSet.getString("maNV"));
                hoaDon.setMaKM(resultSet.getString("maKM"));
                hoaDon.setThoiGianLap(resultSet.getTimestamp("thoiGianLap").toLocalDateTime());
                hoaDon.setTrangThai(resultSet.getBoolean("trangThai"));

                dsHoaDon.add(hoaDon);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }

    /**
     * Tìm hóa đơn theo mã hóa đơn
     * @param hoaDon Đối tượng hóa đơn chứa mã hóa đơn cần tìm
     * @return Đối tượng hóa đơn tìm được, null nếu không tìm thấy
     */
    @Override
    public hoaDonDTO selectById(hoaDonDTO hoaDon) {
        hoaDonDTO result = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOADON WHERE maHoaDon = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, hoaDon.getMaHoaDon());

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                result = new hoaDonDTO();
                result.setMaHoaDon(resultSet.getString("maHoaDon"));
                result.setTongTien(resultSet.getDouble("tongTien"));
                result.setMaKH(resultSet.getString("maKH"));
                result.setMaNV(resultSet.getString("maNV"));
                result.setMaKM(resultSet.getString("maKM"));
                result.setThoiGianLap(resultSet.getTimestamp("thoiGianLap").toLocalDateTime());
                result.setTrangThai(resultSet.getBoolean("trangThai"));
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Tìm hóa đơn theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList chứa các hóa đơn thỏa điều kiện
     */
    @Override
    public ArrayList<hoaDonDTO> selectByCondition(String condition) {
        ArrayList<hoaDonDTO> dsHoaDon = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOADON WHERE " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                hoaDonDTO hoaDon = new hoaDonDTO();
                hoaDon.setMaHoaDon(resultSet.getString("maHoaDon"));
                hoaDon.setTongTien(resultSet.getDouble("tongTien"));
                hoaDon.setMaKH(resultSet.getString("maKH"));
                hoaDon.setMaNV(resultSet.getString("maNV"));
                hoaDon.setMaKM(resultSet.getString("maKM"));
                hoaDon.setThoiGianLap(resultSet.getTimestamp("thoiGianLap").toLocalDateTime());
                hoaDon.setTrangThai(resultSet.getBoolean("trangThai"));

                dsHoaDon.add(hoaDon);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }


    /**
     * Tìm kiếm hóa đơn theo mã hóa đơn, mã khách hàng hoặc mã nhân viên
     * @param keyword Từ khóa tìm kiếm
     * @return ArrayList chứa các hóa đơn tìm thấy
     */
    public ArrayList<hoaDonDTO> timKiemHoaDon(String keyword) {
        ArrayList<hoaDonDTO> dsHoaDon = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOADON WHERE trangThai = 1 AND (maHoaDon LIKE ? OR maKH LIKE ? OR maNV LIKE ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                hoaDonDTO hoaDon = new hoaDonDTO();
                hoaDon.setMaHoaDon(resultSet.getString("maHoaDon"));
                hoaDon.setTongTien(resultSet.getDouble("tongTien"));
                hoaDon.setMaKH(resultSet.getString("maKH"));
                hoaDon.setMaNV(resultSet.getString("maNV"));
                hoaDon.setMaKM(resultSet.getString("maKM"));
                hoaDon.setThoiGianLap(resultSet.getTimestamp("thoiGianLap").toLocalDateTime());
                hoaDon.setTrangThai(resultSet.getBoolean("trangThai"));

                dsHoaDon.add(hoaDon);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }

    /**
     * Tạo mã hóa đơn mới tự động tăng
     * @return Mã hóa đơn mới
     */
    public String taoMaHoaDonMoi() {
        String maHoaDon = "HD001";
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT MAX(maHoaDon) AS MaxMaHoaDon FROM HOADON";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next() && resultSet.getString("MaxMaHoaDon") != null) {
                String maxMaHoaDon = resultSet.getString("MaxMaHoaDon");
                int soMaHoaDon = Integer.parseInt(maxMaHoaDon.substring(2)) + 1;
                maHoaDon = "HD" + String.format("%03d", soMaHoaDon);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maHoaDon;
    }


    /**
     * Lấy danh sách hóa đơn theo khoảng thời gian
     * @param tuNgay Thời gian bắt đầu
     * @param denNgay Thời gian kết thúc
     * @return ArrayList chứa các hóa đơn trong khoảng thời gian
     */
    public ArrayList<hoaDonDTO> layHoaDonTheoKhoangThoiGian(LocalDateTime tuNgay, LocalDateTime denNgay) {
        ArrayList<hoaDonDTO> dsHoaDon = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOADON WHERE trangThai = 1 AND thoiGianLap BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, tuNgay);
            preparedStatement.setObject(2, denNgay);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                hoaDonDTO hoaDon = new hoaDonDTO();
                hoaDon.setMaHoaDon(resultSet.getString("maHoaDon"));
                hoaDon.setTongTien(resultSet.getDouble("tongTien"));
                hoaDon.setMaKH(resultSet.getString("maKH"));
                hoaDon.setMaNV(resultSet.getString("maNV"));
                hoaDon.setMaKM(resultSet.getString("maKM"));
                hoaDon.setThoiGianLap(resultSet.getTimestamp("thoiGianLap").toLocalDateTime());
                hoaDon.setTrangThai(resultSet.getBoolean("trangThai"));

                dsHoaDon.add(hoaDon);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }

    /**
     * Phương thức main để test các chức năng của DAO
     */
    public static void main(String[] args) {
        // Tạo đối tượng DAO
        HoaDonDAO hoaDonDAO = new HoaDonDAO();

        // Test lấy danh sách hóa đơn
        System.out.println("=== Danh sách hóa đơn ===");
        ArrayList<hoaDonDTO> dsHoaDon = hoaDonDAO.selectAll();
        for (hoaDonDTO hd : dsHoaDon) {
            System.out.println(hd.getMaHoaDon() + " - " + hd.getTongTienFormatted() + " - " + hd.getThoiGianLap());
        }

        // Test tạo mã hóa đơn mới
        System.out.println("\n=== Mã hóa đơn mới ===");
        System.out.println("Mã hóa đơn mới: " + hoaDonDAO.taoMaHoaDonMoi());
    }
}