package com.inventory3d.InventoryProject.repository;

import com.inventory3d.InventoryProject.entity.Extra;
import com.inventory3d.InventoryProject.entity.Resin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResinRepo extends JpaRepository<Resin, Integer> {
    List<Resin> findByType(String type);

    Resin findByBrandAndType(String brand, String type);

    Resin findByBrand(String brand);

    @Query("SELECT r FROM Resin r WHERE r.brand = :brand AND r.color = :color AND r.type = :type AND r.price = :price AND r.quantity = :quantity")
    Resin findByAllAttributes(@Param("brand") String brand,
                              @Param("color") String color,
                              @Param("type") String type,
                              @Param("price") Double price,
                              @Param("quantity") Integer quantity);

    @Query("SELECT r FROM Resin r WHERE r.brand = :brand AND r.color = :color AND r.type = :type AND r.price = :price")
    List<Resin> findByAllAttributesExceptQuantity(@Param("brand") String brand,
                                            @Param("color") String color,
                                            @Param("type") String type,
                                            @Param("price") Double price);
}

