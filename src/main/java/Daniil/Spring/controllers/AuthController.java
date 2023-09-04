package Daniil.Spring.controllers;

import Daniil.Spring.Services.RegistrationService;
import Daniil.Spring.models.Person;
import Daniil.Spring.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }


    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person")Person person) {
        return  "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {

        // Если в нашем объекте person есть ошибка, то она будет помещена в bindingResult
        personValidator.validate(person, bindingResult);

        // Если есть ошибки в person
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }
        // Если person валидный, то сохраняем его в БД
        registrationService.register(person);

        return "redirect:/auth/login";
    }


}
