package org.example.DAO;

import org.example.DTO.chiTietPhieuNhapDTO;
import org.example.DTO.loHangDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Lớp DAO xử lý các thao tác CSDL liên quan đến chi tiết phiếu nhập
 */
public class ChiTietPhieuNhapDAO implements DAOInterface<chiTietPhieuNhapDTO> {

    /**
     * Thêm một chi tiết phiếu nhập mới vào CSDL
     * @param chiTietPhieuNhap Đối tượng chi tiết phiếu nhập cần thêm
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int insert(chiTietPhieuNhapDTO chiTietPhieuNhap) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "INSERT INTO CHITIETPHIEUNHAP (maPN, soLuong, giaNhap, maLoHang) VALUES (?, ?, ?, ?)";
        int result = JDBCUtil.executePreparedUpdate(sql,
                chiTietPhieuNhap.getMaPN(),
                chiTietPhieuNhap.getSoLuong(),
                chiTietPhieuNhap.getGiaNhap(),
                chiTietPhieuNhap.getMaLoHang());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Cập nhật thông tin chi tiết phiếu nhập trong CSDL
     * @param chiTietPhieuNhap Đối tượng chi tiết phiếu nhập cần cập nhật
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int update(chiTietPhieuNhapDTO chiTietPhieuNhap) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE CHITIETPHIEUNHAP SET soLuong = ?, giaNhap = ?, maLoHang = ? WHERE maPN = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                chiTietPhieuNhap.getSoLuong(),
                chiTietPhieuNhap.getGiaNhap(),
                chiTietPhieuNhap.getMaLoHang(),
                chiTietPhieuNhap.getMaPN());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Xóa chi tiết phiếu nhập khỏi CSDL
     * @param chiTietPhieuNhap Đối tượng chi tiết phiếu nhập cần xóa
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int delete(chiTietPhieuNhapDTO chiTietPhieuNhap) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "DELETE FROM CHITIETPHIEUNHAP WHERE maPN = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, chiTietPhieuNhap.getMaPN());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy danh sách tất cả chi tiết phiếu nhập từ CSDL
     * @return ArrayList<chiTietPhieuNhapDTO> danh sách chi tiết phiếu nhập
     */
    @Override
    public ArrayList<chiTietPhieuNhapDTO> selectAll() {
        ArrayList<chiTietPhieuNhapDTO> dsChiTietPhieuNhap = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM CHITIETPHIEUNHAP";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                chiTietPhieuNhapDTO chiTietPhieuNhap = new chiTietPhieuNhapDTO();
                chiTietPhieuNhap.setMaPN(resultSet.getString("maPN"));
                chiTietPhieuNhap.setSoLuong(resultSet.getInt("soLuong"));
                chiTietPhieuNhap.setGiaNhap(resultSet.getDouble("giaNhap"));
                chiTietPhieuNhap.setMaLoHang(resultSet.getString("maLoHang"));

                dsChiTietPhieuNhap.add(chiTietPhieuNhap);
            }
            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsChiTietPhieuNhap;
    }

    /**
     * Lấy chi tiết phiếu nhập theo mã phiếu nhập
     * @param chiTietPhieuNhap Đối tượng chi tiết phiếu nhập chứa mã phiếu nhập cần tìm
     * @return chiTietPhieuNhapDTO Đối tượng chi tiết phiếu nhập tìm được hoặc null nếu không tìm thấy
     */
    @Override
    public chiTietPhieuNhapDTO selectById(chiTietPhieuNhapDTO chiTietPhieuNhap) {
        chiTietPhieuNhapDTO result = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM CHITIETPHIEUNHAP WHERE maPN = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, chiTietPhieuNhap.getMaPN());
            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                result = new chiTietPhieuNhapDTO();
                result.setMaPN(resultSet.getString("maPN"));
                result.setSoLuong(resultSet.getInt("soLuong"));
                result.setGiaNhap(resultSet.getDouble("giaNhap"));
                result.setMaLoHang(resultSet.getString("maLoHang"));
            }
            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách chi tiết phiếu nhập theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList<chiTietPhieuNhapDTO> danh sách chi tiết phiếu nhập thỏa điều kiện
     */
    @Override
    public ArrayList<chiTietPhieuNhapDTO> selectByCondition(String condition) {
        ArrayList<chiTietPhieuNhapDTO> dsChiTietPhieuNhap = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM CHITIETPHIEUNHAP WHERE " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                chiTietPhieuNhapDTO chiTietPhieuNhap = new chiTietPhieuNhapDTO();
                chiTietPhieuNhap.setMaPN(resultSet.getString("maPN"));
                chiTietPhieuNhap.setSoLuong(resultSet.getInt("soLuong"));
                chiTietPhieuNhap.setGiaNhap(resultSet.getDouble("giaNhap"));
                chiTietPhieuNhap.setMaLoHang(resultSet.getString("maLoHang"));

                dsChiTietPhieuNhap.add(chiTietPhieuNhap);
            }
            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsChiTietPhieuNhap;
    }

    /**
     * Lấy danh sách chi tiết phiếu nhập theo mã phiếu nhập
     * @param maPN Mã phiếu nhập cần tìm
     * @return ArrayList<chiTietPhieuNhapDTO> danh sách chi tiết phiếu nhập của phiếu nhập
     */
    public ArrayList<chiTietPhieuNhapDTO> layChiTietPhieuNhapTheoMaPN(String maPN) {
        return selectByCondition("maPN = '" + maPN + "'");
    }

    /**
     * Lấy danh sách chi tiết phiếu nhập theo mã lô hàng
     * @param maLoHang Mã lô hàng cần tìm
     * @return ArrayList<chiTietPhieuNhapDTO> danh sách chi tiết phiếu nhập của lô hàng
     */
    public ArrayList<chiTietPhieuNhapDTO> layChiTietPhieuNhapTheoMaLoHang(String maLoHang) {
        return selectByCondition("maLoHang = '" + maLoHang + "'");
    }

    /**
     * Cập nhật số lượng sản phẩm trong lô hàng sau khi nhập hàng
     * @param maLoHang Mã lô hàng cần cập nhật
     * @param soLuong Số lượng sản phẩm nhập thêm
     * @return int Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    public int capNhatSoLuongLoHang(String maLoHang, int soLuong) {
        int result = 0;
        try {
            // Lấy thông tin lô hàng hiện tại
            LoHangDAO loHangDAO = new LoHangDAO();
            loHangDTO loHang = new loHangDTO();
            loHang.setMaLoHang(maLoHang);
            loHang = loHangDAO.selectById(loHang);

            if (loHang != null) {
                // Cập nhật số lượng mới
                int soLuongMoi = loHang.getSoLuong() + soLuong;
                loHang.setSoLuong(soLuongMoi);

                // Lưu vào CSDL
                result = loHangDAO.update(loHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Tính tổng tiền của một phiếu nhập
     * @param maPN Mã phiếu nhập cần tính tổng tiền
     * @return double Tổng tiền của phiếu nhập
     */
    public double tinhTongTienPhieuNhap(String maPN) {
        double tongTien = 0;
        ArrayList<chiTietPhieuNhapDTO> dsChiTiet = layChiTietPhieuNhapTheoMaPN(maPN);

        for (chiTietPhieuNhapDTO chiTiet : dsChiTiet) {
            tongTien += chiTiet.getSoLuong() * chiTiet.getGiaNhap();
        }

        return tongTien;
    }
}
