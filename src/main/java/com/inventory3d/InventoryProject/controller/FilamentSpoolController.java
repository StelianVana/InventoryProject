package com.inventory3d.InventoryProject.controller;

import com.inventory3d.InventoryProject.entity.FilamentSpool;
import com.inventory3d.InventoryProject.mapper.FilamentSpoolMapper;
import com.inventory3d.InventoryProject.model.ExtraDTO;
import com.inventory3d.InventoryProject.model.FilamentSpoolDTO;
import com.inventory3d.InventoryProject.model.ResinDTO;
import com.inventory3d.InventoryProject.service.FilamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Configuration
@Controller
@RequiredArgsConstructor
@RequestMapping("/filamentSpools")
public class FilamentSpoolController {

    private final FilamentService filamentService;
    private final FilamentSpoolMapper filamentSpoolMapper;

    @GetMapping("/all")
    public String listFilamentSpool(Model model) {
        List<FilamentSpool> filamentSpools = filamentService.getAllSpools();
        int totalPieces = filamentSpools.size();
        double totalPrice = filamentSpools.stream().mapToDouble(FilamentSpool::getPrice).sum();
        model.addAttribute("filamentSpools", filamentSpools);
        model.addAttribute("totalPieces", totalPieces);
        model.addAttribute("totalPrice", totalPrice);
        return "filamentSpool";
    }
    @GetMapping("/add")
    public String addFilamentSpoolForm(Model model) {
        FilamentSpoolDTO filamentSpool = new FilamentSpoolDTO();
        model.addAttribute("filamentSpool", filamentSpool);
        return "filamentSpool-add";
    }

    @PostMapping("/add")
    public String addedFilamentSpool(@ModelAttribute("filamentSpool") FilamentSpoolDTO filamentSpoolDTO) {
        FilamentSpoolDTO existingSpoolDTO = filamentService.findByAllAttributes(
                filamentSpoolDTO.getBrand(), filamentSpoolDTO.getColor(),
                filamentSpoolDTO.getType(), filamentSpoolDTO.getPrice(),
                filamentSpoolDTO.getQuantity());

        if (existingSpoolDTO != null) {
            existingSpoolDTO.setQuantity(existingSpoolDTO.getQuantity() + filamentSpoolDTO.getQuantity());
            filamentService.saveSpool(existingSpoolDTO);
        } else {
            filamentService.saveSpool(filamentSpoolDTO);
        }
        return "redirect:/filamentSpools/all";
    }

    @PostMapping("/updateQuantity")
    public String updateFilamentSpoolQuantity(@RequestParam("id") Integer id, @RequestParam("quantity") Integer quantity, RedirectAttributes redirectAttributes) {
        try {
            filamentService.updateQuantity(id, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Quantity updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating quantity.");
        }
        return "redirect:/filamentSpools/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteFilamentSpool(@PathVariable Integer id) {
        filamentService.deleteSpool(id);
        return "redirect:/filamentSpools/all";
    }

    @GetMapping("/edit/{id}")
    public String editFilamentSpoolForm(@PathVariable Integer id, Model model) {
        Optional<FilamentSpoolDTO> filamentSpool = filamentService.findById(id);
        model.addAttribute("filamentSpool", filamentSpool.get());
        return "filamentSpool-edit";
    }

    @PostMapping("/edit/{id}")
    public String editFilamentSpool(@PathVariable Integer id, @ModelAttribute("filamentSpool") FilamentSpoolDTO updatedFilamentSpool, RedirectAttributes redirectAttrs) {
        try {
            filamentService.editSpool(id, updatedFilamentSpool);
            redirectAttrs.addFlashAttribute("successMessage", "Spool updated successfully.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "Error updating spool.");
        }
        return "redirect:/filamentSpools/all";
    }
}
