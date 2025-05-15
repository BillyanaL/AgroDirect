package com.example.agrodirect.controllers;

import com.example.agrodirect.exceptions.LoginCredentialsException;
import com.example.agrodirect.models.dtos.UserRegistrationDTO;
import com.example.agrodirect.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";
    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/register")
    public ModelAndView register(Model model) {

        if (!model.containsAttribute("userRegistrationDTO")) {
            model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        }

        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid UserRegistrationDTO userRegistrationDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        final ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            final String attributeName = "userRegistrationDTO";
            redirectAttributes
                    .addFlashAttribute(attributeName, userRegistrationDTO)
                    .addFlashAttribute(BINDING_RESULT_PATH + DOT + attributeName, bindingResult);
            modelAndView.setViewName("redirect:register");

        } else {

            this.authenticationService.register(userRegistrationDTO);
            modelAndView.setViewName("redirect:login");
        }

        return modelAndView;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ExceptionHandler(LoginCredentialsException.class)
    public ModelAndView handleLoginCredentialsError(LoginCredentialsException e,
                                                    RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("badCredentials", true);
        System.out.println(e.getMessage());
        return new ModelAndView("redirect:login");
    }

}
