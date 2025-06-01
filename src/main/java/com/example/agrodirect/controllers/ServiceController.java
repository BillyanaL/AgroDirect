package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.AddServiceDTO;
import com.example.agrodirect.models.dtos.ArticleViewDTO;
import com.example.agrodirect.models.dtos.ServiceViewDTO;
import com.example.agrodirect.models.dtos.UpdateServiceDTO;
import com.example.agrodirect.services.ServicesService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ServiceController {

    private final ServicesService servicesService;

    public ServiceController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }


    @GetMapping("/farmer/service/add")
    public ModelAndView add(Model model){
        if (!model.containsAttribute("addServiceDTO")){
            model.addAttribute("addServiceDTO",new AddServiceDTO());
        }

        return new ModelAndView("add-service");
    }

    @PostMapping("/farmer/service/add")
    public String createService(@Valid @ModelAttribute("addServiceDTO") AddServiceDTO dto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("addServiceDTO",dto);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "addServiceDTO",bindingResult);
            return "add-service";
        }

        servicesService.createService(dto);

        redirectAttributes.addFlashAttribute("successMessage", "Услугата беше създадена успешно и чака одобрение.");
        return "redirect:/farmer/services/my";

    }

    @GetMapping("/farmer/services/my")
    public String myServices(Model model) {

        List<ServiceViewDTO> myServices = servicesService.getAllServicesByFarmerId();
        model.addAttribute("services", myServices);

        return "my-service";
    }

    @GetMapping("/farmer/service/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        UpdateServiceDTO dto = servicesService.getUpdateDTOById(id);
        model.addAttribute("editServiceDTO", dto);
        return "edit-service";
    }

    @PostMapping("/farmer/service/edit/{id}")
    public String updateService(@PathVariable Long id,
                                @Valid @ModelAttribute("editServiceDTO") UpdateServiceDTO dto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("editServiceDTO", dto);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "editServiceDTO", bindingResult);
            return "edit-service";
        }

        servicesService.updateService(id, dto);

        redirectAttributes.addFlashAttribute("successMessage", "Услугата беше успешно обновена и чака одобрение.");
        return "redirect:/farmer/services/my";
    }

    @PostMapping("/farmer/service/delete/{id}")
    public String deleteService(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            servicesService.deleteServiceById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Услугата беше успешно изтрита.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/farmer/services/my";
    }

    @GetMapping("/services/{id}")
    public String serviceDetails(@PathVariable Long id, Model model) {
        ServiceViewDTO service = servicesService.getServiceViewById(id); // създай такъв метод
        model.addAttribute("service", service);
        return "service-details";
    }

    @GetMapping("/services")
    public String viewAllServices(Model model) {
        List<ServiceViewDTO> allApprovedServices = servicesService.getAllApprovedServices();
        model.addAttribute("allServices", allApprovedServices);
        return "services"; // services.html (шаблонът по-горе)
    }




}
