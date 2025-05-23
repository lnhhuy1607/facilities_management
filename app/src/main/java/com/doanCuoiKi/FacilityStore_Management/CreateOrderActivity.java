package com.doanCuoiKi.FacilityStore_Management;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class CreateOrderActivity extends AppCompatActivity {
    private EditText edtCustomerName, edtQuantity;
    private Spinner spnFacility;
    private Button btnAddToOrder, btnSaveOrder;
    private TextView tvTotalPrice;
    private ListView lvOrderDetails;

    private FacilityDatabase db;
    private List<Facility> FacilityList;
    private List<OrderDetail> orderDetails = new ArrayList<>();
    private ArrayAdapter<String> FacilityAdapter, orderAdapter;
    private double totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtQuantity = findViewById(R.id.edtQuantity);
        spnFacility = findViewById(R.id.spnFacility);
        btnAddToOrder = findViewById(R.id.btnAddToOrder);
        btnSaveOrder = findViewById(R.id.btnSaveOrder);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        lvOrderDetails = findViewById(R.id.lvOrderDetails);

        db = FacilityDatabase.getDatabase(this);
        loadFacilitys();

        btnAddToOrder.setOnClickListener(view -> addToOrder());
        btnSaveOrder.setOnClickListener(view -> saveOrder());
    }

    private void loadFacilitys() {
        new Thread(() -> {
            FacilityList = db.FacilityDao().getAllFacilities();
            runOnUiThread(() -> {
                List<String> FacilityTitles = new ArrayList<>();
                for (Facility Facility : FacilityList) {
                    FacilityTitles.add(Facility.getName() + " (Còn: " + Facility.getQuantity() + ")");
                }
                FacilityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FacilityTitles);
                FacilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnFacility.setAdapter(FacilityAdapter);
            });
        }).start();
    }

    private void addToOrder() {
        int position = spnFacility.getSelectedItemPosition();
        int quantity = Integer.parseInt(edtQuantity.getText().toString());

        if (position < 0 || quantity <= 0) return;

        Facility selectedFacility = FacilityList.get(position);
        if (quantity > selectedFacility.getQuantity()) {
            edtQuantity.setError("Số lượng vượt quá tồn kho!");
            return;
        }

        double itemTotal = selectedFacility.getCost() * quantity;
        totalPrice += itemTotal;
        // Định dạng số tiền
        String formattedTotalPrice = formatCurrency(totalPrice);
        tvTotalPrice.setText("Tổng tiền: " + formattedTotalPrice + " VNĐ");
        orderDetails.add(new OrderDetail(0, selectedFacility.getId(), quantity, itemTotal));
        updateOrderList();
    }

    private void updateOrderList() {
        List<String> orderSummary = new ArrayList<>();
        for (OrderDetail detail : orderDetails) {
            orderSummary.add("ID Cơ sở vật chất: " + detail.getFacilityId() + " - Số lượng: " + detail.getQuantity());
        }
        orderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderSummary);
        lvOrderDetails.setAdapter(orderAdapter);
    }

    private void saveOrder() {
        String customerName = edtCustomerName.getText().toString().trim();
        if (customerName.isEmpty() || orderDetails.isEmpty()) return;

        new Thread(() -> {
            long orderId = db.orderDao().insertOrder(new Order(customerName, totalPrice, System.currentTimeMillis()));

            for (OrderDetail detail : orderDetails) {
                detail.setOrderId((int) orderId);
                db.orderDao().insertOrderDetail(detail);

                // Cập nhật số lượng Cơ sở vật chất trong kho
                Facility Facility = db.FacilityDao().getFacilityById(detail.getFacilityId());
                Facility.setQuantity(Facility.getQuantity() - detail.getQuantity());
                db.FacilityDao().updateFacilityById(Facility.getId(), Facility.getName(),  Facility.getQuantity(), Facility.getCondition(),Facility.getCost());
            }

            finish();
        }).start();
    }
    private String formatCurrency(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        decimalFormat.setDecimalSeparatorAlwaysShown(false);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);

        return decimalFormat.format(amount);
    }
}
