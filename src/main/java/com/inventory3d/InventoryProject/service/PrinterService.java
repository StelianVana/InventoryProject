package com.inventory3d.InventoryProject.service;

import com.inventory3d.InventoryProject.entity.Printer;
import com.inventory3d.InventoryProject.model.PrinterDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface PrinterService {

    void deletePrinter(Integer id);

    void save(PrinterDTO printerDTO);

    List<Printer> getAllPrinters();

    Optional<PrinterDTO> findById(Integer id);

    void updatePrinter(PrinterDTO updatedPrinter);
}
