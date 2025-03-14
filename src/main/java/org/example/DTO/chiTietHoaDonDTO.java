package org.example.DTO;
import java.util.ArrayList;
import java.util.List;

public class chiTietHoaDonDTO {
    private String maHoaDon;
    private String maSP;
    private int soLuong;
    private float giaBan;

    public chiTietHoaDonDTO() {

    }
    public chiTietHoaDonDTO(String maHoaDon, String maSP, int soLuong, float giaBan) {
        this.giaBan = giaBan;
        this.maSP = maSP;
        this.maHoaDon = maHoaDon;
        this.soLuong = soLuong;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }
    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
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

    public float getGiaBan() {
        return giaBan;
    }
    public void setGiaBan(float giaBan) {
        this.giaBan = giaBan;
    }
}
