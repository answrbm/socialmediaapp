package ansarbektassov.socialmediarest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostCreateDTO {

    @NotEmpty(message = "Title shouldn't be empty")
    @NotNull(message = "Title shouldn't be null")
    @Size(min = 1,max = 100, message = "Title should be between 1 and 100 characters")
    private String title;
    @NotEmpty(message = "Description shouldn't be empty")
    @NotNull(message = "Description shouldn't be null")
    @Size(min = 1,max = 1000, message = "Description should be between 1 and 1000 characters")
    private String description;
}
