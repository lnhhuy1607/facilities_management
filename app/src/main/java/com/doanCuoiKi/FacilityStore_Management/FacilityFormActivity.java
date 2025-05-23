package com.doanCuoiKi.FacilityStore_Management;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class FacilityFormActivity extends AppCompatActivity {
    private EditText edtName, edtstatus, edtQuantity, edtPrice;
    private Button btnSaveFacility;
    private FacilityDatabase db;
    private int FacilityId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_form);

        edtName = findViewById(R.id.edtName);
        edtstatus = findViewById(R.id.edtStatus);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtPrice = findViewById(R.id.edtPrice);
        btnSaveFacility = findViewById(R.id.btnSaveFacility);
        db = FacilityDatabase.getDatabase(this);

        if (getIntent().hasExtra("FacilityId")) {
            FacilityId = getIntent().getIntExtra("FacilityId", -1);
            loadFacilityDetails();
        }

        btnSaveFacility.setOnClickListener(view -> saveFacility());
    }

    private void loadFacilityDetails() {
        new Thread(() -> {
            Facility Facility = db.FacilityDao().getFacilityById(FacilityId);
            runOnUiThread(() -> {
                if (Facility != null) {
                    edtName.setText(Facility.getName());
                    edtstatus.setText(Facility.getCondition());
                    edtQuantity.setText(String.valueOf(Facility.getQuantity()));
                    edtPrice.setText(String.valueOf(Facility.getCost()));
                }
            });
        }).start();
    }

    private void saveFacility() {
        new Thread(() -> {
            Facility Facility = new Facility(edtName.getText().toString(), edtstatus.getText().toString(),
                    Integer.parseInt(edtQuantity.getText().toString()),
                    Double.parseDouble(edtPrice.getText().toString()));

            if (FacilityId == -1) {
                db.FacilityDao().insertFacility(Facility);
            } else {
                Facility.setId(FacilityId);
                db.FacilityDao().updateFacilityById(Facility.getId(), Facility.getName(), Facility.getQuantity(),
                        Facility.getCondition(), Facility.getCost());
            }
            finish();
        }).start();
    }
}
