package com.obs.order.barang.repository;

import com.obs.order.barang.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT * FROM item i ORDER BY i.id ASC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Item> findItemsWithPagination(@Param("limit") int limit, @Param("offset") int offset);

    boolean existsByName(String name);
}