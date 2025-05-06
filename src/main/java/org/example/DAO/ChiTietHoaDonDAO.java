package org.example.DAO;

import org.example.DTO.chiTietHoaDonDTO;
import org.example.DTO.SanPhamDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp DAO xử lý các thao tác CSDL liên quan đến chi tiết hóa đơn
 */
public class ChiTietHoaDonDAO implements DAOInterface<chiTietHoaDonDTO> {

    /**
     * Thêm một chi tiết hóa đơn mới vào CSDL
     * @param chiTiet Đối tượng chi tiết hóa đơn cần thêm
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int insert(chiTietHoaDonDTO chiTiet) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "INSERT INTO CHITIETHOADON (maHoaDon, maSP, soLuong, giaBan) VALUES (?, ?, ?, ?)";
        int result = JDBCUtil.executePreparedUpdate(sql,
                chiTiet.getMaHoaDon(),
                chiTiet.getMaSP(),
                chiTiet.getSoLuong(),
                chiTiet.getGiaBan());

        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Cập nhật thông tin chi tiết hóa đơn trong CSDL
     * @param chiTiet Đối tượng chi tiết hóa đơn cần cập nhật
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int update(chiTietHoaDonDTO chiTiet) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE CHITIETHOADON SET soLuong = ?, giaBan = ? WHERE maHoaDon = ? AND maSP = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                chiTiet.getSoLuong(),
                chiTiet.getGiaBan(),
                chiTiet.getMaHoaDon(),
                chiTiet.getMaSP());

        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Xóa chi tiết hóa đơn khỏi CSDL
     * @param chiTiet Đối tượng chi tiết hóa đơn cần xóa
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int delete(chiTietHoaDonDTO chiTiet) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "DELETE FROM CHITIETHOADON WHERE maHoaDon = ? AND maSP = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                chiTiet.getMaHoaDon(),
                chiTiet.getMaSP());

        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy danh sách tất cả chi tiết hóa đơn từ CSDL
     * @return ArrayList<chiTietHoaDonDTO> danh sách tất cả chi tiết hóa đơn
     */
    @Override
    public ArrayList<chiTietHoaDonDTO> selectAll() {
        ArrayList<chiTietHoaDonDTO> dsChiTiet = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT cthd.*, sp.tenSP, sp.donViTinh FROM CHITIETHOADON cthd " +
                         "JOIN SANPHAM sp ON cthd.maSP = sp.maSP";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                chiTietHoaDonDTO chiTiet = new chiTietHoaDonDTO();
                chiTiet.setMaHoaDon(resultSet.getString("maHoaDon"));
                chiTiet.setMaSP(resultSet.getString("maSP"));
                chiTiet.setSoLuong(resultSet.getInt("soLuong"));
                chiTiet.setGiaBan(resultSet.getDouble("giaBan"));
                chiTiet.setTenSP(resultSet.getString("tenSP"));
                chiTiet.setDonViTinh(resultSet.getString("donViTinh"));

                dsChiTiet.add(chiTiet);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsChiTiet;
    }

    /**
     * Lấy chi tiết hóa đơn theo mã hóa đơn và mã sản phẩm
     * @param t Đối tượng chi tiết hóa đơn chứa mã hóa đơn và mã sản phẩm cần tìm
     * @return chiTietHoaDonDTO đối tượng chi tiết hóa đơn tìm thấy, null nếu không tìm thấy
     */
    @Override
    public chiTietHoaDonDTO selectById(chiTietHoaDonDTO t) {
        chiTietHoaDonDTO chiTiet = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT cthd.*, sp.tenSP, sp.donViTinh FROM CHITIETHOADON cthd " +
                         "JOIN SANPHAM sp ON cthd.maSP = sp.maSP " +
                         "WHERE cthd.maHoaDon = ? AND cthd.maSP = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, t.getMaHoaDon(), t.getMaSP());

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                chiTiet = new chiTietHoaDonDTO();
                chiTiet.setMaHoaDon(resultSet.getString("maHoaDon"));
                chiTiet.setMaSP(resultSet.getString("maSP"));
                chiTiet.setSoLuong(resultSet.getInt("soLuong"));
                chiTiet.setGiaBan(resultSet.getDouble("giaBan"));
                chiTiet.setTenSP(resultSet.getString("tenSP"));
                chiTiet.setDonViTinh(resultSet.getString("donViTinh"));
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTiet;
    }

    /**
     * Lấy danh sách chi tiết hóa đơn theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList<chiTietHoaDonDTO> danh sách chi tiết hóa đơn tìm thấy
     */
    @Override
    public ArrayList<chiTietHoaDonDTO> selectByCondition(String condition) {
        ArrayList<chiTietHoaDonDTO> dsChiTiet = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT cthd.*, sp.tenSP, sp.donViTinh FROM CHITIETHOADON cthd " +
                         "JOIN SANPHAM sp ON cthd.maSP = sp.maSP " +
                         "WHERE " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                chiTietHoaDonDTO chiTiet = new chiTietHoaDonDTO();
                chiTiet.setMaHoaDon(resultSet.getString("maHoaDon"));
                chiTiet.setMaSP(resultSet.getString("maSP"));
                chiTiet.setSoLuong(resultSet.getInt("soLuong"));
                chiTiet.setGiaBan(resultSet.getDouble("giaBan"));
                chiTiet.setTenSP(resultSet.getString("tenSP"));
                chiTiet.setDonViTinh(resultSet.getString("donViTinh"));

                dsChiTiet.add(chiTiet);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsChiTiet;
    }

    /**
     * Lấy danh sách chi tiết hóa đơn theo mã hóa đơn
     * @param maHoaDon Mã hóa đơn cần lấy chi tiết
     * @return List<chiTietHoaDonDTO> danh sách chi tiết hóa đơn
     */
    public List<chiTietHoaDonDTO> layChiTietHoaDon(String maHoaDon) {
        return selectByCondition("cthd.maHoaDon = '" + maHoaDon + "'");
    }

    /**
     * Tính tổng tiền của hóa đơn dựa trên chi tiết hóa đơn
     * @param maHoaDon String mã hóa đơn
     * @return double tổng tiền
     */
    public double tinhTongTienHoaDon(String maHoaDon) {
        double tongTien = 0;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT SUM(soLuong * giaBan) AS TongTien FROM CHITIETHOADON WHERE maHoaDon = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, maHoaDon);

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                tongTien = resultSet.getDouble("TongTien");
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongTien;
    }

    /**
     * Kiểm tra sản phẩm đã tồn tại trong hóa đơn chưa
     * @param maHoaDon String mã hóa đơn
     * @param maSP String mã sản phẩm
     * @return boolean true nếu đã tồn tại, false nếu chưa
     */
    public boolean kiemTraSanPhamTonTai(String maHoaDon, String maSP) {
        boolean tonTai = false;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT COUNT(*) AS SoLuong FROM CHITIETHOADON WHERE maHoaDon = ? AND maSP = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, maHoaDon, maSP);

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                tonTai = resultSet.getInt("SoLuong") > 0;
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tonTai;
    }

    /**
     * Cập nhật số lượng sản phẩm trong hóa đơn
     * @param maHoaDon String mã hóa đơn
     * @param maSP String mã sản phẩm
     * @param soLuongMoi int số lượng mới
     * @return int số dòng bị ảnh hưởng
     */
    public int capNhatSoLuong(String maHoaDon, String maSP, int soLuongMoi) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE CHITIETHOADON SET soLuong = ? WHERE maHoaDon = ? AND maSP = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, soLuongMoi, maHoaDon, maSP);

        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Thêm hoặc cập nhật chi tiết hóa đơn (nếu sản phẩm đã tồn tại thì cập nhật số lượng)
     * @param chiTiet chiTietHoaDonDTO đối tượng chi tiết hóa đơn
     * @return int số dòng bị ảnh hưởng
     */
    public int themHoacCapNhat(chiTietHoaDonDTO chiTiet) {
        // Kiểm tra sản phẩm đã tồn tại trong hóa đơn chưa
        if (kiemTraSanPhamTonTai(chiTiet.getMaHoaDon(), chiTiet.getMaSP())) {
            // Nếu đã tồn tại, lấy thông tin chi tiết hiện tại
            chiTietHoaDonDTO chiTietHienTai = new chiTietHoaDonDTO();
            chiTietHienTai.setMaHoaDon(chiTiet.getMaHoaDon());
            chiTietHienTai.setMaSP(chiTiet.getMaSP());
            chiTietHienTai = selectById(chiTietHienTai);

            // Cập nhật số lượng mới = số lượng cũ + số lượng thêm
            int soLuongMoi = chiTietHienTai.getSoLuong() + chiTiet.getSoLuong();
            return capNhatSoLuong(chiTiet.getMaHoaDon(), chiTiet.getMaSP(), soLuongMoi);
        } else {
            // Nếu chưa tồn tại, thêm mới
            return insert(chiTiet);
        }
    }

    /**
     * Xóa tất cả chi tiết của một hóa đơn
     * @param maHoaDon String mã hóa đơn
     * @return int số dòng bị ảnh hưởng
     */
    public int xoaTatCaChiTietHoaDon(String maHoaDon) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "DELETE FROM CHITIETHOADON WHERE maHoaDon = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, maHoaDon);

        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy số lượng sản phẩm trong hóa đơn
     * @param maHoaDon String mã hóa đơn
     * @return int số lượng sản phẩm
     */
    public int laySoLuongSanPham(String maHoaDon) {
        int soLuong = 0;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT COUNT(*) AS SoLuong FROM CHITIETHOADON WHERE maHoaDon = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, maHoaDon);

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                soLuong = resultSet.getInt("SoLuong");
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return soLuong;
    }

    /**
     * Lấy tổng số lượng sản phẩm trong hóa đơn
     * @param maHoaDon String mã hóa đơn
     * @return int tổng số lượng
     */
    public int layTongSoLuong(String maHoaDon) {
        int tongSoLuong = 0;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT SUM(soLuong) AS TongSoLuong FROM CHITIETHOADON WHERE maHoaDon = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, maHoaDon);

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                tongSoLuong = resultSet.getInt("TongSoLuong");
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongSoLuong;
    }

    /**
     * Thêm nhiều chi tiết hóa đơn cùng lúc
     * @param dsChiTiet List<chiTietHoaDonDTO> danh sách chi tiết hóa đơn cần thêm
     * @return boolean true nếu thành công, false nếu thất bại
     */
    public boolean themNhieuChiTiet(List<chiTietHoaDonDTO> dsChiTiet) {
        boolean success = true;
        Connection connection = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL và tắt auto commit
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);

            // Bước 2: Tạo câu lệnh SQL
            String sql = "INSERT INTO CHITIETHOADON (maHoaDon, maSP, soLuong, giaBan) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Bước 3: Thêm từng chi tiết
            for (chiTietHoaDonDTO chiTiet : dsChiTiet) {
                preparedStatement.setString(1, chiTiet.getMaHoaDon());
                preparedStatement.setString(2, chiTiet.getMaSP());
                preparedStatement.setInt(3, chiTiet.getSoLuong());
                preparedStatement.setDouble(4, chiTiet.getGiaBan());
                preparedStatement.addBatch();
            }

            // Bước 4: Thực thi batch
            int[] results = preparedStatement.executeBatch();

            // Bước 5: Kiểm tra kết quả
            for (int result : results) {
                if (result <= 0) {
                    success = false;
                    break;
                }
            }

            // Bước 6: Commit hoặc rollback
            if (success) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            success = false;
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
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
     * Cập nhật số lượng tồn kho sau khi bán hàng
     * @param maSP String mã sản phẩm
     * @param soLuong int số lượng bán
     * @return boolean true nếu thành công, false nếu thất bại
     */
    public boolean capNhatTonKhoSauBan(String maSP, int soLuong) {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        return sanPhamDAO.giamSoLuongTonKho(maSP, soLuong) > 0;
    }

    /**
     * Phương thức main để test
     */
    public static void main(String[] args) {
        ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();

        // Test lấy chi tiết hóa đơn
        String maHoaDon = "HD001"; // Thay bằng mã hóa đơn thực tế
        List<chiTietHoaDonDTO> dsChiTiet = chiTietHoaDonDAO.layChiTietHoaDon(maHoaDon);

        System.out.println("Danh sách chi tiết hóa đơn " + maHoaDon + ":");
        for (chiTietHoaDonDTO chiTiet : dsChiTiet) {
            System.out.println(chiTiet.toString());
        }

        // Test tính tổng tiền
        double tongTien = chiTietHoaDonDAO.tinhTongTienHoaDon(maHoaDon);
        System.out.println("Tổng tiền hóa đơn " + maHoaDon + ": " +
                           org.example.Utils.PhuongThucBoSung.formatMoneyVND(tongTien));
    }
}