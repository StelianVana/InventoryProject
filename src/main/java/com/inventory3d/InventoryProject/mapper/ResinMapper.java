package com.inventory3d.InventoryProject.mapper;

import com.inventory3d.InventoryProject.entity.FilamentSpool;
import com.inventory3d.InventoryProject.entity.Resin;
import com.inventory3d.InventoryProject.model.FilamentSpoolDTO;
import com.inventory3d.InventoryProject.model.ResinDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ResinMapper {

    ResinDTO entityToDto(Resin resin);

    Resin DTOToEntity(ResinDTO resinDTO);
}
