package org.example.DTO;

import java.time.LocalDate;

public class loHangDTO {
    private String maLoHang;
    private String maSP;
    private LocalDate ngaySanXuat;
    private LocalDate ngayHetHan;
    private int soLuong;
    private String trangThai;

    public loHangDTO() {

    }
    public loHangDTO(String maLoHang, String maSP, LocalDate ngaySanXuat, LocalDate ngayHetHan, int soLuong, String trangThai) {
        this.maLoHang = maLoHang;
        this.maSP = maSP;
        this.ngaySanXuat = ngaySanXuat;
        this.ngayHetHan = ngayHetHan;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }

    public String getMaLoHang() {
        return maLoHang;
    }
    public void setMaLoHang(String maLoHang) {
        this.maLoHang = maLoHang;
    }

    public String getMaSP() {
        return maSP;
    }
    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public LocalDate getNgaySanXuat() {
        return ngaySanXuat;
    }
    public void setNgaySanXuat(LocalDate ngaySanXuat) {
        this.ngaySanXuat = ngaySanXuat;
    }

    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }
    public void setNgayHetHan(LocalDate ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
