package org.example.GUI;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainGUI extends JFrame implements ActionListener {

    // Tạo các JMenuItem cho JMenu hangHoa
    JMenuItem datHangItem;
    JMenuItem hoaDonItem;
    JMenuItem vanDonItem;
    JMenuItem traHangItem;
    JMenuItem nhapHangItem;
    JMenuItem traHangNhapItem;
    JMenuItem xuatHuyItem;


    public mainGUI(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        // Tạo JMenuBar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Roboto", Font.PLAIN, 14));
        menuBar.setSize(100, 30);

        // Tạo các JMenu
        JMenu tongQuanMenu = new JMenu("Tổng quan");
        JMenu hangHoaMenu = new JMenu("Hàng hóa");
        JMenu giaoDichMenu = new JMenu("Giao dịch");
        JMenu doiTacMenu = new JMenu("Đối tác");
        JMenu nhanVienMenu = new JMenu("Nhân viên");
        JMenu baoCaoMenu = new JMenu("Báo cáo");

        //thêm các JMenu vào JMenuBar
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

        // Tạo các JMenuItem cho JMenu hangHoa gồm
        datHangItem = new JMenuItem("Đặt hàng");
        hoaDonItem = new JMenuItem("Hoá đơn");
        vanDonItem = new JMenuItem("Vận đơn");
        traHangItem = new JMenuItem("Trả hàng");
        nhapHangItem = new JMenuItem("Nhập hàng");
        traHangNhapItem = new JMenuItem("Trả hàng nhập");
        xuatHuyItem = new JMenuItem("Xuất huỷ");

        //thêm các JMenuItem vào JMenu hangHoa
        hangHoaMenu.add(datHangItem);
        hangHoaMenu.add(hoaDonItem);
        hangHoaMenu.add(vanDonItem);
        hangHoaMenu.add(traHangItem);
        hangHoaMenu.add(nhapHangItem);
        hangHoaMenu.add(traHangNhapItem);
        hangHoaMenu.add(xuatHuyItem);

        // Thêm ActionListener cho JMenuItem "Đặt hàng" để đóng ứng dụng khi được chọn
        datHangItem.addActionListener(this);
        hoaDonItem.addActionListener(this);
        vanDonItem.addActionListener(this);
        traHangItem.addActionListener(this);
        nhapHangItem.addActionListener(this);
        traHangNhapItem.addActionListener(this);
        xuatHuyItem.addActionListener(this);



        setJMenuBar(menuBar);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== datHangItem){
            System.out.println("Đặt hàng");
        }

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

            //Menu
            UIManager.put("MenuBar.foreground", Color.WHITE);
            UIManager.put("MenuBar.selectionForeground", Color.white);

            //JMenuItem
            UIManager.put("MenuItem.foreground", Color.WHITE);
            UIManager.put("MenuItem.selectionBackground", new Color(0, 102, 204));
            UIManager.put("MenuItem.font", new Font("Roboto", Font.PLAIN, 14));

            //Popup Menu
            UIManager.put("PopupMenu.background", new Color(0, 77, 153));

            new mainGUI();
        } catch (Exception e) {
            e.printStackTrace();
    }
}
}



