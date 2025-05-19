package org.example.DTO;
import java.time.LocalDate;

public class chamCongDTO {
    private String maNV;
    private LocalDate thoiGianChamCong;

    public chamCongDTO() {
    }

    public chamCongDTO(String maNV, LocalDate thoiGianChamCong) {
        this.maNV = maNV;
        this.thoiGianChamCong = thoiGianChamCong;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    
    public String getMaNV() {
        return maNV;
    }

    public void setThoiGianChamCong(LocalDate thoiGianChamCong) {
        this.thoiGianChamCong = thoiGianChamCong;
    }
    
    public LocalDate getThoiGianChamCong() {
        return thoiGianChamCong;
    }

    @Override
    public String toString() {
        return "chamCongDTO{" +
                "maNV='" + maNV + '\'' +
                ", thoiGianChamCong=" + thoiGianChamCong +
                '}';
    }
}
