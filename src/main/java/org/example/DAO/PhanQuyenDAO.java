package org.example.DAO;

import org.example.DTO.PhanQuyenDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhanQuyenDAO {
    private Connection conn;

    public PhanQuyenDAO() {
        try {
            conn = JDBCUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy danh sách phân quyền theo mã chức vụ
     * @param maCV String mã chức vụ
     * @return ArrayList<PhanQuyenDTO> danh sách phân quyền
     */
    public ArrayList<PhanQuyenDTO> layDanhSachQuyenTheoChucVu(String maCV) {
        ArrayList<PhanQuyenDTO> danhSachQuyen = new ArrayList<>();
        String sql = "SELECT * FROM PhanQuyen WHERE MaCV = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maCV);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String maChucNang = rs.getString("MaChucNang");
                int quyen = rs.getInt("Quyen");
                
                PhanQuyenDTO phanQuyen = new PhanQuyenDTO(maCV, maChucNang, quyen);
                danhSachQuyen.add(phanQuyen);
            }
            
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return danhSachQuyen;
    }
    
    /**
     * Thêm một phân quyền mới vào cơ sở dữ liệu
     * @param phanQuyen PhanQuyenDTO phân quyền cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public int themPhanQuyen(PhanQuyenDTO phanQuyen) {
        String sql = "INSERT INTO PhanQuyen (MaCV, MaChucNang, Quyen) VALUES (?, ?, ?)";
        int result = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phanQuyen.getMaCV());
            ps.setString(2, phanQuyen.getMaChucNang());
            ps.setInt(3, phanQuyen.getQuyen());
            
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * Cập nhật thông tin phân quyền
     * @param phanQuyen PhanQuyenDTO phân quyền cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public int capNhatPhanQuyen(PhanQuyenDTO phanQuyen) {
        String sql = "UPDATE PhanQuyen SET Quyen = ? WHERE MaCV = ? AND MaChucNang = ?";
        int result = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, phanQuyen.getQuyen());
            ps.setString(2, phanQuyen.getMaCV());
            ps.setString(3, phanQuyen.getMaChucNang());
            
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * Xóa tất cả phân quyền của một chức vụ
     * @param maCV String mã chức vụ cần xóa quyền
     * @return int số dòng bị ảnh hưởng
     */
    public int xoaQuyenTheoChucVu(String maCV) {
        String sql = "DELETE FROM PhanQuyen WHERE MaCV = ?";
        int result = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maCV);
            
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * Kiểm tra xem phân quyền đã tồn tại chưa
     * @param maCV String mã chức vụ
     * @param maChucNang String mã chức năng
     * @return boolean true nếu đã tồn tại, false nếu chưa
     */
    public boolean kiemTraTonTai(String maCV, String maChucNang) {
        String sql = "SELECT COUNT(*) FROM PhanQuyen WHERE MaCV = ? AND MaChucNang = ?";
        boolean result = false;
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maCV);
            ps.setString(2, maChucNang);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                result = true;
            }
            
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * Thêm hoặc cập nhật phân quyền
     * @param phanQuyen PhanQuyenDTO phân quyền cần thêm hoặc cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public int themHoacCapNhatPhanQuyen(PhanQuyenDTO phanQuyen) {
        if (kiemTraTonTai(phanQuyen.getMaCV(), phanQuyen.getMaChucNang())) {
            return capNhatPhanQuyen(phanQuyen);
        } else {
            return themPhanQuyen(phanQuyen);
        }
    }
}
