package com.inventory3d.InventoryProject.controller;

import com.inventory3d.InventoryProject.entity.Extra;
import com.inventory3d.InventoryProject.model.ExtraDTO;
import com.inventory3d.InventoryProject.repository.ExtraRepo;
import com.inventory3d.InventoryProject.service.ExtraService;
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
@RequestMapping("/extras")
public class ExtraController {

    private final ExtraService extraService;
    private final ExtraRepo extraRepo;


    @GetMapping("/all")
    public String listExtra(Model model) {
        List<Extra> extras = extraService.getAllExtras();
        int totalPieces = extras.size();
        double totalPrice = extras.stream().mapToDouble(Extra::getPrice).sum();
        model.addAttribute("extras", extras);
        model.addAttribute("totalPieces", totalPieces);
        model.addAttribute("totalPrice", totalPrice);
        return "extra";
    }

    @GetMapping("/add")
    public String addExtraForm(Model model){
        ExtraDTO extra = new ExtraDTO();
        model.addAttribute("extra", extra);
        return "extra-add";
    }

    @PostMapping("/updateQuantity")
    public String updateExtraQuantity(@RequestParam("id") Integer id, @RequestParam("quantity") Integer quantity, RedirectAttributes redirectAttributes) {
        try {
            extraService.updateQuantity(id, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Quantity updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating quantity.");
        }
        return "redirect:/extras/all";
    }
    @PostMapping("/add")
    public String addedExtra(@ModelAttribute("extra") ExtraDTO extraDTO) {
        ExtraDTO existingExtraDTO = extraService.findByAllAttributes(extraDTO.getBrand(), extraDTO.getCategory(), extraDTO.getName(), extraDTO.getPrice(), extraDTO.getQuantity());

        if (existingExtraDTO != null) {
            // If an exact match exists, update the quantity
            existingExtraDTO.setQuantity(existingExtraDTO.getQuantity() + extraDTO.getQuantity());
            extraService.saveExtra(existingExtraDTO); // Save the updated DTO
        } else {
            // No matching record found, save as a new entry
            extraService.saveExtra(extraDTO);
        }
        return "redirect:/extras/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteExtra(@PathVariable Integer id){
        extraService.deleteExtra(id);
        return "redirect:/extras/all";
    }

    @GetMapping("/edit/{id}")
    public String editExtraForm(@PathVariable Integer id,Model model){
        Optional<ExtraDTO> extra = extraService.findById(id);
        model.addAttribute("extra", extra.get());
        return "extra-edit";
    }

    @PostMapping("/edit/{id}")
    public String editExtra(@PathVariable Integer id, @ModelAttribute("extra") ExtraDTO updatedExtra, RedirectAttributes redirectAttrs) {
        try {
            extraService.editExtra(id, updatedExtra);
            redirectAttrs.addFlashAttribute("successMessage", "Extra updated successfully.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "Error updating extra.");
        }
        return "redirect:/extras/all";
    }
}
