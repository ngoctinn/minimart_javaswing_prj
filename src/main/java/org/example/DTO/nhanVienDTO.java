package org.example.DTO;

import java.time.LocalDate;

/**
 * DTO đại diện cho thông tin nhân viên
 * Dựa trên cấu trúc bảng NhanVien trong CSDL
 */
public class nhanVienDTO {
    private String maNV;       // Mã nhân viên (varchar(20))
    private String hoTen;      // Họ tên nhân viên (nvarchar(50))
    private LocalDate ngaySinh; // Ngày sinh (date)
    private String gioiTinh;   // Giới tính (nvarchar(10))
    private String diaChi;     // Địa chỉ (text)
    private String email;      // Email (varchar(255))
    private String SDT;        // Số điện thoại (varchar(15))
    private boolean trangThai; // Trạng thái (bit)
    private String maCV;       // Mã chức vụ (varchar(20))
    private String matKhau;    // Mật khẩu (varchar(255))

    // Constructors
    public nhanVienDTO() {
    }

    public nhanVienDTO(String maNV, String hoTen, LocalDate ngaySinh, String gioiTinh, String diaChi,
                       String email, String SDT, boolean trangThai, String maCV) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.email = email;
        this.SDT = SDT;
        this.trangThai = trangThai;
        this.maCV = maCV;
    }

    public nhanVienDTO(String maNV, String hoTen, LocalDate ngaySinh, String gioiTinh, String diaChi,
                       String email, String SDT, boolean trangThai, String maCV, String matKhau) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.email = email;
        this.SDT = SDT;
        this.trangThai = trangThai;
        this.maCV = maCV;
        this.matKhau = matKhau;
    }

    // Getters and Setters
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
