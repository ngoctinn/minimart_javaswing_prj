package org.example.DAO;

import org.example.DTO.SanPhamDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SanPhamDAO implements DAOInterface<SanPhamDTO> {

    //===============Thêm sản phẩm=========================
    @Override
    public int insert(SanPhamDTO t) {
        Connection connection = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            connection = JDBCUtil.getConnection();

            // Bước 2: Tạo câu SQL với tham số đầy đủ
            String sql = "INSERT INTO SANPHAM (maSP, tenSP, trangThai, donVi, hinhAnh, maLSP, tonKho, giaBan) VALUES (?, ?, 1, ?, ?, ?, 0, ?)";

            // Bước 3: Thực thi câu lệnh với tất cả các tham số
            int result = JDBCUtil.executePreparedUpdate(sql,
                    t.getMaSP(),
                    t.getTenSP(),
                    t.getDonVi(),
                    t.getHinhAnh(),
                    t.getMaLSP(),
                    t.getGiaBan());

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu có lỗi
        } finally {
            // Bước 4: Đóng kết nối
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //===============Cập nhật sản phẩm=========================
    @Override
    public int update(SanPhamDTO t) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu SQL với tham số đầy đủ
            String sql = "UPDATE SANPHAM SET tenSP = ?, trangThai = ?, donVi = ?, hinhAnh = ?, maLSP = ?, tonKho = ?, giaBan = ? WHERE maSP = ?";
            // Bước 3: Thực thi câu lệnh với tất cả các tham số
            int result = JDBCUtil.executePreparedUpdate(sql,
                    t.getTenSP(),
                    t.getTrangThai(),
                    t.getDonVi(),
                    t.getHinhAnh(),
                    t.getMaLSP(),
                    t.getTonKho(),
                    t.getGiaBan(),
                    t.getMaSP());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu có lỗi
        } finally {
            // Bước 4: Đóng kết nối
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //===============Xóa sản phẩm=========================
    @Override
    public int delete(SanPhamDTO t) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            // Bước 2: Tạo câu SQL với tham số đầy đủ
            String sql = "UPDATE SANPHAM SET trangThai = 0 WHERE maSP = ?";
            // Bước 3: Thực thi câu lệnh với tất cả các tham số
            int result = JDBCUtil.executePreparedUpdate(sql, t.getMaSP());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu có lỗi
        } finally {
            // Bước 4: Đóng kết nối
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //===============Lấy danh sách sản phẩm=========================
    @Override
    public ArrayList<SanPhamDTO> selectAll() {
        ArrayList<SanPhamDTO> dsSanPham = new ArrayList<>();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM SANPHAM";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            while (resultSet.next()) {
                SanPhamDTO sanPhamDTO = new SanPhamDTO();
                sanPhamDTO.setMaSP(resultSet.getString("maSP"));
                sanPhamDTO.setTenSP(resultSet.getString("tenSP"));
                sanPhamDTO.setTrangThai(resultSet.getBoolean("trangThai"));
                sanPhamDTO.setTonKho(resultSet.getInt("tonKho"));
                sanPhamDTO.setHinhAnh(resultSet.getString("hinhAnh"));
                sanPhamDTO.setDonVi(resultSet.getString("donVi"));
                sanPhamDTO.setMaLSP(resultSet.getString("maLSP"));
                sanPhamDTO.setGiaBan(resultSet.getDouble("giaBan"));
                dsSanPham.add(sanPhamDTO);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return dsSanPham;
    }

    //===============Lấy danh sách sản phẩm đang hoạt động=========================
    public ArrayList<SanPhamDTO> layDanhSachSanPham() {
        ArrayList<SanPhamDTO> dsSanPham = new ArrayList<>();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM SANPHAM WHERE trangThai = 1";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            while (resultSet.next()) {
                SanPhamDTO sanPhamDTO = new SanPhamDTO();
                sanPhamDTO.setMaSP(resultSet.getString("maSP"));
                sanPhamDTO.setTenSP(resultSet.getString("tenSP"));
                sanPhamDTO.setTrangThai(resultSet.getBoolean("trangThai"));
                sanPhamDTO.setTonKho(resultSet.getInt("tonKho"));
                sanPhamDTO.setHinhAnh(resultSet.getString("hinhAnh"));
                sanPhamDTO.setDonVi(resultSet.getString("donVi"));
                sanPhamDTO.setMaLSP(resultSet.getString("maLSP"));
                sanPhamDTO.setGiaBan(resultSet.getDouble("giaBan"));
                dsSanPham.add(sanPhamDTO);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return dsSanPham;
    }

    //===============Lấy sản phẩm theo mã=========================
    @Override
    public SanPhamDTO selectById(SanPhamDTO t) {
        SanPhamDTO sanPhamDTO = null;
        try {
            // Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();

            // Bước 2: Tạo câu truy vấn
            String sql = "SELECT * FROM SANPHAM WHERE maSP = ?";

            // Bước 3: Thực thi câu lệnh với tham số
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, t.getMaSP());
            ResultSet resultSet = preparedStatement.executeQuery();

            // Bước 4: Xử lý kết quả trả về
            if (resultSet.next()) {
                sanPhamDTO = new SanPhamDTO();
                sanPhamDTO.setMaSP(resultSet.getString("maSP"));
                sanPhamDTO.setTenSP(resultSet.getString("tenSP"));
                sanPhamDTO.setTrangThai(resultSet.getBoolean("trangThai"));
                sanPhamDTO.setTonKho(resultSet.getInt("tonKho"));
                sanPhamDTO.setHinhAnh(resultSet.getString("hinhAnh"));
                sanPhamDTO.setDonVi(resultSet.getString("donVi"));
                sanPhamDTO.setMaLSP(resultSet.getString("maLSP"));
                sanPhamDTO.setGiaBan(resultSet.getDouble("giaBan"));
            }

            // Bước 5: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sanPhamDTO;
    }

    //===============Lấy sản phẩm theo mã (phương thức tiện ích)=========================
    public SanPhamDTO selectById(String maSP) {
        SanPhamDTO temp = new SanPhamDTO();
        temp.setMaSP(maSP);
        return selectById(temp);
    }

    //===============Lấy sản phẩm theo điều kiện=========================
    @Override
    public ArrayList<SanPhamDTO> selectByCondition(String condition) {
        return null;
    }
}
