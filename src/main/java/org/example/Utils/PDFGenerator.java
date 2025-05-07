package org.example.Utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.example.BUS.KhachHangBUS;
import org.example.BUS.NhanVienBUS;
import org.example.DTO.KhachHangDTO;
import org.example.DTO.chiTietHoaDonDTO;
import org.example.DTO.hoaDonDTO;
import org.example.DTO.nhanVienDTO;
import org.example.GUI.BanHangPanel.CartItem;

import javax.swing.*;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Lớp tiện ích để tạo file PDF hóa đơn
 */
public class PDFGenerator {

    /**
     * Tạo file PDF hóa đơn từ thông tin hóa đơn
     * @param hoaDon Đối tượng hóa đơn
     * @param dsChiTiet Danh sách chi tiết hóa đơn
     * @param filePath Đường dẫn lưu file PDF
     * @return true nếu tạo thành công, false nếu thất bại
     */
    public static boolean taoHoaDonPDF(hoaDonDTO hoaDon, List<chiTietHoaDonDTO> dsChiTiet, String filePath) {
        try {
            // Tạo document mới
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Thêm font chữ tiếng Việt
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 18, Font.BOLD);
            Font headerFont = new Font(baseFont, 12, Font.BOLD);
            Font normalFont = new Font(baseFont, 12, Font.NORMAL);
            Font smallFont = new Font(baseFont, 10, Font.NORMAL);
            Font smallBoldFont = new Font(baseFont, 10, Font.BOLD);

            // Thêm tiêu đề
            Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Thêm thông tin cửa hàng
            Paragraph storeInfo = new Paragraph("MINIMART SGU", headerFont);
            storeInfo.setAlignment(Element.ALIGN_CENTER);
            document.add(storeInfo);

            Paragraph storeAddress = new Paragraph("Địa chỉ: 273 An Dương Vương, Phường 3, Quận 5, TP.HCM", normalFont);
            storeAddress.setAlignment(Element.ALIGN_CENTER);
            document.add(storeAddress);

            Paragraph storePhone = new Paragraph("Điện thoại: 0123 456 789", normalFont);
            storePhone.setAlignment(Element.ALIGN_CENTER);
            document.add(storePhone);
            document.add(Chunk.NEWLINE);

            // Thêm thông tin hóa đơn
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);

            // Mã hóa đơn
            PdfPCell cell1 = new PdfPCell(new Phrase("Mã hóa đơn: " + hoaDon.getMaHoaDon(), headerFont));
            cell1.setBorder(Rectangle.NO_BORDER);
            infoTable.addCell(cell1);

            // Ngày lập
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            PdfPCell cell2 = new PdfPCell(new Phrase("Ngày lập: " + hoaDon.getThoiGianLap().format(formatter), headerFont));
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            infoTable.addCell(cell2);

            // Thông tin khách hàng
            KhachHangDTO khachHang = new KhachHangBUS().layKhachHangTheoMa(hoaDon.getMaKH());
            String tenKhachHang = khachHang != null ? khachHang.getHoTen() : "Khách hàng vãng lai";

            PdfPCell cell3 = new PdfPCell(new Phrase("Khách hàng: " + tenKhachHang, normalFont));
            cell3.setBorder(Rectangle.NO_BORDER);
            infoTable.addCell(cell3);

            // Thông tin nhân viên
            nhanVienDTO nhanVien = new NhanVienBUS().layNhanVienTheoMa(hoaDon.getMaNV());
            String tenNhanVien = nhanVien != null ? nhanVien.getHoTen() : "Admin";

            PdfPCell cell4 = new PdfPCell(new Phrase("Nhân viên: " + tenNhanVien, normalFont));
            cell4.setBorder(Rectangle.NO_BORDER);
            cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
            infoTable.addCell(cell4);

            document.add(infoTable);
            document.add(Chunk.NEWLINE);

            // Thêm bảng chi tiết hóa đơn
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 3, 1, 2, 2});

            // Thêm header cho bảng
            PdfPCell headerCell1 = new PdfPCell(new Phrase("STT", headerFont));
            headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell1.setPadding(5);
            table.addCell(headerCell1);

            PdfPCell headerCell2 = new PdfPCell(new Phrase("Tên sản phẩm", headerFont));
            headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell2.setPadding(5);
            table.addCell(headerCell2);

            PdfPCell headerCell3 = new PdfPCell(new Phrase("SL", headerFont));
            headerCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell3.setPadding(5);
            table.addCell(headerCell3);

            PdfPCell headerCell4 = new PdfPCell(new Phrase("Đơn giá", headerFont));
            headerCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell4.setPadding(5);
            table.addCell(headerCell4);

            PdfPCell headerCell5 = new PdfPCell(new Phrase("Thành tiền", headerFont));
            headerCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell5.setPadding(5);
            table.addCell(headerCell5);

            // Định dạng số tiền
            DecimalFormat formatter2 = new DecimalFormat("#,###");

            // Thêm dữ liệu cho bảng
            int stt = 1;
            double tongTien = 0;

            for (chiTietHoaDonDTO chiTiet : dsChiTiet) {
                // STT
                PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(stt++), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                table.addCell(cell);

                // Tên sản phẩm
                cell = new PdfPCell(new Phrase(chiTiet.getTenSP(), normalFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                table.addCell(cell);

                // Số lượng
                cell = new PdfPCell(new Phrase(String.valueOf(chiTiet.getSoLuong()), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                table.addCell(cell);

                // Đơn giá
                cell = new PdfPCell(new Phrase(formatter2.format(chiTiet.getGiaBan()) + " đ", normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                table.addCell(cell);

                // Thành tiền
                double thanhTien = chiTiet.getSoLuong() * chiTiet.getGiaBan();
                tongTien += thanhTien;

                cell = new PdfPCell(new Phrase(formatter2.format(thanhTien) + " đ", normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                table.addCell(cell);
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            // Thêm thông tin tổng tiền
            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(60);
            summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            // Tổng tiền hàng
            PdfPCell summaryCell1 = new PdfPCell(new Phrase("Tổng tiền hàng:", headerFont));
            summaryCell1.setBorder(Rectangle.NO_BORDER);
            summaryCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            summaryCell1.setPadding(5);
            summaryTable.addCell(summaryCell1);

            PdfPCell summaryCell2 = new PdfPCell(new Phrase(formatter2.format(tongTien) + " đ", headerFont));
            summaryCell2.setBorder(Rectangle.NO_BORDER);
            summaryCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summaryCell2.setPadding(5);
            summaryTable.addCell(summaryCell2);

            // Giảm giá
            double giamGia = tongTien - hoaDon.getTongTien();

            PdfPCell summaryCell3 = new PdfPCell(new Phrase("Giảm giá:", headerFont));
            summaryCell3.setBorder(Rectangle.NO_BORDER);
            summaryCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
            summaryCell3.setPadding(5);
            summaryTable.addCell(summaryCell3);

            PdfPCell summaryCell4 = new PdfPCell(new Phrase(formatter2.format(giamGia) + " đ", headerFont));
            summaryCell4.setBorder(Rectangle.NO_BORDER);
            summaryCell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summaryCell4.setPadding(5);
            summaryTable.addCell(summaryCell4);

            // Tổng thanh toán
            PdfPCell summaryCell5 = new PdfPCell(new Phrase("Tổng thanh toán:", headerFont));
            summaryCell5.setBorder(Rectangle.NO_BORDER);
            summaryCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
            summaryCell5.setPadding(5);
            summaryTable.addCell(summaryCell5);

            PdfPCell summaryCell6 = new PdfPCell(new Phrase(formatter2.format(hoaDon.getTongTien()) + " đ", headerFont));
            summaryCell6.setBorder(Rectangle.NO_BORDER);
            summaryCell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summaryCell6.setPadding(5);
            summaryTable.addCell(summaryCell6);

            document.add(summaryTable);
            document.add(Chunk.NEWLINE);

            // Thêm lời cảm ơn
            Paragraph thankYou = new Paragraph("Cảm ơn quý khách đã mua hàng tại Minimart SGU!", normalFont);
            thankYou.setAlignment(Element.ALIGN_CENTER);
            document.add(thankYou);

            Paragraph comeBack = new Paragraph("Hẹn gặp lại quý khách!", normalFont);
            comeBack.setAlignment(Element.ALIGN_CENTER);
            document.add(comeBack);

            // Đóng document
            document.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo file PDF: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Tạo file PDF hóa đơn từ thông tin giỏ hàng
     * @param hoaDon Đối tượng hóa đơn
     * @param cartItems Danh sách sản phẩm trong giỏ hàng
     * @param filePath Đường dẫn lưu file PDF
     * @return true nếu tạo thành công, false nếu thất bại
     */
    public static boolean taoHoaDonPDFTuGioHang(hoaDonDTO hoaDon, List<CartItem> cartItems, String filePath) {
        try {
            // Tạo document mới
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Thêm font chữ tiếng Việt
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 18, Font.BOLD);
            Font headerFont = new Font(baseFont, 12, Font.BOLD);
            Font normalFont = new Font(baseFont, 12, Font.NORMAL);
            Font smallFont = new Font(baseFont, 10, Font.NORMAL);
            Font smallBoldFont = new Font(baseFont, 10, Font.BOLD);

            // Thêm tiêu đề
            Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Thêm thông tin cửa hàng
            Paragraph storeInfo = new Paragraph("MINIMART SGU", headerFont);
            storeInfo.setAlignment(Element.ALIGN_CENTER);
            document.add(storeInfo);

            Paragraph storeAddress = new Paragraph("Địa chỉ: 273 An Dương Vương, Phường 3, Quận 5, TP.HCM", normalFont);
            storeAddress.setAlignment(Element.ALIGN_CENTER);
            document.add(storeAddress);

            Paragraph storePhone = new Paragraph("Điện thoại: 0123 456 789", normalFont);
            storePhone.setAlignment(Element.ALIGN_CENTER);
            document.add(storePhone);
            document.add(Chunk.NEWLINE);

            // Thêm thông tin hóa đơn
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);

            // Mã hóa đơn
            PdfPCell cell1 = new PdfPCell(new Phrase("Mã hóa đơn: " + hoaDon.getMaHoaDon(), headerFont));
            cell1.setBorder(Rectangle.NO_BORDER);
            infoTable.addCell(cell1);

            // Ngày lập
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            PdfPCell cell2 = new PdfPCell(new Phrase("Ngày lập: " + hoaDon.getThoiGianLap().format(formatter), headerFont));
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            infoTable.addCell(cell2);

            // Thông tin khách hàng
            KhachHangDTO khachHang = new KhachHangBUS().layKhachHangTheoMa(hoaDon.getMaKH());
            String tenKhachHang = khachHang != null ? khachHang.getHoTen() : "Khách hàng vãng lai";

            PdfPCell cell3 = new PdfPCell(new Phrase("Khách hàng: " + tenKhachHang, normalFont));
            cell3.setBorder(Rectangle.NO_BORDER);
            infoTable.addCell(cell3);

            // Thông tin nhân viên
            nhanVienDTO nhanVien = new NhanVienBUS().layNhanVienTheoMa(hoaDon.getMaNV());
            String tenNhanVien = nhanVien != null ? nhanVien.getHoTen() : "Admin";

            PdfPCell cell4 = new PdfPCell(new Phrase("Nhân viên: " + tenNhanVien, normalFont));
            cell4.setBorder(Rectangle.NO_BORDER);
            cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
            infoTable.addCell(cell4);

            document.add(infoTable);
            document.add(Chunk.NEWLINE);

            // Thêm bảng chi tiết hóa đơn
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 3, 1, 2, 2});

            // Thêm header cho bảng
            PdfPCell headerCell1 = new PdfPCell(new Phrase("STT", headerFont));
            headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell1.setPadding(5);
            table.addCell(headerCell1);

            PdfPCell headerCell2 = new PdfPCell(new Phrase("Tên sản phẩm", headerFont));
            headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell2.setPadding(5);
            table.addCell(headerCell2);

            PdfPCell headerCell3 = new PdfPCell(new Phrase("SL", headerFont));
            headerCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell3.setPadding(5);
            table.addCell(headerCell3);

            PdfPCell headerCell4 = new PdfPCell(new Phrase("Đơn giá", headerFont));
            headerCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell4.setPadding(5);
            table.addCell(headerCell4);

            PdfPCell headerCell5 = new PdfPCell(new Phrase("Thành tiền", headerFont));
            headerCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell5.setPadding(5);
            table.addCell(headerCell5);

            // Định dạng số tiền
            DecimalFormat formatter2 = new DecimalFormat("#,###");

            // Thêm dữ liệu cho bảng
            int stt = 1;
            double tongTien = 0;

            for (CartItem item : cartItems) {
                // STT
                PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(stt++), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                table.addCell(cell);

                // Tên sản phẩm
                cell = new PdfPCell(new Phrase(item.getSanPham().getTenSP(), normalFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                table.addCell(cell);

                // Số lượng
                cell = new PdfPCell(new Phrase(String.valueOf(item.getQuantity()), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                table.addCell(cell);

                // Đơn giá
                cell = new PdfPCell(new Phrase(formatter2.format(item.getSanPham().getGiaBan()) + " đ", normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                table.addCell(cell);

                // Thành tiền
                double thanhTien = item.getQuantity() * item.getSanPham().getGiaBan();
                tongTien += thanhTien;

                cell = new PdfPCell(new Phrase(formatter2.format(thanhTien) + " đ", normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                table.addCell(cell);
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            // Thêm thông tin tổng tiền
            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(60);
            summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            // Tổng tiền hàng
            PdfPCell summaryCell1 = new PdfPCell(new Phrase("Tổng tiền hàng:", headerFont));
            summaryCell1.setBorder(Rectangle.NO_BORDER);
            summaryCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            summaryCell1.setPadding(5);
            summaryTable.addCell(summaryCell1);

            PdfPCell summaryCell2 = new PdfPCell(new Phrase(formatter2.format(tongTien) + " đ", headerFont));
            summaryCell2.setBorder(Rectangle.NO_BORDER);
            summaryCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summaryCell2.setPadding(5);
            summaryTable.addCell(summaryCell2);

            // Giảm giá
            double giamGia = tongTien - hoaDon.getTongTien();

            PdfPCell summaryCell3 = new PdfPCell(new Phrase("Giảm giá:", headerFont));
            summaryCell3.setBorder(Rectangle.NO_BORDER);
            summaryCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
            summaryCell3.setPadding(5);
            summaryTable.addCell(summaryCell3);

            PdfPCell summaryCell4 = new PdfPCell(new Phrase(formatter2.format(giamGia) + " đ", headerFont));
            summaryCell4.setBorder(Rectangle.NO_BORDER);
            summaryCell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summaryCell4.setPadding(5);
            summaryTable.addCell(summaryCell4);

            // Tổng thanh toán
            PdfPCell summaryCell5 = new PdfPCell(new Phrase("Tổng thanh toán:", headerFont));
            summaryCell5.setBorder(Rectangle.NO_BORDER);
            summaryCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
            summaryCell5.setPadding(5);
            summaryTable.addCell(summaryCell5);

            PdfPCell summaryCell6 = new PdfPCell(new Phrase(formatter2.format(hoaDon.getTongTien()) + " đ", headerFont));
            summaryCell6.setBorder(Rectangle.NO_BORDER);
            summaryCell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summaryCell6.setPadding(5);
            summaryTable.addCell(summaryCell6);

            document.add(summaryTable);
            document.add(Chunk.NEWLINE);

            // Thêm lời cảm ơn
            Paragraph thankYou = new Paragraph("Cảm ơn quý khách đã mua hàng tại Minimart SGU!", normalFont);
            thankYou.setAlignment(Element.ALIGN_CENTER);
            document.add(thankYou);

            Paragraph comeBack = new Paragraph("Hẹn gặp lại quý khách!", normalFont);
            comeBack.setAlignment(Element.ALIGN_CENTER);
            document.add(comeBack);

            // Đóng document
            document.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo file PDF: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
