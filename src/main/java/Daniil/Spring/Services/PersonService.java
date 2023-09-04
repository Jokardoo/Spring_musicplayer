package Daniil.Spring.Services;

import Daniil.Spring.models.Person;
import Daniil.Spring.repositories.PeopleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private final PeopleRepository peopleRepository;

    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Optional<Person> findById(int id) {
        return peopleRepository.findById(id);
    }
}
