package com.inventory3d.InventoryProject.service;

import com.inventory3d.InventoryProject.entity.FilamentSpool;
import com.inventory3d.InventoryProject.entity.Resin;
import com.inventory3d.InventoryProject.model.FilamentSpoolDTO;
import com.inventory3d.InventoryProject.model.ResinDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface ResinService {

    void deleteResin(Integer id);

    void saveResin(ResinDTO resinDTO);

    ResinDTO findByBrandAndType(String brand, String type);

    ResinDTO findByAllAttributes(String brand, String color, String type, Double price, Integer quantity);

    ResinDTO findByBrand(String brand);

    void updateQuantity(Integer id, Integer quantity);

    List<Resin> getAllResins();

    Optional<ResinDTO> findById(Integer id);

    void editResin(Integer id, ResinDTO updatedResinDTO);
}
