package com.inventory3d.InventoryProject.service;

import com.inventory3d.InventoryProject.entity.Printer;
import com.inventory3d.InventoryProject.mapper.PrinterMapper;
import com.inventory3d.InventoryProject.model.PrinterDTO;
import com.inventory3d.InventoryProject.repository.PrinterRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PrinterServiceImpl implements PrinterService {
    private final PrinterRepo printerRepo;
    private final PrinterMapper printerMapper;

    @Override
    public void deletePrinter(Integer id) {
        printerRepo.deleteById(id);

    }

    @Override
    public void save(PrinterDTO printerDTO) {
        Printer printer = printerMapper.DTOToEntity(printerDTO);
        printerRepo.save(printer);

    }

    @Override
    public List<Printer> getAllPrinters(){
        return printerRepo.findAll();
    }

    @Override
    public Optional<PrinterDTO> findById(Integer id) {
        var optionalPrinter = printerRepo.findById(id);
        return optionalPrinter.map(printerMapper::entityToDto);
    }

    @Override
    public void updatePrinter(PrinterDTO updatedPrinter) {
        // Assuming you have a method to find the printer by its ID
        Printer existingPrinter = printerRepo.findById(updatedPrinter.getId())
                .orElseThrow(() -> new RuntimeException("Printer not found"));

        // Update the existing printer with the fields from the updated printer DTO
        existingPrinter.setName(updatedPrinter.getName());
        existingPrinter.setBrand(updatedPrinter.getBrand());
        existingPrinter.setType(updatedPrinter.getType());
        existingPrinter.setPrice(updatedPrinter.getPrice());

        // Save the updated printer
        printerRepo.save(existingPrinter);
    }


}
