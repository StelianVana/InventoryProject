package com.inventory3d.InventoryProject.mapper;

import com.inventory3d.InventoryProject.entity.Extra;
import com.inventory3d.InventoryProject.entity.FilamentSpool;
import com.inventory3d.InventoryProject.model.ExtraDTO;
import com.inventory3d.InventoryProject.model.FilamentSpoolDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ExtraMapper {

    ExtraDTO entityToDto(Extra extra);

    Extra DTOToEntity(ExtraDTO extraDTO);
}
