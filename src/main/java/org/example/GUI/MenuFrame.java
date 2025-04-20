package org.example.GUI;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.example.GUI.Panels.baoCaoPanel;
import org.example.GUI.Panels.doiTacPanel.KhachHangPanel;
import org.example.GUI.Panels.doiTacPanel.nhaCungCapPanel;
import org.example.GUI.Panels.giaoDichPanel.hoaDonPanel;
import org.example.GUI.Panels.giaoDichPanel.nhapHangPanel;
import org.example.GUI.Panels.giaoDichPanel.traHangNhapPanel;
import org.example.GUI.Panels.hangHoaPanel.SanPhamPanel;
import org.example.GUI.Panels.hangHoaPanel.KiemKhoPanel;
import org.example.GUI.Panels.hangHoaPanel.LoaiSanPhamPanel;
import org.example.GUI.Panels.hangHoaPanel.ThietLapGiaPanel;
import org.example.GUI.Panels.nhanVienPanel.BangChamLuongPanel;
import org.example.GUI.Panels.nhanVienPanel.ChamCongPanel;
import org.example.GUI.Panels.nhanVienPanel.ChucVuPanel;
import org.example.GUI.Panels.nhanVienPanel.NhanVienPanel;
import org.example.GUI.Panels.tongQuanPanel;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MenuFrame extends JFrame implements ActionListener {
    private Map<String, JPanel> panelMap;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    // Các JMenu
    private JMenu tongQuanMenu, hangHoaMenu, giaoDichMenu, doiTacMenu, nhanVienMenu, baoCaoMenu, userMenu;

    // Các JMenuItem cho JMenu giaoDich
    private JMenuItem banHangItem ,hoaDonItem, nhapHangItem;

    // Các JMenuItem cho JMenu hangHoa
    private JMenuItem danhMucItem, loaiSanPhamItem;

    // Các JMenuItem cho JMenu doiTac
    private JMenuItem khachHangItem, nhaCungCapItem;

    // Các JMenuItem cho JMenu nhanVien
    private JMenuItem nhanVienItem, chucVuItem, chamCongItem, bangTinhLuongItem;

    // Các JMenuItem cho JMenu người dùng
    private JMenuItem profileItem, logoutItem;

    public MenuFrame() {
        initializeFrame();
        setupMenuBar();
        setupContentPanel();
        setVisible(true);
    }

    private void initializeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Roboto", Font.PLAIN, 14));

        // Tạo các JMenu
        createMenus();

        // Tạo các JMenuItem
        createMenuItems();

        // Thêm các JMenuItem vào JMenu
        addMenuItems();

        // Thêm ActionListener cho JMenu và JMenuItem
        addActionListeners();

        // Thêm các JMenu vào menuBar
        menuBar.add(tongQuanMenu);
        menuBar.add(hangHoaMenu);
        menuBar.add(giaoDichMenu);
        menuBar.add(doiTacMenu);
        menuBar.add(nhanVienMenu);
        menuBar.add(baoCaoMenu);

        // Thêm JMenu người dùng vào menuBar (ở bên phải)
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(userMenu);

        setJMenuBar(menuBar);
    }

    private void createMenus() {
        // Tạo các JMenu
        tongQuanMenu = new JMenu("Tổng quan");
        hangHoaMenu = new JMenu("Hàng hóa");
        giaoDichMenu = new JMenu("Giao dịch");
        doiTacMenu = new JMenu("Đối tác");
        nhanVienMenu = new JMenu("Nhân viên");
        baoCaoMenu = new JMenu("Báo cáo");
        userMenu = new JMenu("Người dùng");

        // Tạo và thêm Icon cho các JMenu
        tongQuanMenu.setIcon(createIcon("tongquan", 16));
        hangHoaMenu.setIcon(createIcon("hanghoa", 16));
        giaoDichMenu.setIcon(createIcon("giaodich", 16));
        doiTacMenu.setIcon(createIcon("doitac", 16));
        nhanVienMenu.setIcon(createIcon("nhanvien", 16));
        baoCaoMenu.setIcon(createIcon("baocao", 16));
        userMenu.setIcon(createIcon("user", 20));
    }

    private void createMenuItems() {
        // Tạo JMenuItem cho giao dịch
        banHangItem = createMenuItem("Bán hàng", "banhang", 16);
        hoaDonItem = createMenuItem("Hoá đơn", "hoadon", 16);
        nhapHangItem = createMenuItem("Nhập hàng", "nhaphang", 16);

        // Tạo JMenuItem cho hàng hóa
        danhMucItem = createMenuItem("Danh mục", "danhmuc", 16);
        loaiSanPhamItem = createMenuItem("Loại sản phẩm", "loaisanpham", 16);

        // Tạo JMenuItem cho đối tác
        khachHangItem = createMenuItem("Khách hàng", "khachhang", 16);
        nhaCungCapItem = createMenuItem("Nhà cung cấp", "nhacungcap", 16);

        // Tạo JMenuItem cho nhân viên
        nhanVienItem = createMenuItem("Nhân viên", "nhanvien", 16);
        chucVuItem = createMenuItem("Chức vụ", "chucvu", 16);
        chamCongItem = createMenuItem("Chấm công", "chamcong", 16);
        bangTinhLuongItem = createMenuItem("Bảng tính lương", "luong", 16);

        // Tạo JMenuItem cho người dùng
        profileItem = createMenuItem("Thông tin", "profile", 16);
        logoutItem = createMenuItem("Đăng xuất", "logout", 16);
    }

    private JMenuItem createMenuItem(String text, String iconName, int size) {
        JMenuItem item = new JMenuItem(text);
        item.setIcon(createIcon(iconName, size));
        return item;
    }

    private FlatSVGIcon createIcon(String name, int size) {
        return new FlatSVGIcon("Icons/" + name + ".svg", size, size);
    }

    private void addMenuItems() {
        // Thêm JMenuItem vào giao dịch
        giaoDichMenu.add(banHangItem);
        giaoDichMenu.add(hoaDonItem);
        giaoDichMenu.add(nhapHangItem);

        // Thêm JMenuItem vào hàng hóa
        hangHoaMenu.add(danhMucItem);
        hangHoaMenu.add(loaiSanPhamItem);

        // Thêm JMenuItem vào đối tác
        doiTacMenu.add(khachHangItem);
        doiTacMenu.add(nhaCungCapItem);

        // Thêm JMenuItem vào nhân viên
        nhanVienMenu.add(nhanVienItem);
        nhanVienMenu.add(chucVuItem);
        nhanVienMenu.add(chamCongItem);
        nhanVienMenu.add(bangTinhLuongItem);

        // Thêm JMenuItem vào người dùng
        userMenu.add(profileItem);
        userMenu.add(logoutItem);
    }

    private void addActionListeners() {
        // Thêm ActionListener cho JMenu
        tongQuanMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                showPanel("tongQuan");
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {}
        });

        baoCaoMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                showPanel("baoCao");
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });


        // Thêm ActionListener cho các JMenuItem giao dịch
        banHangItem.addActionListener(this);
        hoaDonItem.addActionListener(this);
        nhapHangItem.addActionListener(this);


        // Thêm ActionListener cho các JMenuItem hàng hóa
        danhMucItem.addActionListener(this);
        loaiSanPhamItem.addActionListener(this);



        // Thêm ActionListener cho các JMenuItem đối tác
        khachHangItem.addActionListener(this);
        nhaCungCapItem.addActionListener(this);

        // Thêm ActionListener cho các JMenuItem nhân viên
        nhanVienItem.addActionListener(this);
        chucVuItem.addActionListener(this);
        chamCongItem.addActionListener(this);
        bangTinhLuongItem.addActionListener(this);

        // Thêm ActionListener cho các JMenuItem người dùng
        profileItem.addActionListener(this);
        logoutItem.addActionListener(this);
    }

    private void setupContentPanel() {
        // Tạo vùng chứa chính sử dụng CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Khởi tạo panelMap và các panel
        initializePanels();

        // Thêm các panel vào contentPanel
        for (Map.Entry<String, JPanel> entry : panelMap.entrySet()) {
            contentPanel.add(entry.getKey(), entry.getValue());
        }

        // Hiển thị panel mặc định
        cardLayout.show(contentPanel, "danhMuc");

        // Thêm contentPanel vào JFrame
        add(contentPanel, BorderLayout.CENTER);
    }

    private void initializePanels() {
        panelMap = new HashMap<>();

        panelMap.put("tongQuan", new tongQuanPanel());

        // Hàng hóa panels
        panelMap.put("danhMuc", new SanPhamPanel());
        panelMap.put("loaiSanPham", new LoaiSanPhamPanel());
        panelMap.put("kiemKho", new KiemKhoPanel());
        panelMap.put("thietLapGia", new ThietLapGiaPanel());

        // Giao dịch panels
        panelMap.put("banHang", new BanHangPanel());
        panelMap.put("hoaDon", new hoaDonPanel());
        panelMap.put("nhapHang", new nhapHangPanel());
        panelMap.put("traHang", new traHangNhapPanel());

        // Đối tác panels
        panelMap.put("khachHang", new KhachHangPanel());
        panelMap.put("nhaCungCap", new nhaCungCapPanel());

        // Nhân viên panels
        panelMap.put("nhanVien", new NhanVienPanel());
        panelMap.put("chucVu", new ChucVuPanel());
        panelMap.put("chamCong", new ChamCongPanel());
        panelMap.put("bangTinhLuong", new BangChamLuongPanel());

        // Báo cáo panel
        panelMap.put("baoCao", new baoCaoPanel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // Hàng hóa
        if (source == danhMucItem) {
            showPanel("danhMuc");
        } else if (source == loaiSanPhamItem) {
            showPanel("loaiSanPham");
        }
        // Giao dịch
        else if (source == banHangItem) {
            showPanel("banHang");
        }
        else if (source == hoaDonItem) {
            showPanel("hoaDon");
        } else if (source == nhapHangItem) {
            showPanel("nhapHang");
        }
        // Đối tác
        else if (source == khachHangItem) {
            showPanel("khachHang");
        } else if (source == nhaCungCapItem) {
            showPanel("nhaCungCap");
        }
        // Nhân viên
        else if (source == nhanVienItem) {
            showPanel("nhanVien");
        } else if (source == chucVuItem) {
            showPanel("chucVu");
        } else if (source == chamCongItem) {
            showPanel("chamCong");
        } else if (source == bangTinhLuongItem) {
            showPanel("bangTinhLuong");
        }
        // Báo cáo
        else if (source == baoCaoMenu) {
            showPanel("baoCao");
        }
        // Người dùng
        else if (source == profileItem) {
            handleProfileAction();
        } else if (source == logoutItem) {
            handleLogoutAction();
        }
    }


    public void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
    }

    private void handleProfileAction() {
        // Xử lý khi người dùng chọn xem thông tin cá nhân
        JOptionPane.showMessageDialog(this, "Chức năng xem thông tin cá nhân");
    }

    private void handleLogoutAction() {
        // Xử lý khi người dùng đăng xuất
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn đăng xuất không?", "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // Thực hiện đăng xuất
            dispose();
            // TODO: Thêm code để trở về màn hình đăng nhập
        }
    }

    public static void setupUIManagerProperties() {
        // TitlePane color
        UIManager.put("RootPane.background", new Color(0, 102, 204));
        UIManager.put("TitlePane.background", new Color(0, 102, 204));
        UIManager.put("TitlePane.foreground", Color.WHITE);

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
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            setupUIManagerProperties();
            MenuFrame menuFrame = new MenuFrame();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
