package com.doanCuoiKi.FacilityStore_Management;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DeleteFacilityActivity extends AppCompatActivity {
    private ListView lvFacilitys;
    private Button btnDeleteFacility;
    private FacilityDatabase db;
    private List<Facility> FacilityList;
    private ArrayAdapter<String> adapter;
    private int selectedFacilityId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_facility);

        lvFacilitys = findViewById(R.id.lvFacilitys);
        btnDeleteFacility = findViewById(R.id.btnDeleteFacility);
        db = FacilityDatabase.getDatabase(this);

        loadFacilitys();

        lvFacilitys.setOnItemClickListener((parent, view, position, id) -> {
            selectedFacilityId = FacilityList.get(position).getId();
            Toast.makeText(DeleteFacilityActivity.this, "Đã chọn Cơ sở vật chất: " + FacilityList.get(position).getName(), Toast.LENGTH_SHORT).show();
        });

        btnDeleteFacility.setOnClickListener(view -> {
            if (selectedFacilityId != -1) {
                deleteFacility(selectedFacilityId);
            } else {
                Toast.makeText(this, "Vui lòng chọn Cơ sở vật chất để xóa!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFacilitys() {
        new Thread(() -> {
            FacilityList = db.FacilityDao().getAllFacilities();
            runOnUiThread(() -> {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                        FacilityList.stream().map(Facility::getName).toArray(String[]::new));
                lvFacilitys.setAdapter(adapter);
            });
        }).start();
    }

    private void deleteFacility(int FacilityId) {
        new Thread(() -> {
            db.FacilityDao().deleteOrderDetailsByBookId(FacilityId); // Xóa đơn hàng chứa Cơ sở vật chất
            db.FacilityDao().deleteFacilityById(FacilityId); // Xóa Cơ sở vật chất

            runOnUiThread(() -> {
                Toast.makeText(DeleteFacilityActivity.this, "Xóa Cơ sở vật chất thành công!", Toast.LENGTH_SHORT).show();
                loadFacilitys(); // Cập nhật danh Cơ sở vật chất sau khi xóa
                finish(); // Quay lại trang chính
            });
        }).start();
    }


}