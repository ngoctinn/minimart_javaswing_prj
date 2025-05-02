package org.example.DTO;

public class PhanQuyenDTO {
    private String maCV;
    private String maChucNang;
    private int quyen;

    public PhanQuyenDTO() {
    }
    public PhanQuyenDTO(String maCV, String maChucNang, int quyen) {
        this.maCV = maCV;
        this.maChucNang = maChucNang;
        this.quyen = quyen;
    }
    public String getMaCV() {
        return maCV;
    }
    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }
    public String getMaChucNang() {
        return maChucNang;
    }
    public void setMaChucNang(String maChucNang) {
        this.maChucNang = maChucNang;
    }
    public int getQuyen() {
        return quyen;
    }
    public void setQuyen(int quyen) {
        this.quyen = quyen;
    }
}