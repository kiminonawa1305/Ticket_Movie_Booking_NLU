package com.lamnguyen.ticket_movie_nlu.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CurrencyFormat {
    public static String formatCurrency(double amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        String pattern = "#,### đ";  // Thêm " đ" để hiển thị đơn vị tiền tệ

        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);

        return decimalFormat.format(amount);
    }
}
