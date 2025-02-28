package org.example.Utils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


import com.google.gson.Gson;
import org.example.Components.CustomTexField;

public class diaChiPanelAPI extends JPanel {
    private JComboBox<Province> provinceComboBox;
    private JComboBox<District> districtComboBox;
    private JComboBox<Ward> wardComboBox;
    private List<Province> provinces;

    public diaChiPanelAPI() {

        setLayout(null);
        setBounds(400,50,330,200);
        setFont(new java.awt.Font("Arial", 0, 15));

        Font customFont = new Font("Arial", Font.PLAIN, 15);

        provinceComboBox = new JComboBox<>();
        districtComboBox = new JComboBox<>();
        wardComboBox = new JComboBox<>();


        CustomTexField soNhaField = new CustomTexField("Số nhà");
        soNhaField.setBounds(10, 10, 100, 30); // Lùi lên 10 đơn vị
        soNhaField.setFont(customFont);

        CustomTexField duongField = new CustomTexField("Đường");
        duongField.setBounds(130, 10, 200, 30); // Lùi lên 10 đơn vị
        duongField.setFont(customFont);

        JLabel provincesLabel = new JLabel("Tỉnh/Thành phố");
        provincesLabel.setBounds(10, 50, 100, 30); // Lùi lên 10 đơn vị
        provincesLabel.setFont(customFont);


        provinceComboBox.setBounds(130, 50, 200, 30); // Lùi lên 10 đơn vị
        provinceComboBox.setFont(customFont);

        JLabel districtLabel = new JLabel("Quận/Huyện");
        districtLabel.setBounds(10, 90, 100, 30); // Lùi lên 10 đơn vị
        districtLabel.setFont(customFont);

        districtComboBox.setBounds(130, 90, 200, 30); // Lùi lên 10 đơn vị
        districtComboBox.setFont(customFont);

        JLabel wardLabel = new JLabel("Phường/Xã");
        wardLabel.setBounds(10, 130, 100, 30); // Lùi lên 10 đơn vị
        wardLabel.setFont(customFont);

        wardComboBox.setBounds(130, 130, 200, 30); // Lùi lên 10 đơn vị
        wardComboBox.setFont(customFont);



        add(soNhaField);
        add(duongField);
        add(provincesLabel);
        add(provinceComboBox);
        add(districtLabel);
        add(districtComboBox);
        add(wardLabel);
        add(wardComboBox);


        // Load dữ liệu từ API
        loadData();



        // Khi chọn tỉnh/thành phố thì cập nhật danh sách quận/huyện
        provinceComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Province selectedProvince = (Province) provinceComboBox.getSelectedItem();
                if (selectedProvince != null) {
                    updateDistrictComboBox(selectedProvince);
                }
            }
        });

        // Khi chọn quận/huyện thì cập nhật danh sách phường/xã
        districtComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                District selectedDistrict = (District) districtComboBox.getSelectedItem();
                if (selectedDistrict != null) {
                    updateWardComboBox(selectedDistrict);
                }
            }
        });
    }



    private void loadData() {
        try {
            String urlStr = "https://provinces.open-api.vn/api/?depth=3";
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            String json = content.toString();
            Gson gson = new Gson();
            com.google.gson.reflect.TypeToken<java.util.List<Province>> token = new com.google.gson.reflect.TypeToken<java.util.List<Province>>() {};
            java.lang.reflect.Type provinceListType = token.getType();
            provinces = gson.fromJson(json, provinceListType);

            // Hiển thị dữ liệu vào combobox tỉnh/thành phố
            for (Province province : provinces) {
                provinceComboBox.addItem(province);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ API");
        }
    }

    private void updateDistrictComboBox(Province province) {
        districtComboBox.removeAllItems();
        wardComboBox.removeAllItems(); // Xoá luôn combobox phường/xã khi thay đổi tỉnh
        if (province.getDistricts() != null) {
            for (District district : province.getDistricts()) {
                districtComboBox.addItem(district);
            }
        }
    }

    private void updateWardComboBox(District district) {
        wardComboBox.removeAllItems();
        if (district.getWards() != null) {
            for (Ward ward : district.getWards()) {
                wardComboBox.addItem(ward);
            }
        }
    }

    // Getter để lấy dữ liệu khi người dùng chọn
    public Province getSelectedProvince() {
        return (Province) provinceComboBox.getSelectedItem();
    }

    public District getSelectedDistrict() {
        return (District) districtComboBox.getSelectedItem();
    }

    public Ward getSelectedWard() {
        return (Ward) wardComboBox.getSelectedItem();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Địa chỉ");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.add(new diaChiPanelAPI());
                frame.setVisible(true);
            }
        });
    }

    // Các lớp nội bộ thể hiện cấu trúc dữ liệu của API
    public class Province {
        private int code;
        private String name;
        private List<District> districts;

        public int getCode() {
            return code;
        }
        public String getName() {
            return name;
        }
        public List<District> getDistricts() {
            return districts;
        }
        @Override
        public String toString() {
            return name;
        }
    }

    public class District {
        private int code;
        private String name;
        private List<Ward> wards;

        public int getCode() {
            return code;
        }
        public String getName() {
            return name;
        }
        public List<Ward> getWards() {
            return wards;
        }
        @Override
        public String toString() {
            return name;
        }
    }

    public class Ward {
        private String code;
        private String name;

        public String getCode() {
            return code;
        }
        public String getName() {
            return name;
        }
        @Override
        public String toString() {
            return name;
        }
    }

    // Cách sử dụng:
    // diaChiPanelAPI diaChiPanel = new diaChiPanelAPI();
    // frame.add(diaChiPanel);
    // frame.setVisible(true);

    // Sau khi người dùng chọn địa chỉ, bạn có thể lấy dữ liệu bằng cách:
    // Province selectedProvince = diaChiPanel.getSelectedProvince();
    // District selectedDistrict = diaChiPanel.getSelectedDistrict();
    // Ward selectedWard = diaChiPanel.getSelectedWard();

}
