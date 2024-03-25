package com.inventory3d.InventoryProject.repository;

import com.inventory3d.InventoryProject.entity.FilamentSpool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilamentSpoolRepo extends JpaRepository<FilamentSpool, Integer> {
    List<FilamentSpool> findByType(String type);
    FilamentSpool findByBrandAndType(String brand, String type);
    FilamentSpool findByBrand(String brand);

    @Query("SELECT f FROM FilamentSpool f WHERE f.brand = :brand AND f.color = :color AND f.type = :type AND f.price = :price AND f.quantity = :quantity")
    FilamentSpool findByAllAttributes(@Param("brand") String brand,
                                      @Param("color") String color,
                                      @Param("type") String type,
                                      @Param("price") Double price,
                                      @Param("quantity") Integer quantity);

    @Query("SELECT f FROM FilamentSpool f WHERE f.brand = :brand AND f.color = :color AND f.type = :type AND f.price = :price")
    List<FilamentSpool> findByAllAttributesExceptQuantity(@Param("brand") String brand,
                                                    @Param("color") String color,
                                                    @Param("type") String type,
                                                    @Param("price") Double price);
}

