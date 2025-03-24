package org.example.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.DTO.*;


public class KhachHangDAO_test implements DAO_interface<khachHangDTO>{
    @Override
    public List<khachHangDTO> getAllList() {
        List<khachHangDTO> customerList = new ArrayList<>();
        String query = "SELECT * FROM KHACHHANG";
        try (Connection conn = SQLSConnection_test.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                khachHangDTO kh = new khachHangDTO();
                kh.setMaKH(rs.getString("MaKH"));
                kh.setDiemTichLuy(rs.getInt("DiemTichLuy"));
                kh.setHoTen(rs.getString("HoTen"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setSDT(rs.getString("SDT"));
                kh.setGioiTinh(rs.getString("GioiTinh"));
                kh.setEmail(rs.getString("Email"));
                kh.setNgaySinh(rs.getDate("NgaySinh").toLocalDate());
                kh.setTrangThai(rs.getInt("TrangThai"));
                // Thêm dữ liệu vào list
                customerList.add(kh);
            }
        } catch (SQLException e) {
            System.out.println("ERROR SQL: " + e.getMessage());
        }
        return customerList;
    }

    @Override
    public boolean addData(khachHangDTO costumer) {
        String query = "INSERT INTO KHACHHANG (maKH, hoTen, diaChi, SDT, gioiTinh, email, ngaySinh, trangThai, diemTichLuy) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = SQLSConnection_test.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, costumer.getMaKH());
            stmt.setString(2, costumer.getHoTen());
            stmt.setString(3, costumer.getDiaChi());
            stmt.setString(4, costumer.getSDT());
            stmt.setString(5, costumer.getGioiTinh());
            stmt.setString(6, costumer.getEmail());
            stmt.setDate(7, java.sql.Date.valueOf(costumer.getNgaySinh()));
            stmt.setInt(8, costumer.getTrangThai());
            stmt.setInt(9, costumer.getDiemTichLuy()); // Thêm điểm tích lũy
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateData(khachHangDTO entity) {
        return false;
    }

    @Override
    public boolean deleteData(String id) {
        return false;
    }
}
