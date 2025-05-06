package org.example.BUS;

import org.example.DAO.HoaDonDAO;
import org.example.DAO.ChiTietHoaDonDAO;
import org.example.DTO.hoaDonDTO;
import org.example.DTO.chiTietHoaDonDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Lớp BUS xử lý logic nghiệp vụ liên quan đến hóa đơn
 */
public class HoaDonBUS {
    private HoaDonDAO hoaDonDAO;
    private ChiTietHoaDonDAO chiTietHoaDonDAO;
    private ArrayList<hoaDonDTO> dsHoaDon;

    /**
     * Constructor mặc định
     */
    public HoaDonBUS() {
        hoaDonDAO = new HoaDonDAO();
        chiTietHoaDonDAO = new ChiTietHoaDonDAO();
        dsHoaDon = new ArrayList<>();
    }

    /**
     * Lấy danh sách hóa đơn
     * @return ArrayList chứa các hóa đơn
     */
    public ArrayList<hoaDonDTO> getDsHoaDon() {
        return dsHoaDon;
    }

    /**
     * Đọc danh sách hóa đơn từ CSDL
     * @return true nếu đọc thành công, false nếu thất bại
     */
    public boolean docDanhSachHoaDon() {
        dsHoaDon = hoaDonDAO.layDanhSachHoaDon();
        return dsHoaDon != null;
    }

    /**
     * Thêm hóa đơn mới
     * @param hoaDon Đối tượng hóa đơn cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean themHoaDon(hoaDonDTO hoaDon) {
        // Kiểm tra dữ liệu đầu vào
        if (hoaDon == null) {
            return false;
        }

        // Tạo mã hóa đơn mới nếu chưa có
        if (hoaDon.getMaHoaDon() == null || hoaDon.getMaHoaDon().isEmpty()) {
            hoaDon.setMaHoaDon(hoaDonDAO.taoMaHoaDonMoi());
        }

        // Đặt thời gian lập hóa đơn nếu chưa có
        if (hoaDon.getThoiGianLap() == null) {
            hoaDon.setThoiGianLap(LocalDateTime.now());
        }

        // Thêm hóa đơn vào CSDL
        int result = hoaDonDAO.insert(hoaDon);
        if (result > 0) {
            // Thêm thành công, cập nhật danh sách
            dsHoaDon.add(hoaDon);
            return true;
        }
        return false;
    }

    /**
     * Cập nhật thông tin hóa đơn
     * @param hoaDon Đối tượng hóa đơn cần cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean capNhatHoaDon(hoaDonDTO hoaDon) {
        // Kiểm tra dữ liệu đầu vào
        if (hoaDon == null || hoaDon.getMaHoaDon() == null || hoaDon.getMaHoaDon().isEmpty()) {
            return false;
        }

        // Cập nhật hóa đơn trong CSDL
        int result = hoaDonDAO.update(hoaDon);
        if (result > 0) {
            // Cập nhật thành công, cập nhật danh sách
            for (int i = 0; i < dsHoaDon.size(); i++) {
                if (dsHoaDon.get(i).getMaHoaDon().equals(hoaDon.getMaHoaDon())) {
                    dsHoaDon.set(i, hoaDon);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Xóa hóa đơn (cập nhật trạng thái thành false)
     * @param maHoaDon Mã hóa đơn cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean xoaHoaDon(String maHoaDon) {
        // Kiểm tra dữ liệu đầu vào
        if (maHoaDon == null || maHoaDon.isEmpty()) {
            return false;
        }

        // Tìm hóa đơn cần xóa
        hoaDonDTO hoaDon = timHoaDonTheoMa(maHoaDon);
        if (hoaDon == null) {
            return false;
        }

        // Cập nhật trạng thái thành false
        hoaDon.setTrangThai(false);
        int result = hoaDonDAO.update(hoaDon);
        if (result > 0) {
            // Xóa thành công, cập nhật danh sách
            for (int i = 0; i < dsHoaDon.size(); i++) {
                if (dsHoaDon.get(i).getMaHoaDon().equals(maHoaDon)) {
                    dsHoaDon.remove(i);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Tìm hóa đơn theo mã hóa đơn
     * @param maHoaDon Mã hóa đơn cần tìm
     * @return Đối tượng hóa đơn tìm được, null nếu không tìm thấy
     */
    public hoaDonDTO timHoaDonTheoMa(String maHoaDon) {
        // Kiểm tra dữ liệu đầu vào
        if (maHoaDon == null || maHoaDon.isEmpty()) {
            return null;
        }

        // Tìm trong danh sách đã load
        for (hoaDonDTO hoaDon : dsHoaDon) {
            if (hoaDon.getMaHoaDon().equals(maHoaDon)) {
                return hoaDon;
            }
        }

        // Nếu không tìm thấy trong danh sách, tìm trong CSDL
        hoaDonDTO hoaDon = new hoaDonDTO();
        hoaDon.setMaHoaDon(maHoaDon);
        return hoaDonDAO.selectById(hoaDon);
    }

    /**
     * Tìm kiếm hóa đơn theo từ khóa
     * @param keyword Từ khóa tìm kiếm
     * @return ArrayList chứa các hóa đơn tìm thấy
     */
    public ArrayList<hoaDonDTO> timKiemHoaDon(String keyword) {
        // Kiểm tra dữ liệu đầu vào
        if (keyword == null || keyword.isEmpty()) {
            return dsHoaDon;
        }

        // Tìm kiếm trong CSDL
        return hoaDonDAO.timKiemHoaDon(keyword);
    }


    /**
     * Lấy danh sách hóa đơn theo khoảng thời gian
     * @param tuNgay Thời gian bắt đầu
     * @param denNgay Thời gian kết thúc
     * @return ArrayList chứa các hóa đơn trong khoảng thời gian
     */
    public ArrayList<hoaDonDTO> layHoaDonTheoKhoangThoiGian(LocalDateTime tuNgay, LocalDateTime denNgay) {
        // Kiểm tra dữ liệu đầu vào
        if (tuNgay == null || denNgay == null) {
            return dsHoaDon;
        }

        // Lấy hóa đơn theo khoảng thời gian từ CSDL
        return hoaDonDAO.layHoaDonTheoKhoangThoiGian(tuNgay, denNgay);
    }

    /**
     * Tính tổng doanh thu theo khoảng thời gian
     * @param tuNgay Thời gian bắt đầu
     * @param denNgay Thời gian kết thúc
     * @return Tổng doanh thu
     */
    public double tinhTongDoanhThu(LocalDateTime tuNgay, LocalDateTime denNgay) {
        // Lấy danh sách hóa đơn trong khoảng thời gian
        ArrayList<hoaDonDTO> dsHoaDonTheoThoiGian = layHoaDonTheoKhoangThoiGian(tuNgay, denNgay);
        
        // Tính tổng doanh thu
        double tongDoanhThu = 0;
        for (hoaDonDTO hoaDon : dsHoaDonTheoThoiGian) {
            tongDoanhThu += hoaDon.getTongTien();
        }
        
        return tongDoanhThu;
    }

    /**
     * Tạo hóa đơn mới
     * @param maKH Mã khách hàng
     * @param maNV Mã nhân viên
     * @param maKM Mã khuyến mãi
     * @return Đối tượng hóa đơn mới tạo, null nếu tạo thất bại
     */
    public hoaDonDTO taoHoaDonMoi(String maKH, String maNV, String maKM) {
        // Tạo đối tượng hóa đơn mới
        hoaDonDTO hoaDon = new hoaDonDTO();
        hoaDon.setMaHoaDon(hoaDonDAO.taoMaHoaDonMoi());
        hoaDon.setTongTien(0);
        hoaDon.setMaKH(maKH);
        hoaDon.setMaNV(maNV);
        hoaDon.setMaKM(maKM);
        hoaDon.setThoiGianLap(LocalDateTime.now());
        hoaDon.setTrangThai(true);
        
        // Thêm hóa đơn vào CSDL
        if (themHoaDon(hoaDon)) {
            return hoaDon;
        }
        return null;
    }
}