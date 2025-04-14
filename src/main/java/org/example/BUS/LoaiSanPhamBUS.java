package org.example.BUS;

import org.example.DAO.LoaiSanPhamDAO;
import org.example.DTO.LoaiSanPhamDTO;

import java.util.ArrayList;

public class LoaiSanPhamBUS {

    public static ArrayList<LoaiSanPhamDTO> layDanhSachLoaiSanPham() {
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
        ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = loaiSanPhamDAO.selectAll();
        return dsLoaiSanPham;
    }
}
