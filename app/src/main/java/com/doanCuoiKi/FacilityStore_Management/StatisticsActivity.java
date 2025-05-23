package com.doanCuoiKi.FacilityStore_Management;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Calendar;

public class StatisticsActivity extends AppCompatActivity {
    private TextView tvHighestOrder, tvTopFacilitys, tvMonthlyStats;
    private FacilityDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvHighestOrder = findViewById(R.id.tvHighestOrder);
        tvTopFacilitys = findViewById(R.id.tvTopFacilitys);
        tvMonthlyStats = findViewById(R.id.tvMonthlyStats); // Thay thế thống kê khách hàng bằng thống kê tháng
        db = FacilityDatabase.getDatabase(this);

        loadStatistics();
    }

    private void loadStatistics() {
        new Thread(() -> {
            Order highestOrder = db.orderDao().getHighestOrder();
            List<FacilityStats> topFacilitys = db.orderDao().getTop5Facility();
            MonthlyStats monthlyStats = db.orderDao().getMonthlyStats(getStartOfMonth(), getEndOfMonth());

            // Thiết lập định dạng số với dấu phân cách là dấu "."
            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            decimalFormat.setDecimalSeparatorAlwaysShown(false);

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            decimalFormat.setDecimalFormatSymbols(symbols);

            runOnUiThread(() -> {
                String highestOrderText = (highestOrder != null)
                        ? decimalFormat.format(highestOrder.getTotalPrice()) + " VNĐ"
                        : "N/A";
                tvHighestOrder.setText("Đơn hàng giá trị cao nhất: " + highestOrderText);

                StringBuilder FacilityStatsText = new StringBuilder("Top 5 Cơ sở vật chất bán chạy:\n");
                for (FacilityStats b : topFacilitys) {
                    FacilityStatsText.append("ID: ").append(b.FacilityId).append(" - Số lượng: ").append(b.total).append("\n");
                }
                tvTopFacilitys.setText(FacilityStatsText.toString());

                if (monthlyStats != null) {
                    String formattedRevenue = decimalFormat.format(monthlyStats.totalRevenue);
                    tvMonthlyStats.setText("Thống kê tháng:\nTổng số Cơ sở vật chất đã bán: " + monthlyStats.totalFacilitysSold
                            + "\nTổng doanh thu: " + formattedRevenue + " VNĐ");
                } else {
                    tvMonthlyStats.setText("Chưa có dữ liệu trong tháng này.");
                }
            });
        }).start();
    }

    private long getStartOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    private long getEndOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTimeInMillis();
    }
}
