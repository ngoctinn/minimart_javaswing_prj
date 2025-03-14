package org.example.DTO;
import java.time.LocalDate;

public class khuyenMaiDTO {
    private String maKM;
    private String tenKM;
    // dieuKien chua biet
    private String dieuKien;
    private LocalDate ngayBD;
    private LocalDate ngayKT;
    private float phanTram;

    public khuyenMaiDTO() {

    }
    public khuyenMaiDTO(String maKM, String tenKM, String dieuKien, LocalDate ngayBD, LocalDate ngayKT) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.dieuKien = dieuKien;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.phanTram = phanTram;
    }

    public String getMaKM() {
        return maKM;
    }
    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenKM() {
        return tenKM;
    }
    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }
    //Dieu kien chua biet
    public String getDieuKien() {
        return dieuKien;
    }
    public void setDieuKien(String dieuKien) {
        this.dieuKien = dieuKien;
    }

    public LocalDate getNgayBD() {
        return ngayBD;
    }
    public void setNgayBD(LocalDate ngayBD) {
        this.ngayBD = ngayBD;
    }

    public LocalDate getNgayKT() {
        return ngayKT;
    }
    public void setNgayKT(LocalDate ngayKT) {
        this.ngayKT = ngayKT;
    }

    public float getPhanTram() {
        return phanTram;
    }
}
