package com.doanCuoiKi.FacilityStore_Management;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FacilityDAO {

    @Insert
    void insertFacility(Facility facility);

    // Cập nhật thiết bị theo ID
    @Query("UPDATE facilities SET name = :name, quantity = :quantity, `condition` = :condition, cost = :cost WHERE id = :facilityId")
    void updateFacilityById(int facilityId, String name, int quantity, String condition, double cost);

    // Xóa thiết bị theo ID
    @Query("DELETE FROM facilities WHERE id = :facilityId")
    void deleteFacilityById(int facilityId);

    // Lấy toàn bộ danh Cơ sở vật chất thiết bị
    @Query("SELECT * FROM facilities")
    List<Facility> getAllFacilities();

    // Tìm kiếm thiết bị theo tên
    @Query("SELECT * FROM facilities WHERE name LIKE :query")
    List<Facility> searchFacilities(String query);

    // Lấy thiết bị theo ID
    @Query("SELECT * FROM facilities WHERE id = :facilityId")
    Facility getFacilityById(int facilityId);

    @Query("DELETE FROM order_details WHERE FacilityId = :FacilityId")
    void deleteOrderDetailsByBookId(int FacilityId);

}
