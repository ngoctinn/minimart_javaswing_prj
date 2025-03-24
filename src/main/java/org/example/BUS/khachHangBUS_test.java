package org.example.BUS;
import org.example.DAO.*;
import org.example.DTO.*;
import java.util.List;


public class khachHangBUS_test {
    private KhachHangDAO_test khDAO = new KhachHangDAO_test();
    private List<khachHangDTO> customerList;

    public khachHangBUS_test() {
        customerList = khDAO.getAllList();
        if (customerList == null || customerList.isEmpty()) {
            System.out.println(" Không có khách hàng nào trong DB!");
        }
    }

    public List<khachHangDTO> getAllList() {
        return customerList;
    }
    public boolean addData(khachHangDTO kh) {
        return khDAO.addData(kh);
    }

    public static void main(String[] args){
        khachHangBUS_test test = new khachHangBUS_test();
        List<khachHangDTO> customerList = test.getAllList();
        for(khachHangDTO khDTO : customerList){
            System.out.println(khDTO);
        }

    }
}

