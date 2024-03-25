package com.inventory3d.InventoryProject.service;

import com.inventory3d.InventoryProject.entity.FilamentSpool;
import com.inventory3d.InventoryProject.mapper.FilamentSpoolMapper;
import com.inventory3d.InventoryProject.model.FilamentSpoolDTO;
import com.inventory3d.InventoryProject.repository.FilamentSpoolRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FilamentServiceIMPL implements FilamentService {

    private final FilamentSpoolMapper filamentSpoolMapper;
    private final FilamentSpoolRepo filamentSpoolRepo;


    @Override
    public void deleteSpool(Integer id) {
        filamentSpoolRepo.deleteById(id);
    }

    @Override
    public void saveSpool(FilamentSpoolDTO filamentSpoolDTO) {
        FilamentSpool spool = filamentSpoolMapper.DTOToEntity(filamentSpoolDTO);
        filamentSpoolRepo.save(spool);

    }

    @Override
    public List<FilamentSpool> getAllSpools() {
        return filamentSpoolRepo.findAll();
    }

    @Override
    public FilamentSpoolDTO findByBrand(String brand) {
        FilamentSpool spool = filamentSpoolRepo.findByBrand(brand);
        if (spool != null) {
            return filamentSpoolMapper.entityToDto(spool);
        }
        return null;
    }

    @Override
    public FilamentSpoolDTO findByBrandAndType(String brand, String type) {
        FilamentSpool filamentSpool = filamentSpoolRepo.findByBrandAndType(brand, type);
        return filamentSpool != null ? filamentSpoolMapper.entityToDto(filamentSpool) : null;
    }

    @Override
    public void updateQuantity(Integer id, Integer quantity) {
        FilamentSpool spool = filamentSpoolRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filament spool not found with id " + id));
        spool.setQuantity(quantity);
        filamentSpoolRepo.save(spool);
    }

    @Override
    public FilamentSpoolDTO findByAllAttributes(String brand, String color, String type, Double price, Integer quantity) {
        FilamentSpool spool = filamentSpoolRepo.findByAllAttributes(brand, color, type, price, quantity);
        return spool != null ? filamentSpoolMapper.entityToDto(spool) : null;
    }

    @Override
    public void getFilamentSpool(int count) {

    }

    @Override
    public Optional<FilamentSpoolDTO> findById(Integer id) {
        var optionalSpool = filamentSpoolRepo.findById(id);
        return optionalSpool.map(filamentSpoolMapper::entityToDto);
    }


    @Override
    @Transactional
    public void editSpool(Integer id, FilamentSpoolDTO updatedFilamentSpoolDTO) {
        List<FilamentSpool> matchingSpools = filamentSpoolRepo.findByAllAttributesExceptQuantity(
                updatedFilamentSpoolDTO.getBrand(), updatedFilamentSpoolDTO.getColor(), updatedFilamentSpoolDTO.getType(), updatedFilamentSpoolDTO.getPrice());

        if (!matchingSpools.isEmpty()) {
            // Optional: Decide your logic if multiple spools match
            FilamentSpool matchingSpool = matchingSpools.get(0);

            if (!matchingSpool.getId().equals(id)) {
                // If the found spool is not the one being edited, merge quantities
                matchingSpool.setQuantity(matchingSpool.getQuantity() + updatedFilamentSpoolDTO.getQuantity());
                filamentSpoolRepo.save(matchingSpool); // Save the merged spool
                filamentSpoolRepo.deleteById(id); // Delete the original spool
                return;
            }
        }

        // If no matching spool found, or it's the same spool, update it
        updatedFilamentSpoolDTO.setId(id);
        FilamentSpool spoolToUpdate = filamentSpoolMapper.DTOToEntity(updatedFilamentSpoolDTO);
        filamentSpoolRepo.save(spoolToUpdate);
}
}
