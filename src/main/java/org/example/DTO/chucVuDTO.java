package org.example.DTO;

public class chucVuDTO {
    private String tenCV;
    private String maCV;

    public chucVuDTO() {

    }
    public chucVuDTO( String tenCV, String maCV) {
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
