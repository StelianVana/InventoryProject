package com.inventory3d.InventoryProject.controller;

import com.inventory3d.InventoryProject.entity.FilamentSpool;
import com.inventory3d.InventoryProject.entity.Printer;
import com.inventory3d.InventoryProject.entity.Resin;
import com.inventory3d.InventoryProject.model.PrinterDTO;
import com.inventory3d.InventoryProject.repository.PrinterRepo;
import com.inventory3d.InventoryProject.service.FilamentService;
import com.inventory3d.InventoryProject.service.PrinterService;
import com.inventory3d.InventoryProject.service.ResinService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Controller
@RequiredArgsConstructor
@RequestMapping("/printers")
public class PrinterController {

    private final PrinterService printerService;
    private final PrinterRepo printerRepo;

    private static final List<String> types = Arrays.asList("FDM", "SLA");


    @GetMapping("/all")
    public String listPrinters(Model model) {
        List<Printer> printers = printerService.getAllPrinters();
        int totalPieces = printers.size();
        double totalPrice = printers.stream().mapToDouble(Printer::getPrice).sum();

        model.addAttribute("printers", printers);
        model.addAttribute("totalPieces", totalPieces);
        model.addAttribute("totalPrice", totalPrice);

        return "printers";
    }

    @GetMapping("/add")
    public String addPrinterForm(Model model) {
        model.addAttribute("printer", new PrinterDTO());
        return "printers-add";
    }

    @PostMapping("/add")
    public String addPrinter(@ModelAttribute Printer printer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "printers-add";
        }

        if (printerRepo.existsByName(printer.getName())) {
            result.rejectValue("name", "error.printer", "Printer with this name already exists. Please choose a different name.");
            return "printers-add";
        }

        try {
            printerRepo.save(printer);
        } catch (DataIntegrityViolationException ex) {
            // Handle other constraint violations if any
            return "error-page"; // Redirect to a generic error page
        }

        return "redirect:/printers/all";
    }



    @GetMapping("/delete/{id}")
    public String deletePrinter(@PathVariable Integer id) {
        printerService.deletePrinter(id);
        return "redirect:/printers/all";
    }

    @GetMapping("/edit/{id}")
    public String editPrinterForm(@PathVariable Integer id, Model model) {
        PrinterDTO printer = printerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Printer not found"));

        model.addAttribute("printer", printer);

        return "printers-edit";
    }



    @PostMapping("/edit/{id}")
    public String editPrinter(@PathVariable Integer id, @ModelAttribute("printer") PrinterDTO updatedPrinter) {
        updatedPrinter.setId(id);
        printerService.updatePrinter(updatedPrinter);
        return "redirect:/printers/all";
    }

}

