package org.example.BUS;

import org.example.DAO.LoaiSanPhamDAO;
import org.example.DTO.LoaiSanPhamDTO;

import java.util.ArrayList;

public class LoaiSanPhamBUS {

    public static ArrayList<LoaiSanPhamDTO> selectAll() {
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
        ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = loaiSanPhamDAO.selectAll();
        return dsLoaiSanPham;
    }

    public static ArrayList<LoaiSanPhamDTO> layDanhSachLoaiSanPham() {
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
        ArrayList<LoaiSanPhamDTO> dsLoaiSanPham = loaiSanPhamDAO.layDanhSachLoaiSanPham();
        return dsLoaiSanPham;
    }

    public static int themLoaiSanPham(LoaiSanPhamDTO loaiSanPhamDTO) {
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
        return loaiSanPhamDAO.insert(loaiSanPhamDTO);
    }

    public static int xoaLoaiSanPham(LoaiSanPhamDTO loaiSanPhamDTO) {
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
        return loaiSanPhamDAO.delete(loaiSanPhamDTO);
    }

    public static int capNhatLoaiSanPham(LoaiSanPhamDTO loaiSanPhamDTO) {
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
        return loaiSanPhamDAO.update(loaiSanPhamDTO);
    }

    // tìm kiếm loại sản phẩm theo tên
    public static ArrayList<LoaiSanPhamDTO> timKiemLoaiSanPham(String tenLSP) {
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
        return loaiSanPhamDAO.timKiemLoaiSanPham(tenLSP);
    }

    public static boolean xuatExcel() {
        try {
            // Hiển thị hộp thoại chọn nơi lưu file
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");

            // Thiết lập filter cho file Excel
            javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter(
                    "Excel Files (*.xlsx)", "xlsx");
            fileChooser.setFileFilter(filter);

            // Đặt tên file mặc định
            fileChooser.setSelectedFile(new java.io.File("DanhSachLoaiSanPham.xlsx"));

            // Hiển thị hộp thoại
            int userSelection = fileChooser.showSaveDialog(null);

            // Nếu người dùng không chọn file (nhấn Cancel), trả về false
            if (userSelection != javax.swing.JFileChooser.APPROVE_OPTION) {
                return false;
            }

            // Lấy đường dẫn file đã chọn
            java.io.File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Đảm bảo file có đuôi .xlsx
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
                fileToSave = new java.io.File(filePath);
            }

            ArrayList<LoaiSanPhamDTO> danhSach = selectAll();

            // Tạo workbook mới
            org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();

            // Tạo sheet mới
            org.apache.poi.xssf.usermodel.XSSFSheet sheet = workbook.createSheet("Danh sách loại sản phẩm");

            // Tạo font cho tiêu đề
            org.apache.poi.xssf.usermodel.XSSFFont headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);

            // Tạo style cho tiêu đề
            org.apache.poi.xssf.usermodel.XSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);

            // Tạo hàng tiêu đề
            org.apache.poi.xssf.usermodel.XSSFRow headerRow = sheet.createRow(0);

            // Tạo các ô tiêu đề
            org.apache.poi.xssf.usermodel.XSSFCell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("Mã loại sản phẩm");
            headerCell1.setCellStyle(headerStyle);

            org.apache.poi.xssf.usermodel.XSSFCell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("Tên loại sản phẩm");
            headerCell2.setCellStyle(headerStyle);


            // Thêm dữ liệu vào sheet
            int rowNum = 1;
            for (LoaiSanPhamDTO loai : danhSach) {
                org.apache.poi.xssf.usermodel.XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(loai.getMaLSP());
                row.createCell(1).setCellValue(loai.getTenLSP());
            }

            // Tự động điều chỉnh độ rộng cột
            for (int i = 0; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }

            // Ghi workbook ra file
            java.io.FileOutputStream fileOut = new java.io.FileOutputStream(fileToSave);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            // Mở file Excel sau khi xuất thành công
            if (fileToSave.exists()) {
                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                if (desktop.isSupported(java.awt.Desktop.Action.OPEN)) {
                    desktop.open(fileToSave);
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static String generateNextMaLSP() {
        ArrayList<LoaiSanPhamDTO> danhSach = selectAll();
        int maxId = 0;

        // Find the highest existing ID number
        for (LoaiSanPhamDTO loai : danhSach) {
            String maLSP = loai.getMaLSP();
            if (maLSP.startsWith("LSP")) {
                try {
                    int idNumber = Integer.parseInt(maLSP.substring(3));
                    if (idNumber > maxId) {
                        maxId = idNumber;
                    }
                } catch (NumberFormatException e) {
                    // Skip if not a valid number format
                }
            }
        }

        // Generate next ID
        int nextId = maxId + 1;
        return String.format("LSP%03d", nextId);
    }

}
