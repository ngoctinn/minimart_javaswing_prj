package org.example.BUS;

import org.example.DAO.ChiTietPhieuNhapDAO;
import org.example.DTO.chiTietPhieuNhapDTO;
import org.example.DTO.loHangDTO;
import org.example.DTO.phieuNhapDTO;

import java.util.ArrayList;

/**
 * Lớp xử lý logic nghiệp vụ liên quan đến chi tiết phiếu nhập hàng
 */
public class ChiTietPhieuNhapBUS {
    private static ChiTietPhieuNhapDAO chiTietPhieuNhapDAO = new ChiTietPhieuNhapDAO();

    /**
     * Lấy danh sách tất cả chi tiết phiếu nhập
     * @return ArrayList<chiTietPhieuNhapDTO> danh sách tất cả chi tiết phiếu nhập
     */
    public static ArrayList<chiTietPhieuNhapDTO> selectAll() {
        return chiTietPhieuNhapDAO.selectAll();
    }

    /**
     * Thêm chi tiết phiếu nhập mới
     * @param chiTiet chiTietPhieuNhapDTO đối tượng chi tiết phiếu nhập cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public static int themChiTietPhieuNhap(chiTietPhieuNhapDTO chiTiet) {
        // Kiểm tra dữ liệu hợp lệ
        String error = kiemTraDuLieuHopLe(chiTiet);
        if (error != null) {
            System.out.println("Lỗi: " + error);
            return 0;
        }

        int result = chiTietPhieuNhapDAO.insert(chiTiet);

        // Nếu thêm thành công, cập nhật số lượng lô hàng
        if (result > 0) {
            chiTietPhieuNhapDAO.capNhatSoLuongLoHang(chiTiet.getMaLoHang(), chiTiet.getSoLuong());
        }

        return result;
    }

    /**
     * Cập nhật thông tin chi tiết phiếu nhập
     * @param chiTiet chiTietPhieuNhapDTO đối tượng chi tiết phiếu nhập cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatChiTietPhieuNhap(chiTietPhieuNhapDTO chiTiet) {
        // Kiểm tra dữ liệu hợp lệ
        String error = kiemTraDuLieuHopLe(chiTiet);
        if (error != null) {
            System.out.println("Lỗi: " + error);
            return 0;
        }

        // Lấy thông tin chi tiết cũ
        chiTietPhieuNhapDTO chiTietCu = chiTietPhieuNhapDAO.selectById(chiTiet);
        if (chiTietCu == null) {
            return 0;
        }

        // Tính số lượng thay đổi
        int soLuongThayDoi = chiTiet.getSoLuong() - chiTietCu.getSoLuong();

        // Cập nhật chi tiết phiếu nhập
        int result = chiTietPhieuNhapDAO.update(chiTiet);

        // Nếu cập nhật thành công và có thay đổi số lượng, cập nhật số lượng lô hàng
        if (result > 0 && soLuongThayDoi != 0) {
            chiTietPhieuNhapDAO.capNhatSoLuongLoHang(chiTiet.getMaLoHang(), soLuongThayDoi);
        }

        return result;
    }

    /**
     * Xóa chi tiết phiếu nhập
     * @param chiTiet chiTietPhieuNhapDTO đối tượng chi tiết phiếu nhập cần xóa
     * @return int số dòng bị ảnh hưởng
     */
    public static int xoaChiTietPhieuNhap(chiTietPhieuNhapDTO chiTiet) {
        // Lấy thông tin chi tiết trước khi xóa
        chiTietPhieuNhapDTO chiTietCu = chiTietPhieuNhapDAO.selectById(chiTiet);
        if (chiTietCu == null) {
            return 0;
        }

        // Xóa chi tiết phiếu nhập
        int result = chiTietPhieuNhapDAO.delete(chiTiet);

        // Nếu xóa thành công, cập nhật số lượng lô hàng (giảm đi số lượng đã nhập)
        if (result > 0) {
            chiTietPhieuNhapDAO.capNhatSoLuongLoHang(chiTietCu.getMaLoHang(), -chiTietCu.getSoLuong());
        }

        return result;
    }

    /**
     * Lấy danh sách chi tiết phiếu nhập theo mã phiếu nhập
     * @param maPN String mã phiếu nhập
     * @return ArrayList<chiTietPhieuNhapDTO> danh sách chi tiết phiếu nhập
     */
    public static ArrayList<chiTietPhieuNhapDTO> layChiTietPhieuNhapTheoMaPN(String maPN) {
        return chiTietPhieuNhapDAO.layChiTietPhieuNhapTheoMaPN(maPN);
    }

    /**
     * Lấy danh sách chi tiết phiếu nhập theo mã lô hàng
     * @param maLoHang String mã lô hàng
     * @return ArrayList<chiTietPhieuNhapDTO> danh sách chi tiết phiếu nhập
     */
    public static ArrayList<chiTietPhieuNhapDTO> layChiTietPhieuNhapTheoMaLoHang(String maLoHang) {
        return chiTietPhieuNhapDAO.layChiTietPhieuNhapTheoMaLoHang(maLoHang);
    }

    /**
     * Tính tổng tiền của một phiếu nhập
     * @param maPN String mã phiếu nhập
     * @return double tổng tiền
     */
    public static double tinhTongTienPhieuNhap(String maPN) {
        return chiTietPhieuNhapDAO.tinhTongTienPhieuNhap(maPN);
    }

    /**
     * Cập nhật tổng tiền phiếu nhập
     * @param maPN String mã phiếu nhập
     * @return boolean true nếu thành công, false nếu thất bại
     */
    public static boolean capNhatTongTienPhieuNhap(String maPN) {
        double tongTien = tinhTongTienPhieuNhap(maPN);
        return PhieuNhapBUS.capNhatTongTien(maPN, tongTien) > 0;
    }

    /**
     * Kiểm tra dữ liệu chi tiết phiếu nhập hợp lệ
     * @param chiTiet chiTietPhieuNhapDTO đối tượng chi tiết phiếu nhập cần kiểm tra
     * @return String thông báo lỗi, null nếu hợp lệ
     */
    public static String kiemTraDuLieuHopLe(chiTietPhieuNhapDTO chiTiet) {
        // Kiểm tra mã phiếu nhập
        if (chiTiet.getMaPN() == null || chiTiet.getMaPN().trim().isEmpty()) {
            return "Mã phiếu nhập không được để trống";
        }

        // Kiểm tra mã lô hàng
        if (chiTiet.getMaLoHang() == null || chiTiet.getMaLoHang().trim().isEmpty()) {
            return "Mã lô hàng không được để trống";
        }

        // Kiểm tra số lượng
        if (chiTiet.getSoLuong() <= 0) {
            return "Số lượng phải lớn hơn 0";
        }

        // Kiểm tra giá nhập
        if (chiTiet.getGiaNhap() <= 0) {
            return "Giá nhập phải lớn hơn 0";
        }

        // Kiểm tra phiếu nhập tồn tại
        phieuNhapDTO phieuNhap = PhieuNhapBUS.layPhieuNhapTheoMa(chiTiet.getMaPN());
        if (phieuNhap == null) {
            return "Phiếu nhập không tồn tại";
        }

        // Kiểm tra lô hàng tồn tại
        loHangDTO loHang = LoHangBUS.layLoHangTheoMa(chiTiet.getMaLoHang());
        if (loHang == null) {
            return "Lô hàng không tồn tại";
        }

        return null; // Dữ liệu hợp lệ
    }
}
