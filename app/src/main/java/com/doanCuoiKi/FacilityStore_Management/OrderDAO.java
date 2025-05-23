package com.doanCuoiKi.FacilityStore_Management;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface OrderDAO {
    @Insert
    long insertOrder(Order order);

    @Insert
    void insertOrderDetail(OrderDetail orderDetail);

    @Query("SELECT * FROM orders ORDER BY date DESC")
    List<Order> getAllOrders();

    // Đơn hàng có tổng tiền cao nhất
    @Query("SELECT * FROM orders ORDER BY totalPrice DESC LIMIT 1")
    Order getHighestOrder();

    // Thống kê 5 cuốn Cơ sở vật chất bán chạy nhất
    @Query("SELECT FacilityId, SUM(quantity) as total FROM order_details GROUP BY FacilityId ORDER BY total DESC LIMIT 5")
    List<FacilityStats> getTop5Facility();

    // Thống kê 5 khách hàng mua nhiều Cơ sở vật chất nhất
    @Query("SELECT customerName, SUM(quantity) as total FROM orders INNER JOIN order_details ON orders.id = order_details.orderId GROUP BY customerName ORDER BY total DESC LIMIT 5")
    List<CustomerStats> getTop5Customers();


    @Query("SELECT SUM(od.quantity) AS totalFacilitysSold, SUM(o.totalPrice) AS totalRevenue " +
            "FROM orders o " +
            "JOIN order_details od ON o.id = od.orderId " +
            "WHERE o.date >= :startOfMonth AND o.date < :endOfMonth")
    MonthlyStats getMonthlyStats(long startOfMonth, long endOfMonth);

}
