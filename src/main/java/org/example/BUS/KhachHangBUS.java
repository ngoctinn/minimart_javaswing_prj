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


    // Tìm kiếm khách hàng
    public ArrayList<KhachHangDTO> timKiemKhachHang(String keyword) {
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        String condition = "trangThai = 1 AND (maKH LIKE '%" + keyword + "%' OR hoTen LIKE '%" + keyword + "%' OR SDT LIKE '%" + keyword + "%' OR email LIKE '%" + keyword + "%')";
        return khachHangDAO.selectByCondition(condition);
    }

    // Lấy khách hàng theo mã
    public KhachHangDTO layKhachHangTheoMa(String maKH) {
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        KhachHangDTO khachHangDTO = new KhachHangDTO();
        khachHangDTO.setMaKH(maKH);
        return khachHangDAO.selectById(khachHangDTO);
    }

    // lấy khách hàng theo SĐT
    public static KhachHangDTO layKhachHangTheoSDT(String SDT) {
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        return khachHangDAO.layKhachHangTheoSDT(SDT);
    }

    // cập nhật điểm tích lũy của khách hàng
    public int capNhatDiemTichLuy(String maKH, int diemTichLuy) {
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        return khachHangDAO.capNhatDiemTichLuy(maKH, diemTichLuy);
    }

}


