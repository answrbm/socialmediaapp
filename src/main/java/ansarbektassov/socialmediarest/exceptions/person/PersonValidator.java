package ansarbektassov.socialmediarest.exceptions.person;

import ansarbektassov.socialmediarest.models.Person;
import ansarbektassov.socialmediarest.services.PeopleService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PersonValidator implements Validator {

    private final PeopleService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Person person = (Person) target;

        Optional<Person> foundByUsername = service.findByUsername(person.getUsername());
        Optional<Person> foundByEmail = service.findByEmail(person.getEmail());
        if(foundByUsername.isPresent())
            errors.rejectValue("username","","User with such username already exists");
        if(foundByEmail.isPresent())
            errors.rejectValue("email","","User with such email already exists");

    }
}
