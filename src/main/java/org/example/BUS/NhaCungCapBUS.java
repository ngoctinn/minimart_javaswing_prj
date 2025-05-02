package org.example.BUS;

import org.example.DAO.NhaCungCapDAO;
import org.example.DTO.nhaCungCapDTO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NhaCungCapBUS {
    public static ArrayList<nhaCungCapDTO> layDanhSachNhaCungCap() {
        return NhaCungCapDAO.layDanhSachNhaCungCap();
    }
    
    public static int themNhaCungCap(nhaCungCapDTO ncc) {
        return NhaCungCapDAO.themNhaCungCap(ncc);
    }
    
    public static int capNhatNhaCungCap(nhaCungCapDTO ncc) {
        return NhaCungCapDAO.capNhatNhaCungCap(ncc);
    }
    
    public static int xoaNhaCungCap(String maNCC) {
        return NhaCungCapDAO.xoaNhaCungCap(maNCC);
    }
    
    public static ArrayList<nhaCungCapDTO> timKiemNhaCungCap(String tuKhoa) {
        return NhaCungCapDAO.timKiemNhaCungCap(tuKhoa);
    }
    
    public static String generateNextMaNCC() {
        return NhaCungCapDAO.generateNextMaNCC();
    }
    
    public static boolean xuatExcel() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
            
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Danh sách nhà cung cấp");
                
                // Tạo header
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Mã NCC");
                headerRow.createCell(1).setCellValue("Tên NCC");
                headerRow.createCell(2).setCellValue("Địa chỉ");
                headerRow.createCell(3).setCellValue("Số điện thoại");
                
                // Lấy dữ liệu từ database
                ArrayList<nhaCungCapDTO> danhSachNCC = layDanhSachNhaCungCap();
                
                // Điền dữ liệu vào file Excel
                for (int i = 0; i < danhSachNCC.size(); i++) {
                    nhaCungCapDTO ncc = danhSachNCC.get(i);
                    Row row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue(ncc.getMaNCC());
                    row.createCell(1).setCellValue(ncc.getTenNCC());
                    row.createCell(2).setCellValue(ncc.getDiaChi());
                    row.createCell(3).setCellValue(ncc.getSDT());
                }
                
                // Tự động điều chỉnh độ rộng cột
                for (int i = 0; i < 4; i++) {
                    sheet.autoSizeColumn(i);
                }
                
                // Ghi file
                FileOutputStream outputStream = new FileOutputStream(filePath);
                workbook.write(outputStream);
                workbook.close();
                outputStream.close();
                
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Xuất file Excel thất bại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
