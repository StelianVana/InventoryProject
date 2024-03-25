package com.inventory3d.InventoryProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Extra")
public class Extra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "category")
    private String category;

    @Column(name = "type")
    private String name;

    @Column(name="quantity",nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;
}
