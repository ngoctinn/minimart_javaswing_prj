package org.example.DTO;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Lớp DTO đại diện cho phiếu nhập hàng
 * Dựa trên cấu trúc bảng PHIEUNHAP trong CSDL
 */
public class phieuNhapDTO {
    private String maPN;        // varchar(20) - Mã phiếu nhập
    private LocalDate ngayLap;  // date - Ngày lập phiếu
    private LocalTime gioLap;   // time(7) - Giờ lập phiếu
    private double tongTien;    // decimal(15,2) - Tổng tiền
    private String maNCC;       // varchar(20) - Mã nhà cung cấp
    private String maNV;        // varchar(20) - Mã nhân viên
    private boolean trangThai;  // bit - Trạng thái (1: hoạt động, 0: đã xóa)
    private List<chiTietPhieuNhapDTO> chiTietPhieuNhap; // Danh sách chi tiết phiếu nhập

    /**
     * Constructor mặc định
     */
    public phieuNhapDTO() {
        this.trangThai = true; // Mặc định là hoạt động
    }

    /**
     * Constructor đầy đủ tham số
     */
    public phieuNhapDTO(String maPN, LocalDate ngayLap, LocalTime gioLap, double tongTien, String maNCC, String maNV, boolean trangThai, List<chiTietPhieuNhapDTO> chiTietPhieuNhap) {
        this.maPN = maPN;
        this.ngayLap = ngayLap;
        this.gioLap = gioLap;
        this.tongTien = tongTien;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.trangThai = trangThai;
        this.chiTietPhieuNhap = chiTietPhieuNhap;
    }

    /**
     * Constructor không có trạng thái (mặc định là hoạt động)
     */
    public phieuNhapDTO(String maPN, LocalDate ngayLap, LocalTime gioLap, double tongTien, String maNCC, String maNV, List<chiTietPhieuNhapDTO> chiTietPhieuNhap) {
        this.maPN = maPN;
        this.ngayLap = ngayLap;
        this.gioLap = gioLap;
        this.tongTien = tongTien;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.trangThai = true; // Mặc định là hoạt động
        this.chiTietPhieuNhap = chiTietPhieuNhap;
    }

    public String getMaPN() {
        return maPN;
    }
    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public String getMaNCC() {
        return maNCC;
    }
    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }
    public String getMaNV() {
        return maNV;
    }
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public double getTongTien() {
        // Nếu đã có giá trị tổng tiền, trả về giá trị đó
        if (tongTien > 0) {
            return tongTien;
        }

        // Nếu chưa có giá trị tổng tiền, tính từ chi tiết phiếu nhập
        if(chiTietPhieuNhap == null || chiTietPhieuNhap.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for(chiTietPhieuNhapDTO ctpn : chiTietPhieuNhap) {
            sum += ctpn.getSoLuong() * ctpn.getGiaNhap();
        }
        return sum;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }
    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public LocalTime getGioLap() {
        return gioLap;
    }
    public void setGioLap(LocalTime gioLap) {
        this.gioLap = gioLap;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public List<chiTietPhieuNhapDTO> getChiTietPhieuNhap() {
        return chiTietPhieuNhap;
    }

    public void setChiTietPhieuNhap(List<chiTietPhieuNhapDTO> chiTietPhieuNhap) {
        this.chiTietPhieuNhap = chiTietPhieuNhap;
    }
}
