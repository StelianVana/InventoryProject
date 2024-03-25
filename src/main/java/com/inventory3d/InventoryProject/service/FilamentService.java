package com.inventory3d.InventoryProject.service;

import com.inventory3d.InventoryProject.entity.FilamentSpool;
import com.inventory3d.InventoryProject.model.FilamentSpoolDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public interface FilamentService {

    void deleteSpool(Integer id);

    void saveSpool(FilamentSpoolDTO filamentSpoolDTO);

    List<FilamentSpool> getAllSpools();

    FilamentSpoolDTO findByBrand(String brand);

    FilamentSpoolDTO findByBrandAndType(String brand, String type);

    void updateQuantity(Integer id, Integer quantity);

    FilamentSpoolDTO findByAllAttributes(String brand, String color, String type, Double price, Integer quantity);

    void getFilamentSpool(final int count);

    Optional<FilamentSpoolDTO> findById(Integer id);

    @Transactional
    void editSpool(Integer id, FilamentSpoolDTO updatedFilamentSpoolDTO);
}
