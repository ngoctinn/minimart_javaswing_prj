package org.example.DAO;

import org.example.DTO.loHangDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Lớp DAO xử lý các thao tác với bảng LOHANG trong CSDL
 */
public class LoHangDAO implements DAOInterface<loHangDTO> {

    /**
     * Thêm một lô hàng mới vào CSDL
     * @param loHang Đối tượng lô hàng cần thêm
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int insert(loHangDTO loHang) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "INSERT INTO LOHANG (maLoHang, maSP, ngaySanXuat, ngayHetHan, soLuong, trangThai) VALUES (?, ?, ?, ?, ?, ?)";
        int result = JDBCUtil.executePreparedUpdate(sql,
                loHang.getMaLoHang(),
                loHang.getMaSP(),
                loHang.getNgaySanXuat(),
                loHang.getNgayHetHan(),
                loHang.getSoLuong(),
                loHang.getTrangThai());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Cập nhật thông tin lô hàng trong CSDL
     * @param loHang Đối tượng lô hàng cần cập nhật
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int update(loHangDTO loHang) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE LOHANG SET maSP = ?, ngaySanXuat = ?, ngayHetHan = ?, soLuong = ?, trangThai = ? WHERE maLoHang = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                loHang.getMaSP(),
                loHang.getNgaySanXuat(),
                loHang.getNgayHetHan(),
                loHang.getSoLuong(),
                loHang.getTrangThai(),
                loHang.getMaLoHang());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Xóa lô hàng khỏi CSDL (cập nhật trạng thái)
     * @param loHang Đối tượng lô hàng cần xóa
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int delete(loHangDTO loHang) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE LOHANG SET trangThai = 'Ngừng hoạt động' WHERE maLoHang = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, loHang.getMaLoHang());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy danh sách tất cả lô hàng từ CSDL
     * @return ArrayList<loHangDTO> danh sách lô hàng
     */
    @Override
    public ArrayList<loHangDTO> selectAll() {
        ArrayList<loHangDTO> dsLoHang = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM LOHANG";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                loHangDTO loHang = new loHangDTO();
                loHang.setMaLoHang(resultSet.getString("maLoHang"));
                loHang.setMaSP(resultSet.getString("maSP"));
                loHang.setNgaySanXuat(resultSet.getDate("ngaySanXuat").toLocalDate());
                loHang.setNgayHetHan(resultSet.getDate("ngayHetHan").toLocalDate());
                loHang.setSoLuong(resultSet.getInt("soLuong"));
                loHang.setTrangThai(resultSet.getString("trangThai"));

                dsLoHang.add(loHang);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoHang;
    }

    /**
     * Lấy danh sách lô hàng đang hoạt động
     * @return ArrayList<loHangDTO> danh sách lô hàng đang hoạt động
     */
    public ArrayList<loHangDTO> layDanhSachLoHang() {
        ArrayList<loHangDTO> dsLoHang = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM LOHANG WHERE trangThai = 'Đang hoạt động'";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                loHangDTO loHang = new loHangDTO();
                loHang.setMaLoHang(resultSet.getString("maLoHang"));
                loHang.setMaSP(resultSet.getString("maSP"));
                loHang.setNgaySanXuat(resultSet.getDate("ngaySanXuat").toLocalDate());
                loHang.setNgayHetHan(resultSet.getDate("ngayHetHan").toLocalDate());
                loHang.setSoLuong(resultSet.getInt("soLuong"));
                loHang.setTrangThai(resultSet.getString("trangThai"));

                dsLoHang.add(loHang);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoHang;
    }

    /**
     * Lấy thông tin lô hàng theo mã lô hàng
     * @param loHang Đối tượng lô hàng chứa mã lô hàng cần tìm
     * @return loHangDTO Đối tượng lô hàng tìm thấy, null nếu không tìm thấy
     */
    @Override
    public loHangDTO selectById(loHangDTO loHang) {
        loHangDTO result = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM LOHANG WHERE maLoHang = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, loHang.getMaLoHang());

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                result = new loHangDTO();
                result.setMaLoHang(resultSet.getString("maLoHang"));
                result.setMaSP(resultSet.getString("maSP"));
                result.setNgaySanXuat(resultSet.getDate("ngaySanXuat").toLocalDate());
                result.setNgayHetHan(resultSet.getDate("ngayHetHan").toLocalDate());
                result.setSoLuong(resultSet.getInt("soLuong"));
                result.setTrangThai(resultSet.getString("trangThai"));
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách lô hàng theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList<loHangDTO> danh sách lô hàng thỏa điều kiện
     */
    @Override
    public ArrayList<loHangDTO> selectByCondition(String condition) {
        ArrayList<loHangDTO> dsLoHang = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM LOHANG WHERE " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                loHangDTO loHang = new loHangDTO();
                loHang.setMaLoHang(resultSet.getString("maLoHang"));
                loHang.setMaSP(resultSet.getString("maSP"));
                loHang.setNgaySanXuat(resultSet.getDate("ngaySanXuat").toLocalDate());
                loHang.setNgayHetHan(resultSet.getDate("ngayHetHan").toLocalDate());
                loHang.setSoLuong(resultSet.getInt("soLuong"));
                loHang.setTrangThai(resultSet.getString("trangThai"));

                dsLoHang.add(loHang);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoHang;
    }

    /**
     * Lấy danh sách lô hàng theo mã sản phẩm
     * @param maSP Mã sản phẩm cần tìm
     * @return ArrayList<loHangDTO> danh sách lô hàng của sản phẩm
     */
    public ArrayList<loHangDTO> layDanhSachLoHangTheoMaSP(String maSP) {
        ArrayList<loHangDTO> dsLoHang = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM LOHANG WHERE maSP = ? AND trangThai = 'Đang hoạt động'";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, maSP);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                loHangDTO loHang = new loHangDTO();
                loHang.setMaLoHang(resultSet.getString("maLoHang"));
                loHang.setMaSP(resultSet.getString("maSP"));
                loHang.setNgaySanXuat(resultSet.getDate("ngaySanXuat").toLocalDate());
                loHang.setNgayHetHan(resultSet.getDate("ngayHetHan").toLocalDate());
                loHang.setSoLuong(resultSet.getInt("soLuong"));
                loHang.setTrangThai(resultSet.getString("trangThai"));

                dsLoHang.add(loHang);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoHang;
    }

    /**
     * Tìm kiếm lô hàng theo mã lô hàng hoặc mã sản phẩm
     * @param keyword Từ khóa tìm kiếm
     * @return ArrayList<loHangDTO> danh sách lô hàng tìm thấy
     */
    public ArrayList<loHangDTO> timKiemLoHang(String keyword) {
        ArrayList<loHangDTO> dsLoHang = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM LOHANG WHERE (maLoHang LIKE ? OR maSP LIKE ?) AND trangThai = 'Đang hoạt động'";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, "%" + keyword + "%", "%" + keyword + "%");

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                loHangDTO loHang = new loHangDTO();
                loHang.setMaLoHang(resultSet.getString("maLoHang"));
                loHang.setMaSP(resultSet.getString("maSP"));
                loHang.setNgaySanXuat(resultSet.getDate("ngaySanXuat").toLocalDate());
                loHang.setNgayHetHan(resultSet.getDate("ngayHetHan").toLocalDate());
                loHang.setSoLuong(resultSet.getInt("soLuong"));
                loHang.setTrangThai(resultSet.getString("trangThai"));

                dsLoHang.add(loHang);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoHang;
    }

    /**
     * Tạo mã lô hàng mới tự động tăng
     * @return Mã lô hàng mới
     */
    public String taoMaLoHangMoi() {
        String maLoHang = "LH001";
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT MAX(maLoHang) AS MaxMaLoHang FROM LOHANG";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next() && resultSet.getString("MaxMaLoHang") != null) {
                String maxMaLoHang = resultSet.getString("MaxMaLoHang");
                int soMaLoHang = Integer.parseInt(maxMaLoHang.substring(2)) + 1;
                maLoHang = "LH" + String.format("%03d", soMaLoHang);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maLoHang;
    }

    /**
     * Cập nhật số lượng lô hàng
     * @param maLoHang Mã lô hàng cần cập nhật
     * @param soLuong Số lượng mới
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    public int capNhatSoLuong(String maLoHang, int soLuong) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE LOHANG SET soLuong = ? WHERE maLoHang = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, soLuong, maLoHang);
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Kiểm tra lô hàng đã hết hạn hay chưa
     * @param maLoHang Mã lô hàng cần kiểm tra
     * @return true nếu đã hết hạn, false nếu chưa hết hạn
     */
    public boolean kiemTraHetHan(String maLoHang) {
        boolean hetHan = false;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT ngayHetHan FROM LOHANG WHERE maLoHang = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, maLoHang);

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                LocalDate ngayHetHan = resultSet.getDate("ngayHetHan").toLocalDate();
                LocalDate ngayHienTai = LocalDate.now();
                hetHan = ngayHienTai.isAfter(ngayHetHan) || ngayHienTai.isEqual(ngayHetHan);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hetHan;
    }
}
