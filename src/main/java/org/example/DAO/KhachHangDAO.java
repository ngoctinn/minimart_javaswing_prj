package org.example.DAO;

import org.example.DTO.KhachHangDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class KhachHangDAO implements DAOInterface<KhachHangDTO> {
    @Override
    public int insert(KhachHangDTO t) {
        // Bước 1: Tạo kết nối đến CSDL
        JDBCUtil.getConnection();
        // Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "INSERT INTO KHACHHANG (maKH, hoTen, diaChi, diemTichLuy, SDT, gioiTinh, email, ngaySinh, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";
        int result = JDBCUtil.executePreparedUpdate(sql, t.getMaKH(), t.getHoTen(), t.getDiaChi(), t.getDiemTichLuy(), t.getSDT(), t.getGioiTinh(), t.getEmail(), t.getNgaySinh());
        return result;
    }

    @Override
    public int update(KhachHangDTO t) {
        // Bước 1: Tạo kết nối đến CSDL
        JDBCUtil.getConnection();
        // Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "UPDATE KHACHHANG SET hoTen = ?, diaChi = ?, diemTichLuy = ?, SDT = ?, gioiTinh = ?, email = ?, ngaySinh = ? WHERE maKH = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, t.getHoTen(), t.getDiaChi(), t.getDiemTichLuy(), t.getSDT(), t.getGioiTinh(), t.getEmail(), t.getNgaySinh(), t.getMaKH());
        return result;
    }

    @Override
    public int delete(KhachHangDTO t) {
        // Bước 1: Tạo kết nối đến CSDL
        JDBCUtil.getConnection();
        // Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "UPDATE KHACHHANG SET trangThai = 0 WHERE maKH = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, t.getMaKH());
        return result;
    }

    @Override
    public ArrayList<KhachHangDTO> selectAll() {
        // TODO Auto-generated method stub
        ArrayList<KhachHangDTO> dsKhachHang = new ArrayList<>();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM KHACHHANG";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            while (resultSet.next()) {
                KhachHangDTO khachHangDTO = new KhachHangDTO();
                khachHangDTO.setMaKH(resultSet.getString("maKH"));
                khachHangDTO.setHoTen(resultSet.getString("hoTen"));
                khachHangDTO.setGioiTinh(resultSet.getString("gioiTinh"));
                khachHangDTO.setDiemTichLuy(resultSet.getInt("diemTichLuy"));
                khachHangDTO.setDiaChi(resultSet.getString("diaChi"));
                khachHangDTO.setSDT(resultSet.getString("SDT"));
                khachHangDTO.setEmail(resultSet.getString("email"));
                khachHangDTO.setNgaySinh(resultSet.getDate("ngaySinh").toLocalDate());
                khachHangDTO.setTrangThai(resultSet.getBoolean("trangThai"));
                dsKhachHang.add(khachHangDTO);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

    // Lấy danh sách khách hàng
    public ArrayList<KhachHangDTO> layDanhSachKhachHang() {
        ArrayList<KhachHangDTO> dsKhachHang = new ArrayList<>();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM KHACHHANG WHERE trangThai = 1";
            ResultSet resultSet = JDBCUtil.executeQuery(sql);
            while (resultSet.next()) {
                KhachHangDTO khachHangDTO = new KhachHangDTO();
                khachHangDTO.setMaKH(resultSet.getString("maKH"));
                khachHangDTO.setHoTen(resultSet.getString("hoTen"));
                khachHangDTO.setGioiTinh(resultSet.getString("gioiTinh"));
                khachHangDTO.setDiemTichLuy(resultSet.getInt("diemTichLuy"));
                khachHangDTO.setDiaChi(resultSet.getString("diaChi"));
                khachHangDTO.setSDT(resultSet.getString("SDT"));
                khachHangDTO.setEmail(resultSet.getString("email"));
                khachHangDTO.setNgaySinh(resultSet.getDate("ngaySinh").toLocalDate());
                khachHangDTO.setTrangThai(resultSet.getBoolean("trangThai"));
                dsKhachHang.add(khachHangDTO);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

    @Override
    public KhachHangDTO selectById(KhachHangDTO t) {
        // TODO Auto-generated method stub
        //Bước 1: Tạo kết nối đến CSDL
        JDBCUtil.getConnection();
        //Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "SELECT * FROM KHACHHANG WHERE maKH = ?";
        ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, t.getMaKH());
        KhachHangDTO khachHangDTO = null;
        try {
            if (resultSet.next()) {
                khachHangDTO = new KhachHangDTO();
                khachHangDTO.setMaKH(resultSet.getString("maKH"));
                khachHangDTO.setHoTen(resultSet.getString("hoTen"));
                khachHangDTO.setGioiTinh(resultSet.getString("gioiTinh"));
                khachHangDTO.setDiemTichLuy(resultSet.getInt("diemTichLuy"));
                khachHangDTO.setDiaChi(resultSet.getString("diaChi"));
                khachHangDTO.setSDT(resultSet.getString("SDT"));
                khachHangDTO.setEmail(resultSet.getString("email"));
                khachHangDTO.setNgaySinh(resultSet.getDate("ngaySinh").toLocalDate());
                khachHangDTO.setTrangThai(resultSet.getBoolean("trangThai"));
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return khachHangDTO;
    }

    @Override
    public ArrayList<KhachHangDTO> selectByCondition(String condition) {
        // TODO Auto-generated method stub
        //Bước 1: Tạo kết nối đến CSDL
        JDBCUtil.getConnection();
        //Bước 2: Tạo ra đối tượng statement từ connection
        String sql = "SELECT * FROM KHACHHANG WHERE " + condition;
        ResultSet resultSet = JDBCUtil.executeQuery(sql);
        ArrayList<KhachHangDTO> dsKhachHang = new ArrayList<>();
        try {
            while (resultSet.next()) {
                KhachHangDTO khachHangDTO = new KhachHangDTO();
                khachHangDTO.setMaKH(resultSet.getString("maKH"));
                khachHangDTO.setHoTen(resultSet.getString("hoTen"));
                khachHangDTO.setGioiTinh(resultSet.getString("gioiTinh"));
                khachHangDTO.setDiemTichLuy(resultSet.getInt("diemTichLuy"));
                khachHangDTO.setDiaChi(resultSet.getString("diaChi"));
                khachHangDTO.setSDT(resultSet.getString("SDT"));
                khachHangDTO.setEmail(resultSet.getString("email"));
                khachHangDTO.setNgaySinh(resultSet.getDate("ngaySinh").toLocalDate());
                khachHangDTO.setTrangThai(resultSet.getBoolean("trangThai"));
                dsKhachHang.add(khachHangDTO);
            }
            //Bước 3: Đóng kết nối
            JDBCUtil.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

    // Cập nhật điểm tích lũy của khách hàng
    public int capNhatDiemTichLuy(String maKH, int diemTichLuy) {
        // Bước 1: Tạo kết nối đến CSDL
        JDBCUtil.getConnection();
        // Bước 2: Tạo câu lệnh SQL
        String sql = "UPDATE KHACHHANG SET diemTichLuy = ? WHERE maKH = ?";
        int result = JDBCUtil.executePreparedUpdate(sql, diemTichLuy, maKH);
        // Bước 3: Đóng kết nối
        JDBCUtil.closeConnection();
        return result;
    }

    // lấy khách hàng theo số điện thoại
    public KhachHangDTO layKhachHangTheoSDT(String sdt) {
        KhachHangDTO khachHangDTO = new KhachHangDTO();
        try {
            //Bước 1: Tạo kết nối đến CSDL
            Connection connection = JDBCUtil.getConnection();
            //Bước 2: Tạo ra đối tượng statement từ connection
            String sql = "SELECT * FROM KHACHHANG WHERE SDT = ?";
            ResultSet resultSet = JDBCUtil.executePreparedQuery(sql, sdt);
            if (resultSet.next()) {
                khachHangDTO.setMaKH(resultSet.getString("maKH"));
                khachHangDTO.setHoTen(resultSet.getString("hoTen"));
                khachHangDTO.setGioiTinh(resultSet.getString("gioiTinh"));
                khachHangDTO.setDiemTichLuy(resultSet.getInt("diemTichLuy"));
                khachHangDTO.setDiaChi(resultSet.getString("diaChi"));
                khachHangDTO.setSDT(resultSet.getString("SDT"));
                khachHangDTO.setEmail(resultSet.getString("email"));
                khachHangDTO.setNgaySinh(resultSet.getDate("ngaySinh").toLocalDate());
                khachHangDTO.setTrangThai(resultSet.getBoolean("trangThai"));
                return khachHangDTO;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return khachHangDTO;
    }

    public static void main(String[] args) {
        // test chức năng
    }
}
