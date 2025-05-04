package org.example.DAO;

import org.example.DTO.nhanVienDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Lớp DAO xử lý các thao tác CSDL liên quan đến nhân viên
 */
public class NhanVienDAO implements DAOInterface<nhanVienDTO> {

    /**
     * Thêm một nhân viên mới vào CSDL
     * @param nhanVien Đối tượng nhân viên cần thêm
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int insert(nhanVienDTO nhanVien) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "INSERT INTO NHANVIEN (maNV, hoTen, ngaySinh, gioiTinh, diaChi, email, SDT, trangThai, maCV, matKhau) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int result = JDBCUtil.executePreparedUpdate(sql,
                nhanVien.getMaNV(),
                nhanVien.getHoTen(),
                nhanVien.getNgaySinh(),
                nhanVien.getGioiTinh(),
                nhanVien.getDiaChi(),
                nhanVien.getEmail(),
                nhanVien.getSDT(),
                nhanVien.isTrangThai(),
                nhanVien.getMaCV(),
                nhanVien.getMatKhau());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Cập nhật thông tin nhân viên trong CSDL
     * @param nhanVien Đối tượng nhân viên cần cập nhật
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int update(nhanVienDTO nhanVien) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE NHANVIEN SET hoTen = ?, ngaySinh = ?, gioiTinh = ?, diaChi = ?, " +
                     "email = ?, SDT = ?, trangThai = ?, maCV = ?, matKhau = ? WHERE maNV = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                nhanVien.getHoTen(),
                nhanVien.getNgaySinh(),
                nhanVien.getGioiTinh(),
                nhanVien.getDiaChi(),
                nhanVien.getEmail(),
                nhanVien.getSDT(),
                nhanVien.isTrangThai(),
                nhanVien.getMaCV(),
                nhanVien.getMatKhau(),
                nhanVien.getMaNV());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Xóa nhân viên khỏi CSDL (cập nhật trạng thái thành false)
     * @param nhanVien Đối tượng nhân viên cần xóa
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int delete(nhanVienDTO nhanVien) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL (xóa logic bằng cách cập nhật trạng thái)
        String sql = "UPDATE NHANVIEN SET trangThai = 0 WHERE maNV = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, nhanVien.getMaNV());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy danh sách tất cả nhân viên từ CSDL
     * @return ArrayList chứa tất cả nhân viên
     */
    @Override
    public ArrayList<nhanVienDTO> selectAll() {
        ArrayList<nhanVienDTO> dsNhanVien = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM NHANVIEN";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                nhanVienDTO nhanVien = new nhanVienDTO();
                nhanVien.setMaNV(resultSet.getString("maNV"));
                nhanVien.setHoTen(resultSet.getString("hoTen"));
                nhanVien.setNgaySinh(resultSet.getDate("ngaySinh").toLocalDate());
                nhanVien.setGioiTinh(resultSet.getString("gioiTinh"));
                nhanVien.setDiaChi(resultSet.getString("diaChi"));
                nhanVien.setEmail(resultSet.getString("email"));
                nhanVien.setSDT(resultSet.getString("SDT"));
                nhanVien.setTrangThai(resultSet.getBoolean("trangThai"));
                nhanVien.setMaCV(resultSet.getString("maCV"));
                nhanVien.setMatKhau(resultSet.getString("matKhau"));

                dsNhanVien.add(nhanVien);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNhanVien;
    }

    /**
     * Lấy danh sách nhân viên đang hoạt động (trangThai = 1)
     * @return ArrayList chứa nhân viên đang hoạt động
     */
    public ArrayList<nhanVienDTO> selectAllActive() {
        ArrayList<nhanVienDTO> dsNhanVien = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM NHANVIEN WHERE trangThai = 1";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                nhanVienDTO nhanVien = new nhanVienDTO();
                nhanVien.setMaNV(resultSet.getString("maNV"));
                nhanVien.setHoTen(resultSet.getString("hoTen"));
                nhanVien.setNgaySinh(resultSet.getDate("ngaySinh").toLocalDate());
                nhanVien.setGioiTinh(resultSet.getString("gioiTinh"));
                nhanVien.setDiaChi(resultSet.getString("diaChi"));
                nhanVien.setEmail(resultSet.getString("email"));
                nhanVien.setSDT(resultSet.getString("SDT"));
                nhanVien.setTrangThai(resultSet.getBoolean("trangThai"));
                nhanVien.setMaCV(resultSet.getString("maCV"));
                nhanVien.setMatKhau(resultSet.getString("matKhau"));

                dsNhanVien.add(nhanVien);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNhanVien;
    }

    /**
     * Tìm nhân viên theo mã nhân viên
     * @param nhanVien Đối tượng nhân viên chứa mã nhân viên cần tìm
     * @return Đối tượng nhân viên tìm được, null nếu không tìm thấy
     */
    @Override
    public nhanVienDTO selectById(nhanVienDTO nhanVien) {
        nhanVienDTO result = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM NHANVIEN WHERE maNV = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, nhanVien.getMaNV());

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                result = new nhanVienDTO();
                result.setMaNV(resultSet.getString("maNV"));
                result.setHoTen(resultSet.getString("hoTen"));
                result.setNgaySinh(resultSet.getDate("ngaySinh").toLocalDate());
                result.setGioiTinh(resultSet.getString("gioiTinh"));
                result.setDiaChi(resultSet.getString("diaChi"));
                result.setEmail(resultSet.getString("email"));
                result.setSDT(resultSet.getString("SDT"));
                result.setTrangThai(resultSet.getBoolean("trangThai"));
                result.setMaCV(resultSet.getString("maCV"));
                result.setMatKhau(resultSet.getString("matKhau"));
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Tìm nhân viên theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList chứa các nhân viên thỏa điều kiện
     */
    @Override
    public ArrayList<nhanVienDTO> selectByCondition(String condition) {
        ArrayList<nhanVienDTO> dsNhanVien = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM NHANVIEN WHERE " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);

            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                nhanVienDTO nhanVien = new nhanVienDTO();
                nhanVien.setMaNV(resultSet.getString("maNV"));
                nhanVien.setHoTen(resultSet.getString("hoTen"));
                nhanVien.setNgaySinh(resultSet.getDate("ngaySinh").toLocalDate());
                nhanVien.setGioiTinh(resultSet.getString("gioiTinh"));
                nhanVien.setDiaChi(resultSet.getString("diaChi"));
                nhanVien.setEmail(resultSet.getString("email"));
                nhanVien.setSDT(resultSet.getString("SDT"));
                nhanVien.setTrangThai(resultSet.getBoolean("trangThai"));
                nhanVien.setMaCV(resultSet.getString("maCV"));
                nhanVien.setMatKhau(resultSet.getString("matKhau"));

                dsNhanVien.add(nhanVien);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNhanVien;
    }

    /**
     * Tìm kiếm nhân viên theo tên (tìm kiếm gần đúng)
     * @param tenNV Tên nhân viên cần tìm
     * @return ArrayList chứa các nhân viên có tên chứa chuỗi tìm kiếm
     */
    public ArrayList<nhanVienDTO> timKiemTheoTen(String tenNV) {
        return selectByCondition("hoTen LIKE N'%" + tenNV + "%' AND trangThai = 1");
    }

    /**
     * Tìm kiếm nhân viên theo chức vụ
     * @param maCV Mã chức vụ cần tìm
     * @return ArrayList chứa các nhân viên thuộc chức vụ
     */
    public ArrayList<nhanVienDTO> timKiemTheoChucVu(String maCV) {
        return selectByCondition("maCV = '" + maCV + "' AND trangThai = 1");
    }

    /**
     * Tạo mã nhân viên mới tự động tăng
     * @return Mã nhân viên mới
     */
    public String taoMaNhanVienMoi() {
        String maNV = "NV001";
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT MAX(maNV) AS MaxMaNV FROM NHANVIEN";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next() && resultSet.getString("MaxMaNV") != null) {
                String maxMaNV = resultSet.getString("MaxMaNV");
                int soMaNV = Integer.parseInt(maxMaNV.substring(2)) + 1;
                maNV = "NV" + String.format("%03d", soMaNV);
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maNV;
    }

    /**
     * Kiểm tra email đã tồn tại chưa
     * @param email Email cần kiểm tra
     * @param maNV Mã nhân viên hiện tại (dùng khi cập nhật để loại trừ chính nhân viên đó)
     * @return true nếu email đã tồn tại, false nếu chưa
     */
    public boolean kiemTraEmailTonTai(String email, String maNV) {
        boolean result = false;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT COUNT(*) AS count FROM NHANVIEN WHERE email = ? AND maNV != ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, maNV != null ? maNV : "");
            ResultSet resultSet = preparedStatement.executeQuery();

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next() && resultSet.getInt("count") > 0) {
                result = true;
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Kiểm tra số điện thoại đã tồn tại chưa
     * @param sdt Số điện thoại cần kiểm tra
     * @param maNV Mã nhân viên hiện tại (dùng khi cập nhật để loại trừ chính nhân viên đó)
     * @return true nếu số điện thoại đã tồn tại, false nếu chưa
     */
    public boolean kiemTraSDTTonTai(String sdt, String maNV) {
        boolean result = false;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT COUNT(*) AS count FROM NHANVIEN WHERE SDT = ? AND maNV != ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sdt);
            preparedStatement.setString(2, maNV != null ? maNV : "");
            ResultSet resultSet = preparedStatement.executeQuery();

            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next() && resultSet.getInt("count") > 0) {
                result = true;
            }

            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Phương thức main để test các chức năng của DAO
     */
    public static void main(String[] args) {
        // Tạo đối tượng DAO
        NhanVienDAO nhanVienDAO = new NhanVienDAO();

        // Test lấy danh sách nhân viên
        System.out.println("=== Danh sách nhân viên ===");
        ArrayList<nhanVienDTO> dsNhanVien = nhanVienDAO.selectAll();
        for (nhanVienDTO nv : dsNhanVien) {
            System.out.println(nv.getMaNV() + " - " + nv.getHoTen() + " - " + nv.getEmail());
        }

        // Test tạo mã nhân viên mới
        System.out.println("\n=== Mã nhân viên mới ===");
        System.out.println("Mã nhân viên mới: " + nhanVienDAO.taoMaNhanVienMoi());

        // Test thêm nhân viên mới
        /*
        System.out.println("\n=== Thêm nhân viên mới ===");
        nhanVienDTO nhanVienMoi = new nhanVienDTO();
        nhanVienMoi.setMaNV(nhanVienDAO.taoMaNhanVienMoi());
        nhanVienMoi.setHoTen("Nguyễn Văn Test");
        nhanVienMoi.setNgaySinh(LocalDate.of(1990, 1, 1));
        nhanVienMoi.setGioiTinh("Nam");
        nhanVienMoi.setDiaChi("123 Đường Test");
        nhanVienMoi.setEmail("test@example.com");
        nhanVienMoi.setSDT("0123456789");
        nhanVienMoi.setTrangThai(true);
        nhanVienMoi.setMaCV("CV001");
        nhanVienMoi.setMatKhau("password123");

        int ketQua = nhanVienDAO.insert(nhanVienMoi);
        System.out.println("Kết quả thêm: " + (ketQua > 0 ? "Thành công" : "Thất bại"));
        */
    }
}