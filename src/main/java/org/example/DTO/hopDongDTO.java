package org.example.DTO;

import java.time.LocalDate;

public class hopDongDTO {
    private String maHopDong;    // varchar(20) NOT NULL
    private LocalDate ngayBD;    // date NULL
    private LocalDate ngayKT;    // date NULL
    private double luongCB;      // decimal(15,2) NULL
    private String maNV;         // varchar(20) NOT NULL
    private boolean trangThai;   // bit NULL
    private String hinhThucLamViec;  // nvarchar(20) NULL

    public hopDongDTO() {
        // Constructor mặc định
    }

    public hopDongDTO(String maHopDong, LocalDate ngayBD, LocalDate ngayKT, double luongCB, String maNV, boolean trangThai, String hinhThucLamViec) {
        this.maHopDong = maHopDong;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.luongCB = luongCB;
        this.maNV = maNV;
        this.trangThai = trangThai;
        this.hinhThucLamViec = hinhThucLamViec;
    }

    // Constructor không có trangThai và hinhThucLamViec (cho tương thích ngược)
    public hopDongDTO(String maHopDong, String maNV, double luongCB, LocalDate ngayBD, LocalDate ngayKT) {
        this.maHopDong = maHopDong;
        this.maNV = maNV;
        this.luongCB = luongCB;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.trangThai = true; // Mặc định là hoạt động
        this.hinhThucLamViec = "Toàn thời gian"; // Mặc định là toàn thời gian
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

    public String getHinhThucLamViec() {
        return hinhThucLamViec;
    }

    public void setHinhThucLamViec(String hinhThucLamViec) {
        this.hinhThucLamViec = hinhThucLamViec;
    }
}
