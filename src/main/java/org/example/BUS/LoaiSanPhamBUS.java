package org.example.BUS;

import org.example.DAO.LoaiSanPhamDAO;
import org.example.DTO.LoaiSanPhamDTO;

import java.util.ArrayList;

public class LoaiSanPhamBUS {

    public static ArrayList<LoaiSanPhamDTO> selectAll() {
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
        ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = loaiSanPhamDAO.selectAll();
        return dsLoaiSanPham;
    }

    public static ArrayList<LoaiSanPhamDTO> layDanhSachLoaiSanPham() {
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
        ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = loaiSanPhamDAO.layDanhSachLoaiSanPham();
        return dsLoaiSanPham;
    }

    public static int themLoaiSanPham(LoaiSanPhamDTO loaiSanPhamDTO) {
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
        return loaiSanPhamDAO.insert(loaiSanPhamDTO);
    }

    public static int xoaLoaiSanPham(LoaiSanPhamDTO loaiSanPhamDTO) {
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
        return loaiSanPhamDAO.delete(loaiSanPhamDTO);
    }

    public static String generateNextMaLSP() {
        ArrayList<LoaiSanPhamDTO> danhSach = selectAll();
        int maxId = 0;

        // Find the highest existing ID number
        for (LoaiSanPhamDTO loai : danhSach) {
            String maLSP = loai.getMaLSP();
            if (maLSP.startsWith("LSP")) {
                try {
                    int idNumber = Integer.parseInt(maLSP.substring(3));
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
        return String.format("LSP%03d", nextId);
    }

    public static int capNhatLoaiSanPham(LoaiSanPhamDTO loaiSanPhamDTO) {
        return 0;
    }
}
