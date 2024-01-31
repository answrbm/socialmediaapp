package ansarbektassov.socialmediarest.controllers;

import ansarbektassov.socialmediarest.dto.AuthenticationDTO;
import ansarbektassov.socialmediarest.dto.PersonRegisterDTO;
import ansarbektassov.socialmediarest.exceptions.person.PersonNotCreatedException;
import ansarbektassov.socialmediarest.exceptions.person.PersonNotFoundException;
import ansarbektassov.socialmediarest.exceptions.person.PersonValidator;
import ansarbektassov.socialmediarest.models.Person;
import ansarbektassov.socialmediarest.security.JWTUtil;
import ansarbektassov.socialmediarest.services.PeopleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtGenerator;

    @Operation(
            description = "Post endpoint for authentication",
            summary = "Registration endpoint",
            responses = {
                    @ApiResponse(
                            description = "Successful registration",
                            responseCode = "200",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Invalid data",
                            responseCode = "400",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Not supported in registration",
                            responseCode = "404",
                            content = @Content
                    )
            }
    )
    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Validated PersonRegisterDTO personRegisterDTO
            ,BindingResult bindingResult) {
        Person person = convertToPerson(personRegisterDTO);
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            throw new PersonNotCreatedException(buildError(bindingResult.getFieldErrors()));
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        person.setRole("ROLE_USER");
        peopleService.save(person);
        return Map.of("message", "created");
    }

    @Operation(
            description = "Get endpoint for authentication",
            summary = "Login endpoint",
            responses = {
                    @ApiResponse(
                            description = "Successful login",
                            responseCode = "200",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Incorrect login or password",
                            responseCode = "401",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Not supported in login",
                            responseCode = "404",
                            content = @Content
                    )
            }
    )
    @CrossOrigin
    @PostMapping("/login")
    public Map<String,String> performLogin(@RequestBody AuthenticationDTO authDTO) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(),authDTO.getPassword());

        authenticationManager.authenticate(authToken);

        Person person = peopleService.findByUsername(authDTO.getUsername()).orElseThrow(PersonNotFoundException::new);

        String jwt = jwtGenerator.generateToken(person);
        return Map.of("jwt_token",jwt);
    }

    public Person convertToPerson(PersonRegisterDTO personRegisterDTO) {
        return modelMapper.map(personRegisterDTO,Person.class);
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
