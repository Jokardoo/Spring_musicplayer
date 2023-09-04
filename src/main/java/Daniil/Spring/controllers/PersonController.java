package Daniil.Spring.controllers;

import Daniil.Spring.Services.PersonService;
import Daniil.Spring.models.Person;
import Daniil.Spring.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();

        System.out.println(personDetails.getPerson());

        return "main";
    }

    @GetMapping("/user/{id}")
    public String showUser(Model model, @PathVariable ("id") int id) throws UsernameNotFoundException {
        Optional<Person> person = personService.findById(id);

        if (person.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        Person user = person.get();
        model.addAttribute("user", user);
        return "user/showUserInfo";

    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}
