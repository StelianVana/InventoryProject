package com.inventory3d.InventoryProject.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class PrinterDTO {

    private Integer id;

    private String name;

    private String brand;

    private String type;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;
}
