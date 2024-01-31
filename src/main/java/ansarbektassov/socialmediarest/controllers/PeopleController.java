package ansarbektassov.socialmediarest.controllers;

import ansarbektassov.socialmediarest.dto.PersonDTO;
import ansarbektassov.socialmediarest.dto.PersonUpdateDTO;
import ansarbektassov.socialmediarest.dto.PersonRegisterDTO;
import ansarbektassov.socialmediarest.exceptions.person.PersonNotCreatedException;
import ansarbektassov.socialmediarest.models.Person;
import ansarbektassov.socialmediarest.services.PeopleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/people")
@Tag(name = "People")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<PersonDTO> findAll() {
        return peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList());
    }

    @GetMapping("/{personId}")
    public PersonRegisterDTO findById(@PathVariable("personId") int personId) {
        Person person = peopleService.findById(personId);
        return convertToPersonRegisterDTO(person);
    }

    @PutMapping("/{personId}")
    public Map<String, String> update(@PathVariable("personId") int personId, @RequestBody @Validated PersonUpdateDTO personUpdateDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new PersonNotCreatedException(buildError(bindingResult.getFieldErrors()));
        peopleService.update(personId,personUpdateDTO);
        return Map.of("message","updated");
    }

    @DeleteMapping("/{personId}")
    public Map<String,String> delete(@PathVariable("personId") int personId) {
        peopleService.delete(personId);
        return Map.of("message","deleted");
    }

    public PersonRegisterDTO convertToPersonRegisterDTO(Person person) {
        return modelMapper.map(person,PersonRegisterDTO.class);
    }

    public PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    public String buildError(List<FieldError> fieldErrors) {
        StringBuilder errorMessage = new StringBuilder();
        for(FieldError error : fieldErrors) {
            errorMessage.append(error.getField())
                    .append("-").append(error.getDefaultMessage())
                    .append(";");
        }
        return errorMessage.toString();
    }

}
