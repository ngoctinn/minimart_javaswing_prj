package org.example.DTO;

import java.time.LocalDate;

public class hopDongDTO {
    private String maHopDong;
    private String maNV;
    private double luongCB;
    private LocalDate ngayBD;
    private LocalDate ngayKT;

    public hopDongDTO(){

    }

    public hopDongDTO(String maHopDong, String maNV, double luongCB, LocalDate ngayBD, LocalDate ngayKT) {
        this.maHopDong = maHopDong;
        this.maNV = maNV;
        this.luongCB = luongCB;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
    }

    public String getMaHopDong(){
        return maHopDong;
    }
    public void setMaHopDong(){
        this.maHopDong = maHopDong;
    }

    public String getMaNV(){
        return maNV;
    }
    public void setMaNV(){
        this.maNV = maNV;
    }

    public double getLuongCB(){
        return luongCB;
    }
    public void setLuongCB(){
        this.luongCB = luongCB;
    }

    public LocalDate getNgayBD(){
        return ngayBD;
    }
    public void setNgayBD(){
        this.ngayBD = ngayBD;
    }

    public LocalDate getNgayKT(){
        return ngayKT;
    }
    public void setNgayKT(){
        this.ngayKT = ngayKT;
    }

}
