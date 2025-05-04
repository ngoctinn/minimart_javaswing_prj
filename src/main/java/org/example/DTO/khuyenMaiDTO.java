package org.example.DTO;
import java.time.LocalDate;

/**
 * Lớp DTO cho khuyến mãi
 * Đại diện cho bảng KHUYENMAI trong CSDL
 */
public class khuyenMaiDTO {
    private String maKM;        // varchar(20) - Mã khuyến mãi
    private String tenKM;       // varchar(255) - Tên khuyến mãi
    private String dieuKien;    // text - Điều kiện áp dụng khuyến mãi
    private LocalDate ngayBD;   // date - Ngày bắt đầu
    private LocalDate ngayKT;   // date - Ngày kết thúc
    private double phanTram;    // decimal(5,2) - Phần trăm giảm giá
    private boolean trangThai;  // bit - Trạng thái (1: đang hoạt động, 0: ngừng hoạt động)

    /**
     * Constructor mặc định
     */
    public khuyenMaiDTO() {
        // Khởi tạo mặc định
        this.trangThai = true; // Mặc định là đang hoạt động
    }

    /**
     * Constructor đầy đủ tham số
     * @param maKM Mã khuyến mãi
     * @param tenKM Tên khuyến mãi
     * @param dieuKien Điều kiện áp dụng
     * @param ngayBD Ngày bắt đầu
     * @param ngayKT Ngày kết thúc
     * @param phanTram Phần trăm giảm giá
     * @param trangThai Trạng thái
     */
    public khuyenMaiDTO(String maKM, String tenKM, String dieuKien, LocalDate ngayBD, LocalDate ngayKT, double phanTram, boolean trangThai) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.dieuKien = dieuKien;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.phanTram = phanTram;
        this.trangThai = trangThai;
    }

    /**
     * Constructor không có trạng thái (mặc định là đang hoạt động)
     * @param maKM Mã khuyến mãi
     * @param tenKM Tên khuyến mãi
     * @param dieuKien Điều kiện áp dụng
     * @param ngayBD Ngày bắt đầu
     * @param ngayKT Ngày kết thúc
     * @param phanTram Phần trăm giảm giá
     */
    public khuyenMaiDTO(String maKM, String tenKM, String dieuKien, LocalDate ngayBD, LocalDate ngayKT, double phanTram) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.dieuKien = dieuKien;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.phanTram = phanTram;
        this.trangThai = true; // Mặc định là đang hoạt động
    }

    /**
     * Constructor tương thích với phiên bản cũ
     * @param maKM Mã khuyến mãi
     * @param tenKM Tên khuyến mãi
     * @param dieuKien Điều kiện áp dụng
     * @param ngayBD Ngày bắt đầu
     * @param ngayKT Ngày kết thúc
     */
    public khuyenMaiDTO(String maKM, String tenKM, String dieuKien, LocalDate ngayBD, LocalDate ngayKT) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.dieuKien = dieuKien;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.trangThai = true; // Mặc định là đang hoạt động
    }

    // Getters và Setters
    /**
     * Lấy mã khuyến mãi
     * @return String mã khuyến mãi
     */
    public String getMaKM() {
        return maKM;
    }

    /**
     * Đặt mã khuyến mãi
     * @param maKM String mã khuyến mãi
     */
    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    /**
     * Lấy tên khuyến mãi
     * @return String tên khuyến mãi
     */
    public String getTenKM() {
        return tenKM;
    }

    /**
     * Đặt tên khuyến mãi
     * @param tenKM String tên khuyến mãi
     */
    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    /**
     * Lấy điều kiện áp dụng
     * @return String điều kiện áp dụng
     */
    public String getDieuKien() {
        return dieuKien;
    }

    /**
     * Đặt điều kiện áp dụng
     * @param dieuKien String điều kiện áp dụng
     */
    public void setDieuKien(String dieuKien) {
        this.dieuKien = dieuKien;
    }

    /**
     * Lấy ngày bắt đầu
     * @return LocalDate ngày bắt đầu
     */
    public LocalDate getNgayBD() {
        return ngayBD;
    }

    /**
     * Đặt ngày bắt đầu
     * @param ngayBD LocalDate ngày bắt đầu
     */
    public void setNgayBD(LocalDate ngayBD) {
        this.ngayBD = ngayBD;
    }

    /**
     * Lấy ngày kết thúc
     * @return LocalDate ngày kết thúc
     */
    public LocalDate getNgayKT() {
        return ngayKT;
    }

    /**
     * Đặt ngày kết thúc
     * @param ngayKT LocalDate ngày kết thúc
     */
    public void setNgayKT(LocalDate ngayKT) {
        this.ngayKT = ngayKT;
    }

    /**
     * Lấy phần trăm giảm giá
     * @return double phần trăm giảm giá
     */
    public double getPhanTram() {
        return phanTram;
    }

    /**
     * Đặt phần trăm giảm giá
     * @param phanTram double phần trăm giảm giá
     */
    public void setPhanTram(double phanTram) {
        this.phanTram = phanTram;
    }

    /**
     * Lấy trạng thái
     * @return boolean trạng thái (true: đang hoạt động, false: ngừng hoạt động)
     */
    public boolean isTrangThai() {
        return trangThai;
    }

    /**
     * Đặt trạng thái
     * @param trangThai boolean trạng thái (true: đang hoạt động, false: ngừng hoạt động)
     */
    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    /**
     * Kiểm tra khuyến mãi có đang hoạt động không
     * @return boolean true nếu đang hoạt động và trong thời gian hiệu lực
     */
    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return trangThai &&
               (today.isEqual(ngayBD) || today.isAfter(ngayBD)) &&
               (today.isEqual(ngayKT) || today.isBefore(ngayKT));
    }

    /**
     * Kiểm tra khuyến mãi có hết hạn chưa
     * @return boolean true nếu đã hết hạn
     */
    public boolean isExpired() {
        return LocalDate.now().isAfter(ngayKT);
    }

    @Override
    public String toString() {
        return "khuyenMaiDTO{" +
                "maKM='" + maKM + '\'' +
                ", tenKM='" + tenKM + '\'' +
                ", ngayBD=" + ngayBD +
                ", ngayKT=" + ngayKT +
                ", phanTram=" + phanTram +
                ", trangThai=" + (trangThai ? "Đang hoạt động" : "Ngừng hoạt động") +
                '}';
    }
}
