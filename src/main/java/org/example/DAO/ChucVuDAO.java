package org.example.DAO;

import org.example.DTO.ChucVuDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChucVuDAO implements DAOInterface<ChucVuDTO> {
    @Override
    public int insert(ChucVuDTO t) {
        try {
        //Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        //Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "INSERT INTO CHUCVU(maCV, tenCV) VALUES(?, ?)";
        //Bước 3: Thực hiện câu lệnh SQL
            int result = JDBCUtil.executePreparedUpdate(sql,
                    t.getMaCV(),
                    t.getTenCV());
            //Bước 4: Đóng kết nối
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(ChucVuDTO chucVuDTO) {
        return 0;
    }

    @Override
    public int delete(ChucVuDTO chucVuDTO) {
        return 0;
    }

    @Override
    public ArrayList<ChucVuDTO> selectAll() {
        //Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        //Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "SELECT * FROM CHUCVU";
        ArrayList<ChucVuDTO> dsChucVu = new ArrayList<>();
        try {
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            while (resultSet.next()) {
                ChucVuDTO chucVuDTO = new ChucVuDTO();
                chucVuDTO.setMaCV(resultSet.getString("maCV"));
                chucVuDTO.setTenCV(resultSet.getString("tenCV"));
                dsChucVu.add(chucVuDTO);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsChucVu;
    }

    public ArrayList<ChucVuDTO> layDanhSachChucVu() {
        //Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        //Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "SELECT * FROM CHUCVU WHERE trangThai = 1";
        ArrayList<ChucVuDTO> dsChucVu = new ArrayList<>();
        try {
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            while (resultSet.next()) {
                ChucVuDTO chucVuDTO = new ChucVuDTO();
                chucVuDTO.setMaCV(resultSet.getString("maCV"));
                chucVuDTO.setTenCV(resultSet.getString("tenCV"));
                dsChucVu.add(chucVuDTO);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsChucVu;
    }

    @Override
    public ChucVuDTO selectById(ChucVuDTO chucVuDTO) {
        return null;
    }

    @Override
    public ArrayList<ChucVuDTO> selectByCondition(String condition) {
        return null;
    }
}
