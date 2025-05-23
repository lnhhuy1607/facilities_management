package com.doanCuoiKi.FacilityStore_Management;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.stream.Collectors;

public class FacilityListActivity extends AppCompatActivity {
    private ListView lvFacilitys;
    private EditText edtSearchFacility;
    private Button btnSearchFacility, btnAddFacility;
    private FacilityDatabase db;
    private ArrayAdapter<String> adapter;
    private List<Facility> FacilityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_list);

        lvFacilitys = findViewById(R.id.lvFacility);
        edtSearchFacility = findViewById(R.id.edtSearchFacility);
        btnSearchFacility = findViewById(R.id.btnSearchFacility);
        btnAddFacility = findViewById(R.id.btnAddFacility);
        db = FacilityDatabase.getDatabase(this);

        loadFacilitys("");

        btnSearchFacility.setOnClickListener(view -> {
            String query = edtSearchFacility.getText().toString().trim();
            loadFacilitys(query);
        });

        btnAddFacility.setOnClickListener(view ->
                startActivity(new Intent(FacilityListActivity.this, FacilityFormActivity.class)));

        lvFacilitys.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(FacilityListActivity.this, FacilityFormActivity.class);
            intent.putExtra("FacilityId", FacilityList.get(position).getId());
            startActivity(intent);
        });
    }

    private void loadFacilitys(String searchQuery) {
        new Thread(() -> {
            if (searchQuery.isEmpty()) {
                FacilityList = db.FacilityDao().getAllFacilities();
            } else {
                FacilityList = db.FacilityDao().searchFacilities("%" + searchQuery + "%");
            }

            runOnUiThread(() -> {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                        FacilityList.stream().map(Facility::getName).collect(Collectors.toList()));
                lvFacilitys.setAdapter(adapter);
            });
        }).start();
    }
}