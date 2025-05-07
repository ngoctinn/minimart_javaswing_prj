package org.example.DAO;

import org.example.DTO.chiTietHoaDonDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Lớp DAO xử lý các thao tác CSDL liên quan đến chi tiết hóa đơn
 * Đại diện cho bảng CHITIETHOADON trong CSDL
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
        
        // Cập nhật số lượng tồn kho của sản phẩm
        if (result > 0) {
            SanPhamDAO sanPhamDAO = new SanPhamDAO();
            sanPhamDAO.giamSoLuongTonKho(chiTiet.getMaSP(), chiTiet.getSoLuong());
        }
        
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
        return 0;
    }

    /**
     * Xóa chi tiết hóa đơn khỏi CSDL
     * @param chiTiet Đối tượng chi tiết hóa đơn cần xóa
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int delete(chiTietHoaDonDTO chiTiet) {
        return 0;
    }

    /**
     * Lấy danh sách tất cả chi tiết hóa đơn từ CSDL
     * @return ArrayList chứa tất cả chi tiết hóa đơn
     */
    @Override
    public ArrayList<chiTietHoaDonDTO> selectAll() {
        ArrayList<chiTietHoaDonDTO> dsChiTiet = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT ct.*, sp.tenSP, sp.donVi FROM CHITIETHOADON ct " +
                         "JOIN SANPHAM sp ON ct.maSP = sp.maSP";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                chiTietHoaDonDTO chiTiet = new chiTietHoaDonDTO();
                chiTiet.setMaHoaDon(resultSet.getString("maHoaDon"));
                chiTiet.setMaSP(resultSet.getString("maSP"));
                chiTiet.setSoLuong(resultSet.getInt("soLuong"));
                chiTiet.setGiaBan(resultSet.getDouble("giaBan"));
                chiTiet.setTenSP(resultSet.getString("tenSP"));
                chiTiet.setDonViTinh(resultSet.getString("donVi"));

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
     * Tìm chi tiết hóa đơn theo mã hóa đơn và mã sản phẩm
     * @param chiTiet Đối tượng chi tiết hóa đơn chứa mã hóa đơn và mã sản phẩm cần tìm
     * @return Đối tượng chi tiết hóa đơn tìm được, null nếu không tìm thấy
     */
    @Override
    public chiTietHoaDonDTO selectById(chiTietHoaDonDTO chiTiet) {
        chiTietHoaDonDTO result = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT ct.*, sp.tenSP, sp.donVi FROM CHITIETHOADON ct " +
                         "JOIN SANPHAM sp ON ct.maSP = sp.maSP " +
                         "WHERE ct.maHoaDon = ? AND ct.maSP = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, chiTiet.getMaHoaDon());
            preparedStatement.setString(2, chiTiet.getMaSP());
            ResultSet resultSet = preparedStatement.executeQuery();

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                result = new chiTietHoaDonDTO();
                result.setMaHoaDon(resultSet.getString("maHoaDon"));
                result.setMaSP(resultSet.getString("maSP"));
                result.setSoLuong(resultSet.getInt("soLuong"));
                result.setGiaBan(resultSet.getDouble("giaBan"));
                result.setTenSP(resultSet.getString("tenSP"));
                result.setDonViTinh(resultSet.getString("donVi"));
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Tìm chi tiết hóa đơn theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList chứa các chi tiết hóa đơn thỏa điều kiện
     */
    @Override
    public ArrayList<chiTietHoaDonDTO> selectByCondition(String condition) {
        ArrayList<chiTietHoaDonDTO> dsChiTiet = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT ct.*, sp.tenSP, sp.donVi FROM CHITIETHOADON ct " +
                         "JOIN SANPHAM sp ON ct.maSP = sp.maSP " +
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
                chiTiet.setDonViTinh(resultSet.getString("donVi"));

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
     * Phương thức main để test các chức năng của DAO
     */
    public static void main(String[] args) {
        // Tạo đối tượng DAO
        ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();

        // Test lấy danh sách chi tiết hóa đơn
        System.out.println("=== Danh sách chi tiết hóa đơn ===");
        ArrayList<chiTietHoaDonDTO> dsChiTiet = chiTietHoaDonDAO.selectAll();
        for (chiTietHoaDonDTO ct : dsChiTiet) {
            System.out.println(ct.getMaHoaDon() + " - " + ct.getMaSP() + " - " + ct.getTenSP() + " - " + 
                              ct.getSoLuong() + " " + ct.getDonViTinh() + " - " + 
                              org.example.Utils.PhuongThucBoSung.formatMoneyVND(ct.getGiaBan()));
        }
    }
}