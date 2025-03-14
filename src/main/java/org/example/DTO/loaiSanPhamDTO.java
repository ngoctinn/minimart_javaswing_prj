package org.example.DTO;

public class loaiSanPhamDTO {
    private String maLSP;
    private String tenSP;
    private int trangThai;

    public loaiSanPhamDTO() {

    }

    public loaiSanPhamDTO( String maLSP, String tenSP, int trangThai) {
        this.maLSP = maLSP;

        this.tenSP = tenSP;
        this.trangThai = trangThai;
    }

    public String getMaLSP() {
        return maLSP;
    }
    public void setMaLSP(String maLSP) {
        this.maLSP = maLSP;
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
}

