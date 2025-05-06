package org.example.DTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Lớp DTO cho hóa đơn
 * Đại diện cho bảng HOADON trong CSDL
 */
public class hoaDonDTO {
    private String maHoaDon;     // varchar(20) - Mã hóa đơn
    private double tongTien;    // decimal(15,2) - Tổng tiền hóa đơn
    private String maKH;        // varchar(20) - Mã khách hàng
    private String maNV;        // varchar(20) - Mã nhân viên
    private String maKM;        // varchar(20) - Mã khuyến mãi
    private LocalDateTime thoiGianLap; // datetime - Thời gian lập hóa đơn
    private boolean trangThai;   // bit - Trạng thái hóa đơn

    private List<chiTietHoaDonDTO> chiTietHoaDon; // Danh sách chi tiết hóa đơn

    /**
     * Constructor mặc định
     */
    public hoaDonDTO() {
        // Khởi tạo mặc định
        this.trangThai = true; // Mặc định là hoạt động
    }

    /**
     * Constructor đầy đủ tham số
     */
    public hoaDonDTO(String maHoaDon, double tongTien, LocalDateTime thoiGianLap,
                     String maKH, String maNV, String maKM, boolean trangThai,
                     List<chiTietHoaDonDTO> chiTietHoaDon) {
        this.maHoaDon = maHoaDon;
        this.tongTien = tongTien;
        this.thoiGianLap = thoiGianLap;
        this.maKH = maKH;
        this.maNV = maNV;
        this.maKM = maKM;
        this.trangThai = trangThai;
        this.chiTietHoaDon = chiTietHoaDon;
    }

    /**
     * Lấy mã hóa đơn
     * @return String mã hóa đơn
     */
    public String getMaHoaDon() {
        return maHoaDon;
    }

    /**
     * Đặt mã hóa đơn
     * @param maHoaDon String mã hóa đơn
     */
    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    /**
     * Lấy tổng tiền hóa đơn
     * @return double tổng tiền hóa đơn
     */
    public double getTongTien() {
        return tongTien;
    }

    /**
     * Đặt giá trị tổng tiền cho hóa đơn
     * @param tongTien double tổng tiền hóa đơn
     */
    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    /**
     * Lấy mã khách hàng
     * @return String mã khách hàng
     */
    public String getMaKH() {
        return maKH;
    }

    /**
     * Đặt mã khách hàng
     * @param maKH String mã khách hàng
     */
    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    /**
     * Lấy mã nhân viên lập hóa đơn
     * @return String mã nhân viên
     */
    public String getMaNV() {
        return maNV;
    }

    /**
     * Đặt mã nhân viên lập hóa đơn
     * @param maNV String mã nhân viên
     */
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    /**
     * Lấy mã khuyến mãi
     * @return String mã khuyến mãi
     */
    public String getMaKM() {
        return maKM;
    }

    /**
     * Đặt mã khuyến mãi
     * @param maKM String mã khuyến mãi
     */
    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    /**
     * Lấy thời gian lập hóa đơn
     * @return LocalDateTime thời gian lập
     */
    public LocalDateTime getThoiGianLap() {
        return thoiGianLap;
    }

    /**
     * Đặt thời gian lập hóa đơn
     * @param thoiGianLap LocalDateTime thời gian lập
     */
    public void setThoiGianLap(LocalDateTime thoiGianLap) {
        this.thoiGianLap = thoiGianLap;
    }

    /**
     * Lấy trạng thái hóa đơn
     * @return boolean trạng thái (true: đang hoạt động, false: đã hủy)
     */
    public boolean isTrangThai() {
        return trangThai;
    }

    /**
     * Đặt trạng thái hóa đơn
     * @param trangThai boolean trạng thái (true: đang hoạt động, false: đã hủy)
     */
    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    /**
     * Lấy danh sách chi tiết hóa đơn
     * @return List<chiTietHoaDonDTO> danh sách chi tiết hóa đơn
     */
    public List<chiTietHoaDonDTO> getChiTietHoaDon() {
        return chiTietHoaDon;
    }

    /**
     * Đặt danh sách chi tiết hóa đơn
     * @param chiTietHoaDon List<chiTietHoaDonDTO> danh sách chi tiết hóa đơn
     */
    public void setChiTietHoaDon(List<chiTietHoaDonDTO> chiTietHoaDon) {
        this.chiTietHoaDon = chiTietHoaDon;
    }

    /**
     * Định dạng tổng tiền theo kiểu tiền tệ Việt Nam
     * @return String tổng tiền đã định dạng
     */
    public String getTongTienFormatted() {
        return org.example.Utils.PhuongThucBoSung.formatMoneyVND(getTongTien());
    }

    /**
     * Lấy thông tin chi tiết về hóa đơn
     */
    @Override
    public String toString() {
        return "hoaDonDTO{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", tongTien=" + getTongTienFormatted() +
                ", thoiGianLap=" + thoiGianLap +
                ", maKH='" + maKH + '\'' +
                ", maNV='" + maNV + '\'' +
                ", maKM='" + maKM + '\'' +
                ", trangThai=" + (trangThai ? "Đang hoạt động" : "Đã hủy") +
                ", soLuongSanPham=" + (chiTietHoaDon != null ? chiTietHoaDon.size() : 0) +
                "}";
    }
}