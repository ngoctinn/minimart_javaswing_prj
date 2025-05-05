package org.example.DTO;

/**
 * Lớp DTO đại diện cho chi tiết phiếu nhập hàng
 * Dựa trên cấu trúc bảng CHITIETPHIEUNHAP trong CSDL
 */
public class chiTietPhieuNhapDTO {
    private String maPN;        // varchar(20) - Mã phiếu nhập
    private int soLuong;        // int - Số lượng
    private double giaNhap;     // decimal(15,2) - Giá nhập
    private String maLoHang;    // varchar(20) - Mã lô hàng

    /**
     * Constructor mặc định
     */
    public chiTietPhieuNhapDTO() {

    }

    /**
     * Constructor đầy đủ tham số
     */
    public chiTietPhieuNhapDTO(String maPN, int soLuong, double giaNhap, String maLoHang) {
        this.maPN = maPN;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.maLoHang = maLoHang;
    }

    public String getMaPN() {
        return maPN;
    }
    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public String getMaLoHang() {
        return maLoHang;
    }
    public void setMaLoHang(String maLoHang) {
        this.maLoHang = maLoHang;
    }

    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaNhap() {
        return giaNhap;
    }
    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }
}
