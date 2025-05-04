package org.example.DTO;

import java.time.LocalDate;

public class hopDongDTO {
    private String maHopDong;    // varchar(20)
    private LocalDate ngayBD;    // date
    private LocalDate ngayKT;    // date
    private double luongCB;      // decimal(15,2)
    private String maNV;         // varchar(20)
    private boolean trangThai;   // bit
    private String loaiHopDong;  // nvarchar(20)

    public hopDongDTO() {
        // Constructor mặc định
    }

    public hopDongDTO(String maHopDong, LocalDate ngayBD, LocalDate ngayKT, double luongCB, String maNV, boolean trangThai, String loaiHopDong) {
        this.maHopDong = maHopDong;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.luongCB = luongCB;
        this.maNV = maNV;
        this.trangThai = trangThai;
        this.loaiHopDong = loaiHopDong;
    }

    // Constructor không có trangThai và loaiHopDong (cho tương thích ngược)
    public hopDongDTO(String maHopDong, String maNV, double luongCB, LocalDate ngayBD, LocalDate ngayKT) {
        this.maHopDong = maHopDong;
        this.maNV = maNV;
        this.luongCB = luongCB;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.trangThai = true; // Mặc định là hoạt động
        this.loaiHopDong = "Chính thức"; // Mặc định là hợp đồng chính thức
    }

    // Getters và Setters
    public String getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(String maHopDong) {
        this.maHopDong = maHopDong;
    }

    public LocalDate getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(LocalDate ngayBD) {
        this.ngayBD = ngayBD;
    }

    public LocalDate getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(LocalDate ngayKT) {
        this.ngayKT = ngayKT;
    }

    public double getLuongCB() {
        return luongCB;
    }

    public void setLuongCB(double luongCB) {
        this.luongCB = luongCB;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getLoaiHopDong() {
        return loaiHopDong;
    }

    public void setLoaiHopDong(String loaiHopDong) {
        this.loaiHopDong = loaiHopDong;
    }
}
