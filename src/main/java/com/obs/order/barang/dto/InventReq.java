package com.obs.order.barang.dto;

import com.obs.order.barang.model.Item;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventReq implements Serializable {
    private static final long serialVersionUID = 8261062251707926843L;

    private long id;
    private int qty;
    private String type;
    private Item item;
}
