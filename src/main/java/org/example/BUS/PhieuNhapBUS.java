package org.example.BUS;

import org.example.DAO.PhieuNhapDAO;
import org.example.DTO.phieuNhapDTO;
import org.example.DTO.chiTietPhieuNhapDTO;
import org.example.DTO.loHangDTO;
import org.example.DTO.SanPhamDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp xử lý logic nghiệp vụ liên quan đến phiếu nhập hàng
 */
public class PhieuNhapBUS {
    private static PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();

    /**
     * Lấy danh sách tất cả phiếu nhập
     * @return ArrayList<phieuNhapDTO> danh sách tất cả phiếu nhập
     */
    public static ArrayList<phieuNhapDTO> selectAll() {
        return phieuNhapDAO.selectAll();
    }

    /**
     * Lấy danh sách phiếu nhập đang hoạt động
     * @return ArrayList<phieuNhapDTO> danh sách phiếu nhập đang hoạt động
     */
    public static ArrayList<phieuNhapDTO> layDanhSachPhieuNhap() {
        return phieuNhapDAO.layDanhSachPhieuNhap();
    }

    /**
     * Thêm phiếu nhập mới
     * @param phieuNhap phieuNhapDTO đối tượng phiếu nhập cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public static int themPhieuNhap(phieuNhapDTO phieuNhap) {
        return phieuNhapDAO.insert(phieuNhap);
    }

    /**
     * Cập nhật thông tin phiếu nhập
     * @param phieuNhap phieuNhapDTO đối tượng phiếu nhập cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatPhieuNhap(phieuNhapDTO phieuNhap) {
        return phieuNhapDAO.update(phieuNhap);
    }

    /**
     * Xóa phiếu nhập (cập nhật trạng thái)
     * @param phieuNhap phieuNhapDTO đối tượng phiếu nhập cần xóa
     * @return int số dòng bị ảnh hưởng
     */
    public static int xoaPhieuNhap(phieuNhapDTO phieuNhap) {
        return phieuNhapDAO.delete(phieuNhap);
    }

    /**
     * Tìm kiếm phiếu nhập theo từ khóa
     * @param keyword String từ khóa tìm kiếm
     * @return ArrayList<phieuNhapDTO> danh sách phiếu nhập tìm thấy
     */
    public static ArrayList<phieuNhapDTO> timKiemPhieuNhap(String keyword) {
        return phieuNhapDAO.timKiemPhieuNhap(keyword);
    }

    /**
     * Lấy thông tin phiếu nhập theo mã
     * @param maPN String mã phiếu nhập
     * @return phieuNhapDTO đối tượng phiếu nhập, null nếu không tìm thấy
     */
    public static phieuNhapDTO layPhieuNhapTheoMa(String maPN) {
        phieuNhapDTO phieuNhap = new phieuNhapDTO();
        phieuNhap.setMaPN(maPN);
        return phieuNhapDAO.selectById(phieuNhap);
    }

    /**
     * Tạo mã phiếu nhập mới tự động tăng
     * @return String mã phiếu nhập mới
     */
    public static String taoMaPhieuNhapMoi() {
        return phieuNhapDAO.taoMaPhieuNhapMoi();
    }

    /**
     * Lấy danh sách phiếu nhập theo khoảng thời gian
     * @param tuNgay LocalDate ngày bắt đầu
     * @param denNgay LocalDate ngày kết thúc
     * @return ArrayList<phieuNhapDTO> danh sách phiếu nhập trong khoảng thời gian
     */
    public static ArrayList<phieuNhapDTO> layPhieuNhapTheoKhoangThoiGian(LocalDate tuNgay, LocalDate denNgay) {
        return phieuNhapDAO.layPhieuNhapTheoKhoangThoiGian(tuNgay, denNgay);
    }

    /**
     * Lấy danh sách phiếu nhập theo nhà cung cấp
     * @param maNCC String mã nhà cung cấp
     * @return ArrayList<phieuNhapDTO> danh sách phiếu nhập của nhà cung cấp
     */
    public static ArrayList<phieuNhapDTO> layPhieuNhapTheoNhaCungCap(String maNCC) {
        return phieuNhapDAO.layPhieuNhapTheoNhaCungCap(maNCC);
    }

    /**
     * Lấy danh sách phiếu nhập theo nhân viên
     * @param maNV String mã nhân viên
     * @return ArrayList<phieuNhapDTO> danh sách phiếu nhập của nhân viên
     */
    public static ArrayList<phieuNhapDTO> layPhieuNhapTheoNhanVien(String maNV) {
        return phieuNhapDAO.layPhieuNhapTheoNhanVien(maNV);
    }

    /**
     * Cập nhật tổng tiền phiếu nhập
     * @param maPN String mã phiếu nhập
     * @param tongTien double tổng tiền mới
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatTongTien(String maPN, double tongTien) {
        return phieuNhapDAO.capNhatTongTien(maPN, tongTien);
    }

    /**
     * Tạo phiếu nhập mới với thông tin cơ bản
     * @param maNCC String mã nhà cung cấp
     * @param maNV String mã nhân viên
     * @return phieuNhapDTO đối tượng phiếu nhập mới đã được tạo
     */
    public static phieuNhapDTO taoPhieuNhapMoi(String maNCC, String maNV) {
        // Tạo đối tượng phiếu nhập mới
        phieuNhapDTO phieuNhap = new phieuNhapDTO();
        phieuNhap.setMaPN(taoMaPhieuNhapMoi());
        phieuNhap.setNgayLap(LocalDate.now());
        phieuNhap.setGioLap(LocalTime.now());
        phieuNhap.setTongTien(0);
        phieuNhap.setMaNCC(maNCC);
        phieuNhap.setMaNV(maNV);
        phieuNhap.setTrangThai(true);
        phieuNhap.setChiTietPhieuNhap(new ArrayList<>());

        // Thêm phiếu nhập vào CSDL
        if (themPhieuNhap(phieuNhap) > 0) {
            return phieuNhap;
        }
        return null;
    }

    /**
     * Thêm chi tiết phiếu nhập
     * @param phieuNhap phieuNhapDTO đối tượng phiếu nhập
     * @param chiTiet chiTietPhieuNhapDTO đối tượng chi tiết phiếu nhập
     * @return boolean true nếu thành công, false nếu thất bại
     */
    public static boolean themChiTietPhieuNhap(phieuNhapDTO phieuNhap, chiTietPhieuNhapDTO chiTiet) {
        // Gán mã phiếu nhập cho chi tiết
        chiTiet.setMaPN(phieuNhap.getMaPN());

        // Thêm chi tiết vào danh sách của phiếu nhập
        List<chiTietPhieuNhapDTO> dsChiTiet = phieuNhap.getChiTietPhieuNhap();
        if (dsChiTiet == null) {
            dsChiTiet = new ArrayList<>();
        }
        dsChiTiet.add(chiTiet);
        phieuNhap.setChiTietPhieuNhap(dsChiTiet);

        // Cập nhật tổng tiền
        double tongTien = phieuNhap.getTongTien();
        tongTien += chiTiet.getSoLuong() * chiTiet.getGiaNhap();
        phieuNhap.setTongTien(tongTien);

        // Cập nhật phiếu nhập trong CSDL
        return capNhatPhieuNhap(phieuNhap) > 0;
    }

    /**
     * Kiểm tra dữ liệu phiếu nhập hợp lệ
     * @param phieuNhap phieuNhapDTO đối tượng phiếu nhập cần kiểm tra
     * @return String thông báo lỗi, null nếu hợp lệ
     */
    public static String kiemTraDuLieuHopLe(phieuNhapDTO phieuNhap) {
        // Kiểm tra mã phiếu nhập
        if (phieuNhap.getMaPN() == null || phieuNhap.getMaPN().trim().isEmpty()) {
            return "Mã phiếu nhập không được để trống";
        }

        // Kiểm tra ngày lập
        if (phieuNhap.getNgayLap() == null) {
            return "Ngày lập không được để trống";
        }

        // Kiểm tra giờ lập
        if (phieuNhap.getGioLap() == null) {
            return "Giờ lập không được để trống";
        }

        // Kiểm tra mã nhà cung cấp
        if (phieuNhap.getMaNCC() == null || phieuNhap.getMaNCC().trim().isEmpty()) {
            return "Mã nhà cung cấp không được để trống";
        }

        // Kiểm tra mã nhân viên
        if (phieuNhap.getMaNV() == null || phieuNhap.getMaNV().trim().isEmpty()) {
            return "Mã nhân viên không được để trống";
        }

        // Kiểm tra chi tiết phiếu nhập
        if (phieuNhap.getChiTietPhieuNhap() == null || phieuNhap.getChiTietPhieuNhap().isEmpty()) {
            return "Phiếu nhập phải có ít nhất một chi tiết";
        }

        return null; // Dữ liệu hợp lệ
    }

    /**
     * Kiểm tra dữ liệu chi tiết phiếu nhập hợp lệ
     * @param chiTiet chiTietPhieuNhapDTO đối tượng chi tiết phiếu nhập cần kiểm tra
     * @return String thông báo lỗi, null nếu hợp lệ
     */
    public static String kiemTraDuLieuChiTietHopLe(chiTietPhieuNhapDTO chiTiet) {
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

        return null; // Dữ liệu hợp lệ
    }
}
