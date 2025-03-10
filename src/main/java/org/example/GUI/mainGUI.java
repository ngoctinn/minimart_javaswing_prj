package org.example.GUI;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.example.Components.GoodsPanel;
import org.example.GUI.Panels.baoCaoPanel;
import org.example.GUI.Panels.doiTacPanel.khachHangPanel;
import org.example.GUI.Panels.doiTacPanel.nhaCungCapPanel;
import org.example.GUI.Panels.giaoDichPanel.datHangPanel;
import org.example.GUI.Panels.giaoDichPanel.hoaDonPanel;
import org.example.GUI.Panels.giaoDichPanel.nhapHangPanel;
import org.example.GUI.Panels.giaoDichPanel.traHangNhapPanel;
import org.example.GUI.Panels.hangHoaPanel.danhMucPanel;
import org.example.GUI.Panels.hangHoaPanel.kiemKhoPanel;
import org.example.GUI.Panels.hangHoaPanel.thietLapGiaPanel;
import org.example.GUI.Panels.nhanVienPanel.bangChamLuongPanel;
import org.example.GUI.Panels.nhanVienPanel.chamCongPanel;
import org.example.GUI.Panels.nhanVienPanel.chucVuPanel;
import org.example.GUI.Panels.nhanVienPanel.nhanVienPanel;
// import các panel khác

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class mainGUI extends JFrame implements ActionListener {
    Map<String, JPanel> panelMap;

    // Các JMenu
    JMenu tongQuanMenu;
    JMenu hangHoaMenu;
    JMenu giaoDichMenu;
    JMenu doiTacMenu;
    JMenu nhanVienMenu;
    JMenu baoCaoMenu;

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
        // Tạo JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

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
        baoCaoMenu = new JMenu("Báo cáo");

        // tạo JMenu người dùng nằm bên phải menuBar
        JMenu userMenu = new JMenu("Người dùng");

        // Tạo các Icon cho JMenu người dùng
        FlatSVGIcon userIcon = new FlatSVGIcon("Icons/user.svg", 20, 20);
        userMenu.setIcon(userIcon);

        // Tạo các JMenuItem cho JMenu người dùng
        JMenuItem profileItem = new JMenuItem("Thông tin");
        JMenuItem logoutItem = new JMenuItem("Đăng xuất");

        // Tạo các Icon cho các JMenuItem
        FlatSVGIcon profileIcon = new FlatSVGIcon("Icons/profile.svg", 20, 20);
        FlatSVGIcon logoutIcon = new FlatSVGIcon("Icons/logout.svg", 20, 20);

        // Thêm các JMenuItem vào JMenu người dùng
        userMenu.add(profileItem);
        userMenu.add(logoutItem);

        // Thêm Icon cho các JMenuItem
        profileItem.setIcon(profileIcon);
        logoutItem.setIcon(logoutIcon);

        // Thêm các JMenu vào JMenuBar
        menuBar.add(tongQuanMenu);
        menuBar.add(hangHoaMenu);
        menuBar.add(giaoDichMenu);
        menuBar.add(doiTacMenu);
        menuBar.add(nhanVienMenu);
        menuBar.add(baoCaoMenu);

        // Thêm JMenu người dùng vào menuBar
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(userMenu);

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

        //=================THÊM ACTIONLISTENER=========================

        // tHEM AcionListener cho JMenu
        tongQuanMenu.addActionListener(this);
        baoCaoMenu.addActionListener(this);

        // Thêm ActionListener cho các JMenuItem
        datHangItem.addActionListener(this);
        hoaDonItem.addActionListener(this);
        traHangItem.addActionListener(this);
        nhapHangItem.addActionListener(this);
        traHangNhapItem.addActionListener(this);

        // Thêm ActionListener cho các JMenuItem
        danhMucItem.addActionListener(this);
        thietLapGiaItem.addActionListener(this);
        kiemKhoItem.addActionListener(this);

        // Thêm ActionListener cho các JMenuItem
        khachHang.addActionListener(this);
        nhaCungCap.addActionListener(this);

        // Thêm ActionListener cho các JMenuItem
        nhanVienItem.addActionListener(this);
        chucVuItem.addActionListener(this);
        chamCongItem.addActionListener(this);
        bangTinhLuongItem.addActionListener(this);

        // Thêm ActionListener cho các JMenuItem


        setJMenuBar(menuBar);

        // Tạo vùng chứa chính sử dụng CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        //set panel hiển thị hàng hóa đầu tiên
        contentPanel.add("danhMuc", new danhMucPanel());


        // Khởi tạo các panel và lưu vào Map
        panelMap = new HashMap<>();
        panelMap.put("danhMuc", new danhMucPanel());
        panelMap.put("kiemKho", new kiemKhoPanel());
        panelMap.put("thietLapGia", new thietLapGiaPanel());
        panelMap.put("datHang", new datHangPanel());
        panelMap.put("hoaDon", new hoaDonPanel());
        panelMap.put("nhapHang", new nhapHangPanel());
        panelMap.put("tranHang", new traHangNhapPanel());
        panelMap.put("khachHang", new khachHangPanel());
        panelMap.put("nhaCungCap", new nhaCungCapPanel());
        panelMap.put("nhanVien", new nhanVienPanel());
        panelMap.put("chucVu", new chucVuPanel());
        panelMap.put("chamCong", new chamCongPanel());
        panelMap.put("bangTinhLuong", new bangChamLuongPanel());
        panelMap.put("baoCao", new baoCaoPanel());



        // Thêm các panel vào contentPanel với tên định danh (card name)
        for (Map.Entry<String, JPanel> entry : panelMap.entrySet()) {
            contentPanel.add(entry.getKey(), entry.getValue());
        }



        // Thêm contentPanel vào JFrame, ví dụ ở CENTER của BorderLayout
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Sử dụng CardLayout để hiển thị panel tương ứng
        if (e.getSource() == danhMucItem) {
            cardLayout.show(contentPanel, "danhMuc");
        } else if (e.getSource() == thietLapGiaItem) {
            cardLayout.show(contentPanel, "thietLapGia");
        } else if (e.getSource() == kiemKhoItem) {
            cardLayout.show(contentPanel, "kiemKho");
        } else if (e.getSource() == datHangItem) {
            cardLayout.show(contentPanel, "datHang");
        } else if (e.getSource() == hoaDonItem) {
            cardLayout.show(contentPanel, "hoaDon");
        } else if (e.getSource() == nhapHangItem) {
            cardLayout.show(contentPanel, "nhapHang");
        } else if (e.getSource() == traHangNhapItem) {
            cardLayout.show(contentPanel, "tranHang");
        } else if (e.getSource() == khachHang) {
            cardLayout.show(contentPanel, "khachHang");
        } else if (e.getSource() == nhaCungCap) {
            cardLayout.show(contentPanel, "nhaCungCap");
        } else if (e.getSource() == nhanVienItem) {
            cardLayout.show(contentPanel, "nhanVien");
        } else if (e.getSource() == chucVuItem) {
            cardLayout.show(contentPanel, "chucVu");
        } else if (e.getSource() == chamCongItem) {
            cardLayout.show(contentPanel, "chamCong");
        } else if (e.getSource() == bangTinhLuongItem) {
            cardLayout.show(contentPanel, "bangTinhLuong");
        } else if (e.getSource() == baoCaoMenu) {
            cardLayout.show(contentPanel, "baoCao");
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
            UIManager.put("MenuBar.itemMargins", new Insets(7, 5, 7, 5));

            // JMenuItem
            UIManager.put("MenuItem.foreground", Color.WHITE);
            UIManager.put("MenuItem.selectionBackground", new Color(0, 102, 204));
            UIManager.put("MenuItem.font", new Font("Roboto", Font.PLAIN, 14));
            UIManager.put("MenuItem.margin", new Insets(7, 7, 7, 7));

            // Popup Menu
            UIManager.put("PopupMenu.background", new Color(0, 90, 195));
            UIManager.put("PopupMenu.borderColor", new Color(0, 90, 195));

            new mainGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
