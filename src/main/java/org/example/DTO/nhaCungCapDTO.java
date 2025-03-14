package org.example.DTO;

public class nhaCungCapDTO {
    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private double SDT;

    public nhaCungCapDTO() {

    }
    public nhaCungCapDTO(String maNCC, String tenNCC, String diaChi, double SDT) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.SDT = SDT;
    }

    public String getMaNCC() {
        return maNCC;
    }
    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }
    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getDiaChi() {
        return diaChi;
    }
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public double getSDT() {
        return SDT;
    }
    public void setSDT(double SDT) {
        this.SDT = SDT;
    }
}
