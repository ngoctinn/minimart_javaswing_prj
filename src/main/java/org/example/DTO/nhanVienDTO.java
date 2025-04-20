package org.example.DTO;

import java.time.LocalDate;

public class nhanVienDTO {
    private String maNV;
    private String tenNV;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String email;
    private int trangThai;
    private double soDT;
    private String matKhau;
    private String maCV;
    private String maHD;
    
    // Constructors
    public nhanVienDTO() {
    }
    
    public nhanVienDTO(String maNV, String tenNV, LocalDate ngaySinh, String gioiTinh, String diaChi, 
                      String email, int trangThai, double soDT, String matKhau, String maCV, String maHD) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.email = email;
        this.trangThai = trangThai;
        this.soDT = soDT;
        this.matKhau = matKhau;
        this.maCV = maCV;
        this.maHD = maHD;
    }
    
    // Getters and Setters
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public double getSoDT() {
        return soDT;
    }

    public void setSoDT(double soDT) {
        this.soDT = soDT;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }
}
