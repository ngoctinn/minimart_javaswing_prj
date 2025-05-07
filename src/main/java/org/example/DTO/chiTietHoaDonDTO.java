package org.example.DTO;

/**
 * Lớp DTO cho chi tiết hóa đơn
 * Đại diện cho bảng CHITIETHOADON trong CSDL
 */
public class chiTietHoaDonDTO {
    private String maHoaDon;    // varchar(20) - Mã hóa đơn
    private String maSP;       // varchar(20) - Mã sản phẩm
    private int soLuong;       // int - Số lượng
    private double giaBan;     // decimal(15,2) - Giá bán

    // Các trường bổ sung (không lưu trong CSDL)
    private String tenSP;       // Tên sản phẩm (lấy từ bảng SANPHAM)
    private String donViTinh;   // Đơn vị tính (lấy từ bảng SANPHAM)

    /**
     * Constructor mặc định
     */
    public chiTietHoaDonDTO() {
        // Khởi tạo mặc định
    }

    /**
     * Constructor với các trường cơ bản
     * @param maHoaDon Mã hóa đơn
     * @param maSP Mã sản phẩm
     * @param soLuong Số lượng
     * @param giaBan Giá bán
     */
    public chiTietHoaDonDTO(String maHoaDon, String maSP, int soLuong, double giaBan) {
        this.maHoaDon = maHoaDon;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
    }

    /**
     * Constructor đầy đủ tham số mở rộng
     * @param maHoaDon Mã hóa đơn
     * @param maSP Mã sản phẩm
     * @param soLuong Số lượng
     * @param giaBan Giá bán
     * @param tenSP Tên sản phẩm
     * @param donViTinh Đơn vị tính
     */
    public chiTietHoaDonDTO(String maHoaDon, String maSP, int soLuong, double giaBan, String tenSP, String donViTinh) {
        this.maHoaDon = maHoaDon;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.tenSP = tenSP;
        this.donViTinh = donViTinh;
    }

    /**
     * Lấy mã hóa đơn
     * @return String mã hóa đơn
     */
    public String getMaHoaDon() {
        return maHoaDon;
    }

    /**
     * Đặt mã hóa đơn
     * @param maHoaDon String mã hóa đơn
     */
    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    /**
     * Lấy mã sản phẩm
     * @return String mã sản phẩm
     */
    public String getMaSP() {
        return maSP;
    }

    /**
     * Đặt mã sản phẩm
     * @param maSP String mã sản phẩm
     */
    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    /**
     * Lấy số lượng
     * @return int số lượng
     */
    public int getSoLuong() {
        return soLuong;
    }

    /**
     * Đặt số lượng
     * @param soLuong int số lượng
     */
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    /**
     * Lấy giá bán
     * @return double giá bán
     */
    public double getGiaBan() {
        return giaBan;
    }

    /**
     * Đặt giá bán
     * @param giaBan double giá bán
     */
    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    /**
     * Lấy tên sản phẩm
     * @return String tên sản phẩm
     */
    public String getTenSP() {
        return tenSP;
    }

    /**
     * Đặt tên sản phẩm
     * @param tenSP String tên sản phẩm
     */
    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    /**
     * Lấy đơn vị tính
     * @return String đơn vị tính
     */
    public String getDonViTinh() {
        return donViTinh;
    }

    /**
     * Đặt đơn vị tính
     * @param donViTinh String đơn vị tính
     */
    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    /**
     * Tính thành tiền (số lượng * giá bán)
     * @return double thành tiền
     */
    public double getThanhTien() {
        return soLuong * giaBan;
    }

    /**
     * Định dạng giá bán theo kiểu tiền tệ Việt Nam
     * @return String giá bán đã định dạng
     */
    public String getGiaBanFormatted() {
        return org.example.Utils.PhuongThucBoSung.formatMoneyVND(giaBan);
    }

    /**
     * Định dạng thành tiền theo kiểu tiền tệ Việt Nam
     * @return String thành tiền đã định dạng
     */
    public String getThanhTienFormatted() {
        return org.example.Utils.PhuongThucBoSung.formatMoneyVND(getThanhTien());
    }

    /**
     * Lấy thông tin chi tiết về chi tiết hóa đơn
     */
    @Override
    public String toString() {
        return "chiTietHoaDonDTO{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", maSP='" + maSP + '\'' +
                ", tenSP='" + (tenSP != null ? tenSP : "Chưa có thông tin") + '\'' +
                ", soLuong=" + soLuong +
                ", donViTinh='" + (donViTinh != null ? donViTinh : "Chưa có thông tin") + '\'' +
                ", giaBan=" + getGiaBanFormatted() +
                ", thanhTien=" + getThanhTienFormatted() +
                "}";
    }
}