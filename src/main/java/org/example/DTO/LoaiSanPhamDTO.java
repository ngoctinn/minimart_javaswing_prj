package org.example.DTO;

public class LoaiSanPhamDTO {
    private String maLSP;
    private String tenLSP;
    private int trangThai;

    public LoaiSanPhamDTO() {

    }

    public LoaiSanPhamDTO(String maLSP, String tenLSP, int trangThai) {
        this.maLSP = maLSP;
        this.tenLSP = tenLSP;
        this.trangThai = trangThai;
    }

    public String getMaLSP() {
        return maLSP;
    }
    public void setMaLSP(String maLSP) {
        this.maLSP = maLSP;
    }

    public String getTenLSP() {
        return tenLSP;
    }
    public void setTenLSP(String tenLSP) {
        this.tenLSP = tenLSP;
    }

    public int getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}

