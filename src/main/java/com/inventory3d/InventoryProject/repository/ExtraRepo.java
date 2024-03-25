package com.inventory3d.InventoryProject.repository;

import com.inventory3d.InventoryProject.entity.Extra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExtraRepo extends JpaRepository<Extra, Integer> {

    Extra findByBrandAndCategory(String brand, String category);

    Extra findByBrand(String brand);

    @Query("SELECT e FROM Extra e WHERE e.brand = :brand AND e.category = :category AND e.name = :name AND e.price = :price AND e.quantity = :quantity ")
    Extra findByAllAttributes(@Param("brand") String brand,
                              @Param("category") String category,
                              @Param("name") String name,
                              @Param("price") Double price,
                              @Param("quantity") Integer quantity);

    @Query("SELECT e FROM Extra e WHERE e.brand = :brand AND e.category = :category AND e.name = :name AND e.price = :price")
    List<Extra>findByAllAttributesExceptQuantity(@Param("brand") String brand,
                                                 @Param("category") String category,
                                                 @Param("name") String name,
                                                 @Param("price") Double price);
}

