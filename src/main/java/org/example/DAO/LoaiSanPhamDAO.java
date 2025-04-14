package org.example.DAO;

import org.example.DTO.LoaiSanPhamDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LoaiSanPhamDAO implements DAOInterface<LoaiSanPhamDTO> {

    @Override
    public int insert(LoaiSanPhamDTO loaiSanPhamDTO) {

        return 0;
    }

    @Override
    public int update(LoaiSanPhamDTO loaiSanPhamDTO) {
        return 0;
    }

    @Override
    public int delete(LoaiSanPhamDTO loaiSanPhamDTO) {
        return 0;
    }

    @Override
    public ArrayList<LoaiSanPhamDTO> selectAll() {
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
                loaiSanPhamDTO.setTrangThai(resultSet.getInt("trangThai"));
                dsLoaiSanPham.add(loaiSanPhamDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsLoaiSanPham;
    }

    @Override
    public LoaiSanPhamDTO selectById(LoaiSanPhamDTO loaiSanPhamDTO) {
        return null;
    }

    @Override
    public ArrayList<LoaiSanPhamDTO> selectByCondition(String condition) {
        return null;
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
