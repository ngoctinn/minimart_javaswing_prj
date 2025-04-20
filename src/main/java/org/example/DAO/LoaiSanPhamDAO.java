package org.example.DAO;

import org.example.DTO.LoaiSanPhamDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LoaiSanPhamDAO implements DAOInterface<LoaiSanPhamDTO> {

    @Override
    public int insert(LoaiSanPhamDTO loaiSanPhamDTO) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "INSERT INTO LOAISP (maLSP, tenLSP, trangThai) VALUES (?, ?, 1)";
        int result = JDBCUtil.executePreparedUpdate(sql, loaiSanPhamDTO.getMaLSP(), loaiSanPhamDTO.getTenLSP());
        return result;
    }

    @Override
    public int update(LoaiSanPhamDTO loaiSanPhamDTO) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "UPDATE LOAISP SET tenLSP = ? WHERE maLSP = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, loaiSanPhamDTO.getTenLSP(), loaiSanPhamDTO.getMaLSP());
        return result;
    }

    @Override
    public int delete(LoaiSanPhamDTO loaiSanPhamDTO) {
        // Bước 1: Tạo kết nối đến CSDL
        Connection connection = JDBCUtil.getConnection();
        // Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "UPDATE LOAISP SET trangThai = 0 WHERE maLSP = ?";
        int result = JDBCUtil.  executePreparedUpdate(sql, loaiSanPhamDTO.getMaLSP());
        return result;
    }

    @Override
    public ArrayList<LoaiSanPhamDTO> selectAll() {
        ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = new ArrayList<>();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM LOAISP";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            while (resultSet.next()) {
                LoaiSanPhamDTO loaiSanPhamDTO = new LoaiSanPhamDTO();
                loaiSanPhamDTO.setMaLSP(resultSet.getString("maLSP"));
                loaiSanPhamDTO.setTenLSP(resultSet.getString("tenLSP"));
                loaiSanPhamDTO.setTrangThai(resultSet.getBoolean("trangThai"));
                dsLoaiSanPham.add(loaiSanPhamDTO);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsLoaiSanPham;
    }


    public ArrayList<LoaiSanPhamDTO> layDanhSachLoaiSanPham() {
        ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = new ArrayList<>();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM LOAISP WHERE trangThai = 1";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            while (resultSet.next()) {
                LoaiSanPhamDTO loaiSanPhamDTO = new LoaiSanPhamDTO();
                loaiSanPhamDTO.setMaLSP(resultSet.getString("maLSP"));
                loaiSanPhamDTO.setTenLSP(resultSet.getString("tenLSP"));
                loaiSanPhamDTO.setTrangThai(resultSet.getBoolean("trangThai"));
                dsLoaiSanPham.add(loaiSanPhamDTO);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsLoaiSanPham;
    }

    @Override
    public LoaiSanPhamDTO selectById(LoaiSanPhamDTO loaiSanPhamDTO) {
        LoaiSanPhamDTO loaiSanPham = null;
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM LOAISP WHERE maLSP = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, loaiSanPhamDTO.getMaLSP());
            if (resultSet.next()) {
                loaiSanPham = new LoaiSanPhamDTO();
                loaiSanPham.setMaLSP(resultSet.getString("maLSP"));
                loaiSanPham.setTenLSP(resultSet.getString("tenLSP"));
                loaiSanPham.setTrangThai(resultSet.getBoolean("trangThai"));
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loaiSanPham;
    }

    @Override
    public ArrayList<LoaiSanPhamDTO> selectByCondition(String condition) {
        ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = new ArrayList<>();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM LOAISP WHERE " + condition;
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            while (resultSet.next()) {
                LoaiSanPhamDTO loaiSanPhamDTO = new LoaiSanPhamDTO();
                loaiSanPhamDTO.setMaLSP(resultSet.getString("maLSP"));
                loaiSanPhamDTO.setTenLSP(resultSet.getString("tenLSP"));
                loaiSanPhamDTO.setTrangThai(resultSet.getBoolean("trangThai"));
                dsLoaiSanPham.add(loaiSanPhamDTO);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsLoaiSanPham;
    }

    // Tìm kiếm loại sản phẩm theo tên
    public ArrayList<LoaiSanPhamDTO> timKiemLoaiSanPham(String tenLSP) {
        ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = new ArrayList<>();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM LOAISP WHERE trangThai = 1 AND tenLSP LIKE ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, "%" + tenLSP + "%");
            while (resultSet.next()) {
                LoaiSanPhamDTO loaiSanPhamDTO = new LoaiSanPhamDTO();
                loaiSanPhamDTO.setMaLSP(resultSet.getString("maLSP"));
                loaiSanPhamDTO.setTenLSP(resultSet.getString("tenLSP"));
                loaiSanPhamDTO.setTrangThai(resultSet.getBoolean("trangThai"));
                dsLoaiSanPham.add(loaiSanPhamDTO);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsLoaiSanPham;
    }

    // Main để test
    public static void main(String[] args) {
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
        ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = loaiSanPhamDAO.selectAll();
        for (LoaiSanPhamDTO loaiSanPham : dsLoaiSanPham) {
            System.out.println(loaiSanPham.getMaLSP() + " - " + loaiSanPham.getTenLSP() + " - " + loaiSanPham.getTrangThai());
        }
    }
}
