package com.inventory3d.InventoryProject.mapper;

import com.inventory3d.InventoryProject.entity.FilamentSpool;
import com.inventory3d.InventoryProject.model.FilamentSpoolDTO;
import com.inventory3d.InventoryProject.model.PrinterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FilamentSpoolMapper {

    FilamentSpoolDTO entityToDto(FilamentSpool filamentSpool);

    FilamentSpool DTOToEntity(FilamentSpoolDTO filamentSpoolDTO);
}
