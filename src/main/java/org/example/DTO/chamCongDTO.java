package org.example.DTO;
import java.time.LocalDateTime;

public class chamCongDTO {
    private String maNV;
    private String maChamCong;
    private int namChamCong;
    private int thangChamCong;
    private int soGioLam;
    private float luongThuong;
    private float khauTru;
    private float luongThucNhan;
    private int soNgayLam;

    public chamCongDTO() {

    }

    public chamCongDTO(String maNV, String maChamCong, int namChamCong, int thangChamCong, int soGioLam, float luongThuong, float khauTru, float luongThucNhan, int soNgayLam) {
        this.maNV = maNV;
        this.maChamCong = maChamCong;
        this.namChamCong = namChamCong;
        this.thangChamCong = thangChamCong;
        this.soGioLam = soGioLam;
        this.luongThuong = luongThuong;
        this.khauTru = khauTru;
        this.luongThucNhan = luongThucNhan;
        this.soNgayLam = soNgayLam;
    }
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    public String getMaNV() {
        return maNV;
    }

    public void setMaChamCong(String maChamCong) {
        this.maChamCong = maChamCong;
    }
    public String getMaChamCong() {
        return maChamCong;
    }

    public void setNamChamCong(int namChamCong) {
        this.namChamCong = namChamCong;
    }
    public int getNamChamCong() {
        return namChamCong;
    }

    public void setThangChamCong(int thangChamCong) {
        this.thangChamCong = thangChamCong;
    }
    public int getThangChamCong() {
        return thangChamCong;
    }

    public void setSoGioLam(int soGioLam) {
        this.soGioLam = soGioLam;
    }
    public int getSoGioLam() {
        return soGioLam;
    }

    // Chưa viết
    public float getLuongThuong() {
        return luongThuong;
    }

    public void setKhauTru(float khauTru) {
        this.khauTru = khauTru;
    }
    public float getKhauTru() {
        return khauTru;
    }

    // Chưa viết
    public float getLuongThucNhan() {
        return this.luongThuong - this.khauTru;
    }

    public void setSoNgayLam(int soNgayLam) {
        this.soNgayLam = soNgayLam;
    }
    public int getSoNgayLam() {
        return soNgayLam;
    }

}
