package org.example.DAO;

import org.example.DTO.hoaDonDTO;
import org.example.DTO.chiTietHoaDonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp DAO xử lý các thao tác CSDL liên quan đến hóa đơn
 */
public class HoaDonDAO implements DAOInterface<hoaDonDTO> {

    private ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();

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
        String sql = "INSERT INTO HOADON (maHoaDon, tongTien, maKH, maNV, maKM, thoiGianLap, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Chuyển đổi LocalDateTime thành Timestamp cho SQL Server
        java.sql.Timestamp timestamp = null;
        if (hoaDon.getThoiGianLap() != null) {
            timestamp = java.sql.Timestamp.valueOf(hoaDon.getThoiGianLap());
        }

        int result = JDBCUtil.executePreparedUpdate(sql,
                hoaDon.getMaHoaDon(),
                hoaDon.getTongTien(),
                hoaDon.getMaKH(),
                hoaDon.getMaNV(),
                hoaDon.getMaKM(),
                timestamp, // Sử dụng Timestamp
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
        String sql = "UPDATE HOADON SET tongTien = ?, maKH = ?, maNV = ?, maKM = ?, thoiGianLap = ?, trangThai = ? WHERE maHoaDon = ?";

        // Chuyển đổi LocalDateTime thành Timestamp cho SQL Server
        java.sql.Timestamp timestamp = null;
        if (hoaDon.getThoiGianLap() != null) {
            timestamp = java.sql.Timestamp.valueOf(hoaDon.getThoiGianLap());
        }

        int result = JDBCUtil.executePreparedUpdate(sql,
                hoaDon.getTongTien(),
                hoaDon.getMaKH(),
                hoaDon.getMaNV(),
                hoaDon.getMaKM(),
                timestamp, // Sử dụng Timestamp
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
        // Bước 2: Tạo câu lệnh SQL (cập nhật trạng thái thành false thay vì xóa)
        String sql = "UPDATE HOADON SET trangThai = 0 WHERE maHoaDon = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, hoaDon.getMaHoaDon());

        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy danh sách tất cả hóa đơn từ CSDL
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn
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

                // Xử lý datetime từ SQL Server thành LocalDateTime
                java.sql.Timestamp timestamp = resultSet.getTimestamp("thoiGianLap");
                if (timestamp != null) {
                    hoaDon.setThoiGianLap(timestamp.toLocalDateTime());
                }

                hoaDon.setTrangThai(resultSet.getBoolean("trangThai"));

                // Lấy chi tiết hóa đơn
                hoaDon.setChiTietHoaDon(chiTietHoaDonDAO.layChiTietHoaDon(hoaDon.getMaHoaDon()));

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
     * Lấy thông tin hóa đơn theo mã
     * @param t Đối tượng hóa đơn chứa mã cần tìm
     * @return hoaDonDTO đối tượng hóa đơn tìm thấy, null nếu không tìm thấy
     */
    @Override
    public hoaDonDTO selectById(hoaDonDTO t) {
        hoaDonDTO hoaDon = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOADON WHERE maHoaDon = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, t.getMaHoaDon());

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                hoaDon = new hoaDonDTO();
                hoaDon.setMaHoaDon(resultSet.getString("maHoaDon"));
                hoaDon.setTongTien(resultSet.getDouble("tongTien"));
                hoaDon.setMaKH(resultSet.getString("maKH"));
                hoaDon.setMaNV(resultSet.getString("maNV"));
                hoaDon.setMaKM(resultSet.getString("maKM"));

                // Xử lý datetime từ SQL Server thành LocalDateTime
                java.sql.Timestamp timestamp = resultSet.getTimestamp("thoiGianLap");
                if (timestamp != null) {
                    hoaDon.setThoiGianLap(timestamp.toLocalDateTime());
                }

                hoaDon.setTrangThai(resultSet.getBoolean("trangThai"));

                // Lấy chi tiết hóa đơn
                hoaDon.setChiTietHoaDon(chiTietHoaDonDAO.layChiTietHoaDon(hoaDon.getMaHoaDon()));
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoaDon;
    }

    /**
     * Lấy danh sách hóa đơn theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn tìm thấy
     */
    @Override
    public ArrayList<hoaDonDTO> selectByCondition(String condition) {
        ArrayList<hoaDonDTO> dsHoaDon = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM HOADON WHERE 1=1 " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                hoaDonDTO hoaDon = new hoaDonDTO();
                hoaDon.setMaHoaDon(resultSet.getString("maHoaDon"));
                hoaDon.setTongTien(resultSet.getDouble("tongTien"));
                hoaDon.setMaKH(resultSet.getString("maKH"));
                hoaDon.setMaNV(resultSet.getString("maNV"));
                hoaDon.setMaKM(resultSet.getString("maKM"));

                // Xử lý datetime từ SQL Server thành LocalDateTime
                java.sql.Timestamp timestamp = resultSet.getTimestamp("thoiGianLap");
                if (timestamp != null) {
                    hoaDon.setThoiGianLap(timestamp.toLocalDateTime());
                }

                hoaDon.setTrangThai(resultSet.getBoolean("trangThai"));

                // Lấy chi tiết hóa đơn
                hoaDon.setChiTietHoaDon(chiTietHoaDonDAO.layChiTietHoaDon(hoaDon.getMaHoaDon()));

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
     * Lấy danh sách chi tiết hóa đơn theo mã hóa đơn
     * @param maHoaDon Mã hóa đơn cần lấy chi tiết
     * @return List<chiTietHoaDonDTO> danh sách chi tiết hóa đơn
     */
    public List<chiTietHoaDonDTO> layChiTietHoaDon(String maHoaDon) {
        return chiTietHoaDonDAO.layChiTietHoaDon(maHoaDon);
    }

    /**
     * Thêm chi tiết hóa đơn vào CSDL
     * @param chiTiet Đối tượng chi tiết hóa đơn cần thêm
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    public int themChiTietHoaDon(chiTietHoaDonDTO chiTiet) {
        return chiTietHoaDonDAO.insert(chiTiet);
    }

    /**
     * Thêm hóa đơn và chi tiết hóa đơn trong một giao dịch
     * @param hoaDon Đối tượng hóa đơn cần thêm
     * @return boolean true nếu thành công, false nếu thất bại
     */
    public boolean themHoaDonVaChiTiet(hoaDonDTO hoaDon) {
        boolean success = false;
        Connection connection = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL và tắt auto commit
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);

            // Bước 2: Thêm hóa đơn
            String sqlHoaDon = "INSERT INTO HOADON (maHoaDon, tongTien, maKH, maNV, maKM, thoiGianLap, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstHoaDon = connection.prepareStatement(sqlHoaDon);
            pstHoaDon.setString(1, hoaDon.getMaHoaDon());
            pstHoaDon.setDouble(2, hoaDon.getTongTien());
            pstHoaDon.setString(3, hoaDon.getMaKH());
            pstHoaDon.setString(4, hoaDon.getMaNV());
            pstHoaDon.setString(5, hoaDon.getMaKM());

            // Chuyển đổi LocalDateTime thành Timestamp cho SQL Server
            java.sql.Timestamp timestamp = null;
            if (hoaDon.getThoiGianLap() != null) {
                timestamp = java.sql.Timestamp.valueOf(hoaDon.getThoiGianLap());
            } else {
                // Nếu chưa có thời gian lập, tạo mới với thời gian hiện tại
                LocalDateTime now = LocalDateTime.now();
                hoaDon.setThoiGianLap(now);
                timestamp = java.sql.Timestamp.valueOf(now);
            }
            pstHoaDon.setTimestamp(6, timestamp);

            pstHoaDon.setBoolean(7, hoaDon.isTrangThai());
            pstHoaDon.executeUpdate();

            // Bước 3: Thêm chi tiết hóa đơn sử dụng batch
            if (hoaDon.getChiTietHoaDon() != null && !hoaDon.getChiTietHoaDon().isEmpty()) {
                String sqlChiTiet = "INSERT INTO CHITIETHOADON (maHoaDon, maSP, soLuong, giaBan) VALUES (?, ?, ?, ?)";
                PreparedStatement pstChiTiet = connection.prepareStatement(sqlChiTiet);

                for (chiTietHoaDonDTO chiTiet : hoaDon.getChiTietHoaDon()) {
                    pstChiTiet.setString(1, hoaDon.getMaHoaDon());
                    pstChiTiet.setString(2, chiTiet.getMaSP());
                    pstChiTiet.setInt(3, chiTiet.getSoLuong());
                    pstChiTiet.setDouble(4, chiTiet.getGiaBan());
                    pstChiTiet.addBatch();
                }

                pstChiTiet.executeBatch();

                // Cập nhật lại tổng tiền sau khi thêm chi tiết
                double tongTien = tinhTongTienHoaDon(hoaDon.getMaHoaDon());
                String sqlUpdateTongTien = "UPDATE HOADON SET tongTien = ? WHERE maHoaDon = ?";
                PreparedStatement pstUpdateTongTien = connection.prepareStatement(sqlUpdateTongTien);
                pstUpdateTongTien.setDouble(1, tongTien);
                pstUpdateTongTien.setString(2, hoaDon.getMaHoaDon());
                pstUpdateTongTien.executeUpdate();
            }

            // Bước 4: Commit giao dịch
            connection.commit();
            success = true;

            // Cập nhật lại đối tượng hoaDon với tổng tiền mới
            if (hoaDon.getChiTietHoaDon() != null && !hoaDon.getChiTietHoaDon().isEmpty()) {
                double tongTien = tinhTongTienHoaDon(hoaDon.getMaHoaDon());
                hoaDon.setTongTien(tongTien);
            }
        } catch (SQLException e) {
            // Rollback nếu có lỗi
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Đặt lại auto commit và đóng kết nối
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * Tạo mã hóa đơn mới tự động tăng
     * @return String mã hóa đơn mới
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
     * @param tuNgay LocalDate ngày bắt đầu
     * @param denNgay LocalDate ngày kết thúc
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn
     */
    public ArrayList<hoaDonDTO> layHoaDonTheoKhoangThoiGian(LocalDate tuNgay, LocalDate denNgay) {
        String condition = " AND CONVERT(date, thoiGianLap) BETWEEN '" + tuNgay + "' AND '" + denNgay + "'";
        return selectByCondition(condition);
    }

    /**
     * Lấy danh sách hóa đơn theo ngày cụ thể
     * @param ngay LocalDate ngày cần lấy hóa đơn
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn
     */
    public ArrayList<hoaDonDTO> layHoaDonTheoNgay(LocalDate ngay) {
        String condition = " AND CONVERT(date, thoiGianLap) = '" + ngay + "'";
        return selectByCondition(condition);
    }

    /**
     * Lấy danh sách hóa đơn theo khách hàng
     * @param maKH String mã khách hàng
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn
     */
    public ArrayList<hoaDonDTO> layHoaDonTheoKhachHang(String maKH) {
        String condition = " AND maKH = '" + maKH + "'";
        return selectByCondition(condition);
    }

    /**
     * Lấy danh sách hóa đơn theo nhân viên
     * @param maNV String mã nhân viên
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn
     */
    public ArrayList<hoaDonDTO> layHoaDonTheoNhanVien(String maNV) {
        String condition = " AND maNV = '" + maNV + "'";
        return selectByCondition(condition);
    }

    /**
     * Tìm kiếm hóa đơn theo từ khóa
     * @param keyword String từ khóa tìm kiếm
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn tìm thấy
     */
    public ArrayList<hoaDonDTO> timKiemHoaDon(String keyword) {
        String condition = " AND (maHoaDon LIKE '%" + keyword + "%' OR maKH LIKE '%" + keyword + "%' OR maNV LIKE '%" + keyword + "%')";
        return selectByCondition(condition);
    }

    /**
     * Cập nhật tổng tiền hóa đơn
     * @param maHoaDon String mã hóa đơn
     * @param tongTien double tổng tiền mới
     * @return int số dòng bị ảnh hưởng
     */
    public int capNhatTongTien(String maHoaDon, double tongTien) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE HOADON SET tongTien = ? WHERE maHoaDon = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, tongTien, maHoaDon);

        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Tính tổng tiền của hóa đơn dựa trên chi tiết hóa đơn
     * @param maHoaDon String mã hóa đơn
     * @return double tổng tiền
     */
    public double tinhTongTienHoaDon(String maHoaDon) {
        return chiTietHoaDonDAO.tinhTongTienHoaDon(maHoaDon);
    }

    /**
     * Định dạng tổng tiền hóa đơn theo kiểu tiền tệ Việt Nam
     * @param maHoaDon String mã hóa đơn
     * @return String tổng tiền đã định dạng
     */
    public String getTongTienFormatted(String maHoaDon) {
        double tongTien = tinhTongTienHoaDon(maHoaDon);
        return org.example.Utils.PhuongThucBoSung.formatMoneyVND(tongTien);
    }

    /**
     * Lấy danh sách hóa đơn theo trạng thái
     * @param trangThai boolean trạng thái hóa đơn
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn
     */
    public ArrayList<hoaDonDTO> layHoaDonTheoTrangThai(boolean trangThai) {
        String condition = " AND trangThai = " + (trangThai ? "1" : "0");
        return selectByCondition(condition);
    }

    /**
     * Lấy danh sách hóa đơn theo khuyến mãi
     * @param maKM String mã khuyến mãi
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn
     */
    public ArrayList<hoaDonDTO> layHoaDonTheoKhuyenMai(String maKM) {
        String condition = " AND maKM = '" + maKM + "'";
        return selectByCondition(condition);
    }

    /**
     * Kiểm tra hóa đơn đã tồn tại hay chưa
     * @param maHoaDon String mã hóa đơn cần kiểm tra
     * @return boolean true nếu đã tồn tại, false nếu chưa
     */
    public boolean kiemTraTonTai(String maHoaDon) {
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT COUNT(*) AS count FROM HOADON WHERE maHoaDon = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maHoaDon);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xóa chi tiết hóa đơn
     * @param chiTiet chiTietHoaDonDTO đối tượng chi tiết hóa đơn cần xóa
     * @return int số dòng bị ảnh hưởng
     */
    public int xoaChiTietHoaDon(chiTietHoaDonDTO chiTiet) {
        return chiTietHoaDonDAO.delete(chiTiet);
    }

    /**
     * Cập nhật chi tiết hóa đơn
     * @param chiTiet chiTietHoaDonDTO đối tượng chi tiết hóa đơn cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public int capNhatChiTietHoaDon(chiTietHoaDonDTO chiTiet) {
        return chiTietHoaDonDAO.update(chiTiet);
    }

    /**
     * Thêm hoặc cập nhật chi tiết hóa đơn
     * @param chiTiet chiTietHoaDonDTO đối tượng chi tiết hóa đơn
     * @return int số dòng bị ảnh hưởng
     */
    public int themHoacCapNhatChiTietHoaDon(chiTietHoaDonDTO chiTiet) {
        return chiTietHoaDonDAO.themHoacCapNhat(chiTiet);
    }

    /**
     * Lấy danh sách hóa đơn theo tháng và năm
     * @param thang int tháng cần lấy hóa đơn (1-12)
     * @param nam int năm cần lấy hóa đơn
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn
     */
    public ArrayList<hoaDonDTO> layHoaDonTheoThangNam(int thang, int nam) {
        String condition = " AND MONTH(thoiGianLap) = " + thang + " AND YEAR(thoiGianLap) = " + nam;
        return selectByCondition(condition);
    }

    /**
     * Lấy tổng doanh thu theo khoảng thời gian
     * @param tuNgay LocalDate ngày bắt đầu
     * @param denNgay LocalDate ngày kết thúc
     * @return double tổng doanh thu
     */
    public double tinhTongDoanhThu(LocalDate tuNgay, LocalDate denNgay) {
        double tongDoanhThu = 0;
        ArrayList<hoaDonDTO> dsHoaDon = layHoaDonTheoKhoangThoiGian(tuNgay, denNgay);
        for (hoaDonDTO hoaDon : dsHoaDon) {
            if (hoaDon.isTrangThai()) { // Chỉ tính hóa đơn có trạng thái hoạt động
                tongDoanhThu += hoaDon.getTongTien();
            }
        }
        return tongDoanhThu;
    }

    /**
     * Định dạng tổng doanh thu theo kiểu tiền tệ Việt Nam
     * @param tuNgay LocalDate ngày bắt đầu
     * @param denNgay LocalDate ngày kết thúc
     * @return String tổng doanh thu đã định dạng
     */
    public String getTongDoanhThuFormatted(LocalDate tuNgay, LocalDate denNgay) {
        double tongDoanhThu = tinhTongDoanhThu(tuNgay, denNgay);
        return org.example.Utils.PhuongThucBoSung.formatMoneyVND(tongDoanhThu);
    }

    /**
     * Lấy danh sách hóa đơn mới nhất
     * @param soLuong int số lượng hóa đơn cần lấy
     * @return ArrayList<hoaDonDTO> danh sách hóa đơn mới nhất
     */
    public ArrayList<hoaDonDTO> layHoaDonMoiNhat(int soLuong) {
        ArrayList<hoaDonDTO> dsHoaDon = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT TOP " + soLuong + " * FROM HOADON WHERE trangThai = 1 ORDER BY thoiGianLap DESC";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                hoaDonDTO hoaDon = new hoaDonDTO();
                hoaDon.setMaHoaDon(resultSet.getString("maHoaDon"));
                hoaDon.setTongTien(resultSet.getDouble("tongTien"));
                hoaDon.setMaKH(resultSet.getString("maKH"));
                hoaDon.setMaNV(resultSet.getString("maNV"));
                hoaDon.setMaKM(resultSet.getString("maKM"));

                // Xử lý datetime từ SQL Server thành LocalDateTime
                java.sql.Timestamp timestamp = resultSet.getTimestamp("thoiGianLap");
                if (timestamp != null) {
                    hoaDon.setThoiGianLap(timestamp.toLocalDateTime());
                }

                hoaDon.setTrangThai(resultSet.getBoolean("trangThai"));

                // Lấy chi tiết hóa đơn
                hoaDon.setChiTietHoaDon(chiTietHoaDonDAO.layChiTietHoaDon(hoaDon.getMaHoaDon()));

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
     * Phương thức main để test
     */
    public static void main(String[] args) {
        HoaDonDAO hoaDonDAO = new HoaDonDAO();

        // Test tạo mã hóa đơn mới
        System.out.println("Mã hóa đơn mới: " + hoaDonDAO.taoMaHoaDonMoi());

        // Test lấy danh sách hóa đơn
        ArrayList<hoaDonDTO> dsHoaDon = hoaDonDAO.selectAll();
        for (hoaDonDTO hoaDon : dsHoaDon) {
            System.out.println(hoaDon.toString());
        }

        // Test lấy chi tiết hóa đơn
        if (!dsHoaDon.isEmpty()) {
            String maHoaDon = dsHoaDon.get(0).getMaHoaDon();
            List<chiTietHoaDonDTO> dsChiTiet = hoaDonDAO.layChiTietHoaDon(maHoaDon);
            System.out.println("\nChi tiết hóa đơn " + maHoaDon + ":");
            for (chiTietHoaDonDTO chiTiet : dsChiTiet) {
                System.out.println(chiTiet.toString());
            }
        }

        // Test lấy hóa đơn mới nhất
        System.out.println("\nDanh sách 3 hóa đơn mới nhất:");
        ArrayList<hoaDonDTO> dsHoaDonMoi = hoaDonDAO.layHoaDonMoiNhat(3);
        for (hoaDonDTO hoaDon : dsHoaDonMoi) {
            System.out.println(hoaDon.toString());
        }
    }
}