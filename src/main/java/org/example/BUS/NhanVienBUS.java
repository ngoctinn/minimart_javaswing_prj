package org.example.BUS;

import org.example.DAO.NhanVienDAO;
import org.example.DTO.nhanVienDTO;
import org.example.DTO.ChucVuDTO;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Lớp xử lý logic nghiệp vụ liên quan đến nhân viên
 */
public class NhanVienBUS {
    private static NhanVienDAO nhanVienDAO = new NhanVienDAO();

    /**
     * Lấy danh sách tất cả nhân viên
     * @return ArrayList<nhanVienDTO> danh sách tất cả nhân viên
     */
    public static ArrayList<nhanVienDTO> selectAll() {
        return nhanVienDAO.selectAll();
    }

    /**
     * Lấy danh sách nhân viên đang hoạt động
     * @return ArrayList<nhanVienDTO> danh sách nhân viên đang hoạt động
     */
    public static ArrayList<nhanVienDTO> layDanhSachNhanVien() {
        return nhanVienDAO.selectAllActive();
    }

    /**
     * Thêm nhân viên mới
     * @param nhanVien nhanVienDTO đối tượng nhân viên cần thêm
     * @return int số dòng bị ảnh hưởng
     */
    public static int themNhanVien(nhanVienDTO nhanVien) {
        return nhanVienDAO.insert(nhanVien);
    }

    /**
     * Cập nhật thông tin nhân viên
     * @param nhanVien nhanVienDTO đối tượng nhân viên cần cập nhật
     * @return int số dòng bị ảnh hưởng
     */
    public static int capNhatNhanVien(nhanVienDTO nhanVien) {
        return nhanVienDAO.update(nhanVien);
    }

    /**
     * Xóa nhân viên (cập nhật trạng thái thành false)
     * @param nhanVien nhanVienDTO đối tượng nhân viên cần xóa
     * @return int số dòng bị ảnh hưởng
     */
    public static int xoaNhanVien(nhanVienDTO nhanVien) {
        return nhanVienDAO.delete(nhanVien);
    }

    /**
     * Tìm kiếm nhân viên theo từ khóa (tìm theo mã, tên, email hoặc số điện thoại)
     * @param tuKhoa String từ khóa tìm kiếm
     * @return ArrayList<nhanVienDTO> danh sách nhân viên tìm được
     */
    public static ArrayList<nhanVienDTO> timKiemNhanVien(String tuKhoa) {
        String condition = "trangThai = 1 AND (maNV LIKE N'%" + tuKhoa + "%' OR hoTen LIKE N'%" + tuKhoa + "%' OR email LIKE '%" + tuKhoa + "%' OR SDT LIKE '%" + tuKhoa + "%')";
        return nhanVienDAO.selectByCondition(condition);
    }

    /**
     * Tìm kiếm nhân viên theo tên
     * @param tenNV String tên nhân viên cần tìm
     * @return ArrayList<nhanVienDTO> danh sách nhân viên tìm được
     */
    public static ArrayList<nhanVienDTO> timKiemTheoTen(String tenNV) {
        return nhanVienDAO.timKiemTheoTen(tenNV);
    }

    /**
     * Tìm kiếm nhân viên theo chức vụ
     * @param maCV String mã chức vụ cần tìm
     * @return ArrayList<nhanVienDTO> danh sách nhân viên tìm được
     */
    public static ArrayList<nhanVienDTO> timKiemTheoChucVu(String maCV) {
        return nhanVienDAO.timKiemTheoChucVu(maCV);
    }

    /**
     * Lấy thông tin nhân viên theo mã
     * @param maNV String mã nhân viên cần lấy thông tin
     * @return nhanVienDTO đối tượng nhân viên tìm được, null nếu không tìm thấy
     */
    public static nhanVienDTO layNhanVienTheoMa(String maNV) {
        nhanVienDTO nhanVien = new nhanVienDTO();
        nhanVien.setMaNV(maNV);
        return nhanVienDAO.selectById(nhanVien);
    }

    /**
     * Tạo mã nhân viên mới tự động tăng
     * @return String mã nhân viên mới
     */
    public static String taoMaNhanVienMoi() {
        return nhanVienDAO.taoMaNhanVienMoi();
    }

    /**
     * Kiểm tra email đã tồn tại chưa
     * @param email String email cần kiểm tra
     * @param maNV String mã nhân viên hiện tại (dùng khi cập nhật)
     * @return boolean true nếu email đã tồn tại, false nếu chưa
     */
    public static boolean kiemTraEmailTonTai(String email, String maNV) {
        return nhanVienDAO.kiemTraEmailTonTai(email, maNV);
    }

    /**
     * Kiểm tra số điện thoại đã tồn tại chưa
     * @param sdt String số điện thoại cần kiểm tra
     * @param maNV String mã nhân viên hiện tại (dùng khi cập nhật)
     * @return boolean true nếu số điện thoại đã tồn tại, false nếu chưa
     */
    public static boolean kiemTraSDTTonTai(String sdt, String maNV) {
        return nhanVienDAO.kiemTraSDTTonTai(sdt, maNV);
    }

    /**
     * Kiểm tra dữ liệu nhân viên hợp lệ
     * @param nhanVien nhanVienDTO đối tượng nhân viên cần kiểm tra
     * @param isUpdate boolean true nếu đang cập nhật, false nếu đang thêm mới
     * @return String thông báo lỗi, rỗng nếu hợp lệ
     */
    public static String kiemTraDuLieuHopLe(nhanVienDTO nhanVien, boolean isUpdate) {
        // Kiểm tra họ tên
        if (nhanVien.getHoTen() == null || nhanVien.getHoTen().trim().isEmpty()) {
            return "Họ tên không được để trống";
        }

        // Kiểm tra ngày sinh
        if (nhanVien.getNgaySinh() == null) {
            return "Ngày sinh không được để trống";
        }

        // Kiểm tra tuổi (phải từ 18 tuổi trở lên)
        LocalDate now = LocalDate.now();
        LocalDate minBirthDate = now.minusYears(18);
        if (nhanVien.getNgaySinh().isAfter(minBirthDate)) {
            return "Nhân viên phải đủ 18 tuổi trở lên";
        }

        // Kiểm tra giới tính
        if (nhanVien.getGioiTinh() == null || nhanVien.getGioiTinh().trim().isEmpty()) {
            return "Giới tính không được để trống";
        }

        // Kiểm tra địa chỉ
        if (nhanVien.getDiaChi() == null || nhanVien.getDiaChi().trim().isEmpty()) {
            return "Địa chỉ không được để trống";
        }

        // Kiểm tra email
        if (nhanVien.getEmail() == null || nhanVien.getEmail().trim().isEmpty()) {
            return "Email không được để trống";
        }

        // Kiểm tra định dạng email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!nhanVien.getEmail().matches(emailRegex)) {
            return "Định dạng email không hợp lệ";
        }

        // Kiểm tra email tồn tại
        if (kiemTraEmailTonTai(nhanVien.getEmail(), isUpdate ? nhanVien.getMaNV() : null)) {
            return "Email đã được sử dụng";
        }

        // Kiểm tra mật khẩu
        if (!isUpdate && (nhanVien.getMatKhau() == null || nhanVien.getMatKhau().trim().isEmpty())) {
            return "Mật khẩu không được để trống";
        }

        // Kiểm tra độ dài mật khẩu (nếu có mật khẩu)
        if (nhanVien.getMatKhau() != null && !nhanVien.getMatKhau().trim().isEmpty() && nhanVien.getMatKhau().length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự";
        }

        // Kiểm tra số điện thoại
        if (nhanVien.getSDT() == null || nhanVien.getSDT().trim().isEmpty()) {
            return "Số điện thoại không được để trống";
        }

        // Kiểm tra định dạng số điện thoại
        String phoneRegex = "^0\\d{9}$";
        if (!nhanVien.getSDT().matches(phoneRegex)) {
            return "Định dạng số điện thoại không hợp lệ (phải có 10 số và bắt đầu bằng số 0)";
        }

        // Kiểm tra số điện thoại tồn tại
        if (kiemTraSDTTonTai(nhanVien.getSDT(), isUpdate ? nhanVien.getMaNV() : null)) {
            return "Số điện thoại đã được sử dụng";
        }

        // Kiểm tra mã chức vụ
        if (nhanVien.getMaCV() == null || nhanVien.getMaCV().trim().isEmpty()) {
            return "Chức vụ không được để trống";
        }

        // Kiểm tra chức vụ tồn tại
        boolean chucVuTonTai = false;
        for (ChucVuDTO cv : ChucVuBUS.layDanhSachChucVu()) {
            if (cv.getMaCV().equals(nhanVien.getMaCV())) {
                chucVuTonTai = true;
                break;
            }
        }
        if (!chucVuTonTai) {
            return "Chức vụ không tồn tại";
        }

        // Nếu tất cả đều hợp lệ
        return "";
    }

    /**
     * Phương thức main để test các chức năng của BUS
     */
    public static void main(String[] args) {
        // Test lấy danh sách nhân viên
        System.out.println("=== Danh sách nhân viên ===");
        ArrayList<nhanVienDTO> dsNhanVien = layDanhSachNhanVien();
        for (nhanVienDTO nv : dsNhanVien) {
            System.out.println(nv.getMaNV() + " - " + nv.getHoTen() + " - " + nv.getEmail());
        }

        // Test tạo mã nhân viên mới
        System.out.println("\n=== Mã nhân viên mới ===");
        System.out.println("Mã nhân viên mới: " + taoMaNhanVienMoi());
    }
}