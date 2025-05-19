package org.example.DTO;

import java.math.BigDecimal;

public class LuongDTO {
    private String maLuong;
    private String maNV;
    private Integer tongNgayCong;
    private BigDecimal luongCoBan;
    private BigDecimal luongThuong;
    private BigDecimal luongThucNhan;

    // Default constructor
    public LuongDTO() {
    }

    // Parameterized constructor
    public LuongDTO(String maLuong, String maNV, Integer tongNgayCong, BigDecimal luongCoBan,
                    BigDecimal luongThuong, BigDecimal luongThucNhan) {
        this.maLuong = maLuong;
        this.maNV = maNV;
        this.tongNgayCong = tongNgayCong;
        this.luongCoBan = luongCoBan;
        this.luongThuong = luongThuong;
        this.luongThucNhan = luongThucNhan;
    }

    // Getters and Setters
    public String getMaLuong() {
        return maLuong;
    }

    public void setMaLuong(String maLuong) {
        this.maLuong = maLuong;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Integer getTongNgayCong() {
        return tongNgayCong;
    }

    public void setTongNgayCong(Integer tongNgayCong) {
        this.tongNgayCong = tongNgayCong;
    }

    public BigDecimal getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(BigDecimal luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public BigDecimal getLuongThuong() {
        return luongThuong;
    }

    public void setLuongThuong(BigDecimal luongThuong) {
        this.luongThuong = luongThuong;
    }

    public BigDecimal getLuongThucNhan() {
        return luongThucNhan;
    }

    public void setLuongThucNhan(BigDecimal luongThucNhan) {
        this.luongThucNhan = luongThucNhan;
    }

    @Override
    public String toString() {
        return "LuongDTO{" +
                "maLuong='" + maLuong + '\'' +
                ", maNV='" + maNV + '\'' +
                ", tongNgayCong=" + tongNgayCong +
                ", luongCoBan=" + luongCoBan +
                ", luongThuong=" + luongThuong +
                ", luongThucNhan=" + luongThucNhan +
                '}';
    }
}