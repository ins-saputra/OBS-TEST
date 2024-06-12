package com.obs.order.barang.dto;

import com.obs.order.barang.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReq implements Serializable {
    private static final long serialVersionUID = 7311389380422537328L;

    private Long id;
    private int qty;
    private Item item;
}
