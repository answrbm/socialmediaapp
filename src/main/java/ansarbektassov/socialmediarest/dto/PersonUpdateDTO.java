package ansarbektassov.socialmediarest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonUpdateDTO {

    @NotNull(message = "Name shouldn't be null")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 200, message = "Name should be between 2 and 200 characters")
    private String name;
}
