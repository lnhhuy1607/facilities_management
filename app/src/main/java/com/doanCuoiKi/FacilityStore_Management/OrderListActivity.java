package com.doanCuoiKi.FacilityStore_Management;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    private ListView lvOrders;
    private FacilityDatabase db;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        lvOrders = findViewById(R.id.lvOrders);
        db = FacilityDatabase.getDatabase(this);

        loadOrders();
    }

    private void loadOrders() {
        new Thread(() -> {
            orderList = db.orderDao().getAllOrders();
            runOnUiThread(() -> {
                OrderAdapter adapter = new OrderAdapter(this, orderList);
                lvOrders.setAdapter(adapter);
            });
        }).start();
    }
}
