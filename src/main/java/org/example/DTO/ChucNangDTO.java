package org.example.DTO;

public class ChucNangDTO {
    private String maChucNang;
    private String tenChucNang;

    public ChucNangDTO() {
    }
    public ChucNangDTO(String maChucNang, String tenChucNang) {
        this.maChucNang = maChucNang;
        this.tenChucNang = tenChucNang;
    }
    public String getMaChucNang() {
        return maChucNang;
    }
    public void setMaChucNang(String maChucNang) {
        this.maChucNang = maChucNang;
    }
    public String getTenChucNang() {
        return tenChucNang;
    }
    public void setTenChucNang(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }
    @Override
    public String toString() {
        return "ChucNangDTO{" +
                "maChucNang='" + maChucNang + '\'' +
                ", tenChucNang='" + tenChucNang + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ChucNangDTO that = (ChucNangDTO) obj;
        return maChucNang.equals(that.maChucNang);
    }
}
