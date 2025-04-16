package org.example.DTO;

public class SanPhamDTO {
    private String maSP;
    private String tenSP;
    private boolean trangThai;
    private int tonKho;
    private String hinhAnh;
    private String donVi;
    private String maLSP;
    private double giaBan;

    public SanPhamDTO() {

    }
    public SanPhamDTO(String maSP, String tenSP, boolean trangThai, int tonKho, String hinhAnh, String donVi, String maLSP, double giaBan) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.trangThai = trangThai;
        this.tonKho = tonKho;
        this.hinhAnh = hinhAnh;
        this.donVi = donVi;
        this.maLSP = maLSP;
        this.giaBan = giaBan;
    }

    public SanPhamDTO(String maSP, String tenSP, boolean trangThai, String hinhAnh, String donVi, String maLSP, double giaBan) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
        this.donVi = donVi;
        this.maLSP = maLSP;
        this.giaBan = giaBan;
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

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getTonKho() {
        return tonKho;
    }

    public void setTonKho(int tonKho) {
        this.tonKho = tonKho;
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

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }


}
