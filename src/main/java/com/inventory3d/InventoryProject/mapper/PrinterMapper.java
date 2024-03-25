package com.inventory3d.InventoryProject.mapper;

import com.inventory3d.InventoryProject.entity.Printer;
import com.inventory3d.InventoryProject.model.PrinterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)

public interface PrinterMapper {

    PrinterDTO entityToDto(Printer printer);

    Printer DTOToEntity(PrinterDTO printerDTO);
}
