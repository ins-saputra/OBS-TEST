package com.obs.order.barang.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 748062563735125501L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_order")
    private Long id;

    @Column(name = "qty")
    private int qty;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}
