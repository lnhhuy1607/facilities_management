package com.doanCuoiKi.FacilityStore_Management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends ArrayAdapter<Order> {
    private Context context;
    private List<Order> orderList;

    public OrderAdapter(@NonNull Context context, List<Order> orders) {
        super(context, 0, orders);
        this.context = context;
        this.orderList = orders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        }

        Order order = orderList.get(position);
        TextView tvCustomerName = convertView.findViewById(R.id.tvCustomerName);
        TextView tvTotalPrice = convertView.findViewById(R.id.tvTotalPrice);
        TextView tvOrderDate = convertView.findViewById(R.id.tvOrderDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
// Thiết lập định dạng số tiền
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        decimalFormat.setDecimalSeparatorAlwaysShown(false);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);

        // Định dạng số tiền
        String formattedPrice = decimalFormat.format(order.getTotalPrice());

        tvCustomerName.setText("Khách hàng: " + order.getCustomerName());
        tvTotalPrice.setText("Tổng tiền: " + formattedPrice + " VNĐ");
        tvOrderDate.setText("Ngày: " + dateFormat.format(order.getDate()));

        return convertView;
    }
}
