package org.example.BUS;

import org.example.DAO.SanPhamDAO;
import org.example.DTO.LoaiSanPhamDTO;
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
}
