package com.inventory3d.InventoryProject.repository;

import com.inventory3d.InventoryProject.entity.Printer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrinterRepo extends JpaRepository<Printer, Integer> {
    boolean existsByName(String name);
}
