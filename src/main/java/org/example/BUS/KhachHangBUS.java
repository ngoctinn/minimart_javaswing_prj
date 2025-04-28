package org.example.BUS;

import org.example.DAO.KhachHangDAO;
import org.example.DTO.KhachHangDTO;

import java.util.ArrayList;

public class KhachHangBUS {

    // Lấy danh sách khách hàng
    public ArrayList<KhachHangDTO> layDanhSachKhachHang() {
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        ArrayList<KhachHangDTO> dsKhachHang = khachHangDAO.layDanhSachKhachHang();
        return dsKhachHang;
    }

    // lấy toàn bộ dữ liệu khách hàng
    public ArrayList<KhachHangDTO> selectAll() {
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        ArrayList<KhachHangDTO> dsKhachHang = khachHangDAO.selectAll();
        return dsKhachHang;
    }

    // Thêm khách hàng
    public int themKhachHang(KhachHangDTO khachHangDTO) {
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        return khachHangDAO.insert(khachHangDTO);
    }

    // Cập nhật khách hàng
    public int capNhatKhachHang(KhachHangDTO khachHangDTO) {
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        return khachHangDAO.update(khachHangDTO);
    }

    // Xóa khách hàng
    public int xoaKhachHang(KhachHangDTO khachHangDTO) {
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        return khachHangDAO.delete(khachHangDTO);
    }

    // Tự động sinh mã khách hàng
    public static String generateNextMaKH() {
        ArrayList<KhachHangDTO> danhSach = new KhachHangBUS().selectAll();
        int maxId = 0;

        // Find the highest existing ID number
        for (KhachHangDTO khachHangDTO : danhSach) {
            String maKH = khachHangDTO.getMaKH();
            if (maKH.startsWith("KH")) {
                try {
                    int idNumber = Integer.parseInt(maKH.substring(2));
                    if (idNumber > maxId) {
                        maxId = idNumber;
                    }
                } catch (NumberFormatException e) {
                    // Skip if not a valid number format
                }
            }
        }

        // Generate next ID
        int nextId = maxId + 1;
        return String.format("KH%03d", nextId);
    }


    public static void main(String[] args) {
        KhachHangBUS khachHangBUS = new KhachHangBUS();

        ArrayList<KhachHangDTO> dsKhachHang = khachHangBUS.selectAll();
        for (KhachHangDTO khachHang : dsKhachHang) {
            System.out.println("Mã KH: " + khachHang.getMaKH());
            System.out.println("Họ tên: " + khachHang.getHoTen());
            System.out.println("Giới tính: " + khachHang.getGioiTinh());
            System.out.println("Điểm tích lũy: " + khachHang.getDiemTichLuy());
            System.out.println("Địa chỉ: " + khachHang.getDiaChi());
            System.out.println("SĐT: " + khachHang.getSDT());
            System.out.println("Email: " + khachHang.getEmail());
            System.out.println("Ngày sinh: " + khachHang.getNgaySinh());
            System.out.println("Trạng thái: " + (khachHang.getTrangThai() ? "Hoạt động" : "Ngừng hoạt động"));
            System.out.println("------------------------------");
        }

        String a = generateNextMaKH();
        System.out.println("Mã KH tiếp theo: " + a);
    }


}


