package Daniil.Spring.Services;

import Daniil.Spring.models.Person;
import Daniil.Spring.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final PeopleRepository peopleRepository;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, PeopleRepository peopleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.peopleRepository = peopleRepository;
    }

    // шифроание
    @Transactional
    public void register(Person person) {
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        // По конвенции роли пользователей нужно начинать со слова "ROLE_", тогда Spring Security будет понимать, что это роль
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
    }
}
