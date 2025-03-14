package org.example.DTO;

import java.time.LocalDate;

public class chiTietPhieuNhapDTO {
    private String maPN;
    private String maSP;
    private int soLuong;
    private double giaNhap;
    private LocalDate ngaySX;
    private LocalDate hanSD;

    public chiTietPhieuNhapDTO() {

    }
    public chiTietPhieuNhapDTO(String maPN, String maSP, int soLuong, double giaNhap, LocalDate ngaySX, LocalDate hanSD) {
        this.maPN = maPN;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.ngaySX = ngaySX;
        this.hanSD = hanSD;
    }

    public String getMaPN() {
        return maPN;
    }
    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public String getMaSP() {
        return maSP;
    }
    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaNhap() {
        return giaNhap;
    }
    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public LocalDate getNgaySX() {
        return ngaySX;
    }
    public void setNgaySX(LocalDate ngaySX) {
        this.ngaySX = ngaySX;
    }

    public LocalDate getHanSD() {
        return hanSD;
    }
    public void setHanSD(LocalDate hanSD) {
        this.hanSD = hanSD;
    }

}
