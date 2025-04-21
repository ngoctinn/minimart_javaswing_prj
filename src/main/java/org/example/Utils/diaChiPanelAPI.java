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
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(330, 140));
        setBackground(Color.white);
        setFont(new java.awt.Font("Arial", Font.BOLD, 15));

        Font customFont = new Font("Arial", Font.PLAIN, 14);
        Font customFontTitle = new Font("Arial", Font.BOLD, 15);

        provinceComboBox = new JComboBox<>();
        districtComboBox = new JComboBox<>();
        wardComboBox = new JComboBox<>();

        // Cấu hình GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Số nhà
        CustomTexField soNhaField = new CustomTexField("Số nhà");
        soNhaField.setFont(customFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(0, 0, 5, 10);
        add(soNhaField, gbc);

        // Đường
        CustomTexField duongField = new CustomTexField("Đường");
        duongField.setFont(customFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(0, 10, 5, 0);
        add(duongField, gbc);

        // Tỉnh/Thành phố
        JLabel provincesLabel = new JLabel("Tỉnh/Thành phố");
        provincesLabel.setFont(customFontTitle);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(5, 0, 5, 10);
        add(provincesLabel, gbc);

        provinceComboBox.setFont(customFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(5, 10, 5, 0);
        add(provinceComboBox, gbc);

        // Quận/Huyện
        JLabel districtLabel = new JLabel("Quận/Huyện");
        districtLabel.setFont(customFontTitle);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(5, 0, 5, 10);
        add(districtLabel, gbc);

        districtComboBox.setFont(customFont);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(5, 10, 5, 0);
        add(districtComboBox, gbc);

        // Phường/Xã
        JLabel wardLabel = new JLabel("Phường/Xã");
        wardLabel.setFont(customFontTitle);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(0, 0, 0, 10);
        add(wardLabel, gbc);

        wardComboBox.setFont(customFont);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(0, 10, 0, 0);
        add(wardComboBox, gbc);


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

    // ToString để hển thị cả 3 biến thành 1 biến diaChi
    public String toString() {
        String diaChi = "";
        if (getSelectedProvince() != null) {
            diaChi += getSelectedProvince().getName();
        }
        if (getSelectedDistrict() != null) {
            diaChi += ", " + getSelectedDistrict().getName();
        }
        if (getSelectedWard() != null) {
            diaChi += ", " + getSelectedWard().getName();
        }
        return diaChi;
    }

    // Cách sử dụng:
    // diaChiPanelAPI diaChiPanel = new diaChiPanelAPI();
    // frame.add(diaChiPanel);
    // frame.setVisible(true);

    // Sau khi người dùng chọn địa chỉ, bạn có thể lấy dữ liệu bằng cách:
//     Province selectedProvince = diaChiPanel.getSelectedProvince();
//     District selectedDistrict = diaChiPanel.getSelectedDistrict();
//     Ward selectedWard = diaChiPanel.getSelectedWard();

    // lấy địa chỉ
//     String diaChi = diaChiPanel.toString();


}
