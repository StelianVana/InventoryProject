package com.inventory3d.InventoryProject.controller;

import com.inventory3d.InventoryProject.entity.Printer;
import com.inventory3d.InventoryProject.model.PrinterDTO;
import com.inventory3d.InventoryProject.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PrinterService printerService;

    @GetMapping("/")
    public String home(Model model) {
        List<Printer> printers = printerService.getAllPrinters();
        model.addAttribute("printers", printers);

        // Add logging
        printers.forEach(printer -> System.out.println("Printer: " + printer.getName()));

        return "home";
    }

//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }
}

