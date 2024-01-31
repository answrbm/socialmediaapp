package ansarbektassov.socialmediarest.services;

import ansarbektassov.socialmediarest.models.Person;
import ansarbektassov.socialmediarest.repositories.PeopleRepository;
import ansarbektassov.socialmediarest.security.PersonDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> foundPerson = peopleRepository.findByUsername(username);
        if(foundPerson.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(foundPerson.get());
    }
}
