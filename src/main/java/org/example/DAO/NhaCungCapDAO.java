package org.example.DAO;

import org.example.DTO.nhaCungCapDTO;
import org.example.DAO.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhaCungCapDAO {
    public static ArrayList<nhaCungCapDTO> layDanhSachNhaCungCap() {
        ArrayList<nhaCungCapDTO> danhSachNCC = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM NhaCungCap WHERE TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                nhaCungCapDTO ncc = new nhaCungCapDTO();
                ncc.setMaNCC(rs.getString("MaNCC"));
                ncc.setTenNCC(rs.getString("TenNCC"));
                ncc.setDiaChi(rs.getString("DiaChi"));
                ncc.setSDT(rs.getString("SDT"));
                ncc.setTrangThai(rs.getBoolean("TrangThai"));
                danhSachNCC.add(ncc);
            }
            
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNCC;
    }
    
    public static int themNhaCungCap(nhaCungCapDTO ncc) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, SDT, TrangThai) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, ncc.getMaNCC());
            pst.setString(2, ncc.getTenNCC());
            pst.setString(3, ncc.getDiaChi());
            pst.setString(4, ncc.getSDT());
            pst.setBoolean(5, ncc.isTrangThai());
            
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    
    public static int capNhatNhaCungCap(nhaCungCapDTO ncc) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE NhaCungCap SET TenNCC = ?, DiaChi = ?, SDT = ? WHERE MaNCC = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, ncc.getTenNCC());
            pst.setString(2, ncc.getDiaChi());
            pst.setString(3, ncc.getSDT());
            pst.setString(4, ncc.getMaNCC());
            
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    
    public static int xoaNhaCungCap(String maNCC) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE NhaCungCap SET TrangThai = 0 WHERE MaNCC = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maNCC);
            
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    
    public static ArrayList<nhaCungCapDTO> timKiemNhaCungCap(String tuKhoa) {
        ArrayList<nhaCungCapDTO> danhSachNCC = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM NhaCungCap WHERE (TenNCC LIKE ? OR DiaChi LIKE ? OR SDT LIKE ?) AND TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + tuKhoa + "%");
            pst.setString(2, "%" + tuKhoa + "%");
            pst.setString(3, "%" + tuKhoa + "%");
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                nhaCungCapDTO ncc = new nhaCungCapDTO();
                ncc.setMaNCC(rs.getString("MaNCC"));
                ncc.setTenNCC(rs.getString("TenNCC"));
                ncc.setDiaChi(rs.getString("DiaChi"));
                ncc.setSDT(rs.getString("SDT"));
                ncc.setTrangThai(rs.getBoolean("TrangThai"));
                danhSachNCC.add(ncc);
            }
            
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNCC;
    }
    
    public static String generateNextMaNCC() {
        String maNCC = "NCC001";
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT MAX(MaNCC) AS MaxMaNCC FROM NhaCungCap";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next() && rs.getString("MaxMaNCC") != null) {
                String maxMaNCC = rs.getString("MaxMaNCC");
                int soMaNCC = Integer.parseInt(maxMaNCC.substring(3)) + 1;
                maNCC = "NCC" + String.format("%03d", soMaNCC);
            }
            
            JDBCUtil.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maNCC;
    }
}
