package org.example.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class PhuongThucBoSung {

    public static String formatMoneyVND(double money) {
        // Định dạng số có dấu phẩy phân cách hàng nghìn
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(money) + " VND";
    }

}
