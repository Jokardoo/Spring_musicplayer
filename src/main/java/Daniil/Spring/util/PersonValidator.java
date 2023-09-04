package Daniil.Spring.util;

import Daniil.Spring.Services.PersonDetailsService;
import Daniil.Spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Person person = (Person) o;

        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        }
        catch (UsernameNotFoundException ignored) {
            return; //здесь все ок, пользователь с таким именем не найден
        }

        errors.rejectValue("username", "", "человек с таким именем пользователя уже существует");
    }
}
