package org.example.DTO;
import java.time.LocalDate;

public class khachHangDTO {
    private String maKH;
    private String hoTen;
    private String diaChi;
    private int SDT;
    private String gioiTinh;
    private String email;
    private LocalDate ngaySinh;
    private int trangThai;


    public khachHangDTO(String maKH, String hoTen, String diaChi, int SDT, String gioiTinh, String email, LocalDate ngaySinh, int trangThai) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.SDT = SDT;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.trangThai = trangThai;
    }

    public String getMaKH() {
        return maKH;
    }
    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getSDT() {
        return SDT;
    }
    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }
    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public int getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
