package org.example.DTO;
import java.util.List;
import java.time.LocalDate;

public class phieuNhapDTO {
    private String maPN;
    private String maNCC;
    private String maNV;
    private double tongTien;
    private LocalDate ngayLap;
    private LocalDate gioLap;
    private List<chiTietPhieuNhapDTO> chiTietPhieuNhap;

    public phieuNhapDTO() {

    }
    public phieuNhapDTO(String maPN, String maNCC, String maNV, double tongTien, LocalDate ngayLap, LocalDate gioLap, List<chiTietPhieuNhapDTO> chiTietPhieuNhap) {
        this.maPN = maPN;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
        this.gioLap = gioLap;
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
        if(chiTietPhieuNhap == null && chiTietPhieuNhap.isEmpty()) {
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

    public LocalDate getGioLap() {
        return gioLap;
    }
    public void setGioLap(LocalDate gioLap) {
        this.gioLap = gioLap;
    }
}
