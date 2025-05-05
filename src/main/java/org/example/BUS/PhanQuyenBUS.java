package org.example.BUS;

import org.example.DAO.ChucNangDAO;
import org.example.DAO.PhanQuyenDAO;
import org.example.DTO.ChucNangDTO;
import org.example.DTO.PhanQuyenDTO;

import java.util.ArrayList;

public class PhanQuyenBUS {
    private static PhanQuyenDAO phanQuyenDAO = new PhanQuyenDAO();
    private static ChucNangDAO chucNangDAO = new ChucNangDAO();
    
    /**
     * Lấy danh sách phân quyền theo mã chức vụ
     * @param maCV String mã chức vụ
     * @return ArrayList<PhanQuyenDTO> danh sách phân quyền
     */
    public static ArrayList<PhanQuyenDTO> layDanhSachQuyenTheoChucVu(String maCV) {
        return phanQuyenDAO.layDanhSachQuyenTheoChucVu(maCV);
    }
    
    /**
     * Thêm một phân quyền mới
     * @param phanQuyen PhanQuyenDTO phân quyền cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public static int themPhanQuyen(PhanQuyenDTO phanQuyen) {
        return phanQuyenDAO.themPhanQuyen(phanQuyen);
    }
    
    /**
     * Cập nhật thông tin phân quyền
     * @param phanQuyen PhanQuyenDTO phân quyền cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatPhanQuyen(PhanQuyenDTO phanQuyen) {
        return phanQuyenDAO.capNhatPhanQuyen(phanQuyen);
    }
    
    /**
     * Xóa tất cả phân quyền của một chức vụ
     * @param maCV String mã chức vụ cần xóa quyền
     * @return int số dòng bị ảnh hưởng
     */
    public static int xoaQuyenTheoChucVu(String maCV) {
        return phanQuyenDAO.xoaQuyenTheoChucVu(maCV);
    }
    
    /**
     * Thêm hoặc cập nhật phân quyền
     * @param phanQuyen PhanQuyenDTO phân quyền cần thêm hoặc cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public static int themHoacCapNhatPhanQuyen(PhanQuyenDTO phanQuyen) {
        return phanQuyenDAO.themHoacCapNhatPhanQuyen(phanQuyen);
    }


    public static ArrayList<ChucNangDTO> layDanhSachChucNang() {
        return chucNangDAO.layDanhSachChucNang();
    }
    
    /**
     * Cập nhật tất cả quyền cho một chức vụ
     * @param maCV String mã chức vụ
     * @param danhSachQuyen ArrayList<PhanQuyenDTO> danh sách phân quyền mới
     * @return boolean true nếu cập nhật thành công, false nếu thất bại
     */
    public static boolean capNhatTatCaQuyen(String maCV, ArrayList<PhanQuyenDTO> danhSachQuyen) {
        // Xóa tất cả quyền hiện tại của chức vụ
        int result = xoaQuyenTheoChucVu(maCV);
        
        // Thêm lại các quyền mới
        boolean success = true;
        for (PhanQuyenDTO phanQuyen : danhSachQuyen) {
            int addResult = themPhanQuyen(phanQuyen);
            if (addResult <= 0) {
                success = false;
            }
        }
        
        return success;
    }

    // test lấy phân quyền theo chức vụ
    public static void main(String[] args) {
        //
        ArrayList<PhanQuyenDTO> danhSachQuyen = layDanhSachQuyenTheoChucVu("CV006");
        for (PhanQuyenDTO phanQuyen : danhSachQuyen) {
            System.out.println(phanQuyen.getMaCV() + " - " + phanQuyen.getMaChucNang() + " - " + phanQuyen.getQuyen());

        }
        System.out.println("=== End ===");
    }
}
