package com.obs.order.barang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail implements Serializable {
    private static final long serialVersionUID = 7859940117469970809L;

    private String message;

}
