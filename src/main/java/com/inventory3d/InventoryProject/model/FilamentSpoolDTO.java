package com.inventory3d.InventoryProject.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FilamentSpoolDTO {

    private Integer id;

    private String brand;

    private String color;

    private String type;

    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;
}
