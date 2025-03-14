package org.example.DTO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class hoaDonDTO {
    private String maHoaDon;
    private double tongTien;
    private LocalDate ngayLap;
    private LocalTime gioLap;
    private String maKH;
    private String maKM;
    private List<chiTietHoaDonDTO> chiTietHoaDon;
    private List<khuyenMaiDTO> dsKM;

    public hoaDonDTO() {

    }
    public hoaDonDTO(String maHoaDon, double tongTien, LocalDate ngayLap, LocalTime gioLap, String maKH, String maKM, List<chiTietHoaDonDTO> chiTietHoaDon, List<khuyenMaiDTO> dsKM) {
        this.maHoaDon = maHoaDon;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
        this.gioLap = gioLap;
        this.maKH = maKH;
        this.maKM = maKM;
        this.chiTietHoaDon = chiTietHoaDon;
        this.dsKM = dsKM;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }
    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public double getTongTien() {
        // Tính tổng tiền
        if(chiTietHoaDon == null || chiTietHoaDon.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (chiTietHoaDonDTO cthd : chiTietHoaDon) {
            // Tính tổng tiền của từng sản phẩm
            sum += cthd.getSoLuong() * cthd.getGiaBan();
        }
        // Tính phần trăm giảm giá
        float phanTram = 0;
        if(maKM != null && dsKM != null){
            for(khuyenMaiDTO km : dsKM){
                // Tìm chuỗi
                if(km.getMaKM().equals(maKM)){
                // Gán phần trăm với mã khuyến mãi tương ứng
                phanTram = (float) ( km.getPhanTram() / 100.0);
                break;
                }
            }
        }
        // Tổng tiền khi đã áp dụng khuyến mãi. Nếu không có khuyến mãi thì phần trăm = 0 <=> sum * (1-0)
        return sum * (1 - phanTram);
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }
    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public LocalTime getGioLap() {
        return gioLap;
    }
    public void setGioLap(LocalTime gioLap) {
        this.gioLap = gioLap;
    }

    public String getMaKH() {
        return maKH;
    }
    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaKM() {
        return maKM;
    }
    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public List<chiTietHoaDonDTO> getChiTietHoaDon() {
        return chiTietHoaDon;
    }

    public List<khuyenMaiDTO> getDsKM() {
        return dsKM;
    }
}
