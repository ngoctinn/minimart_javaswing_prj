package org.example.DAO;

import org.example.DTO.phieuNhapDTO;
import org.example.DTO.chiTietPhieuNhapDTO;
import org.example.DTO.loHangDTO;
import org.example.DTO.SanPhamDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp DAO xử lý các thao tác CSDL liên quan đến phiếu nhập hàng
 */
public class PhieuNhapDAO implements DAOInterface<phieuNhapDTO> {

    /**
     * Thêm một phiếu nhập mới vào CSDL
     * @param phieuNhap Đối tượng phiếu nhập cần thêm
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int insert(phieuNhapDTO phieuNhap) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "INSERT INTO PHIEUNHAP (maPN, ngayLap, gioLap, tongTien, maNCC, maNV, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int result = JDBCUtil.executePreparedUpdate(sql,
                phieuNhap.getMaPN(),
                phieuNhap.getNgayLap(),
                phieuNhap.getGioLap(),
                phieuNhap.getTongTien(),
                phieuNhap.getMaNCC(),
                phieuNhap.getMaNV(),
                phieuNhap.isTrangThai());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Cập nhật thông tin phiếu nhập trong CSDL
     * @param phieuNhap Đối tượng phiếu nhập cần cập nhật
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int update(phieuNhapDTO phieuNhap) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE PHIEUNHAP SET ngayLap = ?, gioLap = ?, tongTien = ?, maNCC = ?, maNV = ?, trangThai = ? WHERE maPN = ?";
        int result = JDBCUtil.executePreparedUpdate(sql,
                phieuNhap.getNgayLap(),
                phieuNhap.getGioLap(),
                phieuNhap.getTongTien(),
                phieuNhap.getMaNCC(),
                phieuNhap.getMaNV(),
                phieuNhap.isTrangThai(),
                phieuNhap.getMaPN());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Xóa phiếu nhập khỏi CSDL (cập nhật trạng thái)
     * @param phieuNhap Đối tượng phiếu nhập cần xóa
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    @Override
    public int delete(phieuNhapDTO phieuNhap) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL (cập nhật trạng thái thay vì xóa)
        String sql = "UPDATE PHIEUNHAP SET trangThai = 0 WHERE maPN = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, phieuNhap.getMaPN());
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy danh sách tất cả phiếu nhập từ CSDL
     * @return ArrayList<phieuNhapDTO> danh sách phiếu nhập
     */
    @Override
    public ArrayList<phieuNhapDTO> selectAll() {
        ArrayList<phieuNhapDTO> dsPhieuNhap = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM PHIEUNHAP";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                phieuNhapDTO phieuNhap = new phieuNhapDTO();
                phieuNhap.setMaPN(resultSet.getString("maPN"));
                phieuNhap.setNgayLap(resultSet.getDate("ngayLap").toLocalDate());
                phieuNhap.setGioLap(resultSet.getTime("gioLap").toLocalTime());
                phieuNhap.setTongTien(resultSet.getDouble("tongTien"));
                phieuNhap.setMaNCC(resultSet.getString("maNCC"));
                phieuNhap.setMaNV(resultSet.getString("maNV"));
                phieuNhap.setTrangThai(resultSet.getBoolean("trangThai"));

                // Lấy chi tiết phiếu nhập
                phieuNhap.setChiTietPhieuNhap(layChiTietPhieuNhap(phieuNhap.getMaPN()));

                dsPhieuNhap.add(phieuNhap);
            }
            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhieuNhap;
    }

    /**
     * Lấy phiếu nhập theo mã phiếu nhập
     * @param phieuNhap Đối tượng phiếu nhập chứa mã phiếu nhập cần tìm
     * @return phieuNhapDTO Đối tượng phiếu nhập tìm được hoặc null nếu không tìm thấy
     */
    @Override
    public phieuNhapDTO selectById(phieuNhapDTO phieuNhap) {
        phieuNhapDTO result = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM PHIEUNHAP WHERE maPN = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, phieuNhap.getMaPN());
            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                result = new phieuNhapDTO();
                result.setMaPN(resultSet.getString("maPN"));
                result.setNgayLap(resultSet.getDate("ngayLap").toLocalDate());
                result.setGioLap(resultSet.getTime("gioLap").toLocalTime());
                result.setTongTien(resultSet.getDouble("tongTien"));
                result.setMaNCC(resultSet.getString("maNCC"));
                result.setMaNV(resultSet.getString("maNV"));
                result.setTrangThai(resultSet.getBoolean("trangThai"));

                // Lấy chi tiết phiếu nhập
                result.setChiTietPhieuNhap(layChiTietPhieuNhap(result.getMaPN()));
            }
            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách phiếu nhập theo điều kiện
     * @param condition Điều kiện tìm kiếm
     * @return ArrayList<phieuNhapDTO> danh sách phiếu nhập thỏa điều kiện
     */
    @Override
    public ArrayList<phieuNhapDTO> selectByCondition(String condition) {
        ArrayList<phieuNhapDTO> dsPhieuNhap = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM PHIEUNHAP WHERE " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                phieuNhapDTO phieuNhap = new phieuNhapDTO();
                phieuNhap.setMaPN(resultSet.getString("maPN"));
                phieuNhap.setNgayLap(resultSet.getDate("ngayLap").toLocalDate());
                phieuNhap.setGioLap(resultSet.getTime("gioLap").toLocalTime());
                phieuNhap.setTongTien(resultSet.getDouble("tongTien"));
                phieuNhap.setMaNCC(resultSet.getString("maNCC"));
                phieuNhap.setMaNV(resultSet.getString("maNV"));
                phieuNhap.setTrangThai(resultSet.getBoolean("trangThai"));

                // Lấy chi tiết phiếu nhập
                phieuNhap.setChiTietPhieuNhap(layChiTietPhieuNhap(phieuNhap.getMaPN()));

                dsPhieuNhap.add(phieuNhap);
            }
            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhieuNhap;
    }

    /**
     * Lấy danh sách phiếu nhập đang hoạt động
     * @return ArrayList<phieuNhapDTO> danh sách phiếu nhập đang hoạt động
     */
    public ArrayList<phieuNhapDTO> layDanhSachPhieuNhap() {
        return selectByCondition("trangThai = 1");
    }

    /**
     * Lấy danh sách chi tiết phiếu nhập theo mã phiếu nhập
     * @param maPN Mã phiếu nhập
     * @return List<chiTietPhieuNhapDTO> danh sách chi tiết phiếu nhập
     */
    private List<chiTietPhieuNhapDTO> layChiTietPhieuNhap(String maPN) {
        List<chiTietPhieuNhapDTO> dsChiTiet = new ArrayList<>();
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT * FROM CHITIETPHIEUNHAP WHERE maPN = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, maPN);
            // Bước 3: Xử lý kết quả trả về
            while (resultSet.next()) {
                chiTietPhieuNhapDTO chiTiet = new chiTietPhieuNhapDTO();
                chiTiet.setMaPN(resultSet.getString("maPN"));
                chiTiet.setSoLuong(resultSet.getInt("soLuong"));
                chiTiet.setGiaNhap(resultSet.getDouble("giaNhap"));
                chiTiet.setMaLoHang(resultSet.getString("maLoHang"));

                dsChiTiet.add(chiTiet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsChiTiet;
    }

    /**
     * Tìm kiếm phiếu nhập theo từ khóa
     * @param keyword Từ khóa tìm kiếm (mã phiếu nhập, mã nhà cung cấp, mã nhân viên)
     * @return ArrayList<phieuNhapDTO> danh sách phiếu nhập tìm thấy
     */
    public ArrayList<phieuNhapDTO> timKiemPhieuNhap(String keyword) {
        String condition = "trangThai = 1 AND (maPN LIKE '%" + keyword + "%' OR maNCC LIKE '%" + keyword + "%' OR maNV LIKE '%" + keyword + "%')";
        return selectByCondition(condition);
    }

    /**
     * Tạo mã phiếu nhập mới tự động tăng
     * @return String mã phiếu nhập mới
     */
    public String taoMaPhieuNhapMoi() {
        String maPN = "PN001";
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu lệnh SQL
            String sql = "SELECT TOP 1 maPN FROM PHIEUNHAP ORDER BY maPN DESC";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            // Bước 3: Xử lý kết quả trả về
            if (resultSet.next()) {
                String maPNCuoi = resultSet.getString("maPN");
                // Tách phần số từ mã
                String prefix = "PN";
                String numberStr = maPNCuoi.substring(2);

                // Chuyển phần số thành số nguyên và tăng lên 1
                int number = Integer.parseInt(numberStr) + 1;

                // Format lại mã mới với số 0 đệm phía trước
                maPN = String.format("%s%03d", prefix, number);
            }
            // Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maPN;
    }

    /**
     * Cập nhật tổng tiền phiếu nhập
     * @param maPN Mã phiếu nhập cần cập nhật tổng tiền
     * @param tongTien Tổng tiền mới
     * @return int Số dòng bị ảnh hưởng
     */
    public int capNhatTongTien(String maPN, double tongTien) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE PHIEUNHAP SET tongTien = ? WHERE maPN = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, tongTien, maPN);
        // Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    /**
     * Lấy danh sách phiếu nhập theo khoảng thời gian
     * @param tuNgay Ngày bắt đầu
     * @param denNgay Ngày kết thúc
     * @return ArrayList<phieuNhapDTO> danh sách phiếu nhập trong khoảng thời gian
     */
    public ArrayList<phieuNhapDTO> layPhieuNhapTheoKhoangThoiGian(LocalDate tuNgay, LocalDate denNgay) {
        String condition = "trangThai = 1 AND ngayLap BETWEEN '" + tuNgay + "' AND '" + denNgay + "'";
        return selectByCondition(condition);
    }

    /**
     * Lấy danh sách phiếu nhập theo nhà cung cấp
     * @param maNCC Mã nhà cung cấp
     * @return ArrayList<phieuNhapDTO> danh sách phiếu nhập của nhà cung cấp
     */
    public ArrayList<phieuNhapDTO> layPhieuNhapTheoNhaCungCap(String maNCC) {
        String condition = "trangThai = 1 AND maNCC = '" + maNCC + "'";
        return selectByCondition(condition);
    }

    /**
     * Lấy danh sách phiếu nhập theo nhân viên
     * @param maNV Mã nhân viên
     * @return ArrayList<phieuNhapDTO> danh sách phiếu nhập của nhân viên
     */
    public ArrayList<phieuNhapDTO> layPhieuNhapTheoNhanVien(String maNV) {
        String condition = "trangThai = 1 AND maNV = '" + maNV + "'";
        return selectByCondition(condition);
    }

    /**
     * Thực hiện nhập hàng hoàn chỉnh với phiếu nhập và chi tiết phiếu nhập
     * @param phieuNhap Đối tượng phiếu nhập cần thêm
     * @return int Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    public int nhapHangHoanChinh(phieuNhapDTO phieuNhap) {
        Connection connection = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Bước 2: Thêm phiếu nhập
            String sql = "INSERT INTO PHIEUNHAP (maPN, ngayLap, gioLap, tongTien, maNCC, maNV, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
            int result = JDBCUtil.executePreparedUpdate(sql,
                    phieuNhap.getMaPN(),
                    phieuNhap.getNgayLap(),
                    phieuNhap.getGioLap(),
                    phieuNhap.getTongTien(),
                    phieuNhap.getMaNCC(),
                    phieuNhap.getMaNV(),
                    phieuNhap.isTrangThai());

            if (result > 0 && phieuNhap.getChiTietPhieuNhap() != null && !phieuNhap.getChiTietPhieuNhap().isEmpty()) {
                // Bước 3: Thêm chi tiết phiếu nhập và cập nhật tồn kho
                ChiTietPhieuNhapDAO chiTietPhieuNhapDAO = new ChiTietPhieuNhapDAO();
                boolean thanhCong = true;

                for (chiTietPhieuNhapDTO chiTiet : phieuNhap.getChiTietPhieuNhap()) {
                    // Đảm bảo mã phiếu nhập được gán đúng
                    chiTiet.setMaPN(phieuNhap.getMaPN());

                    // Thêm chi tiết và cập nhật tồn kho
                    int ketQua = chiTietPhieuNhapDAO.insert(chiTiet);
                    if (ketQua <= 0) {
                        thanhCong = false;
                        break;
                    }

                    // Cập nhật số lượng lô hàng
                    LoHangDAO loHangDAO = new LoHangDAO();
                    loHangDTO loHang = new loHangDTO();
                    loHang.setMaLoHang(chiTiet.getMaLoHang());
                    loHang = loHangDAO.selectById(loHang);

                    if (loHang != null) {
                        // Cập nhật số lượng lô hàng
                        int soLuongMoi = loHang.getSoLuong() + chiTiet.getSoLuong();
                        loHang.setSoLuong(soLuongMoi);
                        int updateLoHang = loHangDAO.update(loHang);

                        if (updateLoHang <= 0) {
                            thanhCong = false;
                            break;
                        }

                        // Cập nhật số lượng tồn kho của sản phẩm
                        SanPhamDAO sanPhamDAO = new SanPhamDAO();
                        int updateTonKho = sanPhamDAO.capNhatSoLuongTonKho(loHang.getMaSP(), chiTiet.getSoLuong());

                        if (updateTonKho <= 0) {
                            thanhCong = false;
                            break;
                        }
                    } else {
                        thanhCong = false;
                        break;
                    }
                }

                if (thanhCong) {
                    // Bước 4: Cập nhật tổng tiền phiếu nhập
                    double tongTien = chiTietPhieuNhapDAO.tinhTongTienPhieuNhap(phieuNhap.getMaPN());
                    int updateTongTien = capNhatTongTien(phieuNhap.getMaPN(), tongTien);

                    if (updateTongTien > 0) {
                        connection.commit(); // Hoàn tất transaction
                        return 1; // Thành công
                    }
                }
            }

            // Nếu có lỗi, rollback transaction
            connection.rollback();
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return 0;
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
    }

    // test tạo mã phieuNhap mới
    public static void main(String[] args) {
        PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();
        System.out.println(phieuNhapDAO.taoMaPhieuNhapMoi());
    }
}
