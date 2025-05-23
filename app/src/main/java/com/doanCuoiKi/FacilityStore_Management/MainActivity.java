package com.doanCuoiKi.FacilityStore_Management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnManageFacilitys, btnManageOrders, btnStatistics, btnCreateOrder,btnDeleteFacility, btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnManageFacilitys = findViewById(R.id.btnManageFacilitys);
        btnManageOrders = findViewById(R.id.btnManageOrders);
        btnStatistics = findViewById(R.id.btnStatistics);

        btnManageFacilitys.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, FacilityListActivity.class)));

        btnManageOrders.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, OrderListActivity.class)));

        btnStatistics.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, com.doanCuoiKi.FacilityStore_Management.StatisticsActivity.class)));


        btnCreateOrder = findViewById(R.id.btnCreateOrder);
        btnCreateOrder.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, CreateOrderActivity.class)));

        btnDeleteFacility = findViewById(R.id.btnDeleteFacility);
        btnDeleteFacility.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, DeleteFacilityActivity.class)));

        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, LoginActivity.class)));
    }


}
