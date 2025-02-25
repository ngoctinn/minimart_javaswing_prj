package org.example.GUI;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.example.Components.GoodsPanel;
// import các panel khác

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainGUI extends JFrame implements ActionListener {

    // Các JMenuItem cho JMenu hangHoa
    JMenuItem datHangItem;
    JMenuItem hoaDonItem;
    JMenuItem vanDonItem;
    JMenuItem traHangItem;
    JMenuItem nhapHangItem;
    JMenuItem traHangNhapItem;
    JMenuItem xuatHuyItem;

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

        // Tạo các JMenuItem cho JMenu hangHoa
        datHangItem = new JMenuItem("Đặt hàng");
        hoaDonItem = new JMenuItem("Hoá đơn");
        vanDonItem = new JMenuItem("Vận đơn");
        traHangItem = new JMenuItem("Trả hàng");
        nhapHangItem = new JMenuItem("Nhập hàng");
        traHangNhapItem = new JMenuItem("Trả hàng nhập");
        xuatHuyItem = new JMenuItem("Xuất huỷ");

        // Thêm các JMenuItem vào JMenu hangHoa
        hangHoaMenu.add(datHangItem);
        hangHoaMenu.add(hoaDonItem);
        hangHoaMenu.add(vanDonItem);
        hangHoaMenu.add(traHangItem);
        hangHoaMenu.add(nhapHangItem);
        hangHoaMenu.add(traHangNhapItem);
        hangHoaMenu.add(xuatHuyItem);

        // Thêm ActionListener cho các JMenuItem
        datHangItem.addActionListener(this);
        hoaDonItem.addActionListener(this);
        vanDonItem.addActionListener(this);
        traHangItem.addActionListener(this);
        nhapHangItem.addActionListener(this);
        traHangNhapItem.addActionListener(this);
        xuatHuyItem.addActionListener(this);

        setJMenuBar(menuBar);

        // Tạo vùng chứa chính sử dụng CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

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
        } else if (e.getSource() == vanDonItem) {
            cardLayout.show(contentPanel, "VanDon");
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
