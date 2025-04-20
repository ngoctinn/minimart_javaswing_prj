package org.example.BUS;

import org.example.DAO.ChucVuDAO;
import org.example.DTO.ChucVuDTO;
import org.example.DTO.LoaiSanPhamDTO;

import java.util.ArrayList;

public class ChucVuBUS {

    // lấy danh sách chức vụ
    public static ArrayList<ChucVuDTO> layDanhSachChucVu() {
        ChucVuDAO chucVuDAO = new ChucVuDAO();
        ArrayList<ChucVuDTO> dsChucVu = chucVuDAO.layDanhSachChucVu();
        return dsChucVu;
    }

    // tự động sinh mã chức vụ
    public static String generateNextMaLSP() {
        ArrayList<ChucVuDTO> danhSach = layDanhSachChucVu();
        int maxId = 0;

        // Find the highest existing ID number
        for (ChucVuDTO chucVuDTO : danhSach) {
            String maCV = chucVuDTO.getMaCV();
            if (maCV.startsWith("CV")) {
                try {
                    int idNumber = Integer.parseInt(maCV.substring(3));
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
}
