package org.example.DTO;

public class PhanQuyenDTO {
    private String maPhanQuyen;
    private String phanQuyen;
    private String maCV;
    private String module;
    private boolean trangThai;

    public PhanQuyenDTO() {
    }

    public PhanQuyenDTO(String maPhanQuyen, String phanQuyen, String maCV, String module, boolean trangThai) {
        this.maPhanQuyen = maPhanQuyen;
        this.phanQuyen = phanQuyen;
        this.maCV = maCV;
        this.module = module;
        this.trangThai = trangThai;
    }

    public String getMaPhanQuyen() {
        return maPhanQuyen;
    }

    public void setMaPhanQuyen(String maPhanQuyen) {
        this.maPhanQuyen = maPhanQuyen;
    }

    public String getPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(String phanQuyen) {
        this.phanQuyen = phanQuyen;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}