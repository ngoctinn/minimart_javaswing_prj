package org.example.DAO;

import org.example.DTO.sanPhamDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SanPhamDAO implements DAOInterface<sanPhamDTO> {

    @Override
    public int insert(sanPhamDTO t) {
        int ketQua = 0;
        try{
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            Statement statement = connection.createStatement();
            //Bước 3: Thực thi câu lệnh SQL
            String sql = "INSERT INTO SanPham (maSP, tenSP, trangThai, soLuong, hinhAnh, donVi, maLSP) " +
                    "VALUES (t.get, ?, ?, ?, ?, ?, ?)";



        }catch (SQLException e) {

        }
        return 0;
    }

    @Override
    public int update(sanPhamDTO t) {
        return 0;
    }

    @Override
    public int delete(sanPhamDTO t) {
        return 0;
    }

    @Override
    public ArrayList<sanPhamDTO> selectAll() {
        return null;
    }

    @Override
    public sanPhamDTO selectById(sanPhamDTO t) {
        return null;
    }

    @Override
    public ArrayList<sanPhamDTO> selectByCondition(String condition) {
        return null;
    }
}
