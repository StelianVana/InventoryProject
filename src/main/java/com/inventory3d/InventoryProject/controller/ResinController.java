package com.inventory3d.InventoryProject.controller;

import com.inventory3d.InventoryProject.entity.Resin;
import com.inventory3d.InventoryProject.model.ResinDTO;
import com.inventory3d.InventoryProject.service.ResinService;
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
@RequestMapping("/resins")
public class ResinController {

    private final ResinService resinService;

    @GetMapping("/all")
    public String listResin(Model model) {
        List<Resin> resins = resinService.getAllResins();
        int totalPieces = resins.size();
        double totalPrice = resins.stream().mapToDouble(Resin::getPrice).sum();
        model.addAttribute("resins", resins);
        model.addAttribute("totalPieces", totalPieces);
        model.addAttribute("totalPrice", totalPrice);
        return "resin";
    }

    @GetMapping("/add")
    public String addResinForm(Model model) {
        ResinDTO resin = new ResinDTO();
        model.addAttribute("resin", resin);
        return "resin-add";
    }

    @PostMapping("/updateQuantity")
    public String updateResinQuantity(@RequestParam("id") Integer id, @RequestParam("quantity") Integer quantity, RedirectAttributes redirectAttributes) {
        try {
            resinService.updateQuantity(id, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Quantity updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating quantity.");
        }
        return "redirect:/resins/all";
    }

    @PostMapping("/add")
    public String addedResin(@ModelAttribute("resin") ResinDTO resinDTO) {
        ResinDTO existingResinDTO = resinService.findByAllAttributes(
                resinDTO.getBrand(), resinDTO.getColor(),
                resinDTO.getType(), resinDTO.getPrice(),
                resinDTO.getQuantity());

        if (existingResinDTO != null) {
            existingResinDTO.setQuantity(existingResinDTO.getQuantity() + resinDTO.getQuantity());
            resinService.saveResin(existingResinDTO);
        } else {
            resinService.saveResin(resinDTO);
        }
        return "redirect:/resins/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteResin(@PathVariable Integer id) {
        resinService.deleteResin(id);
        return "redirect:/resins/all";
    }

    @GetMapping("/edit/{id}")
    public String editResinForm(@PathVariable Integer id, Model model) {
        Optional<ResinDTO> resin = resinService.findById(id);
        model.addAttribute("resin", resin.get());
        return "resin-edit";
    }

    @PostMapping("/edit/{id}")
    public String editResin(@PathVariable Integer id, @ModelAttribute("resin") ResinDTO updatedResin, RedirectAttributes redirectAttrs) {
        try {
            resinService.editResin(id, updatedResin);
            redirectAttrs.addFlashAttribute("successMessage", "Resin updated successfully.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "Error updating resin.");
        }
        return "redirect:/resins/all";
    }
}
