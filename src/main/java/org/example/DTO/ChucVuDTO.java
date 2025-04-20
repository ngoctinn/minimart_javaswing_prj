package org.example.DTO;

public class ChucVuDTO {
    private String tenCV;
    private String maCV;

    public ChucVuDTO() {

    }
    public ChucVuDTO(String tenCV, String maCV) {
        this.tenCV = tenCV;
        this.maCV = maCV;
    }

    public String getTenCV() {
        return tenCV;
    }
    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }

    public String getMaCV() {
        return maCV;
    }
    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

}
