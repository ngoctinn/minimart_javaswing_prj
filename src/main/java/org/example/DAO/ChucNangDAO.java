package org.example.DAO;

import org.example.DTO.ChucNangDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChucNangDAO {
    private Connection conn;

    public ChucNangDAO() {
        try {
            conn = JDBCUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy danh sách tất cả chức năng từ cơ sở dữ liệu
     * @return ArrayList<ChucNangDTO> danh sách chức năng
     */
    public ArrayList<ChucNangDTO> layDanhSachChucNang() {
        ArrayList<ChucNangDTO> danhSachChucNang = new ArrayList<>();
        String sql = "SELECT * FROM ChucNang";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String maCN = rs.getString("MaChucNang");
                String tenCN = rs.getString("TenChucNang");
                
                ChucNangDTO chucNang = new ChucNangDTO(maCN, tenCN);
                danhSachChucNang.add(chucNang);
            }
            
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return danhSachChucNang;
    }
    
    /**
     * Thêm một chức năng mới vào cơ sở dữ liệu
     * @param chucNang ChucNangDTO chức năng cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public int themChucNang(ChucNangDTO chucNang) {
        String sql = "INSERT INTO ChucNang (MaChucNang, TenChucNang) VALUES (?, ?)";
        int result = 0;
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, chucNang.getMaChucNang());
            ps.setString(2, chucNang.getTenChucNang());
            
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * Kiểm tra xem chức năng đã tồn tại trong cơ sở dữ liệu chưa
     * @param maChucNang String mã chức năng cần kiểm tra
     * @return boolean true nếu đã tồn tại, false nếu chưa
     */
    public boolean kiemTraTonTai(String maChucNang) {
        String sql = "SELECT COUNT(*) FROM ChucNang WHERE MaChucNang = ?";
        boolean result = false;
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maChucNang);
            
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
     * Khởi tạo dữ liệu mặc định cho bảng ChucNang nếu chưa có
     * @return boolean true nếu khởi tạo thành công, false nếu thất bại
     */
    public boolean khoiTaoDuLieuMacDinh() {
        ArrayList<ChucNangDTO> danhSachChucNang = new ArrayList<>();
        danhSachChucNang.add(new ChucNangDTO("QLCV", "Quản lý chức vụ"));
        danhSachChucNang.add(new ChucNangDTO("QLHD", "Quản lý hóa đơn"));
        danhSachChucNang.add(new ChucNangDTO("QLK", "Quản lý kho"));
        danhSachChucNang.add(new ChucNangDTO("QLKH", "Quản lý khách hàng"));
        danhSachChucNang.add(new ChucNangDTO("QLKM", "Quản lý khuyến mãi"));
        danhSachChucNang.add(new ChucNangDTO("QLLSP", "Quản lý loại sản phẩm"));
        danhSachChucNang.add(new ChucNangDTO("QLNCC", "Quản lý nhà cung cấp"));
        danhSachChucNang.add(new ChucNangDTO("QLNH", "Quản lý nhập hàng"));
        danhSachChucNang.add(new ChucNangDTO("QLNV", "Quản lý nhân viên"));
        danhSachChucNang.add(new ChucNangDTO("QLSP", "Quản lý sản phẩm"));
        danhSachChucNang.add(new ChucNangDTO("QLTK", "Quản lý tài khoản"));
        danhSachChucNang.add(new ChucNangDTO("XBC", "Xem báo cáo"));
        
        boolean success = true;
        
        for (ChucNangDTO chucNang : danhSachChucNang) {
            if (!kiemTraTonTai(chucNang.getMaChucNang())) {
                int result = themChucNang(chucNang);
                if (result <= 0) {
                    success = false;
                }
            }
        }
        
        return success;
    }
}



