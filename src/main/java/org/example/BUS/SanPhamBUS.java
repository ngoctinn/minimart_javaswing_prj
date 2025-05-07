package org.example.BUS;

import org.example.DAO.SanPhamDAO;
import org.example.DTO.SanPhamDTO;

import java.util.ArrayList;

public class SanPhamBUS {

    public static ArrayList<SanPhamDTO> layDanhSachSanPham() {
        ArrayList<SanPhamDTO> dsSanPham = new ArrayList<>();
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        dsSanPham = sanPhamDAO.layDanhSachSanPham();
        return dsSanPham;
    }

    public static ArrayList<SanPhamDTO> selectAll() {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        ArrayList<SanPhamDTO> dsSanPham = sanPhamDAO.selectAll();
        return dsSanPham;
    }

    // Thêm sản phẩm
    public static int themSanPham(SanPhamDTO sanPhamDTO) {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        return sanPhamDAO.insert(sanPhamDTO);
    }

    // Cập nhật sản phẩm
    public static int capNhatSanPham(SanPhamDTO sanPhamDTO) {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        return sanPhamDAO.update(sanPhamDTO);
    }

    // Xóa sản phẩm
    public static int xoaSanPham(SanPhamDTO sanPhamDTO) {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        return sanPhamDAO.delete(sanPhamDTO);
    }

    //Lấy danh sách sản phẩm theo loại
    public static ArrayList<SanPhamDTO> layDanhSachSanPhamTheoLoai(String maLSP) {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        return sanPhamDAO.selectByCondition(" and maLSP = '" + maLSP + "'");
    }

    // Tìm kiếm sản phẩm theo tên và mã
    public static ArrayList<SanPhamDTO> timKiemSanPham(String giaTri){
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        return sanPhamDAO.selectByCondition(" and ( tenSP LIKE '%" + giaTri + "%' OR maSP LIKE '%" + giaTri + "%' )");
    }

    // sắp xếp tăng dần
    public static ArrayList<SanPhamDTO> sapXepTangDan() {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        return sanPhamDAO.selectByCondition("ORDER BY giaBan ASC");
    }

    // sắp xếp giảm dần
    public static ArrayList<SanPhamDTO> sapXepGiamDan() {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        return sanPhamDAO.selectByCondition("ORDER BY giaBan DESC");
    }

    //lấy danh sách sản phẩm theo mã
    public static SanPhamDTO layDanhSachSanPhamTheoMa(String maSP) {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        return sanPhamDAO.selectById(maSP);
    }

    // Kiểm tra số lượng sản phẩm tồn kho
    public static boolean kiemTraTonKho(String maSP, int soLuong) {
        SanPhamDTO sanPham = layDanhSachSanPhamTheoMa(maSP);
        return sanPham.getTonKho() >= soLuong;
    }



    // Sinh mã sản phẩm
    public static String generateNextMaSP() {
        ArrayList<SanPhamDTO> danhSach = selectAll();
        int maxId = 0;

        // Find the highest existing ID number
        for (SanPhamDTO loai : danhSach) {
            String maSP = loai.getMaSP();
            if (maSP.startsWith("SP")) {
                try {
                    int idNumber = Integer.parseInt(maSP.substring(3));
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
        return String.format("SP%03d", nextId);
    }

    /**
     * Cập nhật số lượng tồn kho của sản phẩm
     * @param maSP Mã sản phẩm cần cập nhật
     * @param soLuong Số lượng thay đổi (dương: tăng, âm: giảm)
     * @return int Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
     */
    public static int capNhatSoLuongTonKho(String maSP, int soLuong) {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        return sanPhamDAO.capNhatSoLuongTonKho(maSP, soLuong);
    }
}
