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
@Table(name = "inventory")
public class Inventory implements Serializable {
    private static final long serialVersionUID = 748062563735125503L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "qty")
    private int qty;

    @Column(name = "type")
    private String type; // T for Top Up, W for Withdrawal

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;


}
