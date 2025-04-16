package org.example.DAO;

import org.example.DTO.SanPhamDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SanPhamDAO implements DAOInterface<SanPhamDTO> {

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

    @Override
    public int update(SanPhamDTO t) {
        return 0;
    }

    @Override
    public int delete(SanPhamDTO t) {
        return 0;
    }

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

    @Override
    public SanPhamDTO selectById(SanPhamDTO t) {
        return null;
    }

    @Override
    public ArrayList<SanPhamDTO> selectByCondition(String condition) {
        return null;
    }
}
