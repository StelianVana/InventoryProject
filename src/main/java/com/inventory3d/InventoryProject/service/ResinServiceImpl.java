package com.inventory3d.InventoryProject.service;

import com.inventory3d.InventoryProject.entity.Resin;
import com.inventory3d.InventoryProject.mapper.ResinMapper;
import com.inventory3d.InventoryProject.model.ResinDTO;
import com.inventory3d.InventoryProject.repository.ResinRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResinServiceImpl implements ResinService {

    private final ResinRepo resinRepo;
    private final ResinMapper resinMapper;

    @Override
    public void deleteResin(Integer id) {
        resinRepo.deleteById(id);

    }

    @Override
    public void saveResin(ResinDTO resinDTO) {
        Resin resin = resinMapper.DTOToEntity(resinDTO);
        resinRepo.save(resin);

    }

    @Override
    public ResinDTO findByBrandAndType(String brand, String type) {
        Resin resin = resinRepo.findByBrandAndType(brand, type);
        return resin != null ? resinMapper.entityToDto(resin) : null;
    }

    @Override
    public ResinDTO findByAllAttributes(String brand, String color, String type, Double price, Integer quantity) {
        Resin resin = resinRepo.findByAllAttributes(brand, color, type, price, quantity);
        return resin != null ? resinMapper.entityToDto(resin) : null;
    }

    @Override
    public ResinDTO findByBrand(String brand) {
        Resin resin = resinRepo.findByBrand(brand);
        if (resin != null) {
            return resinMapper.entityToDto(resin);
        }
        return null;
    }

    @Override
    public void updateQuantity(Integer id, Integer quantity) {
        Resin resin = resinRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filament spool not found with id " + id));
        resin.setQuantity(quantity);
        resinRepo.save(resin);
    }

    @Override
    public List<Resin> getAllResins() {
        return resinRepo.findAll();
    }


    @Override
    public Optional<ResinDTO> findById(Integer id) {
        var optionalResin = resinRepo.findById(id);
        return optionalResin.map(resinMapper::entityToDto);
    }

    @Override
    public void editResin(Integer id, ResinDTO updatedResinDTO) {
        List<Resin> matchingResins = resinRepo.findByAllAttributesExceptQuantity(
                updatedResinDTO.getBrand(), updatedResinDTO.getColor(), updatedResinDTO.getType(), updatedResinDTO.getPrice());

        if (!matchingResins.isEmpty()) {
            Resin matchingResin = matchingResins.get(0);
            if (!matchingResin.getId().equals(id)) {
                matchingResin.setQuantity(matchingResin.getQuantity() + updatedResinDTO.getQuantity());
                resinRepo.save(matchingResin);
                resinRepo.deleteById(id);
                return;
            }
        }

        updatedResinDTO.setId(id);
        Resin resinToUpdate = resinMapper.DTOToEntity(updatedResinDTO);
        resinRepo.save(resinToUpdate);
    }
}
