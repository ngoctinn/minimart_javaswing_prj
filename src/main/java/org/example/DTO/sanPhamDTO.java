package org.example.DTO;

public class sanPhamDTO {
    private String maSP;
    private String tenSP;
    private int trangThai;
    private int soLuong;
    private String hinhAnh;
    private String donVi;
    private String maLSP;

    public sanPhamDTO() {

    }
    public sanPhamDTO(String maSP, String tenSP, int trangThai, int soLuong, String hinhAnh, String donVi, String maLSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.trangThai = trangThai;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
        this.donVi = donVi;
        this.maLSP = maLSP;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getMaLSP() {
        return maLSP;
    }

    public void setMaLSP(String maLSP) {
        this.maLSP = maLSP;
    }
}
