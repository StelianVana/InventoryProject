package com.inventory3d.InventoryProject.service;

import com.inventory3d.InventoryProject.entity.Extra;
import com.inventory3d.InventoryProject.mapper.ExtraMapper;
import com.inventory3d.InventoryProject.model.ExtraDTO;
import com.inventory3d.InventoryProject.repository.ExtraRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExtraServiceImpl implements ExtraService {

    private final ExtraMapper extraMapper;
    private final ExtraRepo extraRepo;

    @Override
    public void deleteExtra(Integer id) {
        extraRepo.deleteById(id);
    }

    @Override
    public void saveExtra(ExtraDTO extraDTO) {
        Extra extra = extraMapper.DTOToEntity(extraDTO);
        extraRepo.save(extra);

    }

    @Override
    public ExtraDTO findByBrand(String brand) {
        Extra extra = extraRepo.findByBrand(brand);
        if (extra != null) {
            return extraMapper.entityToDto(extra);
        }
        return null;
    }

    @Override
    public ExtraDTO findByBrandAndCategory(String brand, String category) {
        Extra extra = extraRepo.findByBrandAndCategory(brand, category);
        return extra != null ? extraMapper.entityToDto(extra) : null;
    }

    @Override
    public ExtraDTO findByAllAttributes(String brand, String category, String name, Double price, Integer quantity) {
        Extra extra = extraRepo.findByAllAttributes(brand, category, name, price, quantity);
        return extra != null ? extraMapper.entityToDto(extra) : null;
    }

    @Override
    public void updateQuantity(Integer id, Integer quantity) {
        Extra extra = extraRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filament spool not found with id " + id));
        extra.setQuantity(quantity);
        extraRepo.save(extra);
    }

    @Override
    public List<Extra> getAllExtras() {
        return extraRepo.findAll();
    }

    @Override
    public void getExtra(int count) {

    }

    @Override
    public Optional<ExtraDTO> findById(Integer id) {
        var optionalExtra = extraRepo.findById(id);
        return optionalExtra.map(extraMapper::entityToDto);
    }

    @Override
    public void editExtra(Integer id, ExtraDTO updatedExtraDTO) {
        List<Extra> matchingExtras = extraRepo.findByAllAttributesExceptQuantity(
                updatedExtraDTO.getBrand(), updatedExtraDTO.getCategory(), updatedExtraDTO.getName(), updatedExtraDTO.getPrice());

        if (!matchingExtras.isEmpty()) {
            Extra matchingExtra = matchingExtras.get(0);
            if (!matchingExtra.getId().equals(id)) {
                matchingExtra.setQuantity(matchingExtra.getQuantity() + updatedExtraDTO.getQuantity());
                extraRepo.save(matchingExtra);
                extraRepo.deleteById(id);
                return;
            }
        }

        updatedExtraDTO.setId(id);
        Extra extraToUpdate = extraMapper.DTOToEntity(updatedExtraDTO);
        extraRepo.save(extraToUpdate);
    }
}
