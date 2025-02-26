package org.example.GUI;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.example.Components.GoodsPanel;
import org.example.GUI.Panels.hangHoaPanel;
// import các panel khác

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class mainGUI extends JFrame implements ActionListener {
    Map<String, JPanel> panelMap;

    // Các JMenuItem cho JMenu giaoDich
    JMenuItem datHangItem;
    JMenuItem hoaDonItem;
    JMenuItem traHangItem;
    JMenuItem nhapHangItem;
    JMenuItem traHangNhapItem;

    // Các JMenuItem cho JMenu hangHoa
    JMenuItem danhMucItem;
    JMenuItem thietLapGiaItem;
    JMenuItem kiemKhoItem;

    // Các JMenuItem cho JMenu doiTac
    JMenuItem khachHang;
    JMenuItem nhaCungCap;

    //Các JMenuItem cho JMenu nhanVien
    JMenuItem nhanVienItem;
    JMenuItem chucVuItem;
    JMenuItem chamCongItem;
    JMenuItem bangTinhLuongItem;

    // Các JMenuItem cho JMenu giaoDich gồm (đặt hàng, hoá đơn, vận đơn, trả hàng, nhập hàng, trả hàng nhập, xuất huỷ)


    // CardLayout và panel chứa các panel khác
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public mainGUI(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        // Sử dụng BorderLayout cho JFrame
        setLayout(new BorderLayout());

        // Tạo JMenuBar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Roboto", Font.PLAIN, 14));

        // Tạo các JMenu
        JMenu tongQuanMenu = new JMenu("Tổng quan");
        JMenu hangHoaMenu = new JMenu("Hàng hóa");
        JMenu giaoDichMenu = new JMenu("Giao dịch");
        JMenu doiTacMenu = new JMenu("Đối tác");
        JMenu nhanVienMenu = new JMenu("Nhân viên");
        JMenu baoCaoMenu = new JMenu("Báo cáo");

        // Thêm các JMenu vào JMenuBar
        menuBar.add(tongQuanMenu);
        menuBar.add(hangHoaMenu);
        menuBar.add(giaoDichMenu);
        menuBar.add(doiTacMenu);
        menuBar.add(nhanVienMenu);
        menuBar.add(baoCaoMenu);

        // Tạo các Icon cho JMenu
        FlatSVGIcon tongQuanIcon = new FlatSVGIcon("Icons/tongquan.svg", 16, 16);
        FlatSVGIcon hangHoaIcon = new FlatSVGIcon("Icons/hanghoa.svg", 16, 16);
        FlatSVGIcon giaoDichIcon = new FlatSVGIcon("Icons/giaodich.svg", 16, 16);
        FlatSVGIcon doiTacIcon = new FlatSVGIcon("Icons/doitac.svg", 16, 16);
        FlatSVGIcon nhanVienIcon = new FlatSVGIcon("Icons/nhanvien.svg", 16, 16);
        FlatSVGIcon baoCaoIcon = new FlatSVGIcon("Icons/baocao.svg", 16, 16);

        // Thêm Icon cho JMenu
        tongQuanMenu.setIcon(tongQuanIcon);
        hangHoaMenu.setIcon(hangHoaIcon);
        giaoDichMenu.setIcon(giaoDichIcon);
        doiTacMenu.setIcon(doiTacIcon);
        nhanVienMenu.setIcon(nhanVienIcon);
        baoCaoMenu.setIcon(baoCaoIcon);

        //===================GIAODICH===========================

        // Tạo các JMenuItem cho JMenu giaoDich
        datHangItem = new JMenuItem("Đặt hàng");
        hoaDonItem = new JMenuItem("Hoá đơn");
        traHangItem = new JMenuItem("Trả hàng");
        nhapHangItem = new JMenuItem("Nhập hàng");
        traHangNhapItem = new JMenuItem("Trả hàng nhập");

        // Tạo các Icon cho các JMenuItem
        FlatSVGIcon datHangIcon = new FlatSVGIcon("Icons/dathang.svg", 16, 16);
        FlatSVGIcon hoaDonIcon = new FlatSVGIcon("Icons/hoadon.svg", 16, 16);
        FlatSVGIcon traHangIcon = new FlatSVGIcon("Icons/trahang.svg", 16, 16);
        FlatSVGIcon nhapHangIcon = new FlatSVGIcon("Icons/nhaphang.svg", 16, 16);
        FlatSVGIcon traHangNhapIcon = new FlatSVGIcon("Icons/trahangnhap.svg", 16, 16);


        // Thêm Icon cho các JMenuItem
        datHangItem.setIcon(datHangIcon);
        hoaDonItem.setIcon(hoaDonIcon);
        traHangItem.setIcon(traHangIcon);
        nhapHangItem.setIcon(nhapHangIcon);
        traHangNhapItem.setIcon(traHangNhapIcon);


        // Thêm các JMenuItem vào JMenu giaoDich
        giaoDichMenu.add(datHangItem);
        giaoDichMenu.add(hoaDonItem);
        giaoDichMenu.add(nhapHangItem);
        giaoDichMenu.add(traHangNhapItem);

        //===================HANGHOA===========================

        // Tạo các JMenuItem cho các JMenu hangHoa
        danhMucItem = new JMenuItem("Danh mục");
        thietLapGiaItem = new JMenuItem("Thiết lập giá");
        kiemKhoItem = new JMenuItem("Kiểm kho");

        // Tạo các Icon cho các JMenuItem
        FlatSVGIcon danhMucIcon = new FlatSVGIcon("Icons/danhmuc.svg", 16, 16);
        FlatSVGIcon thietLapGiaIcon = new FlatSVGIcon("Icons/thietlapgia.svg", 16, 16);
        FlatSVGIcon kiemKhoIcon = new FlatSVGIcon("Icons/kiemkho.svg", 16, 16);

        // Thêm Icon cho các JMenuItem
        danhMucItem.setIcon(danhMucIcon);
        thietLapGiaItem.setIcon(thietLapGiaIcon);
        kiemKhoItem.setIcon(kiemKhoIcon);

        // Thêm các JMenuItem vào JMenu hangHoa
        hangHoaMenu.add(danhMucItem);
        hangHoaMenu.add(thietLapGiaItem);
        hangHoaMenu.add(kiemKhoItem);

        //===================DOITAC===========================

        //Tạo các JMenuItem cho JMenu doiTac
        khachHang = new JMenuItem("Khách hàng");
        nhaCungCap = new JMenuItem("Nhà cung cấp");

        // Tạo các Icon cho các JMenuItem
        FlatSVGIcon khachHangIcon = new FlatSVGIcon("Icons/khachhang.svg", 16, 16);
        FlatSVGIcon nhaCungCapIcon = new FlatSVGIcon("Icons/nhacungcap.svg", 16, 16);

        // Thêm Icon cho các JMenuItem
        khachHang.setIcon(khachHangIcon);
        nhaCungCap.setIcon(nhaCungCapIcon);

        // Thêm các JMenuItem vào JMenu doiTac
        doiTacMenu.add(khachHang);
        doiTacMenu.add(nhaCungCap);

        //===================NHANVIEN===========================

        // Tạo các JMenuItem cho các JMenu nhanVien
        nhanVienItem = new JMenuItem("Nhân viên");
        chucVuItem = new JMenuItem("Chức vụ");
        chamCongItem = new JMenuItem("Chấm công");
        bangTinhLuongItem = new JMenuItem("Bảng tính lương");

        // Tạo các Icon cho các JMenuItem
        FlatSVGIcon nhanVienIIcon = new FlatSVGIcon("Icons/nhanvien.svg", 16, 16);
        FlatSVGIcon chucVuIcon = new FlatSVGIcon("Icons/chucvu.svg", 16, 16);
        FlatSVGIcon chamCongIcon = new FlatSVGIcon("Icons/chamcong.svg", 16, 16);
        FlatSVGIcon bangTinhLuongIcon = new FlatSVGIcon("Icons/luong.svg", 16, 16);

        // Thêm Icon cho các JMenuItem
        nhanVienItem.setIcon(nhanVienIIcon);
        chucVuItem.setIcon(chucVuIcon);
        chamCongItem.setIcon(chamCongIcon);
        bangTinhLuongItem.setIcon(bangTinhLuongIcon);

        // Thêm các JMenuItem cho các JMenu nhanVien
        nhanVienMenu.add(nhanVienItem);
        nhanVienMenu.add(chucVuItem);
        nhanVienMenu.add(chamCongItem);
        nhanVienMenu.add(bangTinhLuongItem);


        // Thêm ActionListener cho các JMenuItem
        datHangItem.addActionListener(this);
        hoaDonItem.addActionListener(this);
        traHangItem.addActionListener(this);
        traHangNhapItem.addActionListener(this);

        // Thêm ActionListener cho các JMenuItem
        danhMucItem.addActionListener(this);
        thietLapGiaItem.addActionListener(this);
        kiemKhoItem.addActionListener(this);

        // Thêm ActionListener cho các JMenuItem
        khachHang.addActionListener(this);
        nhaCungCap.addActionListener(this);

        setJMenuBar(menuBar);

        // Tạo vùng chứa chính sử dụng CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Khởi tạo các panel và lưu vào Map
        panelMap = new HashMap<>();
        panelMap.put("hangHoa", new hangHoaPanel());

        // Thêm các panel vào contentPanel với tên định danh (card name)


        // Khởi tạo và thêm các panel vào contentPanel với tên định danh (card name)
        GoodsPanel datHangPanel = new GoodsPanel();
        contentPanel.add(datHangPanel, "DatHang");

        // Ví dụ: tạo thêm các panel khác (HoaDonPanel, VanDonPanel, ...)
        JPanel hoaDonPanel = new JPanel(new BorderLayout());
        hoaDonPanel.add(new JLabel("Đây là panel Hoá đơn", SwingConstants.CENTER), BorderLayout.CENTER);
        contentPanel.add(hoaDonPanel, "HoaDon");

        // Các panel khác tương tự
        JPanel vanDonPanel = new JPanel(new BorderLayout());
        vanDonPanel.add(new JLabel("Đây là panel Vận đơn", SwingConstants.CENTER), BorderLayout.CENTER);
        contentPanel.add(vanDonPanel, "VanDon");

        // Thêm contentPanel vào JFrame, ví dụ ở CENTER của BorderLayout
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Sử dụng CardLayout để hiển thị panel tương ứng
        if (e.getSource() == datHangItem) {
            cardLayout.show(contentPanel, "DatHang");
        } else if (e.getSource() == hoaDonItem) {
            cardLayout.show(contentPanel, "HoaDon");
        }
        // Xử lý các menu khác theo cách tương tự
    }

    public static void main(String[] args) {
        // Thiết lập Look and Feel của FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());

            // TitlePane color
            UIManager.put("RootPane.background",  new Color(0, 102, 204));
            UIManager.put("TitlePane.background", new Color(0, 102, 204));
            UIManager.put("TitlePane.foreground", Color.WHITE);
            UIManager.put("TitlePane.borderColor", new Color(0, 10, 0));

            // Menu
            UIManager.put("MenuBar.foreground", Color.WHITE);
            UIManager.put("MenuBar.selectionForeground", Color.WHITE);
            UIManager.put("MenuBar.hoverBackground", new Color(0, 90, 195));

            // JMenuItem
            UIManager.put("MenuItem.foreground", Color.WHITE);
            UIManager.put("MenuItem.selectionBackground", new Color(0, 102, 204));
            UIManager.put("MenuItem.font", new Font("Roboto", Font.PLAIN, 14));

            // Popup Menu
            UIManager.put("PopupMenu.background", new Color(0, 90, 195));
            UIManager.put("PopupMenu.borderColor", new Color(0, 90, 195));

            new mainGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
