package org.example.GUI;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.example.GUI.Panels.baoCaoPanel;
import org.example.GUI.Panels.doiTacPanel.KhachHangPanel;
import org.example.GUI.Panels.doiTacPanel.nhaCungCapPanel;
import org.example.GUI.Panels.giaoDichPanel.hoaDonPanel;
import org.example.GUI.Panels.giaoDichPanel.nhapHangPanel;
import org.example.GUI.Panels.giaoDichPanel.PhieuNhapPanel;
import org.example.GUI.Panels.hangHoaPanel.SanPhamPanel;
import org.example.GUI.Panels.hangHoaPanel.LoaiSanPhamPanel;
import org.example.GUI.Panels.nhanVienPanel.ChamCongPanel;
import org.example.GUI.Panels.nhanVienPanel.ChucVuPanel;
import org.example.GUI.Panels.nhanVienPanel.HopDongPanel;
import org.example.GUI.Panels.nhanVienPanel.NhanVienPanel;

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
    private JMenu hangHoaMenu, giaoDichMenu, doiTacMenu, nhanVienMenu, baoCaoMenu, userMenu;

    // Các JMenuItem cho JMenu giaoDich
    private JMenuItem banHangItem, hoaDonItem, nhapHangItem, phieuNhapItem, khuyenMaiItem;

    // Các JMenuItem cho JMenu hangHoa
    private JMenuItem sanPhamItem, loaiSanPhamItem;

    // Các JMenuItem cho JMenu doiTac
    private JMenuItem khachHangItem, nhaCungCapItem;

    // Các JMenuItem cho JMenu nhanVien
    private JMenuItem nhanVienItem, hopDongItem, chucVuItem, chamCongItem;

    // Các JMenuItem cho JMenu người dùng
    private JMenuItem profileItem, logoutItem;

    public MenuFrame() {
        initializeFrame();
        setupMenuBar();
        setupContentPanel();
        setVisible(false);
    }

    // Thêm phương thức để ẩn menu item
    public void hideMenuItem(String itemName) {
        switch (itemName) {
            case "Quản lý sản phẩm":
                sanPhamItem.setVisible(false);
                break;
            case "Quản lý loại sản phẩm":
                loaiSanPhamItem.setVisible(false);
                break;
            case "Quản lý bán hàng":
                banHangItem.setVisible(false);
                break;
            case "Quản lý hoá đơn":
                hoaDonItem.setVisible(false);
                break;
            case "Quản lý nhập hàng":
                nhapHangItem.setVisible(false);
                break;
            case "Quản lý phiếu nhập":
                phieuNhapItem.setVisible(false);
                break;
            case "Quản lý khuyến mãi":
                khuyenMaiItem.setVisible(false);
                break;
            case "Quản lý khách hàng":
                khachHangItem.setVisible(false);
                break;
            case "Quản lý nhà cung cấp":
                nhaCungCapItem.setVisible(false);
                break;
            case "Quản lý nhân viên":
                nhanVienItem.setVisible(false);
                break;
            case "Quản lý hợp đồng":
                hopDongItem.setVisible(false);
                break;
            case "Quản lý chức vụ":
                chucVuItem.setVisible(false);
                break;
            case "Quản lý chấm công":
                chamCongItem.setVisible(false);
                break;
        }
    }


    //phương thức để ẩn menu hành động trong panel
    public void hideActionPanel(String panelName) {
        switch (panelName) {
            case "Quản lý sản phẩm":
                ((SanPhamPanel) panelMap.get("sanPham")).hideActionPanel();
                break;
            case "Quản lý loại sản phẩm":
                ((LoaiSanPhamPanel) panelMap.get("loaiSanPham")).hideActionPanel();
                break;
            case "Quản lý khách hàng":
                ((KhachHangPanel) panelMap.get("khachHang")).hideActionPanel();
                break;
            case "Quản lý nhà cung cấp":
                ((nhaCungCapPanel) panelMap.get("nhaCungCap")).hideActionPanel();
                break;
            case "Quản lý nhân viên":
                ((NhanVienPanel) panelMap.get("nhanVien")).hideActionPanel();
                break;
            case "Quản lý phiếu nhập":
                ((PhieuNhapPanel) panelMap.get("phieuNhap")).hideActionPanel();
                break;
        }
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
        hangHoaMenu = new JMenu("Hàng hóa");
        giaoDichMenu = new JMenu("Giao dịch");
        doiTacMenu = new JMenu("Đối tác");
        nhanVienMenu = new JMenu("Nhân viên");
        baoCaoMenu = new JMenu("Báo cáo");
        userMenu = new JMenu("Người dùng");

        // Tạo và thêm Icon cho các JMenu
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
        phieuNhapItem = createMenuItem("Phiếu nhập", "nhaphang", 16);
        khuyenMaiItem = createMenuItem("Khuyến mãi", "khuyenmai", 16);

        // Tạo JMenuItem cho hàng hóa
        sanPhamItem = createMenuItem("Sản phẩm", "danhmuc", 16);
        loaiSanPhamItem = createMenuItem("Loại sản phẩm", "loaisanpham", 16);

        // Tạo JMenuItem cho đối tác
        khachHangItem = createMenuItem("Khách hàng", "khachhang", 16);
        nhaCungCapItem = createMenuItem("Nhà cung cấp", "nhacungcap", 16);

        // Tạo JMenuItem cho nhân viên
        nhanVienItem = createMenuItem("Nhân viên", "nhanvien", 16);
        hopDongItem = createMenuItem("Hợp đồng", "hopdong", 16);
        chucVuItem = createMenuItem("Chức vụ", "chucvu", 16);
        chamCongItem = createMenuItem("Chấm công", "chamcong", 16);

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
        giaoDichMenu.add(phieuNhapItem);
        giaoDichMenu.add(khuyenMaiItem);

        // Thêm JMenuItem vào hàng hóa
        hangHoaMenu.add(sanPhamItem);
        hangHoaMenu.add(loaiSanPhamItem);

        // Thêm JMenuItem vào đối tác
        doiTacMenu.add(khachHangItem);
        doiTacMenu.add(nhaCungCapItem);

        // Thêm JMenuItem vào nhân viên
        nhanVienMenu.add(nhanVienItem);
        nhanVienMenu.add(hopDongItem);
        nhanVienMenu.add(chucVuItem);
        nhanVienMenu.add(chamCongItem);


        // Thêm JMenuItem vào người dùng
        userMenu.add(profileItem);
        userMenu.add(logoutItem);
    }

    private void addActionListeners() {
        // Thêm ActionListener cho JMenu

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

        // Thêm ActionListener cho các JMenuItem hàng hóa
        sanPhamItem.addActionListener(this);
        loaiSanPhamItem.addActionListener(this);


        // Thêm ActionListener cho các JMenuItem giao dịch
        banHangItem.addActionListener(this);
        hoaDonItem.addActionListener(this);
        nhapHangItem.addActionListener(this);
        phieuNhapItem.addActionListener(this);
        khuyenMaiItem.addActionListener(this);


        // Thêm ActionListener cho các JMenuItem đối tác
        khachHangItem.addActionListener(this);
        nhaCungCapItem.addActionListener(this);

        // Thêm ActionListener cho các JMenuItem nhân viên
        nhanVienItem.addActionListener(this);
        hopDongItem.addActionListener(this);
        chucVuItem.addActionListener(this);
        chamCongItem.addActionListener(this);

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

        // Hiển thị panel sản phẩm mặc định
        cardLayout.show(contentPanel, "tongQuan");

        // Thêm contentPanel vào JFrame
        add(contentPanel, BorderLayout.CENTER);
    }

    private void initializePanels() {
        panelMap = new HashMap<>();

        //tổng quan
        panelMap.put("tongQuan", new org.example.GUI.Panels.tongQuanPanel());

        // Hàng hóa panels
        panelMap.put("sanPham", new SanPhamPanel());
        panelMap.put("loaiSanPham", new LoaiSanPhamPanel());

        // Giao dịch panels
        panelMap.put("banHang", new BanHangPanel());
        panelMap.put("hoaDon", new hoaDonPanel());
        panelMap.put("nhapHang", new nhapHangPanel());
        panelMap.put("phieuNhap", new PhieuNhapPanel());
        panelMap.put("khuyenMai", new org.example.GUI.Panels.giaoDichPanel.KhuyenMaiPanel());

        // Đối tác panels
        panelMap.put("khachHang", new KhachHangPanel());
        panelMap.put("nhaCungCap", new nhaCungCapPanel());

        // Nhân viên panels
        panelMap.put("nhanVien", new NhanVienPanel());
        panelMap.put("hopDong", new HopDongPanel());
        panelMap.put("chucVu", new ChucVuPanel());
        panelMap.put("chamCong", new ChamCongPanel());

        // Báo cáo panel
        panelMap.put("baoCao", new baoCaoPanel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // Hàng hóa
        if (source == sanPhamItem) {
            showPanel("sanPham");
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
        } else if (source == phieuNhapItem) {
            showPanel("phieuNhap");
        } else if (source == khuyenMaiItem) {
            showPanel("khuyenMai");
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
        }
        else if (source == hopDongItem) {
            showPanel("hopDong");
        }
        else if (source == chucVuItem) {
            showPanel("chucVu");
        } else if (source == chamCongItem) {
            showPanel("chamCong");
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

    // phương thức ẩn menu item
    public void hideMenuItem(JMenuItem menuItem) {
        menuItem.setVisible(false);
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

        // Menu selection color
        UIManager.put("Menu.selectionBackground", new Color(0, 80, 180));
        UIManager.put("Menu.selectionForeground", Color.WHITE);
        UIManager.put("Menu.background", new Color(0, 102, 204));
        UIManager.put("Menu.foreground", Color.WHITE);
        UIManager.put("Menu.opaque", true);

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
            UIManager.setLookAndFeel(new FlatLightLaf());
            setupUIManagerProperties();
            MenuFrame menuFrame = new MenuFrame();
            menuFrame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
