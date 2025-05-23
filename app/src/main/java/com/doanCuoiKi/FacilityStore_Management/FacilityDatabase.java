package com.doanCuoiKi.FacilityStore_Management;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;



@Database(entities = {Facility.class, Order.class, OrderDetail.class}, version = 2, exportSchema = false)
public abstract class FacilityDatabase extends RoomDatabase {
    private static volatile FacilityDatabase INSTANCE;

    public abstract FacilityDAO FacilityDao();
    public abstract OrderDAO orderDao();

    public static FacilityDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FacilityDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FacilityDatabase.class, "Facility_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
