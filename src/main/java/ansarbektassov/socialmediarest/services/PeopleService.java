package ansarbektassov.socialmediarest.services;

import ansarbektassov.socialmediarest.dto.PersonUpdateDTO;
import ansarbektassov.socialmediarest.exceptions.person.PersonNotFoundException;
import ansarbektassov.socialmediarest.models.Person;
import ansarbektassov.socialmediarest.repositories.PeopleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PeopleService {

    private PeopleRepository peopleRepository;

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int personId) {
        return peopleRepository.findById(personId).orElseThrow(PersonNotFoundException::new);
    }

    public Optional<Person> findByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }

    public Optional<Person> findByEmail(String email) {
        return peopleRepository.findByEmail(email);
    }

    public void save(Person personToSave) {
        peopleRepository.save(personToSave);
    }

    public void update(int personId, PersonUpdateDTO personUpdateDTO) {
        Person person = findById(personId);
        person.setName(personUpdateDTO.getName());
        save(person);
    }

    public void delete(int personId) {
        Person person = findById(personId);
        peopleRepository.delete(person);
    }



}
