package org.example.BUS;

import org.example.DAO.NhanVienDAO;
import org.example.DAO.PhanQuyenDAO;
import org.example.DAO.ChucVuDAO;
import org.example.DTO.ChucNangDTO;
import org.example.DTO.nhanVienDTO;
import org.example.DTO.PhanQuyenDTO;
import org.example.DTO.ChucVuDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Lớp xử lý logic đăng nhập và phân quyền
 */
public class LoginBUS {
    private static NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private static PhanQuyenDAO phanQuyenDAO = new PhanQuyenDAO();
    private static ChucVuDAO chucVuDAO = new ChucVuDAO();

    // Lưu thông tin người dùng đã đăng nhập
    private static nhanVienDTO currentUser = null;

    // Lưu danh sách quyền của người dùng hiện tại
    private static Map<String, Integer> userPermissions = new HashMap<>();

    /**
     * Đăng nhập vào hệ thống
     * @param username Tên đăng nhập (email hoặc số điện thoại)
     * @param password Mật khẩu
     * @return nhanVienDTO đối tượng nhân viên nếu đăng nhập thành công, null nếu thất bại
     */
    public static nhanVienDTO login(String username, String password) {
        // Kiểm tra đăng nhập bằng email
        ArrayList<nhanVienDTO> dsNhanVien = nhanVienDAO.selectByCondition(
                "email = '" + username + "' AND matKhau = '" + password + "' AND trangThai = 1");

        if (dsNhanVien.isEmpty()) {
            // Nếu không tìm thấy bằng email, kiểm tra bằng số điện thoại
            dsNhanVien = nhanVienDAO.selectByCondition(
                    "SDT = '" + username + "' AND matKhau = '" + password + "' AND trangThai = 1");
        }

        if (!dsNhanVien.isEmpty()) {
            // Đăng nhập thành công
            currentUser = dsNhanVien.get(0);

            // Lấy danh sách quyền của người dùng
            loadUserPermissions();

            return currentUser;
        }

        return null; // Đăng nhập thất bại
    }

    /**
     * Đăng xuất khỏi hệ thống
     */
    public static void logout() {
        currentUser = null;
        userPermissions.clear();
    }

    /**
     * Lấy thông tin người dùng hiện tại
     * @return nhanVienDTO đối tượng nhân viên đang đăng nhập, null nếu chưa đăng nhập
     */
    public static nhanVienDTO getCurrentUser() {
        return currentUser;
    }

    /**
     * Kiểm tra người dùng đã đăng nhập chưa
     * @return boolean true nếu đã đăng nhập, false nếu chưa
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Lấy danh sách quyền của người dùng hiện tại
     */
    private static void loadUserPermissions() {
        userPermissions.clear();

        if (currentUser != null) {
            ArrayList<PhanQuyenDTO> dsQuyen = phanQuyenDAO.layDanhSachQuyenTheoChucVu(currentUser.getMaCV());

            for (PhanQuyenDTO quyen : dsQuyen) {
                userPermissions.put(quyen.getMaChucNang(), quyen.getQuyen());
            }
        }
    }

    /**
     * Kiểm tra người dùng có quyền thực hiện chức năng hay không
     * @param maChucNang Mã chức năng cần kiểm tra
     * @return boolean true nếu có quyền, false nếu không
     */
    public static boolean hasPermission(String maChucNang) {
        if (!isLoggedIn()) {
            return false;
        }

        // Nếu quyền không tồn tại trong danh sách, mặc định là không có quyền
        return userPermissions.getOrDefault(maChucNang, 0) > 0;
    }

    /**
     * Lấy mức độ quyền của người dùng đối với một chức năng
     * @param maChucNang Mã chức năng cần kiểm tra
     * @return int Mức độ quyền (0: không có quyền, 1: chỉ xem, 2: xem và chỉnh sửa)
     */
    public static int getPermissionLevel(String maChucNang) {
        if (!isLoggedIn()) {
            return 0;
        }

        return userPermissions.getOrDefault(maChucNang, 0);
    }

    /**
     * Lấy tên chức vụ của người dùng hiện tại
     * @return String tên chức vụ, rỗng nếu chưa đăng nhập
     */
    public static String getCurrentUserRole() {
        if (!isLoggedIn()) {
            return "";
        }

        ArrayList<ChucVuDTO> dsChucVu = chucVuDAO.layDanhSachChucVu();
        for (ChucVuDTO chucVu : dsChucVu) {
            if (chucVu.getMaCV().equals(currentUser.getMaCV())) {
                return chucVu.getTenCV();
            }
        }

        return "";
    }

    /**
     * Đổi mật khẩu cho người dùng hiện tại
     * @param oldPassword Mật khẩu cũ
     * @param newPassword Mật khẩu mới
     * @return boolean true nếu đổi mật khẩu thành công, false nếu thất bại
     */
    public static boolean changePassword(String oldPassword, String newPassword) {
        if (!isLoggedIn()) {
            return false;
        }

        // Kiểm tra mật khẩu cũ có đúng không
        if (!currentUser.getMatKhau().equals(oldPassword)) {
            return false;
        }

        // Cập nhật mật khẩu mới
        currentUser.setMatKhau(newPassword);
        int result = nhanVienDAO.update(currentUser);

        return result > 0;
    }

    /**
     * Kiểm tra tài khoản có tồn tại không
     * @param username Tên đăng nhập (email hoặc số điện thoại)
     * @return boolean true nếu tài khoản tồn tại, false nếu không
     */
    public static boolean checkAccountExists(String username) {
        // Kiểm tra tồn tại bằng email
        ArrayList<nhanVienDTO> dsNhanVien = nhanVienDAO.selectByCondition(
                "email = '" + username + "' AND trangThai = 1");

        if (!dsNhanVien.isEmpty()) {
            return true;
        }

        // Kiểm tra tồn tại bằng số điện thoại
        dsNhanVien = nhanVienDAO.selectByCondition(
                "SDT = '" + username + "' AND trangThai = 1");

        return !dsNhanVien.isEmpty();
    }

    /**
     * Đặt lại mật khẩu cho tài khoản
     * @param username Tên đăng nhập (email hoặc số điện thoại)
     * @param newPassword Mật khẩu mới
     * @return boolean true nếu đặt lại mật khẩu thành công, false nếu thất bại
     */
    public static boolean resetPassword(String username, String newPassword) {
        // Tìm kiếm tài khoản bằng email
        ArrayList<nhanVienDTO> dsNhanVien = nhanVienDAO.selectByCondition(
                "email = '" + username + "' AND trangThai = 1");

        if (dsNhanVien.isEmpty()) {
            // Nếu không tìm thấy bằng email, tìm bằng số điện thoại
            dsNhanVien = nhanVienDAO.selectByCondition(
                    "SDT = '" + username + "' AND trangThai = 1");
        }

        if (!dsNhanVien.isEmpty()) {
            // Tìm thấy tài khoản, cập nhật mật khẩu
            nhanVienDTO nhanVien = dsNhanVien.get(0);
            nhanVien.setMatKhau(newPassword);
            int result = nhanVienDAO.update(nhanVien);
            return result > 0;
        }

        return false; // Không tìm thấy tài khoản
    }

    // test main
    public static void main(String[] args) {
        LoginBUS loginBUS = new LoginBUS();
        loginBUS.login("0123456789", "123456");
        System.out.println(loginBUS.getCurrentUser().getHoTen());
        System.out.println(loginBUS.getCurrentUser().getMaCV());
        // lấy danh sách quyền của người dùng hiện tại
        ArrayList<ChucNangDTO> dsChucNang = PhanQuyenBUS.layDanhSachChucNang();
        for (ChucNangDTO chucNang : dsChucNang) {
            System.out.println(chucNang.getTenChucNang() + " - " + loginBUS.getPermissionLevel(chucNang.getMaChucNang()));
        }




    }
}
