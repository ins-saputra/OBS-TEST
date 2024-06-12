package com.obs.order.barang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemReq implements Serializable {
    private static final long serialVersionUID = 2633652332084778573L;

    private long id;
    private String name;
    private BigDecimal price;

}
