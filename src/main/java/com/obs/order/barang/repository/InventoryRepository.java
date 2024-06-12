package com.obs.order.barang.repository;

import com.obs.order.barang.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query(value = "SELECT COALESCE(SUM(qty), 0) FROM inventory WHERE item_id = :itemId AND type = :type", nativeQuery = true)
    int sumQtyByItemIdAndType(@Param("itemId") Long itemId, @Param("type") String type);

}
