package ansarbektassov.socialmediarest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonRegisterDTO {

    @NotNull(message = "Username shouldn't be null")
    @NotEmpty(message = "Username shouldn't be empty")
    private String username;
    @NotNull(message = "Password shouldn't be null")
    @NotEmpty(message = "Password shouldn't be empty")
    private String password;
    @NotNull(message = "Email shouldn't be null")
    @NotEmpty(message = "Email shouldn't be empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Name shouldn't be null")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 200, message = "Name should be between 2 and 200 characters")
    private String name;
}
