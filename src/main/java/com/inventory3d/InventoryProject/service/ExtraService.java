package com.inventory3d.InventoryProject.service;

import com.inventory3d.InventoryProject.entity.Extra;
import com.inventory3d.InventoryProject.model.ExtraDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface ExtraService {

    void deleteExtra(Integer id);

    void saveExtra(ExtraDTO extraDTO);

    ExtraDTO findByBrand(String brand);

    ExtraDTO findByBrandAndCategory(String brand, String category);

    ExtraDTO findByAllAttributes(String brand, String category, String name, Double price, Integer quantity);

    void updateQuantity(Integer id, Integer quantity);

    List<Extra> getAllExtras();

    void getExtra(final int count);

    Optional<ExtraDTO> findById(Integer id);

    void editExtra(Integer id, ExtraDTO updatedExtraDTO);
}
