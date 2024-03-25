package com.inventory3d.InventoryProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Filament")
public class FilamentSpool  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "color")
    private String color;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name="quantity",nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "printer_id")
    private Printer printer;

}

